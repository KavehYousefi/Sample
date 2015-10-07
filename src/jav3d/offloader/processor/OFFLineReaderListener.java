package jav3d.offloader.processor;


/**
 * <div class="introClass">
 *   An <code>OFFLineReaderListener</code> awaits notification about
 *   a read <i>OFF</i> file line.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFLineReaderListener.java
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
 *     <td>The interface has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public interface OFFLineReaderListener
{
  abstract public void commentLineEncountered (OFFLineReadValidLineEvent event);
  
  abstract public void emptyLineEncountered (OFFLineReadValidLineEvent event);
  
  abstract public void formatLineEncountered (OFFLineReadValidLineEvent event);
  
  abstract public void sizeLineEncountered (OFFLineReadValidLineEvent event);
  
  abstract public void vertexLineEncountered (OFFLineReadValidLineEvent event);
  
  abstract public void faceLineEncountered (OFFLineReadValidLineEvent event);
  
  abstract public void endOfFileLineEncountered (OFFLineReadValidLineEvent event);
}
