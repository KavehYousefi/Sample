package owntest;
// 27.03.2014

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.swing.JApplet;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import org.web3d.j3d.loaders.VRML97Loader;
import org.web3d.j3d.loaders.X3DLoader;


/* Verwendeter Loader: "Xj3D" (Version 1.0).
 */
@SuppressWarnings ("serial")
public class ObjectLoader006_XJ3D  extends JApplet
{
  private BranchGroup sceneNode;
  
  public ObjectLoader006_XJ3D ()
  {
    GraphicsConfiguration graphicsConf  = SimpleUniverse.getPreferredConfiguration ();
    Canvas3D              canvas3D      = new Canvas3D (graphicsConf);
    SimpleUniverse        universe      = new SimpleUniverse ();
    ViewingPlatform       viewPlatform  = universe.getViewingPlatform ();
    BranchGroup           branchGroup   = new BranchGroup ();
    Scene                 scene         = null;
    Node                  node          = null;
    Transform3D           transform     = new Transform3D    ();
    TransformGroup        transGroup    = new TransformGroup ();
    BoundingSphere        bounds        = new BoundingSphere (new Point3d (0.0, 0.0, 0.0), 100.0);
    Color3f               ambientColor  = new Color3f (0.5f, 0.5f, 0.5f);
    AmbientLight          ambientLight  = new AmbientLight (ambientColor);
    Color3f               directColor   = new Color3f (1.0f, 1.0f, 1.0f);
    Vector3f              directVector  = new Vector3f (-1.0f, -1.0f, -1.0f);
    DirectionalLight      directLight   = new DirectionalLight (directColor, directVector);
    MouseRotate           mausDreher    = new MouseRotate ();
    MouseTranslate        mausBeweger   = new MouseTranslate ();
    MouseWheelZoom        mausRadZoomer = new MouseWheelZoom ();
    
    BorderLayout          layout        = new BorderLayout ();
    
    VRML97Loader          vrmlLoader    = new VRML97Loader ();
    X3DLoader             x3dLoader     = new X3DLoader    ();
    
    
    setLayout (layout);
    add       (canvas3D);
    
    ambientLight.setInfluencingBounds (bounds);
    directLight.setInfluencingBounds  (bounds);
    
    
    vrmlLoader.setFlags (VRML97Loader.LOAD_ALL);
    x3dLoader.setFlags  (X3DLoader.LOAD_ALL);
    
    try
    {
      scene = vrmlLoader.load ("myresources/Figur_001.wrl");
//      scene = vrmlLoader.load ("myresources\\meadow-001.wrl");
    }
    catch (Exception e)
    {
      System.err.println ("Fehler: " + e.getMessage ());
      System.exit (1);
    }
    
    node = scene.getSceneGroup ();
    
//    sceneNode = (BranchGroup) node;
    
    sceneNode = new BranchGroup ();
    TransformGroup sceneTransformGroup = new TransformGroup ();
    Transform3D    sceneTransform      = new Transform3D    ();
    sceneTransform.setScale (0.020);
//    sceneTransform.rotX (Math.PI / 2.0);
    sceneTransform.setTranslation (new Vector3d (0.00, -0.05, 0.00));
    sceneTransformGroup.setTransform (sceneTransform);
    sceneTransformGroup.addChild     (node);
    sceneNode.addChild               (sceneTransformGroup);
    
    transGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    transGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    transGroup.setTransform  (transform);
//    transGroup.addChild      (node);
    
    mausDreher.setTransformGroup      (transGroup);
    mausDreher.setSchedulingBounds    (bounds);
    mausBeweger.setTransformGroup     (transGroup);
    mausBeweger.setSchedulingBounds   (bounds);
    mausRadZoomer.setTransformGroup   (transGroup);
    mausRadZoomer.setSchedulingBounds (bounds);
    
    // Form hinzufuegen.
    branchGroup.addChild (ambientLight);
    branchGroup.addChild (directLight);
    branchGroup.addChild (transGroup);
    branchGroup.addChild (mausDreher);
    branchGroup.addChild (mausBeweger);
    branchGroup.addChild (mausRadZoomer);
    branchGroup.compile  ();
    
    viewPlatform.setNominalViewingTransform ();
    universe.addBranchGraph (branchGroup);
  }
  
  public BranchGroup getScene ()
  {
    return sceneNode;
  }
  
  
//  public static void main (String[] args)
//  {
//    new ObjectLoader006_XJ3D ();
//  }
}