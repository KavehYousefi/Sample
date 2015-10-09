package ky.gamelogic;


/**
 * Holds data important to a character.
 * 
 * @author Kaveh Yousefi
 */
public class CharacterInfo
{
  private String     name;
  private Statistics statistics;
  private Element    element;
  private boolean    alive;
  private boolean    defending;
  private boolean    canAct;
  private int        playerNumber;
  
  
  public CharacterInfo (String name, Statistics statistics, Element element)
  {
    this.name         = name;
    this.statistics   = statistics;
    this.element      = element;
    this.alive        = true;
    this.defending    = false;
    this.canAct       = false;
    this.playerNumber = 0;
  }
  
  public CharacterInfo (String name, Element element)
  {
    this.name         = name;
    this.statistics   = new Statistics (1, 1, 1);
    this.element      = element;
    this.alive        = true;
    this.defending    = false;
    this.canAct       = false;
    this.playerNumber = 0;
  }
  
  
  public String getName ()
  {
    return name;
  }
  
  public void setName (String name)
  {
    this.name = name;
  }
  
  public Statistics getStatistics ()
  {
    return statistics;
  }
  
  public void setStatistics (Statistics statictics)
  {
    this.statistics = statictics;
  }
  
  public Element getElement ()
  {
    return element;
  }
  
  public void setElement (Element element)
  {
    this.element = element;
  }
  
  public boolean isAlive ()
  {
    return alive;
  }
  
  public void setAlive (boolean isAlive)
  {
    this.alive = isAlive;
  }
  
  public boolean isDefending ()
  {
    return defending;
  }
  
  public void setDefending (boolean isDefending)
  {
    this.defending = isDefending;
  }
  
  public boolean canAct ()
  {
    return canAct;
  }
  
  public void setCanAct (boolean canAct)
  {
    this.canAct = canAct;
  }
  
  public int getPlayerNumber ()
  {
    return playerNumber;
  }
  
  public void setPlayerNumber (int playerNumber)
  {
    this.playerNumber = playerNumber;
  }
}
