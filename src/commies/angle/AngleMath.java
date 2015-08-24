package commies.angle;

import safercode.CheckingUtils;


/**
 * <div class="introClass">
 *   The <code>AngleMath</code> class holds a collection of useful
 *   methods for dealing with angle related mathematics.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       AngleMath.java
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
 *       14.03.2015
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
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class AngleMath
{
  /**
   * <div class="introConstructor">
   *   A non-functional constructor.
   * </div>
   */
  private AngleMath ()
  {
  }
  
  
  public static double cos (Angle angle)
  {
    checkAngle (angle);
    
    double cosValue       = 0.0;
    double angleInRadians = 0.0;
    
    angleInRadians = angle.getSizeInRadians ();
    cosValue       = Math.cos (angleInRadians);
    
    return cosValue;
  }
  
  public static double sin (Angle angle)
  {
    checkAngle (angle);
    
    double sinValue       = 0.0;
    double angleInRadians = 0.0;
    
    angleInRadians = angle.getSizeInRadians ();
    sinValue       = Math.sin (angleInRadians);
    
    return sinValue;
  }
  
  public static double tan (Angle angle)
  {
    checkAngle (angle);
    
    double tanValue       = 0.0;
    double angleInRadians = 0.0;
    
    angleInRadians = angle.getSizeInRadians ();
    tanValue       = Math.tan (angleInRadians);
    
    return tanValue;
  }
  
  
  // Return atan2 (y, x) in given angleUnit or degrees (default).
  public static Angle atan2 (double y, double x, AngleUnit angleUnit)
  {
    Angle  angle      = null;
    double atan2Value = 0.0;
    
    atan2Value = Math.atan2 (y, x);
    
//    if (angleUnit != null)
//    {
//      angle = new Angle (angleUnit, AngleUnit.RADIAN, atan2Value);
//    }
//    else
//    {
//      angle = new Angle (AngleUnit.DEGREE, AngleUnit.RADIAN, atan2Value);
//    }
    
    angle = createAngleFromRadians (angleUnit, atan2Value);
    
    return angle;
  }
  
  public static Angle atan (double atanValue, AngleUnit angleUnit)
  {
    Angle  angle          = null;
    double angleInRadians = 0.0;
    
    angleInRadians = Math.atan              (atanValue);
    angle          = createAngleFromRadians (angleUnit, angleInRadians);
    
    return angle;
  }
  
  public static Angle acos (double x, AngleUnit angleUnit)
  {
    Angle  angle     = null;
    double acosValue = 0.0;
    
    acosValue = Math.acos (x);
    
//    if (angleUnit != null)
//    {
//      angle = new Angle (angleUnit, AngleUnit.RADIAN, acosValue);
//    }
//    else
//    {
//      angle = new Angle (AngleUnit.DEGREE, AngleUnit.RADIAN, acosValue);
//    }
    
    angle = createAngleFromRadians (angleUnit, acosValue);
    
    return angle;
  }
  
  public static Angle asin (double x, AngleUnit angleUnit)
  {
    Angle  angle     = null;
    double asinValue = 0.0;
    
    asinValue = Math.asin (x);
    
//    if (angleUnit != null)
//    {
//      angle = new Angle (angleUnit, AngleUnit.RADIAN, asinValue);
//    }
//    else
//    {
//      angle = new Angle (AngleUnit.DEGREE, AngleUnit.RADIAN, asinValue);
//    }
    
    angle = createAngleFromRadians (angleUnit, asinValue);
    
    return angle;
  }
  
  
  
  // 15.03.2015
  // Sums up angles and returns the sum in given unit.
  public static Angle sumOf (AngleUnit unit, Angle... angles)
  {
    Angle resultingAngle = null;
    
    resultingAngle = new Angle (0.0);
    
    if (angles != null)
    {
      for (Angle currentAngle : angles)
      {
        if (currentAngle != null)
        {
          resultingAngle.addAngle (currentAngle);
        }
      }
    }
    
    if (unit != null)
    {
      resultingAngle = resultingAngle.getConvertedAngle (unit);
    }
    
    return resultingAngle;
  }
  
  
  // Returns angle^exponent
  public static Angle power (Angle angle, double exponent)
  {
    checkAngle (angle);
    
    Angle     newAngle = null;
    AngleUnit unit     = null;
    double    size     = 0.0;
    
    unit     = angle.getUnit ();
    size     = Math.pow      (size, exponent);
    newAngle = new Angle     (unit, size);
    
    return newAngle;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static final void checkAngle (Angle angle)
  {
    CheckingUtils.checkForNull (angle, "No angle given.");
  }
  
  private static final Angle createAngleFromRadians
  (
    AngleUnit destinationUnit,
    double    angleSizeInRadians
  )
  {
    Angle angle = null;
    
    if (destinationUnit != null)
    {
      angle = new Angle (destinationUnit,
                         AngleUnit.RADIAN, angleSizeInRadians);
    }
    else
    {
      angle = new Angle (AngleUnit.DEGREE,
                         AngleUnit.RADIAN, angleSizeInRadians);
    }
    
    return angle;
  }
}