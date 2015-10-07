package ky;

import ky.Position;
import safercode.CheckingUtils;

import java.awt.geom.Line2D;

import javax.media.j3d.Geometry;
import javax.media.j3d.LineArray;
import javax.vecmath.Vector3d;


/**
 * <div class="introClass">
 *   Implements a three-dimensional line segment, defined by its
 *   two end points.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       LineSegment3D.java
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Author:
 *     </td>
 *     <td>
 *       Kaveh Yousefi
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Date:
 *     </td>
 *     <td>
 *       22.03.2015
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Version:
 *     </td>
 *     <td>
 *       1.0
 *     </td>
 *   </tr>
 * </table>
 * 
 * <div class="classHistoryTitle">History:</div>
 * <table class="classHistoryTable">
 *   <tr>
 *     <th>Date</th>
 *     <th>Version</th>
 *     <th>Changes</th>
 *   </tr>
 *   <tr>
 *     <td>22.03.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class LineSegment3D
{
  public static final double FRACTION_FOR_START_POINT = 0.0;
  public static final double FRACTION_FOR_MID_POINT   = 0.5;
  public static final double FRACTION_FOR_END_POINT   = 1.0;
  
  private Position startPoint;
  private Position endPoint;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a line segment between two points.
   * </div>
   * 
   * @param startPoint  The starting point.
   * @param endPoint    The ending   point.
   */
  public LineSegment3D (Position startPoint, Position endPoint)
  {
    this.startPoint = startPoint;
    this.endPoint   = endPoint;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a line segment starting at a given point and ending at
   *   the given distances from it.
   * </div>
   * 
   * @param startPoint  The starting point.
   * @param endPoint    The distances of the end point from the start.
   */
  public LineSegment3D (Position startPoint, Vector3d distances)
  {
    checkStartPoint (startPoint);
    checkEndPoint   (endPoint);
    
    this.startPoint = startPoint;
    this.endPoint   = startPoint.getAddedPosition (distances);
  }
  
  public LineSegment3D
  (
    Position startPoint,
    double   distanceX,
    double   distanceY,
    double   distanceZ
  )
  {
    checkStartPoint (startPoint);
    
    this.startPoint = startPoint;
    this.endPoint   = startPoint.getAddedPosition (distanceX, distanceY, distanceZ);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a line segment with start and end point in the origin.
   * </div>
   */
  public LineSegment3D ()
  {
    this.startPoint = new Position ();
    this.endPoint   = new Position ();
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the line's start point.
   * </div>
   * 
   * @return  The line start point.
   */
  public Position getStartPoint ()
  {
    return startPoint;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the start point.
   * </div>
   * 
   * @param startPoint  The start point.
   */
  public void setStartPoint (Position startPoint)
  {
    checkStartPoint (startPoint);
    this.startPoint = new Position (startPoint);
  }
  
  /**
   * <div class="introMethod">
   *   Returns the line's end point.
   * </div>
   * 
   * @return  The line end point.
   */
  public Position getEndPoint ()
  {
    return endPoint;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the end point.
   * </div>
   * 
   * @param endPoint  The end point.
   */
  public void setEndPoint (Position endPoint)
  {
    checkEndPoint (endPoint);
    this.endPoint = new Position (endPoint);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the end point by a vector relative to the start point.
   * </div>
   * 
   * @param distances  The vector from the start to the end point.
   *                   It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If <code>distances</code> is
   *                               <code>null</code>.
   */
  public void setEndPointByDistances (Vector3d distances)
  {
    this.endPoint = startPoint.getAddedPosition (distances);
  }
  
  // Implements the equation: L = P + v * t.
  //   v := distances
  //   t := fraction  (usually in [0, 1]).
  public void setEndPointByDistances
  (
    Vector3d distances,
    double   fraction
  )
  {
    Vector3d scaledDistances = null;
    
    scaledDistances = new Vector3d (distances);
    scaledDistances.scale          (fraction);
    this.endPoint = startPoint.getAddedPosition (scaledDistances);
  }
  
  /**
   * <div class="introMethod">
   *   Swaps the start and end point.
   * </div>
   */
  public void swapStartAndEndPoint ()
  {
    Position formerStartPoint = null;
    
    formerStartPoint = startPoint;
    startPoint       = endPoint;
    endPoint         = formerStartPoint;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a vector from the start point to the end point.
   * </div>
   * 
   * @return  A vector from the start to the end point.
   */
  public Vector3d getVectorFromStartToEnd ()
  {
    return startPoint.getVector3dFromThisToOtherPoint (endPoint);
  }
  
  /**
   * <div class="introMethod">
   *   Returns a vector from the end point to the start point.
   * </div>
   * 
   * @return  A vector from the end to the start point.
   */
  public Vector3d getVectorFromEndToStart ()
  {
    return endPoint.getVector3dFromThisToOtherPoint (startPoint);
  }
  
  /**
   * <div class="introMethod">
   *   Returns the line's length.
   * </div>
   * 
   * @return  The line length.
   */
  public double getLength ()
  {
    double length = 0.0;
    
    length = startPoint.getEuclideanDistance3DFrom (endPoint);
    
    return length;
  }
  
  public void setLength (double length)
  {
    Vector3d distancesVector = null;
    
    distancesVector = new Vector3d (getVectorFromEndToStart ());
    distancesVector.scale          (length);
    setEndPointByDistances         (distancesVector);
  }
  
  /**
   * <div class="introMethod">
   *   Returns the point on the line at the given fraction.
   * </div>
   * 
   * @param fraction  The fraction of the point on the line.
   * 
   * @return          The point at the given <code>fraction</code> on
   *                  the line.
   */
  public Position getPointAtFraction (double fraction)
  {
    Position pointAtFraction = null;
    
    pointAtFraction = startPoint.getPositionOnLine
    (
      endPoint,
      fraction
    );
    
    return pointAtFraction;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the midpoint between the line's start and end points.
   * </div>
   * 
   * @return  The midpoint on the line.
   */
  public Position getMidpoint ()
  {
    return startPoint.getMidpoint (endPoint);
  }
  
  public void setPositionFromFraction (double fraction)
  {
    Position pointAtFraction = null;
    
    pointAtFraction = getPointAtFraction (fraction);
    this.startPoint = this.startPoint.getSubtractedPosition (pointAtFraction);
    this.endPoint   = this.endPoint.getSubtractedPosition   (pointAtFraction);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  public static LineSegment3D createFromStartAndEndPoint
  (
    Position startPoint,
    Position endPoint
  )
  {
    LineSegment3D lineSegment = null;
    
    lineSegment = new LineSegment3D (startPoint, endPoint);
    
    return lineSegment;
  }
  
  public static LineSegment3D createFromStartPointAndVectorToEndPoint
  (
    Position startPoint,
    Vector3d vectorToEndPoint
  )
  {
    LineSegment3D lineSegment = null;
    
    lineSegment = new LineSegment3D (startPoint, vectorToEndPoint);
    
    return lineSegment;
  }
  
  public static LineSegment3D createFromStartPointAndDistancesToEndPoint
  (
    Position startPoint,
    double   distanceXToEndPoint,
    double   distanceYToEndPoint,
    double   distanceZToEndPoint
  )
  {
    LineSegment3D lineSegment = null;
    
    lineSegment = new LineSegment3D
    (
      startPoint,
      distanceXToEndPoint,
      distanceYToEndPoint,
      distanceZToEndPoint
    );
    
    return lineSegment;
  }
  
  public static LineSegment3D createFromVectorRelativeToOrigin
  (
    Vector3d vectorFromOrigin
  )
  {
    return LineSegment3D.createFromStartPointAndVectorToEndPoint
    (
      new Position (),
      vectorFromOrigin
    );
  }
  
  public static LineSegment3D createFromCoordinatesRelativeToOrigin
  (
    double offsetX,
    double offsetY,
    double offsetZ
  )
  {
    return LineSegment3D.createFromStartPointAndDistancesToEndPoint
    (
      new Position (),
      offsetX,
      offsetY,
      offsetZ
    );
  }
  
  /**
   * <div class="introMethod">
   *   Creates a 3D line segment based on a given <code>Line2D</code>
   *   shape.
   * </div>
   * 
   * @param line2D  The <code>Line2D</code> object to base the 3D
   *                line segment on.
   *                The <code>null</code> value in not permitted.
   * 
   * @return  A new <code>LineSegment3D</code> based on the given
   *          <code>Line2D</code> instance.
   * 
   * @throws NullPointerException  If the <code>line2D</code> object
   *                               equals <code>null</code>.
   */
  public static LineSegment3D createFromLine2D (Line2D line2D)
  {
    CheckingUtils.checkForNull (line2D, "Line2D is null.");
    
    LineSegment3D lineSegment = null;
    Position      startPoint  = null;
    Position      endPoint    = null;
    
    startPoint  = Position.createFromPoint2D (line2D.getP1 ());
    endPoint    = Position.createFromPoint2D (line2D.getP2 ());
    lineSegment = LineSegment3D.createFromStartAndEndPoint
    (
      startPoint,
      endPoint
    );
    
    return lineSegment;
  }
  
  public static LineSegment3D createZeroLineSegment3D ()
  {
    return new LineSegment3D ();
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "Solid".                    -- //
  //////////////////////////////////////////////////////////////////////
  
  public Geometry createGeometry ()
  {
    LineArray lineArray    = null;
    int       vertexCount  = 0;
    int       vertexFormat = 0;
    
    vertexCount  = 2;
    vertexFormat = LineArray.COORDINATES;
    lineArray    = new LineArray (vertexCount, vertexFormat);
    
    lineArray.setCoordinate (0, startPoint.getAsPoint3d ());
    lineArray.setCoordinate (1, endPoint.getAsPoint3d   ());
    
    return lineArray;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = null;
    
    asString = String.format
    (
      "LineSegment3D(start=%s, end=%s)",
      startPoint, endPoint
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static void checkStartPoint (Position startPoint)
  {
    CheckingUtils.checkForNull (startPoint, "Start point is null.");
  }
  
  private static void checkEndPoint (Position endPoint)
  {
    CheckingUtils.checkForNull (endPoint, "End point is null.");
  }
}
