package ky.angle;

import ky.angle.Angle;
import ky.angle.AngleUnit;
import safercode.CheckingUtils;

import javax.vecmath.Vector3d;


/**
 * <div class="introClass">
 *   Euler angles define rotations around the three axes <i>x</i>,
 *   <i>y</i> and <i>z</i>.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       EulerAngles.java
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
 *       24.09.2013
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
 * <div class="classHistoryTitle">History:</div>
 * <table class="classHistoryTable">
 *   <tr>
 *     <th>Date</th>
 *     <th>Version</th>
 *     <th>Changes</th>
 *   </tr>
 *   <tr>
 *     <td>24.09.2013</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class EulerAngles
{
  private Angle angleX;
  private Angle angleY;
  private Angle angleZ;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a set of Euler angles around the given axes.
   * </div>
   * 
   * @param angleX  The rotation around the <i>x</i>-axis.
   *                It must not be <code>null</code>.
   * @param angleY  The rotation around the <i>y</i>-axis.
   *                It must not be <code>null</code>.
   * @param angleZ  The rotation around the <i>z</i>-axis.
   *                It must not be <code>null</code>.
   */
  public EulerAngles (Angle angleX, Angle angleY, Angle angleZ)
  {
    this.angleX = Angle.createByCopying (angleX);
    this.angleY = Angle.createByCopying (angleY);
    this.angleZ = Angle.createByCopying (angleZ);
  }
  
  public EulerAngles (EulerAngles otherEulerAngles)
  {
    this.angleX = Angle.createByCopying (otherEulerAngles.getAngleX ());
    this.angleY = Angle.createByCopying (otherEulerAngles.getAngleY ());
    this.angleZ = Angle.createByCopying (otherEulerAngles.getAngleZ ());
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a set of Euler angles with all angles set to
   *   zero degrees.
   * </div>
   */
  public EulerAngles ()
  {
    this.angleX = new Angle (0.0);
    this.angleY = new Angle (0.0);
    this.angleZ = new Angle (0.0);
  }
  
  
  public Angle[] getAnglesAsArray ()
  {
    Angle[] angleArray = null;
    
    angleArray = new Angle[]
    {
      angleX, angleY, angleZ
    };
    
    return angleArray;
  }
  
  public Vector3d getAnglesAsVector3d (AngleUnit angleUnit)
  {
    Vector3d anglesAsVector = null;
    
    anglesAsVector = new Vector3d ();
    anglesAsVector.setX           (angleX.getSizeIn (angleUnit));
    anglesAsVector.setY           (angleY.getSizeIn (angleUnit));
    anglesAsVector.setZ           (angleZ.getSizeIn (angleUnit));
    
    return anglesAsVector;
  }
  
  public double[] getAngleSizesInGivenUnitAsArray (AngleUnit angleUnit)
  {
    double[] angleSizes = new double[3];
    
    angleSizes[0] = angleX.getSizeIn (angleUnit);
    angleSizes[1] = angleY.getSizeIn (angleUnit);
    angleSizes[2] = angleZ.getSizeIn (angleUnit);
    
    return angleSizes;
  }
  
  public Angle getAngleX ()
  {
    return angleX;
  }
  
  public void setAngleX (Angle angleX)
  {
    this.angleX = new Angle (angleX);
  }
  
  public Angle getAngleY ()
  {
    return angleY;
  }
  
  public void setAngleY (Angle angleY)
  {
    this.angleY = new Angle (angleY);
  }
  
  public Angle getAngleZ ()
  {
    return angleZ;
  }
  
  public void setAngleZ (Angle angleZ)
  {
    this.angleZ = new Angle (angleZ);
  }
  
  
  public void setAnglesFromArray (Angle[] angleArray)
  {
    CheckingUtils.checkForNull (angleArray, "Angle array is null.");
    
    int numberOfAngles = angleArray.length;
    
    if (numberOfAngles >= 1)
    {
      this.angleX = Angle.createByCopying (angleArray[0]);
    }
    
    if (numberOfAngles >= 2)
    {
      this.angleY = Angle.createByCopying (angleArray[1]);
    }
    
    if (numberOfAngles >= 3)
    {
      this.angleZ = Angle.createByCopying (angleArray[2]);
    }
  }
  
  public void setAllAnglesTo (Angle angle)
  {
    this.angleX = Angle.createByCopying (angle);
    this.angleY = Angle.createByCopying (angle);
    this.angleZ = Angle.createByCopying (angle);
  }
  
  public void setFromEulerAngles (EulerAngles otherEulerAngles)
  {
    this.angleX = otherEulerAngles.getAngleX ();
    this.angleY = otherEulerAngles.getAngleY ();
    this.angleZ = otherEulerAngles.getAngleZ ();
  }
  
  
  public boolean areEqual (EulerAngles otherEulerAngles)
  {
    boolean areEqual = false;
    
    if (otherEulerAngles != null)
    {
      Angle otherAngleX = otherEulerAngles.getAngleX ();
      Angle otherAngleY = otherEulerAngles.getAngleY ();
      Angle otherAngleZ = otherEulerAngles.getAngleZ ();
      
      areEqual =
      (
        (angleX.isSameAngle (otherAngleX)) &&
        (angleY.isSameAngle (otherAngleY)) &&
        (angleZ.isSameAngle (otherAngleZ))
      );
    }
    // Other Euler angles are null? => Not equal.
    else
    {
      areEqual = false;
    }
    
    return areEqual;
  }
  
  
  public Angle[] getAngleDifferencesAsArray (EulerAngles otherOrientation)
  {
    checkOtherEulerAngles (otherOrientation);
    
    Angle[] angleDifferences = null;
    
    Angle otherAngleX = otherOrientation.getAngleX ();
    Angle otherAngleY = otherOrientation.getAngleY ();
    Angle otherAngleZ = otherOrientation.getAngleZ ();
    
    angleDifferences = new Angle[]
    {
      angleX.getDistanceAsAngle (otherAngleX),
      angleY.getDistanceAsAngle (otherAngleY),
      angleZ.getDistanceAsAngle (otherAngleZ)
    };
    
    return angleDifferences;
  }
  
  // Creates a new EulerAngles set from this - andereOrientierung.
  public EulerAngles getAngleDifferencesAsOrientation
  (
    EulerAngles otherOrientation
  )
  {
    checkOtherEulerAngles (otherOrientation);
    
    EulerAngles differenceOrientation = null;
    Angle[]     angleDifferencesArray = null;
    
    angleDifferencesArray = getAngleDifferencesAsArray
    (
      otherOrientation
    );
    differenceOrientation = new EulerAngles
    (
      angleDifferencesArray[0],
      angleDifferencesArray[1],
      angleDifferencesArray[2]
    );
    
    return differenceOrientation;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  public static EulerAngles createFromAnglesXYZ
  (
    Angle angleX,
    Angle angleY,
    Angle angleZ
  )
  {
    return new EulerAngles (angleX, angleY, angleZ);
  }
  
  /**
   * <div class="introMethod">
   *   Create a set of Euler angles with the given angle around the
   *   <i>x</i>-axis and zero angles around <i>y</i> and <i>z</i>.
   * </div>
   * 
   * @param angleX  The angle around the <i>x</i>-axis.
   *                The <code>null</code> value is invalid.
   * 
   * @return        An Euler angles set with following angles:
   *                (<code>angleX</code>, 0.0°, 0.0°).
   * 
   * @throws NullPointerException  If the <code>angleX</code> equals
   *                               <code>null</code>.
   */
  public static EulerAngles createFromAngleX (Angle angleX)
  {
    return new EulerAngles (angleX, new Angle (), new Angle ());
  }
  
  /**
   * <div class="introMethod">
   *   Create a set of Euler angles with the given angle around the
   *   <i>y</i>-axis and zero angles around <i>x</i> and <i>z</i>.
   * </div>
   * 
   * @param angleY  The angle around the <i>y</i>-axis.
   *                The <code>null</code> value is invalid.
   * 
   * @return        An Euler angles set with following angles:
   *                (0.0°, <code>angleY</code>, 0.0°).
   * 
   * @throws NullPointerException  If the <code>angleY</code> equals
   *                               <code>null</code>.
   */
  public static EulerAngles createFromAngleY (Angle angleY)
  {
    return new EulerAngles (new Angle (), angleY, new Angle ());
  }
  
  /**
   * <div class="introMethod">
   *   Create a set of Euler angles with the given angle around the
   *   <i>z</i>-axis and zero angles around <i>x</i> and <i>y</i>.
   * </div>
   * 
   * @param angleZ  The angle around the <i>z</i>-axis.
   *                The <code>null</code> value is invalid.
   * 
   * @return        An Euler angles set with following angles:
   *                (0.0°, 0.0°, <code>angleZ</code>).
   * 
   * @throws NullPointerException  If the <code>angleZ</code> equals
   *                               <code>null</code>.
   */
  public static EulerAngles createFromAngleZ (Angle angleZ)
  {
    return new EulerAngles (new Angle (), new Angle (), angleZ);
  }
  
  /**
   * <div class="introMethod">
   *   Create a set of Euler angles with the given angle along all
   *   axes.
   * </div>
   * 
   * @param angle  The angle to use for all axes.
   *               The <code>null</code> value is invalid.
   * 
   * @return       An Euler angles set with following angles:
   *               (<code>angle</code>, <code>angle</code>,
   *                <code>angle</code>).
   * 
   * @throws NullPointerException  If the <code>angle</code> equals
   *                               <code>null</code>.
   */
  public static EulerAngles createFromUniformAngle (Angle angle)
  {
    return new EulerAngles (angle, angle, angle);
  }
  
  /**
   * <div class="introMethod">
   *   Creates an Euler angles set with all angles equal to zero
   *   degrees.
   * </div>
   * 
   * @return  An Euler angles set with following angles:
   *          (0.0°, 0.0°, 0.0°).
   */
  public static EulerAngles createZeroAngles ()
  {
    return new EulerAngles ();
  }
  
  public static EulerAngles createByCopying (EulerAngles eulerAngles)
  {
    return new EulerAngles (eulerAngles);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = null;
    
    asString = String.format
    (
      "EulerAngles(angleX=%s, angleY=%s, angleZ=%s)",
      angleX, angleY, angleZ
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static void checkOtherEulerAngles (EulerAngles otherEulerAngles)
  {
    CheckingUtils.checkForNull
    (
      otherEulerAngles,
      "Other Euler angles are null."
    );
  }
}
