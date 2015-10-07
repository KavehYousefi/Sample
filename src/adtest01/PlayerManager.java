package adtest01;


public class PlayerManager
{
  private int activePlayer;
  
  
  public PlayerManager ()
  {
    this.activePlayer = 1;
  }
  
  
  public int getActivePlayer ()
  {
    return activePlayer;
  }
  
  public int getInactivePlayer ()
  {
    switch (activePlayer)
    {
      case 1 :
      {
        return 2;
      }
      case 2 :
      {
        return 1;
      }
      default :
      {
        return 0;
      }
    }
  }
  
  public void activateNextPlayer ()
  {
    switch (activePlayer)
    {
      case 1 :
      {
        activePlayer = 2;
        break;
      }
      case 2 :
      {
        activePlayer = 1;
        break;
      }
      default :
      {
        activePlayer = 1;
        break;
      }
    }
  }
  
  public void setActivePlayer (int activePlayer)
  {
    this.activePlayer = activePlayer;
  }
  
  public boolean isActivePlayer (int player)
  {
    return (activePlayer == player);
  }
}
