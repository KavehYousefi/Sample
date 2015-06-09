// 02.06.2015

package commies;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Bounds;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Sphere;


public class BoundsRenderer
{
  private float TRANSPARENCY_VALUE = 0.75f;
  
  private Bounds            bounds;
  private Appearance        appearance;
  private CollisionDetector collisionDetector;
  private Sphere            sphere;
  
  
  public BoundsRenderer (Bounds bounds)
  {
    this.bounds            = new BoundingSphere    (bounds);
    this.appearance        = createAppearance      ();
    this.sphere            = getSphereFromBounds   (this.bounds);
    this.collisionDetector = new CollisionDetector (sphere);
    
    this.sphere.setUserData ("AX");
//    this.collisionDetector.addCollisionListener (null);
  }
  
  
  public Bounds getBounds ()
  {
    return bounds;
  }
  
  public void setBounds (Bounds bounds)
  {
    this.bounds = new BoundingSphere (bounds);
  }
  
  public BranchGroup getRootNode ()
  {
    BranchGroup rootNode = null;
    
    rootNode = new BranchGroup ();
    
    rootNode.addChild (sphere);
    
    return rootNode;
  }
  
  public void setColor (Color3f color)
  {
    appearance.getMaterial ().setAmbientColor (color);
    appearance.getMaterial ().setDiffuseColor (color);
  }
  
  
  public void addCollisionListener (CollisionListener listener)
  {
    collisionDetector.addCollisionListener (listener);
  }
  
  public void removeCollisionListener (CollisionListener listener)
  {
    collisionDetector.removeCollisionListener (listener);
  }
  
  
  private Appearance createAppearance ()
  {
    Appearance             appearance          = null;
    TransparencyAttributes transparencyAttribs = null;
    Material               material            = null;
    
    appearance          = new Appearance             ();
    transparencyAttribs = new TransparencyAttributes ();
    material            = new Material               ();
    
    transparencyAttribs.setTransparencyMode (TransparencyAttributes.BLENDED);
    transparencyAttribs.setTransparency     (TRANSPARENCY_VALUE);
    
    material.setAmbientColor  (0.20f, 0.20f, 0.70f);
    material.setEmissiveColor (0.00f, 0.00f, 0.00f);
    material.setDiffuseColor  (0.20f, 0.20f, 0.70f);
    material.setSpecularColor (0.85f, 0.85f, 0.85f);
    material.setShininess     (22.0f);
    material.setCapability    (Material.ALLOW_COMPONENT_READ |
                               Material.ALLOW_COMPONENT_WRITE);
    
    appearance.setMaterial               (material);
    appearance.setTransparencyAttributes (transparencyAttribs);
    
    return appearance;
  }
  
  private Sphere getSphereFromBounds (Bounds bounds)
  {
    Sphere         sphere         = null;
    BoundingSphere boundingSphere = null;
    
    boundingSphere = new BoundingSphere (bounds);
    sphere         = new Sphere
    (
      new Double (boundingSphere.getRadius ()).floatValue (),
      Sphere.GENERATE_NORMALS, 25,
      appearance
    );
    
    return sphere;
  }
}
