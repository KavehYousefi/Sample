// 23.06.2015

package ky;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adtest01.MarkerModel;


public class Player
{
  private int               number;
  private List<MarkerModel> characters;
  
  
  public Player (int number)
  {
    this.number     = number;
    this.characters = new ArrayList<MarkerModel> ();
  }
  
  
  public int getNumber ()
  {
    return number;
  }
  
  public List<MarkerModel> getCharacters ()
  {
    return Collections.unmodifiableList (characters);
  }
  
  public void addCharacter (MarkerModel character)
  {
    if ((character != null) && (! characters.contains (character)))
    {
      characters.add (character);
    }
  }
}
