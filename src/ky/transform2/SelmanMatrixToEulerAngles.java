// 08.08.2015
// 
// Original (first) implementation.


package ky.transform2;

import ky.angle.EulerAngles;
import ky.angle.Angle;
import ky.angle.AngleUnit;
import safercode.CheckingUtils;

import javax.vecmath.Matrix3d;


public class SelmanMatrixToEulerAngles
extends      AbstractMatrixToEulerAngles
{
  public static final double ZERO_THRESHOLD = 0.00001;
  
  
  public SelmanMatrixToEulerAngles (AngleUnit angleUnit)
  {
    super (angleUnit);
  }
  
  public SelmanMatrixToEulerAngles ()
  {
    super ();
  }
  
  
  @Override
  public EulerAngles getEulerAngles (Matrix3d matrix)
  {
    EulerAngles eulerAngles = null;
    
    eulerAngles = getOrientationFromMatrix3d (matrix, angleUnit);
    
    return eulerAngles;
  }
  
  
  
  
  //*** ????????!!! NOT WORKING PROPERLY !!!???????????????????????????
  /**
   * <div class="introMethod">
   *   Extracts the rotation data as Euler angles from a
   *   <code>Matrix3d</code> object.
   * </div>
   * 
   * <div>
   *   The algorithm was found in following sources:
   *   <ul>
   *     <li>
   *       <a href="http://www.mail-archive.com/java3d-interest@sun.com/msg01714.html">
   *         http://www.mail-archive.com/java3d-interest@sun.com/msg01714.html
   *       </a>
   *       <br />
   *       Original source of the code.
   *       <br />
   *       Date: 08.08.2015
   *     </li>
   *     <li>
   *       <a href="http://www.mail-archive.com/java3d-interest@sun.com/msg01755.html">
   *         http://www.mail-archive.com/java3d-interest@sun.com/msg01755.html
   *       </a>
   *       <br />
   *       Example for usage of the above algorithm.
   *       <br />
   *       Date: 03.03.2013
   *     </li>
   *     <li>
   *       <a href="http://www.mail-archive.com/java3d-interest@sun.com/msg01714.html">
   *         http://www.mail-archive.com/java3d-interest@sun.com/msg01714.html
   *       </a>
   *       <br />
   *       Date: 03.03.2013
   *     </li>
   *     <li>
   *       <a href="http://fivedots.coe.psu.ac.th/~ad/jg/ch165/augReality.pdf">
   *         http://fivedots.coe.psu.ac.th/~ad/jg/ch165/augReality.pdf
   *       </a>, page 25
   *       <br />
   *       Date: 20.04.2015
   *     </li>
   *   </ul>
   * </div>
   * 
   * @param matrix     The non-<code>null</code> rotation matrix.
   * @param angleUnit  The angle unit to express the angles in.
   *                   It must not be <code>null</code>.
   * 
   * @return           The three Euler angles as orientation.
   * 
   * @throws NullPointerException  If <code>matrix</code> or
   *                               <code>angleUnit</code> equals
   *                               <code>null</code>.
   */
  public static EulerAngles getOrientationFromMatrix3d
  (
    Matrix3d  matrix,
    AngleUnit angleUnit
  )
  {
    CheckingUtils.checkForNull (matrix,    "Matrix is null.");
    CheckingUtils.checkForNull (angleUnit, "Angle unit is null.");
    
    EulerAngles orientation     = new EulerAngles ();
    Angle       angleX          = null;
    Angle       angleY          = null;
    Angle       angleZ          = null;
    // Equals "m_Rotation.x", "m_Rotation.y" und "m_Rotation.z".
    double      angleXInRadians = 0.0;
    double      angleYInRadians = 0.0;
    double      angleZInRadians = 0.0;
    // Equals "c", the cosine of "rotationY".
    double      cosAngleY       = 0.0;
    // The interesting elements from the "matrix".
    double      matrix_2_0      = 0.0;
    double      matrix_2_2      = 0.0;
    double      matrix_2_1      = 0.0;
    double      matrix_0_0      = 0.0;
    double      matrix_1_0      = 0.0;
    double      matrix_1_1      = 0.0;
    double      matrix_0_1      = 0.0;
    double      arcTangentX     = 0.0;   // tRx (tempx)
    double      arcTangentY     = 0.0;   // tRy (tempy)
    
    
    matrix_2_0      = matrix.getElement (2, 0);
    /* According to "https://www.java.net/node/654962":
     *   rotationY =  (Math.asin (-matrix.getElement (2, 0)));
     *               ^            ^
     *               |____________|  Minus sign put here.
     */
    angleYInRadians = -(Math.asin (matrix_2_0));
    cosAngleY       =   Math.cos  (angleYInRadians);
    
    if (Math.abs (angleYInRadians) > ZERO_THRESHOLD)
    {
      matrix_2_2 = matrix.getElement (2, 2);
      matrix_2_1 = matrix.getElement (2, 1);
      matrix_0_0 = matrix.getElement (0, 0);
      matrix_1_0 = matrix.getElement (1, 0);
      
      arcTangentX     = (  matrix_2_2  / cosAngleY);
      arcTangentY     = (-(matrix_2_1) / cosAngleY);
      angleXInRadians = Math.atan2 (arcTangentY, arcTangentX);
      
      arcTangentX     = (  matrix_0_0  / cosAngleY);
      arcTangentY     = (-(matrix_1_0) / cosAngleY);
      angleZInRadians = Math.atan2 (arcTangentY, arcTangentX);
    }
    else
    {
      matrix_1_1      = matrix.getElement (1, 1);
      matrix_0_1      = matrix.getElement (0, 1);
      
      angleXInRadians = 0.0;
      arcTangentX     = matrix_1_1;
      arcTangentY     = matrix_0_1;
      angleZInRadians = Math.atan2 (arcTangentY, arcTangentX);
    }
    
    angleXInRadians = -(angleXInRadians);
    angleZInRadians = -(angleZInRadians);
    
    /* Ensure that all angles are positive by shifting them by
     * 180 degrees, if necessary.
     */
    angleXInRadians = getPositiveAngleInRadiansIfNecessary (angleXInRadians);
    angleYInRadians = getPositiveAngleInRadiansIfNecessary (angleYInRadians);
    angleZInRadians = getPositiveAngleInRadiansIfNecessary (angleZInRadians);
    
    angleX = Angle.createByConvertingFromSourceToDestinationUnit (angleXInRadians, AngleUnit.RADIAN, angleUnit);
    angleY = Angle.createByConvertingFromSourceToDestinationUnit (angleYInRadians, AngleUnit.RADIAN, angleUnit);
    angleZ = Angle.createByConvertingFromSourceToDestinationUnit (angleZInRadians, AngleUnit.RADIAN, angleUnit);
    
    orientation.setAngleX (angleX);
    orientation.setAngleY (angleY);
    orientation.setAngleZ (angleZ);
    
    return orientation;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static double getPositiveAngleInRadiansIfNecessary
  (
    double angleInRadians
  )
  {
    double positiveAngle = 0.0;
    
    if (angleInRadians < 0.0)
    {
      positiveAngle = (angleInRadians + TWO_PI);
    }
    else
    {
      positiveAngle = angleInRadians;
    }
    
    return positiveAngle;
  }
}
