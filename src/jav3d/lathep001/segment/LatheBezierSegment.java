package jav3d.lathep001.segment;

import ky.Position;
import jav3d.lathep001.ParameterSchleife;
import jav3d.lathep001.curves.BezierCurve;

import java.util.List;


// 30.03.2015
public class LatheBezierSegment
implements   LathePathSegment
{
  private Position firstControlPoint;
  private Position secondControlPoint;
  private Position endPoint;
  private double   stepSize;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a path segment based on a Bézier curve in the
   *   three-dimensional space.
   * </div>
   * 
   * @param firstControlPoint   The first control point.
   *                            The <code>null</code> value is invalid.
   * @param secondControlPoint  The second control point.
   *                            The <code>null</code> value is invalid.
   * @param endPoint            The end point.
   *                            The <code>null</code> value is invalid.
   * @param stepSize            The distance the calculated points on
   *                            the curve.
   *                            It must be greater than zero.
   * 
   * @throws NullPointerException  If the <code>firstControlPoint</code>
   *                               equals <code>null</code>.
   * @throws NullPointerException  If the
   *                               <code>secondControlPoint</code>
   *                               equals <code>null</code>.
   * @throws NullPointerException  If the <code>endPoint</code>
   *                               equals <code>null</code>.
   * @throws IllegalArgumentException  If the <code>stepSize</code>
   *                                   is less than or equal to zero.
   */
  public LatheBezierSegment
  (
    Position firstControlPoint,
    Position secondControlPoint,
    Position endPoint,
    double   stepSize
  )
  {
    this.firstControlPoint  = firstControlPoint;
    this.secondControlPoint = secondControlPoint;
    this.endPoint           = endPoint;
    this.stepSize           = stepSize;
  }
  
  
  @Override
  public List<Position> getPointsOnSegment (List<Position> previousPoints)
  {
    List<Position>    points        = null;
    BezierCurve       bezierCurve   = null;
    ParameterSchleife parameterLoop = null;
    Position          lastPoint     = null;
    
    lastPoint     = previousPoints.get    (previousPoints.size () - 1);
    bezierCurve   = new BezierCurve       ();
    parameterLoop = new ParameterSchleife (0.0, 1.0, stepSize);
    
    bezierCurve.setzeStartPunkt           (lastPoint);
    bezierCurve.setzeErstenKontrollPunkt  (firstControlPoint);
    bezierCurve.setzeZweitenKontrollPunkt (secondControlPoint);
    bezierCurve.setzeEndPunkt             (endPoint);
    bezierCurve.setzeParameterSchleife    (parameterLoop);
    
    points = bezierCurve.nennePunkte ();
    
    // Remove first point, as it is the already existing "lastPoint".
    if (! points.isEmpty ())
    {
      points.remove (0);
    }
    
    return points;
  }
  
  @Override
  public List<Position> processPoints (List<Position> previousPoints)
  {
    List<Position> newPoints = getPointsOnSegment (previousPoints);
    
    previousPoints.addAll (newPoints);
    
    return previousPoints;
  }
}