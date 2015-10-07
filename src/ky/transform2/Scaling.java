package ky.transform2;

import ky.Dimensions;
import ky.Position;
import safercode.CheckingUtils;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix3d;
import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;


/* TODO:
 *   Use "anchorPoint" in method "getAsTransformMatrix2D ()".
 */
/**
 * <div class="introClass">
 *   Implements a possible non-uniform scaling transformation in two or
 *   three dimensions.
 * </div>
 * 
 * <div>
 *   Following sources have been useful:
 *   <ul>
 *     <li>
 *       <a href="http://www.developer.com/java/other/article.php/3717101/Understanding-Transforms-in-Java-3D.htm">
 *         http://www.developer.com/java/other/article.php/3717101/Understanding-Transforms-in-Java-3D.htm
 *       </a>
 *       <br />
 *       Topic: Transformations in Java 3D.
 *       <br />
 *       Accessed: 03.03.2014
 *     </li>
 *     <li>
 *       <a href="http://cs.fit.edu/~wds/classes/cse5255/thesis/scale/scale.html#3dScale">
 *         http://cs.fit.edu/~wds/classes/cse5255/thesis/scale/scale.html#3dScale
 *       </a>
 *       <br />
 *       Topic: Scaling in 3D. Note that the scaling matrix
 *              used by the source is not suitable for Java 3D.
 *       <br />
 *       Accessed: 03.03.2014
 *     </li>
 *   </ul>
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Scaling.java
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
 *       03.03.2014
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
 *     <td>03.03.2014</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Scaling
implements   TransformObject
{
  private Position anchorPoint;
  private Vector3d scalingFactors;
  
  
  public Scaling (double factorX, double factorY, double factorZ)
  {
    initScaling (factorX, factorY, factorZ);
  }
  
  public Scaling (Dimensions dimensions)
  {
    initScaling
    (
      dimensions.getWidth  (),
      dimensions.getHeight (),
      dimensions.getDepth  ()
    );
  }
  
  public Scaling (Tuple3d scaleFactors)
  {
    initScaling
    (
      scaleFactors.getX (),
      scaleFactors.getY (),
      scaleFactors.getZ ()
    );
  }
  
  public Scaling (Tuple2d scaleFactors)
  {
    initScaling
    (
      scaleFactors.getX (),
      scaleFactors.getY (),
      1.0
    );
  }
  
  public Scaling (double factorX, double factorY)
  {
    initScaling (factorX, factorY, 1.0);
  }
  
  public Scaling (double factor, boolean alsoScaleAlongZ)
  {
    if (alsoScaleAlongZ)
    {
      initScaling (factor, factor, factor);
    }
    else
    {
      initScaling (factor, factor, 1.0);
    }
  }
  
  public Scaling (double factor)
  {
    initScaling (factor, factor, factor);
  }
  
  public Scaling ()
  {
    initScaling (1.0, 1.0, 1.0);
  }
  
  
  public Position getReferencePoint ()
  {
    return anchorPoint;
  }
  
  public void setReferencePoint (Position position)
  {
    this.anchorPoint = position;
  }
  
  public void setReferencePoint (double x, double y, double z)
  {
    this.anchorPoint = new Position (x, y, z);
  }
  
  
  public Vector3d getAsVector3d ()
  {
    return scalingFactors;
  }
  
  public Dimensions getAsDimensions ()
  {
    return new Dimensions (scalingFactors);
  }
  
  public void setFromVector3d (Vector3d vector3d)
  {
    this.scalingFactors = new Vector3d (vector3d);
  }
  
  /**
   * <div class="introMethod">
   *   Returns the two-dimensional scaling, that is, along the
   *   <i>x</i>- and <i>y</i>-axis, as <code>Vector2d</code> object.
   * </div>
   * 
   * @return  A <code>Vector2d</code> containing the
   *          <i>x</i> and <i>y</i> scaling factors.
   */
  public Vector2d getAsVector2d ()
  {
    Vector2d scalingFactorsIn2D = null;
    
    scalingFactorsIn2D = new Vector2d ();
    scalingFactorsIn2D.setX (scalingFactors.getX ());
    scalingFactorsIn2D.setY (scalingFactors.getY ());
    
    return scalingFactorsIn2D;
  }
  
  
  public double getScalingX ()
  {
    return scalingFactors.getX ();
  }
  
  public void setScalingX (double scaleFactorX)
  {
    scalingFactors.setX (scaleFactorX);
  }
  
  
  public double getScalingY ()
  {
    return scalingFactors.getY ();
  }
  
  public void setScalingY (double scaleFactorY)
  {
    scalingFactors.setY (scaleFactorY);
  }
  
  public double getScalingZ ()
  {
    return scalingFactors.getZ ();
  }
  
  public void setScalingZ (double scaleFactorZ)
  {
    scalingFactors.setZ (scaleFactorZ);
  }
  
  public void setFromDimensions (Dimensions dimensionen)
  {
    if (dimensionen != null)
    {
      scalingFactors.set (dimensionen.getAsArray ());
    }
  }
  
  public void setFromXYZFactors
  (
    double scaleFactorX,
    double scaleFactorY,
    double scaleFactorZ
  )
  {
    scalingFactors.setX (scaleFactorX);
    scalingFactors.setY (scaleFactorY);
    scalingFactors.setZ (scaleFactorZ);
  }
  
  public void setFromXY (double scaleFactorX, double scaleFactorY)
  {
    setFromXYZFactors (scaleFactorX, scaleFactorY, scalingFactors.getZ ());
  }
  
  public void setFromMatrix3d (Matrix3d matrix3d)
  {
    CheckingUtils.checkForNull (matrix3d, "Matrix3d is null.");
    
    setScalingX (matrix3d.getElement (0, 0));
    setScalingY (matrix3d.getElement (1, 1));
    setScalingZ (matrix3d.getElement (2, 2));
  }
  
  public void setFromTransform3D (Transform3D transform3D)
  {
    CheckingUtils.checkForNull (transform3D, "Transform3D is null.");
    
    Matrix3d matrix3d = null;
    
    matrix3d = new Matrix3d ();
    transform3D.get         (matrix3d);
    setFromMatrix3d         (matrix3d);
  }
  
  public void setAllTo (double scaleFactor)
  {
    setFromXYZFactors (scaleFactor, scaleFactor, scaleFactor);
  }
  
  public boolean isUniformIn3D ()
  {
    boolean isUniformIn3D = false;
    
    isUniformIn3D =
    (
      (scalingFactors.getX () == scalingFactors.getY ()) &&
      (scalingFactors.getY () == scalingFactors.getZ ())
    );
    
    return isUniformIn3D;
  }
  
  public boolean isUniformIn2D ()
  {
    boolean isUniformIn2D = false;
    
    isUniformIn2D = (scalingFactors.getX () == scalingFactors.getY ());
    
    return isUniformIn2D;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  /**
   * <div class="introMethod">
   *   Creates a scaling transformation based on the values of a given
   *   <code>Dimensions</code>.
   * </div>
   * 
   * @param dimensions  The dimensions representing the scaling factors.
   *                    It must not be <code>null</code>.
   * 
   * @return            A <code>Scaling</code> transformation with the
   *                    <code>dimensions</code> as scaling factors.
   * 
   * @throws NullPointerException  If the <code>dimensions</code> equal
   *                               <code>null</code>.
   */
  public static Scaling createFromDimensions (Dimensions dimensions)
  {
    Scaling scaling = null;
    
    scaling = new Scaling (dimensions);
    
    return scaling;
  }
  
  /**
   * <div class="introMethod">
   *   Creates a scaling transformation based on three factors given
   *   as a <code>Tuple3d</code>.
   * </div>
   * 
   * @param tuple3d  The 3D tuple holding the scaling factors.
   *                 It must not be <code>null</code>.
   * 
   * @return         A <code>Scaling</code> transformation with the
   *                 <code>tuple3d</code> scaling factors.
   * 
   * @throws NullPointerException  If the <code>tuple3d</code> equals
   *                               <code>null</code>.
   */
  public static Scaling createFromTuple3d (Tuple3d tuple3d)
  {
    Scaling scaling = null;
    
    scaling = new Scaling (tuple3d);
    
    return scaling;
  }
  
  public static Scaling createFromTuple2d (Tuple2d tuple2d)
  {
    Scaling scaling = null;
    
    scaling = new Scaling (tuple2d);
    
    return scaling;
  }
  
  public static Scaling createFromFactorsXYZ
  (
    double factorX,
    double factorY,
    double factorZ
  )
  {
    Scaling scaling = null;
    
    scaling = new Scaling (factorX, factorY, factorZ);
    
    return scaling;
  }
  
  public static Scaling createFromFactorsXY
  (
    double factorX,
    double factorY
  )
  {
    Scaling scaling = null;
    
    scaling = new Scaling (factorX, factorY);
    
    return scaling;
  }
  
  /**
   * <div class="introMethod">
   *   Creates a scaling transformation with all scaling factors set
   *   to the given value.
   * </div>
   * 
   * @param factor  The uniform scaling factor.
   * 
   * @return        A uniform <code>factor</code> <code>Scaling</code>
   *                transformation.
   */
  public static Scaling createFromUniformFactor (double factor)
  {
    Scaling scaling = null;
    
    scaling = new Scaling (factor);
    
    return scaling;
  }
  
  /**
   * <div class="introMethod">
   *   Creates a scaling transformation with all scaling factors set
   *   to one.
   * </div>
   * 
   * @return  A unit <code>Scaling</code> transformation.
   */
  public static Scaling createUnitScaling ()
  {
    return createFromUniformFactor (1.0);
  }
  
  /**
   * <div class="introMethod">
   *   Creates a scaling transformation with all scaling factors set
   *   to zero.
   * </div>
   * 
   * @return  A zero <code>Scaling</code> transformation.
   */
  public static Scaling createZeroScaling ()
  {
    return createFromUniformFactor (0.0);
  }
  
  public static Scaling createFromMatrix3d (Matrix3d matrix3d)
  {
    Scaling scaling = null;
    
    scaling = new Scaling   ();
    scaling.setFromMatrix3d (matrix3d);
    
    return scaling;
  }
  
  public static Scaling createFromTransform3D (Transform3D transform3D)
  {
    Scaling scaling = null;
    
    scaling = new Scaling      ();
    scaling.setFromTransform3D (transform3D);
    
    return scaling;
  }
  
  public static Scaling createFromAffineTransform (AffineTransform affineTransform)
  {
    Scaling scaling = null;
    
    scaling = new Scaling ();
    scaling.setScalingX   (affineTransform.getScaleX ());
    scaling.setScalingY   (affineTransform.getScaleY ());
    
    return scaling;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "TransformObject".          -- //
  //////////////////////////////////////////////////////////////////////
  
  // Translation, scaling, translation.
  @Override
  public double[] getAsTransformMatrix3D ()
  {
    double[] matrixArray  = null;
    double   scaleFactorX = scalingFactors.getX ();
    double   scaleFactorY = scalingFactors.getY ();
    double   scaleFactorZ = scalingFactors.getZ ();
    double   m20          = (1.0 - scaleFactorX) * anchorPoint.getX ();
    double   m31          = (1.0 - scaleFactorY) * anchorPoint.getY ();
    double   m32          = (1.0 - scaleFactorZ) * anchorPoint.getZ ();
    
    matrixArray = new double[]
    {
      scaleFactorX, 0.000000000,  0.000000000,  m20,
      0.000000000,  scaleFactorY, 0.000000000,  m31,
      0.000000000,  0.000000000,  scaleFactorZ, m32,
      0.000000000,  0.000000000,  0.000000000,  1.000000000
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
  
  @Override
  public double[] getAsTransformMatrix2D ()
  {
    double[] matrixArray = null;
    double   skalierungX = scalingFactors.getX ();
    double   skalierungY = scalingFactors.getY ();
    
    matrixArray = new double[]
    {
      // First column (m00, m10).
      skalierungX,
      0.000000000,
      // Second column (m01, m11).
      0.000000000,
      skalierungY,
      // Third column (m02, m12).
      0.000000000,
      0.000000000
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
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = String.format
    (
      "Scaling(factorX=%s, factorY=%s, factorZ=%s)",
      scalingFactors.getX (),
      scalingFactors.getY (),
      scalingFactors.getZ ()
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initScaling
  (
    double factorX,
    double factorY,
    double factorZ
  )
  {
    this.anchorPoint    = new Position (0.0, 0.0, 0.0);
    this.scalingFactors = new Vector3d (factorX, factorY, factorZ);
  }
}
