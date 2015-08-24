// 21.03.2014

package ky.transform;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;


public interface TransformObject
{
  /**
   * <div class="introMethod">
   *   Returns a one-dimensional array, which represents the 3D
   *   transformation matrix in row-major order, being for example
   *   suitable for use in a <code>Transform3D</code> object.
   * </div>
   * 
   * @return  The 3D transformation matrix as one-dimensional array in
   *          row-major order.
   */
  abstract public double[]    getAsTransformMatrix3D ();
  
  /**
   * <div class="introMethod">
   *   Returns the 3D transformation as a <code>Transform3D</code>
   *   object.
   * </div>
   * 
   * @return  The 3D transformation as a <code>Transform3D</code>.
   */
  abstract public Transform3D getAsTransform3D       ();
  
  
  /**
   * <div class="introMethod">
   *   Returns a one-dimensional array, which represents the 2D
   *   transformation matrix in column-major order, being, for example,
   *   suitable for use in an <code>AffineTransform</code> object.
   * </div>
   * 
   * @return  The 2D transformation matrix as one-dimensional array in
   *          column-major order.
   */
  abstract public double[]        getAsTransformMatrix2D ();
  
  /**
   * <div class="introMethod">
   *   Returns the 2D transformation as an <code>AffineTransform</code>
   *   object.
   * </div>
   * 
   * @return  The 2D transformation as an <code>AffineTransform</code>.
   */
  abstract public AffineTransform getAsAffineTransform   ();
}