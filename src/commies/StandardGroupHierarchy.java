// 14.07.2015
// Common order of transformation: -> "http://gamedev.stackexchange.com/questions/29260/transform-matrix-multiplication-order"

package commies;

import javax.media.j3d.BranchGroup;
import javax.media.j3d.Switch;
import javax.media.j3d.TransformGroup;


/* Hierarchy structure
 *   [+] root
 *       [+] translationGroup
 *           [+] rotationGroup
 *               [+] scaleGroup
 *                   [+] visibilityGroup
 * 
 * Description:
 *   root              :: BranchGroup to allow attachment/detachment of the whole hierarchy.
 *   translationGroup  :: TransformGroup for translation.
 *   rotationGroup     :: TransformGroup for rotation
 *   scaleGroup        :: TransformGroup for scaling.
 *   visibilityGroup   :: Switch for showing/hiding child elements. Add children here.
 */
public class StandardGroupHierarchy
{
  private BranchGroup    root;
  private TransformGroup translationGroup;
  private TransformGroup rotationGroup;
  private TransformGroup scaleGroup;
  private Switch         visibilityGroup;
  
  
  public StandardGroupHierarchy ()
  {
    this.root             = createDynamicBranchGroup    ();
    this.translationGroup = createDynamicTransformGroup ();
    this.rotationGroup    = createDynamicTransformGroup ();
    this.scaleGroup       = createDynamicTransformGroup ();
    this.visibilityGroup  = new Switch                  ();
    
    visibilityGroup.setCapability (Switch.ALLOW_SWITCH_READ);
    visibilityGroup.setCapability (Switch.ALLOW_SWITCH_WRITE);
    
    root.addChild                 (translationGroup);
    translationGroup.addChild     (rotationGroup);
    rotationGroup.addChild        (scaleGroup);
    scaleGroup.addChild           (visibilityGroup);
    visibilityGroup.setWhichChild (Switch.CHILD_ALL);
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
  
  
  private BranchGroup createDynamicBranchGroup ()
  {
    BranchGroup branchGroup = null;
    
    branchGroup = new BranchGroup ();
    branchGroup.setCapability (BranchGroup.ALLOW_DETACH);
    branchGroup.setCapability (BranchGroup.ALLOW_CHILDREN_EXTEND);
    branchGroup.setCapability (BranchGroup.ALLOW_CHILDREN_READ);
    branchGroup.setCapability (BranchGroup.ALLOW_CHILDREN_WRITE);
    
    return branchGroup;
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
