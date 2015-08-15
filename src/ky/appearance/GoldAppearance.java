// 30.12.2012

package ky.appearance;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;


public class GoldAppearance extends Appearance
{
  private static final Color3f GOLDEN      = new Color3f (new Color (214, 158, 0));
  private static final Color3f HELL_GOLDEN = new Color3f (new Color (255, 211, 87));
  private static final Color3f WEISS       = new Color3f (1.0f, 1.0f, 1.0f);
  
  
  public GoldAppearance ()
  {
    super ();
    
    Material material = new Material ();
    
    material.setAmbientColor  (HELL_GOLDEN);
    material.setDiffuseColor  (HELL_GOLDEN);
    material.setEmissiveColor (GOLDEN);
    material.setSpecularColor (WEISS);
    material.setShininess     (128.0f);
    setMaterial               (material);
  }
}