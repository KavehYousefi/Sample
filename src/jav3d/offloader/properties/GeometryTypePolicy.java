package jav3d.offloader.properties;


/**
 * <div class="introClass">
 *   The geometry type policy determines, which type(s) of geometries
 *   will be created.
 * </div>
 * 
 * <div>
 *   Although triangles can model any face polygon, sometimes they
 *   may not be appropriate, for instance, if the face can be
 *   represented by quadrilaterals. In such cases, it may be better to
 *   allow the loading processor to select the best geometry.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       GeometryTypePolicy.java
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
public enum GeometryTypePolicy
{
  /**
   * <div class="introEnumInstance">
   *   Let the implementation find the geometry type best suited for
   *   a face, for instance, by choosing quadrilaterals (instances of
   *   <code>IndexedQuadArray</code>) instead of triangles, where
   *   possible.
   * </div>
   */
  SELECT_BEST_PRIMITIVE,
  /**
   * <div class="introEnumInstance">
   *   Always generate triangles, that is, create instances of
   *   <code>IndexedTriangleArray</code>.
   * </div>
   */
  ALWAYS_GENERATE_TRIANGLES;
  
  
  /**
   * <div class="introConstructor">
   *   Defines the policy for selecting a suitable primitive for a
   *   face.
   * </div>
   */
  private GeometryTypePolicy ()
  {
  }
}
