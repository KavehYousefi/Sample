package ky.gamelogic;

import java.util.Collections;
import java.util.List;

import adtest01.DetectMarkers;
import adtest01.MarkerModel;
import adtest01.MultiNyAR;
import adtest01.Player;


/**
 * Swaps on of each player's characters with the opponent.
 * 
 * @author Kaveh Yousefi
 */
public class CharacterSwapper
implements   Effect
{
  private MultiNyAR     multiNyAR;
  private DetectMarkers detectMarkers;
  private boolean       isDone;
  
  
  public CharacterSwapper (MultiNyAR multiNyAR, DetectMarkers detectMarkers)
  {
    this.multiNyAR     = multiNyAR;
    this.detectMarkers = detectMarkers;
    this.isDone        = false;
  }
  
  
  @Override
  public void update ()
  {
    if (isDone)
    {
      return;
    }
    
    List<MarkerModel> modelsOfPlayer1      = null;
    List<MarkerModel> modelsOfPlayer2      = null;
    MarkerModel       randomModelOfPlayer1 = null;
    MarkerModel       randomModelOfPlayer2 = null;
    boolean           canModelOfPlayer1Act = false;
    boolean           canModelOfPlayer2Act = false;
    
    modelsOfPlayer1 = detectMarkers.getMarkerModelsForPlayer (Player.PLAYER_1);
    modelsOfPlayer2 = detectMarkers.getMarkerModelsForPlayer (Player.PLAYER_2);
    
    if (modelsOfPlayer1.isEmpty () || modelsOfPlayer2.isEmpty ())
    {
      isDone = true;
      return;
    }
    
    multiNyAR.showMessage3D ("SWAPPING", 1000);
    
    Collections.shuffle (modelsOfPlayer1);
    Collections.shuffle (modelsOfPlayer2);
    
    randomModelOfPlayer1 = modelsOfPlayer1.get (0);
    randomModelOfPlayer2 = modelsOfPlayer2.get (0);
    
    canModelOfPlayer1Act = randomModelOfPlayer1.canAct ();
    canModelOfPlayer2Act = randomModelOfPlayer2.canAct ();
    
    randomModelOfPlayer1.setPlayerNumber (Player.PLAYER_2);
    randomModelOfPlayer1.addProperty     ("swapped");
    
    if (randomModelOfPlayer1.getCharacterInfo ().isAlive ())
    {
      randomModelOfPlayer1.setCanAct (canModelOfPlayer2Act);
    }
    
    randomModelOfPlayer2.setPlayerNumber (Player.PLAYER_1);
    randomModelOfPlayer2.addProperty     ("swapped");
    
    if (randomModelOfPlayer2.getCharacterInfo ().isAlive ())
    {
      randomModelOfPlayer2.setCanAct (canModelOfPlayer1Act);
    }
    
    randomModelOfPlayer1.update ();
    randomModelOfPlayer2.update ();
    
    isDone = true;
  }
}
