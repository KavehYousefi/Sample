package ky.transform2;

import ky.Position;
import ky.angle.Angle;
import ky.transform.edgerotation.RotationAroundEdge;
import ky.transform.edgerotation.RotationEdge;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;


// 14.03.2015
public class TransformUtils
{
  private TransformUtils ()
  {
  }
  
  
  
  // -> "http://stackoverflow.com/questions/5508424/convert-2d-affine-transformation-matrix-to-3d-affine-transformation-matrix"
  public static double[] affineTransformToTransform3DMatrix
  (
    AffineTransform affineTransform
  )
  {
    // The AffineTransform matrix is COLUMN major.
    double[] affineMatrix = new double[6];
    // The Transform3D     matrix is ROW major.
    double[] matrix3D     = null;
    double   translationX = 0.0;
    double   translationY = 0.0;
    double   translationZ = 0.0;
    double   scaleX       = 0.0;
    double   scaleY       = 0.0;
    double   scaleZ       = 0.0;
    double   shearX       = 0.0;
    double   shearY       = 0.0;
    
    affineTransform.getMatrix (affineMatrix);
    
    translationX = affineMatrix[4];
    translationY = affineMatrix[5];
    translationZ = 0.0;
    scaleX       = affineMatrix[0];
    scaleY       = affineMatrix[3];
    scaleZ       = 1.0;
    shearX       = affineMatrix[2];
    shearY       = affineMatrix[1];
    matrix3D     = new double[]
    {
      // 1st row.
      scaleX,  shearX,  0.0,     translationX,
      // 2nd row.
      shearY,  scaleY,  0.0,     translationY,
      // 3rd row.
      0.0,     0.0,     scaleZ,  translationZ,
      // 4th row.
      0.0,     0.0,     0.0,     1.0
    };
    
    return matrix3D;
  }
  
  public static Transform3D affineTransformToTransform3D
  (
    AffineTransform affineTransform
  )
  {
    Transform3D transform3D  = null;
    double[]    matrix3D     = null;
    
    matrix3D    = affineTransformToTransform3DMatrix (affineTransform);
    transform3D = new Transform3D                    (matrix3D);
    
    return transform3D;
  }
  
  public static double[] transform3DToAffineTransformMatrix
  (
    Transform3D transform3D
  )
  {
    double[] affineMatrix      = null;
    double[] transform3DMatrix = null;
    double   translationX      = 0.0;
    double   translationY      = 0.0;
    double   scaleX            = 0.0;
    double   scaleY            = 0.0;
    double   shearX            = 0.0;
    double   shearY            = 0.0;
    
    transform3DMatrix = new double[16];
    transform3D.get (transform3DMatrix);
    translationX      = transform3DMatrix[3];
    translationY      = transform3DMatrix[7];
    scaleX            = transform3DMatrix[0];
    scaleY            = transform3DMatrix[5];
    shearX            = transform3DMatrix[1];
    shearY            = transform3DMatrix[4];
    
    affineMatrix = new double[]
    {
      // 1st column.
      scaleX,       shearY,
      // 2nd column.
      shearX,       scaleY,
      // 3rd column.
      translationX, translationY
    };
    
    return affineMatrix;
  }
  
  public static AffineTransform transform3DToAffineTransform
  (
    Transform3D transform3D
  )
  {
    AffineTransform affineTransform   = null;
    double[]        affineMatrix      = null;
    
    affineMatrix    = transform3DToAffineTransformMatrix (transform3D);
    affineTransform = new AffineTransform (affineMatrix);
    
    return affineTransform;
  }
  
  
  
  /**
   * <div class="introMethod">
   *   Creates the composite 3D-transformation for a rotation around
   *   an edge (line).
   * </div>
   * 
   * <div>
   *   The transformation will be a concatenation of following
   *   single transformations:
   *   <ul>
   *     <li>
   *       A translation of the edge's midpoint to the coordinate
   *       system origin.
   *     </li>
   *     <li>
   *       A rotation by the given angle around the origin.
   *     </li>
   *     <li>
   *       A translation of the edge's midpoint to its original
   *       position.
   *     </li>
   *   </ul>
   * </div>
   * 
   * @param endpoint1  The rotation edge's first  end point.
   * @param endpoint2  The rotation edge's second end point.
   * @param angle      The angle to rotate about.
   * 
   * @return           The 3D-transformation for rotating around the
   *                   edge from <code>endpoint1</code> to
   *                   <code>endpoint2</code> by the <code>angle</code>.
   */
  public static Transform3D rotateAroundEdge
  (
    Position endpoint1,         // First  edge end point.
    Position endpoint2,         // Second edge end point.
    Angle    angle              // Angle to rotate about.
  )
  {
    Transform3D        transform = null;
    RotationAroundEdge rotation  = null;
    
    rotation  = new RotationAroundEdge (new RotationEdge (endpoint1, endpoint2, angle));
    transform = rotation.getCompoundTransform3D ();
    
    return transform;
  }
}