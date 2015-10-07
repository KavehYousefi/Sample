// 29.09.2013

package ky.angle;

import safercode.CheckingUtils;
import ky.angle.Angle;
import ky.math.IntervallWerkz;


public enum Quadrant
{
  QUADRANT_I
  {
    @Override
    public boolean containsAngle (Angle angle)
    {
      CheckingUtils.checkForNull (angle, "Angle is null.");
      
      boolean containsAngle     = false;
      Angle   normalizedAngle   = null;
      double  normalizedDegrees = 0.0;
      
      containsAngle     = false;
      normalizedAngle   = angle.getNormalizedAngle (false);
      normalizedDegrees = normalizedAngle.getSizeInDegrees ();
      containsAngle     = IntervallWerkz.isInRange
      (
        0.0, true, 90.0, true, normalizedDegrees
      );
      
      return containsAngle;
    }
  },
  
  QUADRANT_II
  {
    @Override
    public boolean containsAngle (Angle angle)
    {
      CheckingUtils.checkForNull (angle, "Angle is null.");
      
      boolean containsAngle     = false;
      Angle   normalizedAngle   = null;
      double  normalizedDegrees = 0.0;
      
      containsAngle     = false;
      normalizedAngle   = angle.getNormalizedAngle (false);
      normalizedDegrees = normalizedAngle.getSizeInDegrees ();
      containsAngle     = IntervallWerkz.isInRange
      (
        90.0, false, 180.0, true, normalizedDegrees
      );
      
      return containsAngle;
    }
  },
  
  QUADRANT_III
  {
    @Override
    public boolean containsAngle (Angle angle)
    {
      CheckingUtils.checkForNull (angle, "Angle is null.");
      
      boolean containsAngle     = false;
      Angle   normalizedAngle   = null;
      double  normalizedDegrees = 0.0;
      
      containsAngle     = false;
      normalizedAngle   = angle.getNormalizedAngle (false);
      normalizedDegrees = normalizedAngle.getSizeInDegrees ();
      containsAngle     = IntervallWerkz.isInRange
      (
        180.0, false, 270.0, true, normalizedDegrees
      );
      
      return containsAngle;
    }
  },
  
  QUADRANT_IV
  {
    @Override
    public boolean containsAngle (Angle angle)
    {
      CheckingUtils.checkForNull (angle, "Angle is null.");
      
      boolean containsAngle     = false;
      Angle   normalizedAngle   = null;
      double  normalizedDegrees = 0.0;
      
      containsAngle     = false;
      normalizedAngle   = angle.getNormalizedAngle (false);
      normalizedDegrees = normalizedAngle.getSizeInDegrees ();
      containsAngle     = IntervallWerkz.isInRange
      (
        270.0, false, 360.0, false, normalizedDegrees
      );
      
      return containsAngle;
    }
  };
  
  
  private Quadrant ()
  {
  }
  
  
  abstract public boolean containsAngle (Angle angle);
}