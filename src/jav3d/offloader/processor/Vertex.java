package jav3d.offloader.processor;

import ky.Position;
import jav3d.offloader.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color4f;


/**
 * <div class="introClass">
 *   A <code>Vertex</code> is defined by a set of coordinates and
 *   possibly normals and color components.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Vertex.java
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
 *       24.06.2015
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
 *     <td>24.06.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Vertex
{
  private List<Double> coordinates;
  private List<Double> colorComponents;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an empty vertex.
   * </div>
   */
  public Vertex ()
  {
    this.coordinates     = new ArrayList<Double> ();
    this.colorComponents = new ArrayList<Double> ();
  }
  
  
  public List<Double> getCoordinates ()
  {
    return coordinates;
  }
  
  public Position getPosition ()
  {
    return new Position (coordinates.get (0),
                         coordinates.get (1),
                         coordinates.get (2));
  }
  
  public void addCoordinate (double coordinate)
  {
    coordinates.add (coordinate);
  }
  
  public List<Double> getColorComponents ()
  {
    return colorComponents;
  }
  
  public void addColorComponents (double colorComponent)
  {
    colorComponents.add (colorComponent);
  }
  
  public int getColorComponentCount ()
  {
    return colorComponents.size ();
  }
  
  public Color4f getColor4f ()
  {
    Color4f color = null;
    
    if (colorComponents.size () >= 3)
    {
      color = ColorUtils.getColor4fFromColorComponentList (colorComponents);
    }
    else
    {
      color = null;
    }
    
    return color;
  }
}
