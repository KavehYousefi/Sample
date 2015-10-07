package jav3d.offloader.processor;

import safercode.CheckingUtils;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * <div class="introClass">
 *   The <code>OFFHeaderParser</code> interprets a string as a header
 *   keyword and extracts its components.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFHeaderParser.java
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
 *       21.06.2015
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
 *     <td>21.06.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class OFFHeaderParser
{
  public OFFHeaderParser ()
  {
  }
  
  
  public OFFHeaderDescriptor getOFFHeaderDescriptor (String stringToEvaluate)
  {
    CheckingUtils.checkForNull (stringToEvaluate, "Input string is null.");
    
    ModifiableOFFHeaderDescriptor headerDescriptor = null;
    
    headerDescriptor = new ModifiableOFFHeaderDescriptor ();
    setComponentsFromHeaderString (headerDescriptor, stringToEvaluate);
    
    return headerDescriptor;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void setComponentsFromHeaderString
  (
    ModifiableOFFHeaderDescriptor headerDescriptor,
    String                        headerString
  )
  {
    String                          preparedString    = null;
    Map<String, OFFHeaderComponent> tokenComponentMap = null;
    
    preparedString    = headerString.trim    ();
    tokenComponentMap = getTokenComponentMap ();
    
    for (String token : tokenComponentMap.keySet ())
    {
      if (preparedString.contains (token))
      {
        OFFHeaderComponent headerComponent = null;
        
        headerComponent = tokenComponentMap.get (token);
        headerDescriptor.addComponent (headerComponent);
      }
    }
  }
  
  // Maps OFFHeaderComponent to characters.
  private Map<String, OFFHeaderComponent> getTokenComponentMap ()
  {
    Map<String, OFFHeaderComponent> characterComponentMap = null;
    
    characterComponentMap = new LinkedHashMap<String, OFFHeaderComponent> ();
    characterComponentMap.put ("C", OFFHeaderComponent.COLOR_PER_VERTEX);
    characterComponentMap.put ("c", OFFHeaderComponent.COLOR_PER_VERTEX);
    characterComponentMap.put ("N", OFFHeaderComponent.NORMAL_PER_VERTEX);
    characterComponentMap.put ("4", OFFHeaderComponent.FOUR_COORD_COMPONENTS);
    characterComponentMap.put ("n", OFFHeaderComponent.SPACE_DIMENSION);
    characterComponentMap.put ("ST", OFFHeaderComponent.TEXCOORD_PER_VERTEX);
    
    return characterComponentMap;
  }
}
