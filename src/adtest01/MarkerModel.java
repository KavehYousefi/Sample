package adtest01;
// MarkerModel.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, April 2010

/* Holds NyARToolkit marker information and the Java3D scene graph for its
   associated model.

   The model is loaded using the PropManager class, which is described in Chapter 16
   of "Killer Game Programming in Java" (http://fivedots.coe.psu.ac.th/~ad/jg/ch9/)
*/


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.util.*;



import javax.media.j3d.*;
import javax.vecmath.*;

import commies.BoundsRenderer;

//import jp.nyatla.nyartoolkit.java3d.utils.*;
import jp.nyatla.nyartoolkit.core.*;
//import jp.nyatla.nyartoolkit.core.param.NyARParam;
import jp.nyatla.nyartoolkit.core.transmat.NyARTransMatResult;
//import jp.nyatla.nyartoolkit.detector.*;
//import jp.nyatla.nyartoolkit.core.types.*;



public class MarkerModel
{
  private final String MARKER_DIR = "Data/";
  private final double MARKER_SIZE = 0.095;    // 95 cm width and height in Java 3D world units


  private String markerName, modelName;
  private NyARCode markerInfo = null;     // NYArToolkit marker details

  private TransformGroup moveTg;      // for moving the marker model

  private Switch visSwitch;           // for changing the model's visibility
  private boolean isVisible;

  private SmoothMatrix sMat;          // for smoothing the transforms applied to the model

  // details about a model's position and orientation (in degrees)
  private Point3d posInfo = null;
  private Point3d rotsInfo = null;

  private int numTimesLost = 0;   // number of times marker for this model not detected
  
  // @TODO Added by me.
  private BoundsRenderer interactionBounds;
  
  
  
  public MarkerModel
  (
    String  markerFnm,
    String  modelFnm,
    double  scale,
    boolean hasCoords
  )
  {
    markerName = markerFnm;
    // remove filename extension
    modelName  = modelFnm.substring (0, modelFnm.lastIndexOf('.'));

    // build a branch for the model: TG --> Switch --> TG --> model

    // load the model, with scale and coords info
    TransformGroup modelTG = loadModel(modelFnm, scale, hasCoords);

    // create switch for model visibility 
    visSwitch = new Switch  ();
    visSwitch.setCapability (Switch.ALLOW_SWITCH_WRITE);
    visSwitch.addChild      (modelTG);
    visSwitch.setWhichChild (Switch.CHILD_NONE );   // make invisible
    isVisible = false;
    
    
    interactionBounds = new BoundsRenderer (new BoundingSphere (modelTG.getBounds ()));
    modelTG.addChild (interactionBounds.getRootNode ());
    interactionBounds.setColor (new Color3f (1.0f, 1.0f, 0.0f));
    
    
    // create transform group for positioning the model
    moveTg = new TransformGroup ();
    moveTg.setCapability        (TransformGroup.ALLOW_TRANSFORM_WRITE);  // so this tg can change
    moveTg.addChild             (visSwitch);
    
    // load marker info
    try
    {
      /*
       * TODO: INVALID!
      markerInfo = new NyARCode(16,16);    // default integer width, height
      markerInfo.loadARPattFromFile(MARKER_DIR+markerName);  // load marker image
      */
      
      // TODO: ADDED BY ME.
      try
      {
        markerInfo = NyARCode.createFromARPattFile
        (
          new FileInputStream (new File (MARKER_DIR + markerName)),
          16, 16
        );
      }
      catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
    }
    catch(NyARException e)
    {
      System.out.println(e);  
      markerInfo = null;
    }
  
    sMat = new SmoothMatrix();
  }  // end of MarkerModel()
  
  
  
  public BoundsRenderer getInteractionBounds ()
  {
    return interactionBounds;
  }
  
  public void setInteractionBounds (BoundsRenderer interactionBounds)
  {
    this.interactionBounds = interactionBounds;
  }
  
  
  
  private TransformGroup loadModel(String modelFnm, double scale, boolean hasCoords)
  // load the model, rotating and scaling it
  {
    PropManager propMan = new PropManager(modelFnm, hasCoords);  

    // get the TG for the prop (model)
    TransformGroup propTG = propMan.getTG();

    // rotate and scale the prop
    Transform3D modelT3d = new Transform3D();
    modelT3d.rotX( Math.PI/2.0 );    
         // the prop lies flat on the marker; rotate forwards 90 degrees so it is standing
    Vector3d scaleVec = calcScaleFactor(propTG, scale);   // scale the prop
    modelT3d.setScale( scaleVec );

    TransformGroup modelTG = new TransformGroup(modelT3d);
    modelTG.addChild(propTG);

    return modelTG;
  }  // end of loadModel()



  private Vector3d calcScaleFactor(TransformGroup modelTG, double scale)
  // Scale the prop based on its original bounding box size
  {
     BoundingBox boundbox = new BoundingBox (modelTG.getBounds ());
     Point3d     lower    = null;
     Point3d     upper    = null;
     
     System.out.println(boundbox);
     
     // obtain the upper and lower coordinates of the box
     lower = new Point3d ();
     boundbox.getLower   (lower);
     upper = new Point3d ();
     boundbox.getUpper   (upper);
     
     // store the largest X, Y, or Z dimension and calculate a scale factor
     double max = 0.0;
     if (Math.abs (upper.x - lower.x) > max)
     {
       max = Math.abs (upper.x - lower.x);
     }
     
     if (Math.abs (upper.y - lower.y) > max)
     {
       max = Math.abs (upper.y - lower.y);
     }
     
     if( Math.abs (upper.z - lower.z) > max)
     {
       max = Math.abs (upper.z - lower.z);
     }
     
     double scaleFactor = scale/max;
     System.out.printf
     (
       "MarkerModel.calcScaleFactor(): max dimension: %.3f;  " +
                                       "scale factor: %.3f\n",
       max, scaleFactor
     );
     
     // limit the scaling so that a big model isn't scaled too much
     if (scaleFactor < 0.0005)
     {
       scaleFactor = 0.0005;
     }

     return new Vector3d (scaleFactor, scaleFactor, scaleFactor);
  }  // end of calcScaleFactor()



  public String getNameInfo()
  { return markerName + " / " + modelName; }

  public NyARCode getMarkerInfo()
  {  return markerInfo;  }

  public double getMarkerWidth()
  { // System.out.println("Width: " + markerInfo.getWidth());
    return MARKER_SIZE;   // markerInfo.getWidth() not valid since requires Java 3D units
  }


  public TransformGroup getMoveTg()
  {  return moveTg;  }


  public void moveModel (NyARTransMatResult transMat)
  // detected marker so update model's moveTG
  {
    visSwitch.setWhichChild( Switch.CHILD_ALL );   // make visible
    isVisible = true;

    sMat.add(transMat);

    Matrix4d    mat   = sMat.get        ();
    Transform3D t3d   = new Transform3D (mat);
    int         flags = t3d.getType     ();
    
    if ((flags & Transform3D.AFFINE) == 0)
    {
      System.out.println ("Ignoring non-affine transformation");
    }
    else
    {
      if (moveTg != null)
      {
        moveTg.setTransform (t3d);
      }
      
      // System.out.println("transformation matrix: " + mat);
      calcPosition  (mat);
      calcEulerRots (mat);
    }
  }  // end of moveModel()




  private void calcPosition(Matrix4d mat)
  // extract the (x,y,z) position vals stored in the matrix
  {
    // convert to cm and round
    double x = roundToNumPlaces( mat.getElement(0,3)*100, 1);
    double y = roundToNumPlaces( mat.getElement(1,3)*100, 1);
    double z = roundToNumPlaces( mat.getElement(2,3)*100, 1);
    // System.out.println(getNameInfo() + " (" + x + ", " + y + ", " + z + ")");
    posInfo = new Point3d(x, y, z);
  }  // end of reportPosition()



  private double roundToNumPlaces(double val, int numPlaces) 
  {
    double power = Math.pow(10, numPlaces);
    long temp = Math.round(val*power);
    return ((double)temp)/power;
  }


  public Point3d getPos()
  {  return posInfo;  }



  private void calcEulerRots(Matrix4d mat)
  /* calculate the Euler rotation angles from the upper 3x3 rotation
     components of the 4x4 transformation matrix. 
     Based on code by Daniel Selman, December 1999
     which is based on pseudo-code in "Matrix and Quaternion FAQ", Q37
       http://www.j3d.org/matrix_faq/matrfaq_latest.html
  */
  {
    rotsInfo = new Point3d();

    rotsInfo.y = -Math.asin(mat.getElement(2,0));
    double c = Math.cos(rotsInfo.y);

    double tRx, tRy, tRz;
    if(Math.abs(rotsInfo.y) > 0.00001) {
      tRx = mat.getElement(2,2)/c;
      tRy = -mat.getElement(2,1)/c;
      rotsInfo.x = Math.atan2(tRy, tRx);

      tRx = mat.getElement(0,0)/c;
      tRy = -mat.getElement(1,0)/c;
      rotsInfo.z = Math.atan2(tRy, tRx);
    }
    else {
      rotsInfo.x  = 0.0;

      tRx = mat.getElement(1,1);
      tRy = mat.getElement(0,1);
      rotsInfo.z = Math.atan2(tRy, tRx);
    }

    rotsInfo.x = -rotsInfo.x;
    rotsInfo.z = -rotsInfo.z;

    // ensure the values are positive by adding 2*PI if necessary...
    if(rotsInfo.x < 0.0)
    rotsInfo.x += 2*Math.PI;

    if(rotsInfo.y < 0.0)
      rotsInfo.y += 2*Math.PI;

    if(rotsInfo.z < 0.0)
      rotsInfo.z += 2*Math.PI;

    // convert to degrees and round
    rotsInfo.x = roundToNumPlaces( Math.toDegrees(rotsInfo.x), 0);
    rotsInfo.y = roundToNumPlaces( Math.toDegrees(rotsInfo.y), 0);
    rotsInfo.z = roundToNumPlaces( Math.toDegrees(rotsInfo.z), 0);

    // System.out.println(getNameInfo() + " rots (" + 
    //         rotsInfo.x + ", " + rotsInfo.y + ", " + rotsInfo.z + ")");
  }  // end of calcEulerRots()


  public Point3d getRots()
  {  return rotsInfo; }



  public void resetNumTimesLost()
  {  numTimesLost = 0;  }

  public void incrNumTimesLost()
  {  numTimesLost++;  }

  public int getNumTimesLost()
  {  return numTimesLost;  }



  public void hideModel()
  {
    visSwitch.setWhichChild( Switch.CHILD_NONE );   // make model invisible
    isVisible = false;
  } 

  public boolean isVisible()
  {  return isVisible; }


}  // end of MarkerModel class
