// 27.01.2013

package ky.appearance;

import java.awt.Color;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Color3f;


public class ColorAppearance extends Appearance
{
  public static final Color3f VORGABE_EMISSIVE_COLOR = new Color3f (Color.WHITE);
  public static final float   VORGABE_SHININESS      = 12.0f;
  public static final boolean DEFAULT_IS_TWO_SIDED   = false;
  
  
  public ColorAppearance ()
  {
    initFarbAppearance (VORGABE_EMISSIVE_COLOR, VORGABE_SHININESS,
                        DEFAULT_IS_TWO_SIDED);
  }
  
  public ColorAppearance (boolean isTwoSided)
  {
    initFarbAppearance (VORGABE_EMISSIVE_COLOR, VORGABE_SHININESS,
                        isTwoSided);
  }
  
  public ColorAppearance (Color3f farbe)
  {
    initFarbAppearance (farbe, VORGABE_SHININESS, DEFAULT_IS_TWO_SIDED);
  }
  
  public ColorAppearance (Color3f farbe, boolean isTwoSided)
  {
    initFarbAppearance (farbe, VORGABE_SHININESS, isTwoSided);
  }
  
  public ColorAppearance (Color3f farbe, float shininess, boolean isTwoSided)
  {
    initFarbAppearance (farbe, shininess, isTwoSided);
  }
  
  public ColorAppearance (Color3f farbe, float shininess)
  {
    initFarbAppearance (farbe, shininess, DEFAULT_IS_TWO_SIDED);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Umsetzung der Hilfsmethoden.                               -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initFarbAppearance
  (
    Color3f emissiveColor,
    float   shininess,
    boolean isTwoSided
  )
  {
    Material material = new Material ();
    
    material.setAmbientColor   (new Color3f (Color.BLACK));
//    material.setDiffuseColor   (new Color3f (Color.WHITE));
    material.setDiffuseColor   (emissiveColor            );
    material.setEmissiveColor  (emissiveColor            );
    material.setSpecularColor  (new Color3f (Color.WHITE));
    material.setShininess      (shininess                );
    material.setLightingEnable (true                     );
    
    setMaterial                (material);
    
    if (isTwoSided)
    {
      PolygonAttributes polygonAttribs = new PolygonAttributes ();
      
      polygonAttribs.setCullFace (PolygonAttributes.CULL_NONE);
      setPolygonAttributes       (polygonAttribs);
    }
  }
}