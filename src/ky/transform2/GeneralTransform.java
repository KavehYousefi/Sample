package ky.transform2;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;


// 14.03.2015
public class GeneralTransform
implements   TransformObject
{
  private Transform3D transform3D;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a general transformation based on a given
   *   <code>Transform3D</code> object.
   * </div>
   * 
   * @param transform3D  The <code>Transform3D</code> to represent.
   */
  public GeneralTransform (Transform3D transform3D)
  {
    this.transform3D = transform3D;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a general transformation based on a given
   *   <code>AffineTransform</code> object.
   * </div>
   * 
   * @param transform3D  The <code>AffineTransform</code> to represent.
   */
  public GeneralTransform (AffineTransform affineTransform)
  {
    this.transform3D = TransformUtils.affineTransformToTransform3D
    (
      affineTransform
    );
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a general transformation with identity transformation.
   * </div>
   */
  public GeneralTransform ()
  {
    this.transform3D = new Transform3D ();
  }
  
  
  public Transform3D getTransform3D ()
  {
    return transform3D;
  }
  
  public void setTransform3D (Transform3D transform3D)
  {
    this.transform3D = transform3D;
  }
  
  public AffineTransform getAffineTransform ()
  {
    AffineTransform affineTransform = null;
    
    affineTransform = TransformUtils.transform3DToAffineTransform
    (
      transform3D
    );
    
    return affineTransform;
  }
  
  public void setAffineTransform (AffineTransform affineTransform)
  {
    this.transform3D = TransformUtils.affineTransformToTransform3D
    (
      affineTransform
    );
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  public static GeneralTransform createFromTransform3D (Transform3D transform3D)
  {
    return new GeneralTransform (transform3D);
  }
  
  public static GeneralTransform createFromAffineTransform (AffineTransform affineTransform)
  {
    return new GeneralTransform (affineTransform);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "TransformObject".          -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public double[] getAsTransformMatrix3D ()
  {
    double[] asTransformMatrix = new double[16];
    
    transform3D.get (asTransformMatrix);
    
    return asTransformMatrix;
  }
  
  @Override
  public Transform3D getAsTransform3D ()
  {
    Transform3D asTransform3D = new Transform3D (transform3D);
    
    return asTransform3D;
  }
  
  @Override
  public double[] getAsTransformMatrix2D ()
  {
    double[] affineMatrix = null;
    
    affineMatrix = TransformUtils.transform3DToAffineTransformMatrix
    (
      transform3D
    );
    
    return affineMatrix;
  }
  
  @Override
  public AffineTransform getAsAffineTransform ()
  {
    AffineTransform affineTransform = null;
    
    affineTransform = TransformUtils.transform3DToAffineTransform
    (
      transform3D
    );
    
    return affineTransform;
  }
}