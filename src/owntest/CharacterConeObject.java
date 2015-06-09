package owntest;

import javax.media.j3d.BranchGroup;

import com.sun.j3d.utils.geometry.Cone;

public class CharacterConeObject
implements   CharacterObject
{
  public CharacterConeObject ()
  {
    
  }
  
  
  @Override
  public BranchGroup getBranchGroup ()
  {
    BranchGroup group = null;
    Cone        cone  = null;
    
    group = new BranchGroup ();
    cone  = new Cone (0.2f, 0.7f, Cone.GENERATE_NORMALS, null);
    
    group.addChild (cone);
    
    return group;
  }
}