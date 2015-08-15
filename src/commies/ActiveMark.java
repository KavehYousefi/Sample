// 23.06.2015

package commies;


import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import ky.appearance.DefaultMaterials;
import ky.appearance.DefaultMaterials.MaterialName;

import com.sun.j3d.utils.geometry.Cone;


public class ActiveMark
{
  private BranchGroup    root;
  private TransformGroup translationGroup;
  private TransformGroup rotationGroup;
  private TransformGroup scaleGroup;
  private Switch         visibilityGroup;
  private Cone           arrowShape3D;
  
  
  public ActiveMark ()
  {
    this.root             = new BranchGroup             ();
    this.translationGroup = createDynamicTransformGroup ();
    this.rotationGroup    = createDynamicTransformGroup ();
    this.scaleGroup       = createDynamicTransformGroup ();
    this.visibilityGroup  = new Switch                  ();
    this.arrowShape3D     = createArrowShape3D          ();
    
    visibilityGroup.setCapability (Switch.ALLOW_SWITCH_READ);
    visibilityGroup.setCapability (Switch.ALLOW_SWITCH_WRITE);
    
    rotationGroup.setTransform (createUpsideDownRotation ());
    
    root.addChild             (translationGroup);
    translationGroup.addChild (rotationGroup);
    rotationGroup.addChild    (scaleGroup);
    scaleGroup.addChild       (visibilityGroup);
    visibilityGroup.addChild  (arrowShape3D);
  }
  
  
  public BranchGroup getRootNode ()
  {
    return root;
  }
  
  public void setPosition (Point3d point3d)
  {
    Transform3D translation       = null;
    Vector3d    translationVector = null;
    
    translation       = new Transform3D ();
    translationVector = new Vector3d    (point3d);
    
    translationGroup.getTransform (translation);
    translation.setTranslation    (translationVector);
    translationGroup.setTransform (translation);
  }
  
  public void setScaling (Vector3d scalingFactors)
  {
    Transform3D scaling = null;
    
    scaling = new Transform3D ();
    scaleGroup.getTransform   (scaling);
    scaling.setScale          (scalingFactors);
    scaleGroup.setTransform   (scaling);
  }
  
  public void setAppearance (Appearance appearance)
  {
    arrowShape3D.setAppearance (appearance);
  }
  
  public void show ()
  {
    visibilityGroup.setWhichChild (Switch.CHILD_ALL);
  }
  
  public void hide ()
  {
    visibilityGroup.setWhichChild (Switch.CHILD_NONE);
  }
  
  
  
  private TransformGroup createDynamicTransformGroup ()
  {
    TransformGroup transformGroup = null;
    
    transformGroup = new TransformGroup ();
    transformGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    transformGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    
    return transformGroup;
  }
  
  private Transform3D createUpsideDownRotation ()
  {
    Transform3D rotation    = null;
    AxisAngle4d axisAngle4d = null;
    
    rotation    = new Transform3D ();
    axisAngle4d = new AxisAngle4d (1.0, 0.0, 0.0, -Math.PI / 2.0);
    rotation.setRotation          (axisAngle4d);
    
    return rotation;
  }
  
  private Cone createArrowShape3D ()
  {
    Cone arrow = null;
    
    arrow = new Cone
    (
      1.0f,
      1.0f,
      DefaultMaterials.getAppearance (MaterialName.HANSEN_BRASS)
//      new WireframeAppearance ()
    );
    
    return arrow;
  }
}
