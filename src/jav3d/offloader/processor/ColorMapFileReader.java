package jav3d.offloader.processor;

import jav3d.offloader.ColorUtils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color4f;


/**
 * <div class="introClass">
 *   Instances of this class read in a color map file and return a
 *   list of the colors found in it.
 * </div>
 * 
 * <div>
 *   Following sources have been consulted:
 *   <ul>
 *     <li>
 *       <a href="https://docs.oracle.com/javase/tutorial/essential/io/legacy.html">https://docs.oracle.com/javase/tutorial/essential/io/legacy.html</a>
 *       <br />
 *       Topic: Interoperability of the old <code>File</code> and the
 *              new <code>Path</code> class for file representation.
 *       <br />
 *       Accessed: 22.06.2015
 *     </li>
 *   </ul>
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       ColorMapFileReader.java
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
 *       22.06.2015
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
 *     <td>22.06.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class ColorMapFileReader
{
  private Path colorMapPath;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a color map reader.
   * </div>
   * 
   * @param colorMapPath  The <code>Path</code> to the color map file.
   */
  public ColorMapFileReader (Path colorMapPath)
  {
    this.colorMapPath = colorMapPath;
  }
  
  
  /**
   * <div class="introMethod">
   *   Reads the specified color map file and returns the encountered
   *   colors as a list.
   * </div>
   * 
   * @return  A list containing the colors, represented as
   *          <code>Color4f</code> objects.
   * 
   * @throws FileNotFoundException  If the color map file could not be
   *                                found.
   * @throws IOException            If a problem occurred while opening,
   *                                reading or closing the color map
   *                                file.
   */
  public List<Color4f> getColor4fList ()
  throws FileNotFoundException, IOException
  {
    List<Color4f>  colorList      = new ArrayList<Color4f> ();
    FileReader     fileReader     = null;
    BufferedReader bufferedReader = null;
    String         line           = null;
    
    fileReader     = new FileReader     (colorMapPath.toFile ());
    bufferedReader = new BufferedReader (fileReader);
    
    do
    {
      line = bufferedReader.readLine ();
      
      if (line == null)
      {
        break;
      }
      else
      {
        line = line.trim ();
      }
      
      if ((line.isEmpty ()) || (line.startsWith ("#")))
      {
        continue;
      }
      else
      {
        Color4f      color4f               = null;
        List<Double> colorComponents       = null;
        String       commandPart           = null;
        String[]     tokens                = null;
        
        commandPart = ParserUtils.getLinePartBeforeComment (line);
        
        if (commandPart.isEmpty ())
        {
          continue;
        }
        
        tokens          = commandPart.split     ("\\s+");
        colorComponents = new ArrayList<Double> ();
        
        for (String token : tokens)
        {
          colorComponents.add (Double.parseDouble (token));
        }
        
        color4f = ColorUtils.getColor4fFromColorComponentList (colorComponents);
        
        colorList.add (color4f);
      }
    }
    while (line != null);
    
    fileReader.close     ();
    bufferedReader.close ();
    
    return colorList;
  }
}
