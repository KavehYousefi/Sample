package ky.gamelogic;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import adtest01.DetectMarkers;
import adtest01.MarkerModel;
import adtest01.MultiNyAR;


/**
 * Mixes all statistic points among all characters.
 * 
 * @author Kaveh Yousefi
 */
public class StatisticsMixer
implements   Effect
{
  private MultiNyAR     multiNyAR;
  private DetectMarkers detectMarkers;
  private boolean       isDone;
  
  
  public StatisticsMixer
  (
    MultiNyAR     multiNyAR,
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
    
    multiNyAR.showMessage3D ("MIXING", 1000);
    
    List<MarkerModel>   characterModels    = null;
    LinkedList<Integer> valuesToDistribute = null;
    
    characterModels    = new LinkedList<MarkerModel> ();
    valuesToDistribute = new LinkedList<Integer>     ();
    
    for (MarkerModel markerModel : detectMarkers.getMarkerModels ())
    {
      if (markerModel.isCharacter ())
      {
        characterModels.add (markerModel);
      }
    }
    
    // Collect all statistics data.
    for (MarkerModel markerModel : characterModels)
    {
      Statistics statistics = null;
      
      statistics = markerModel.getCharacterInfo ().getStatistics ();
      
      valuesToDistribute.add (statistics.getCurrentAttack  ());
      valuesToDistribute.add (statistics.getCurrentDefense ());
      valuesToDistribute.add (statistics.getCurrentPower   ());
    }
    
    Collections.shuffle (valuesToDistribute);
    
    // Distribute the collected statistics data.
    for (MarkerModel markerModel : characterModels)
    {
      Statistics statistics = null;
      
      statistics = markerModel.getCharacterInfo ().getStatistics ();
      statistics.setCurrentPower   (valuesToDistribute.removeFirst ());
      statistics.setCurrentAttack  (valuesToDistribute.removeFirst ());
      statistics.setCurrentDefense (valuesToDistribute.removeFirst ());
      markerModel.addProperty      ("mixed");
      markerModel.update           ();
    }
    
    isDone = true;
  }
}
