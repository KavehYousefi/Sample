// 11.05.2015

package ky;

import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point3d;

import safercode.CheckingUtils;


public class PositionUtils
{
  private PositionUtils ()
  {
  }
  
  
  public static Position getTransformedPoint
  (
    Position    point,
    Transform3D transform3D
  )
  {
    Position transformedPoint   = null;
    Point3d  transformedPoint3d = null;
    Point3d  originalPoint3d    = null;
    
    originalPoint3d    = point.getAsPoint3d ();
    transformedPoint3d = new Point3d ();
    transform3D.transform (originalPoint3d, transformedPoint3d);
    transformedPoint   = new Position (transformedPoint3d);
    
    return transformedPoint;
  }
  
  public static List<Position> getTransformedPoints
  (
    List<Position> pointList,
    Transform3D    transform3D
  )
  {
    List<Position> transformedPoints = new ArrayList<Position> ();
    
    for (Position point : pointList)
    {
      Position transformedPoint = null;
      
      transformedPoint = getTransformedPoint (point, transform3D);
      transformedPoints.add (transformedPoint);
    }
    
    return transformedPoints;
  }
  
  public static List<Point3d> convertPositionListToPoint3dList
  (
    List<Position> pointList
  )
  {
    CheckingUtils.checkForNull (pointList, "The pointList is null.");
    
    List<Point3d> point3dList = new ArrayList<Point3d> ();
    
    for (Position point : pointList)
    {
      Point3d positionAsPoint3d = null;
      
      if (point != null)
      {
        positionAsPoint3d = point.getAsPoint3d ();
      }
      else
      {
        positionAsPoint3d = null;
      }
      
      point3dList.add (positionAsPoint3d);
    }
    
    return point3dList;
  }
  
  /**
   * <div class="introMethod">
   *   Creates an array of <code>Point3d</code> objects from a list of
   *   <code>Position</code>s.
   * </div>
   * 
   * <div>
   *   While the input <code>pointList</code> must not be
   *   <code>null</code>, its elements are allowed to equal
   *   <code>null</code>, thus the resulting <code>Point3d[]</code>
   *   array may contain <code>null</code> values.
   * </div>
   * 
   * @param pointList  The positions to convert to a
   *                   <code>Point3d[]</code> array.
   * 
   * @return           An array of <code>Point3d</code>s from  the given
   *                   <code>pointList</code>.
   * 
   * @throws NullPointerException  If <code>pointList</code> equals
   *                               <code>null</code>.
   */
  public static Point3d[] convertPositionListToPoint3dArray
  (
    List<Position> pointList
  )
  {
    Point3d[]     point3dArray = new Point3d[0];
    List<Point3d> point3dList  = null;
    
    point3dList  = convertPositionListToPoint3dList (pointList);
    point3dArray = point3dList.toArray (point3dArray);
    
    return point3dArray;
  }
  
  public static double[] convertPositionListToDoubleArray (List<Position> pointList)
  {
    double[] coordinatesArray = null;
    int      pointCount       = 0;
    int      coordinateOffset = 0;
    
    pointCount       = pointList.size ();
    coordinatesArray = new double[pointCount * 3];
    coordinateOffset = 0;
    
    for (Position point : pointList)
    {
      coordinatesArray[coordinateOffset + 0] = point.getX ();
      coordinatesArray[coordinateOffset + 1] = point.getY ();
      coordinatesArray[coordinateOffset + 2] = point.getZ ();
      coordinateOffset                       = coordinateOffset + 3;
    }
    
    return coordinatesArray;
  }
}