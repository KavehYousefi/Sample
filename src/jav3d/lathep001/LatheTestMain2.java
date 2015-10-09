package jav3d.lathep001;

import ky.Position;
import ky.angle.Angle;
import ky.appearance.QuickShaderAppearance;
import jav3d.lathep001.LatheGeometry.BackAtStartBehavior;
import jav3d.lathep001.segment.LathePath2DSegment;
import jav3d.lathep001.segment.LatheStartSegment;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.ShaderAttributeObject;
import javax.media.j3d.ShaderAttributeValue;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;


public class LatheTestMain2
{
  public LatheTestMain2 ()
  {
    System.setProperty ("sun.awt.noerasebackground", "true");
    
    GraphicsConfiguration graphicsConf   = SimpleUniverse.getPreferredConfiguration ();
    @SuppressWarnings ("unused")
    Canvas3D              canvas3D       = new Canvas3D (graphicsConf);
    SimpleUniverse        universe       = new SimpleUniverse ();
    ViewingPlatform       viewPlatform   = universe.getViewingPlatform ();
    BranchGroup           branchGroup    = new BranchGroup ();
    BoundingSphere        grenzen        = new BoundingSphere
                                           (
                                             new Point3d (0.0, 0.0, 0.0),
                                             1000.0
                                           );
    AmbientLight         licht0          = legeLicht0An (grenzen);
    DirectionalLight     licht1          = legeLicht1An (grenzen);
    MouseRotate          mausDreher      = new MouseRotate ();
    MouseTranslate       mausVerschieber = new MouseTranslate ();
    MouseWheelZoom       mausRadZoomer   = new MouseWheelZoom ();
    TransformGroup       transGruppe     = new TransformGroup ();
    Transform3D          transformation  = new Transform3D ();
    
    transGruppe.setTransform  (transformation);
    transGruppe.setCapability (TransformGroup.ALLOW_TRANSFORM_READ);
    transGruppe.setCapability (TransformGroup.ALLOW_TRANSFORM_WRITE);
    
    mausDreher.setSchedulingBounds      (grenzen);
    mausDreher.setTransformGroup        (transGruppe);
    mausRadZoomer.setSchedulingBounds   (grenzen);
    mausRadZoomer.setTransformGroup     (transGruppe);
    mausVerschieber.setSchedulingBounds (grenzen);
    mausVerschieber.setTransformGroup   (transGruppe);
    mausVerschieber.setFactor           (0.005);
    
    
    // ADD THE SHAPE(S).
    SegmentedLathePath lathePath     = new SegmentedLathePath ();
    LatheShape3D       latheShape3D  = null;
    LatheGeometry      latheGeometry = null;
    Path2D             path2D        = new Path2D.Double ();
    
    // Handling clockwise point creation.
    lathePath.setPointsReversed (true);
    
    lathePath.addSegment (new LatheStartSegment  (new Position (0.0,  0.5)));
    
    path2D.moveTo (0.0, 0.0);
    path2D.append
    (
      new TwoPointsCircularArcCalculator
      (
        new Position (0.0, 0.0),
        new Position (0.2, -0.7),
        TwoPointsCircularArcCalculator.ArcDirection.COUNTER_CLOCKWISE
      ).getArc2D (Arc2D.OPEN),
      false
    );
    path2D.lineTo (0.2, -0.9);
    path2D.quadTo (0.2, -1.0, 0.0, -1.0);
    lathePath.addSegment (new LathePath2DSegment
    (
      path2D,
      null,
      0.001,
      LathePath2DSegment.InitialMoveHandling.SET_TO_LAST_SEGMENT_POINT
    ));
    
    
    latheShape3D  = null;
    latheGeometry = new LatheGeometry (lathePath);
    
    latheGeometry.setAngleStep (new Angle (1.0));
    latheGeometry.setLatheBase (new GearLatheBase (new GearCurve (1.0, 10.0, 6.0)));
    latheGeometry.setBackAtStartBehavior (BackAtStartBehavior.WRAP_TO_ORIGIN);
    
    latheShape3D = new LatheShape3D (latheGeometry);
    latheShape3D.setAppearance (new QuickShaderAppearance
    (
      "myresources/brick-shader_001.vert",
      "myresources/brick-shader_001.frag",
      new String[]
      {
        "LightPosition",
        "BrickColor",
        "MortarColor",
        "BrickSize",
        "BrickPercentage"
      },
      new ShaderAttributeObject[]
      {
        new ShaderAttributeValue ("LightPosition",   new Vector3f (0.00f, 0.00f, 4.00f)),
        new ShaderAttributeValue ("BrickColor",      new Vector3f (1.00f, 0.30f, 0.20f)),
        new ShaderAttributeValue ("MortarColor",     new Vector3f (0.85f, 0.86f, 0.84f)),
        new ShaderAttributeValue ("BrickSize",       new Vector2f (0.30f, 0.15f)),
        new ShaderAttributeValue ("BrickPercentage", new Vector2f (0.90f, 0.85f))
      },
      null
    ));
    transGruppe.addChild (latheShape3D.getShape3D ());
    
    branchGroup.addChild (licht0);
    branchGroup.addChild (licht1);
    branchGroup.addChild (transGruppe);
    branchGroup.addChild (mausDreher);
    branchGroup.addChild (mausRadZoomer);
    branchGroup.addChild (mausVerschieber);
    branchGroup.compile  ();
    
    viewPlatform.setNominalViewingTransform ();
    universe.addBranchGraph                 (branchGroup);
  }
  
  
  
  public static void main (String[] args)
  {
    new LatheTestMain2 ();
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private AmbientLight legeLicht0An (BoundingSphere grenzen)
  {
    AmbientLight licht = new AmbientLight ();
    
    licht.setColor             (new Color3f (Color.WHITE));
    licht.setInfluencingBounds (grenzen);
    
    return licht;
  }
  
  private DirectionalLight legeLicht1An (BoundingSphere grenzen)
  {
    DirectionalLight licht = new DirectionalLight ();
    
    licht.setColor             (new Color3f (Color.WHITE));
    licht.setDirection         (2.0f, -2.0f, -2.0f);
    licht.setInfluencingBounds (grenzen);
    
    return licht;
  }
}