package adtest01;


// DetectMarkers.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, April 2010

/* Collection of MarkerModel objects and a detector that finds
   markers in the camera's captured image. The new marker position
   is used to move its corresponding model
*/


import java.util.*;

import javax.media.j3d.Transform3D;
//import javax.media.j3d.*;
import javax.vecmath.*;

import collision.CollisionEvent;
import collision.CollisionListener;
import jp.nyatla.nyartoolkit.java3d.utils.*;
import jp.nyatla.nyartoolkit.core.*;
import jp.nyatla.nyartoolkit.core.param.NyARParam;
import jp.nyatla.nyartoolkit.core.transmat.NyARTransMatResult;
import jp.nyatla.nyartoolkit.detector.*;
//import jp.nyatla.nyartoolkit.core.types.*;



public class DetectMarkers
{
  private final static double MIN_CONF = 0.3; 
                // smallest confidence accepted for finding a marker

  private final static int CONF_SIZE = 1000;   
               // for converting confidence level double <--> integer

  private final static int MAX_NO_DETECTIONS = 50;
               // number of times a marker goes undetected before being made invisible


  private ArrayList<MarkerModel> markerModels;
  private int                    numMarkers;

  private MultiNyAR              top;    // for reporting status
  private NyARDetectMarker       detector;

  private NyARTransMatResult transMat = new NyARTransMatResult();   
     // transformation matrix for a marker, which is used to move its model
  
  private List<CollisionListener> collisionListeners;
  
  
  public DetectMarkers (MultiNyAR top)
  { 
    this.top     = top;
    markerModels = new ArrayList<MarkerModel> ();
    numMarkers   = 0;
    
    this.collisionListeners = new ArrayList<CollisionListener> ();
  }  // end of DetectMarkers()
  
  
  public void addCollisionListener (CollisionListener listener)
  {
    if (listener != null)
    {
      collisionListeners.add (listener);
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
  
  public List<MarkerModel> getMarkerModelsForPlayer (int playerNumber)
  {
    List<MarkerModel> modelsForPlayer = new ArrayList<MarkerModel> ();
    
    for (MarkerModel currentModel : markerModels)
    {
      if ((currentModel                    != null) &&
          (currentModel.getPlayerNumber () == playerNumber))
      {
        modelsForPlayer.add (currentModel);
      }
    }
    
    return modelsForPlayer;
  }
  
  


  public void addMarker (MarkerModel mm)
  { 
    markerModels.add (numMarkers, mm);  // add to end of list
    numMarkers++;
  }



  public void createDetector(NyARParam params, J3dNyARRaster_RGB rasterRGB)
  // create a single detector for all the markers
  {
    NyARCode[] markersInfo = new NyARCode[numMarkers];
    double[]   widths      = new double  [numMarkers];
    int        i           = 0;
    
    for (MarkerModel mm : markerModels)
    {
      markersInfo[i] = mm.getMarkerInfo();
      widths     [i] = mm.getMarkerWidth();
      // System.out.println("Object " + i + ": marker info = " + markersInfo[i]);
      i++;
    }

    try
    {
      // TODO: CHANGED BY MYSELF.
//      detector = new NyARDetectMarker(params, markersInfo, widths, numMarkers,
//                                         rasterRGB.getBufferReader().getBufferType());
      detector = new NyARDetectMarker(params, markersInfo, widths, numMarkers);
      
      detector.setContinueMode(false);   // no history stored; use SmoothMatrix instead
    }
    catch (NyARException e)
    {  System.out.println("Could not create markers detector");  
       System.exit(1);
    }
  }  // end of createDetector()
  
  
  
  public void updateModels(J3dNyARRaster_RGB rasterRGB)
  // move marker models using the detected marker positions inside the raster
  {
    int numDetections = getNumDetections (detector, rasterRGB);
    // System.out.println("numDetections: " + numDetections);

    try
    {
      StringBuffer statusInfo = new StringBuffer();   // for holding status information
      
      statusInfo.append ("DetectMarkers.updateModels (): ...\n");
      
      // find the best detected match for each marker
      for (int mkIdx = 0; mkIdx < numMarkers; mkIdx++)
      {
        MarkerModel mm = markerModels.get (mkIdx);

        int[]  detectInfo      = findBestDetectedIdx (detector, numDetections, mkIdx);   // look for mkIdx
        int    bestDetectedIdx = detectInfo[0];
        double confidence      = ((double) detectInfo[1])/CONF_SIZE;  // convert back to double
        
        if (bestDetectedIdx == -1)   // marker not found so incr numTimesLost
        {
          mm.incrNumTimesLost ();
        }
        // marker found
        else 
        {
          // detected a marker for mkIdx with high confidence
          if (confidence >= MIN_CONF)
          {
            mm.resetNumTimesLost ();
            // apply the transformation from the detected marker to the marker's model
            // System.out.println("  For markers list index " + mkIdx + 
            //                                ": best detected index = " + bestDetectedIdx);
            detector.getTransmationMatrix (bestDetectedIdx, transMat);
            
            if (transMat.has_value)
            {
              mm.moveModel (transMat);
            }
            else
            {
              System.out.println ("Problem with transformation matrix");
            }
            
            // TODO: BY ME -- Show model independent from confidence.
            mm.moveModel (transMat);
          }
          // else // found a marker, but with low confidence
          //  System.out.println("  ***** " + mkIdx + " conf: " + confidence);
        }
        
        // marker not detected too many times
        if (mm.getNumTimesLost () > MAX_NO_DETECTIONS)
        {
          mm.hideModel ();    // make its model invisible
        }

        statusInfo.append (mkIdx + ". " + mm.getNameInfo() + " (" + confidence + ")\n");
        addToStatusInfo   (mm, statusInfo);
      }
      top.setStatus( statusInfo.toString());   // display marker models status in the GUI
      
      
      checkForCollision ();
    }
    catch (NyARException e)
    {
      System.out.println(e);
    }
  }  // end of updateModels()
  
  
  // TODO: Added by me.
  private void checkForCollision ()
  {
    //double minimumDistance = 0.147271;
    double minimumDistance = 0.160000;
    
    /* Problem: Distances depend on camera position.
     *          The nearer the camera, the smaller the distance gets.
     * Solution: "Strahlensatz"?
     */
    for (MarkerModel observingModel : markerModels)
    {
      Point3d           myPosition  = null;
      List<MarkerModel> otherModels = null;
      
      myPosition = observingModel.getPos ();
      
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
        
        try
        {
          distance = getMarkerDistance (observingModel, observedModel);
          System.out.format
          (
            "DetectMarkers.checkForCollision(): %s -> %s distance = %f%n",
            observingModel.getNameInfo (),
            observedModel.getNameInfo  (),
            distance
          );
          
          if (distance <= minimumDistance)
          {
            CollisionEvent event = null;
            
            event = new CollisionEvent
            (
              observingModel,
              observedModel,
              distance,
              timestamp
            );
            
            fireCollisionStartedEvent (event);
          }
        }
        catch (NullPointerException npe)
        {
          continue;
        }
      }
    }
  }
  
  
  // -> "http://www.hitl.washington.edu/artoolkit/documentation/faq.htm"
  // -> "http://www.hitl.washington.edu/artoolkit/mail-archive/message-thread-00798-Re--Distance-between-two.html"
  // -> "https://github.com/lucab/artoolkit/blob/master/examples/relation/relationTest.c"
  private double getMarkerDistance (MarkerModel observingModel, MarkerModel observedModel)
  {
    if (observingModel == null)
    {
      throw new NullPointerException ("Observing model is null.");
    }
    
    if (observedModel == null)
    {
      throw new NullPointerException ("Observed model is null.");
    }
    
    double      distance            = 0.0;
    Transform3D transform1          = null;
    Matrix4d    inverseOfMatrix1    = null;
    Transform3D transform2          = null;
    Matrix4d    matrix2             = null;
    Vector3d    translationColumn3d = null;
    Matrix4d    resultMatrix        = null;
    
    transform1          = new Transform3D ();
    inverseOfMatrix1    = new Matrix4d    ();
    transform2          = new Transform3D ();
    matrix2             = new Matrix4d    ();
    translationColumn3d = new Vector3d    ();
    
    observingModel.getMoveTg ().getTransform (transform1);
//    transform1.invert ();
    transform1.get    (inverseOfMatrix1);
    inverseOfMatrix1.invert ();
    
    observedModel.getMoveTg ().getTransform (transform2);
    transform2.get (matrix2);
    
    resultMatrix = inverseOfMatrix1;
    resultMatrix.mul (matrix2);
    
    // The fourth column (= translation) holds the distance coordinates.
    translationColumn3d.setX (resultMatrix.getM03 ());
    translationColumn3d.setY (resultMatrix.getM13 ());
    translationColumn3d.setZ (resultMatrix.getM23 ());
    
    distance = translationColumn3d.length ();
    
    return distance;
  }


  private int getNumDetections(NyARDetectMarker detector, J3dNyARRaster_RGB rasterRGB)
  {
    int numDetections = 0;
    try
    {
      // TODO: FORMATTED BY MYSELF.
      synchronized (rasterRGB)
      {
        /*
        if (rasterRGB.hasData())
        {
          numDetections = detector.detectMarkerLite(rasterRGB, 100);
        }
        */
        numDetections = detector.detectMarkerLite (rasterRGB, 100);
      }
    }
    catch (NyARException e)
    {
      System.out.println(e);
    }

    return numDetections;
  }  // end of getNumDetections()



  private int[] findBestDetectedIdx(NyARDetectMarker detector, int numDetections, int markerIdx)
  /* return best detected marker index for marker markerIdx from all detected markers,
     along with its confidence value as an integer */
  {
    int    iBest    = -1;
    double confBest = -1;

    // System.out.println("  Look at detections for marker " + markerIdx);
    for (int i = 0; i < numDetections; i++) // check all detected markers
    {
      int    codesIdx = detector.getARCodeIndex (i);
      double conf     = detector.getConfidence  (i);
      // System.out.println("    detections index["+i+"] = code index " + codesIdx + " -- conf: " + conf);

      if ((codesIdx == markerIdx) && (conf > confBest))
      {
        iBest    = i;  // detected marker index with highest confidence
        confBest = conf;
      }
    }
    // System.out.println("    mark index "+ markerIdx+" iBest = " + iBest + " conf: " + confBest);

    int[] detectInfo = {iBest, (int)(confBest*CONF_SIZE)};
    return detectInfo;
  }  // end of findBestDetectedIdx()


  private void addToStatusInfo(MarkerModel mm, StringBuffer statusInfo)
  // add details about MarkerModel object to status info string
  {
    if (!mm.isVisible())
      statusInfo.append(" not visible\n");
    else {   // model is visible, so report position and orientation
      Point3d pos = mm.getPos();
      if (pos != null)
        statusInfo.append("    at (" + pos.x + ", " + pos.y + ", " + pos.z + ")\n");
      else
        statusInfo.append("    at an unknown position\n");

      Point3d rots = mm.getRots();
      if (rots != null)
        statusInfo.append("    rots (" + rots.x + ", " + rots.y + ", " + rots.z + ")\n");
      else
        statusInfo.append("    with unknown rotations\n");
    }
  }  // end of addToStatusInfo()


}  // end of class DetectMarkers
