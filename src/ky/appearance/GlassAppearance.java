// 21.12.2012

package ky.appearance;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;


public class GlassAppearance
extends      Appearance
{
  private static final float   DEFAULT_TRANSPARENCY   = 0.5f;
  private static final Color3f DEFAULT_EMISSIVE_COLOR = new Color3f (Color.LIGHT_GRAY);
  
  
  public GlassAppearance ()
  {
    initGlassAppearance (DEFAULT_TRANSPARENCY, DEFAULT_EMISSIVE_COLOR);
  }
  
  public GlassAppearance (float transparency)
  {
    initGlassAppearance (transparency, DEFAULT_EMISSIVE_COLOR);
  }
  
  public GlassAppearance (Color3f emissiveColor)
  {
    initGlassAppearance (DEFAULT_TRANSPARENCY, emissiveColor);
  }
  
  public GlassAppearance (float transparency, Color3f emissiveColor)
  {
    initGlassAppearance (transparency, emissiveColor);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Umsetzung der Hilfsmethoden.                               -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initGlassAppearance
  (
    float   transparency,
    Color3f emissiveColor
  )
  {
    Material               material      = new Material ();
    TransparencyAttributes transpAttribs = new TransparencyAttributes ();
    
    material.setAmbientColor   (new Color3f (Color.BLACK));
    material.setDiffuseColor   (emissiveColor            );
    material.setEmissiveColor  (emissiveColor            );
    material.setSpecularColor  (new Color3f (Color.WHITE));
    material.setShininess      (12.0f                    );
    material.setLightingEnable (true                     );
    
    transpAttribs.setTransparencyMode (TransparencyAttributes.BLENDED);
//    transpAttribs.setDstBlendFunction (TransparencyAttributes.BLEND_SRC_ALPHA);
//    transpAttribs.setSrcBlendFunction (TransparencyAttributes.BLEND_ONE_MINUS_DST_COLOR);
    transpAttribs.setTransparency     (transparency);
    
    setMaterial                       (material);
    setTransparencyAttributes         (transpAttribs);
  }
}