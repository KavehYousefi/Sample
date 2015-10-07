package jav3d.offloader.properties;


/**
 * <div class="introClass">
 *   The geometry grouping policy defines how faces read from an
 *   OFF file should be grouped to build geometries.
 * </div>
 * 
 * <div>
 *   Following types are defined:
 *   <table>
 *     <tr>
 *       <th>GeometryGroupingPolicy</th>
 *       <th>Description</th>
 *     </tr>
 *     <tr>
 *       <td><code>PER_FILE</code></td>
 *       <td>
 *         All faces are put into one geometry, as far as possible.
 *         This is the least memory intensive option.<br />
 *         Note that the ability to group all faces into one geometry
 *         may depend on the <code>GeometryTypePolicy</code>: If, for
 *         instance, quadrilaterals may be created instead of triangles,
 *         faces with 12 vertices may become quadrilaterals, those with
 *         9 will become triangles, thus deeming ONE geometry for all
 *         faces impossible, and forcing multiple geometries per file
 *         to be created.
 *       </td>
 *     </tr>
 *     <tr>
 *       <td><code>PER_FACE_INDEX_GROUP</code></td>
 *       <td>
 *         Successive faces with equal number of vertex indices
 *         build a geometry.
 *       </td>
 *     </tr>
 *     <tr>
 *       <td><code>PER_FACE</code></td>
 *       <td>
 *         Each face becomes an own geometry.<br />
 *         <strong>Note: Except for the most simple OFF files, this
 *         geometry grouping policy tends to exhaust the system memory,
 *         causing a
 *         <code>java.lang.OutOfMemoryError: Java heap space</code>
 *         exception.</strong>
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
 *       GeometryGroupingPolicy.java
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
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum GeometryGroupingPolicy
{
  /**
   * <div class="introEnumInstance">
   *   Create one single geometry containing all faces.
   * </div>
   */
  PER_FILE,
  /**
   * <div class="introEnumInstance">
   *   Put successive faces with equal number of face vertex indices
   *   into the same geometry.
   * </div>
   */
  PER_FACE_INDEX_GROUP,
  /**
   * <div class="introEnumInstance">
   *   Put each face into its own geometry.
   * </div>
   */
  PER_FACE;
  
  
  /**
   * <div class="introConstructor">
   *   Defines the assignment of faces to geometries.
   * </div>
   */
  private GeometryGroupingPolicy ()
  {
  }
}
