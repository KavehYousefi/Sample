package jav3d.offloader.processor;


/**
 * <div class="introClass">
 *   An <code>OFFHeaderComponent</code> represents information about the
 *   respective OFF file coded in the header string.
 * </div>
 * 
 * <div>
 *   Following header tokens are defined:
 *   <table>
 *     <tr>
 *       <th>Header token</th>
 *       <th>OFFHeaderComponent</th>
 *       <th>Description</th>
 *     </tr>
 *     <tr>
 *       <td>C</td>
 *       <td><code>COLOR_PER_VERTEX</code></td>
 *       <td>Vertices may contain color information.</td>
 *     </tr>
 *     <tr>
 *       <td>N</td>
 *       <td><code>NORMAL_PER_VERTEX</code></td>
 *       <td>Vertices may contain three normal coordinates.</td>
 *     </tr>
 *     <tr>
 *       <td>4</td>
 *       <td><code>FOUR_COORD_COMPONENTS</code></td>
 *       <td>
 *         Each vertex has <strong>four</strong> positional coordinates,
 *         the fourth being a homogeneous component.
 *       </td>
 *     </tr>
 *     <tr>
 *       <td>n</td>
 *       <td><code>SPACE_DIMENSIONS</code></td>
 *       <td>Vertices have a certain space dimension.</td>
 *     </tr>
 *     <tr>
 *       <td>ST</td>
 *       <td><code>TEXCOORD_PER_VERTEX</code></td>
 *       <td>
 *         Vertices may contain two-dimensional texture coordinates.
 *       </td>
 *     </tr>
 *   </table>
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFHeaderComponent.java
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
 *       21.06.2015
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
 *     <td>21.06.2015</td>
 *     <td>1.0</td>
 *     <td>The enum type has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum OFFHeaderComponent
{
  /**
   * <div class="introEnumInstance">
   *   Token specifying color per vertex.
   * </div>
   */
  COLOR_PER_VERTEX,        // "C"
  /**
   * <div class="introEnumInstance">
   *   Token specifying normal per vertex.
   * </div>
   */
  NORMAL_PER_VERTEX,       // "N"
  /**
   * <div class="introEnumInstance">
   *   Token specifying four positional coordinates vertex with the
   *   fourth being a homogeneous component.
   * </div>
   */
  FOUR_COORD_COMPONENTS,   // "4"
  /**
   * <div class="introEnumInstance">
   *   Token specifying certain space dimension for the vertices.
   * </div>
   */
  SPACE_DIMENSION,         // "n"
  /**
   * <div class="introEnumInstance">
   *   Token specifying two-dimensional texture coordinates per vertex.
   * </div>
   */
  TEXCOORD_PER_VERTEX;     // "ST"
  
  
  private OFFHeaderComponent ()
  {
  }
}
