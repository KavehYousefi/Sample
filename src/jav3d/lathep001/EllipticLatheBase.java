package jav3d.lathep001;

import ky.angle.Angle;
import ky.angle.AngleMath;

/* xRadius sollte 1.0 sein, da ansonsten Bruch in Koerper auftritt.
 * 
 */
public class EllipticLatheBase
implements   LatheBase
{
  private double xRadius;
  private double zRadius;
  
  
  public EllipticLatheBase (double xRadius, double zRadius)
  {
    this.xRadius = xRadius;
    this.zRadius = zRadius;
  }
  
  
  @Override
  public double getXCoordAt (double radius, Angle angle)
  {
    double xCoord = (radius * AngleMath.cos (angle) * xRadius);
    
    return xCoord;
  }
  
  @Override
  public double getZCoordAt (double radius, Angle angle)
  {
    double zCoord = (radius * AngleMath.sin (angle) * zRadius);
    
    return zCoord;
  }
}