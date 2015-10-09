package jav3d.lathep001.segment;

import ky.Position;

import java.util.ArrayList;
import java.util.List;


/**
 * <div class="introClass">
 *   This defines the start segment of a path, thus having to be its
 *   first element.
 * </div>
 * 
 * <div>
 *   
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       LatheStartSegment.java
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
public class LatheStartSegment
implements   LathePathSegment
{
  private Position startPoint;
  
  
  public LatheStartSegment (Position startPoint)
  {
    this.startPoint = startPoint;
  }
  
  
  @Override
  public List<Position> getPointsOnSegment (List<Position> lastPoints)
  {
    List<Position> points = new ArrayList<Position> ();
    
    points.add (startPoint);
    
    return points;
  }
  
  @Override
  public List<Position> processPoints (List<Position> previousPoints)
  {
    List<Position> newPoints = getPointsOnSegment (previousPoints);
    
    previousPoints.addAll (newPoints);
    
    return previousPoints;
  }
}