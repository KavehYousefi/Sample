package ky.transform;

import safercode.CheckingUtils;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix3d;

import ky.angle.Angle;
import ky.angle.AngleMath;
import ky.angle.AngleUnit;


/**
 * <div class="introClass">
 *   Implements a shear (also known as <i>skew</i>) transformation in
 *   two or three dimensions.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Shearing.java
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
 *       24.12.2013
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
 *     <td>24.12.2013</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Shearing
implements   TransformObject
{
  // Maps shear parameter to shear factor.
  private EnumMap<ShearParameter, Double> shearings;
  
  
  /**
   * <div class="introConstructor">
   *   This constructor allows the definition of all possible
   *   shearing factors in a three-dimensional space.
   * </div>
   * 
   * @param factorXY  The shearing factor of the top/bottom plane
   *                  along the <i>x</i>-axis.
   * @param factorXZ  The shearing factor of the front/back plane
   *                  along the <i>x</i>-axis.
   * @param factorYX  The shearing factor of the left/right plane
   *                  along the <i>y</i>-axis.
   * @param factorYZ  The shearing factor of the front/back plane
   *                  along the <i>y</i>-axis.
   * @param factorZX  The shearing factor of the left/right plane
   *                  along the <i>z</i>-axis.
   * @param factorZY  The shearing factor of the top/bottom plane
   *                  along the <i>z</i>-axis.
   */
  public Shearing
  (
    double factorXY,
    double factorXZ,
    double factorYX,
    double factorYZ,
    double factorZX,
    double factorZY
  )
  {
    double[] shearingsArray =
    {
      factorXY,
      factorXZ,
      factorYX,
      factorYZ,
      factorZX,
      factorZY
    };
    
    initShear                   ();
    setShearsFromFactorArray (shearingsArray);
  }
  
  /**
   * <div class="introConstructor">
   *   A two-dimensional shearing of the given factors is created.
   * </div>
   * 
   * @param factorX  The shearing along the <i>x</i> axis.
   * @param factorY  The shearing along the <i>y</i> axis.
   */
  public Shearing (double factorX, double factorY)
  {
    initShear     ();
    setShearFactorssXY2D (factorX, factorY);
  }
  
  /**
   * <div class="introConstructor">
   *   A two-dimensional shearing of the given angles is created.
   * </div>
   * 
   * @param shearAngleX  The shearing angle along the <i>x</i> axis.
   *                     It must not be <code>null</code>.
   * @param shearAngleY  The shearing angle along the <i>y</i> axis.
   *                     It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If <code>shearAngleX</code> or
   *                               <code>shearAngleY</code> equals
   *                               <code>null</code>.
   */
  public Shearing (Angle shearAngleX, Angle shearAngleY)
  {
    initShear          ();
    setShearAnglesXY2D (shearAngleX, shearAngleY);
  }
  
  /**
   * <div class="introConstructor">
   *   A shearing of zero along each axis is created.
   * </div>
   */
  public Shearing ()
  {
    initShear ();
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns a mapping of each shear parameter to its shear factor.
   * </div>
   * 
   * @return  An <code>EnumMap</code> with the shear parameters and
   *          factors.
   */
  public EnumMap<ShearParameter, Double> getShearFactorMap ()
  {
    return shearings;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a mapping of each shear parameter to its shear angle.
   * </div>
   * 
   * @return  An <code>EnumMap</code> with the shear parameters and
   *          angles.
   */
  public EnumMap<ShearParameter, Angle> getShearAngleMap (AngleUnit angleUnit)
  {
    EnumMap<ShearParameter, Angle> shearAngleMap = null;
    
    shearAngleMap = new EnumMap<ShearParameter, Angle> (ShearParameter.class);
    
    for (Entry<ShearParameter, Double> shearFactorEntries : shearings.entrySet ())
    {
      ShearParameter shearParameter = null;
      Angle          shearAngle     = null;
      
      shearParameter = shearFactorEntries.getKey   ();
      shearAngle     = getShearAngle (shearParameter, angleUnit);
      
      shearAngleMap.put (shearParameter, shearAngle);
    }
    
    return shearAngleMap;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the shearing factor for a given shearing parameter.
   * </div>
   * 
   * @param shearParameter  The non-<code>null</code> shearing
   *                        parameter.
   * 
   * @return                The shearing factor for the given parameter.
   * 
   * @throws NullPointerException  If <code>shearParameter</code>
   *                               equals <code>null</code>.
   */
  public double getShearFactorFromParameter (ShearParameter shearParameter)
  {
    return shearings.get (shearParameter);
  }
  
  public double getShearFactorFromSideAndAxis (ShearSide side, ShearAxis axis)
  {
    ShearParameter parameter = null;
    
    parameter = ShearParameter.getShearParameterFromSideAndAxis (side, axis);
    
    return shearings.get (parameter);
  }
  
  /**
   * <div class="introMethod">
   *   Returns an array containing the shear factors.
   * </div>
   * 
   * @return  An array containing the shear factors:
   *          <table>
   *            <tr>
   *              <th>Index</th>
   *              <th>Shear factor</th>
   *            </tr>
   *            <tr>
   *              <td>0</td>
   *              <td><code>ShearParameter.SHEARING_XY</code></td>
   *            </tr>
   *            <tr>
   *              <td>1</td>
   *              <td><code>ShearParameter.SHEARING_XZ</code></td>
   *            </tr>
   *            <tr>
   *              <td>2</td>
   *              <td><code>ShearParameter.SHEARING_YX</code></td>
   *            </tr>
   *            <tr>
   *              <td>3</td>
   *              <td><code>ShearParameter.SHEARING_YZ</code></td>
   *            </tr>
   *            <tr>
   *              <td>4</td>
   *              <td><code>ShearParameter.SHEARING_ZX</code></td>
   *            </tr>
   *            <tr>
   *              <td>5</td>
   *              <td><code>ShearParameter.SHEARING_ZY</code></td>
   *            </tr>
   *          </table>
   */
  public double[] getShearFactorsArray ()
  {
    double[] shearFactorsArray = new double[]
    {
      shearings.get (ShearParameter.SHEARING_XY),
      shearings.get (ShearParameter.SHEARING_XZ),
      shearings.get (ShearParameter.SHEARING_YX),
      shearings.get (ShearParameter.SHEARING_YZ),
      shearings.get (ShearParameter.SHEARING_ZX),
      shearings.get (ShearParameter.SHEARING_ZY)
    };
    
    return shearFactorsArray;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a list containing the shear factors.
   * </div>
   * 
   * @return  A list containing the shear factors:
   *          <table>
   *            <tr>
   *              <th>Index</th>
   *              <th>Shear factor</th>
   *            </tr>
   *            <tr>
   *              <td>0</td>
   *              <td><code>ShearParameter.SHEARING_XY</code></td>
   *            </tr>
   *            <tr>
   *              <td>1</td>
   *              <td><code>ShearParameter.SHEARING_XZ</code></td>
   *            </tr>
   *            <tr>
   *              <td>2</td>
   *              <td><code>ShearParameter.SHEARING_YX</code></td>
   *            </tr>
   *            <tr>
   *              <td>3</td>
   *              <td><code>ShearParameter.SHEARING_YZ</code></td>
   *            </tr>
   *            <tr>
   *              <td>4</td>
   *              <td><code>ShearParameter.SHEARING_ZX</code></td>
   *            </tr>
   *            <tr>
   *              <td>5</td>
   *              <td><code>ShearParameter.SHEARING_ZY</code></td>
   *            </tr>
   *          </table>
   */
  public List<Double> getShearFactorsList ()
  {
    List<Double> shearFactorsList = new ArrayList<Double> ();
    
    shearFactorsList.add (shearings.get (ShearParameter.SHEARING_XY));
    shearFactorsList.add (shearings.get (ShearParameter.SHEARING_XZ));
    shearFactorsList.add (shearings.get (ShearParameter.SHEARING_YX));
    shearFactorsList.add (shearings.get (ShearParameter.SHEARING_YZ));
    shearFactorsList.add (shearings.get (ShearParameter.SHEARING_ZX));
    shearFactorsList.add (shearings.get (ShearParameter.SHEARING_ZY));
    
    return shearFactorsList;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a list containing the shear angles.
   * </div>
   * 
   * @return  A list containing the shear angles:
   *          <table>
   *            <tr>
   *              <th>Index</th>
   *              <th>Shear factor</th>
   *            </tr>
   *            <tr>
   *              <td>0</td>
   *              <td><code>ShearParameter.SHEARING_XY</code></td>
   *            </tr>
   *            <tr>
   *              <td>1</td>
   *              <td><code>ShearParameter.SHEARING_XZ</code></td>
   *            </tr>
   *            <tr>
   *              <td>2</td>
   *              <td><code>ShearParameter.SHEARING_YX</code></td>
   *            </tr>
   *            <tr>
   *              <td>3</td>
   *              <td><code>ShearParameter.SHEARING_YZ</code></td>
   *            </tr>
   *            <tr>
   *              <td>4</td>
   *              <td><code>ShearParameter.SHEARING_ZX</code></td>
   *            </tr>
   *            <tr>
   *              <td>5</td>
   *              <td><code>ShearParameter.SHEARING_ZY</code></td>
   *            </tr>
   *          </table>
   */
  public List<Angle> getShearAnglesList (AngleUnit angleUnit)
  {
    List<Angle> shearAnglesList = new ArrayList<Angle> ();
    
    shearAnglesList.add (getShearAngle (ShearParameter.SHEARING_XY, angleUnit));
    shearAnglesList.add (getShearAngle (ShearParameter.SHEARING_XZ, angleUnit));
    shearAnglesList.add (getShearAngle (ShearParameter.SHEARING_YX, angleUnit));
    shearAnglesList.add (getShearAngle (ShearParameter.SHEARING_YZ, angleUnit));
    shearAnglesList.add (getShearAngle (ShearParameter.SHEARING_ZX, angleUnit));
    shearAnglesList.add (getShearAngle (ShearParameter.SHEARING_ZY, angleUnit));
    
    return shearAnglesList;
  }
  
  public double getShearX2D ()
  {
    return shearings.get (ShearParameter.SHEARING_XY);
  }
  
  /**
   * <div class="introMethod">
   *   Returns the (2D) shearing angle along the <i>x</i>-axis.
   * </div>
   * 
   * @param angleUnit  The optional unit of the resulting angle.
   *                   It defaults to <code>AngleUnit.DEGREE</code>.
   *                   
   * @return           The (2D) shearing angle along the <i>x</i>-axis.
   */
  public Angle getShearAngleX2D (AngleUnit angleUnit)
  {
    return getShearAngle (ShearParameter.SHEARING_XY, angleUnit);
  }
  
  public double getShearY2D ()
  {
    return shearings.get (ShearParameter.SHEARING_YX);
  }
  
  /**
   * <div class="introMethod">
   *   Returns the (2D) shearing angle along the <i>y</i>-axis.
   * </div>
   * 
   * @param angleUnit  The optional unit of the resulting angle.
   *                   It defaults to <code>AngleUnit.DEGREE</code>.
   *                   
   * @return           The (2D) shearing angle along the <i>y</i>-axis.
   */
  public Angle getShearAngleY2D (AngleUnit angleUnit)
  {
    return getShearAngle (ShearParameter.SHEARING_YX, angleUnit);
  }
  
  // Shear angle is simply: atan(shearFactor).
  public Angle getShearAngle (ShearParameter shearParameter, AngleUnit angleUnit)
  {
    Angle  shearAngle  = null;
    double shearFactor = 0.0;
    
    shearFactor = getShearFactorFromParameter (shearParameter);
    shearAngle  = shearFactorToAngle    (shearFactor, angleUnit);
    
    return shearAngle;
  }
  
  // ignoreNullValues = true?  => Only copy entries with value != null.
  // ignoreNullValues = false? => Interpret value = null as factor 0.0.
  public void setShearFactorMap
  (
    Map<ShearParameter, Double> shearFactorMap,
    boolean                     ignoreNullValues
  )
  {
    CheckingUtils.checkForNull (shearFactorMap, "Shearing map is null.");
    
    for (ShearParameter parameter : shearFactorMap.keySet ())
    {
      Double factor = shearFactorMap.get (parameter);
      
      // Null values are not ignored? => Interpret them as zero.
      if ((factor == null) && (! ignoreNullValues))
      {
        factor = new Double (0.0);
      }
      
      this.shearings.put (parameter, factor);
    }
  }
  
  public void setShearsFromFactorArray (double[] shearingsArray)
  {
    CheckingUtils.checkForNull (shearingsArray,
                                "Shearing array is null.");
    
    shearings.put (ShearParameter.SHEARING_XY, shearingsArray[0]);
    shearings.put (ShearParameter.SHEARING_XZ, shearingsArray[1]);
    shearings.put (ShearParameter.SHEARING_YX, shearingsArray[2]);
    shearings.put (ShearParameter.SHEARING_YZ, shearingsArray[3]);
    shearings.put (ShearParameter.SHEARING_ZX, shearingsArray[4]);
    shearings.put (ShearParameter.SHEARING_ZY, shearingsArray[5]);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the shearing of a side (plane) along an axis by a given
   *   factor.
   * </div>
   * 
   * @param side    The pair of sides or the plane to be sheared.
   *                Given <code>null</code>, this method will do
   *                nothing.
   * @param axis    The axis to shear along. Given <code>null</code>,
   *                this method will do nothing.
   * @param factor  The factor to shear by.
   */
  public void setShearFactorFromSideAndAxis
  (
    ShearSide side,
    ShearAxis axis,
    double    factor
  )
  {
    ShearParameter shearParameter = ShearParameter.getShearParameterFromSideAndAxis
    (
      side,
      axis
    );
    
    if (shearParameter != null)
    {
      shearings.put (shearParameter, factor);
    }
  }
  
  public void setShearFactor (ShearParameter parameter, double factor)
  {
    shearings.put (parameter, factor);
  }
  
  public void setShearAngle (ShearParameter parameter, Angle shearAngle)
  {
    shearings.put (parameter, shearAngleToFactor (shearAngle));
  }
  
  public void setAllShearsToFactor (double factor)
  {
    for (ShearParameter parameter : ShearParameter.values ())
    {
      shearings.put (parameter, factor);
    }
  }
  
  public void setShearFactorssXY2D (double factorX, double factorY)
  {
    shearings.put (ShearParameter.SHEARING_XY, factorX);
    shearings.put (ShearParameter.SHEARING_YX, factorY);
  }
  
  public void setShearFactorX2D (double factorX)
  {
    shearings.put (ShearParameter.SHEARING_XY, factorX);
  }
  
  public void setShearFactorY2D (double factorY)
  {
    shearings.put (ShearParameter.SHEARING_YX, factorY);
  }
  
  public void setShearAngleX2D (Angle shearAngleX)
  {
    setShearAngle (ShearParameter.SHEARING_XY, shearAngleX);
  }
  
  public void setShearAngleY2D (Angle shearAngleY)
  {
    setShearAngle (ShearParameter.SHEARING_YX, shearAngleY);
  }
  
  public void setShearAnglesXY2D (Angle shearAngleX, Angle shearAngleY)
  {
    setShearAngleX2D (shearAngleX);
    setShearAngleY2D (shearAngleY);
  }
  
  public void setShearFactorXY (double factorXY)
  {
    shearings.put (ShearParameter.SHEARING_XY, factorXY);
  }
  
  public void setShearFactorXZ (double factorXZ)
  {
    shearings.put (ShearParameter.SHEARING_XZ, factorXZ);
  }
  
  public void setShearFactorYX (double factorYX)
  {
    shearings.put (ShearParameter.SHEARING_YX, factorYX);
  }
  
  public void setShearFactorYZ (double factorYZ)
  {
    shearings.put (ShearParameter.SHEARING_YZ, factorYZ);
  }
  
  public void setShearFactorZX (double factorZX)
  {
    shearings.put (ShearParameter.SHEARING_ZX, factorZX);
  }
  
  public void setShearFactorZY (double factorZY)
  {
    shearings.put (ShearParameter.SHEARING_ZY, factorZY);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the shearig factors from the given 3D matrix.
   * </div>
   * 
   * @param matrix3d  The matrix to extract the shearing data from.
   *                  The <code>null</code> value is invalid.
   * 
   * @throws NullPointerException  If the <code>matrix3d</code> equals
   *                               <code>null</code>.
   */
  public void setFromMatrix3d (Matrix3d matrix3d)
  {
    CheckingUtils.checkForNull (matrix3d, "Matrix3d is null.");
    
    setShearFactorXY (matrix3d.getElement (0, 1));
    setShearFactorXZ (matrix3d.getElement (0, 2));
    setShearFactorYX (matrix3d.getElement (1, 0));
    setShearFactorYZ (matrix3d.getElement (1, 2));
    setShearFactorZX (matrix3d.getElement (2, 0));
    setShearFactorZY (matrix3d.getElement (2, 1));
  }
  
  public void setFromTransform3D (Transform3D transform3D)
  {
    CheckingUtils.checkForNull (transform3D, "Transform3D is null.");
    
    Matrix3d matrix3d = null;
    
    matrix3d = new Matrix3d ();
    transform3D.get         (matrix3d);
    setFromMatrix3d         (matrix3d);
  }
  
  /**
   * <div class="introMethod">
   *   Sets all shearing factors to zero.
   * </div>
   */
  public void reset ()
  {
    initShear ();
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  /**
   * <div class="introMethod">
   *   Creates a new shearing from the given 3D matrix.
   * </div>
   * 
   * @param matrix3d  The matrix to extract the shearing data from.
   *                  The <code>null</code> value is invalid.
   * 
   * @return          A new instance of this <code>Shearing</code> class
   *                  from the <code>matrix3d</code>'s data.
   * 
   * @throws NullPointerException  If the <code>matrix3d</code> equals
   *                               <code>null</code>.
   */
  public static Shearing createFromMatrix3d (Matrix3d matrix3d)
  {
    Shearing shearing = null;
    
    shearing = new Shearing  ();
    shearing.setFromMatrix3d (matrix3d);
    
    return shearing;
  }
  
  public static Shearing createFromTransform3D (Transform3D transform3D)
  {
    Shearing shearing = null;
    
    shearing = new Shearing     ();
    shearing.setFromTransform3D (transform3D);
    
    return shearing;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "TransformObject".          -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public double[] getAsTransformMatrix3D ()
  {
    double[] matrixArray = null;
    double   shearingXY  = shearings.get (ShearParameter.SHEARING_XY);
    double   shearingXZ  = shearings.get (ShearParameter.SHEARING_XZ);
    double   shearingYX  = shearings.get (ShearParameter.SHEARING_YX);
    double   shearingYZ  = shearings.get (ShearParameter.SHEARING_YZ);
    double   shearingZX  = shearings.get (ShearParameter.SHEARING_ZX);
    double   shearingZY  = shearings.get (ShearParameter.SHEARING_ZY);
    
    matrixArray = new double[]
    {
      1.00000000,  shearingXY,  shearingXZ,  0.00000000,
      shearingYX,  1.00000000,  shearingYZ,  0.00000000,
      shearingZX,  shearingZY,  1.00000000,  0.00000000,
      0.00000000,  0.00000000,  0.00000000,  1.00000000
    };
    
    return matrixArray;
  }
  
  @Override
  public Transform3D getAsTransform3D ()
  {
    Transform3D transform3D = null;
    double[]    matrix      = getAsTransformMatrix3D ();
    
    transform3D = new Transform3D (matrix);
    
    return transform3D;
  }
  
  
  
  // Liefert 6-Elemente-Array.
  // Auch zweite Methode anbieten fuer 4-Elemente-Array?
  /* VORSICHT!!!
   *   Der Konstruktor
   *     public AffineTransform(double[] flatmatrix)
   *   erwartet die Parameter im "column-major"-Format, also Spalte
   *   fuer Spalte, NICHT Zeile fuer Zeile!
   * 
   */
  @Override
  public double[] getAsTransformMatrix2D ()
  {
    double[] matrixArray = null;
    double   shearingXY  = shearings.get (ShearParameter.SHEARING_XY);
    double   shearingYX  = shearings.get (ShearParameter.SHEARING_YX);
    
    matrixArray = new double[]
    {
      // First  column (m00, m10).
      1.00000000,
      shearingYX,
      // Second column (m01, m11).
      shearingXY,
      1.00000000,
      // Third  column (m02, m12).
      0.00000000,
      0.00000000
    };
    
    return matrixArray;
  }
  
  @Override
  public AffineTransform getAsAffineTransform ()
  {
    AffineTransform affineTransform = null;
    double[]        flatMatrix      = getAsTransformMatrix2D ();
    
    affineTransform = new AffineTransform (flatMatrix);
    
    return affineTransform;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static methods.                          -- //
  //////////////////////////////////////////////////////////////////////
  
  // Shear factor is simply: tan(shearAngle).
  public static double shearAngleToFactor (Angle shearAngle)
  {
    return AngleMath.tan (shearAngle);
  }
  
  // Shear angle is simply: atan(shearFactor).
  public static Angle shearFactorToAngle
  (
    double    shearFactor,
    AngleUnit angleUnit
  )
  {
    return AngleMath.atan (shearFactor, angleUnit);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString   = null;
    double shearingXY = shearings.get (ShearParameter.SHEARING_XY);
    double shearingXZ = shearings.get (ShearParameter.SHEARING_XZ);
    double shearingYX = shearings.get (ShearParameter.SHEARING_YX);
    double shearingYZ = shearings.get (ShearParameter.SHEARING_YZ);
    double shearingZX = shearings.get (ShearParameter.SHEARING_ZX);
    double shearingZY = shearings.get (ShearParameter.SHEARING_ZY);
    
    asString = String.format
    (
      "Shearing(factorXY=%s; factorXZ=%s; " +
               "factorYX=%s; factorYZ=%s; " +
               "factorZX=%s; factorZY=%s)",
      shearingXY, shearingXZ,
      shearingYX, shearingYZ,
      shearingZX, shearingZY
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initShear ()
  {
    this.shearings = new EnumMap<ShearParameter, Double> (ShearParameter.class);
    
    for (ShearParameter shearParameter : ShearParameter.values ())
    {
      this.shearings.put (shearParameter, 0.0);
    }
  }
}