// 08.08.2015

package ky.transform2;

import ky.angle.EulerAngles;
import ky.angle.AngleUnit;

import javax.vecmath.Matrix3d;


abstract public class AbstractMatrixToEulerAngles
{
  protected static final double TWO_PI = (2.0 * Math.PI);
  
  
  protected AngleUnit angleUnit;
  
  
  public AbstractMatrixToEulerAngles (AngleUnit angleUnit)
  {
    this.angleUnit = angleUnit;
  }
  
  public AbstractMatrixToEulerAngles ()
  {
    this.angleUnit = AngleUnit.DEGREE;
  }
  
  
  public AngleUnit getAngleUnit ()
  {
    return angleUnit;
  }
  
  public void setAngleUnit (AngleUnit angleUnit)
  {
    this.angleUnit = angleUnit;
  }
  
  
  abstract public EulerAngles getEulerAngles (Matrix3d matrix);
}
