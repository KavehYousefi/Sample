package ky.transform2;

import ky.Dimensions;
import ky.angle.EulerAngles;
import ky.Position;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;


/**
 * <div class="introClass">
 *   The <code>TransformationDecomposition</code> class extracts the
 *   single transformation parts from a complex transformation.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       TransformationDecomposition.java
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
 *       11.08.2015
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
 *     <td>11.08.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
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
    return Position.createFromTuple3d (translation.getAsVector3d ());
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
  
  public EulerAngles getEulerAngles ()
  {
    return rotation.getEulerAngles ();
  }
  
  public Shearing getShearing ()
  {
    return shearing;
  }
  
  
  public void decomposeTransform3D (Transform3D transform3D)
  {
    extractTranslation (transform3D);
    extractScaling     (transform3D);
    extractRotation    (transform3D);
    extractShearing    (transform3D);
  }
  
  public void decomposeAffineTransform (AffineTransform affineTransform)
  {
    this.translation = Translation.createFromAffineTransform (affineTransform);
    this.scaling     = Scaling.createFromAffineTransform     (affineTransform);
    this.rotation    = Rotation.createFromAffineTransform    (affineTransform);
    this.shearing    = Shearing.createFromAffineTransform    (affineTransform);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void extractTranslation (Transform3D transform3D)
  {
    this.translation = Translation.createFromTransform3D (transform3D);
  }
  
  private void extractScaling (Transform3D transform3D)
  {
    this.scaling = Scaling.createFromTransform3D (transform3D);
  }
  
  private void extractRotation (Transform3D transform3D)
  {
    this.rotation = Rotation.createFromTransform3D (transform3D);
  }
  
  private void extractShearing (Transform3D transform3D)
  {
    this.shearing = Shearing.createFromTransform3D (transform3D);
  }
}
