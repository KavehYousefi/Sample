package jav3d.offloader.properties;


/**
 * <div class="introClass">
 *   The texture coordinates auto generation policy defines if
 *   texture coordinates are created for <i>OFF</i> files lacking these.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       TextureCoordinatesAutoGenerationPolicy.java
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
 *       21.08.2015
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
 *     <td>21.08.2015</td>
 *     <td>1.0</td>
 *     <td>The enum type has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum TextureCoordinatesAutoGenerationPolicy
{
  /**
   * <div class="introEnumInstance">
   *   Always generate texture coordinates, if none are specified.
   * </div>
   */
  ALWAYS,
  /**
   * <div class="introEnumInstance">
   *   Generate texture coordinates, if the header specifies them,
   *   but the the content does not contain them.
   * </div>
   */
  ONLY_IF_SPECIFIED_IN_HEADER,
  /**
   * <div class="introEnumInstance">
   *   Never generate texture coordinates, if none are specified.
   * </div>
   */
  NEVER;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a texture coordinates auto generation policy.
   * </div>
   */
  private TextureCoordinatesAutoGenerationPolicy ()
  {
  }
}
