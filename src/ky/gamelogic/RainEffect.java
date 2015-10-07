package ky.gamelogic;

import javax.swing.JOptionPane;

import com.sun.j3d.utils.geometry.Cylinder;

import adtest01.DetectMarkers;
import adtest01.MarkerModel;
import adtest01.MultiNyAR;
import adtest01.TransformHierarchy;
import ky.appearance.WireframeAppearance;


public class RainEffect
implements   Effect
{
  private MultiNyAR         multiNyAR;
  private DetectMarkers     detectMarkers;
  private boolean           isDone;
  
  
  public RainEffect
  (
    MultiNyAR          multiNyAR,
    DetectMarkers      detectMarkers,
    TransformHierarchy hierarchy
  )
  {
    this.multiNyAR      = multiNyAR;
    this.detectMarkers  = detectMarkers;
    this.isDone         = false;
    
    Cylinder cylinder = new Cylinder (0.1f, 0.2f, Cylinder.GENERATE_NORMALS, new WireframeAppearance ());
    
    hierarchy.getMoveTransformGroup ().addChild (cylinder);
  }
  
  
  public MultiNyAR getMultiNyAR ()
  {
    return multiNyAR;
  }
  
  
  @Override
  public void update ()
  {
    if (isDone)
    {
      return;
    }
    
    multiNyAR.showMessage3D ("RAIN", 1000);
    
    for (MarkerModel markerModel : detectMarkers.getMarkerModels ())
    {
      boolean isCharacter = markerModel.isCharacter ();
      
      if (isCharacter)
      {
        boolean isWaterElement = false;
        boolean isFireElement  = false;
        
        isWaterElement = markerModel.getCharacterInfo ()
                                    .getElement       ()
                                    .equals           (Element.WATER);
        isFireElement  = markerModel.getCharacterInfo ()
                                    .getElement       ()
                                    .equals           (Element.FIRE);
        
        if (isWaterElement)
        {
          int currentPower = 0;
          
          currentPower = markerModel.getCharacterInfo ().getStatistics ().getCurrentPower ();
          currentPower = currentPower * 2;
          markerModel.getCharacterInfo ().getStatistics ().setCurrentPower (currentPower);
          markerModel.addProperty      ("frozen");
          markerModel.update           ();
        }
        else if (isFireElement)
        {
          int currentPower = 0;
          
          currentPower = markerModel.getCharacterInfo ().getStatistics ().getCurrentPower ();
          currentPower = currentPower / 2;
          markerModel.getCharacterInfo ().getStatistics ().setCurrentPower (currentPower);
          markerModel.addProperty      ("extinguished");
          markerModel.update           ();
        }
      }
    }
    
    isDone = true;
    
    JOptionPane.showMessageDialog (null, "?");
  }
}
