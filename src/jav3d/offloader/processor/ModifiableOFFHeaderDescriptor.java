package jav3d.offloader.processor;

import safercode.CheckingUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * <div class="introClass">
 *   The <code>ModifiableOFFHeaderDescriptor</code> class is an
 *   implementation of the <code>OFFHeaderDescriptor</code> interface
 *   with additional capability to add and remove the header keyword
 *   tokens (components).
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       ModifiableOFFHeaderDescriptor.java
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
public class ModifiableOFFHeaderDescriptor
implements   OFFHeaderDescriptor
{
  private List<OFFHeaderComponent> components;
  
  
  public ModifiableOFFHeaderDescriptor ()
  {
    this.components = new ArrayList<OFFHeaderComponent> ();
  }
  
  
  public void addComponent (OFFHeaderComponent headerComponent)
  {
    CheckingUtils.checkForNull (headerComponent, "Header component is null.");
    
    if (! components.contains (headerComponent))
    {
      components.add (headerComponent);
    }
  }
  
  public void removeComponent (OFFHeaderComponent headerComponent)
  {
    if (components.contains (headerComponent))
    {
      components.remove (headerComponent);
    }
  }
  
  
  @Override
  public List<OFFHeaderComponent> getComponents ()
  {
    return components;
  }
  
  @Override
  public OFFHeaderComponent getComponentAtIndex (int index)
  {
    return components.get (index);
  }
  
  @Override
  public int getComponentCount ()
  {
    return components.size ();
  }
  
  @Override
  public boolean hasComponent (OFFHeaderComponent component)
  {
    return components.contains (component);
  }
  
  @Override
  public boolean hasPerVertexColor ()
  {
    return hasComponent (OFFHeaderComponent.COLOR_PER_VERTEX);
  }
  
  @Override
  public boolean hasPerVertexNormal ()
  {
    return hasComponent (OFFHeaderComponent.NORMAL_PER_VERTEX);
  }
  
  @Override
  public boolean hasTextureCoordinates ()
  {
    return hasComponent (OFFHeaderComponent.TEXCOORD_PER_VERTEX);
  }
  
  @Override
  public boolean hasHomogeneousComponent ()
  {
    return hasComponent (OFFHeaderComponent.FOUR_COORD_COMPONENTS);
  }
  
  @Override
  public boolean hasSpaceDimensions ()
  {
    return hasComponent (OFFHeaderComponent.SPACE_DIMENSION);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = null;
    
    asString = String.format
    (
      "ModifiableOFFHeaderDescriptor(components=%s)",
      components
    );
    
    return asString;
  }
}
