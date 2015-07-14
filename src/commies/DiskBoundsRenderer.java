// 14.07.2015

package commies;

import javax.media.j3d.Appearance;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.media.j3d.TriangleFanArray;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;


public class DiskBoundsRenderer
{
  private float TRANSPARENCY_VALUE = 0.75f;
  
  private StandardGroupHierarchy groupHierarchy;
  private Shape3D                diskShape3D;
  
  
  public DiskBoundsRenderer (int cornerPointCount)
  {
    this.groupHierarchy = new StandardGroupHierarchy ();
    this.diskShape3D    = createDiskShape3D          (cornerPointCount);
  }
  
  
  public BranchGroup getRootNode ()
  {
    BranchGroup rootNode = null;
    
    rootNode = new BranchGroup ();
    
//    rootNode.addChild (sphere);
//    rootNode.addChild (collisionDetector);
    
    return rootNode;
  }
  
  public void setAppearance (Appearance appearance)
  {
    this.diskShape3D.setAppearance (appearance);
  }
  
  
//  public void addCollisionListener (CollisionListener listener)
//  {
//    collisionDetector.addCollisionListener (listener);
//  }
//  
  
//  public void removeCollisionListener (CollisionListener listener)
//  {
//    collisionDetector.removeCollisionListener (listener);
//  }
  
  
  private Appearance createAppearance ()
  {
    Appearance             appearance          = null;
    TransparencyAttributes transparencyAttribs = null;
    Material               material            = null;
    
    appearance          = new Appearance             ();
    transparencyAttribs = new TransparencyAttributes ();
    material            = new Material               ();
    
    transparencyAttribs.setTransparencyMode (TransparencyAttributes.BLENDED);
    transparencyAttribs.setTransparency     (TRANSPARENCY_VALUE);
    
    material.setAmbientColor  (0.20f, 0.20f, 0.70f);
    material.setEmissiveColor (0.00f, 0.00f, 0.00f);
    material.setDiffuseColor  (0.20f, 0.20f, 0.70f);
    material.setSpecularColor (0.85f, 0.85f, 0.85f);
    material.setShininess     (22.0f);
    material.setCapability    (Material.ALLOW_COMPONENT_READ |
                               Material.ALLOW_COMPONENT_WRITE);
    
    appearance.setMaterial               (material);
    appearance.setTransparencyAttributes (transparencyAttribs);
    
    return appearance;
  }
  
  private Shape3D createDiskShape3D (int cornerPointCount)
  {
    Shape3D    diskShape3D  = null;
    Geometry   diskGeometry = null;
    Appearance appearance   = null;
    
    diskShape3D  = new Shape3D        ();
    diskGeometry = createDiskGeometry (cornerPointCount);
    appearance   = createAppearance   ();
    
    diskShape3D.setGeometry   (diskGeometry);
    diskShape3D.setAppearance (appearance);
    
    return diskShape3D;
  }
  
  private Geometry createDiskGeometry (int cornerPointCount)
  {
    GeometryArray    finalGeometry     = null;
    GeometryInfo     geometryInfo      = null;
    NormalGenerator  normalGenerator   = null;
    TriangleFanArray rawGeometry       = null;
    int              vertexCount       = 0;
    int              vertexFormat      = 0;
    int[]            stripVertexCounts = null;
    double           currentAngle      = 0.0;
    double           angleStepSize     = 0.0;
    double           angleDivisor      = 0.0;
    
    vertexCount       = cornerPointCount + 1;
    vertexFormat      = TriangleFanArray.COORDINATES |
                        TriangleFanArray.NORMALS;
    stripVertexCounts = new int[] {vertexCount};
    rawGeometry       = new TriangleFanArray (vertexCount,
                                              vertexFormat,
                                              stripVertexCounts);
    normalGenerator   = new NormalGenerator ();
    angleDivisor      = new Integer (cornerPointCount).doubleValue ();
    angleStepSize     = ((2.0 * Math.PI) / angleDivisor);
    
    rawGeometry.setCoordinate (0, new Point3d (0.0, 0.0, 0.0));
    
    for (int vertexIndex = 1; vertexIndex < vertexCount; vertexIndex++)
    {
      Point3d cornerPoint = null;
      double  xCoordinate = 0.0;
      double  yCoordinate = 0.0;
      double  zCoordinate = 0.0;
      
      xCoordinate  = Math.cos (currentAngle);
      yCoordinate  = Math.sin (currentAngle);
      zCoordinate  = 0.0;
      cornerPoint  = new Point3d (xCoordinate, yCoordinate, zCoordinate);
      
      rawGeometry.setCoordinate (vertexIndex, cornerPoint);
      currentAngle = currentAngle + angleStepSize;
    }
    
    geometryInfo = new GeometryInfo               (rawGeometry);
    normalGenerator.generateNormals               (geometryInfo);
    finalGeometry = geometryInfo.getGeometryArray ();
    
    return finalGeometry;
  }
}
