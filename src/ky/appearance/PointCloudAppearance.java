// 26.12.2012

package ky.appearance;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PointAttributes;
import javax.media.j3d.PolygonAttributes;

import javax.vecmath.Color3f;


/* ALTER NAME: "PunktnetzAppearance".
 * 
 */
public class PointCloudAppearance extends Appearance
{
  private static final float   VORGABE_PUNKT_GROESSE = 1.0f;
  private static final Color3f VORGABE_FARBE         = new Color3f (1.0f, 1.0f, 1.0f);
  
  
  public PointCloudAppearance ()
  {
    super ();
    initPunktnetzAppearance (VORGABE_PUNKT_GROESSE, VORGABE_FARBE);
  }
  
  public PointCloudAppearance (float punktGroesse)
  {
    super ();
    initPunktnetzAppearance (punktGroesse, VORGABE_FARBE);
  }
  
  public PointCloudAppearance (Color3f farbe)
  {
    super ();
    initPunktnetzAppearance (VORGABE_PUNKT_GROESSE, farbe);
  }
  
  public PointCloudAppearance (float punktGroesse, Color3f farbe)
  {
    super ();
    initPunktnetzAppearance (punktGroesse, farbe);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Umsetzung der Hilfsmethoden.                               -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initPunktnetzAppearance
  (
    float   punktGroesse,
    Color3f farbe
  )
  {
    PolygonAttributes polygonAttribs = new PolygonAttributes ();
    PointAttributes   pointAttribs   = new PointAttributes   ();
    Material          material       = new Material          ();
    
    polygonAttribs.setCullFace    (PolygonAttributes.CULL_NONE);
    polygonAttribs.setPolygonMode (PolygonAttributes.POLYGON_POINT);
    
    pointAttribs.setPointSize               (punktGroesse);
    pointAttribs.setPointAntialiasingEnable (true);
    
    material.setEmissiveColor               (farbe);
    
    setPolygonAttributes (polygonAttribs);
    setPointAttributes   (pointAttribs);
    setMaterial          (material);
  }
}