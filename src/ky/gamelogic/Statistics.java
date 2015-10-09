package ky.gamelogic;


/**
 * Stores a character's power, attack and defense points.
 * 
 * @author Kaveh Yousefi
 */
public class Statistics
{
  private int maximumPower;
  private int currentPower;
  private int maximumAttack;
  private int currentAttack;
  private int maximumDefense;
  private int currentDefense;
  
  
  public Statistics (int maximumPower, int maximumAttack, int maximumDefense)
  {
    this.maximumPower   = maximumPower;
    this.currentPower   = maximumPower;
    this.maximumAttack  = maximumAttack;
    this.currentAttack  = maximumAttack;
    this.maximumDefense = maximumDefense;
    this.currentDefense = maximumDefense;
  }
  
  public Statistics ()
  {
    this (0, 0, 0);
  }
  
  
  public int getMaximumPower ()
  {
    return maximumPower;
  }
  
  public void setMaximumPower (int maximumPower)
  {
    this.maximumPower = maximumPower;
  }
  
  public int getCurrentPower ()
  {
    return currentPower;
  }
  
  public void setCurrentPower (int currentPower)
  {
    this.currentPower = currentPower;
  }
  
  
  public int getMaximumAttack ()
  {
    return maximumAttack;
  }
  
  public void setMaximumAttack (int maximumAttack)
  {
    this.maximumAttack = maximumAttack;
  }
  
  public int getCurrentAttack ()
  {
    return currentAttack;
  }
  
  public void setCurrentAttack (int currentAttack)
  {
    this.currentAttack = currentAttack;
  }
  
  
  public int getMaximumDefense ()
  {
    return maximumDefense;
  }
  
  public void setMaximumDefense (int maximumDefense)
  {
    this.maximumDefense = maximumDefense;
  }
  
  public int getCurrentDefense ()
  {
    return currentDefense;
  }
  
  public void setCurrentDefense (int currentDefense)
  {
    this.currentDefense = currentDefense;
  }
  
  
  public void normalizeMinimumValues ()
  {
    this.currentPower   = getMinimumIfNecessary (this.currentPower);
    this.currentAttack  = getMinimumIfNecessary (this.currentAttack);
    this.currentDefense = getMinimumIfNecessary (this.currentDefense);
  }
  
  
  public void reset ()
  {
    this.currentPower   = maximumPower;
    this.currentAttack  = maximumAttack;
    this.currentDefense = maximumDefense;
  }
  
  
  
  @Override
  public String toString ()
  {
    String asString = null;
    
    asString = String.format
    (
      "Statistics(power=%s/%s, attack=%s/%s, defense=%s/%s)",
      currentPower, maximumPower,
      currentAttack, maximumAttack,
      currentDefense, maximumDefense
    );
    
    return asString;
  }
  
  
  
  private int getMinimumIfNecessary (int value)
  {
    return Math.max (value, 0);
  }
}
