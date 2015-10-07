package adtest01;

import java.util.HashMap;
import java.util.Map;

import ky.gamelogic.Effect;


public class EffectFactory
{
  private Map<String, Effect> effects;
  
  
  public EffectFactory ()
  {
    this.effects = new HashMap<String, Effect> ();
  }
  
  
  public void addEffect (String effectName, Effect effect)
  {
    effects.put (effectName, effect);
  }
  
  
  public Effect getEffectByName (String effectName)
  {
    return effects.get (effectName);
  }
}
