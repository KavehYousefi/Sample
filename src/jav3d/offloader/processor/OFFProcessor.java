// 20.08.2015
// 
// COPY OF "OFFLineGeometryReaderListener03".
// 
// color specification for OFF files: -> "http://www.holmes3d.net/graphics/offfiles/offdesc.txt"
// color per face: -> "http://stackoverflow.com/questions/26369331/face-coloring-in-java3d"

package jav3d.offloader.processor;

import jav3d.offloader.properties.ColorMapHandling;
import jav3d.offloader.properties.DataOrdering;
import jav3d.offloader.properties.GeometryGroupingPolicy;
import jav3d.offloader.properties.NormalAutoGenerationPolicy;
import jav3d.offloader.properties.NormalizationPolicy;
import jav3d.offloader.properties.OFFLoaderProperties;
import ky.Cuboid;
import ky.Position;
import ky.PositionToPointConverter;
import ky.PositionUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.media.j3d.Geometry;
import javax.media.j3d.IndexedGeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JOptionPane;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;

import ky.math.BoundsNormalizer;
import ky.math.CuboidAroundPointsCalculator;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;


/* TODO:
 *  o IMPLEMENT FULL SPECIFICATION FROM
 *      - http://www.geomview.org/docs/html/OFF.html
 *      - http://www.holmes3d.net/graphics/offfiles/offdesc.txt
 *    For instance: File starts with "COFF", "NOFF", "4OFF", etc.
 *    (and perhaps STOFF      for texture coordinates,
 *                 OFF BINARY for binary format).
 *    Examples:
 *      - COFF: http://people.sc.fsu.edu/~jburkardt/data/off/vertcube.off
 *      - 4OFF: http://people.sc.fsu.edu/~jburkardt/data/off/trapezoid.4d.off
 *              http://people.sc.fsu.edu/~jburkardt/data/off/hypercube.4d.off
 *    
 *    Other examples:
 *      - color map indices: http://people.sc.fsu.edu/~jburkardt/data/off/abstr.off
 *  
 *  o Handle colors in vertex and face lines.
 *    {!} ESPECIALLY colors per vertex              {!}
 *    {!} ALLOW MIXING PER VERTEX & PER FACE COLORS {!}
 *  
 *  o Handle comments in vertex and face lines.
 *  
 *  o Handle common errors:
 *      - Example for missing line break: http://people.sc.fsu.edu/~jburkardt/data/off/socket.off
 */
public class OFFProcessor
implements   OFFLineReaderListener
{
  private OFFHeaderParser      headerParser;
  // List of collected vertices (vertex rows).
  private List<Vertex>         vertices;
  private List<FaceIndexGroup> faceIndexGroups;
  /* The number of vertex indices for the last face. Necessary for
   * creating FaceIndexGroups: If the current number of vertex indices
   * differs from this value, a new FaceIndexGroup has to be created,
   * and the old has to be saved.
   */
  private int                  lastIndexCountPerFace;
  // Current FaceIndexGroup to add faces (= lines) to.
  private FaceIndexGroup       currentFaceIndexGroup;
  private OFFHeaderDescriptor  headerDescriptor;
  private OFFLoaderProperties  properties;
  
  
  public OFFProcessor ()
  {
    this.headerParser           = new OFFHeaderParser           ();
    this.vertices               = new ArrayList<Vertex>         ();
    this.faceIndexGroups        = new ArrayList<FaceIndexGroup> ();
    this.lastIndexCountPerFace  = -1;
    this.currentFaceIndexGroup  = null;
    this.headerDescriptor       = null;
    this.properties             = new OFFLoaderProperties ();
    
    reset ();
  }
  
  
  public OFFLoaderProperties getProperties ()
  {
    return properties;
  }
  
  public void setProperties (OFFLoaderProperties properties)
  {
    this.properties = properties;
  }
  
  
  public List<Position> getPoints ()
  {
    List<Position> points = new ArrayList<Position> ();
    
    for (Vertex vertex : vertices)
    {
      points.add (vertex.getPosition ());
    }
    
    if (properties.getNormalizationPolicy ().equals (NormalizationPolicy.TRANSFORM_VERTEX_COORDINATES))
    {
      Cuboid      sourceBounds = new CuboidAroundPointsCalculator (points).getCuboid ();
      Transform3D normalizing  = new BoundsNormalizer ().getNormalizingTransform3D (sourceBounds);
      points = PositionUtils.getTransformedPoints (points, normalizing);
    }
    
    return points;
  }
  
  public boolean hasPerVertexColor ()
  {
    for (Vertex vertex : vertices)
    {
      if (vertex.getColorComponentCount () > 0)
      {
        return true;
      }
    }
    
    return false;
  }
  
  public List<FaceIndexGroup> getFaceIndexGroups ()
  {
    return faceIndexGroups;
  }
  
  public boolean hasColorForAllFacesInFaceIndexGroup (FaceIndexGroup faceIndexGroup)
  {
    for (Face face : faceIndexGroup.getFaces ())
    {
      if (face.getColor4f () == null)
      {
        return false;
      }
    }
    
    return true;
  }
  
  public boolean hasColorForAllFaces ()
  {
    for (FaceIndexGroup faceIndexGroup : faceIndexGroups)
    {
      if (! hasColorForAllFacesInFaceIndexGroup (faceIndexGroup))
      {
        return false;
      }
    }
    
    return true;
  }
  
  public boolean usesColorMap ()
  {
    for (FaceIndexGroup faceIndexGroup : faceIndexGroups)
    {
      for (Face face : faceIndexGroup.getFaces ())
      {
        if (face.getColorMapIndex () == null)
        {
          return false;
        }
      }
    }
    
    // Only use the color map, if wanted.
    if (properties.getColorMapHandling ().equals (ColorMapHandling.IGNORE_COLORMAP))
    {
      return false;
    }
    
    return true;
  }
  
  public List<Face> getAllFaces ()
  {
    List<Face> faces = new ArrayList<Face> ();
    
    for (FaceIndexGroup faceIndexGroup : faceIndexGroups)
    {
      faces.addAll (faceIndexGroup.getFaces ());
    }
    
    return faces;
  }
  
  
  // Interesting if NormalizationPolicy.CREATE_TRANSFORM_GROUP chosen.
  // Otherwise simply a TransformGroup with default Transform3D.
  public TransformGroup getTransformGroup ()
  {
    TransformGroup transformGroup = null;
    
    if (properties.getNormalizationPolicy ().equals (NormalizationPolicy.CREATE_TRANSFORM_GROUP))
    {
      List<Position> points       = getPoints ();
      Cuboid         sourceBounds = new CuboidAroundPointsCalculator (points).getCuboid ();
      transformGroup = new BoundsNormalizer ().getNormalizingTransformGroup (sourceBounds);
    }
    else
    {
      transformGroup = new TransformGroup ();
    }
    
    for (Geometry geometry : getGeometries ())
    {
      transformGroup.addChild (new Shape3D (geometry));
    }
    
    return transformGroup;
  }
  
  
  public List<Geometry> getGeometries ()
  {
    List<Geometry>         geometries       = null;
    GeometryGroupingPolicy geometryGrouping = null;
    
    geometries       = new ArrayList<Geometry>              ();
    geometryGrouping = properties.getGeometryGroupingPolicy ();
    
    switch (geometryGrouping)
    {
      case PER_FILE :
      {
        geometries.addAll (getGeometryPerFile ());
        break;
      }
      case PER_FACE_INDEX_GROUP :
      {
        geometries.addAll (getGeometriesPerFaceIndexGroup ());
        break;
      }
      case PER_FACE :
      {
        geometries.addAll (getGeometriesPerFace ());
        break;
      }
      default :
      {
        throw new IllegalStateException ("Invalid GeometryGroupingPolicy: " + geometryGrouping);
      }
    }
    
    return geometries;
  }
  
  // Create ONE SINGLE geometry for all faces.
  // Using List<Geometry> instead of direct Geometry for convenience.
  public List<Geometry> getGeometryPerFile ()
  {
    List<Geometry>       geometries        = null;
    IndexedGeometryArray geometry          = null;
    int                  geometryType      = 0;
    GeometryInfo         geometryInfo      = null;
    Point3d[]            coordinates       = null;
    int[]                coordinateIndices = null;
    List<Face>           faces             = null;
    int                  faceCount         = 0;
    boolean              hasPerVertexColor = false;
    boolean              hasPerFaceColor   = false;
    boolean              usesColorMap      = false;
    
    geometries        = new ArrayList<Geometry>         ();
    coordinateIndices = getCoordinateIndicesForAllFaces ();
    faces             = getAllFaces                     ();
    faceCount         = faces.size                      ();
    coordinates       = PositionUtils.convertPositionListToPoint3dArray (getPoints ());
    coordinateIndices = getCoordinateIndicesForAllFaces ();
    
    hasPerVertexColor = true;
    hasPerFaceColor   = true;
    usesColorMap      = true;
    
    for (Vertex vertex : vertices)
    {
      if (vertex.getColor4f () == null)
      {
        hasPerVertexColor = false;
      }
    }
    
    for (Face face : faces)
    {
      if (face.getColor4f () == null)
      {
        hasPerFaceColor = false;
      }
      
      if (face.getColorMapIndex () == null)
      {
        usesColorMap = false;
      }
    }
    
    // Only use the color map, if wanted.
    if (properties.getColorMapHandling ().equals (ColorMapHandling.IGNORE_COLORMAP))
    {
      usesColorMap = false;
    }
    
//    colorComponents   = face.getColorComponents  ();
//    colorCompCount    = colorComponents.size     ();
//    hasPerVertexColor = hasPerVertexColor        ();
//    hasPerFaceColor   = (colorCompCount >= 3);
    
    // Choose, which geometry type (primitive) to create.
    switch (properties.getGeometryTypePolicy ())
    {
      case SELECT_BEST_PRIMITIVE :
      {
        if (areAllFacesQuadrilaterals ())
        {
          geometryType = GeometryInfo.QUAD_ARRAY;
        }
        else
        {
          geometryType = GeometryInfo.POLYGON_ARRAY;
        }
        break;
      }
      case ALWAYS_GENERATE_TRIANGLES :
      {
        geometryType = GeometryInfo.POLYGON_ARRAY;
        break;
      }
    }
    
    geometryInfo = new GeometryInfo   (geometryType);
    geometryInfo.setCoordinates       (coordinates);
    geometryInfo.setCoordinateIndices (coordinateIndices);
    
    
    /*
    // For creating texture coordinates.
    TexCoord2f[] tc = new TexCoord2f[coordinates.length];
    for (int i = 0; i < tc.length; i++)
    {
      tc[i] = new TexCoord2f ((float) Math.random (), (float) Math.random ());
//      tc[i] = new TexCoord2f ();
    }
    geometryInfo.setTextureCoordinateParams  (1, 2);
    geometryInfo.setTextureCoordinateIndices (0, coordinateIndices);
    geometryInfo.setTextureCoordinates       (0, tc);
    */
    
    
    if (geometryType == GeometryInfo.POLYGON_ARRAY)
    {
      // "strips" are actually faces.
      int[] stripCounts = new int[faceCount];
      
      /* Each face becomes a strip, represented by its number
       * of vertex indices.
       */
      for (int faceIndex = 0; faceIndex < faceCount; faceIndex++)
      {
        Face face             = faces.get                (faceIndex);
        int  vertexIndexCount = face.getVertexIndexCount ();
        
        stripCounts[faceIndex] = vertexIndexCount;
      }
      
      geometryInfo.setStripCounts (stripCounts);
    }
    
    if (hasPerVertexColor)
    {
      Color4f[] colorArray   = null;
      int[]     colorIndices = null;
      
      colorArray   = getVertexColors                  ();
      colorIndices = getVertexColorIndicesForAllFaces ();
      
      geometryInfo.setColors       (colorArray);
      geometryInfo.setColorIndices (colorIndices);
    }
    
    if (hasPerFaceColor)
    {
      Color4f[] colorArray   = null;
      int[]     colorIndices = null;
      
      colorArray   = getColorsForAllFaces       ();
      colorIndices = getColorIndicesForAllFaces ();
      
      geometryInfo.setColors       (colorArray);
      geometryInfo.setColorIndices (colorIndices);
    }
    
    if (usesColorMap)
    {
      Color4f[] colorArray   = null;
      int[]     colorIndices = null;
      
      colorArray   = getColorMapColors             ();
      colorIndices = getColorMapIndicesForAllFaces ();
      
      geometryInfo.setColors       (colorArray);
      geometryInfo.setColorIndices (colorIndices);
    }
    
    /* Some OFF files, like
     * "http://people.sc.fsu.edu/~jburkardt/data/off/abstr.off"
     * seem to need reversed "geometryInfo".
     */
    applyDataOrdering           (geometryInfo);
    applyNormalGenerationPolicy (geometryInfo);
    
    geometry = geometryInfo.getIndexedGeometryArray ();
    
    geometries.add (geometry);
    
    return geometries;
  }
  
  // Create a geometry for EACH FACE INDEX GROUP.
  public List<Geometry> getGeometriesPerFaceIndexGroup ()
  {
    List<Geometry> geometries = new ArrayList<Geometry> ();
    
    for (FaceIndexGroup faceIndexGroup : faceIndexGroups)
    {
      IndexedGeometryArray geometry          = null;
      int                  indexCount        = 0;
      int[]                indexArray        = null;
      GeometryInfo         geometryInfo      = null;
      List<Integer>        indexList         = null;
      List<Double>         colorComponents   = null;
      int                  colorCompCount    = 0;
      int                  indexCountPerFace = 0;
      Point3d[]            coordinates       = null;
      int                  geometryType      = 0;
      boolean              hasPerVertexColor = false;
      boolean              hasPerFaceColor   = false;
      boolean              usesColorMap      = false;
      
      indexList         = faceIndexGroup.getVertexIndices     ();
      colorComponents   = faceIndexGroup.getColorComponents   ();
      colorCompCount    = colorComponents.size                ();
      indexCountPerFace = faceIndexGroup.getIndexCountPerFace ();
      hasPerVertexColor = hasPerVertexColor                   ();
      hasPerFaceColor   = (colorCompCount >= 3);
      usesColorMap      = usesColorMap ();
      
      indexCount      = faceIndexGroup.getVertexIndices ().size ();
      indexArray      = new int[indexCount];
      coordinates     = PositionUtils.convertPositionListToPoint3dArray (getPoints ());
      
      // Convert list of indices (Integer) to array of indices (int).
      for (int indexNumber = 0; indexNumber < indexCount; indexNumber++)
      {
        indexArray[indexNumber] = indexList.get (indexNumber);
      }
      
      // Choose, which geometry type (primitive) to create.
      geometryType = selectGeometryType (indexCountPerFace);
      
      geometryInfo = new GeometryInfo   (geometryType);
      geometryInfo.setCoordinates       (coordinates);
      geometryInfo.setCoordinateIndices (indexArray);
      
      if (geometryType == GeometryInfo.POLYGON_ARRAY)
      {
        // "strips" are actually faces.
        // => We must define an array, giving the number of vertices per face.
        //    This is easy: Each face in the FaceIndexGroup has the same number of vertices.
        //    The array's length is the number of faces in the FaceIndexGroup.
        int   stripCount  = faceIndexGroup.getFaceCount ();
        int[] stripCounts = new int[stripCount];
        
        for (int stripIndex = 0; stripIndex < stripCount; stripIndex++)
        {
          stripCounts[stripIndex] = indexCountPerFace;
        }
        
        geometryInfo.setStripCounts (stripCounts);
      }
      
      
      if (hasPerVertexColor)
      {
        Color4f[] colorArray   = null;
        int[]     colorIndices = null;
        
        colorArray   = getVertexColors       ();
        colorIndices = getVertexColorIndicesPerFaceIndexGroup (faceIndexGroup);
        
        geometryInfo.setColors       (colorArray);
        geometryInfo.setColorIndices (colorIndices);
      }
      
      if (hasPerFaceColor)
      {
        Color4f[] colorArray   = null;
        int[]     colorIndices = null;
        
        colorArray   = getFaceIndexGroupColors       (faceIndexGroup);
        colorIndices = getFaceIndexGroupColorIndices (faceIndexGroup);
        
        geometryInfo.setColors       (colorArray);
        geometryInfo.setColorIndices (colorIndices);
      }
      
      // References color map and shall not ignore it? => Load color data.
      if ((usesColorMap) &&
          (! properties.getColorMapHandling ().equals (ColorMapHandling.IGNORE_COLORMAP)))
      {
        Color4f[] colorArray   = null;
        int[]     colorIndices = null;
        
        colorArray   = getColorMapColors                ();
        colorIndices = getFaceIndexGroupColorMapIndices (faceIndexGroup);
        
        geometryInfo.setColors       (colorArray);
        geometryInfo.setColorIndices (colorIndices);
      }
      
      /* Some OFF files, like
       * "http://people.sc.fsu.edu/~jburkardt/data/off/abstr.off"
       * seem to need reversed "geometryInfo".
       */
      applyDataOrdering           (geometryInfo);
      applyNormalGenerationPolicy (geometryInfo);
      
      geometry = geometryInfo.getIndexedGeometryArray ();
      
      geometries.add (geometry);
    }
    
    return geometries;
  }
  
  // Create a geometry for EACH FACE.
  public List<Geometry> getGeometriesPerFace ()
  {
    List<Geometry> geometries = new ArrayList<Geometry> ();
    
    for (Face face : getAllFaces ())
    {
      IndexedGeometryArray geometry          = null;
      int                  indexCount        = 0;
      int[]                indexArray        = null;
      GeometryInfo         geometryInfo      = null;
      List<Integer>        indexList         = null;
      List<Double>         colorComponents   = null;
      int                  colorCompCount    = 0;
      int                  indexCountPerFace = 0;
      Point3d[]            coordinates       = null;
      int                  geometryType      = 0;
      boolean              hasPerVertexColor = false;
      boolean              hasPerFaceColor   = false;
      
      indexList         = face.getVertexIndices    ();
      colorComponents   = face.getColorComponents  ();
      colorCompCount    = colorComponents.size     ();
      indexCountPerFace = face.getVertexIndexCount ();
      hasPerVertexColor = hasPerVertexColor        ();
      hasPerFaceColor   = (colorCompCount >= 3);
      
      indexCount      = indexList.size ();
      indexArray      = new int[indexCount];
      coordinates     = new PositionToPointConverter ().convertPositionListToPoint3dArray (getPoints ());
      
      // Convert list of indices (Integer) to array of indices (int).
      for (int indexNumber = 0; indexNumber < indexCount; indexNumber++)
      {
        indexArray[indexNumber] = indexList.get (indexNumber);
      }
      
      // Choose, which geometry type (primitive) to create.
      geometryType = selectGeometryType (indexCountPerFace);
      
      geometryInfo = new GeometryInfo   (geometryType);
      geometryInfo.setCoordinates       (coordinates);
      geometryInfo.setCoordinateIndices (indexArray);
      
      if (geometryType == GeometryInfo.POLYGON_ARRAY)
      {
        // "strips" are actually faces.
        int   stripCount  = 1;
        int[] stripCounts = new int[stripCount];
        
        stripCounts[0] = indexCountPerFace;
        
        geometryInfo.setStripCounts (stripCounts);
      }
      
      if (hasPerVertexColor)
      {
        Color4f[] colorArray   = null;
        int[]     colorIndices = null;
        
        colorArray   = getVertexColors              ();
        colorIndices = getVertexColorIndicesPerFace (face);
        
        geometryInfo.setColors       (colorArray);
        geometryInfo.setColorIndices (colorIndices);
      }
      
      if (hasPerFaceColor)
      {
        Color4f[] colorArray   = null;
        int[]     colorIndices = null;
        
        colorArray   = getFaceColors       (face);
        colorIndices = getFaceColorIndices (face);
        
        geometryInfo.setColors       (colorArray);
        geometryInfo.setColorIndices (colorIndices);
      }
      
//      if (usesColorMap ())
      if (face.getColorMapIndex () != null)
      {
        Color4f[] colorArray   = null;
        int[]     colorIndices = null;
        
//        colorArray   = getFaceColorMapColors  (faceIndex);
        colorArray   = getColorMapColors      ();
        colorIndices = getFaceColorMapIndices (face);
        
        geometryInfo.setColors       (colorArray);
        geometryInfo.setColorIndices (colorIndices);
      }
      
      /* Some OFF files, like
       * "http://people.sc.fsu.edu/~jburkardt/data/off/abstr.off"
       * seem to need reversed "geometryInfo".
       */
      applyDataOrdering           (geometryInfo);
      applyNormalGenerationPolicy (geometryInfo);
      
      geometry = geometryInfo.getIndexedGeometryArray ();
      
      geometries.add (geometry);
    }
    
    return geometries;
  }
  
  public void reset ()
  {
    this.vertices               = new ArrayList<Vertex>         ();
    this.faceIndexGroups        = new ArrayList<FaceIndexGroup> ();
    this.lastIndexCountPerFace  = -1;
    this.currentFaceIndexGroup  = null;
    this.headerDescriptor       = null;
    this.properties             = new OFFLoaderProperties ();
  }
  
  
  
  // Is each face's number of vertex indices a multiple of four?
  // => Whole geometry can be represented by quadrilaterals.
  private boolean areAllFacesQuadrilaterals ()
  {
    for (Face face : getAllFaces ())
    {
      if ((face.getVertexIndexCount () % 4) != 0)
      {
        return false;
      }
    }
    
    return true;
  }
  
  private Color4f[] getVertexColors ()
  {
    Color4f[] colorArray  = null;
    int       vertexCount = 0;
    
    vertexCount = vertices.size ();
    colorArray  = new Color4f[vertexCount];
    
    for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++)
    {
      Vertex  vertex = vertices.get      (vertexIndex);
      Color4f color  = vertex.getColor4f ();
      
      colorArray[vertexIndex] = color;
    }
    
    return colorArray;
  }
  
  private int[] getVertexColorIndicesForAllFaces ()
  {
    int[]         colorIndices    = null;
    List<Integer> colorIndexList  = null;
    int           colorIndexCount = 0;
    
    colorIndexList = new ArrayList<Integer> ();
    
    for (Face face : getAllFaces ())
    {
      /* As the vertex index equals the vertex' color index, we only
       * have to insert each face's vertex index into the color index
       * list.
       */
      for (int index = 0; index < face.getVertexIndexCount (); index++)
      {
        int vertexIndex = face.getVertexIndices ().get (index);
        
        colorIndexList.add (new Integer (vertexIndex));
      }
    }
    
    colorIndexCount = colorIndexList.size ();
    colorIndices    = new int[colorIndexCount];
    
    for (int colorIndexPosition = 0; colorIndexPosition < colorIndexCount; colorIndexPosition++)
    {
      colorIndices[colorIndexPosition] = colorIndexList.get (colorIndexPosition);
    }
    
    return colorIndices;
  }
  
  private int[] getVertexColorIndicesPerFaceIndexGroup (FaceIndexGroup faceIndexGroup)
  {
    int[]      colorIndices       = null;
    int        colorIndexPosition = 0;
    List<Face> faces              = null;
    int        faceCount          = 0;
    int        colorIndexCount    = 0;
    int        indexCountPerFace  = faceIndexGroup.getIndexCountPerFace ();
    
    faces           = faceIndexGroup.getFaces ();
    faceCount       = faces.size              ();
    colorIndexCount = (faceCount * indexCountPerFace);
    colorIndices    = new int    [colorIndexCount];
    
    // Iterate over each face, ...
    for (int faceIndex = 0; faceIndex < faceCount; faceIndex++)
    {
      Face face = faces.get (faceIndex);
      
      // ... and insert its vertex indices as color indices.
      for (int index = 0; index < face.getVertexIndexCount (); index++)
      {
        int vertexIndex = face.getVertexIndices ().get (index);
        
        // As each vertex has a color, vertexIndex equals color index.
        colorIndices[colorIndexPosition] = vertexIndex;
        colorIndexPosition++;
        
//        System.out.format
//        (
//          "colorIndex = vertexIndex = %s, color = %s%n",
//          vertexIndex, vertices.get (vertexIndex).getColorComponents ()
//        );
      }
    }
    
    return colorIndices;
  }
  
  private int[] getVertexColorIndicesPerFace (Face face)
  {
    int[] colorIndices       = null;
    int   colorIndexPosition = 0;
    int   faceCount          = 0;
    int   colorIndexCount    = 0;
    int   indexCountPerFace  = face.getVertexIndexCount ();
    
    faceCount       = 1;
    colorIndexCount = (faceCount * indexCountPerFace);
    colorIndices    = new int [colorIndexCount];
    
    // ... and insert its vertex indices as color indices.
    for (int index = 0; index < face.getVertexIndexCount (); index++)
    {
      int vertexIndex = face.getVertexIndices ().get (index);
      
      // As each vertex has a color, vertexIndex equals color index.
      colorIndices[colorIndexPosition] = vertexIndex;
      colorIndexPosition++;
    }
    
    return colorIndices;
  }
  
  private int[] getCoordinateIndicesForAllFaces ()
  {
    int[]         coordinateIndices    = null;
    List<Integer> coordinateIndexList  = null;
    int           coordinateIndexCount = 0;
    List<Face>    faces                = null;
    
    coordinateIndexList = new ArrayList<Integer> ();
    faces               = getAllFaces            ();
    
    for (Face face : faces)
    {
      for (int vertexIndex : face.getVertexIndices ())
      {
        coordinateIndexList.add (vertexIndex);
      }
    }
    
    coordinateIndexCount = coordinateIndexList.size ();
    coordinateIndices    = new int[coordinateIndexCount];
    
    for (int coordIndexPosition = 0;
             coordIndexPosition < coordinateIndexCount;
             coordIndexPosition++)
    {
      coordinateIndices[coordIndexPosition] = coordinateIndexList.get (coordIndexPosition);
    }
    
    return coordinateIndices;
  }
  
  // All faces' colors.
  private Color4f[] getColorsForAllFaces ()
  {
    List<Color4f> colorList  = null;
    Color4f[]     colorArray = null;
    
    colorArray = new Color4f[0];
    colorList  = new ArrayList<Color4f> ();
    
    for (Face face : getAllFaces ())
    {
      colorList.add (face.getColor4f ());
    }
    
    colorArray = colorList.toArray (colorArray);
    
    return colorArray;
  }
  
  private int[] getColorIndicesForAllFaces ()
  {
    int[]         colorIndices    = null;
    List<Integer> colorIndexList  = null;
    int           faceCount       = 0;
    List<Face>    faces           = null;
    int           colorIndexCount = 0;
    
    colorIndices   = null;
    colorIndexList = new ArrayList<Integer> ();
    faces          = getAllFaces            ();
    faceCount      = faces.size             ();
    
    for (int faceIndex = 0; faceIndex < faceCount; faceIndex++)
    {
      Face face               = faces.get (faceIndex);
      int  indexRepitionCount = face.getVertexIndexCount ();
      
      /* Insert color index for each vertex index in face.
       * E.g.: Face at index 7 has five vertex indices.
       *       => {7, 7, 7, 7, 7}.
       */
      for (int colorIndexPosition = 0;
               colorIndexPosition < indexRepitionCount;
               colorIndexPosition++)
      {
        colorIndexList.add (new Integer (faceIndex));
      }
    }
    
    colorIndexCount = colorIndexList.size ();
    colorIndices    = new int[colorIndexCount];
    
    for (int colorIndexPosition = 0; colorIndexPosition < colorIndexCount; colorIndexPosition++)
    {
      colorIndices[colorIndexPosition] = colorIndexList.get (colorIndexPosition);
    }
    
    return colorIndices;
  }
  
  private int[] getColorMapIndicesForAllFaces ()
  {
    int[]         colorIndices    = null;
    List<Integer> colorIndexList  = null;
    int           faceCount       = 0;
    List<Face>    faces           = null;
    int           colorIndexCount = 0;
    
    colorIndices   = null;
    colorIndexList = new ArrayList<Integer> ();
    faces          = getAllFaces            ();
    faceCount      = faces.size             ();
    
    for (int faceIndex = 0; faceIndex < faceCount; faceIndex++)
    {
      Face face               = faces.get (faceIndex);
      int  indexRepitionCount = face.getVertexIndexCount ();
      
      /* Insert color index for each vertex index in face.
       * E.g.: Face at index 7 has five vertex indices.
       *       => {7, 7, 7, 7, 7}.
       */
      for (int colorIndexPosition = 0;
               colorIndexPosition < indexRepitionCount;
               colorIndexPosition++)
      {
        colorIndexList.add (face.getColorMapIndex ());
      }
    }
    
    colorIndexCount = colorIndexList.size ();
    colorIndices    = new int[colorIndexCount];
    
    for (int colorIndexPosition = 0; colorIndexPosition < colorIndexCount; colorIndexPosition++)
    {
      colorIndices[colorIndexPosition] = colorIndexList.get (colorIndexPosition);
    }
    
    return colorIndices;
  }
  
  private Color4f[] getFaceIndexGroupColors (FaceIndexGroup faceIndexGroup)
  {
    Color4f[]  colorArray = null;
    List<Face> faces      = null;
    int        faceCount  = 0;
    
    faces      = faceIndexGroup.getFaces ();
    faceCount  = faces.size              ();
    colorArray = new Color4f[faceCount];
    
    /* As each face has its own color, faceIndex and
     * color index are equal.
     */
    for (int faceIndex = 0; faceIndex < faceCount; faceIndex++)
    {
      Face    face  = faces.get (faceIndex);
      Color4f color = face.getColor4f ();
      
      colorArray[faceIndex] = color;
    }
    
    return colorArray;
  }
  
  private int[] getFaceIndexGroupColorIndices (FaceIndexGroup faceIndexGroup)
  {
    int[]      colorIndices       = null;
    int        colorIndexPosition = 0;
    List<Face> faces              = null;
    int        faceCount          = 0;
    int        colorIndexCount    = 0;
    int        indexCountPerFace  = 0;
    
    indexCountPerFace = faceIndexGroup.getIndexCountPerFace ();
    faces             = faceIndexGroup.getFaces ();
    faceCount         = faces.size              ();
    colorIndexCount   = (faceCount * indexCountPerFace);
    colorIndices      = new int    [colorIndexCount];
    
    /* As each face has its own color, faceIndex and
     * color index are equal.
     */
    for (int faceIndex = 0; faceIndex < faceCount; faceIndex++)
    {
      /* Repeat the color index "indexCountPerFace" times.
       * => We color each vertex of the face in the same color.
       *    => The whole face will be in the same color.
       */
      for (int colorRepitionNumber = 0;
               colorRepitionNumber < indexCountPerFace;
               colorRepitionNumber++)
      {
        colorIndices[colorIndexPosition] = faceIndex;
        colorIndexPosition++;
      }
    }
    
    return colorIndices;
  }
  
  /* TODO:
   *   HANDLE EXCEPTION (PASS/PRINT).
   */
  private Color4f[] getColorMapColors ()
  {
    Color4f[]          colorArray        = null;
    List<Color4f>      colorList         = null;
    ColorMapFileReader colorMapReader    = null;
    Path               colorMapFile      = null;
    boolean            ignoreMissingFile = false;
    
    colorMapFile      = properties.getColorMapFilePath ();
    colorMapReader    = new ColorMapFileReader         (colorMapFile);
    ignoreMissingFile = properties.getColorMapHandling   ().equals (ColorMapHandling.IGNORE_MISSING_COLORMAP_FILE);
    
    /* Color map file does not exist and no errors wanted?
     * => Return null.
     */
    if ((Files.notExists (colorMapFile)) && (ignoreMissingFile))
    {
      return null;
    }
    
    try
    {
      colorList = colorMapReader.getColor4fList ();
    }
    catch (Exception e)
    {
      JOptionPane.showMessageDialog (null, e.getMessage ());
    }
    
    colorArray = new Color4f[0];
    colorArray = colorList.toArray (colorArray);
    
    return colorArray;
  }
  
  private int[] getFaceIndexGroupColorMapIndices (FaceIndexGroup faceIndexGroup)
  {
    int[]      colorIndices       = null;
    int        colorIndexPosition = 0;
    List<Face> faces              = null;
    int        faceCount          = 0;
    int        colorIndexCount    = 0;
    int        indexCountPerFace  = faceIndexGroup.getIndexCountPerFace ();
    
    faces           = faceIndexGroup.getFaces ();
    faceCount       = faces.size              ();
    colorIndexCount = (faceCount * indexCountPerFace);
    colorIndices    = new int    [colorIndexCount];
    
    // Use the face's color map index.
    for (int faceIndex = 0; faceIndex < faceCount; faceIndex++)
    {
      Face face = faces.get (faceIndex);
      
      /* Repeat the color index "indexCountPerFace" times.
       * => We color each vertex of the face in the same color.
       *    => The whole face will be in the same color.
       */
      for (int colorRepitionNumber = 0;
               colorRepitionNumber < indexCountPerFace;
               colorRepitionNumber++)
      {
        colorIndices[colorIndexPosition] = face.getColorMapIndex ();
        colorIndexPosition++;
      }
    }
    
    return colorIndices;
  }
  
  private Color4f[] getFaceColors (Face face)
  {
    Color4f[] colorArray = null;
    
    colorArray    = new Color4f[1];
    colorArray[0] = face.getColor4f ();
    
    return colorArray;
  }
  
  private int[] getFaceColorIndices (Face face)
  {
    int[] colorIndices      = null;
    int   indexCountPerFace = 0;
    
    indexCountPerFace = face.getVertexIndexCount ();
    colorIndices      = new int[indexCountPerFace];
    
    // Repeat single color index 0 "indexCountPerFace" times.
//    for (int colorIndexPosition = 0;
//             colorIndexPosition < indexCountPerFace;
//             colorIndexPosition++)
//    {
//      colorIndices[colorIndexPosition] = 0;
//    }
    
    // Repeat single color index 0 "indexCountPerFace" times.
    Arrays.fill (colorIndices, 0);
    
    return colorIndices;
  }
  
  private int[] getFaceColorMapIndices (Face face)
  {
    int[] colorIndices      = null;
    int   indexCountPerFace = 0;
    
    indexCountPerFace = face.getVertexIndexCount ();
    colorIndices      = new int[indexCountPerFace];
    
    // Repeat single color index of face "indexCountPerFace" times.
    Arrays.fill (colorIndices, face.getColorMapIndex ());
    
    return colorIndices;
  }
  
  private int selectGeometryType (int indexCountPerFace)
  {
    int geometryType = 0;
    
    // Choose, which geometry type (primitive) to create.
    switch (properties.getGeometryTypePolicy ())
    {
      case SELECT_BEST_PRIMITIVE :
      {
        /* {!} NEEDS TESTING BECAUSE OF COLOR INDICES {!}
         *     => *POTENTIAL* SOURCE OF HARD TO DETECT BUGS.
         * 
         * Decide, if a IndexedQuadArray is possible.
         */
        if ((indexCountPerFace % 4) == 0)
        {
          geometryType = GeometryInfo.QUAD_ARRAY;
        }
        else
        {
          geometryType = GeometryInfo.POLYGON_ARRAY;
        }
        break;
      }
      case ALWAYS_GENERATE_TRIANGLES :
      {
        geometryType = GeometryInfo.POLYGON_ARRAY;
        break;
      }
    }
    
    return geometryType;
  }
  
  private void applyDataOrdering (GeometryInfo geometryInfo)
  {
    boolean reverseDataOrder = false;
    
    reverseDataOrder = properties.getDataOrdering ()
                                 .equals (DataOrdering.REVERSED);
    
    if (reverseDataOrder)
    {
      geometryInfo.reverse ();
    }
  }
  
  private void applyNormalGenerationPolicy (GeometryInfo geometryInfo)
  {
    NormalGenerator normalGenerator      = new NormalGenerator ();
    boolean         shallGenerateNormals = false;
    
    shallGenerateNormals = properties.getNormalGenerationPolicy ()
                                     .equals (NormalAutoGenerationPolicy.ALWAYS_GENERATE_NORMALS);
    
    if (shallGenerateNormals)
    {
      normalGenerator.generateNormals (geometryInfo);
    }
  }
  
  
  @Override
  public void commentLineEncountered (OFFLineReadValidLineEvent event)
  {
  }

  @Override
  public void emptyLineEncountered (OFFLineReadValidLineEvent event)
  {
  }

  @Override
  public void formatLineEncountered (OFFLineReadValidLineEvent event)
  {
    headerDescriptor = headerParser.getOFFHeaderDescriptor (event.getLine ());
  }

  @Override
  public void sizeLineEncountered (OFFLineReadValidLineEvent event)
  {
  }

  @Override
  public void vertexLineEncountered (OFFLineReadValidLineEvent event)
  {
    Vertex       vertex             = new Vertex      ();
    List<String> tokens             = event.getTokens ();
    // Either coordinates in 3D or 4D => 3 or 4.
    int          numberOfDimensions = 0;
    
    if (headerDescriptor.hasComponent (OFFHeaderComponent.FOUR_COORD_COMPONENTS))
    {
      numberOfDimensions = 4;
    }
    else
    {
      numberOfDimensions = 3;
    }
    
    // Add the vertex coordinates.
    for (int tokenIndex = 0; tokenIndex < numberOfDimensions; tokenIndex++)
    {
      String token         = tokens.get         (tokenIndex);
      double tokenAsDouble = Double.parseDouble (token);
      
      vertex.addCoordinate (tokenAsDouble);
    }
    
    // Add color components and/or normal coordinates.
    for (int tokenIndex = numberOfDimensions; tokenIndex < tokens.size (); tokenIndex++)
    {
      String token = tokens.get (tokenIndex);
      
      if (token.equalsIgnoreCase ("#"))
      {
        break;
      }
      else
      {
        double tokenAsDouble = Double.parseDouble (tokens.get (tokenIndex));
        
        vertex.addColorComponents (tokenAsDouble);
      }
    }
    
    vertices.add (vertex);
  }

  @Override
  public void faceLineEncountered (OFFLineReadValidLineEvent event)
  {
    Face         face           = null;
    String       preparedLine   = null;
    List<String> tokens         = null;
    int          tokenCount     = 0;
    int          faceIndexCount = 0;
    int          tokenCountAfterVertexIndices = 0;
    
    face           = new Face          ();
    preparedLine   = event.getLine     ();
    preparedLine   = ParserUtils.getLinePartBeforeComment (preparedLine);
    preparedLine   = preparedLine.trim ();
    tokens         = Arrays.asList     (preparedLine.split ("\\s+"));
    tokenCount     = tokens.size       ();
    faceIndexCount = Integer.parseInt  (tokens.get (0));
    
    /* New value for number of indices per face found?
     * => Start new FaceIndexGroup.
     */
    if (lastIndexCountPerFace != faceIndexCount)
    {
      // Add old FaceIndexGroup to the list.
      if (currentFaceIndexGroup != null)
      {
        faceIndexGroups.add (currentFaceIndexGroup);
      }
      
      lastIndexCountPerFace = faceIndexCount;
      currentFaceIndexGroup = new FaceIndexGroup ();
      currentFaceIndexGroup.setIndexCountPerFace (faceIndexCount);
      // Add new FaceIndexGroup to the list.
      faceIndexGroups.add (currentFaceIndexGroup);
    }
    
    tokenCountAfterVertexIndices = (tokenCount - (lastIndexCountPerFace + 1));
    
    currentFaceIndexGroup.addFace (face);
    
    face.setVertexIndexCount (faceIndexCount);
    
    for (int tokenIndex = 1; tokenIndex < tokenCount; tokenIndex++)
    {
      String token          = tokens.get (tokenIndex);
      int    tokenAsInteger = 0;
      
      /* Only the first [1, n] tokens are vertex indices.
       * The tokens after them are either face color components
       * (red, green, blue as double) or a comment.
       */
      if (tokenIndex <= lastIndexCountPerFace)
      {
        tokenAsInteger = Integer.parseInt (token);
        face.addVertexIndex                     (tokenAsInteger);
      }
      /* More tokens follow after the "faceIndexCount" values?
       * => Either color components (RGBA) or comment.
       */
      else
      {
        
        boolean isTokenIntegerValue = false;
        
        isTokenIntegerValue = token.matches ("[0-9]+");
        
        // Token is integer value? => Either component or index.
        if (isTokenIntegerValue)
        {
          tokenAsInteger = Integer.parseInt (token);
          
          // Only one integer token follows indices? => Color map index.
          if (tokenCountAfterVertexIndices == 1)
          {
            face.setColorMapIndex (tokenAsInteger);
          }
          // More than one integer token? => RGB/RGBA as integers.
          else
          {
            face.addIntegerColorComponent (tokenAsInteger);
          }
        }
        // Token is no integer value? => Interpret as double.
        else
        {
          double tokenAsDouble = Double.parseDouble (token);
          
          face.addColorComponent (tokenAsDouble);
        }
      }
    }
  }
  
  @Override
  public void endOfFileLineEncountered (OFFLineReadValidLineEvent event)
  {
  }
}
