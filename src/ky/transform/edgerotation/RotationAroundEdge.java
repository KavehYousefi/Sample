package ky.transform.edgerotation;

import safercode.CheckingUtils;

import javax.media.j3d.Transform3D;
import javax.vecmath.AxisAngle4d;

import ky.transform2.TransformBuilder;
import ky.transform2.Translation;


/**
 * <div class="introClass">
 *   Implements a rotation (and translation) around a rotation edge.
 * </div>
 * 
 * <div>
 *   The principle is the following:
 *   <ol>
 *     <li>
 *       Translate the line segment midpoint to the origin to prepare
 *       the rotation.
 *     </li>
 *     <li>
 *       Define an <code>AxisAngle4d</code> from the vector between
 *       line start and end point and the rotation angle.
 *     </li>
 *     <li>
 *       Rotate around the <code>AxisAngle4d</code>.
 *     </li>
 *     <li>
 *       Translate the line segment midpoint to its original
 *       coordinates.
 *     </li>
 *   </ol>
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       RotationAroundEdge.java
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
 *       24.05.2015
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
 *     <td>24.05.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class RotationAroundEdge
{
  private RotationEdge rotationEdge;
  
  
  /**
   * <div class="introConstructor">
   *   Defines a rotation around the given edge.
   * </div>
   * 
   * @param rotationEdge  The non-<code>null</code> edge to rotate
   *                      around.
   * 
   * @throws NullPointerException  If the <code>rotationEdge</code>
   *                               equals <code>null</code>.
   */
  public RotationAroundEdge (RotationEdge rotationEdge)
  {
    checkRotationEdge (rotationEdge);
    this.rotationEdge = rotationEdge;
  }
  
  /**
   * <div class="introConstructor">
   *   Defines a rotation around a default rotation edge.
   * </div>
   */
  public RotationAroundEdge ()
  {
    this.rotationEdge = new RotationEdge ();
  }
  
  
  /**
   * <div class="introConstructor">
   *   Returns the rotation edge.
   * </div>
   * 
   * @return  The rotation edge.
   */
  public RotationEdge getRotationEdge ()
  {
    return rotationEdge;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the edge to rotate around.
   * </div>
   * 
   * @param rotationEdge  The non-<code>null</code> edge to rotate
   *                      around.
   * 
   * @throws NullPointerException  If the <code>rotationEdge</code>
   *                               equals <code>null</code>.
   */
  public void setRotationEdge (RotationEdge rotationEdge)
  {
    checkRotationEdge (rotationEdge);
    this.rotationEdge = rotationEdge;
  }
  
  
  public Translation getTranslationToOrigin ()
  {
    return Translation.createFromPosition (rotationEdge.getEdge ().getMidpoint ().getNegatedPosition ());
  }
  
  public Transform3D getRotation ()
  {
    Transform3D rotation  = null;
    AxisAngle4d axisAngle = null;
    
    rotation  = new Transform3D               ();
    axisAngle = rotationEdge.getAsAxisAngle4d ();
    
    rotation.setRotation (axisAngle);
    
    return rotation;
  }
  
  public Translation getTranslationToStart ()
  {
    return Translation.createFromPosition (rotationEdge.getEdge ().getMidpoint ());
  }
  
  public TransformBuilder getCompoundTransformBuilder ()
  {
    TransformBuilder transformBuilder = null;
    
    transformBuilder = new TransformBuilder ();
    transformBuilder.addTransform   (getTranslationToOrigin ());
    transformBuilder.addTransform3D (getRotation            ());
    transformBuilder.addTransform   (getTranslationToStart  ());
    
    return transformBuilder;
  }
  
  /**
   * <div class="introMethod">
   *   Creates the composite 3D-transformation for a rotation around
   *   an edge (line).
   * </div>
   * 
   * <div>
   *   The transformation will be a concatenation of following
   *   single transformations:
   *   <ul>
   *     <li>
   *       A translation of the edge's midpoint to the coordinate
   *       system origin.
   *     </li>
   *     <li>
   *       A rotation by the given angle around the origin.
   *     </li>
   *     <li>
   *       A translation of the edge's midpoint to its original
   *       position.
   *     </li>
   *   </ul>
   * </div>
   * 
   * @return  The 3D-transformation for rotating around the
   *          rotation edge.
   */
  public Transform3D getCompoundTransform3D ()
  {
    Transform3D      transformation   = null;
    TransformBuilder transformBuilder = null;
    
    transformBuilder = getCompoundTransformBuilder               ();
    transformation   = transformBuilder.buildCombinedTransform3D ();
    
    return transformation;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static void checkRotationEdge (RotationEdge rotationEdge)
  {
    CheckingUtils.checkForNull
    (
      rotationEdge,
      "Rotation edge is null."
    );
  }
}