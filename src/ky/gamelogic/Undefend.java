package ky.gamelogic;

import java.util.List;

import adtest01.DetectMarkers;
import adtest01.MarkerModel;
import adtest01.MultiNyAR;


public class Undefend
implements   Effect
{
  private MultiNyAR     multiNyAR;
  private DetectMarkers detectMarkers;
  private boolean       isDone;
  
  
  public Undefend
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
    
    int               inactivePlayer = 0;
    List<MarkerModel> enemyModels    = null;
    
    inactivePlayer = multiNyAR.getInactivePlayer            ();
    enemyModels    = detectMarkers.getMarkerModelsForPlayer (inactivePlayer);
    
    for (MarkerModel markerModel : enemyModels)
    {
      boolean isAlive     = markerModel.getCharacterInfo ().isAlive     ();
      boolean isDefending = markerModel.getCharacterInfo ().isDefending ();
      
      if (isAlive && isDefending)
      {
        markerModel.getCharacterInfo ().setDefending (false);
        markerModel.addProperty                      ("undefended");
        markerModel.update                           ();
        isDone = true;
      }
    }
    
    if (isDone)
    {
      multiNyAR.showMessage3D ("UNDEFEND", 1000);
    }
  }
}
