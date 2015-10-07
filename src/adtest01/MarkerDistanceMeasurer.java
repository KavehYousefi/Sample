package adtest01;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix4d;
import javax.vecmath.Vector3d;

public class MarkerDistanceMeasurer
{
  public MarkerDistanceMeasurer ()
  {
  }
  
  
  // -> "http://www.hitl.washington.edu/artoolkit/documentation/faq.htm"
  // -> "http://www.hitl.washington.edu/artoolkit/mail-archive/message-thread-00798-Re--Distance-between-two.html"
  // -> "https://github.com/lucab/artoolkit/blob/master/examples/relation/relationTest.c"
  public double getMarkerDistance
  (
    MarkerModel observingModel,
    MarkerModel observedModel
  )
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
    
//    double distance = 0.0;
//    distance = observingModel.getPosition ().distance (observedModel.getPosition ());
//    System.out.println (distance);
    
    return distance;
  }
}
