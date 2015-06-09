// 02.06.2015

package commies;

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
    for (Shape3D shape3D : new ModelInspection ().getShape3Ds (modelGroup))
    {
      shape3D.setAppearance (appearance);
    }
  }
}
