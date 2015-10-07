// 07-03-2014

package ky.transform2;

import javax.vecmath.Matrix4d;


public enum DefaultRotationOrder
implements  RotationOrder
{
  /**
   * <div class="introEnumInstance">
   *   Represents rotations along the <i>x</i>-, <i>y</i>- and
   *   <i>z</i>-axis in this order.
   * </div>
   */
  XYZ
  {
    @Override
    public Matrix4d multiplyMatrices
    (
      Matrix4d matrixX,
      Matrix4d matrixY,
      Matrix4d matrixZ
    )
    {
      Matrix4d ergebnisMatrix = new Matrix4d (matrixZ);
      
      ergebnisMatrix.mul (matrixY);
      ergebnisMatrix.mul (matrixX);
      
      return ergebnisMatrix;
    }
  },
  
  /**
   * <div class="introEnumInstance">
   *   Represents rotations along the <i>x</i>-, <i>z</i>- and
   *   <i>y</i>-axis in this order.
   * </div>
   */
  XZY
  {
    @Override
    public Matrix4d multiplyMatrices
    (
      Matrix4d matrixX,
      Matrix4d matrixY,
      Matrix4d matrixZ
    )
    {
      Matrix4d ergebnisMatrix = new Matrix4d (matrixY);
      
      ergebnisMatrix.mul (matrixZ);
      ergebnisMatrix.mul (matrixX);
      
      return ergebnisMatrix;
    }
  },
  
  /**
   * <div class="introEnumInstance">
   *   Represents rotations along the <i>y</i>-, <i>x</i>- and
   *   <i>z</i>-axis in this order.
   * </div>
   */
  YXZ
  {
    @Override
    public Matrix4d multiplyMatrices
    (
      Matrix4d matrixX,
      Matrix4d matrixY,
      Matrix4d matrixZ
    )
    {
      Matrix4d ergebnisMatrix = new Matrix4d (matrixZ);
      
      ergebnisMatrix.mul (matrixX);
      ergebnisMatrix.mul (matrixY);
      
      return ergebnisMatrix;
    }
  },
  
  /**
   * <div class="introEnumInstance">
   *   Represents rotations along the <i>y</i>-, <i>z</i>- and
   *   <i>x</i>-axis in this order.
   * </div>
   */
  YZX
  {
    @Override
    public Matrix4d multiplyMatrices
    (
      Matrix4d matrixX,
      Matrix4d matrixY,
      Matrix4d matrixZ
    )
    {
      Matrix4d ergebnisMatrix = new Matrix4d (matrixX);
      
      ergebnisMatrix.mul (matrixZ);
      ergebnisMatrix.mul (matrixY);
      
      return ergebnisMatrix;
    }
  },
  
  /**
   * <div class="introEnumInstance">
   *   Represents rotations along the <i>z</i>-, <i>x</i>- and
   *   <i>y</i>-axis in this order.
   * </div>
   */
  ZXY
  {
    @Override
    public Matrix4d multiplyMatrices
    (
      Matrix4d matrixX,
      Matrix4d matrixY,
      Matrix4d matrixZ
    )
    {
      Matrix4d ergebnisMatrix = new Matrix4d (matrixY);
      
      ergebnisMatrix.mul (matrixX);
      ergebnisMatrix.mul (matrixZ);
      
      return ergebnisMatrix;
    }
  },
  
  /**
   * <div class="introEnumInstance">
   *   Represents rotations along the <i>z</i>-, <i>y</i>- and
   *   <i>x</i>-axis in this order.
   * </div>
   */
  ZYX
  {
    @Override
    public Matrix4d multiplyMatrices
    (
      Matrix4d matrixX,
      Matrix4d matrixY,
      Matrix4d matrixZ
    )
    {
      Matrix4d ergebnisMatrix = new Matrix4d (matrixX);
      
      ergebnisMatrix.mul (matrixY);
      ergebnisMatrix.mul (matrixZ);
      
      return ergebnisMatrix;
    }
  };
  
  
  /**
   * <div class="introConstructor">
   *   Defines a rotation order.
   * </div>
   */
  private DefaultRotationOrder ()
  {
  }
}