package jav3d.offloader.processor;


/**
 * <div class="introClass">
 *   Based on a header descriptor, the <code>OFFHeaderCreator</code>
 *   creates a string representation of its header keyword.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFHeaderCreator.java
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
 *       22.08.2015
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
 *     <td>22.08.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class OFFHeaderCreator
{
  public OFFHeaderCreator ()
  {
  }
  
  
  public String createHeaderKeyword (OFFHeaderDescriptor headerDescriptor)
  {
    String        headerKeyword  = null;
    StringBuilder keywordBuilder = null;
    
    keywordBuilder = new StringBuilder ();
    
    if (headerDescriptor.hasTextureCoordinates ())
    {
      keywordBuilder.append ("ST");
    }
    
    if (headerDescriptor.hasPerVertexColor ())
    {
      keywordBuilder.append ("C");
    }
    
    if (headerDescriptor.hasPerVertexNormal ())
    {
      keywordBuilder.append ("N");
    }
    
    if (headerDescriptor.hasHomogeneousComponent ())
    {
      keywordBuilder.append ("4");
    }
    
    if (headerDescriptor.hasSpaceDimensions ())
    {
      keywordBuilder.append ("n");
    }
    
    keywordBuilder.append ("OFF");
    
    headerKeyword = keywordBuilder.toString ();
    
    return headerKeyword;
  }
}
