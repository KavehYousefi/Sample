// 24.05.2015
// -> "http://mathworld.wolfram.com/GearCurve.html"

package jav3d.lathep001;

import ky.angle.Angle;
import ky.angle.AngleMath;


public class GearLatheBase
implements   LatheBase
{
  private GearCurve gearCurve;
  
  
  public GearLatheBase (GearCurve gearCurve)
  {
    this.gearCurve = gearCurve;
  }
  
  public GearLatheBase ()
  {
    this.gearCurve = new GearCurve (1.0, 10.0, 6.0);
  }
  
  
  @Override
  public double getXCoordAt (double radius, Angle angle)
  {
    double xCoord = 0.0;
    double r      = 0.0;
    
    r      = gearCurve.getR (angle.getSizeInRadians ());
    xCoord = radius * r * AngleMath.cos (angle);
    
    return xCoord;
  }
  
  @Override
  public double getZCoordAt (double radius, Angle angle)
  {
    double zCoord = 0.0;
    double r      = 0.0;
    
    r      = gearCurve.getR (angle.getSizeInRadians ());
    zCoord = radius * r * AngleMath.sin (angle);
    
    return zCoord;
  }
}