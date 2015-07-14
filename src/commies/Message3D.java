package commies;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Font3D;
import javax.media.j3d.FontExtrusion;
import javax.media.j3d.Interpolator;
import javax.media.j3d.Node;
import javax.media.j3d.PositionInterpolator;
//import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Switch;
import javax.media.j3d.Text3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.Timer;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import commies.OpenGLMaterials.MaterialName;


public class Message3D
{
  private static final Vector3d DEFAULT_SCALING_FACTORS = new Vector3d (-0.005, 0.005, 0.005);
  
  private BranchGroup     root;
  private Switch          visibilitySwitch;    // Show/hide node.
  private TransformGroup  transformGroup;      // Static scale/pos.
  private TransformGroup  rotationGroup;       // Animation group.
  private Alpha           rotationAlpha;
  private Transform3D     rotationTransform3D; // Animation transf.
  private Interpolator    rotationInterpolator;
  private Font3D          font3D;
  private Text3D          text3D;
  private Shape3D         textShape3D;
  private Timer           timer;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a three-dimensional message positioned at the origin.
   * </div>
   */
  public Message3D ()
  {
    root                 = new BranchGroup          ();
    visibilitySwitch     = new Switch               ();
    transformGroup       = new TransformGroup       ();
    rotationGroup        = new TransformGroup       ();
    rotationAlpha        = new Alpha                ();
    rotationTransform3D  = new Transform3D          ();
//    rotationInterpolator = new RotationInterpolator
//    (
//      rotationAlpha,
//      rotationGroup,
//      rotationTransform3D,
//      0.0f,
//      new Double (2.0 * Math.PI).floatValue ()
//    );
    rotationTransform3D.set (new AxisAngle4d (0.0, 1.0, 0.0, Math.PI * 0.5));
    rotationInterpolator = new PositionInterpolator
    (
      rotationAlpha,
      rotationGroup,
      rotationTransform3D,
      // At least in [-10.0, -8.0]
      -10.0f, 0.0f
    );
    font3D               = createFont3D      (new Font ("Times New Roman", Font.PLAIN, 1));
    text3D               = createText3D      (font3D);
    textShape3D          = createTextShape3D (text3D);
    timer                = null;
    
    visibilitySwitch.setWhichChild (Switch.CHILD_ALL);
    visibilitySwitch.setCapability (Switch.ALLOW_SWITCH_READ);
    visibilitySwitch.setCapability (Switch.ALLOW_SWITCH_WRITE);
    
    transformGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    transformGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
//    transformGroup.setTransform  (createInitialTransform ());
    
    TransformGroup scaleTransformGroup = new TransformGroup ();
    scaleTransformGroup.setTransform (createInitialTransform ());
    
    rotationGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    rotationGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    rotationAlpha.setIncreasingAlphaDuration (5000L);
    rotationAlpha.setLoopCount               (-1);
    rotationInterpolator.setSchedulingBounds (new BoundingSphere ());
    
    textShape3D.setCapability     (Shape3D.ALLOW_APPEARANCE_READ);
    textShape3D.setCapability     (Shape3D.ALLOW_APPEARANCE_WRITE);
    textShape3D.setCapability     (Shape3D.ALLOW_APPEARANCE_OVERRIDE_READ);
    textShape3D.setCapability     (Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
//    textShape3D.setAppearance     (new WireframeAppearance ());
//    textShape3D.setAppearance     (new QuickShaderAppearance
//    (
//      "myresources/misc-test_024.vert",
//      "myresources/misc-test_024.frag"
//    ));
//    textShape3D.getAppearance ().setMaterial (new Material
//    (
//      ColorConstants.RED_3F,
//      ColorConstants.BLACK_3F,
//      ColorConstants.RED_3F,
//      ColorConstants.WHITE_3F,
//      100.0f
//    ));
    textShape3D.setAppearance (OpenGLMaterials.getAppearance (MaterialName.GOLD_2));
    
    text3D.setCapability          (Text3D.ALLOW_POSITION_READ);
    text3D.setCapability          (Text3D.ALLOW_POSITION_WRITE);
    text3D.setCapability          (Text3D.ALLOW_STRING_READ);
    text3D.setCapability          (Text3D.ALLOW_STRING_WRITE);
    text3D.setCapability          (Text3D.ALLOW_FONT3D_READ);
    text3D.setCapability          (Text3D.ALLOW_FONT3D_WRITE);
    text3D.setCapability          (Text3D.ALLOW_ALIGNMENT_READ);
    text3D.setCapability          (Text3D.ALLOW_ALIGNMENT_WRITE);
    
    root.addChild             (visibilitySwitch);
    visibilitySwitch.addChild (transformGroup);
    scaleTransformGroup.addChild   (rotationGroup);
    transformGroup.addChild   (scaleTransformGroup);
    rotationGroup.addChild    (textShape3D);
    rotationGroup.addChild    (rotationInterpolator);
  }
  
  
  private Font3D createFont3D (Font font)
  {
    Font3D        font3D        = null;
    FontExtrusion fontExtrusion = null;
    
    fontExtrusion = new FontExtrusion ();
    font3D        = new Font3D        (font, fontExtrusion);
    
    return font3D;
  }
  
  private Text3D createText3D (Font3D font3D)
  {
    Text3D text3D = new Text3D ();
    
    text3D.setAlignment  (Text3D.ALIGN_CENTER);
    text3D.setCapability (Text3D.ALLOW_POSITION_READ);
    text3D.setCapability (Text3D.ALLOW_POSITION_WRITE);
    text3D.setCapability (Text3D.ALLOW_STRING_READ);
    text3D.setCapability (Text3D.ALLOW_STRING_WRITE);
    text3D.setCapability (Text3D.ALLOW_FONT3D_READ);
    text3D.setCapability (Text3D.ALLOW_FONT3D_WRITE);
    text3D.setCapability (Text3D.ALLOW_ALIGNMENT_READ);
    text3D.setCapability (Text3D.ALLOW_ALIGNMENT_WRITE);
    text3D.setFont3D     (font3D);
    
    return text3D;
  }
  
  private Shape3D createTextShape3D (Text3D text3D)
  {
    Shape3D textShape3D = new Shape3D ();
    
    textShape3D.setGeometry   (text3D);
    textShape3D.setAppearance (null);
    textShape3D.setCapability (Shape3D.ALLOW_APPEARANCE_READ);
    textShape3D.setCapability (Shape3D.ALLOW_APPEARANCE_WRITE);
    textShape3D.setCapability (Shape3D.ALLOW_APPEARANCE_OVERRIDE_READ);
    textShape3D.setCapability (Shape3D.ALLOW_APPEARANCE_OVERRIDE_WRITE);
    
    return textShape3D;
  }
  
  private Transform3D createInitialTransform ()
  {
    Transform3D transformation = new Transform3D ();
    
    transformation.setScale (DEFAULT_SCALING_FACTORS);
    
    return transformation;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a scene graph node.
   * </div>
   * 
   * @return  A node to be used in a scene graph.
   */
  public Node getNode3D ()
  {
    return root;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the position.
   * </div>
   * 
   * @param position  The new position. It may not be <code>null</code>.
   * @throws NullPointerException  If <code>position</code> equals
   *                               <code>null</code>.
   */
  public void setPosition (Point3d position)
  {
    Transform3D newTransform      = new Transform3D ();
    Vector3d    translationVector = new Vector3d    (position);
    
    // Obtain the current transformation (position, scaling, etc.) ...
    transformGroup.getTransform (newTransform);
    // ... and translate it to the new position, ...
    newTransform.setTranslation (translationVector);
    // ... then update the TransformGroup.
    transformGroup.setTransform (newTransform);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the message text.
   * </div>
   * 
   * @param text  The new message text.
   */
  public void setText (String text)
  {
    text3D.setString (text);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the appearance.
   * </div>
   * 
   * @param appearance  The new appearance.
   */
  public void setAppearance (Appearance appearance)
  {
    textShape3D.setAppearance (appearance);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the message's font.
   * </div>
   * 
   * @param font  The message's font. It may not be <code>null</code>.
   * @throws NullPointerException  If <code>font</code> equals
   *                               <code>null</code>.
   */
  public void setFont (Font font)
  {
    Font3D        newFont3D     = null;
    FontExtrusion fontExtrusion = new FontExtrusion ();
    
    font3D.getFontExtrusion (fontExtrusion);
    newFont3D = new Font3D  (font, fontExtrusion);
    font3D    = newFont3D;
    
    text3D.setFont3D (newFont3D);
  }
  
  /**
   * <div class="introMethod">
   *   Displays the message permanently.
   * </div>
   */
  public void show ()
  {
    visibilitySwitch.setWhichChild (Switch.CHILD_ALL);
  }
  
  /**
   * <div class="introMethod">
   *   Displays the message for the given duration, then hides it
   *   automatically.
   * </div>
   * 
   * @param durationInMs  The duration to show the message in
   *                      milliseconds. It must be positive.
   * @throws IllegalArgumentException  If the <code>durationInMs</code>
   *                                   is negative.
   */
  public void showFor (int durationInMs)
  {
    if (durationInMs < 0)
    {
      String exceptionText = String.format
      (
        "The duration must be positive: %s < 0",
        durationInMs
      );
      throw new IllegalArgumentException (exceptionText);
    }
    
    show ();
    
    if (timer != null)
    {
      timer.stop ();
    }
    
    timer = new Timer (durationInMs, new ActionListener ()
    {
      @Override
      public void actionPerformed (ActionEvent event)
      {
        hide       ();
        timer.stop ();
      }
    });
    
    timer.setInitialDelay (durationInMs);
    timer.setRepeats      (false);
    timer.start           ();
  }
  
  /**
   * <div class="introMethod">
   *   Hides the message permanently.
   * </div>
   */
  public void hide ()
  {
    visibilitySwitch.setWhichChild (Switch.CHILD_NONE);
  }
}