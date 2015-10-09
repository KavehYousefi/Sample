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
import ky.Message3D;

import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.java3d.utils.*;


@SuppressWarnings ({"serial"})
public class MultiNyAR
extends      JFrame
implements   CollisionListener, MarkerModelListener
{
  private static final String CAMERA_PARAMETERS_FILE = "Data/camera_para.dat";
  private static final int    PANEL_WIDTH            = 320;   // size of panel
  private static final int    PANEL_HEIGHT           = 240; 
  private static final double BOUNDS_SIZE            = 100.0; // larger than world
  
  private J3dNyARParam         cameraParams;
  
  private Message3D            message3D;
  private CharacterStatusPanel characterStatusPanel;
  
  private PlayerManager        playerManager;
  private DetectMarkers        detectMarkers;
  private GameState            gameState;
  
  
  public MultiNyAR()
  {
    super ("ARCs - An NyARToolkit Based Augmented Reality Game");
    
    this.playerManager        = new PlayerManager ();
    this.detectMarkers        = new DetectMarkers (this);
    this.cameraParams         = readCameraParams (CAMERA_PARAMETERS_FILE);
    this.gameState            = GameState.RUNNING;
    this.characterStatusPanel = new CharacterStatusPanel (detectMarkers, PANEL_WIDTH, 80);
    this.message3D            = new Message3D ();
    
    message3D.setPosition     (new Point3d (0.0, 0.0, 0.0));
    
    Container contentPane = getContentPane ();
    // Create a JPanel in the center of JFrame.
    JPanel    canvasPanel = new JPanel     ();
    
    canvasPanel.setLayout        (new BorderLayout ());
    canvasPanel.setPreferredSize (new Dimension (PANEL_WIDTH, PANEL_HEIGHT));
    contentPane.add              (canvasPanel, BorderLayout.CENTER);
    
    // put the 3D canvas inside the JPanel
    canvasPanel.add (createCanvas3D (), BorderLayout.CENTER);
    contentPane.add (characterStatusPanel.getPanel (), BorderLayout.NORTH);
    
    // configure the JFrame
    setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE );
    pack                     ();
    setVisible               (true);
    setExtendedState         (getExtendedState () | JFrame.MAXIMIZED_BOTH);
    
    this.playerManager.setActivePlayer (1);
    updateMarkerModelStates            ();
  }  // end of MultiNyAR()
  
  
  
  public void showMessage3D (String messageText, int durationInMilliseconds)
  {
    message3D.hide    ();
    message3D.setText (messageText);
    message3D.showFor (durationInMilliseconds);
  }
  
  public PlayerManager getPlayerManager ()
  {
    return playerManager;
  }
  
  public int getActivePlayer ()
  {
    return playerManager.getActivePlayer ();
  }
  
  public int getInactivePlayer ()
  {
    return playerManager.getInactivePlayer ();
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "CollisionListener".        -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public void collisionStarted (CollisionEvent event)
  {
    if (gameState.equals (GameState.OVER))
    {
      return;
    }
    
    if (gameState.equals (GameState.PREPARING))
    {
      return;
    }
    
    MarkerModel   observingModel = null;
    MarkerModel   observedModel  = null;
    MarkerModel   attackingModel = null;
    MarkerModel   targetModel    = null;
    AttackHandler attackHandler  = null;
    int           activePlayer   = 0;
    int           inactivePlayer = 0;
    
    observingModel = event.getObservingModel ();
    observedModel  = event.getObservedModel  ();
    activePlayer   = getActivePlayer         ();
    inactivePlayer = getInactivePlayer       ();
    
    if (isNullCollision (observingModel, observedModel))
    {
      return;
    }
    else if (isDefenseGesture (observingModel, observedModel))
    {
      showMessage3D ("DEFENDING", 1000);
      if (observingModel.isCharacter ())
      {
        observingModel.getCharacterInfo ().setDefending (true);
        observingModel.setCanAct                        (false);
      }
      else
      {
        observedModel.getCharacterInfo ().setDefending (true);
        observedModel.setCanAct                        (false);
      }
    }
    else if (isSamePlayerCollision (observingModel, observedModel))
    {
      return;
    }
    else if (isDeadModelCollision (observingModel, observedModel))
    {
      return;
    }
    else if (isValidAttack (observingModel, observedModel))
    {
      showMessage3D ("ATTACK", 1000);
      
      // Determine which is the "active" model and which the target.
      if (playerManager.isActivePlayer (observingModel.getPlayerNumber ()))
      {
        attackingModel = observingModel;
        targetModel    = observedModel;
      }
      else
      {
        attackingModel = observedModel;
        targetModel    = observingModel;
      }
      
      attackHandler = new AttackHandler ();
      attackHandler.processAttack       (attackingModel, targetModel);
      
      attackingModel.setCanAct (false);
    }
    
    
    boolean isPlayerDone = true;
    boolean hasPlayerWon = true;
    
    isPlayerDone = isPlayerDone  (activePlayer);
    hasPlayerWon = hasPlayerLost (inactivePlayer);
    
    if (hasPlayerWon)
    {
      gameState = GameState.OVER;
      JOptionPane.showMessageDialog (null, "PLAYER " + activePlayer + " HAS WON!");
      return;
    }
    
    if (isPlayerDone)
    {
      changePlayer ();
    }
    
    printData (null);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "MarkerModelListener".      -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public void markerModelUpdated (MarkerModel markerModel)
  {
    if (isMarkerModelSearchingPlayer (markerModel))
    {
      if (isMarkerModelOnLeftArenaSide (markerModel))
      {
        markerModel.setPlayerNumber (Player.PLAYER_1);
      }
      else
      {
        markerModel.setPlayerNumber (Player.PLAYER_2);
      }
      
      setMarkerModelStates (markerModel);
    }
    
    printData (markerModel);
    characterStatusPanel.update ();
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void printData (MarkerModel markerModel)
  {
    StringBuilder output = new StringBuilder ();
    
    output.append ("Time stamp: ");
    output.append (System.currentTimeMillis ());
    output.append ("\n");
    
    for (MarkerModel currentModel : detectMarkers.getMarkerModels ())
    {
      output.append ("[");
      output.append (currentModel.getPlayerNumber ());
      output.append ("] ");
      
      if (! currentModel.getCharacterInfo ().isAlive ())
      {
        output.append ("{\u271D} ");
      }
      
      if ((markerModel != null) && (currentModel.equals (markerModel)))
      {
        output.append ("# ");
      }
      
      if (currentModel.getCharacterInfo () != null)
      {
        output.append (currentModel.getModelName ());
        output.append (": ");
        output.append (currentModel.getCharacterInfo ().getStatistics ());
      }
      
      output.append (" at ");
      output.append (currentModel.getPosition ());
      
      if (currentModel.getCharacterInfo ().isDefending ())
      {
        output.append ("<DEFENSE> ");
      }
      
      if (currentModel.canAct ())
      {
        output.append (" [can_act]");
      }
      
      output.append ("   props: ");
      output.append (currentModel.getProperties ());
      
      output.append ("\n");
    }
  }
  
  private void changePlayer ()
  {
    int previousActivePlayer = 0;
    int currentActivePlayer  = 0;
    
    previousActivePlayer = playerManager.getActivePlayer ();
    playerManager.activateNextPlayer                     ();
    currentActivePlayer  = playerManager.getActivePlayer ();
    
    // Unmark the previous active player's characters.
    for (MarkerModel model : detectMarkers.getMarkerModelsForPlayer (previousActivePlayer))
    {
//      model.setMarked (false);
      model.setCanAct (false);
      
      if (! model.getCharacterInfo ().isAlive ())
      {
        model.setCanCollide (false);
      }
    }
    
    // Mark the current active player's characters.
    for (MarkerModel model : detectMarkers.getMarkerModelsForPlayer (currentActivePlayer))
    {
      if (model.getCharacterInfo ().isAlive ())
      {
//        model.setMarked (true);
        model.setCanAct (true);
      }
      else
      {
//        model.setMarked     (false);
        model.setCanAct     (false);
        model.setCanCollide (false);
      }
    }
  }
  
  private void updateMarkerModelStates ()
  {
    for (MarkerModel model : detectMarkers.getMarkerModels ())
    {
      boolean isModelOfActivePlayer = false;
      boolean isAlive               = false;
      
      isModelOfActivePlayer = playerManager.isActivePlayer (model.getPlayerNumber ());
      isAlive               = model.getCharacterInfo ().isAlive ();
      
      if (isModelOfActivePlayer && isAlive)
      {
        model.setCanAct (true);
      }
      else
      {
        model.setCanAct (false);
      }
    }
  }
  
  
  private J3dNyARParam readCameraParams (String cameraParameterFileName)
  {
    J3dNyARParam cameraParams = null;  
    try
    {
        // XXX: INVALID!.
//      cameraParams = new J3dNyARParam();
//      cameraParams.loadARParamFromFile(fnm);
      
      try
      {
        cameraParams = (J3dNyARParam) J3dNyARParam.loadARParamFile
        (
          new FileInputStream (new File (cameraParameterFileName))
        );
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace ();
      }
      
      cameraParams.changeScreenSize (PANEL_WIDTH, PANEL_HEIGHT);
    }
    catch (NyARException e)
    {
      JOptionPane.showMessageDialog
      (
        this,
        "Could not read camera parameters from " +
         cameraParameterFileName
      );
      System.exit (1);
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
    config = SimpleUniverse.getPreferredConfiguration ();
    
    c3d    = new Canvas3D (config);
    locale.addBranchGraph (createView (c3d));  // add view branch
    
    return c3d;
  }  // end of createCanvas3D()
  
  
  
  private BranchGroup createSceneGraph ()
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
    BranchGroup        sceneBG             = null;
    Background         background          = null;
    MarkerModelFactory markerObjectFactory = null;
    
    
    sceneBG             = new BranchGroup         ();
    background          = makeBackground          ();
    markerObjectFactory = new MarkerModelFactory (this, detectMarkers);
    
    lightScene       (sceneBG);        // add lights
    sceneBG.addChild (background);     // add background
    
//    detectMarkers = new DetectMarkers  (this);
    detectMarkers.addCollisionListener (this);
    
    
//    // the "kanji" marker uses a cow model, scaled by 0.12 units, with coords file
//    cowMarkerModel = new MarkerModel ("patt.kanji", "cow.obj", 0.12, true);
//    cowMarkerModel.setPlayerNumber (9);
//    addAndRegisterMarker (cowMarkerModel, sceneBG, detectMarkers);
    
    /*
    addAndRegisterMarkerNode
    (
      new MarkerNode ("marker_BlackWhiteShield_001.pat",
                      new Sphere (0.1f, new WireframeAppearance ()),
                      "Sphere", 0.15, false),
      sceneBG,
      detectMarkers
    );
    */
    
    
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("bunny",    Player.SEARCHING_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("starship", Player.SEARCHING_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("bird",     Player.SEARCHING_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("snake",    Player.SEARCHING_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("boy",      Player.SEARCHING_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("robot",    Player.SEARCHING_PLAYER), sceneBG, detectMarkers);
    
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("defenseMarker1", Player.PLAYER_1), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("defenseMarker2", Player.PLAYER_2), sceneBG, detectMarkers);
    
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("characterSwapper", Player.NO_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("defenseForAttack", Player.NO_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("rain",             Player.NO_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("statisticsMixer",  Player.NO_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("resurrection",     Player.NO_PLAYER), sceneBG, detectMarkers);
    addAndRegisterMarkerModel (markerObjectFactory.getMarkerModelByName ("undefend",         Player.NO_PLAYER), sceneBG, detectMarkers);
    
    // Create a NyAR multiple marker behavior.
    sceneBG.addChild (new NyARMarkersBehavior (cameraParams, background, detectMarkers));
    
    // Append the message object to the scene graph.
    sceneBG.addChild (message3D.getNode3D ());
//    sceneBG.addChild (characterStatusPanel.getNode ());
    
    sceneBG.compile ();       // optimize the sceneBG graph
    return sceneBG;
  }  // end of createSceneGraph()
  
  
  
  private void lightScene (BranchGroup sceneBG)
  /* One ambient light, 2 directional lights */
  {
    Color3f        white  = new Color3f        (1.0f, 1.0f, 1.0f);
    BoundingSphere bounds = new BoundingSphere (new Point3d (0,0,0), BOUNDS_SIZE); 

    // Set up the ambient light
    AmbientLight ambientLightNode = new AmbientLight(white);
    ambientLightNode.setInfluencingBounds(bounds);
    sceneBG.addChild(ambientLightNode);
    
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
  
  
  
  private Background makeBackground ()
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
  
  
  
  private BranchGroup createView (Canvas3D c3d)
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
    view.setProjectionPolicy        (View.PERSPECTIVE_PROJECTION);
    view.setLeftProjection          (cameraParams.getCameraTransform ());   // camera projection
    
    viewTransform.rotY           (Math.PI);   // rotate 180 degrees
    viewTransform.setTranslation (new Vector3d(0.0, 0.0, 0.0));   // start at origin
    viewGroup.setTransform       (viewTransform);
    viewGroup.addChild           (viewPlatform);
    
    viewBG.addChild (viewGroup);
    
    return viewBG;
  }  // end of createView()
  
  
  private void addAndRegisterMarkerModel
  (
    MarkerModel   markerModel,
    BranchGroup   sceneBG,
    DetectMarkers detectMarkers
  )
  {
    if (markerModel.getMarkerInfo () != null)
    {
      sceneBG.addChild                   (markerModel.getMoveTg ());
      detectMarkers.addMarker            (markerModel);
      markerModel.addMarkerModelListener (this);
    }
  }
  
  private boolean isNullCollision
  (
    MarkerModel observingModel,
    MarkerModel observedModel
  )
  {
    return ((observingModel == null) || (observedModel  == null));
  }
  
  private boolean isDefenseGesture
  (
    MarkerModel observingModel,
    MarkerModel observedModel
  )
  {
    if ((observingModel.isCharacter     () && observedModel.isDefenseMarker ()) ||
        (observingModel.isDefenseMarker () && observedModel.isCharacter    ()))
    {
      return (observingModel.getPlayerNumber () == observedModel.getPlayerNumber ());
    }
    else
    {
      return false;
    }
  }
  
  private boolean isSamePlayerCollision
  (
    MarkerModel observingModel,
    MarkerModel observedModel
  )
  {
    return (observingModel.getPlayerNumber () == observedModel.getPlayerNumber ());
  }
  
  private boolean isDeadModelCollision
  (
    MarkerModel observingModel,
    MarkerModel observedModel
  )
  {
    return ((! observingModel.getCharacterInfo ().isAlive ()) ||
            (! observedModel.getCharacterInfo  ().isAlive ()));
  }
  
  private boolean isValidAttack
  (
    MarkerModel observingModel,
    MarkerModel observedModel
  )
  {
    return
    (
      (observingModel.getPlayerNumber () != observedModel.getPlayerNumber ()) &&
      (observingModel.isCharacter     ()) &&
      (observedModel.isCharacter      ())
    );
  }
  
  private boolean isPlayerDone (int player)
  {
    for (MarkerModel model : detectMarkers.getMarkerModelsForPlayer (player))
    {
      if ((model.isCharacter ()) && (model.canAct ()))
      {
        return false;
      }
    }
    
    return true;
  }
  
  private boolean hasPlayerLost (int player)
  {
    for (MarkerModel model : detectMarkers.getMarkerModelsForPlayer (player))
    {
      if ((model.isCharacter ()) && (model.getCharacterInfo ().isAlive ()))
      {
        return false;
      }
    }
    
    return true;
  }
  
  private boolean isMarkerModelOnLeftArenaSide (MarkerModel markerModel)
  {
    if (markerModel.getPosition () == null)
    {
      return false;
    }
    else
    {
      return (markerModel.getPosition ().getX () > 0.0);
    }
  }
  
  private boolean isMarkerModelSearchingPlayer (MarkerModel markerModel)
  {
    return (markerModel.getPlayerNumber () == Player.SEARCHING_PLAYER);
  }
  
  private void setMarkerModelStates (MarkerModel markerModel)
  {
    if (playerManager.isActivePlayer (markerModel.getPlayerNumber ()))
    {
      markerModel.setCanAct (true);
      
      if (! markerModel.getCharacterInfo ().isAlive ())
      {
        markerModel.setCanCollide (false);
      }
    }
    else
    {
      markerModel.setCanAct (false);
    }
  }
  
  // ------------------------------------------------------------

  public static void main(String args[])
  {
    new MultiNyAR();
  }
  
} // end of MultiNyAR class
