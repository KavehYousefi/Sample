package jav3d.lathep001;

import ky.Position;
import jav3d.lathep001.segment.LathePathSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import safercode.CheckingUtils;


/**
 * <div class="introClass">
 *   A segmented lathe path is comprised of multiple path segments,
 *   implementations of the <code>LathePathSegment</code> interface.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       SegmentedLathePath.java
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
public class SegmentedLathePath
implements   LathePath
{
  private List<LathePathSegment> segments;
  // Return points in reversed order. => Solve clockwise point order.
  private boolean                reversePoints;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an empty segmented lathe path.
   * </div>
   */
  public SegmentedLathePath ()
  {
    this.segments      = new ArrayList<LathePathSegment> ();
    this.reversePoints = false;
  }
  
  
  /**
   * <div class="introMethod">
   *   Adds a segment to the path.
   * </div>
   * 
   * @param segment  The segment to add.
   *                 It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>segment</code> is
   *                               <code>null</code>.
   */
  public void addSegment (LathePathSegment segment)
  {
    CheckingUtils.checkForNull (segment, "Path segment is null.");
    segments.add               (segment);
  }
  
  
  public boolean arePointsReversed ()
  {
    return reversePoints;
  }
  
  public void setPointsReversed (boolean reversePoints)
  {
    this.reversePoints = reversePoints;
  }
  
  
  @Override
  public List<Position> getPoints ()
  {
    List<Position> points = new ArrayList<Position> ();
    
    if (! segments.isEmpty ())
    {
      for (LathePathSegment segment : segments)
      {
//        List<Position> segmentPoints = segment.getPointsOnSegment (points);
//        
//        points.addAll (segmentPoints);
        
        points = segment.processPoints (points);
      }
    }
    
    if (reversePoints)
    {
      Collections.reverse (points);
    }
    
    return points;
  }
}
