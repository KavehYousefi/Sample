package jav3d.offloader.processor;


/**
 * <div class="introClass">
 *   The <code>LineType</code> defines the various categories of
 *   lines, which can be expected in an OFF file.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       LineType.java
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
 *       19.06.2015
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
 *     <td>19.06.2015</td>
 *     <td>1.0</td>
 *     <td>The enum type has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum LineType
{
  /**
   * <div class="introEnumInstance">
   *   The line is a comment line.
   * </div>
   */
  COMMENT,
  /**
   * <div class="introEnumInstance">
   *   The line is an empty line.
   * </div>
   */
  EMPTY,
  /**
   * <div class="introEnumInstance">
   *   The line is the end of the file (<i>EOF</i>).
   * </div>
   */
  END_OF_FILE,
  /**
   * <div class="introEnumInstance">
   *   The line contains face data.
   * </div>
   */
  FACE,
  /**
   * <div class="introEnumInstance">
   *   The line contains the header.
   * </div>
   */
  FORMAT_TYPE,
  /**
   * <div class="introEnumInstance">
   *   The line contains the number of vertices, faces and edges.
   * </div>
   */
  SIZES,
  /**
   * <div class="introEnumInstance">
   *   The line contains vertex data.
   * </div>
   */
  VERTEX;
  
  
  /**
   * <div class="introConstructor">
   *   Defines a type of line which can possibly be encountered while
   *   reading an OFF file.
   * </div>
   */
  private LineType ()
  {
  }
}
