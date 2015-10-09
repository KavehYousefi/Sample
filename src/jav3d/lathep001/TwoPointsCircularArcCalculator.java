// 10.05.2015

package jav3d.lathep001;

import ky.Position;

import java.awt.geom.Arc2D;


public class TwoPointsCircularArcCalculator
{
  public static enum ArcDirection
  {
    /**
     * <div class="introEnum">
     *   The arc extends in counter-clockwise direction.
     * </div>
     */
    COUNTER_CLOCKWISE,
    /**
     * <div class="introEnum">
     *   The arc extends in clockwise direction.
     * </div>
     */
    CLOCKWISE,
    /**
     * <div class="introEnum">
     *   The arc extends in direction of the positive y-axis,
     *   that is, always "up".
     * </div>
     */
    ALWAYS_UP,
    /**
     * <div class="introEnum">
     *   The arc extends in direction of the negative y-axis,
     *   that is, always "down".
     * </div>
     */
    ALWAYS_DOWN;
  }
  
  
  private Position     startPoint;
  private Position     endPoint;
  private ArcDirection direction;
  
  
  /**
   * <div class="introMethod">
   *   Creates a circular arc between two points, following a given
   *   direction.
   * </div>
   * 
   * @param startPoint  The start point.
   * @param endPoint    The end point.
   * @param direction   The arc direction.
   */
  public TwoPointsCircularArcCalculator
  (
    Position     startPoint,
    Position     endPoint,
    ArcDirection direction
  )
  {
    this.startPoint = startPoint;
    this.endPoint   = endPoint;
    this.direction  = direction;
  }
  
  public TwoPointsCircularArcCalculator
  (
    Position startPoint,
    Position endPoint
  )
  {
    this (startPoint, endPoint, ArcDirection.COUNTER_CLOCKWISE);
  }
  
  
  public Arc2D getArc2D (int arc2DType)
  {
    Arc2D    arc2D       = null;
    double   diameter    = 0.0;
    double   radius      = 0.0;
    Position center      = null;
    double   angleExtent = 0.0;
    
    arc2D    = new Arc2D.Double ();
    diameter = startPoint.getEuclideanDistance2DFrom (endPoint);
    radius   = (diameter / 2.0);
    center   = startPoint.getMidpoint (endPoint);
    
    arc2D.setArcByCenter
    (
      center.getX (),
      center.getY (),
      radius,
      0.0,
      0.0,
      Arc2D.OPEN
    );
    arc2D.setAngles (startPoint.getAsPoint2D (),
                     endPoint.getAsPoint2D   ());
    
    angleExtent = arc2D.getAngleExtent ();
    
    switch (direction)
    {
      case COUNTER_CLOCKWISE :
      {
        break;
      }
      case CLOCKWISE :
      {
        angleExtent = -angleExtent;
        break;
      }
      case ALWAYS_DOWN :
      {
        // x1 < x2?
        if (isStartPointLeftFromEndPoint (startPoint, endPoint))
        {
          angleExtent = -angleExtent;
        }
        break;
      }
      case ALWAYS_UP :
      {
        // x1 > x2?
        if (isStartPointRightFromEndPoint (startPoint, endPoint))
        {
          angleExtent = -angleExtent;
        }
        break;
      }
    }
    
    arc2D.setAngleExtent (angleExtent);
    
    return arc2D;
  }
  
  
  // startX < endX?
  private static boolean isStartPointLeftFromEndPoint
  (
    Position startPoint,
    Position endPoint
  )
  {
    boolean isStartLeft = false;
    double  startX      = 0.0;
    double  endX        = 0.0;
    
    startX      = startPoint.getX ();
    endX        = endPoint.getX   ();
    isStartLeft = (startX < endX);
    
    return isStartLeft;
  }
  
  // startX > endX?
  private static boolean isStartPointRightFromEndPoint
  (
    Position startPoint,
    Position endPoint
  )
  {
    boolean isStartLeft = false;
    double  startX      = 0.0;
    double  endX        = 0.0;
    
    startX      = startPoint.getX ();
    endX        = endPoint.getX   ();
    isStartLeft = (startX > endX);
    
    return isStartLeft;
  }
}