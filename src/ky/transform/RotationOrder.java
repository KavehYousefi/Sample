package ky.transform;

import javax.vecmath.Matrix4d;


public interface RotationOrder
{
  /**
   * <div class="introMethod">
   *   Calculates the result matrix from three rotation matrices
   *   which represent the rotation around the <i>x</i>-, <i>y</i>-
   *   and <i>z</i>-axis.
   * </div>
   * 
   * @param matrixX  The rotation matrix for the rotation around the
   *                 <i>x</i>-axis.
   * @param matrixY  The rotation matrix for the rotation around the
   *                 <i>y</i>-axis.
   * @param matrixZ  The rotation matrix for the rotation around the
   *                 <i>z</i>-axis.
   * 
   * @return         The calculated rotation matrix for an
   *                 <i>x</i>, <i>y</i> and <i>z</i> rotation.
   */
  abstract public Matrix4d multiplyMatrices
  (
    Matrix4d matrixX,
    Matrix4d matrixY,
    Matrix4d matrixZ
  );
}