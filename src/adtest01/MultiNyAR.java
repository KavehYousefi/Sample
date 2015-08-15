package adtest01;
// MultiNyAR.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, April 2010

/*  An Java3D NyARToolkit example using multiple markers and models,
    that reports position and rotation information.
    NYARToolkit is available at:
           http://nyatla.jp/nyartoolkit/wiki/index.php?NyARToolkit%20for%20Java.en

    JMF also required: 
       http://java.sun.com/javase/technologies/desktop/media/jmf/

    NCSA Portfolio is used to load the models. It is available at:
      http://fivedots.coe.psu.ac.th/~ad/jg/ch9/

    --------------------
    Usage:
      > compile *.java
      > run MultiNyAR
*/


import java.awt.*;
import java.io.*;

import javax.swing.*;

import collision.CollisionEvent;
import collision.CollisionListener;

import com.sun.j3d.utils.universe.*;

import commies.Message3D;

import ky.appearance.DefaultMaterials;

import javax.media.j3d.*;
import javax.vecmath.*;

import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.java3d.utils.*;



@SuppressWarnings ({"serial"})
public class MultiNyAR
extends      JFrame
implements   CollisionListener
{
  private final String PARAMS_FNM = "Data/camera_para.dat";

  private static final int PWIDTH  = 320;   // size of panel
  private static final int PHEIGHT = 240; 

//  private static final double SHAPE_SIZE = 0.02; 

  private static final int BOUNDSIZE = 100;  // larger than world

  private J3dNyARParam cameraParams;
  private JTextArea    statusTA;
  
  private Message3D    message3D;
  
  
  private int          activePlayer;
  private long         lastCollisionTimestamp;
  private DetectMarkers detectMarkers;


  public MultiNyAR()
  {
    super ("Multiple markers NyARToolkit Example");
    
    
    activePlayer           = 1;
    lastCollisionTimestamp = 0;
    
    message3D = new Message3D ();
    message3D.setPosition     (new Point3d (0.0, 0.0, 0.0));
    this.detectMarkers = new DetectMarkers (this);
    
    
    cameraParams = readCameraParams (PARAMS_FNM);
    
    Container cp = getContentPane ();
    // Create a JPanel in the center of JFrame.
    JPanel    p  = new JPanel     ();
    
    p.setLayout        (new BorderLayout ());
    p.setPreferredSize (new Dimension (PWIDTH, PHEIGHT));
    cp.add             (p, BorderLayout.CENTER);
    
    // put the 3D canvas inside the JPanel
    p.add(createCanvas3D(), BorderLayout.CENTER);
    
    // add status field to bottom of JFrame
    statusTA = new JTextArea(7, 10);   // updated by DetectMarkers object (see createSceneGraph())
    statusTA.setEditable(false);
    cp.add(statusTA, BorderLayout.SOUTH);
    
    // configure the JFrame
    setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE );
    pack                     ();
    setVisible               (true);
  }  // end of MultiNyAR()
  
  
  
  
  @Override
  public void collisionStarted (CollisionEvent event)
  {
//    message3D.setText ("Collision detected.");
//    message3D.showFor (1000);
    
    MarkerModel observingModel = event.getObservingModel ();
    MarkerModel observedModel  = event.getObservedModel  ();
    
//    System.out.println (observingModel.getPlayerNumber () + " -- " + observedModel.getPlayerNumber ());
    
    if (observingModel != null)
    {
      // Enemy unit is not null?
      if (observedModel != null)
      {
        final long MINIMUM_COLLISION_IGNORE_TIME = 3000L;
        
        int    observingPlayer = 0;
        int    observedPlayer  = 0;
        String messageText     = null;
        long   currentCollisionTimestamp = 0L;
        long   timespanWithoutCollision  = 0L;
        
        currentCollisionTimestamp = System.currentTimeMillis ();
        timespanWithoutCollision  = (currentCollisionTimestamp - lastCollisionTimestamp);
        
//        System.out.format ("TC: %s / %s\n",
//            timespanWithoutCollision,
//            MINIMUM_COLLISION_IGNORE_TIME);
        
        if (timespanWithoutCollision < MINIMUM_COLLISION_IGNORE_TIME)
        {
          System.out.format ("Too little time from last collision: %s < %s\n",
                             timespanWithoutCollision,
                             MINIMUM_COLLISION_IGNORE_TIME);
          return;
        }
        
        lastCollisionTimestamp = currentCollisionTimestamp;
        
        observingPlayer = observingModel.getPlayerNumber ();
        observedPlayer  = observedModel.getPlayerNumber  ();
        
        // Enemy unit hit? => Perform action for active player.
        if (observingPlayer != observedPlayer)
        {
          message3D.hide ();
          
          messageText = String.format
          (
            "{%s} \u2194 {%s}",
            observingModel.getPlayerNumber (),
            observedModel.getPlayerNumber  ()
          );
          message3D.setText (messageText);
          message3D.showFor (1000);
          changePlayer    ();
//          activePlayer = -1;
        }
      }
      // Enemy unit is null?
      else
      {
        System.out.println ("[?] Collision with NULL detected.");
      }
    }
  }
  
  private void changePlayer ()
  {
    int previousActivePlayer = activePlayer;
    
    if (previousActivePlayer == 1)
    {
      activePlayer = 2;
    }
    else
    {
      activePlayer = 1;
    }
    
    // Unmark the previous active player's characters.
    for (MarkerModel model : detectMarkers.getMarkerModelsForPlayer (previousActivePlayer))
    {
      model.setMarked (false);
    }
    
    // Mark the current active player's characters.
    for (MarkerModel model : detectMarkers.getMarkerModelsForPlayer (activePlayer))
    {
      model.setMarked (true);
    }
  }
  
  
  
  private J3dNyARParam readCameraParams(String fnm)
  {
    // TODO: ADDED BY MYSELF.
    J3dNyARParam cameraParams = null;  
    try
    {
//      cameraParams = new J3dNyARParam();
//      cameraParams.loadARParamFromFile(fnm);
      
      try
      {
        cameraParams = (J3dNyARParam) J3dNyARParam.loadARParamFile
        (
          new FileInputStream (new File (fnm))
        );
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace ();
      }
      
      cameraParams.changeScreenSize (PWIDTH, PHEIGHT);
    }
    catch(NyARException e)
    {
      System.out.println("Could not read camera parameters from " + fnm);
      System.exit(1);
    }
    return cameraParams;
  }  // end of readCameraParams()



  private Canvas3D createCanvas3D()
  /* Build a 3D canvas for a Universe which contains
     the 3D scene and view 
            univ --> locale --> scene BG
                          |
                           ---> view BG  --> Canvas3D
                              (set up using camera cameraParams)
   */
  {
    Canvas3D              c3d    = null;
    GraphicsConfiguration config = null;
    Locale                locale = new Locale (new VirtualUniverse ());
    
    locale.addBranchGraph (createSceneGraph ());   // add the scene
    
    // get the preferred graphics configuration for the default screen
    config = SimpleUniverse.getPreferredConfiguration();
    
    c3d    = new Canvas3D (config);
    locale.addBranchGraph (createView (c3d));  // add view branch
    
    return c3d;
  }  // end of createCanvas3D()



  private BranchGroup createSceneGraph()
  /* The scene graph:
         sceneBG 
               ---> lights
               |
               ---> background
               |
               -----> tg1 ---> model1  
               -----> tg2 ---> model2 
               |
               ---> behavior  (controls the bg and the tg's of the models)
  */
  {
    BranchGroup   sceneBG              = null;
    Background    background           = null;
    MarkerModel   robotMarkerModel     = null;
    MarkerModel   cowMarkerModel       = null;
    MarkerModel   sphereBoyMarkerModel = null;
//    DetectMarkers detectMarkers        = null;
    
    sceneBG    = new BranchGroup ();
    background = makeBackground  ();
    
    lightScene       (sceneBG);        // add lights
    sceneBG.addChild (background);     // add background
    
//    detectMarkers = new DetectMarkers  (this);
    detectMarkers.addCollisionListener (this);
    
    // the "hiro" marker uses a robot model, scaled by 0.15 units, with no coords file
//    MarkerModel mm1 = new MarkerModel("patt.hiro", "robot.3ds", 0.15, false);
    robotMarkerModel = new MarkerModel ("marker_circleSegment.pat", "robot.3ds", 0.15, false);
    robotMarkerModel.setPlayerNumber (1);
    robotMarkerModel.getActiveMark ().setAppearance (DefaultMaterials.getAppearance (DefaultMaterials.MaterialName.KILGARD_YELLOW_RUBBER));
//    robotMarkerModel.setMarked       (true);
    // creation was successful
    addAndRegisterMarker (robotMarkerModel, sceneBG, detectMarkers);
//    if (mm1.getMarkerInfo() != null)
//    {
//      sceneBG.addChild (mm1.getMoveTg ());
//      detectMarkers.addMarker (mm1);
//    }
    
    // the "kanji" marker uses a cow model, scaled by 0.12 units, with coords file
    cowMarkerModel = new MarkerModel("patt.kanji", "cow.obj", 0.12, true);
    cowMarkerModel.setPlayerNumber (9);
    addAndRegisterMarker (cowMarkerModel, sceneBG, detectMarkers);
    
    
    // the "black-white-shield_001" marker uses a robot model, scaled by 0.15 units, with no coords file
//    MarkerModel mmShield = new MarkerModel("black-white-shield_001.pat", "bunny.obj", 0.15, false);
    sphereBoyMarkerModel = new MarkerModel ("marker_BlackWhiteShield_001.pat", "Figur_002.wrl", 0.15, false);
//    sphereBoyMarkerModel.setCanAct (true);
    sphereBoyMarkerModel.setPlayerNumber (2);
//    sphereBoyMarkerModel.setMarked       (true);
    addAndRegisterMarker (sphereBoyMarkerModel, sceneBG, detectMarkers);
    sphereBoyMarkerModel.getActiveMark ().setAppearance (DefaultMaterials.getAppearance (DefaultMaterials.MaterialName.COLE_RED_ALLOY));
//    ModelAppearanceAssigner appearanceAssigner = new ModelAppearanceAssigner
//    (
//      sphereBoyMarkerModel.getMoveTg ()
//    );
//    QuickShaderAppearance   shaderAppearance   = new QuickShaderAppearance
//    (
//      "myresources/misc-test_024.vert",
//      "myresources/misc-test_024.frag"
//    );
//    shaderAppearance.setMaterial (new Material
//    (
//      ColorConstants.RED_3F,
//      ColorConstants.BLACK_3F,
//      ColorConstants.RED_3F,
//      ColorConstants.WHITE_3F,
//      100.0f
//    ));
//    shaderAppearance.setMaterial (OpenGLMaterials.getMaterial (OpenGLMaterials.MaterialName.GOLD_2));
//    appearanceAssigner.assignAppearance (shaderAppearance);
    
    
    // create a NyAR multiple marker behaviour
    sceneBG.addChild (new NyARMarkersBehavior (cameraParams, background, detectMarkers));
    
    // Append the message object to the scene graph.
    sceneBG.addChild (message3D.getNode3D ());
    
    sceneBG.compile ();       // optimize the sceneBG graph
    return sceneBG;
  }  // end of createSceneGraph()



  private void lightScene(BranchGroup sceneBG)
  /* One ambient light, 2 directional lights */
  {
    Color3f        white  = new Color3f        (1.0f, 1.0f, 1.0f);
    BoundingSphere bounds = new BoundingSphere (new Point3d(0,0,0), BOUNDSIZE); 

    // Set up the ambient light
    AmbientLight ambientLightNode = new AmbientLight(white);
    ambientLightNode.setInfluencingBounds(bounds);
    sceneBG.addChild(ambientLightNode);
    
    /*
    // TODO: OWN TEST
    // Set up the directional lights
    Vector3f light1Direction  = new Vector3f(0.0f, 3.0f, -1.0f);
    Vector3f light2Direction  = new Vector3f(0.0f, -1.0f, 1.0f);
    */
    
    // Set up the directional lights
    Vector3f light1Direction  = new Vector3f(-1.0f, -1.0f, -1.0f);
       // left, down, backwards 
    Vector3f light2Direction  = new Vector3f(1.0f, -1.0f, 1.0f);
       // right, down, forwards

    DirectionalLight light1 =  new DirectionalLight(white, light1Direction);
    light1.setInfluencingBounds(bounds);
    sceneBG.addChild(light1);

    DirectionalLight light2 = new DirectionalLight(white, light2Direction);
    light2.setInfluencingBounds(bounds);
    sceneBG.addChild(light2);
  }  // end of lightScene()



  private Background makeBackground()
  // the background will be the current image captured by the camera
  { 
    Background     bg     = new Background     ();
    BoundingSphere bounds = new BoundingSphere ();
    bounds.setRadius        (10.0);
    bg.setApplicationBounds (bounds);
    bg.setImageScaleMode    (Background.SCALE_FIT_ALL);
    bg.setCapability        (Background.ALLOW_IMAGE_WRITE);  // so can change image

    return bg;
  }  // end of makeBackground()



  private BranchGroup createView(Canvas3D c3d)
  // create a view graph using the camera parameters
  {
    BranchGroup    viewBG        = new BranchGroup    ();
    TransformGroup viewGroup     = new TransformGroup ();
    Transform3D    viewTransform = new Transform3D    ();
    View           view          = new View           ();
    ViewPlatform   viewPlatform  = new ViewPlatform   ();
    
    view.attachViewPlatform (viewPlatform);
    view.addCanvas3D        (c3d);
    
    view.setPhysicalBody        (new PhysicalBody());
    view.setPhysicalEnvironment (new PhysicalEnvironment());

    view.setCompatibilityModeEnable (true);
    view.setProjectionPolicy (View.PERSPECTIVE_PROJECTION);
    view.setLeftProjection   (cameraParams.getCameraTransform ());   // camera projection
    
    viewTransform.rotY           (Math.PI);   // rotate 180 degrees
    viewTransform.setTranslation (new Vector3d(0.0, 0.0, 0.0));   // start at origin
    viewGroup.setTransform       (viewTransform);
    viewGroup.addChild           (viewPlatform);

    viewBG.addChild (viewGroup);

    return viewBG;
  }  // end of createView()



  public void setStatus(String msg)
  // called from DetectMarkers
  {
    statusTA.setText(msg);
  }  // end of setStatus()
  
  
  private void addAndRegisterMarker
  (
    MarkerModel   markerModel,
    BranchGroup   sceneBG,
    DetectMarkers detectMarkers
  )
  {
    if (markerModel.getMarkerInfo() != null)
    {
      sceneBG.addChild        (markerModel.getMoveTg ());
      detectMarkers.addMarker (markerModel);
    }
  }


  // ------------------------------------------------------------

  public static void main(String args[])
  {  new MultiNyAR();  }
    
    
} // end of MultiNyAR class
