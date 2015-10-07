package adtest01;
// MarkerModel.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, April 2010

import java.awt.image.BufferedImage;

/* Holds NyARToolkit marker information and the Java3D scene graph for its
   associated model.

   The model is loaded using the PropManager class, which is described in Chapter 16
   of "Killer Game Programming in Java" (http://fivedots.coe.psu.ac.th/~ad/jg/ch9/)
*/


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.media.j3d.*;
import javax.vecmath.*;

import ky.ActiveMark;
import ky.DiskBoundsRenderer;
import ky.angle.EulerAngles;
import ky.angle.SelmanMatrixToEulerAngles;
import ky.appearance.ColorConstants;
import ky.gamelogic.CharacterInfo;
import ky.gamelogic.Effect;
import ky.gamelogic.Element;
import ky.gamelogic.Statistics;
import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.core.transmat.NyARTransMatResult;


public class MarkerModel
{
  private static final double MARKER_SIZE   = 0.095;    // 95 cm width and height in Java 3D world units
  private static final int    MARKER_WIDTH  = 16;
  private static final int    MARKER_HEIGHT = 16;
  
  private Path     markerFilePath;
  private String   modelName;
  private NyARCode markerInfo;         // NYArToolkit marker details
  
  // For moving the marker model
  private TransformGroup     moveTransformGroup;
  
  // For changing the model's visibility
  private Switch             visibilitySwitch;
  private boolean            isVisible;
  
  // For smoothing the transforms applied to the model
  private SmoothMatrix       smoothMatrix;
  
  // Number of times marker for this model not detected.
  private int                numTimesLost = 0;
  
  protected MarkerModelType markerObjectType;
  protected Point3d          posInfo;      // Model position.
  protected EulerAngles      orientation;  // Model orientation.
  
  private boolean                   canAct;
  private boolean                   canCollide;
  private int                       playerNumber;
  private ActiveMark                activeMark;
  private DiskBoundsRenderer        diskBoundsRenderer;
  private CharacterInfo             characterInfo;
  // Holds main 3D content; directly under the "visibilitySwitch".
  private TransformHierarchy        transformHierarchy;
  private Effect                    effect;
  private List<MarkerModelListener> listeners;
  private CollisionPolicy           collisionPolicy;
  private List<String>              properties;
  private BufferedImage             icon;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a marker model.
   * </div>
   * 
   * @param markerFilePath      The file containing the marker pattern.
   * @param modelName           A name for the model.
   * @param transformHierarchy  The transform hierarchy containing the
   *                            3D content.
   */
  public MarkerModel
  (
    Path               markerFilePath,
    String             modelName,
    TransformHierarchy transformHierarchy
  )
  {
    this.markerInfo       = null;
    this.markerFilePath   = markerFilePath;
    this.modelName        = modelName;
    this.effect           = null;
    this.markerObjectType = MarkerModelType.EFFECT;
    this.posInfo          = null;
    this.orientation      = null;
    this.listeners        = new ArrayList<MarkerModelListener> ();
    this.collisionPolicy  = createCollisionPolicy              ();
    this.playerNumber     = Player.NO_PLAYER;
    this.properties       = new ArrayList<String>              ();
    this.icon             = createEmptyIcon                    ();
    
    // build a branch for the model: TG --> Switch --> TG --> model
    this.transformHierarchy = transformHierarchy;
    
    // create switch for model visibility 
    visibilitySwitch = new Switch  ();
    visibilitySwitch.setCapability (Switch.ALLOW_SWITCH_WRITE);
    visibilitySwitch.addChild      (transformHierarchy.getRootTransformGroup ());
    visibilitySwitch.setWhichChild (Switch.CHILD_NONE );   // make invisible
    isVisible = false;
    
//    modelTG.addChild (interactionBounds.getRootNode ());
    this.canAct             = false;
    this.canCollide         = true;
    this.activeMark         = new ActiveMark ();
    this.diskBoundsRenderer = new DiskBoundsRenderer (0.160000 / 2.0, 20);
    this.activeMark.setPosition (new Point3d  (0.000, 0.000, 0.100));
    this.activeMark.setScaling  (new Vector3d (0.015, 0.030, 0.015));
    this.characterInfo      = new CharacterInfo (modelName, Element.NONE);
    
    
    // create transform group for positioning the model
    moveTransformGroup = new TransformGroup ();
    moveTransformGroup.setCapability        (TransformGroup.ALLOW_TRANSFORM_WRITE);  // so this tg can change
    moveTransformGroup.addChild             (visibilitySwitch);
    
    moveTransformGroup.addChild (activeMark.getRootNode         ());
    moveTransformGroup.addChild (diskBoundsRenderer.getRootNode ());
    
    // load marker info
    try
    {
      /*
       * XXX: INVALID!
      markerInfo = new NyARCode(16,16);    // default integer width, height
      markerInfo.loadARPattFromFile(MARKER_DIR+markerName);  // load marker image
      */
      
      try
      {
        markerInfo = NyARCode.createFromARPattFile
        (
          new FileInputStream (markerFilePath.toFile ()),
          MARKER_WIDTH, MARKER_HEIGHT
        );
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
    }
    catch (NyARException e)
    {
      System.out.println (e);  
      markerInfo = null;
    }
    
    smoothMatrix = new SmoothMatrix();
  }  // end of MarkerModel()
  
  
  public boolean canAct ()
  {
    return canAct;
  }
  
  public void setCanAct (boolean canAct)
  {
    this.canAct = canAct;
    
    if (canAct)
    {
      diskBoundsRenderer.setVisible (true);
    }
    else
    {
      diskBoundsRenderer.setVisible (false);
    }
  }
  
  public boolean canCollide ()
  {
    return canCollide;
  }
  
  public void setCanCollide (boolean canCollide)
  {
    this.canCollide = canCollide;
  }
  
  public boolean canCollideWith (MarkerModel otherModel)
  {
    return collisionPolicy.canCollideWith (this, otherModel);
  }
  
  public int getPlayerNumber ()
  {
    return playerNumber;
  }
  
  public void setPlayerNumber (int playerNumber)
  {
    this.playerNumber = playerNumber;
    
    setActiveBoundsAppearance (playerNumber);
  }
  
  public CharacterInfo getCharacterInfo ()
  {
    return characterInfo;
  }
  
  public void setCharacterInfo (CharacterInfo characterInfo)
  {
    this.characterInfo = characterInfo;
  }
  
  public Statistics getStatistics ()
  {
    return characterInfo.getStatistics ();
  }
  
  public boolean isCharacter ()
  {
    return markerObjectType.equals (MarkerModelType.CHARACTER);
  }
  
  public boolean isEffect ()
  {
    return markerObjectType.equals (MarkerModelType.EFFECT);
  }
  
  public boolean isDefenseMarker ()
  {
    return markerObjectType.equals (MarkerModelType.DEFENSE_MARKER);
  }
  
//  public void setMarked (boolean isMarked)
//  {
//    if (isMarked)
//    {
//      activeMark.show ();
//    }
//    else
//    {
//      activeMark.hide ();
//    }
//  }
  
  public ActiveMark getActiveMark ()
  {
    return activeMark;
  }
  
  public void setInteractionRadius (double interactionRadius)
  {
    this.diskBoundsRenderer.setDiskRadius (interactionRadius);
  }
  
  public DiskBoundsRenderer getBoundsRenderer ()
  {
    return diskBoundsRenderer;
  }
  
  
  
  public String getModelName ()
  {
    return modelName;
  }
  
  
  
  
  
  
  public String getNameInfo ()
  {
    return markerFilePath.getFileName () + " / " + modelName;
  }
  
  public NyARCode getMarkerInfo ()
  {
    return markerInfo;
  }
  
  public double getMarkerWidth ()
  {
    return MARKER_SIZE;   // markerInfo.getWidth() not valid since requires Java 3D units
  }
  
  public TransformHierarchy getTransformHierarchy ()
  {
    return transformHierarchy;
  }
  
  public TransformGroup getMoveTg ()
  {
    return moveTransformGroup;
  }
  
  public TransformGroup getModelTransformGroup ()
  {
    return transformHierarchy.getRootTransformGroup ();
  }
  
  public void moveModel (NyARTransMatResult transMat)
  // detected marker so update model's moveTG
  {
    checkIfStillAlive ();
    
    if (! getCharacterInfo ().isAlive ())
    {
      return;
    }
    
    visibilitySwitch.setWhichChild (Switch.CHILD_ALL);   // make visible
    isVisible = true;
    
    smoothMatrix.add (transMat);
    
    Matrix4d    mat   = smoothMatrix.get ();
    Transform3D t3d   = new Transform3D  (mat);
    int         flags = t3d.getType      ();
    
    if ((flags & Transform3D.AFFINE) == 0)
    {
      System.out.println ("Ignoring non-affine transformation");
    }
    else
    {
      if (moveTransformGroup != null)
      {
        moveTransformGroup.setTransform (t3d);
      }
      
      // System.out.println("transformation matrix: " + mat);
      calcPosition  (mat);
      calcEulerRots (mat);
    }
    
    if (effect != null)
    {
      effect.update ();
    }
    
    update ();
  }  // end of moveModel()
  
  public void resetNumTimesLost()
  {
    numTimesLost = 0;
  }
  
  public void incrNumTimesLost()
  {
    numTimesLost++;
  }
  
  public int getNumTimesLost()
  {
    return numTimesLost;
  }
  
  public void hideModel()
  {
    visibilitySwitch.setWhichChild (Switch.CHILD_NONE);   // make model invisible
    isVisible = false;
  }
  
  public boolean isVisible()
  {
    return isVisible;
  }
  
  
  public MarkerModelType getMarkerModelType ()
  {
    return markerObjectType;
  }
  
  public void setMarkerObjectType (MarkerModelType type)
  {
    this.markerObjectType = type;
  }
  
  public Point3d getPosition ()
  {
    return posInfo;
  }
  
  public EulerAngles getOrientation ()
  {
    return orientation;
  }
  
  
  public Effect getEffect ()
  {
    return effect;
  }
  
  public void setEffect (Effect effect)
  {
    this.effect = effect;
  }
  
  public void update ()
  {
    checkIfStillAlive ();
    listeners.forEach (listener -> listener.markerModelUpdated (this));
  }
  
  public void addMarkerModelListener (MarkerModelListener listener)
  {
    if (listener != null)
    {
      listeners.add (listener);
    }
  }
  
  public void removeMarkerModelListener (MarkerModelListener listener)
  {
    if (listener != null)
    {
      listeners.remove (listener);
    }
  }
  
  
  public List<String> getProperties ()
  {
    return properties;
  }
  
  public void addProperty (String property)
  {
    if (! properties.contains (property))
    {
      properties.add (property);
    }
  }
  
  public void removeProperty (String property)
  {
    properties.remove (property);
  }
  
  public boolean hasProperty (String property)
  {
    return properties.contains (property);
  }
  
  
  public BufferedImage getIcon ()
  {
    return icon;
  }
  
  public void setIcon (BufferedImage icon)
  {
    this.icon = icon;
  }
  
  
  @Override
  public String toString ()
  {
    return String.format ("MarkerModel(name=%s, player=%s)",
                          modelName, playerNumber);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private CollisionPolicy createCollisionPolicy ()
  {
    CompoundCollisionPolicy compoundPolicy = null;
    
    compoundPolicy = new CompoundCollisionPolicy ();
    compoundPolicy.addPolicy (new DefaultCollisionPolicy  ());
    compoundPolicy.addPolicy (new DistanceCollisionPolicy (0.160000));
    compoundPolicy.addPolicy (new OpponentCollisionPolicy ());
//    compoundPolicy.addPolicy (new DefenseMarkerCollisionPolicy ());
    
    return compoundPolicy;
  }
  
  private void calcPosition (Matrix4d mat)
  // extract the (x,y,z) position vals stored in the matrix
  {
    // convert to cm and round
    double x = roundToNumPlaces (mat.getElement(0,3) * 100.0, 1);
    double y = roundToNumPlaces (mat.getElement(1,3) * 100.0, 1);
    double z = roundToNumPlaces (mat.getElement(2,3) * 100.0, 1);
    
    posInfo = new Point3d (x, y, z);
  }  // end of reportPosition()
  
  private double roundToNumPlaces (double val, int numPlaces) 
  {
    double power = Math.pow   (10, numPlaces);
    long   temp  = Math.round (val * power);
    
    return ((double) temp)/power;
  }
  
  private void calcEulerRots (Matrix4d mat)
  {
    SelmanMatrixToEulerAngles matrixToEulerAngles = null;
    Matrix3d                  matrix3d            = null;
    
    matrixToEulerAngles = new SelmanMatrixToEulerAngles ();
    
    matrix3d = new Matrix3d ();
    matrix3d.setM00 (mat.getM00 ());
    matrix3d.setM01 (mat.getM01 ());
    matrix3d.setM02 (mat.getM02 ());
    matrix3d.setM10 (mat.getM10 ());
    matrix3d.setM11 (mat.getM11 ());
    matrix3d.setM12 (mat.getM12 ());
    matrix3d.setM20 (mat.getM20 ());
    matrix3d.setM21 (mat.getM21 ());
    matrix3d.setM22 (mat.getM22 ());
    
    orientation = matrixToEulerAngles.getEulerAngles (matrix3d);
  }
  
  private void checkIfStillAlive ()
  {
    int currentPower = characterInfo.getStatistics ().getCurrentPower ();
    
    characterInfo.getStatistics ().normalizeMinimumValues ();
    
    if (currentPower <= 0)
    {
      characterInfo.setAlive        (false);
      hideModel                     ();
      diskBoundsRenderer.setVisible (false);
    }
  }
  
  private void setActiveBoundsAppearance (int playerNumber)
  {
    Material boundsMaterial = diskBoundsRenderer.getAppearance ()
                                                .getMaterial   ();
    
    if (playerNumber == 1)
    {
      boundsMaterial.setDiffuseColor (ColorConstants.CYAN_3F);
    }
    else if (playerNumber == 2)
    {
      boundsMaterial.setDiffuseColor (ColorConstants.RED_3F);
    }
    else
    {
      boundsMaterial.setDiffuseColor (ColorConstants.LIGHT_GRAY_3F);
    }
  }
  
  private static BufferedImage createEmptyIcon ()
  {
    return new BufferedImage (16, 16, BufferedImage.TYPE_4BYTE_ABGR);
  }
  
}  // end of MarkerModel class
