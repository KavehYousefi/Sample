// 02.06.2015

package commies;

import j3dextraction.SceneGraphDataExtractor;

import javax.media.j3d.Appearance;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;


public class ModelAppearanceAssigner
{
  private Group modelGroup;
  
  
  public ModelAppearanceAssigner (Group modelGroup)
  {
    this.modelGroup = modelGroup;
  }
  
  
  public void assignAppearance (Appearance appearance)
  {
    SceneGraphDataExtractor sceneGraphDataExtractor = null;
    
    sceneGraphDataExtractor = new SceneGraphDataExtractor ();
    sceneGraphDataExtractor.examine (modelGroup);
    
    for (Shape3D shape3D : sceneGraphDataExtractor.getShape3Ds ())
    {
      shape3D.setAppearance (appearance);
    }
  }
}
