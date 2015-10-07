package adtest01;
// SmoothMatrix.java
// Andrew Davison, ad@fivedots.coe.psu.ac.th, April 2010

/* To reduce shaking of model due to slight variations in
   calculaed rotations and positions in the transformation
   matrix.
*/

import java.util.*;

import javax.media.j3d.*;
import javax.vecmath.*;

import jp.nyatla.nyartoolkit.core.transmat.NyARTransMatResult;



public class SmoothMatrix
{
  private final static int MAX_SIZE = 10;

  private ArrayList<Matrix4d> matsStore;
  private int                 numberOfMatrices;


  public SmoothMatrix() 
  {
    this.matsStore        = new ArrayList<Matrix4d>();
    this.numberOfMatrices = 0;
  } // end of SmoothMatrix()
  
  
  public boolean add (NyARTransMatResult transMat)
  {
    Matrix4d matrix4d = null;
    
    matrix4d = getMatrix4dFromNyARTransMatResult (transMat);
    
    if (isAffineMatrix (matrix4d))
    {
      if (numberOfMatrices == MAX_SIZE)
      {
        matsStore.remove (0);   // remove oldest
        numberOfMatrices--;
      }
      
      matsStore.add(matrix4d);  // add at end of list
      numberOfMatrices++;
      return true;
    }
    else
    {
      System.out.println("Not adding a non-affine matrix");
      return false;
    }
  }  // end of add()


  public Matrix4d get ()
  // average matrices in store
  {
    if (numberOfMatrices == 0)
    {
      return null;
    }
    
    Matrix4d averageMatrix = new Matrix4d ();
    
    for (Matrix4d mat : matsStore)
    {
      averageMatrix.add (mat);
    }
    
    averageMatrix.mul (1.0 / numberOfMatrices);

    return averageMatrix;
  }  // end of get()
  
  
  private Matrix4d getMatrix4dFromNyARTransMatResult (NyARTransMatResult transMat)
  {
    Matrix4d matrix4d = null;
    
    matrix4d = new Matrix4d
    (
      -transMat.m00, -transMat.m01, -transMat.m02, -transMat.m03,
      -transMat.m10, -transMat.m11, -transMat.m12, -transMat.m13, 
       transMat.m20,  transMat.m21,  transMat.m22,  transMat.m23,
       0.0,           0.0,           0.0,           1.0
    );
    
    return matrix4d;
  }
  
  private boolean isAffineMatrix (Matrix4d matrix4d)
  {
    Transform3D transform3D = null;
    int         flags       = 0;
    
    transform3D = new Transform3D     (matrix4d);
    flags       = transform3D.getType ();
    
    if ((flags & Transform3D.AFFINE) == 0)
    {
      System.out.println ("Not adding a non-affine matrix");
      return false;
    }
    else
    {
      return true;
    }
  }
  
}  // end of SmoothMatrix class

