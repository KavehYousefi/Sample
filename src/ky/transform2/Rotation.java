package ky.transform2;

import ky.angle.EulerAngles;
import ky.angle.Angle;
import ky.angle.AngleMath;
import ky.angle.AngleUnit;
//import commies.transform.conversion.toeulerangles.QuaternionToEulerAngles;
//import commies.transform.conversion.toeulerangles.Transform3DQuaternionToEulerAngles;
import safercode.CheckingUtils;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix4d;
import javax.vecmath.Quat4d;


/**
 * <div class="introClass">
 *   Implements a rotation transformation in two or three dimensions.
 * </div>
 * 
 * <div>
 *   Following sources have been useful:
 *   <ul>
 *     <li>
 *       <a href="http://www.codeproject.com/Articles/42086/Space-and-Matrix-Transformations-Building-a-D-Eng">
 *         http://www.codeproject.com/Articles/42086/Space-and-Matrix-Transformations-Building-a-D-Eng
 *       </a><br />
 *       Topic: Transformations in 3D.<br />
 *       Accessed: 07.03.2014
 *     </li>
 *     <li>
 *       <a href="http://rodrigo-silveira.com/3d-programming-transformation-matrix-tutorial/#.UxoAIs4xgR4">
 *         http://rodrigo-silveira.com/3d-programming-transformation-matrix-tutorial/#.UxoAIs4xgR4
 *       </a><br />
 *       Topic: Transformations in 3D.<br />
 *       Accessed: 07.03.2014
 *     </li>
 *     <li>
 *       <a href="http://www.euclideanspace.com/maths/algebra/matrix/resources/code/index.htm">
 *         http://www.euclideanspace.com/maths/algebra/matrix/resources/code/index.htm
 *       </a><br />
 *       Topic: Transformations in 3D.<br />
 *       Accessed: 07.03.2014
 *     </li>
 *     <li>
 *       <a href="http://ken-soft.com/2008/12/25/graph3d-java-project-3d-points-to-2d/">
 *         http://ken-soft.com/2008/12/25/graph3d-java-project-3d-points-to-2d/
 *       </a><br />
 *       Topic: Transformations in 3D.<br />
 *       Accessed: 07.03.2014
 *     </li>
 *     <li>
 *       <a href="https://www.java.net/node/646711">
 *         https://www.java.net/node/646711
 *       </a><br />
 *       Topic: Java 3D transformations and Euler angles.<br />
 *       Accessed: 07.03.2014
 *     </li>
 *     <li>
 *       <a href="http://show.docjava.com/book/cgij/code/data/lectures/">
 *         http://show.docjava.com/book/cgij/code/data/lectures/
 *       </a><br />
 *       (see under "cr325" &rarr; "transforms.ppt" and "j3d.ppt")
 *       Topic: Transformations in Java 3D.<br />
 *       Accessed: 07.03.2014
 *     </li>
 *     <li>
 *       <a href="https://groups.google.com/forum/#!topic/uw.cs.cs349/gpaYRPQggvc">
 *         https://groups.google.com/forum/#!topic/uw.cs.cs349/gpaYRPQggvc
 *       </a><br />
 *       Topic: Retrieve rotation from <code>AffineTransform</code>.
 *       <br />
 *       Accessed: 13.09.2015
 *     </li>
 *     <li>
 *       <a href="http://stackoverflow.com/questions/12657710/get-rotation-from-2d-transformation-matrix">
 *         http://stackoverflow.com/questions/12657710/get-rotation-from-2d-transformation-matrix
 *       </a><br />
 *       Topic: Retrieve rotation from 2D transformation matrix.<br />
 *       Accessed: 13.09.2015
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
 *       Rotation.java
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
 *       07.03.2014
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
 *     <td>07.03.2014</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 *   <tr>
 *     <td>13.09.2015</td>
 *     <td>2.0</td>
 *     <td>
 *       Added static method for creating instances based on a given
 *       <code>AffineTransform</code>:
 *       <code>createFromAffineTransform(...)</code>.
 *     </td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Rotation
implements   TransformObject
{
  private EulerAngles   orientation;
  private RotationOrder order;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation defined by an orientation and a rotation
   *   order.
   * </div>
   * 
   * @param orientation  The orientation to provide the angles. The
   *                     <code>null</code> value defaults to an
   *                     orientation to all angles set to zero.
   * @param order        The rotation order. <code>null</code> defaults
   *                     to <code>DefaultRotationOrder.XYZ</code>.
   */
  public Rotation (EulerAngles orientation, RotationOrder order)
  {
    if (orientation != null)
    {
      this.orientation = orientation;
    }
    else
    {
      this.orientation = new EulerAngles ();
    }
    
    this.order = order;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation defined by an orientation and the default
   *   rotation order <code>DefaultRotationOrder.XYZ</code>.
   * </div>
   * 
   * @param orientation  The orientation to provide the angles. The
   *                     <code>null</code> value defaults to an
   *                     orientation to all angles set to zero.
   */
  public Rotation (EulerAngles orientation)
  {
    CheckingUtils.checkForNull (orientation, "Orientation is null.");
    this.orientation = orientation;
    this.order       = DefaultRotationOrder.XYZ;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation defined by three Euler angles and the default
   *   rotation order <code>DefaultRotationOrder.XYZ</code>.
   * </div>
   * 
   * @param angleX  The rotation angle around the <i>x</i> axis.
   *                The <code>null</code> value default to an angle
   *                of zero degrees.
   * @param angleY  The rotation angle around the <i>y</i> axis.
   *                The <code>null</code> value default to an angle
   *                of zero degrees.
   * @param angleZ  The rotation angle around the <i>z</i> axis.
   *                The <code>null</code> value default to an angle
   *                of zero degrees.
   */
  public Rotation (Angle angleX, Angle angleY, Angle angleZ)
  {
    initRotation (angleX, angleY, angleZ, DefaultRotationOrder.XYZ);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation around the <i>z</i> axis only.
   * </div>
   * 
   * @param angleZ  The rotation angle around the <i>z</i> axis.
   *                The <code>null</code> value default to an angle
   *                of zero degrees.
   */
  public Rotation (Angle angleZ)
  {
    initRotation (new Angle (), new Angle (), angleZ, DefaultRotationOrder.XYZ);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a default rotation of zero degrees around each axis.
   * </div>
   */
  public Rotation ()
  {
    initRotation (new Angle (), new Angle (), new Angle (), DefaultRotationOrder.XYZ);
  }
  
  
  public EulerAngles getEulerAngles ()
  {
    return orientation;
  }
  
  public void setEulerAngles (EulerAngles orientation)
  {
    this.orientation = orientation;
  }
  
  
  public Angle getRotationX ()
  {
    return orientation.getAngleX ();
  }
  
  /**
   * <div class="introMethod">
   *   Sets the rotation around the <i>x</i> axis.
   * </div>
   * 
   * @param angleX  The rotation around the <i>x</i> axis. Defaults to
   *                zero degrees, if given as <code>null</code>.
   */
  public void setRotationX (Angle angleX)
  {
    orientation.setAngleX (angleX);
  }
  
  
  public Angle getRotationY ()
  {
    return orientation.getAngleY ();
  }
  
  /**
   * <div class="introMethod">
   *   Sets the rotation around the <i>y</i> axis.
   * </div>
   * 
   * @param angleY  The rotation around the <i>y</i> axis. Defaults to
   *                zero degrees, if given as <code>null</code>.
   */
  public void setRotationY (Angle angleY)
  {
    orientation.setAngleY (angleY);
  }
  
  
  public Angle getRotationZ ()
  {
    return orientation.getAngleZ ();
  }
  
  /**
   * <div class="introMethod">
   *   Sets the rotation around the <i>z</i> axis.
   * </div>
   * 
   * @param angleZ  The rotation around the <i>z</i> axis. Defaults to
   *                zero degrees, if given as <code>null</code>.
   */
  public void setRotationZ (Angle angleZ)
  {
    orientation.setAngleZ (angleZ);
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the rotation order.
   * </div>
   * 
   * @return  The rotation order.
   */
  public RotationOrder getRotationOrder ()
  {
    return order;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the rotation order.
   * </div>
   * 
   * @param order  The rotation order.
   */
  public void setRotationOrder (RotationOrder order)
  {
    this.order = order;
  }
  
  
  public double[] getRotationXAsTransformMatrix3D ()
  {
    double[] matrixArray    = null;
    Angle    angleX         = orientation.getAngleX ();
    double   angleInRadians = angleX.getSizeInRadians ();
    double   cosAngleX      = Math.cos (angleInRadians);
    double   sinAngleX      = Math.sin (angleInRadians);
    
    matrixArray = new double[]
    {
      1.000000000,  0.000000000,  0.000000000,  0.000000000,
      0.000000000,  cosAngleX  ,  -sinAngleX ,  0.000000000,
      0.000000000,  sinAngleX  ,   cosAngleX ,  0.000000000,
      0.000000000,  0.000000000,  0.000000000,  1.000000000
    };
    
    return matrixArray;
  }
  
  public Transform3D getRotationXAsTransform3D ()
  {
    Transform3D transform3D = null;
    double[]    matrix      = getRotationXAsTransformMatrix3D ();
    
    transform3D = new Transform3D (matrix);
    
    return transform3D;
  }
  
  
  public double[] getRotationYAsTransformMatrix3D ()
  {
    double[] matrixArray = null;
    Angle    winkel      = orientation.getAngleY ();
    double   winkelInRad = winkel.getSizeInRadians ();
    double   cosWinkel   = Math.cos (winkelInRad);
    double   sinWinkel   = Math.sin (winkelInRad);
    
    matrixArray = new double[]
    {
      cosWinkel  ,  0.000000000,  sinWinkel  ,  0.000000000,
      0.000000000,  1.000000000,  0.000000000,  0.000000000,
      -sinWinkel ,  0.000000000,  cosWinkel  ,  0.000000000,
      0.000000000,  0.000000000,  0.000000000,  1.000000000
    };
    
    return matrixArray;
  }
  
  public Transform3D getRotationYAsTransform3D ()
  {
    Transform3D transform3D = null;
    double[]    matrix      = getRotationYAsTransformMatrix3D ();
    
    transform3D = new Transform3D (matrix);
    
    return transform3D;
  }
  
  
  public double[] getRotationZAsTransformMatrix3D ()
  {
    double[] matrixArray = null;
    Angle    winkel      = orientation.getAngleZ ();
    double   winkelInRad = winkel.getSizeInRadians ();
    double   cosWinkel   = Math.cos (winkelInRad);
    double   sinWinkel   = Math.sin (winkelInRad);
    
    matrixArray = new double[]
    {
      cosWinkel  ,  -sinWinkel ,  0.000000000,  0.000000000,
      sinWinkel  ,   cosWinkel ,  0.000000000,  0.000000000,
      0.000000000,  0.000000000,  1.000000000,  0.000000000,
      0.000000000,  0.000000000,  0.000000000,  1.000000000
    };
    
    return matrixArray;
  }
  
  public Transform3D getRotationZAsTransform3D ()
  {
    Transform3D transform3D = null;
    double[]    matrix      = getRotationZAsTransformMatrix3D ();
    
    transform3D = new Transform3D (matrix);
    
    return transform3D;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  public static Rotation createFromEulerAnglesAndOrder
  (
    EulerAngles   eulerAngles,
    RotationOrder rotationOrder
  )
  {
    return new Rotation (eulerAngles, rotationOrder);
  }
  
  public static Rotation createFromEulerAngles (EulerAngles eulerAngles)
  {
    Rotation rotation = new Rotation ();
    
    rotation = new Rotation (eulerAngles);
    
    return rotation;
  }
  
  public static Rotation createFromAngleX (Angle angleX)
  {
    Rotation rotation = null;
    
    rotation = new Rotation
    (
      angleX,
      Angle.createZeroDegreeAngle (),
      Angle.createZeroDegreeAngle ()
    );
    
    return rotation;
  }
  
  public static Rotation createFromAngleY (Angle angleY)
  {
    Rotation rotation = null;
    
    rotation = new Rotation
    (
      Angle.createZeroDegreeAngle (),
      angleY,
      Angle.createZeroDegreeAngle ()
    );
    
    return rotation;
  }
  
  public static Rotation createFromAngleZ (Angle angleZ)
  {
    Rotation rotation = null;
    
    rotation = new Rotation
    (
      Angle.createZeroDegreeAngle (),
      Angle.createZeroDegreeAngle (),
      angleZ
    );
    
    return rotation;
  }
  
  public static Rotation createFromUniformAngle (Angle angle)
  {
    return new Rotation (Angle.createByCopying (angle),
                         Angle.createByCopying (angle),
                         Angle.createByCopying (angle));
  }
  
  public static Rotation createFromZeroAngles ()
  {
    return new Rotation ();
  }
  
  // Uses the "Transform3DQuaternionToEulerAngles" class.
  public static Rotation createFromTransform3D (Transform3D transform3D)
  {
    CheckingUtils.checkForNull (transform3D, "Transform3D is null.");
    
    Rotation                rotation               = null;
    Quat4d                  quaternion             = null;
    EulerAngles             eulerAngles            = null;
    QuaternionToEulerAngles quatToEulerTransformer = null;
    
    quaternion             = new Quat4d                             ();
    quatToEulerTransformer = new Transform3DQuaternionToEulerAngles ();
    transform3D.get (quaternion);
    eulerAngles   = quatToEulerTransformer.getEulerAngles (quaternion);
    rotation      = new Rotation (eulerAngles);
    
    return rotation;
  }
  
  public static Rotation createFromAffineTransform (AffineTransform affineTransform)
  {
    Rotation rotation        = null;
    Angle    angleZ          = null;
    double   angleZInRadians = 0.0;
    double   sinAlpha        = 0.0;
    double   cosAlpha        = 0.0;
    
    sinAlpha        = affineTransform.getShearY ();    // m10 (shearY)
    cosAlpha        = affineTransform.getScaleY ();    // m11 (scaleY)
    angleZInRadians = Math.atan2 (sinAlpha, cosAlpha); // atan(m10, m11)
    angleZ          = Angle.createByConvertingFromSourceToDestinationUnit
    (
      angleZInRadians,
      AngleUnit.RADIAN,
      AngleUnit.DEGREE
    );
    
    rotation        = new Rotation ();
    rotation.setRotationZ          (angleZ);
    
    return rotation;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "TransformObject".          -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public double[] getAsTransformMatrix3D ()
  {
    double[] matrixArray  = null;
    double[] matrixArrayX = getRotationXAsTransformMatrix3D ();
    double[] matrixArrayY = getRotationYAsTransformMatrix3D ();
    double[] matrixArrayZ = getRotationZAsTransformMatrix3D ();
    Matrix4d matrixX      = new Matrix4d (matrixArrayX);
    Matrix4d matrixY      = new Matrix4d (matrixArrayY);
    Matrix4d matrixZ      = new Matrix4d (matrixArrayZ);
    Matrix4d resultMatrix = null;
    
    resultMatrix = order.multiplyMatrices (matrixX, matrixY, matrixZ);
    
    matrixArray  = new double[]
    {
      resultMatrix.m00, resultMatrix.m01, resultMatrix.m02, resultMatrix.m03,
      resultMatrix.m10, resultMatrix.m11, resultMatrix.m12, resultMatrix.m13,
      resultMatrix.m20, resultMatrix.m21, resultMatrix.m22, resultMatrix.m23,
      resultMatrix.m30, resultMatrix.m31, resultMatrix.m32, resultMatrix.m33
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
    Angle    angleZ      = orientation.getAngleZ ();
    double   cosAngle    = AngleMath.cos (angleZ);
    double   sinAngle    = AngleMath.sin (angleZ);
    
    matrixArray = new double[]
    {
      // First column  (m00, m10).
      cosAngle,
      sinAngle,
      // Second column (m01, m11).
      -sinAngle,
      cosAngle,
      // Third column  (m02, m12).
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
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = String.format
    (
      "Rotation(angleX=%s, angleY=%s, angleZ=%s)",
      orientation.getAngleX (),
      orientation.getAngleY (),
      orientation.getAngleZ ()
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initRotation
  (
    Angle         angleX,
    Angle         angleY,
    Angle         angleZ,
    RotationOrder rotationOrder
  )
  {
    this.orientation = new EulerAngles (angleX, angleY, angleZ);
    
    if (rotationOrder != null)
    {
      this.order = rotationOrder;
    }
    else
    {
      this.order = DefaultRotationOrder.XYZ;
    }
  }
}
