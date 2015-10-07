package ky.transform2;

import ky.Position;
import safercode.CheckingUtils;

import java.awt.geom.AffineTransform;

import javax.media.j3d.Transform3D;
import javax.vecmath.Matrix4d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;


/**
 * <div class="introClass">
 *   Implements a translation transformation in two or three dimensions.
 * </div>
 * 
 * <div>
 *   Following sources have been useful:
 *   <ul>
 *     <li>
 *       <a href="http://www.developer.com/java/other/article.php/3717101/Understanding-Transforms-in-Java-3D.htm">
 *         http://www.developer.com/java/other/article.php/3717101/Understanding-Transforms-in-Java-3D.htm
 *       </a><br />
 *       Topic: Transformations in Java 3D.<br />
 *       Accessed: 03.03.2014
 *     </li>
 *     <li>
 *       <a href="http://cs.fit.edu/~wds/classes/cse5255/thesis/translate/translate.html">
 *         http://cs.fit.edu/~wds/classes/cse5255/thesis/translate/translate.html
 *       </a><br />
 *       Topic: Translation in 3D. Note that the translation matrix
 *              used by the source is not suitable for Java 3D.<br />
 *       Accessed: 03.03.2014
 *     </li>
 *     <li>
 *       <a href="http://stackoverflow.com/questions/9239019/how-to-find-the-position-of-objects-in-java-3d">
 *         "http://stackoverflow.com/questions/9239019/how-to-find-the-position-of-objects-in-java-3d"
 *       </a><br />
 *       Topic: Extracting the position from a <code>Transform3D</code>.
 *              <br />
 *              Note that the code describes how to obtain the
 *              translation as a <code>Point3d</code>.<br />
 *       Accessed: 10.03.2013
 *     </li>
 *   </ul>
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Translation.java
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Author:
 *     </td>
 *     <td>
 *       Kaveh Yousefi
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Date:
 *     </td>
 *     <td>
 *       03.03.2014
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Version:
 *     </td>
 *     <td>
 *       1.0
 *     </td>
 *   </tr>
 * </table>
 * 
 * <div class="classHistoryTitle">History:</div>
 * <table class="classHistoryTable">
 *   <tr>
 *     <th>Date</th>
 *     <th>Version</th>
 *     <th>Changes</th>
 *   </tr>
 *   <tr>
 *     <td>03.03.2014</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Translation
implements   TransformObject
{
  private Vector3d translations;
  
  
  public Translation
  (
    double translatX,
    double translatY,
    double translatZ
  )
  {
    translations = new Vector3d (translatX, translatY, translatZ);
  }
  
  public Translation (Position position)
  {
    if (position != null)
    {
      translations = position.getAsVector3d ();
    }
    else
    {
      translations = new Vector3d ();
    }
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a three-dimensional translation along each of the axes
   *   by the given distance.
   * </div>
   * 
   * @param translationXYZ  The translation along the <i>x</i>-,
   *                        <i>y</i>- and <i>z</i>-axis.
   */
  public Translation
  (
    double translationXYZ
  )
  {
    translations = new Vector3d
    (
      translationXYZ,
      translationXYZ,
      translationXYZ
    );
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a two-dimensional translation along the <i>x</i> and
   *   <i>y</i> axis.
   * </div>
   * 
   * @param translationX  The translation along the <i>x</i> axis.
   * @param translationY  The translation along the <i>y</i> axis.
   */
  public Translation
  (
    double translationX,
    double translationY
  )
  {
    translations = new Vector3d (translationX, translationY, 0.0);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a translation of zero along each axis.
   * </div>
   */
  public Translation ()
  {
    translations = new Vector3d (0.0, 0.0, 0.0);
  }
  
  
  public Position getAsPosition ()
  {
    return new Position (translations);
  }
  
  public Vector3d getAsVector3d ()
  {
    Vector3d vector3d = new Vector3d (translations);
    
    return vector3d;
  }
  
  public void setFromTuple3d (Tuple3d tuple3d)
  {
    this.translations.set (tuple3d);
  }
  
  public void setFromPosition (Position position)
  {
    this.translations.set (position.getAsVector3d ());
  }
  
  
  public double getTranslationX ()
  {
    return translations.getX ();
  }
  
  public void setTranslationX (double translationX)
  {
    translations.setX (translationX);
  }
  
  
  public double getTranslationY ()
  {
    return translations.getY ();
  }
  
  public void setTranslationY (double translationY)
  {
    translations.setY (translationY);
  }
  
  
  public double getTranslationZ ()
  {
    return translations.getZ ();
  }
  
  public void setTranslationZ (double translationZ)
  {
    translations.setZ (translationZ);
  }
  
  
  public void setTranslationXYZ
  (
    double translatX,
    double translatY,
    double translatZ
  )
  {
    translations.setX (translatX);
    translations.setY (translatY);
    translations.setZ (translatZ);
  }
  
  public void setTranslationXY (double translatX, double translatY)
  {
    setTranslationXYZ (translatX, translatY, translations.getZ ());
  }
  
  public void setTranslations (double translationXYZ)
  {
    setTranslationXYZ (translationXYZ, translationXYZ, translationXYZ);
  }
  
  public void setFromMatrix4d (Matrix4d matrix4d)
  {
    CheckingUtils.checkForNull (matrix4d, "Matrix4d is null.");
    
    setTranslationX (matrix4d.getElement (0, 3));
    setTranslationY (matrix4d.getElement (1, 3));
    setTranslationZ (matrix4d.getElement (2, 3));
  }
  
  public void setFromTransform3D (Transform3D transform3D)
  {
    CheckingUtils.checkForNull (transform3D, "Transform3D is null.");
    
    Matrix4d matrix4d = null;
    
    matrix4d = new Matrix4d ();
    transform3D.get         (matrix4d);
    setFromMatrix4d         (matrix4d);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  /**
   * <div class="introMethod">
   *   Creates a translation from the given 3D coordinates.
   * </div>
   * 
   * @param x  The translation along the <i>x</i>-axis.
   * @param y  The translation along the <i>y</i>-axis.
   * @param z  The translation along the <i>z</i>-axis.
   * 
   * @return   A new <code>Translation</code> object representing the
   *           translation by the given coordinates.
   */
  public static Translation createFromXYZ (double x, double y, double z)
  {
    Translation translation = null;
    
    translation = new Translation (x, y, z);
    
    return translation;
  }
  
  public static Translation createFromPosition (Position position)
  {
    Translation translation = null;
    
    translation = new Translation (position);
    
    return translation;
  }
  
  public static Translation createFromTuple3d (Tuple3d tuple3d)
  {
    Translation translation = null;
    
    translation = new Translation ();
    translation.setFromTuple3d    (tuple3d);
    
    return translation;
  }
  
  public static Translation createFromMatrix4d (Matrix4d matrix4d)
  {
    Translation translation = null;
    
    translation = new Translation ();
    translation.setFromMatrix4d   (matrix4d);
    
    return translation;
  }
  
  /**
   * <div class="introMethod">
   *   Extracts the translation component from a
   *   <code>Transform3D</code>.
   * </div>
   * 
   * @param transform3D  The non-<code>null</code> transformation.
   * 
   * @return             A new <code>Translation</code> based on the
   *                     <code>transform3D</code> object.
   * 
   * @throws NullPointerException  If <code>transform3D</code>
   *                               equals <code>null</code>.
   */
  public static Translation createFromTransform3D (Transform3D transform3D)
  {
    Translation translation = null;
    
    translation = new Translation  ();
    translation.setFromTransform3D (transform3D);
    
    return translation;
  }
  
  public static Translation createFromAffineTransform (AffineTransform affineTransform)
  {
    CheckingUtils.checkForNull (affineTransform, "AffineTransform is null.");
    
    Translation translation = null;
    
    translation = new Translation ();
    translation.setTranslationX   (affineTransform.getTranslateX ());
    translation.setTranslationX   (affineTransform.getTranslateY ());
    
    return translation;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "TransformObject".          -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public double[] getAsTransformMatrix3D ()
  {
    double[] matrixArray  = null;
    double   translationX = translations.getX ();
    double   translationY = translations.getY ();
    double   translationZ = translations.getZ ();
    
    matrixArray = new double[]
    {
      1.00000000000,  0.00000000000,  0.00000000000,  translationX,
      0.00000000000,  1.00000000000,  0.00000000000,  translationY,
      0.00000000000,  0.00000000000,  1.00000000000,  translationZ,
      0.00000000000,  0.00000000000,  0.00000000000,  1.00000000000
    };
    
    return matrixArray;
  }
  
  @Override
  public Transform3D getAsTransform3D ()
  {
    Transform3D transform3D = null;
    double[]    matrix      = getAsTransformMatrix3D ();
    
    transform3D = new Transform3D (matrix);
    
    return transform3D;
  }
  
  @Override
  public double[] getAsTransformMatrix2D ()
  {
    double[] matrixArray  = null;
    double   translationX = translations.getX ();
    double   translationY = translations.getY ();
    
    matrixArray = new double[]
    {
      // First column  (m00, m10).
      1.00000000000,
      0.00000000000,
      // Second column (m01, m11).
      0.00000000000,
      1.00000000000,
      // Third column  (m02, m12).
      translationX,
      translationY
    };
    
    return matrixArray;
  }
  
  @Override
  public AffineTransform getAsAffineTransform ()
  {
    AffineTransform affineTransform = null;
    double[]        flatMatrix      = null;
    
    flatMatrix      = getAsTransformMatrix2D ();
    affineTransform = new AffineTransform (flatMatrix);
    
    return affineTransform;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = String.format
    (
      "Translation(x=%s; y=%s; z=%s)",
      translations.getX (),
      translations.getY (),
      translations.getZ ()
    );
    
    return asString;
  }
}
