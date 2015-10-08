package ky.gamelogic;

import adtest01.DetectMarkers;
import adtest01.MarkerModel;
import adtest01.MultiNyAR;


public class DefenseToAttack
implements   Effect
{
  private MultiNyAR     multiNyAR;
  private DetectMarkers detectMarkers;
  private boolean       isDone;
  
  
  public DefenseToAttack
  (
    MultiNyAR      multiNyAR,
    DetectMarkers detectMarkers
  )
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
    
    multiNyAR.showMessage3D ("DEFENSE TO ATTACK", 1000);
    
    for (MarkerModel markerModel : detectMarkers.getMarkerModelsForPlayer (multiNyAR.getActivePlayer ()))
    {
      if (markerModel.getCharacterInfo ().isAlive ())
      {
        Statistics statistics = markerModel.getStatistics ();
        
        int defense = statistics.getCurrentDefense ();
        int attack  = statistics.getCurrentAttack  ();
        
        statistics.setCurrentDefense (0);
        statistics.setCurrentAttack  (attack + defense);
        markerModel.update           ();
      }
    }
    
    isDone = true;
  }
}
