package jav3d.lathep001;

import ky.Position;
import ky.angle.Angle;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.GeometryArray;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;


public class LatheGeometry
{
  // What to do, if slice == sliceCount?
  public static enum BackAtStartBehavior
  {
    WRAP_TO_ORIGIN,
    ADVANCE;
    
    private BackAtStartBehavior ()
    {
    }
  }
  
  
  private static final int    POINTS_PER_QUAD       = 4;
  private static final int    COORDINATES_PER_POINT = 3;
  private static final int    COORDINATES_PER_QUAD  = POINTS_PER_QUAD * COORDINATES_PER_POINT;
  
  private static final Angle  ANGLE_INCR  = new Angle (15.0);
  
  private LathePath           lathePath;
  private GeometryArray       geometry;
  private LatheBase           latheBase;
  private Angle               angleStep;
  private int                 sliceCount;
  private BackAtStartBehavior backAtStartBehavior;
  
  
  /**
   * <div class="">
   *   Creates a lathe geometry from a given lathe path and default
   *   rest properites.
   * </div>
   * 
   * @param lathePath  The profile cure defining the lathe path.
   */
  public LatheGeometry (LathePath lathePath)
  {
    this.lathePath           = lathePath;
    this.latheBase           = new CircularLatheBase ();
    this.angleStep           = new Angle             (ANGLE_INCR);
    this.sliceCount          = calculateSliceCount   (angleStep);
    this.backAtStartBehavior = BackAtStartBehavior.WRAP_TO_ORIGIN;
  }
  
  
  public Angle getAngleStep ()
  {
    return angleStep;
  }
  
  public void setAngleStep (Angle angleStep)
  {
    this.angleStep      = angleStep;
    this.sliceCount = calculateSliceCount (this.angleStep);
  }
  
  
  public LathePath getLathePath ()
  {
    return lathePath;
  }
  
  public void setLathePath (OldPathTypeBasedLathePath lathePath)
  {
    this.lathePath = lathePath;
  }
  
  public LatheBase getLatheBase ()
  {
    return latheBase;
  }
  
  public void setLatheBase (LatheBase latheBase)
  {
    this.latheBase = latheBase;
  }
  
  public BackAtStartBehavior getBackAtStartBehavior ()
  {
    return backAtStartBehavior;
  }
  
  public void setBackAtStartBehavior (BackAtStartBehavior backAtStartBehavior)
  {
    this.backAtStartBehavior = backAtStartBehavior;
  }
  
  
  // TODO: SEPARATE LATHE CURVE VIA INTERFACE???
  public GeometryArray getGeometry ()
  {
//    LatheCurve    latheCurve   = new LatheCurve (lathePath);
//    List<Point2d> kurvenPunkte = latheCurve.nennePunkte ();
//    
//    geometrie = createGeometry
//    (
//      kurvenPunkte,
//      scheibenAnzahl,
//      RADS_DEGREE,
//      angleStep.getSizeInDegrees (),
//      latheBase
//    );
//    
//    return geometrie;
    
    
    List<Point2d> kurvenPunkte = new ArrayList<Point2d> ();
    
    for (Position pointOnCurve : lathePath.getPoints ())
    {
      kurvenPunkte.add (pointOnCurve.getAsPoint2d ());
    }
    
    geometry = createGeometry
    (
      kurvenPunkte,
      sliceCount,
      angleStep,
      latheBase,
      backAtStartBehavior
    );
    
    return geometry;
  }
  
  
  
//  protected static double xCoord (LatheBase latheBase, double radius, double angle)
//  {
//    double xCoord = (radius * Math.cos (angle));
//    
//    return xCoord;
//  }
//  
//  protected static double zCoord (LatheBase latheBase, double radius, double angle)
//  {
//    double zCoord = (radius * Math.sin (angle));
//    
//    return zCoord;
//  }
  
  // Entspricht "addCorner()".
  // Eine Ecke eines Quads im QuadArray (Punkt(x;y;z)) wird erzeugt.
  public static Point3d createCorner
  (
    Point2d   originalPoint,
    int       slice,
    int       numSlices,
    Angle     angleIncrease,
    LatheBase latheBase,
    BackAtStartBehavior backAtStartBehavior
  )
  {
    Point3d cornerCoords  = new Point3d ();
    double  xOrigin       = originalPoint.getX ();
    double  yOrigin       = originalPoint.getY ();
    // Equals: double angleInRadians = (radsDegree * (slice * angleIncrease));
    Angle   angle         = angleIncrease.getMultipliedByScalar (slice);
    boolean isBackAtStart = (slice == numSlices);
    
    if (isBackAtStart)
    {
      switch (backAtStartBehavior)
      {
        case WRAP_TO_ORIGIN :
        {
          cornerCoords.setX (xOrigin);
          cornerCoords.setZ (0.0);
          break;
        }
        case ADVANCE :
        {
          cornerCoords.setX (latheBase.getXCoordAt (xOrigin, angle));
          cornerCoords.setZ (latheBase.getZCoordAt (xOrigin, angle));
          break;
        }
        default :
        {
          throw new IllegalStateException ("Unknown BackAtStartBehavior.");
        }
      }
    }
    else
    {
      cornerCoords.setX (latheBase.getXCoordAt (xOrigin, angle));
      cornerCoords.setZ (latheBase.getZCoordAt (xOrigin, angle));
    }
    
    cornerCoords.setY (yOrigin);
    
    return cornerCoords;
  }
  
  public static double[] surfaceRevolve
  (
    List<Point2d> points,
    int           numSlices,
    Angle         angleIncrease,
    LatheBase     latheBase,
    BackAtStartBehavior backAtStartBehavior
  )
  {
    double[] coords      = null;
    int      coordsCount = 0;
    int      pointCount  = 0;
    int      coordIndex  = 0;
    
    pointCount  = points.size ();
    coordsCount = numSlices * (pointCount - 1) * COORDINATES_PER_QUAD;
    coords      = new double[coordsCount];
    
    for (int pointIndex = 0; pointIndex < (pointCount - 1); pointIndex++)
    {
      Point2d currentPoint = points.get (pointIndex);
      Point2d nextPoint    = points.get (pointIndex + 1);
      
      for (int slice = 0; slice < numSlices; slice++)
      {
        Point3d bottomRightCorner = null;
        Point3d topRightCorner    = null;
        Point3d topLeftCorner     = null;
        Point3d bottomLeftCorner  = null;
        
        bottomRightCorner = createCorner (currentPoint, slice,     numSlices, angleIncrease, latheBase, backAtStartBehavior);
        topRightCorner    = createCorner (nextPoint,    slice,     numSlices, angleIncrease, latheBase, backAtStartBehavior);
        topLeftCorner     = createCorner (nextPoint,    slice + 1, numSlices, angleIncrease, latheBase, backAtStartBehavior);
        bottomLeftCorner  = createCorner (currentPoint, slice + 1, numSlices, angleIncrease, latheBase, backAtStartBehavior);
        
        coords[coordIndex +  0] = bottomRightCorner.getX ();
        coords[coordIndex +  1] = bottomRightCorner.getY ();
        coords[coordIndex +  2] = bottomRightCorner.getZ ();
        coords[coordIndex +  3] = topRightCorner.getX    ();
        coords[coordIndex +  4] = topRightCorner.getY    ();
        coords[coordIndex +  5] = topRightCorner.getZ    ();
        coords[coordIndex +  6] = topLeftCorner.getX     ();
        coords[coordIndex +  7] = topLeftCorner.getY     ();
        coords[coordIndex +  8] = topLeftCorner.getZ     ();
        coords[coordIndex +  9] = bottomLeftCorner.getX  ();
        coords[coordIndex + 10] = bottomLeftCorner.getY  ();
        coords[coordIndex + 11] = bottomLeftCorner.getZ  ();
        
        coordIndex = (coordIndex + 12);
      }
    }
    
    return coords;
  }
  
  public static GeometryArray createGeometry
  (
    List<Point2d> punkte,
    int           numSlices,
    Angle         angleIncrease,
    LatheBase     latheBase,
    BackAtStartBehavior backAtStartBehavior
  )
  {
    GeometryArray   geometry        = null;
    double[]        coords          = null;
    GeometryInfo    geometryInfo    = null;
    NormalGenerator normalGenerator = null;
    
    coords       = surfaceRevolve   (punkte, numSlices,
                                     angleIncrease, latheBase,
                                     backAtStartBehavior);
    geometryInfo = new GeometryInfo (GeometryInfo.QUAD_ARRAY);
    geometryInfo.setCoordinates     (coords);
    
    normalGenerator = new NormalGenerator ();
    normalGenerator.generateNormals       (geometryInfo);
    
    geometry = geometryInfo.getGeometryArray ();
    
    return geometry;
  }
  
  
  private static int calculateSliceCount (Angle angleStep)
  {
    int    sliceCount     = 0;
    double angleInDegrees = angleStep.getSizeInDegrees ();
    
//    sliceCount = new Double (360.0 / angleInDegrees).intValue ();
    sliceCount = new Double (Math.ceil (360.0 / angleInDegrees)).intValue ();  // ?Better?
    
    return sliceCount;
  }
}