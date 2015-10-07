package jav3d.offloader.properties;


/**
 * <div class="introClass">
 *   The data ordering determines, if the data (vertex coordinates,
 *   normals, etc.) should be used in the order they were encountered,
 *   or reversed.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       DataOrdering.java
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
 *       22.06.2015
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
 *     <td>22.06.2015</td>
 *     <td>1.0</td>
 *     <td>The enum type has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum DataOrdering
{
  /**
   * <div class="introEnumInstance">
   *   Use the default order of the data (vertex coordinates,
   *   vertex indices, colors, normals, etc.).
   * </div>
   */
  DEFAULT,
  /**
   * <div class="introEnumInstance">
   *   Reverse the order of the data (vertex coordinates,
   *   vertex indices, colors, normals, etc.).
   * </div>
   */
  REVERSED;
  
  
  /**
   * <div class="introConstructor">
   *   Defines the ordering of the data.
   * </div>
   */
  private DataOrdering ()
  {
  }
}
