// 09.08.2015
// 
// Uses the Java 3D class javax.media.j3d.Transform3D to obtain the
// rotation matrix from a quaternion and then convert it via a self
// implemented matrix-to-Euler-angles class into Euler angles.


package ky.transform2;

import ky.angle.EulerAngles;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix3d;
import javax.vecmath.Quat4d;


public class Transform3DQuaternionToEulerAngles
implements   QuaternionToEulerAngles
{
  private AbstractMatrixToEulerAngles matrixToEulerAngles;
  
  
  public Transform3DQuaternionToEulerAngles ()
  {
    super ();
    this.matrixToEulerAngles = new SelmanMatrixToEulerAngles ();
  }
  
  
  @Override
  public EulerAngles getEulerAngles (Quat4d quaternion)
  {
    EulerAngles eulerAngles = null;
    Transform3D transform   = null;
    // Rotation matrix storing the quaternion.
    Matrix3d    eulerMatrix = null;
    
    // Save the quaternion to the transformation.
    transform = new Transform3D ();
    transform.set (quaternion);
    
    // Get the rotation matrix from the transformation.
    eulerMatrix = new Matrix3d ();
    transform.get (eulerMatrix);
    
    // Convert the rotation matrix to Euler angles.
    eulerAngles = matrixToEulerAngles.getEulerAngles (eulerMatrix);
    
    return eulerAngles;
  }
}
