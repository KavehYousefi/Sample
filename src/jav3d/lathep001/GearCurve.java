package jav3d.lathep001;

import ky.Position;


// 09.03.2015
// -> "http://mathworld.wolfram.com/GearCurve.html"
// -> "http://mathematica.stackexchange.com/questions/8482/problem-with-parametricplot?rq=1"
// 
// a: Radius of gear (not really the radius, as it can be zero, but
//                    a valid gear curve exists, for example with
//                    a = 0.0, b = 1.0, c = 6.0).
// b: Length of teeth. The smaller b, the longer the teeth.
//    More precise: The valley-to-teeth ratio. The smaller b,
//                  the longer the teeth, while the valleys fall
//                  towards the gear center.
// n: Number of teeth. Should be a natural number (positive integer >= 0).
// t: in [0; 2*PI]
public class GearCurve
{
  private double a;
  private double b;
  private double toothCount;  // Known as parameter n.
  
  
  public GearCurve (double a, double b, double toothCount)
  {
    initGearCurve (a, b, toothCount);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a gear curve with default properties.
   * </div>
   */
  public GearCurve ()
  {
    initGearCurve (1.0, 10.0, 12.0);
  }
  
  
  public Position getPoint (double t)
  {
    Position point = null;
    
    point = calculatePoint (a, b, toothCount, t);
    
    return point;
  }
  
  
  public double getR (double t)
  {
    double r = 0.0;
    
    r = calculateR (a, b, toothCount, t);
    
    return r;
  }
  
  
  
  
  // Calculates the "r" parameter.
  public static double calculateR
  (
    double a,
    double b,
    double n,
    double t
  )
  {
    double r = 0.0;
    
    r = a + (1.0 / b) * Math.tanh (b * Math.sin (n * t));
    
    return r;
  }
  
  public static Position calculatePoint
  (
    double a,
    double b,
    double n,
    double t
  )
  {
    Position point = null;
    double   x     = 0.0;
    double   y     = 0.0;
    double   r     = 0.0;
    
    r     = calculateR   (a, b, n, t);
    x     = r * Math.cos (t);
    y     = r * Math.sin (t);
    point = new Position (x, y);
    
    return point;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initGearCurve (double a, double b, double toothCount)
  {
    this.a          = a;
    this.b          = b;
    this.toothCount = toothCount;
  }
}