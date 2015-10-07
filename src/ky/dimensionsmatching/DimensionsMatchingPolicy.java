package ky.dimensionsmatching;

import ky.Dimensions;
import ky.transform2.Scaling;


/**
 * <div class="introClass">
 *   A <code>DimensionsMatchingPolicy</code> defines the circumstances
 *   under which a source <code>Dimensions</code> object can be
 *   regarded as matching a destination <code>Dimensions</code>
 *   by computing the scaling transformation to perform the match.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       DimensionsMatchingPolicy.java
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
 *     <td>The interface has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public interface DimensionsMatchingPolicy
{
  /**
   * <div class="introMethod">
   *   Returns the scaling necessary for matching the source dimensions
   *   to the destination dimensions.
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
   *                               equal <code>null</code>.
   * @throws NullPointerException  If the
   *                               <code>destinationDimensions</code>
   *                               equal <code>null</code>.
   */
  abstract public Scaling getScalingToMatchDimensions
  (
    Dimensions sourceDimensions,
    Dimensions destinationDimensions
  );
}
