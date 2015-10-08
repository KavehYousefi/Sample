package adtest01;


// DetectMarkers.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, April 2010

/* Collection of MarkerModel objects and a detector that finds
   markers in the camera's captured image. The new marker position
   is used to move its corresponding model
*/


import java.util.*;
import java.util.stream.Collectors;

import javax.vecmath.*;

import collision.CollisionEvent;
import collision.CollisionListener;
import jp.nyatla.nyartoolkit.java3d.utils.*;
import ky.angle.EulerAngles;
import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.core.param.NyARParam;
import jp.nyatla.nyartoolkit.core.transmat.NyARTransMatResult;
import jp.nyatla.nyartoolkit.detector.*;


public class DetectMarkers
{
  // smallest confidence accepted for finding a marker
//  private static final double MINIMUM_DETECTABLE_CONFIDENCE = 0.3;
  private static final double MINIMUM_DETECTABLE_CONFIDENCE = 0.8;
  // number of times a marker goes undetected before being made invisible
  private static final int    MAXIMUM_NUMBER_OF_TIMES_LOST  = 50;
  // Used in the method "getNumberOfDetections ()".
  private static final int    DETECTION_THRESHOLD           = 100;
  
  
  private ArrayList<MarkerModel>  markerModels;
  private int                     numberOfMarkers;
  
  @SuppressWarnings               ("unused")
  private MultiNyAR               top;    // for reporting status
  private NyARDetectMarker        detector;
  
  /* Transformation matrix for a marker, which is used to move
   * its model.
   */
  private NyARTransMatResult      transMat;   
  
  private List<CollisionListener> collisionListeners;
  private MarkerDistanceMeasurer  markerDistanceMeasurer;
  private CollisionDelayer        collisionDelayer;
  
  
  public DetectMarkers (MultiNyAR top)
  { 
    this.transMat               = new NyARTransMatResult ();
    this.top                    = top;
    this.markerModels           = new ArrayList<MarkerModel> ();
    this.numberOfMarkers        = 0;
    this.collisionListeners     = new ArrayList<CollisionListener> ();
    this.markerDistanceMeasurer = new MarkerDistanceMeasurer       ();
    this.collisionDelayer       = new CollisionDelayer             ();
    
    this.collisionDelayer.start ();
  }  // end of DetectMarkers()
  
  
  public void addCollisionListener (CollisionListener listener)
  {
    if (listener != null)
    {
      collisionListeners.add (listener);
    }
  }
  
  public List<MarkerModel> getMarkerModels ()
  {
    return markerModels;
  }
  
  public List<MarkerModel> getSortedMarkerModels (Comparator<MarkerModel> comparator)
  {
    ArrayList<MarkerModel> sortedModels = null;
    
    sortedModels = new ArrayList<MarkerModel> (markerModels);
    Collections.sort (sortedModels, comparator);
    
    return sortedModels;
  }
  
  public List<MarkerModel> getMarkerModelsForPlayer (int playerNumber)
  {
    List<MarkerModel> modelsForPlayer = new ArrayList<MarkerModel> ();
    
//    for (MarkerModel currentModel : markerModels)
//    {
//      if ((currentModel                    != null) &&
//          (currentModel.getPlayerNumber () == playerNumber))
//      {
//        modelsForPlayer.add (currentModel);
//      }
//    }
    
    modelsForPlayer = markerModels.stream ()
                      .filter  (markerModel
                                -> markerModelBelongsToPlayer
                                   (
                                     markerModel,
                                     playerNumber
                                   ))
                      .collect (Collectors.toList ());
    
    return modelsForPlayer;
  }
  
  public void addMarker (MarkerModel mm)
  { 
    markerModels.add (numberOfMarkers, mm);  // add to end of list
    numberOfMarkers++;
  }
  
  
  
  public void createDetector (NyARParam params, J3dNyARRaster_RGB rasterRGB)
  // create a single detector for all the markers
  {
    NyARCode[] markersInfo = new NyARCode[numberOfMarkers];
    double[]   widths      = new double  [numberOfMarkers];
    int        markerIndex = 0;
    
    for (MarkerModel markerModel : markerModels)
    {
      markersInfo[markerIndex] = markerModel.getMarkerInfo  ();
      widths     [markerIndex] = markerModel.getMarkerWidth ();
      markerIndex++;
    }
    
    try
    {
      // XXX: CHANGED BY MYSELF.
//      detector = new NyARDetectMarker(params, markersInfo, widths, numMarkers,
//                                         rasterRGB.getBufferReader().getBufferType());
      detector = new NyARDetectMarker
      (
        params,
        markersInfo,
        widths,
        numberOfMarkers
      );
      
      detector.setContinueMode (false);   // no history stored; use SmoothMatrix instead
    }
    catch (NyARException e)
    {
      System.out.println ("Could not create markers detector");  
      System.exit        (1);
    }
  }  // end of createDetector()
  
  
  
  public void updateModels (J3dNyARRaster_RGB rasterRGB)
  // move marker models using the detected marker positions inside the raster
  {
    int numberOfDetections = getNumberOfDetections (detector, rasterRGB);
    
    try
    {
      StringBuffer statusInfo = new StringBuffer ();   // for holding status information
      
      statusInfo.append ("DetectMarkers.updateModels (): ...\n");
      
      // find the best detected match for each marker
      for (int markerIndex = 0; markerIndex < numberOfMarkers; markerIndex++)
      {
        MarkerModel markerModel = markerModels.get (markerIndex);
        
        MarkerDetectionResult detectInfo      = null;
        int                   bestDetectedIdx = 0;
        double                confidence      = 0.0;
        
        // look for mkIdx
        detectInfo      = findBestDetectedMarker
        (
          detector,
          numberOfDetections,
          markerIndex
        );
        bestDetectedIdx = detectInfo.getIndexOfBestMarker      ();
        confidence      = detectInfo.getConfidenceOfBestMarker ();
        
        // Marker not found (bestMarkerIndex = -1), so increment numTimesLost.
        if (! detectInfo.hasResult ())
        {
          markerModel.incrNumTimesLost ();
        }
        // marker found
        else 
        {
          // detected a marker for mkIdx with high confidence
          if (confidence >= MINIMUM_DETECTABLE_CONFIDENCE)
          {
            markerModel.resetNumTimesLost ();
            // apply the transformation from the detected marker to the marker's model
            detector.getTransmationMatrix (bestDetectedIdx, transMat);
            
            // XXX: EDITED BY ME -- DEACTIVATED THIS CHECK.
//            if (transMat.has_value)
//            {
//              markerModel.moveModel (transMat);
//            }
//            else
//            {
//              System.out.println ("Problem with transformation matrix");
//            }
            
            markerModel.moveModel (transMat);
          }
        }
        
        // marker not detected too many times
        if (markerModel.getNumTimesLost () > MAXIMUM_NUMBER_OF_TIMES_LOST)
        {
          markerModel.hideModel ();    // make its model invisible
        }
        
        statusInfo.append (markerIndex + ". " + markerModel.getNameInfo () + " (" + confidence + ")\n");
        addToStatusInfo   (markerModel, statusInfo);
      }
      
      
      checkForCollision ();
    }
    catch (NyARException e)
    {
      System.out.println (e);
    }
  }  // end of updateModels()
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private boolean markerModelBelongsToPlayer (MarkerModel markerModel, int playerNumber)
  {
    return ((markerModel                    != null) && 
            (markerModel.getPlayerNumber () == playerNumber));
  }
  
  // XXX: Added by me.
  private void checkForCollision ()
  {
    if (! collisionDelayer.isUpdatePossible ())
    {
      return;
    }
    
    for (MarkerModel observingModel : markerModels)
    {
      Point3d           myPosition  = null;
      List<MarkerModel> otherModels = null;
      
      myPosition = observingModel.getPosition ();
      
      if (myPosition == null)
      {
        continue;
      }
      
      otherModels = new ArrayList<MarkerModel> (markerModels);
      otherModels.remove                       (observingModel);
      
      
      for (MarkerModel observedModel : otherModels)
      {
        double distance  = 0.0;
        long   timestamp = System.currentTimeMillis ();
        
        if ((observedModel == null) || (! observedModel.isVisible ()))
        {
          continue;
        }
        
        if ((! observingModel.canCollide ()) ||
            (! observedModel.canCollide  ()))
        {
          continue;
        }
        
        try
        {
          distance = markerDistanceMeasurer.getMarkerDistance (observingModel, observedModel);
          
          if (observingModel.canCollideWith (observedModel))
          {
            collisionDelayer.updateTimestamp ();
            CollisionEvent event = null;
            
            event = new CollisionEvent
            (
              observingModel,
              observedModel,
              distance,
              timestamp
            );
            
            fireCollisionStartedEvent (event);
            return;
          }
        }
        catch (NullPointerException npe)
        {
          continue;
        }
      }
    }
  }
  
  protected void fireCollisionStartedEvent (CollisionEvent event)
  {
    for (CollisionListener listener : collisionListeners)
    {
      if (listener != null)
      {
        listener.collisionStarted (event);
      }
    }
  }
  


  private int getNumberOfDetections
  (
    NyARDetectMarker  detector,
    J3dNyARRaster_RGB rasterRGB
  )
  {
    int numDetections = 0;
    try
    {
      // XXX: FORMATTED BY MYSELF.
      synchronized (rasterRGB)
      {
        /*
        if (rasterRGB.hasData())
        {
          numDetections = detector.detectMarkerLite(rasterRGB, 100);
        }
        */
        
        numDetections = detector.detectMarkerLite (rasterRGB, DETECTION_THRESHOLD);
      }
    }
    catch (NyARException e)
    {
      System.out.println (e);
    }
    
    return numDetections;
  }  // end of getNumDetections()



  /* return best detected marker index for marker markerIdx from all detected markers,
  along with its confidence value as an integer */
  private MarkerDetectionResult findBestDetectedMarker
  (
    NyARDetectMarker detector,
    int              numberOfDetections,
    int              markerIndex
  )
  {
    MarkerDetectionResult detectionResult      = null;
    int                   indexOfBestMarker    = MarkerDetectionResult.NO_MARKER_INDEX;
    double                bestMarkerConfidence = -1.0;
    
    detectionResult = new MarkerDetectionResult ();
    
    // check all detected markers
    for (int detectionIndex = 0; detectionIndex < numberOfDetections;
             detectionIndex++)
    {
      int     arCodeIndex       = 0;
      double  currentConfidence = 0.0;
      boolean isThisMarker      = false;
      
      arCodeIndex       = detector.getARCodeIndex (detectionIndex);
      currentConfidence = detector.getConfidence  (detectionIndex);
      isThisMarker      = (arCodeIndex == markerIndex);
      
      // Detected marker index with highest confidence
      if (isThisMarker)
      {
        indexOfBestMarker    = detectionIndex;
        bestMarkerConfidence = Math.max (bestMarkerConfidence,
                                         currentConfidence);
      }
    }
    
    detectionResult.setIndexOfBestMarker      (indexOfBestMarker);
    detectionResult.setConfidenceOfBestMarker (bestMarkerConfidence);
    
    return detectionResult;
  }  // end of findBestDetectedIdx()


  private void addToStatusInfo (MarkerModel markerObject, StringBuffer statusInfo)
  // add details about MarkerModel object to status info string
  {
    EulerAngles orientation = null;
    
    if (! markerObject.isVisible ())
    {
      statusInfo.append (" not visible\n");
    }
    // model is visible, so report position and orientation
    else
    {
      Point3d pos = markerObject.getPosition ();
      if (pos != null)
      {
        statusInfo.append ("    at (" + pos.x + ", " + pos.y + ", " + pos.z + ")\n");
      }
      else
      {
        statusInfo.append ("    at an unknown position\n");
      }
      
      orientation = markerObject.getOrientation ();
      
      if (orientation != null)
      {
        statusInfo.append ("    " + orientation + "\n");
      }
      else
      {
        statusInfo.append ("    with unknown rotations\n");
      }
    }
  }  // end of addToStatusInfo()
  
  
}  // end of class DetectMarkers
