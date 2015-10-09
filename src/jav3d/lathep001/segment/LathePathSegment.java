package jav3d.lathep001.segment;

import ky.Position;

import java.util.List;


/**
 * <div class="introClass">
 *   A path segment is an element building a path.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       FILENAME.java
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
 *       30.03.2015
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
 *     <td>30.03.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public interface LathePathSegment
{
  abstract public List<Position> getPointsOnSegment (List<Position> previousPoints);
  
  abstract public List<Position> processPoints      (List<Position> previousPoints);
}