package ky.transform;

import commies.Position;
import commies.angle.Angle;
import safercode.CheckingUtils;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector3d;

import ky.dimension.Dimensions;


/* !!!!!!!!!!!!!!
 * !!!? TODO ?!!!
 * !!!!!!!!!!!!!!
 * 
 * # Combines transform objects in range to ONE transform object.
 * public void combine (int fromIndex, int toIndex) {...}
 * 
 * # Returns list of Transform3D objects, each for ONE TransformObject.
 * # Analoguous: List<TransformGroup> getSingleTransformGroups () {...}
 * public List<Transform3D> getSingleTransform3Ds () {...}
 * 
 * # Successive combination of TransformObjects ("reduce"):
 *   Let transforms = [t1, t2, t3],
 *   thus we get a new list of three TransformObjects:
 *     [t1, t1 * t2, t1 * t2 * t3]
 */
public class TransformBuilder
{
  private List<TransformObject> transforms;
  
  
  /**
   * <div class="introcConstructor">
   *   Creates a <code>TransformBuilder</code> by copying an existing
   *   one.
   * </div>
   * 
   * @param builderToCopy  The tranform builder to copy. Leaving it
   *                       to <code>null</code> is allowed and results
   *                       in an empty instance being created.
   */
  public TransformBuilder (TransformBuilder builderToCopy)
  {
    CheckingUtils.checkForNull
    (
      builderToCopy,
      "TransformBuilder to copy is null."
    );
    
    this.transforms = new ArrayList<TransformObject> ();
    
    this.transforms.addAll (builderToCopy.transforms);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates an empty transformation builder.
   * </div>
   */
  public TransformBuilder ()
  {
    this.transforms = new ArrayList<TransformObject> ();
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns a list of all transforms.
   * </div>
   * 
   * @return  A list of all transforms.
   */
  public List<TransformObject> getTransformObjects ()
  {
    return Collections.unmodifiableList (transforms);
  }
  
  public List<TransformObject> getTransformObjectsFromTo
  (
    int fromIndex,
    int toIndex
  )
  {
    List<TransformObject> sublistTransforms = null;
    
    sublistTransforms = transforms.subList (fromIndex, toIndex);
    
    return sublistTransforms;
  }
  
  public TransformObject getTransformObjectAtIndex (int index)
  {
    return transforms.get (index);
  }
  
  
  /**
   * <div class="introMethod">
   *   Translates by the given three-dimensional coordinates.
   * </div>
   * 
   * @param translateX  The translation along the <i>x</i>-axis.
   * @param translateY  The translation along the <i>y</i>-axis.
   * @param translateZ  The translation along the <i>z</i>-axis.
   * 
   * @return            The generated <code>Translation</code> object.
   */
  public Translation translate
  (
    double translateX,
    double translateY,
    double translateZ
  )
  {
    Translation translation = null;
    
    translation = new Translation (translateX, translateY, translateZ);
    transforms.add (translation);
    
    return translation;
  }
  
  public Translation translate (Position position)
  {
    Translation translation = null;
    
    translation = new Translation (position);
    transforms.add (translation);
    
    return translation;
  }
  
  public Translation translateX (double translateX)
  {
    Translation translation = null;
    
    translation = translate (translateX, 0.0, 0.0);
    
    return translation;
  }
  
  public Translation translateY (double translateY)
  {
    Translation translation = null;
    
    translation = translate (0.0, translateY, 0.0);
    
    return translation;
  }
  
  public Translation translateZ (double translateZ)
  {
    Translation translation = null;
    
    translation = translate (0.0, 0.0, translateZ);
    
    return translation;
  }
  
  /**
   * <div class="introMethod">
   *   Adds three rotations, each around one axis.
   * </div>
   * 
   * @param angleX  The rotation angle along the <i>x</i>-axis.
   *                It may not be <code>null</code>.
   * @param angleY  The rotation angle along the <i>y</i>-axis.
   *                It may not be <code>null</code>.
   * @param angleZ  The rotation angle along the <i>z</i>-axis.
   *                It may not be <code>null</code>.
   * @return        The generated <code>Rotation</code> object.
   * @throws NullPointerException  If given <code>null</code> for an
   *                               argument.
   */
  public Rotation rotate (Angle angleX, Angle angleY, Angle angleZ)
  {
    Rotation rotation = new Rotation (angleX, angleY, angleZ);
    
    transforms.add (rotation);
    
    return rotation;
  }
  
  /**
   * <div class="introMethod">
   *   Adds a rotation along the <i>x</i>-axis.
   * </div>
   * 
   * @param angleX  The rotation angle along the <i>x</i>-axis.
   *                It may not be <code>null</code>.
   * 
   * @return        The generated <code>Rotation</code> object.
   * 
   * @throws NullPointerException  If given <code>null</code> for the
   *                               argument <code>angleX</code>.
   */
  public Rotation rotateX (Angle angleX)
  {
    Rotation rotation = new Rotation ();
    
    rotation.setRotationX (angleX);
    transforms.add        (rotation);
    
    return rotation;
  }
  
  /**
   * <div class="introMethod">
   *   Adds a rotation along the <i>y</i>-axis.
   * </div>
   * 
   * @param angleY  The rotation angle along the <i>y</i>-axis.
   *                It may not be <code>null</code>.
   * @return        The generated <code>Rotation</code> object.
   * @throws NullPointerException  If given <code>null</code> for the
   *                               argument <code>angleY</code>.
   */
  public Rotation rotateY (Angle angleY)
  {
    Rotation rotation = new Rotation ();
    
    rotation.setRotationY (angleY);
    transforms.add        (rotation);
    
    return rotation;
  }
  
  /**
   * <div class="introMethod">
   *   Adds a rotation along the <i>z</i>-axis.
   * </div>
   * 
   * @param angleZ  The rotation angle along the <i>z</i>-axis.
   *                It may not be <code>null</code>.
   * @return        The generated <code>Rotation</code> object.
   * @throws NullPointerException  If given <code>null</code> for the
   *                               argument <code>angleZ</code>.
   */
  public Rotation rotateZ (Angle angleZ)
  {
    Rotation rotation = new Rotation (angleZ);
    
    transforms.add (rotation);
    
    return rotation;
  }
  
  /**
   * <div class="introMethod">
   *   Scales by the <i>x</i>-, <i>y</i>- and <i>z</i> scaling factors.
   * </div>
   * 
   * @param scaleX  The scaling along the <i>x</i>-axis.
   * @param scaleY  The scaling along the <i>y</i>-axis.
   * @param scaleZ  The scaling along the <i>z</i>-axis.
   * @return        The generated <code>Scaling</code> object.
   */
  public Scaling scale (double scaleX, double scaleY, double scaleZ)
  {
    Scaling scaling = new Scaling (scaleX, scaleY, scaleZ);
    
    transforms.add (scaling);
    
    return scaling;
  }
  
  public Scaling scale (Dimensions dimensions)
  {
    Scaling scaling = Scaling.createFromDimensions (dimensions);
    
    transforms.add (scaling);
    
    return scaling;
  }
  
  public Scaling scale (Vector3d scaleFactors)
  {
    Scaling scaling = Scaling.createFromTuple3d (scaleFactors);
    
    transforms.add (scaling);
    
    return scaling;
  }
  
  public Scaling scale (Vector2d scaleFactors2D)
  {
    Scaling scaling = Scaling.createFromTuple2d (scaleFactors2D);
    
    transforms.add (scaling);
    
    return scaling;
  }
  
  /**
   * <div class="introMethod">
   *   Adds a uniform scaling by a given factor.
   * </div>
   * 
   * @param scaleFactor  The uniform scaling factor.
   * @return             The generated <code>Scaling</code> object.
   */
  public Scaling scale (double scaleFactor)
  {
    Scaling scaling = new Scaling (scaleFactor);
    
    transforms.add (scaling);
    
    return scaling;
  }
  
  public Shearing shear
  (
    double factorXY,
    double factorXZ,
    double factorYX,
    double factorYZ,
    double factorZX,
    double factorZY
  )
  {
    Shearing shearing = null;
    
    shearing = new Shearing
    (
      factorXY,
      factorXZ,
      factorYX,
      factorYZ,
      factorZX,
      factorZY
    );
    
    transforms.add (shearing);
    
    return shearing;
  }
  
  public Shearing shear (ShearParameter shearParameter, double factor)
  {
    Shearing shearing = new Shearing ();
    
    shearing.setShearFactor (shearParameter, factor);
    transforms.add          (shearing);
    
    return shearing;
  }
  
  public Shearing shearXY (double factorXY)
  {
    Shearing shearing = new Shearing ();
    
    shearing.setShearFactor (ShearParameter.SHEARING_XY, factorXY);
    transforms.add          (shearing);
    
    return shearing;
  }
  
  public Shearing shearXZ (double factorXZ)
  {
    Shearing shearing = new Shearing ();
    
    shearing.setShearFactor (ShearParameter.SHEARING_XZ, factorXZ);
    transforms.add          (shearing);
    
    return shearing;
  }
  
  public Shearing shearYX (double factorYX)
  {
    Shearing shearing = new Shearing ();
    
    shearing.setShearFactor (ShearParameter.SHEARING_YX, factorYX);
    transforms.add          (shearing);
    
    return shearing;
  }
  
  public Shearing shearYZ (double factorYZ)
  {
    Shearing shearing = new Shearing ();
    
    shearing.setShearFactor (ShearParameter.SHEARING_YZ, factorYZ);
    transforms.add          (shearing);
    
    return shearing;
  }
  
  
  /**
   * <div class="introMethod">
   *   Adds a transform object.
   * </div>
   * 
   * @param transformObject  The transform to be added. It may not be
   *                         <code>null</code>.
   * 
   * @throws NullPointerException  If <code>transformObject</code>
   *                               equals <code>null</code>.
   */
  public void addTransform (TransformObject transformObject)
  {
    checkTransformObject (transformObject);
    transforms.add       (transformObject);
  }
  
  public void addTransformAtStart (TransformObject transformObject)
  {
    checkTransformObject (transformObject);
    transforms.add       (0, transformObject);
  }
  
  public void addTransformAtIndex (TransformObject transformObject, int index)
  {
    checkTransformObject (transformObject);
    transforms.add       (index, transformObject);
  }
  
  
  
  /**
   * <div class="introMethod">
   *   Reverses the transformations' order.
   * </div>
   */
  public void reverse ()
  {
    Collections.reverse (transforms);
  }
  
  /**
   * <div class="introMethod">
   *   Swaps two transformations' positions by their index.
   * </div>
   * 
   * @param firstIndex   The first transform's index. It must be in
   *                     [0, <i>numberOfTransforms</i>).
   * @param secondIndex  The second transform's index. It must be in
   *                     [0, <i>numberOfTransforms</i>).
   * 
   * @throws IndexOutOfBoundsException  If the indices are not in the
   *                                    range
   *                                    [0, <i>numberOfTransforms</i>).
   */
  public void swap (int firstIndex, int secondIndex)
  {
    TransformObject firstTransform  = null;
    TransformObject secondTransform = null;
    
    firstTransform  = transforms.get (firstIndex);
    secondTransform = transforms.get (secondIndex);
    
    transforms.set (firstIndex,  secondTransform);
    transforms.set (secondIndex, firstTransform);
  }
  
  /**
   * <div class="introMethod">
   *   Removes the last transform of the transformation list.
   * </div>
   * 
   * @throws EmptyTransformBuilderException  If the transformation
   *                                         builder is empty.
   */
  public void removeLast ()
  {
    checkForEmptyTransformBuilder ();
    
    int lastIndex = 0;
    
    lastIndex = (transforms.size () - 1);
    transforms.remove (lastIndex);
  }
  
  /**
   * <div class="introMethod">
   *   Removes a transformation at a given index and returns it.
   * </div>
   * 
   * @param index  The index of the transformation to be removed. It
   *               must be in the range [0, <i>numberOfTransforms</i>).
   * 
   * @throws IndexOutOfBoundsException  If the <code>index</code> is
   *                                    outside of the range
   *                                    [0, <i>numberOfTransforms</i>).
   */
  public TransformObject removeTransformObjectAtIndex (int index)
  {
    return transforms.remove (index);
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns a <code>Transform3D</code> object as result of all
   *   transforms combined (multiplied) in the given order.
   * </div>
   * 
   * @return  A <code>Transform3D</code> of all transforms combined
   *          (multiplied).
   */
  public Transform3D buildCombinedTransform3D ()
  {
    Transform3D           combinedTransform3D = null;
    List<TransformObject> reverseTransforms   = null;
    
    combinedTransform3D = new Transform3D ();
    reverseTransforms   = new ArrayList<TransformObject> ();
    
    reverseTransforms.addAll (transforms);
    Collections.reverse      (reverseTransforms);
    
    for (TransformObject transformObject : reverseTransforms)
    {
      Transform3D transform3D = transformObject.getAsTransform3D ();
      
      combinedTransform3D.mul (transform3D);
    }
    
    return combinedTransform3D;
  }
  
  public Transform3D buildCombinedTransform3D
  (
    int fromIndex,
    int toIndex
  )
  {
    Transform3D           combinedTransform3D = null;
    List<TransformObject> sublistTransforms   = null;
    
    combinedTransform3D = new Transform3D           ();
    sublistTransforms   = getTransformObjectsFromTo (fromIndex, toIndex);
    
    Collections.reverse (sublistTransforms);
    
    for (TransformObject transformObject : sublistTransforms)
    {
      Transform3D transform3D = transformObject.getAsTransform3D ();
      
      combinedTransform3D.mul (transform3D);
    }
    
    return combinedTransform3D;
  }
  
  public TransformGroup buildCombinedTransformGroup ()
  {
    TransformGroup transformGroup    = new TransformGroup       ();
    Transform3D    combinedTransform = buildCombinedTransform3D ();
    
    transformGroup.setTransform (combinedTransform);
    
    return transformGroup;
  }
  
  public TransformGroup buildCombinedTransformGroup
  (
    int fromIndex,
    int toIndex
  )
  {
    TransformGroup transformGroup    = new TransformGroup       ();
    Transform3D    combinedTransform = buildCombinedTransform3D (fromIndex, toIndex);
    
    transformGroup.setTransform (combinedTransform);
    
    return transformGroup;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns an <code>AffineTransform</code> object as result of all
   *   transforms combined (multiplied) in the given order.
   * </div>
   * 
   * <div>
   *   Note that information about three-dimensional transformations
   *   will be lost in the resulting <code>AffineTransform</code>
   *   object.
   * </div>
   * 
   * @return  An <code>AffineTransform</code> of all transforms combined
   *          (multiplied).
   */
  public AffineTransform buildCombinedAffineTransform ()
  {
    AffineTransform       combinedTransform = new AffineTransform ();
    List<TransformObject> reverseTransforms = null;
    
    reverseTransforms = new ArrayList<TransformObject> ();
    
    reverseTransforms.addAll (transforms);
    Collections.reverse      (reverseTransforms);
    
    for (TransformObject transformObject : reverseTransforms)
    {
      AffineTransform affineTransform = null;
      
      affineTransform = transformObject.getAsAffineTransform ();
      combinedTransform.concatenate (affineTransform);
    }
    
    return combinedTransform;
  }
  
  public AffineTransform buildCombinedAffineTransform
  (
    int fromIndex,
    int toIndex
  )
  {
    AffineTransform       combinedTransform = null;
    List<TransformObject> sublistTransforms = null;
    
    combinedTransform = new AffineTransform       ();
    sublistTransforms = getTransformObjectsFromTo (fromIndex, toIndex);
    
    Collections.reverse (sublistTransforms);
    
    for (TransformObject transformObject : sublistTransforms)
    {
      AffineTransform affineTransform = null;
      
      affineTransform = transformObject.getAsAffineTransform ();
      combinedTransform.concatenate (affineTransform);
    }
    
    return combinedTransform;
  }
  
  
  /**
   * <div class="introMethod">
   *   Sets the object to its original state by removing all transforms.
   * </div>
   */
  public void reset ()
  {
    transforms.clear ();
  }
  
  
  
  private static void checkTransformObject (TransformObject transformObject)
  {
    CheckingUtils.checkForNull
    (
      transformObject,
      "No transform object given."
    );
  }
  
  private void checkForEmptyTransformBuilder ()
  {
    if (transforms.isEmpty ())
    {
      throw new EmptyTransformBuilderException
      (
        "Tried to remove last element of an empty TransformBuilder."
      );
    }
  }
}