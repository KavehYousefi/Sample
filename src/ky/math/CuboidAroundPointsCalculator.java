package ky.math;

import ky.Position;
import safercode.CheckingUtils;
import ky.Cuboid;

import java.util.ArrayList;
import java.util.List;


// Alternative name: "PointsBoundingCuboid" or "MinimumBoundingCuboid".
/**
 * <div class="introClass">
 *   The <code>CuboidAroundPointsCalculator</code> takes a collection of
 *   points and returns the minimum bounding cuboid.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       CuboidAroundPointsCalculator.java
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
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class CuboidAroundPointsCalculator
{
  private List<Position> points;
  
  
  public CuboidAroundPointsCalculator (List<Position> points)
  {
    this.points = null;
    setPoints (points);
  }
  
  public CuboidAroundPointsCalculator ()
  {
    this.points = new ArrayList<Position> ();
  }
  
  
  public List<Position> getPoints ()
  {
    return points;
  }
  
  public void setPoints (List<Position> points)
  {
    CheckingUtils.checkForNull (points, "Point list is null.");
    this.points = points;
  }
  
  
  public Cuboid getCuboid ()
  {
    Cuboid boundingCuboid = null;
    double minimumX       = Double.MAX_VALUE;
    double maximumX       = Double.MIN_VALUE;
    double minimumY       = Double.MAX_VALUE;
    double maximumY       = Double.MIN_VALUE;
    double minimumZ       = Double.MAX_VALUE;
    double maximumZ       = Double.MIN_VALUE;
    
    if (points.isEmpty ())
    {
      boundingCuboid = Cuboid.createZeroCubeAtOrigin ();
    }
    else
    {
      for (Position position : points)
      {
        minimumX = Math.min (minimumX, position.getX ());
        maximumX = Math.max (maximumX, position.getX ());
        minimumY = Math.min (minimumY, position.getY ());
        maximumY = Math.max (maximumY, position.getY ());
        minimumZ = Math.min (minimumZ, position.getZ ());
        maximumZ = Math.max (maximumZ, position.getZ ());
      }
      
      boundingCuboid = Cuboid.createFromSpaceDiagonal
      (
        new Position (minimumX, minimumY, minimumZ),
        new Position (maximumX, maximumY, maximumZ)
      );
    }
    
    return boundingCuboid;
  }
}
