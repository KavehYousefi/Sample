// 09.08.2015

package ky.transform2;

import ky.angle.EulerAngles;

import javax.vecmath.Quat4d;


public interface QuaternionToEulerAngles
{
  /**
   * <div class="introMethod">
   *   Converts a quaternion to a set of Euler angles and returns it.
   * </div>
   * 
   * @param quaternion  The quaternion to convert.
   *                    It must not be <code>null</code>.
   * 
   * @return            An <code>Orientation</code> containing an
   *                    Euler angles representation of the quaternion.
   * 
   * @throws NullPointerException  If the <code>quaternion</code> is
   *                               <code>null</code>.
   */
  abstract public EulerAngles getEulerAngles (Quat4d quaternion);
}
