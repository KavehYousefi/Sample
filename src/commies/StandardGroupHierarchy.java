// 14.07.2015

package commies;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.media.j3d.TransformGroup;


public class StandardGroupHierarchy
{
  private BranchGroup    root;
  private TransformGroup translationGroup;
  private TransformGroup rotationGroup;
  private TransformGroup scaleGroup;
  private Switch         visibilityGroup;
  
  
  public StandardGroupHierarchy ()
  {
    this.root             = new BranchGroup             ();
    this.translationGroup = createDynamicTransformGroup ();
    this.rotationGroup    = createDynamicTransformGroup ();
    this.scaleGroup       = createDynamicTransformGroup ();
    this.visibilityGroup  = new Switch                  ();
    
    visibilityGroup.setCapability (Switch.ALLOW_SWITCH_READ);
    visibilityGroup.setCapability (Switch.ALLOW_SWITCH_WRITE);
    
//    rotationGroup.setTransform (createUpsideDownRotation ());
    
    root.addChild             (translationGroup);
    translationGroup.addChild (rotationGroup);
    rotationGroup.addChild    (scaleGroup);
    scaleGroup.addChild       (visibilityGroup);
  }
  
  
  public BranchGroup getRootBranchGroup ()
  {
    return root;
  }
  
  public TransformGroup getTranslationTransformGroup ()
  {
    return translationGroup;
  }
  
  public TransformGroup getRotationTransformGroup ()
  {
    return rotationGroup;
  }
  
  public TransformGroup getScaleTransformGroup ()
  {
    return scaleGroup;
  }
  
  public Switch getVisibilitySwitchGroup ()
  {
    return visibilityGroup;
  }
  
  
  private TransformGroup createDynamicTransformGroup ()
  {
    TransformGroup transformGroup = null;
    
    transformGroup = new TransformGroup ();
    transformGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    transformGroup.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    
    return transformGroup;
  }
}
