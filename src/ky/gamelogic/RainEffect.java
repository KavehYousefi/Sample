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
        Element element        = null;
        boolean isWaterElement = false;
        boolean isFireElement  = false;
        boolean isMetalElement = false;
        
        element        = markerModel.getCharacterInfo ()
                                    .getElement       ();
        isWaterElement = element.equals (Element.ICE);
        isFireElement  = element.equals (Element.FIRE);
        isMetalElement = element.equals (Element.METAL);
        
        if (isWaterElement)
        {
          int currentPower = 0;
          
          currentPower = markerModel.getStatistics ().getCurrentPower ();
          currentPower = currentPower * 2;
          markerModel.getStatistics ().setCurrentPower (currentPower);
          markerModel.addProperty   ("waterPlus");
          markerModel.update        ();
        }
        else if (isFireElement)
        {
          int currentPower = 0;
          
          currentPower = markerModel.getStatistics ().getCurrentPower ();
          currentPower = currentPower / 2;
          markerModel.getStatistics ().setCurrentPower (currentPower);
          markerModel.addProperty   ("extinguished");
          markerModel.update        ();
        }
        else if (isMetalElement)
        {
          int currentAttack = 0;
          
          currentAttack = markerModel.getStatistics ().getCurrentAttack ();
          currentAttack = currentAttack / 2;
          markerModel.getStatistics ().setCurrentAttack  (currentAttack);
          markerModel.getStatistics ().setCurrentDefense (0);
          markerModel.addProperty   ("rusty");
          markerModel.update        ();
        }
      }
    }
    
    isDone = true;
    
    JOptionPane.showMessageDialog (null, "?");
  }
}
