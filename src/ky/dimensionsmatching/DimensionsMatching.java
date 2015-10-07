package ky.dimensionsmatching;

import ky.Dimensions;
import ky.transform2.Scaling;
import safercode.CheckingUtils;


/**
 * <div class="introClass">
 *   Dimensions matching computes the scaling transformation necessary
 *   for matching one <code>Dimensions</code> to another one,
 *   according to a certain <code>DimensionsMatchingPolicy</code>'s
 *   definition of "matching".
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       DimensionsMatching.java
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
 *       04.08.2015
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
 *     <td>04.08.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class DimensionsMatching
{
  private DimensionsMatchingPolicy matchingPolicy;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a <code>DimensionsMatching</code> instance utilizing the
   *   given dimensions matching policy.
   * </div>
   * 
   * @param matchingPolicy The dimensions matching policy.
   *                       The <code>null</code> value is invalid.
   * 
   * @throws NullPointerException  If the <code>matchingPolicy</code>
   *                               equals <code>null</code>.
   */
  public DimensionsMatching (DimensionsMatchingPolicy matchingPolicy)
  {
    checkDimensionsMatchingPolicy (matchingPolicy);
    this.matchingPolicy = matchingPolicy;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a <code>DimensionsMatching</code> instance utilizing the
   *   default matching policy of <code>MatchToFitAll3D</code>.
   * </div>
   */
  public DimensionsMatching ()
  {
    this.matchingPolicy = new StretchForcefullyToFitInside3D ();
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the dimensions matching policy, which defines how to
   *   match each dimension from source to destination.
   * </div>
   * 
   * @return  The dimensions matching policy.
   */
  public DimensionsMatchingPolicy getDimensionsMatchingPolicy ()
  {
    return matchingPolicy;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the dimensions matching policy, which defines how to match
   *   each dimension from source to destination.
   * </div>
   * 
   * @param matchingPolicy The new dimensions matching policy.
   *                       The <code>null</code> value is invalid.
   * 
   * @throws NullPointerException  If the <code>matchingPolicy</code>
   *                               equals <code>null</code>.
   */
  public void setDimensionsMatchingPolicy
  (
    DimensionsMatchingPolicy matchingPolicy
  )
  {
    checkDimensionsMatchingPolicy (matchingPolicy);
    this.matchingPolicy = matchingPolicy;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the scaling necessary for matching the source dimensions
   *   to the destination dimensions.
   * </div>
   * 
   * <div>
   *   The definition of "matching" dimensions depends on the currently
   *   set dimensions matching policy.
   * </div>
   * 
   * @param sourceDimensions       The source dimensions to be matched.
   *                               The <code>null</code> value is
   *                               invalid.
   * @param destinationDimensions  The destination dimensions.
   *                               The <code>null</code> value is
   *                               invalid.
   * 
   * @return                       A <code>Scaling</code> representing
   *                               the necessary resizing to match the
   *                               <code>sourceDimensions</code> to the
   *                               <code>destinationDimensions</code>.
   * 
   * @throws NullPointerException  If the <code>sourceDimensions</code>
   *                               or <code>destinationDimensions</code>
   *                               equal <code>null</code>.
   */
  public Scaling getScalingToMatchSourceToDestinationDimensions
  (
    Dimensions sourceDimensions,
    Dimensions destinationDimensions
  )
  {
    Scaling scaling = null;
    
    scaling = matchingPolicy.getScalingToMatchDimensions
    (
      sourceDimensions,
      destinationDimensions
    );
    
    return scaling;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static void checkDimensionsMatchingPolicy
  (
    DimensionsMatchingPolicy matchingPolicy
  )
  {
    CheckingUtils.checkForNull
    (
      matchingPolicy,
      "Dimensions matching policy is null."
    );
  }
}
