package adtest01;

import javax.media.j3d.TransformGroup;


public class TransformHierarchy
{
  private TransformGroup rootTG;
  private TransformGroup moveTG;
  private TransformGroup rotTG;
  private TransformGroup scaleTG;    
  
  
  public TransformHierarchy ()
  {
    // create a transform group for scaling the object
    scaleTG = new TransformGroup ();
    scaleTG.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    scaleTG.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
//    scaleTG.addChild      (objBoundsTG);
    
    // create a transform group for rotating the object
    rotTG = new TransformGroup ();
    rotTG.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    rotTG.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    rotTG.addChild      (scaleTG);
    
    // create a transform group for moving the object
    moveTG = new TransformGroup ();
    moveTG.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    moveTG.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    moveTG.addChild      (rotTG);
    
    rootTG = new TransformGroup ();
    rootTG.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    rootTG.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    rootTG.addChild      (moveTG);
  }
  
  
  public TransformGroup getRootTransformGroup ()
  {
    return rootTG;
  }
  
  public TransformGroup getMoveTransformGroup ()
  {
    return moveTG;
  }
  
  public TransformGroup getRotationTransformGroup ()
  {
    return rotTG;
  }
  
  public TransformGroup getScaleTransformGroup ()
  {
    return scaleTG;
  }
}
