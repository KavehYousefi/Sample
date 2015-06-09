package commies;

public class CharacterUserData
{
  private int playerID;
  private int characterID;
  
  
  public CharacterUserData (int playerID, int characterID)
  {
    this.playerID    = playerID;
    this.characterID = characterID;
  }
  
  
  public int getPlayerID ()
  {
    return playerID;
  }
  
  public int getCharacterID ()
  {
    return characterID;
  }
}
