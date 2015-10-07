package adtest01;

import ky.gamelogic.Statistics;


public class AttackHandler
{
  public AttackHandler ()
  {
  }
  
  
  public void processAttack (MarkerModel attacker, MarkerModel target)
  {
    if ((attacker == null) || (target == null))
    {
      return;
    }
    if (attacker.isEffect () || target.isEffect ())
    {
      return;
    }
    
    Statistics attackerStatistics = null;
    Statistics targetStatistics   = null;
    int        attackerAttack     = 0;
    int        targetPower        = 0;
    int        targetDefense      = 0;
    
    attackerStatistics = attacker.getCharacterInfo ().getStatistics ();
    targetStatistics   = target.getCharacterInfo   ().getStatistics ();
    attackerAttack     = attackerStatistics.getCurrentAttack ();
    targetPower        = targetStatistics.getCurrentPower    ();
    targetDefense      = targetStatistics.getCurrentDefense  ();
    
    if ((target.getCharacterInfo ().isDefending ()) &&
        (targetDefense > 0))
    {
      targetDefense = targetDefense - attackerAttack;
    }
    else
    {
      targetPower = targetPower - attackerAttack;
    }
    
    targetStatistics.setCurrentPower   (targetPower);
    targetStatistics.setCurrentDefense (targetDefense);
    
    attacker.update ();
    target.update   ();
  }
}
