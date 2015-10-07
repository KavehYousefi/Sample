package jav3d.offloader.j3dloader;

import jav3d.offloader.processor.OFFFileReader;
import jav3d.offloader.processor.OFFLineReader;
import jav3d.offloader.processor.OFFProcessor;
import jav3d.offloader.properties.OFFLoaderProperties;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.LoaderBase;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.SceneBase;


// TODO: CALCULATE BOUNDING BOX?
/**
 * <div class="introClass">
 *   The <code>OFFLoader</code> class implements a Java 3D compatible
 *   loader for reading <i>OFF</i> files (file suffix ".off").
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFLoader.java
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
 *       14.08.2015
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
 *     <td>14.08.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 * @see      com.sun.j3d.loaders.Loader
 * @see      com.sun.j3d.loaders.Scene
 */
public class OFFLoader
extends      LoaderBase
{
  private OFFLoaderProperties properties;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an <code>OFFLoader</code> with the given properties.
   * </div>
   * 
   * @param properties  The non-<code>null</code> properties to apply.
   * 
   * @throws NullPointerException  If the <code>properties</code> equal
   *                               <code>null</code>.
   */
  public OFFLoader (OFFLoaderProperties properties)
  {
    super ();
    this.properties = properties;
  }
  
  /**
   * <div class="introConstructor">
   *   Creates an <code>OFFLoader</code> with the default properties.
   * </div>
   */
  public OFFLoader ()
  {
    this (new OFFLoaderProperties ());
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the properties defined for the loader.
   * </div>
   * 
   * @return  The loader properties.
   */
  public OFFLoaderProperties getProperties ()
  {
    return properties;
  }
  
  public void setProperties (OFFLoaderProperties properties)
  {
    this.properties = properties;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "Loader".                   -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public Scene load (String fileName)
  throws FileNotFoundException,
         IncorrectFormatException,
         ParsingErrorException
  {
    checkFileNameForMissingFile (fileName);
    
    SceneBase     scene      = null;
    OFFLineReader lineReader = null;
    OFFProcessor  processor  = null;
    OFFFileReader fileReader = null;
    
    lineReader = new OFFLineReader ();
    processor  = new OFFProcessor  ();
    fileReader = new OFFFileReader (lineReader);
    
    processor.setProperties             (properties);
    lineReader.addOFFLineReaderListener (processor);
    
    try
    {
      // Only line, which differs between the three methods.
      fileReader.readFileFromFileName (fileName);
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }
    
    scene = createSceneFromOFFProcessor (processor);
    
    return scene;
  }
  
  @Override
  public Scene load (URL url)
  throws FileNotFoundException,
         IncorrectFormatException,
         ParsingErrorException
  {
    SceneBase     scene      = null;
    OFFLineReader lineReader = null;
    OFFProcessor  processor  = null;
    OFFFileReader fileReader = null;
    
    lineReader = new OFFLineReader ();
    processor  = new OFFProcessor  ();
    fileReader = new OFFFileReader (lineReader);
    
    processor.setProperties             (properties);
    lineReader.addOFFLineReaderListener (processor);
    
    try
    {
      fileReader.readFileFromURL (url);
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }
    
    scene = createSceneFromOFFProcessor (processor);
    
    return scene;
  }
  
  @Override
  public Scene load (Reader reader)
  throws FileNotFoundException,
         IncorrectFormatException,
         ParsingErrorException
  {
    SceneBase     scene      = null;
    OFFLineReader lineReader = null;
    OFFProcessor  processor  = null;
    OFFFileReader fileReader = null;
    
    lineReader = new OFFLineReader ();
    processor  = new OFFProcessor  ();
    fileReader = new OFFFileReader (lineReader);
    
    processor.setProperties             (properties);
    lineReader.addOFFLineReaderListener (processor);
    
    try
    {
      fileReader.readFileWithReader (reader);
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }
    
    scene = createSceneFromOFFProcessor (processor);
    
    return scene;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static void checkFileNameForMissingFile (String fileName)
  throws FileNotFoundException
  {
    if (! Files.exists (Paths.get (fileName)))
    {
      String exceptionText = null;
      
      exceptionText = String.format
      (
        "The file \"%s\" could not be found.",
        fileName
      );
      throw new FileNotFoundException (exceptionText);
    }
  }
  
  private SceneBase createSceneFromOFFProcessor (OFFProcessor processor)
  {
    SceneBase   scene      = null;
    BranchGroup sceneGroup = null;
    
    scene      = new SceneBase   ();
    sceneGroup = new BranchGroup ();
    
    for (Geometry geometry : processor.getGeometries ())
    {
      Shape3D shape = new Shape3D (geometry, new Appearance ());
      
      sceneGroup.addChild (shape);
    }
    
    scene.setSceneGroup (sceneGroup);
    
    return scene;
  }
}
