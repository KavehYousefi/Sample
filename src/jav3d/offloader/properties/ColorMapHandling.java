package jav3d.offloader.properties;


/**
 * <div class="introClass">
 *   The color map handling defines how to handle -
 *   existing or missing - color map files.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       ColorMapHandling.java
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
 *     <td>The enum type has been created</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum ColorMapHandling
{
  /**
   * <div class="introEnumInstance">
   *   Do not use the color map at all, even if available.
   * </div>
   */
  IGNORE_COLORMAP,
  /**
   * <div class="introEnumInstance">
   *   Ignore a missing color map file.
   * </div>
   */
  IGNORE_MISSING_COLORMAP_FILE,
  /**
   * <div class="introEnumInstance">
   *   Throw an exception, if a necessary color map file cannot be
   *   found.
   * </div>
   */
  THROW_EXCEPTION_ON_MISSING_COLORMAP_FILE;
  
  
  /**
   * <div class="introConstructor">
   *   Defines the behavior for handling a color map.
   * </div>
   */
  private ColorMapHandling ()
  {
  }
}
