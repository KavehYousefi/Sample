package adtest01;
// PropManager.java
// Andrew Davison, January 2003, ad@fivedots.coe.psu.ac.th

/* The user supplies the name of a 3D object file to be loaded.
   Its bounding sphere is automatically scaled to have a radius 
   of 1 unit, and rotated -90 around x-axis if it is a 3ds model.

   A large range of different 3D object formats can be loaded
   since we are using the Portfolio loaders.

   Once loaded, the image can be moved and rotated along the
   X, Y, and Z axes, and scaled. The resulting position, 
   rotation, and scaling information can be stored in a 
   'coords' file (which has the same name as the 3D file
   + "Coords.txt").

   The rotation information is stored as a series of rotation
   *numbers* which must be executed in order to get to the curent
   overall rotation:
      1 = positive ROT_INCR around x-axis
      2 = negative ROT_INCR around x-axis

      3 = positive ROT_INCR around y-axis
      4 = negative ROT_INCR around y-axis

      5 = positive ROT_INCR around z-axis
      6 = negative ROT_INCR around z-axis

   This approach is used to try to avoid the problem that a mix of
   rotations about diffrent axes do not produce the same result if
   carried out in different orders.

   The coordinates information can be loaded along with the object
   by using:
		java Loader3D -c <3D filename>

   The loaded object is hung off several TGs, and the top one can be
   accessed by calling getTG().

   Changes:
   - removed use of j3d-fly VRML loader and starfire 3DS loader

   April 2010
     - fixed rotInfo generic typing
     - used Java3D's ObjectFile to load wavefront files so material is correct
*/


import java.nio.file.Path;

import javax.media.j3d.*;

import org.web3d.j3d.loaders.Web3DLoader;

import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.ObjectFile;

import jav3d.offloader.j3dloader.OFFLoader;
import ky.angle.Angle;
// Portfolio loader packages
import ncsa.j3d.loaders.*;
   // NCSA Portfolio is available at http://fivedots.coe.psu.ac.th/~ad/jg/ch9/


public class PropManager
{
  private static final Angle CORRECTION_ROTATION = new Angle (-50.0);
  
  // TGs which the loaded object (the prop) hangs off: 
  //       moveTG-->rotTG-->scaleTG-->objBoundsTG-->obj
  private TransformHierarchy transformHierarchy;
  
  
  public PropManager (Path modelFilePath)
  {
    this.transformHierarchy = null;

    loadFile (modelFilePath);
  }  // end of PropManager()
  
  
  public TransformHierarchy getTransformHierarchy ()
  {
    return transformHierarchy;
  }
  
  
  private void loadFile (Path modelFilePath)
  /* The 3D object file is loaded using a Portfolio loader.

     The loaded object has 4 transform groups above it -- objBoundsTG is
     for adjusting the object's bounded sphere so it is centered at
     (0,0,0) and has unit radius. 

     The other TGs are for doing separate moves, rotations, and scaling
     of the object.
        moveTG-->rotTG-->scaleTG-->objBoundsTG-->object
  */
  {
    BranchGroup    sceneGroup    = null;
    TransformGroup objBoundsTG   = null;
    BoundingSphere objBounds     = null;
    String         fileExtension = null;
    String         filePath      = null;
    Scene          loadedScene   = null;
    
    fileExtension = getFileExtension (modelFilePath.toFile ().getAbsolutePath ());
    filePath      = modelFilePath.toFile ().getAbsolutePath ();
    
    try
    {
      // the file is a wavefront model
      if (fileExtension.equals ("obj"))
      {
        // System.out.println("Loading obj file");
        ObjectFile of = new ObjectFile ();
        
        of.setFlags (ObjectFile.RESIZE      |
                     ObjectFile.TRIANGULATE |
                     ObjectFile.STRIPIFY);
        loadedScene = of.load (filePath);
      }
      // Use the XJ3D loader for VRML and X3D files.
      else if ((fileExtension.equals ("wrl")) || (fileExtension.equals ("x3d")))
      {
        Web3DLoader vrml97Loader = null;
        
        vrml97Loader = new Web3DLoader   ();
        loadedScene  = vrml97Loader.load (filePath);
      }
      else if ((fileExtension.equalsIgnoreCase ("off")))
      {
        OFFLoader offLoader = null;
        
        offLoader   = new OFFLoader  ();
        loadedScene = offLoader.load (filePath);
      }
      // use Portfolio loader for other models
      else
      {
        ModelLoader modelLoader = new ModelLoader ();
        loadedScene = modelLoader.load (filePath);   // handles many types of file
      }
    }
    catch (Exception e)
    {
      System.err.println (e);
      System.exit        (1);
    }
    
    // get the branch group for the loaded object
    sceneGroup = loadedScene.getSceneGroup ();
    
    // create a transform group for the object's bounding sphere
    objBoundsTG = new TransformGroup ();
    objBoundsTG.addChild (sceneGroup);
    
    // resize loaded object's bounding sphere (and maybe rotate)
    objBounds = (BoundingSphere) sceneGroup.getBounds();
    normalizeBoundingSphere (objBoundsTG, objBounds.getRadius(), fileExtension);
    
    transformHierarchy = new TransformHierarchy ();
    transformHierarchy.getScaleTransformGroup ().addChild (objBoundsTG);
    
  } // end of loadFile()


  private String getFileExtension (String fileName)
  // return the extension of fnm, or "(none)"
  {
    int dotposn = fileName.lastIndexOf(".");
    if (dotposn == -1)  // no extension
      return "(none)";
    else
      return fileName.substring (dotposn+1).toLowerCase ();
  }


  private void normalizeBoundingSphere
  (
    TransformGroup objBoundsTG,
    double         boundingSphereRadius,
    String         fileExtension
  )
  // Scale the object to unit radius, and rotate -90 around x-axis if the
  // file contains a 3ds model
  {
    Transform3D objectTrans = new Transform3D();
    objBoundsTG.getTransform( objectTrans );

    // System.out.println("radius: " + df.format(radius));

    // scale the object so its bounds are within a 1 unit radius sphere
    Transform3D scaleTrans  = new Transform3D ();
    double      scaleFactor = 1.0 / boundingSphereRadius;
    // System.out.println("scaleFactor: " + df.format(scaleFactor) );
    scaleTrans.setScale (scaleFactor);

    // final transform = [original] * [scale] (and possible *[rotate])
    objectTrans.mul (scaleTrans);
    
    // the file is a 3ds model
    if (fileExtension.equals ("3ds"))
    {
      Angle planeAlignmentRotationFor3ds = null;
      
      planeAlignmentRotationFor3ds = CORRECTION_ROTATION.getAddedAngle (new Angle (-90.0));
      
      // System.out.println("Rotating -90 around x-axis");
      Transform3D rotTrans = new Transform3D();
      // 3ds models are often on their face; fix that
      rotTrans.rotX   (planeAlignmentRotationFor3ds.getSizeInRadians ());
      objectTrans.mul (rotTrans);
    }
    else
    {
      Transform3D rotTrans = new Transform3D();
      // 3ds models are often on their face; fix that
      rotTrans.rotX   (CORRECTION_ROTATION.getSizeInRadians ());
      objectTrans.mul (rotTrans);
    }

    objBoundsTG.setTransform(objectTrans);
  } // end of setBSPosn()
  
}  // end of PropManager class
