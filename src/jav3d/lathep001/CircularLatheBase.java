package jav3d.lathep001;

import ky.angle.Angle;
import ky.angle.AngleMath;

public class CircularLatheBase
implements   LatheBase
{
  public CircularLatheBase ()
  {
  }
  
  
  @Override
  public double getXCoordAt (double radius, Angle angle)
  {
    double xCoord = (radius * AngleMath.cos (angle));
    
    return xCoord;
  }
  
  @Override
  public double getZCoordAt (double radius, Angle angle)
  {
    double zCoord = (radius * AngleMath.sin (angle));
    
    return zCoord;
  }
}