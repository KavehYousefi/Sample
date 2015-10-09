package ky.gamelogic;

import adtest01.DetectMarkers;
import adtest01.MarkerModel;
import adtest01.MultiNyAR;


/**
 * Randomly resurrects one of the active player's characters.
 * 
 * @author Kaveh Yousefi
 */
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
    
    for (MarkerModel markerModel : detectMarkers.getMarkerModelsForPlayer (multiNyAR.getActivePlayer ()))
    {
      if (! markerModel.getCharacterInfo ().isAlive ())
      {
        multiNyAR.showMessage3D ("RESURRECTION", 1000);
        
        markerModel.getCharacterInfo ().setAlive (true);
        markerModel.getStatistics ().reset       ();
        markerModel.setCanAct                    (true);
        markerModel.addProperty                  ("resurrected");
        markerModel.update                       ();
        isDone = true;
        return;
      }
    }
  }
}
