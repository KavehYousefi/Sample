package ky.gamelogic;

import adtest01.DetectMarkers;
import adtest01.MarkerModel;
import adtest01.MultiNyAR;


public class Resurrection
implements   Effect
{
  private MultiNyAR     multiNyAR;
  private DetectMarkers detectMarkers;
  private boolean       isDone;
  
  
  public Resurrection
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
    
    multiNyAR.showMessage3D ("RESURRECTION", 1000);
    
    for (MarkerModel markerModel : detectMarkers.getMarkerModelsForPlayer (multiNyAR.getActivePlayer ()))
    {
      if (! markerModel.getCharacterInfo ().isAlive ())
      {
        markerModel.getCharacterInfo ().setAlive (true);
        markerModel.getStatistics ().reset       ();
        markerModel.setCanAct                    (true);
        markerModel.update                       ();
        isDone = true;
        break;
      }
    }
  }
}
