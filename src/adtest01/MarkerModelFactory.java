package adtest01;

import java.nio.file.Paths;
import java.util.Map;

import ky.gamelogic.CharacterSwapper;
import ky.gamelogic.RainEffect;
import ky.gamelogic.StatisticsMixer;


public class MarkerModelFactory
{
  private EffectFactory              effectFactory;
  private MarkerModelsXMLLoader      markerModelsXMLLoaders;
  private Map<String, MarkerModel>   markerModelsMap;
  
  
  public MarkerModelFactory
  (
    MultiNyAR     multiNyAR,
    DetectMarkers detectMarkers
  )
  {
    this.effectFactory          = createEffectFactory
    (
      multiNyAR,
      detectMarkers
    );
    this.markerModelsXMLLoaders = new MarkerModelsXMLLoader
    (
      effectFactory,
      new TransformHierarchyFactory ()
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
    
    return effectFactory;
  }
}
