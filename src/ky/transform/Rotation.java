// 07-03-2014

package ky.transform;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix4d;

import ky.angle.Angle;
import ky.angle.AngleMath;


// -> "http://www.codeproject.com/Articles/42086/Space-and-Matrix-Transformations-Building-a-D-Eng"
// -> "http://rodrigo-silveira.com/3d-programming-transformation-matrix-tutorial/#.UxoAIs4xgR4"
// -> "http://www.euclideanspace.com/maths/algebra/matrix/resources/code/index.htm"
// -> "http://ken-soft.com/2008/12/25/graph3d-java-project-3d-points-to-2d/"
// -> "https://www.java.net/node/646711"
// -> "http://show.docjava.com/book/cgij/code/data/lectures/" -> "cr325" -> "transforms.ppt" und "j3d.ppt"
public class Rotation
implements   TransformObject
{
  private Orientation   orientation;
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
  public Rotation (Orientation orientation, RotationOrder order)
  {
    if (orientation != null)
    {
      this.orientation = orientation;
    }
    else
    {
      this.orientation = new Orientation ();
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
  public Rotation (Orientation orientation)
  {
    if (orientation != null)
    {
      this.orientation = orientation;
    }
    else
    {
      this.orientation = new Orientation ();
    }
    
    this.order = DefaultRotationOrder.XYZ;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation defined by three Euler angles and the
   *   rotation order.
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
   * @param order   The rotation order. <code>null</code> defaults to
   *                <code>DefaultRotationOrder.XYZ</code>.
   */
  public Rotation
  (
    Angle         angleX,
    Angle         angleY,
    Angle         angleZ,
    RotationOrder order
  )
  {
    initRotation (angleX, angleY, angleZ, order);
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
    initRotation (angleX, angleY, angleZ, null);
  }
  
  public Rotation (Angle angle, boolean useForAllAngles)
  {
    initRotation (null, null, null, null);
    
    orientation = new Orientation ();
    
    if (useForAllAngles)
    {
      orientation.setAllAnglesTo (angle);
    }
    else
    {
      orientation.setAngleZ (angle);
    }
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
    initRotation (new Angle (), new Angle (), angleZ, null);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a default rotation of zero degrees around each axis.
   * </div>
   */
  public Rotation ()
  {
    initRotation (null, null, null, null);
  }
  
  
  public Orientation getOrientation ()
  {
    return orientation;
  }
  
  public void setOrientation (Orientation orientation)
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
  
  
  public void setRotationXYZ (Angle angleX, Angle angleY, Angle angleZ)
  {
    orientation.setAngleX (angleX);
    orientation.setAngleY (angleY);
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
  
  public static Rotation createFromEulerAngles (Orientation eulerAngles)
  {
    Rotation rotation = new Rotation ();
    
    rotation = new Rotation (eulerAngles);
    
    return rotation;
  }
  
  public static Rotation createFromAngleX (Angle angleX)
  {
    Rotation rotation = null;
    
    rotation = new Rotation (angleX, new Angle (), new Angle ());
    
    return rotation;
  }
  
  public static Rotation createFromAngleY (Angle angleY)
  {
    Rotation rotation = null;
    
    rotation = new Rotation (new Angle (), angleY, new Angle ());
    
    return rotation;
  }
  
  public static Rotation createFromAngleZ (Angle angleZ)
  {
    Rotation rotation = null;
    
    rotation = new Rotation (new Angle (), new Angle (), angleZ);
    
    return rotation;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "TransformObject".          -- //
  //////////////////////////////////////////////////////////////////////
  
//  public double[] nenneAlsTransformMatrix3D ()
//  {
//    double[] matrixArray  = null;
//    double[] matrixArrayX = nenneRotationXAlsTransformMatrix3D ();
//    double[] matrixArrayY = nenneRotationYAlsTransformMatrix3D ();
//    double[] matrixArrayZ = nenneRotationZAlsTransformMatrix3D ();
//    Matrix4d matrixX      = new Matrix4d (matrixArrayX);
//    Matrix4d matrixY      = new Matrix4d (matrixArrayY);
//    Matrix4d matrixZ      = new Matrix4d (matrixArrayZ);
//    
//    matrixX.mul (matrixY);
//    matrixX.mul (matrixZ);
//    
//    matrixArray = new double[]
//    {
//      matrixX.m00, matrixX.m01, matrixX.m02, matrixX.m03,
//      matrixX.m10, matrixX.m11, matrixX.m12, matrixX.m13,
//      matrixX.m20, matrixX.m21, matrixX.m22, matrixX.m23,
//      matrixX.m30, matrixX.m31, matrixX.m32, matrixX.m33
//    };
//    
//    return matrixArray;
//  }
  
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
      "Rotation(angleX=%s; angleY=%s; angleZ=%s)",
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
    this.orientation = new Orientation (angleX, angleY, angleZ);
    
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