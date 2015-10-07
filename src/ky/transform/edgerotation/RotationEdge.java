package ky.transform.edgerotation;

import ky.Position;
import ky.angle.Angle;
import safercode.CheckingUtils;
import ky.LineSegment3D;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Vector3d;


/**
 * <div class="introClass">
 *   A rotation edge represents a line segment, around which to rotate.
 * </div>
 * 
 * <div>
 *   You can regard a rotation edge as an extension of an
 *   <code>AxisAngle4d</code> by combining it with a translation to
 *   the line segment's center point.
 * </div>
 * 
 * <div>
 *   Do not forget that the order of end points is important, usually
 *   reversing the rotation if swapped.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       RotationEdge.java
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
 *       15.03.2015
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
 *     <td>15.03.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class RotationEdge
{
  private LineSegment3D edge;
  private Angle         angle;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation edge from a given line segment and an angle
   *   to rotate around.
   * </div>
   * 
   * @param edge   The non-<code>null</code> edge to rotate about.
   * @param angle  The non-<code>null</code> angle to rotate around.
   * 
   * @throws NullPointerException  If one of the arguments is
   *                               <code>null</code>.
   */
  public RotationEdge (LineSegment3D edge, Angle angle)
  {
    CheckingUtils.checkForNull (edge,  "Edge is null.");
    CheckingUtils.checkForNull (angle, "Angle is null.");
    
    this.edge  = edge;
    this.angle = angle;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation edge between the given points to rotate
   *   around the given angle.
   * </div>
   * 
   * @param startPoint  The non-<code>null</code> edge start point.
   * @param endPoint    The non-<code>null</code> edge end point.
   * @param angle       The non-<code>null</code> angle to
   *                    rotate around.
   * 
   * @throws NullPointerException  If one of the arguments is
   *                               <code>null</code>.
   */
  public RotationEdge
  (
    Position startPoint,
    Position endPoint,
    Angle    angle
  )
  {
    initRotationEdge (startPoint, endPoint, angle);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation edge between the given points to rotate
   *   around, and a rotation angle of zero degrees.
   * </div>
   * 
   * @param startPoint  The first  non-<code>null</code> start point.
   * @param endPoint    The second non-<code>null</code> end point.
   * 
   * @throws NullPointerException  If one of the arguments is
   *                               <code>null</code>.
   */
  public RotationEdge (Position endpoint1, Position endpoint2)
  {
    initRotationEdge (endpoint1, endpoint2, new Angle ());
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation edge from the origin
   *   P<sub>1</sub>(0.0, 0.0, 0.0) to P<sub>2</sub>(0.0, 1.0, 0.0)
   *   with a rotation angle of zero degrees.
   * </div>
   */
  public RotationEdge ()
  {
    initRotationEdge
    (
      new Position (0.0, 0.0, 0.0),
      new Position (0.0, 1.0, 0.0),
      new Angle    ()
    );
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the line segments representing the edge.
   * </div>
   * 
   * @return  The edge as line segment.
   */
  public LineSegment3D getEdge ()
  {
    return edge;
  }
  
  public Position getStartPoint ()
  {
    return edge.getStartPoint ();
  }
  
  public void setStartPoint (Position startPoint)
  {
    this.edge.setStartPoint (startPoint);
  }
  
  public Position getEndPoint ()
  {
    return edge.getEndPoint ();
  }
  
  public void setEndPoint (Position endPoint)
  {
    this.edge.setEndPoint (endPoint);
  }
  
  public Angle getAngle ()
  {
    return angle;
  }
  
  public void setAngle (Angle angle)
  {
    this.angle = angle;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns an <code>AxisAngle4</code> object representing the
   *   rotation.
   * </div>
   * 
   * @return  The rotation as <code>AxisAngle4d</code> object.
   */
  public AxisAngle4d getAsAxisAngle4d ()
  {
    // Uses the edgeVector as vector to rotate around.
    AxisAngle4d axisAngle      = null;
    Vector3d    edgeVector     = null;
    double      angleInRadians = 0.0;
    
    edgeVector     = edge.getVectorFromStartToEnd ();
    angleInRadians = angle.getSizeInRadians       ();
    axisAngle      = new AxisAngle4d (edgeVector, angleInRadians);
    
    return axisAngle;
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
      "RotationEdge(startPoint=%s, endPoint=%s, angle=%s)",
      edge.getStartPoint (),
      edge.getEndPoint   (),
      angle
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initRotationEdge
  (
    Position startPoint,
    Position endPoint,
    Angle    angle
  )
  {
    CheckingUtils.checkForNull (startPoint, "Start point is null.");
    CheckingUtils.checkForNull (endPoint,   "End point is null.");
    CheckingUtils.checkForNull (angle,      "Angle is null.");
    
    this.edge  = new LineSegment3D (startPoint, endPoint);
    this.angle = angle;
  }
}
