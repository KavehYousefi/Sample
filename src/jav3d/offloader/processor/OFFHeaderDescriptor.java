package jav3d.offloader.processor;

import java.util.List;


/**
 * <div class="introClass">
 *   The <code>OFFHeaderDescriptor</code> interface defines the
 *   description of an <i>OFF</i> file header keyword, thus listing the
 *   formal the capabilities of the file.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFHeaderDescriptor.java
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
 *     <td>The interface has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public interface OFFHeaderDescriptor
{
  
  public abstract List<OFFHeaderComponent> getComponents ();
  
  public abstract OFFHeaderComponent getComponentAtIndex (int index);
  
  public abstract int getComponentCount ();
  
  public abstract boolean hasComponent (OFFHeaderComponent component);
  
  public abstract boolean hasPerVertexColor ();
  
  public abstract boolean hasPerVertexNormal ();
  
  public abstract boolean hasTextureCoordinates ();
  
  public abstract boolean hasHomogeneousComponent ();
  
  public abstract boolean hasSpaceDimensions ();
  
}
