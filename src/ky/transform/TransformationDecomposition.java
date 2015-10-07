// 11.08.2015

package ky.transform;


import javax.media.j3d.Transform3D;

import ky.dimension.Dimensions;
import ky.Position;


public class TransformationDecomposition
{
  private Translation translation;
  private Scaling     scaling;
  private Rotation    rotation;
  private Shearing    shearing;
  
  
  public TransformationDecomposition ()
  {
    this.translation = null;
    this.scaling     = null;
    this.rotation    = null;
    this.shearing    = null;
  }
  
  
  public Translation getTranslation ()
  {
    return translation;
  }
  
  public Position getPosition ()
  {
    return translation.getAsPosition ();
  }
  
  public Scaling getScaling ()
  {
    return scaling;
  }
  
  public Dimensions getDimensions ()
  {
    return scaling.getAsDimensions ();
  }
  
  public Rotation getRotation ()
  {
    return rotation;
  }
  
  public Orientation getEulerAngles ()
  {
    return rotation.getOrientation ();
  }
  
  public Shearing getShearing ()
  {
    return shearing;
  }
  
  
  public void decompose (Transform3D transform3D)
  {
    extractTranslation (transform3D);
    extractScaling     (transform3D);
//    extractRotation    (transform3D);
    extractShearing    (transform3D);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  /**
   * <div class="introMethod">
   *   Extracts the translation component from a
   *   <code>Transform3D</code>.
   * </div>
   * 
   * <div>
   *   Following source was used:
   *   <ul>
   *     <li>
   *       <a href="http://stackoverflow.com/questions/9239019/how-to-find-the-position-of-objects-in-java-3d">
   *         "http://stackoverflow.com/questions/9239019/how-to-find-the-position-of-objects-in-java-3d"
   *       </a>
   *       <br />
   *       Note that the code describes how to obtain the translation as
   *       <code>Point3d</code>.
   *       <br />
   *       Date: 10.03.2013
   *     </li>
   *   </ul>
   * </div>
   * 
   * @param transform3D  The non-<code>null</code> transformation.
   * 
   * @throws NullPointerException  If <code>transform3D</code>
   *                               equals <code>null</code>.
   */
  private void extractTranslation (Transform3D transform3D)
  {
//    Vector3d translationVector = null;
//    
//    translationVector = new Vector3d ();
//    transform3D.get                  (translationVector);
//    this.translation = Translation.createFromTuple3d (translationVector);
    
    this.translation = Translation.createFromTransform3D (transform3D);
  }
  
  private void extractScaling (Transform3D transform3D)
  {
//    Vector3d scalingFactors = null;
//    
//    scalingFactors = new Vector3d            ();
//    transform3D.getScale                     (scalingFactors);
//    this.scaling = Scaling.createFromTuple3d (scalingFactors);
    
    this.scaling = Scaling.createFromTransform3D (transform3D);
  }
  
//  private void extractRotation (Transform3D transform3D)
//  {
//    Quat4d                  quaternion             = null;
//    Orientation             eulerAngles            = null;
//    QuaternionToEulerAngles quatToEulerTransformer = null;
//    
//    quaternion             = new Quat4d ();
//    quatToEulerTransformer = new Transform3DQuaternionToEulerAngles ();
//    transform3D.get (quaternion);
//    eulerAngles   = quatToEulerTransformer.getEulerAngles (quaternion);
//    this.rotation = new Rotation (eulerAngles);
//  }
  
  private void extractShearing (Transform3D transform3D)
  {
//    Matrix3d transformMatrix = null;
//    
//    transformMatrix = new Matrix3d              ();
//    transform3D.get                             (transformMatrix);
//    this.shearing = Shearing.createFromMatrix3d (transformMatrix);
    
    this.shearing = Shearing.createFromTransform3D (transform3D);
  }
}
