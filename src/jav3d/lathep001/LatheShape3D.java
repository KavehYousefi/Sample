package jav3d.lathep001;


import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.Shape3D;

import ky.appearance.DefaultMaterials;
import ky.appearance.DefaultMaterials.MaterialName;


public class LatheShape3D
{
  private LatheGeometry latheGeometry;
  private Appearance    appearance;
  
  
  public LatheShape3D (LathePath lathePfad)
  {
    latheGeometry = new LatheGeometry (lathePfad);
    appearance    = DefaultMaterials.getAppearance (MaterialName.COLE_GOLD);
  }
  
  public LatheShape3D (LatheGeometry latheGeometry)
  {
    this.latheGeometry = latheGeometry;
    appearance         = DefaultMaterials.getAppearance (MaterialName.COLE_GOLD);
  }
  
  
  public Appearance getAppearance ()
  {
    return appearance;
  }
  
  public void setAppearance (Appearance appearance)
  {
    this.appearance = appearance;
  }
  
  public Shape3D getShape3D ()
  {
    Shape3D  shape3D   = new Shape3D               ();
    Geometry geometrie = latheGeometry.getGeometry ();
    
    shape3D.setGeometry   (geometrie);
    shape3D.setAppearance (appearance);
    
    return shape3D;
  }
  
  /*
  public static Appearance createAppearance
  (
    Color3f darkColor,
    Color3f lightColor
  )
  {
    Appearance        appearance        = null;
    PolygonAttributes polygonAttributes = null;
    Material          material          = null;
    Color3f           black             = null;
    
    appearance = new Appearance ();
    
    polygonAttributes = new PolygonAttributes ();
    polygonAttributes.setCullFace (PolygonAttributes.CULL_NONE);
    
    black    = new Color3f  (1.0f, 0.5f, 0.0f);
    material = new Material
               (
                 darkColor,
                 black,
                 lightColor,
                 black,
                 1.0f
               );
    material.setLightingEnable (true);
    
    appearance.setPolygonAttributes (polygonAttributes);
    appearance.setMaterial          (material);
    
    return appearance;
  }
  */
}