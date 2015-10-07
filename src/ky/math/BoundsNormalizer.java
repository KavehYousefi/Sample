package ky.math;

import ky.Dimensions;
import ky.Position;
import ky.dimensionsmatching.DimensionsMatching;
import ky.dimensionsmatching.ScaleUniformlyToFitInside;
import ky.transform2.Scaling;
import ky.transform2.TransformBuilder;
import ky.transform2.Translation;
import safercode.CheckingUtils;
import ky.Cuboid;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;


/**
 * <div class="introClass">
 *   The <code>BoundsNormalizer</code> calculates the transformations
 *   that is, translations and scaling, necessary to match the source
 *   bounds to desired destination bounds.
 * </div>
 * 
 * <div>
 *   The transformations involved in this matching operation are
 *   usually the following three in the given order:
 *   <ol>
 *     <li>
 *       <strong>Translation</strong> of the source bounds' center to
 *       the origin.
 *     </li>
 *     <li>
 *       <strong>Scaling</strong> of the source bounds' dimensions to
 *       match the destination bounds' dimensions.
 *     </li>
 *     <li>
 *       <strong>Translation</strong> of the source bounds' center to
 *       the destination bounds' center.
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
 *       BoundsNormalizer.java
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
 *       01.09.2015
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
 *     <td>01.09.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class BoundsNormalizer
{
  // Defines center and dimensions of destination bounds.
  private Cuboid             destinationBounds;
  private DimensionsMatching dimensionsMatching;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a bounds normalizer based on the given destination
   *   bounds and dimensions matching.
   * </div>
   * 
   * @param dimensionsMatching  The dimensions matcher for calculating
   *                            the necessary scaling.
   *                            It must not be <code>null</code>.
   * @param destinationBounds   The destination bounds cuboid.
   *                            It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If the
   *                               <code>dimensionsMatching</code>
   *                               equals <code>null</code>.
   * @throws NullPointerException  If the
   *                               <code>destinationBounds</code>
   *                               equal <code>null</code>.
   */
  public BoundsNormalizer
  (
    DimensionsMatching dimensionsMatching,
    Cuboid             destinationBounds
  )
  {
    checkDestinationBounds  (destinationBounds);
    checkDimensionsMatching (dimensionsMatching);
    
    this.destinationBounds  = destinationBounds;
    this.dimensionsMatching = dimensionsMatching;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a bounds normalizer based on a unit cube in the origin as
   *   destination bounds and a proportional dimensions matching
   * </div>
   */
  public BoundsNormalizer ()
  {
    this.destinationBounds  = Cuboid.createUnitCubeAtOrigin ();
    this.dimensionsMatching = new DimensionsMatching (new ScaleUniformlyToFitInside ());
  }
  
  
  public Cuboid getDestinationBounds ()
  {
    return destinationBounds;
  }
  
  public void setDestinationBounds (Cuboid destinationBounds)
  {
    checkDestinationBounds (destinationBounds);
    this.destinationBounds = destinationBounds;
  }
  
  public DimensionsMatching getDimensionsMatching ()
  {
    return dimensionsMatching;
  }
  
  public void setDimensionsMatching (DimensionsMatching dimensionsMatching)
  {
    checkDimensionsMatching (dimensionsMatching);
    this.dimensionsMatching = dimensionsMatching;
  }
  
  
  public Translation getTranslationToOrigin (Cuboid sourceBounds)
  {
    checkSourceBounds (sourceBounds);
    
    Position sourceCenter = sourceBounds.getPosition ();
    
    return Translation.createFromPosition (sourceCenter.getNegatedPosition ());
  }
  
  public Scaling getScaling (Cuboid sourceBounds)
  {
    checkSourceBounds (sourceBounds);
    
    Scaling    scaling               = null;
    Dimensions sourceDimensions      = null;
    Dimensions destinationDimensions = null;
    
    sourceDimensions      = sourceBounds.getDimensions      ();
    destinationDimensions = destinationBounds.getDimensions ();
    scaling               = dimensionsMatching.getScalingToMatchSourceToDestinationDimensions
    (
      sourceDimensions,
      destinationDimensions
    );
    
    return scaling;
  }
  
  public Translation getTranslationToDestination ()
  {
    return Translation.createFromPosition (destinationBounds.getPosition ());
  }
  
  /**
   * <div class="introMethod">
   *   Returns a <code>TransformBuilder</code> containing the
   *   transformations to match the source to the destination bounds.
   * </div>
   * 
   * @param sourceBounds  The source bounds cuboid.
   *                      It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>sourceBounds</code>
   *                               equal <code>null</code>.
   */
  public TransformBuilder getTotalTransformBuilder (Cuboid sourceBounds)
  {
    checkSourceBounds (sourceBounds);
    
    TransformBuilder transformBuilder         = null;
    Translation      translationToOrigin      = null;
    Scaling          scaling                  = null;
    Translation      translationToDestination = null;
    
    translationToOrigin      = getTranslationToOrigin (sourceBounds);
    scaling                  = getScaling             (sourceBounds);
    translationToDestination = getTranslationToDestination ();
    transformBuilder         = new TransformBuilder        ();
    
    transformBuilder.addTransform (translationToOrigin);
    transformBuilder.addTransform (scaling);
    transformBuilder.addTransform (translationToDestination);
    
    return transformBuilder;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the <code>Transform3D</code> representing the
   *   transformations to match the source to the destination bounds.
   * </div>
   * 
   * @param sourceBounds  The source bounds cuboid.
   *                      It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>sourceBounds</code>
   *                               equal <code>null</code>.
   */
  public Transform3D getNormalizingTransform3D (Cuboid sourceBounds)
  {
    checkSourceBounds (sourceBounds);
    
    Transform3D      normalizingTransform  = null;
    TransformBuilder totalTransformBuilder = null;
    
    totalTransformBuilder = getTotalTransformBuilder (sourceBounds);
    normalizingTransform  = totalTransformBuilder.buildCombinedTransform3D ();
    
    return normalizingTransform;
  }
  
  /**
   * <div class="introMethod">
   *   Returns an empty <code>TransformGroup</code> containing the
   *   transformations to match the source to the destination bounds.
   * </div>
   * 
   * @param sourceBounds  The source bounds cuboid.
   *                      It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>sourceBounds</code>
   *                               equal <code>null</code>.
   */
  public TransformGroup getNormalizingTransformGroup (Cuboid sourceBounds)
  {
    checkSourceBounds (sourceBounds);
    
    TransformGroup unitTransformGroup   = null;
    Transform3D    normalizingTransform = null;
    
    unitTransformGroup   = new TransformGroup        ();
    normalizingTransform = getNormalizingTransform3D (sourceBounds);
    
    unitTransformGroup.setTransform (normalizingTransform);
    
    return unitTransformGroup;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static void checkDestinationBounds (Cuboid destinationBounds)
  {
    CheckingUtils.checkForNull (destinationBounds, "Destination bounds cuboid is null.");
  }
  
  private static void checkDimensionsMatching (DimensionsMatching dimensionsMatching)
  {
    CheckingUtils.checkForNull (dimensionsMatching, "Dimensions matchin is null.");
  }
  
  private static void checkSourceBounds (Cuboid sourceBounds)
  {
    CheckingUtils.checkForNull (sourceBounds, "Source bounds are null.");
  }
}
