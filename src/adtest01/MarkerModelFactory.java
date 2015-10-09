package adtest01;

import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import java.nio.file.Paths;
import java.util.Map;

import javax.media.j3d.ShaderAttributeObject;
import javax.media.j3d.ShaderAttributeValue;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import jav3d.lathep001.GearCurve;
import jav3d.lathep001.GearLatheBase;
import jav3d.lathep001.LatheGeometry;
import jav3d.lathep001.LatheShape3D;
import jav3d.lathep001.SegmentedLathePath;
import jav3d.lathep001.TwoPointsCircularArcCalculator;
import jav3d.lathep001.LatheGeometry.BackAtStartBehavior;
import jav3d.lathep001.segment.LathePath2DSegment;
import jav3d.lathep001.segment.LatheStartSegment;
import ky.Cuboid;
import ky.Position;
import ky.angle.Angle;
import ky.appearance.QuickShaderAppearance;
import ky.dimensionsmatching.DimensionsMatching;
import ky.dimensionsmatching.ScaleUniformlyToFitInside;
import ky.gamelogic.CharacterSwapper;
import ky.gamelogic.DefenseForAttack;
import ky.gamelogic.RainEffect;
import ky.gamelogic.Resurrection;
import ky.gamelogic.StatisticsMixer;
import ky.gamelogic.Undefend;
import ky.math.BoundsNormalizer;


public class MarkerModelFactory
{
  private TransformHierarchyFactory  transformHierarchyFactory;
  private EffectFactory              effectFactory;
  private MarkerModelsXMLLoader      markerModelsXMLLoaders;
  private Map<String, MarkerModel>   markerModelsMap;
  
  
  public MarkerModelFactory
  (
    MultiNyAR     multiNyAR,
    DetectMarkers detectMarkers
  )
  {
    this.transformHierarchyFactory = createTransformHierarchyFactory ();
    this.effectFactory             = createEffectFactory
    (
      multiNyAR,
      detectMarkers
    );
    this.markerModelsXMLLoaders = new MarkerModelsXMLLoader
    (
      effectFactory,
      transformHierarchyFactory
    );
    try
    {
      this.markerModelsMap = markerModelsXMLLoaders.loadFromPath
      (
        Paths.get ("myresources", "markerModels.xml")
      );
    }
    catch (Exception e)
    {
      e.printStackTrace ();
      return;
    }
  }
  
  
  public MarkerModel getMarkerModelByName
  (
    String itemName,
    int    playerNumber
  )
  {
    MarkerModel markerModel = null;
    
    markerModel = loadMarkerModelByName (itemName);
    
    return markerModel;
  }
  
  
  private MarkerModel loadMarkerModelByName (String name)
  {
    return markerModelsMap.get (name);
  }
  
  
  private EffectFactory createEffectFactory
  (
    MultiNyAR     multiNyAR,
    DetectMarkers detectMarkers
  )
  {
    EffectFactory effectFactory = null;
    
    effectFactory = new EffectFactory ();
    effectFactory.addEffect ("characterSwapper", new CharacterSwapper (multiNyAR, detectMarkers));
    effectFactory.addEffect ("rain",             new RainEffect       (multiNyAR, detectMarkers, new TransformHierarchy ()));
    effectFactory.addEffect ("statisticsMixer",  new StatisticsMixer  (multiNyAR, detectMarkers));
    effectFactory.addEffect ("defenseForAttack", new DefenseForAttack (multiNyAR, detectMarkers));
    effectFactory.addEffect ("resurrection",     new Resurrection     (multiNyAR, detectMarkers));
    effectFactory.addEffect ("undefend",         new Undefend         (multiNyAR, detectMarkers));
    
    return effectFactory;
  }
  
  private TransformHierarchyFactory createTransformHierarchyFactory ()
  {
    TransformHierarchyFactory hierarchies = null;
    
    hierarchies = new TransformHierarchyFactory ();
    hierarchies.addTransformHierarchy
    (
      "starship", createStarship ()
    );
    
    return hierarchies;
  }
  
  private TransformHierarchy createStarship ()
  {
    TransformHierarchy transformHierarchy = new TransformHierarchy ();
    SegmentedLathePath lathePath          = new SegmentedLathePath ();
    LatheShape3D       latheShape3D       = null;
    LatheGeometry      latheGeometry      = null;
    Path2D             path2D             = new Path2D.Double ();
    TransformGroup     latheGroup         = null;
    
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
    
    latheGroup = new BoundsNormalizer
    (
      new DimensionsMatching (new ScaleUniformlyToFitInside ()),
      Cuboid.createCubeAtOrigin (0.15)
    ).getNormalizingTransformGroup
    (
      Cuboid.createFromBounds (latheShape3D.getShape3D ().getBounds ())
    );
    latheGroup.addChild (latheShape3D.getShape3D ());
    transformHierarchy.getScaleTransformGroup ().addChild (latheGroup);
    
    return transformHierarchy;
  }
}
