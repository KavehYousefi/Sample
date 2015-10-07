package jav3d.offloader.properties;


/**
 * <div class="introClass">
 *   The normalization policy specifies if and how a normalization
 *   of the loaded <i>OFF</i> file data will be performed.
 * </div>
 * 
 * <div>
 *   A normalization is a combination of transformations to match
 *   the 3D content into a unit cube of width, height and depth equal
 *   to one, centered at the origin <i>P(0, 0, 0)</i>.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       NormalizationPolicy.java
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
 *       02.09.2015
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
 *     <td>02.09.2015</td>
 *     <td>1.0</td>
 *     <td>The enumerated type has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum NormalizationPolicy
{
  /**
   * <div class="introEnumInstance">
   *   No normalization should be performed.
   * </div>
   */
  NO_NORMALIZATION,
  /**
   * <div class="introEnumInstance">
   *   The vertex coordinates will be transformed to match the
   *   unit cube's dimensions and position.
   * </div>
   */
  TRANSFORM_VERTEX_COORDINATES,
  /**
   * <div class="introEnumInstance">
   *   A <code>TransformGroup</code> node with suitable transformation,
   *   containing all geometries, will be provided to match the
   *   unit cube's dimensions and position.
   * </div>
   */
  CREATE_TRANSFORM_GROUP;
  
  
  private NormalizationPolicy ()
  {
  }
}
