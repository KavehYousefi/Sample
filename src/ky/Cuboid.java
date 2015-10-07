package ky;

import ky.Dimensions;
import ky.transform2.Scaling;
import ky.transform2.Translation;
import safercode.CheckingUtils;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Bounds;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.IndexedQuadArray;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


// TODO: Static creation methods to create cuboid from "double" array.
/**
 * <div class="introClass">
 *   This class implements a three-dimensional cuboid.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Cuboid.java
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
 *       07.03.2013
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
 *     <td>07.03.2013</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Cuboid
{
  public  static final int    NUMBER_OF_CORNERS = 8;
  
  // Constants for the method "getPointFromFraction (...)".
  public  static final double X_LEFT         = -0.5;
  public  static final double X_MIDDLE       =  0.0;
  public  static final double X_RIGHT        =  0.5;
  public  static final double Y_TOP          =  0.5;
  public  static final double Y_MIDDLE       =  0.0;
  public  static final double Y_BOTTOM       = -0.5;
  public  static final double Z_FRONT        =  0.5;
  public  static final double Z_MIDDLE       =  0.0;
  public  static final double Z_BACK         = -0.5;
  
  private static final double DEFAULT_WIDTH  =  1.0;
  private static final double DEFAULT_HEIGHT =  1.0;
  private static final double DEFAULT_DEPTH  =  1.0;
  
  private Position position;          // The center.
  private double   width;
  private double   height;
  private double   depth;
  
  
  /**
   * <div class="introConstructor">
   *   A cuboid with given center point and dimensions is created.
   * </div>
   * 
   * @param position  The optional position of the center. It defaults
   *                  to the origin.
   * @param width     The width.
   * @param height    The height.
   * @param depth     The depth.
   */
  public Cuboid
  (
    Position position,
    double   width,
    double   height,
    double   depth
  )
  {
    initCuboid (position, width, height, depth);
  }
  
  // +/ 15-10-2013
  /**
   * <div class="introConstructor">
   *   A cuboid with given center point and dimensions is created.
   * </div>
   * 
   * @param position    The optional position of the center. It defaults
   *                    to the origin.
   * @param dimensions  The optional dimensions. They default to
   *                    zero along each axis.
   */
  public Cuboid (Position center, Dimensions dimensions)
  {
    initCuboid
    (
      new Position         (center),
      dimensions.getWidth  (),
      dimensions.getHeight (),
      dimensions.getDepth  ()
    );
  }
  
  // + 05.12.2013
  /**
   * <div class="introConstructor">
   *   Creates a cuboid from its center coordinates and dimensions.
   * </div>
   * 
   * @param centerX  The center's <i>x</i> coordinate.
   * @param centerY  The center's <i>y</i> coordinate.
   * @param centerZ  The center's <i>z</i> coordinate.
   * @param width    The width.
   * @param height   The height.
   * @param depth    The depth.
   */
  public Cuboid
  (
    double centerX,
    double centerY,
    double centerZ,
    double width,
    double height,
    double depth
  )
  {
    Position center = new Position (centerX, centerY, centerZ);
    
    initCuboid (center, width, height, depth);
  }
  
  //+/ 24.03.2013
  /**
   * <div class="introConstructor">
   *   Creates a cuboid centered at the origin with given dimensions.
   * </div>
   * 
   * <div>
   *   Calling this constructor is equivalent to
   *   <pre>new Cuboid (null, width, height, depth)</pre>
   * </div>
   * 
   * @param width   The width.
   * @param height  The height.
   * @param depth   The depth.
   */
  public Cuboid (double width, double height, double depth)
  {
    initCuboid (null, width, height, depth);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a cube of the given position and side length.
   * </div>
   * 
   * @param position    The optional position for the cube's center.
   *                    It defaults to (0.0, 0.0, 0.0).
   * @param sideLength  The length of one cube side. It may not be
   *                    negative.
   * 
   * @throws IllegalArgumentException  If encountering a negative
   *                                   <code>sideLength</code>.
   */
  public Cuboid (Position position, double sideLength)
  {
    initCuboid (position, sideLength, sideLength, sideLength);
  }
  
  //+/ 24.03.2013
  /**
   * <div class="introConstructor">
   *   Creates a cube of a given side length in the origin.
   * </div>
   * 
   * <div>
   *   This constructor is identical to<br />
   *   <code>public Cuboid (Position position, double sideLength)</code>
   *   <br />
   *   when called as<br />
   *   <code>new Cuboid (null, sideLength)</code>
   * </div>
   * 
   * @param sideLength  The length of one cube side. It may not be
   *                    negative.
   * 
   * @throws IllegalArgumentException  If encountering a negative
   *                                   <code>sideLength</code>.
   */
  public Cuboid (double sideLength)
  {
    initCuboid (null, sideLength, sideLength, sideLength);
  }
  
  // /+/ 21.03.2013
  /**
   * <div class="introConstructor">
   *   Constructs a cuboid from the endpoints of its space diagonal.
   * </div>
   * 
   * @param positionLOV  Der erste Punkt. Er darf nicht
   *                     <code>null</code> sein.
   * @param positionRUH  Der zweite Punkt. Er darf nicht
   *                     <code>null</code> sein.
   * 
   * @throws NullPointerException  Falls <code>positionLOV</code> oder
   *                               <code>positionRUH</code> den Wert
   *                               <code>null</code> besitzt.
   */
  public Cuboid (Position positionLOV, Position positionRUH)
  {
    setFromSpaceDiagonal (positionLOV, positionRUH);
  }
  
  // Copy constructor.
  // 05-12-2013
  /**
   * <div class="introConstructor">
   *   Creates a cuboid by copying an existing one.
   * </div>
   * 
   * @param cuboidToCopy  An optional cuboid to copy. Leaving it to
   *                      <code>null</code> results in a default cuboid
   *                      to be created.
   */
  public Cuboid (Cuboid cuboidToCopy)
  {
    // Cuboid given? => Copy its position and dimensions.
    if (cuboidToCopy != null)
    {
      Position center = cuboidToCopy.getPosition ();
      double   width  = cuboidToCopy.getWidth    ();
      double   height = cuboidToCopy.getHeight   ();
      double   depth  = cuboidToCopy.getDepth    ();
      
      initCuboid (center, width, height, depth);
    }
    // No cuboid to copy given? => Create default object.
    else
    {
      initCuboid (null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_DEPTH);
    }
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a cuboid by defining a reference point, an interpretation
   *   of this and the dimensions.
   * </div>
   * 
   * <div>
   *   The <code>referencePosition</code> defines which position of
   *   the cuboid will lie on the <code>referencePoint</code>. Usually
   *   when creating a cuboid, its <strong>center</strong> point is
   *   placed to the desired coordinates. This constructor allows an
   *   arbitrary cuboid position to be placed there, for example its
   *   left-top-front corner.
   * </div>
   * 
   * @param referencePoint     The point to place the cuboid's
   *                           <code>referencePosition</code> on.
   * @param referencePosition  The cuboid position which interprets the
   *                           <code>referencePoint</code>, defining
   *                           how to place the cuboid relative to it.
   * @param dimensions         The cuboid dimensions.
   */
  public Cuboid
  (
    Position       referencePoint,
    CuboidPosition referencePosition,
    Dimensions     dimensions
  )
  {
    initCuboid
    (
      referencePoint,
      dimensions.getWidth  (),
      dimensions.getHeight (),
      dimensions.getDepth  ()
    );
    
    setPositionToReferencePoint (referencePosition);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a cuboid with all default properties applied.
   * </div>
   */
  public Cuboid ()
  {
    initCuboid (null, DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_DEPTH);
  }
  
  
  /**
   * <div class="introConstructor">
   *   Returns the center position.
   * </div>
   * 
   * @return  The center position.
   */
  public Position getPosition ()
  {
    Position copyOfPosition = new Position (position);
    
    return copyOfPosition;
  }
  
  public double getPositionX ()
  {
    double x = position.getX ();
    
    return x;
  }
  
  public double getPositionY ()
  {
    double y = position.getY ();
    
    return y;
  }
  
  public double getPositionZ ()
  {
    double z = position.getZ ();
    
    return z;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the position from its three coordinates.
   * </div>
   * 
   * @param x  The center's new <i>x</i> coordinate.
   * @param y  The center's new <i>y</i> coordinate.
   * @param z  The center's new <i>z</i> coordinate.
   */
  public void setPosition (double x, double y, double z)
  {
    position = new Position (x, y, z);
  }
  
  public void setPosition (Position position)
  {
    this.position = position;
  }
  
  public void setX (double x)
  {
    position.setX (x);
  }
  
  public void setY (double y)
  {
    position.setY (y);
  }
  
  public void setZ (double z)
  {
    position.setZ (z);
  }
  
  public double getMinimumX ()
  {
    double minX = getPointAtLeftTopFront ().getX ();
    
    return minX;
  }
  
  public double getMaximumX ()
  {
    double maxX = getPointAtRightBottomBack ().getX ();
    
    return maxX;
  }
  
  public double getMinimumY ()
  {
    double minY = getPointAtRightBottomBack ().getY ();
    
    return minY;
  }
  
  public double getMaximumY ()
  {
    double maxY = getPointAtLeftTopFront ().getY ();
    
    return maxY;
  }
  
  public double getMinimumZ ()
  {
    double minZ = getPointAtRightBottomBack ().getZ ();
    
    return minZ;
  }
  
  public double getMaximumZ ()
  {
    double maxZ = getPointAtLeftTopFront ().getZ ();
    
    return maxZ;
  }
  
  
  public void setPositionRelative
  (
    double deltaX,
    double deltaY,
    double deltaZ
  )
  {
    position.setRelative  (deltaX, deltaY, deltaZ);
//    position = position.createPositionRelativeToThis (deltaX, deltaY, deltaZ);
  }
  
  public void setPositionRelative (Vector3d deltas)
  {
    CheckingUtils.checkForNull (deltas, "Vector is null.");
    setPositionRelative (deltas.getX (), deltas.getY (), deltas.getZ ());
  }
  
  public void setPositionToReferencePoint
  (
    CuboidPosition referencePosition
  )
  {
    Vector3d deltas = null;
    
    deltas = getDeltasFromPositionToReferencePoint (referencePosition);
    
    setPositionRelative (deltas.getX (), deltas.getY (), deltas.getZ ());
  }
  
  public Vector3d getDeltasFromPositionToReferencePoint
  (
    CuboidPosition referencePosition
  )
  {
    Vector3d deltaVector   = null;
    Position deltaPosition = getPointAtPosition (referencePosition);
    double   deltaX        = (position.getX () - deltaPosition.getX ());
    double   deltaY        = (position.getY () - deltaPosition.getY ());
    double   deltaZ        = (position.getZ () - deltaPosition.getZ ());
    
    deltaVector = new Vector3d (deltaX, deltaY, deltaZ);
    
    return deltaVector;
  }
  
  public void setPosition
  (
    Position       referencePoint,
    CuboidPosition referencePosition
  )
  {
    // This line commented out it is problematic when using the same
    // Position object with multiple Cuboids.
//    setPosition                 (referencePoint);
    setPosition                 (new Position (referencePoint));
    setPositionToReferencePoint (referencePosition);
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the dimensions.
   * </div>
   * 
   * @return  The dimensions.
   */
  public Dimensions getDimensions ()
  {
    Dimensions dimensionen = new Dimensions (width, height, depth);
    
    return dimensionen;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the cuboid dimensions from width, height and depth.
   * </div>
   * 
   * @param width   The new width.
   * @param height  The new height.
   * @param depth   The new depth.
   */
  public void setDimensions (double width, double height, double depth)
  {
    this.width  = width;
    this.height = height;
    this.depth  = depth;
  }
  
  public void setDimensions (Dimensions dimensions)
  {
    CheckingUtils.checkForNull (dimensions, "Dimensions are null.");
    setDimensions              (dimensions.getWidth  (),
                                dimensions.getHeight (),
                                dimensions.getDepth  ());
  }
  
  public double getWidth ()
  {
    return width;
  }
  
  public void setWidth (double width)
  {
    this.width = width;
  }
  
  public double getHeight ()
  {
    return height;
  }
  
  public void setHeight (double height)
  {
    this.height = height;
  }
  
  public double getDepth ()
  {
    return depth;
  }
  
  public void setDepth (double depth)
  {
    this.depth = depth;
  }
  
  public double getMinimumBetweenWidthAndHeight ()
  {
    return Math.min (width, height);
  }
  
  public double getMinimumBetweenWidthAndDepth ()
  {
    return Math.min (width, depth);
  }
  
  public double getMinimumBetweenHeightAndDepth ()
  {
    return Math.min (height, depth);
  }
  
  
  // 03-08-2014
  /**
   * <div class="introMethod">
   *   Sets the position and dimensions of this cuboid to the one
   *   given by the endpoints of its space diagonal.
   * </div>
   * 
   * @param leftFrontTopPosition     The space diagonal's first
   *                                 endpoint. It may not be
   *                                 <code>null</code>
   * @param rightBackBottomPosition  The space diagonal's second
   *                                 endpoint. It may not be
   *                                 <code>null</code>
   * 
   * @throw NullPointerException  If one of the argments equals
   *                              <code>null</code>.
   */
  public void setFromSpaceDiagonal
  (
    Position leftFrontTopPosition,
    Position rightBackBottomPosition
  )
  {
    Cuboid   referenceCuboid = null;
    Position newPosition     = null;
    double   newWidth        = 0.0;
    double   newHeight       = 0.0;
    double   newDepth        = 0.0;
    
    referenceCuboid = createFromSpaceDiagonal
    (
      leftFrontTopPosition,
      rightBackBottomPosition
    );
    newPosition     = referenceCuboid.getPosition ();
    newWidth        = referenceCuboid.getWidth    ();
    newHeight       = referenceCuboid.getHeight   ();
    newDepth        = referenceCuboid.getDepth    ();
    
    initCuboid (newPosition, newWidth, newHeight, newDepth);
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the four points defining a certain face of this cuboid.
   * </div>
   * 
   * @param face  The identifier of the wanted face.
   * 
   * @return      The four points defining the <code>face</code>.
   *              It may not be <code>null</code>.
   * 
   * @throws NullPointerException      If the given <code>face</code>
   *                                   is <code>null</code>.
   * @throws IllegalArgumentException  If the given <code>face</code>
   *                                   is invalid.
   */
  public List<Position> getFaceCornerPoints (CuboidFace face)
  {
    CheckingUtils.checkForNull (face, "Cuboid face is null.");
    
    List<Position> faceCornerPoints = null;
//    int[]          pointIndices     = null;
//    
//    pointIndices     = face.getPointIndices    ();
//    faceCornerPoints = new ArrayList<Position> ();
//    
//    faceCornerPoints.add (punkte[pointIndices[0]]);
//    faceCornerPoints.add (punkte[pointIndices[1]]);
//    faceCornerPoints.add (punkte[pointIndices[2]]);
//    faceCornerPoints.add (punkte[pointIndices[2]]);
    
    return faceCornerPoints;
  }
  
  
  // + 13/10/2013
  /**
   * <div class="introMethod">
   *   Computes a point from the fractions of the dimensions relative
   *   to the cuboid center.
   * </div>
   * 
   * <div>
   *   The parameters can be categorized as follows:
   *   <table>
   *     <tr>
   *       <th>Parameter</th>
   *       <th>Dimension</th>
   *       <th>Negative value</th>
   *       <th>Zero</th>
   *       <th>Positive value</th>
   *     </tr>
   *     <tr>
   *       <td><code>xFraction</code></td>
   *       <td>width</td>
   *       <td>to the left</td>
   *       <td>center</td>
   *       <td>to the right</td>
   *     </tr>
   *     <tr>
   *       <td><code>yFraction</code></td>
   *       <td>height</td>
   *       <td>to the bottom</td>
   *       <td>center</td>
   *       <td>to the top</td>
   *     </tr>
   *     <tr>
   *       <td><code>zFraction</code></td>
   *       <td>depth</td>
   *       <td>to the back</td>
   *       <td>center</td>
   *       <td>to the front</td>
   *     </tr>
   *   </table>
   * </div>
   * 
   * <div>
   *   To gain better readable code, following class constants have been
   *   defined, which may be used as parameters:
   *   <ul>
   *     <li><code>X_LEFT</code></li>
   *     <li><code>X_MIDDLE</code></li>
   *     <li><code>X_RIGHT</code></li>
   *     <li><code>Y_BOTTOM</code></li>
   *     <li><code>Y_MIDDLE</code></li>
   *     <li><code>Y_TOP</code></li>
   *     <li><code>Z_BACK</code></li>
   *     <li><code>Z_MIDDLE</code></li>
   *     <li><code>Z_FRONT</code></li>
   *   </ul>
   * </div>
   * 
   * @param xFraction  The fraction of the width, yielding the
   *                   <i>x</i>-coordinate.
   * @param yFraction  The fraction of the height, yielding the
   *                   <i>y</i>-coordinate.
   * @param zFraction  The fraction of the depth, yielding the
   *                   <i>z</i>-coordinate.
   * 
   * @return           The position calculated from the fractions
   *                   of the coordinates relative to the center.
   */
  public Position getPointAtFraction
  (
    double xFraction,
    double yFraction,
    double zFraction
  )
  {
    Position pointAtFraction  = new Position ();
    double   fractionOfWidth  = (xFraction * width);
    double   fractionOfHeight = (yFraction * height);
    double   fractionOfDepth  = (zFraction * depth);
    
    pointAtFraction = position.getAddedPosition
    (
      fractionOfWidth,
      fractionOfHeight,
      fractionOfDepth
    );
    
    return pointAtFraction;
  }
  
  public Position getPointAtLeftTopFront ()
  {
    Position punktLTF = null;
    
    punktLTF = getPointAtPosition (CuboidPosition.LEFT_TOP_FRONT);
    
    return punktLTF;
  }
  
  public Position getPointAtRightBottomBack ()
  {
    Position punktRBB = null;
    
    punktRBB = getPointAtPosition (CuboidPosition.RIGHT_BOTTOM_BACK);
    
    return punktRBB;
  }
  
  
  
  /**
   * <div class="introMethod">
   *   Returns the Euclidean distance between two positions on the
   *   cuboid.
   * </div>
   * 
   * @param corner1  The first position. It may not be
   *                 <code>null</code>.
   * @param corner2  The second position. It may not be
   *                 <code>null</code>.
   * 
   * @return  The Euclidean distance between <code>corner1</code> and
   *          <code>corner2</code>.
   */
  public double getCornerDistance
  (
    CuboidPosition corner1,
    CuboidPosition corner2
  )
  {
    double   distance = 0.0;
    Position point1   = getPointAtPosition (corner1);
    Position point2   = getPointAtPosition (corner2);
    
    distance = point1.getEuclideanDistance3DFrom (point2);
    
    return distance;
  }
  
  // Abstand zwischen Punkten LOV - RUH.
  // -> Space diagonal: "http://en.wikipedia.org/wiki/Space_diagonal"
  // -> Formula       : "http://en.wikipedia.org/wiki/Cuboid"
  /**
   * <div class="introMethod">
   *   Returns the length of the space diagonal.
   * </div>
   * 
   * @return  The length of the space diagonal.
   */
  public double getSpaceDiagonalLength ()
  {
    double length = Math.sqrt ((width * width) + (height * height) +
                               (depth * depth));
    
    return length;
  }
  
  // -> "http://en.wikipedia.org/wiki/Face_diagonal"
  /**
   * <div class="introMethod">
   *   Returns the length of the face diagonal on the
   *   <i>x</i>-<i>y</i> plane (between "width" and "height" or
   *   in other words: at the "front" and "back").
   * </div>
   * 
   * @return  The <i>x</i>-<i>y</i> plane face diagonal's length.
   */
  public double getFaceDiagonalXYLength ()
  {
    return (Math.hypot (width, height));
  }
  
  // -> "http://en.wikipedia.org/wiki/Face_diagonal"
  /**
   * <div class="introMethod">
   *   Returns the length of the face diagonal on the
   *   <i>x</i>-<i>z</i> plane (between "width" and "depth" or
   *   in other words: at the "top" and "bottom").
   * </div>
   * 
   * @return  The <i>x</i>-<i>z</i> plane face diagonal's length.
   */
  public double getFaceDiagonalXZLength ()
  {
    return (Math.hypot (width, depth));
  }
  
  // -> "http://en.wikipedia.org/wiki/Face_diagonal"
  /**
   * <div class="introMethod">
   *   Returns the length of the face diagonal on the
   *   <i>y</i>-<i>z</i> plane (between "height" and "depth" or
   *   in other words: at the "left side" and "right side").
   * </div>
   * 
   * @return  The <i>y</i>-<i>z</i> plane face diagonal's length.
   */
  public double getFaceDiagonalYZLength ()
  {
    return (Math.hypot (height, depth));
  }
  
  
  public double getShortestSide ()
  {
    double shortestSide = 0.0;
    
    shortestSide = Math.min (width,        height);
    shortestSide = Math.min (shortestSide, depth);
    
    return shortestSide;
  }
  
  public boolean isCube ()
  {
    boolean isCube = false;
    
    isCube = ((width == height) && (height == depth));
    
    return isCube;
  }
  
  
  
  
  // 25.04.2014
  public Vector3d getPositionAsVector3d ()
  {
    Vector3d vector3d = position.getAsVector3d ();
    
    return vector3d;
  }
  
  // 25.04.2014
  public Translation createTranslationFromPosition ()
  {
    Translation translation = new Translation ();
    
    translation.setFromPosition (position);
    
    return translation;
  }
  
  // 28.04.2014
  public Scaling createScalingFromDimensions ()
  {
    Scaling scaling = new Scaling (width, height, depth);
    
    return scaling;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static methods.                          -- //
  //////////////////////////////////////////////////////////////////////
  
  public static Cuboid createFromPositionAndDimensions
  (
    Position   center,
    Dimensions dimensions
  )
  {
    Cuboid cuboid = new Cuboid (center, dimensions);
    
    return cuboid;
  }
  
  public static Cuboid createFromDimensionsAroundOrigin
  (
    Dimensions dimensions
  )
  {
    Cuboid cuboid = new Cuboid (new Position (), dimensions);
    
    return cuboid;
  }
  
  public static Cuboid createFromWidthHeightDepthAroundOrigin
  (
    double width,
    double height,
    double depth
  )
  {
    return new Cuboid (width, height, depth);
  }
  
  /**
   * <div class="introMethod">
   *   Creates a cuboid from two endpoints defining its space diagonal.
   * </div>
   * 
   * @param firstEndpoint   The first endpoint of the space diagonal.
   *                        The <code>null</code> value is prohibited.
   * @param secondEndpoint  The second endpoint of the space diagonal.
   *                        The <code>null</code> value is prohibited.
   * 
   * @return                A new cuboid created from the given
   *                        space diagonal's endpoints.
   * 
   * @throws NullPointerException  If one of the endpoints equals
   *                               <code>null</code>.
   */
  public static Cuboid createFromSpaceDiagonal
  (
    Position firstEndpoint,
    Position secondEndpoint
  )
  {
    Cuboid     cuboid     = null;
    Position   center     = null;
    double     centerX    = 0.0;
    double     centerY    = 0.0;
    double     centerZ    = 0.0;
    Dimensions dimensions = null;
    double     width      = 0.0;
    double     height     = 0.0;
    double     depth      = 0.0;
    // Coordinates of the "firstEndpoint".
    double     firstX     = firstEndpoint.getX ();
    double     firstY     = firstEndpoint.getY ();
    double     firstZ     = firstEndpoint.getZ ();
    // Coordinates of the "secondEndpoint".
    double     secondX    = secondEndpoint.getX ();
    double     secondY    = secondEndpoint.getY ();
    double     secondZ    = secondEndpoint.getZ ();
    
    // Avoid negative width, if second endpoint is left of first one.
    width      = Math.abs (secondX - firstX);
    // Avoid negative height, if second endpoint is below first one.
    height     = Math.abs (secondY - firstY);
    // Avoid negative depth, if second endpoint is behind first one.
    depth      = Math.abs (secondZ - firstZ);
    
    centerX    = (Math.min (firstX, secondX) + (width  / 2.0));
    centerY    = (Math.min (firstY, secondY) + (height / 2.0));
    centerZ    = (Math.min (firstZ, secondZ) + (depth  / 2.0));
    
    center     = new Position   (centerX, centerY, centerZ);
    dimensions = new Dimensions (width, height, depth);
    cuboid     = new Cuboid     (center, dimensions);
    
    return cuboid;
  }
  
  /**
   * <div class="introMethod">
   *   Creates a cuboid by defining a reference point, an interpretation
   *   of this and the dimensions.
   * </div>
   * 
   * <div>
   *   The <code>referencePosition</code> defines which position of
   *   the cuboid will lie on the <code>referencePoint</code>. Usually
   *   when creating a cuboid, its <strong>center</strong> point is
   *   placed to the desired coordinates. This constructor allows an
   *   arbitrary cuboid position to be placed there, for example its
   *   left-top-front corner.
   * </div>
   * 
   * @param referencePoint     The point to place the cuboid's
   *                           <code>referencePosition</code> on.
   *                           It must not be <code>null</code>.
   * @param referencePosition  The cuboid position which interprets the
   *                           <code>referencePoint</code>, defining
   *                           how to place the cuboid relative to it.
   *                           It must not be <code>null</code>.
   * @param dimensions         The cuboid dimensions.
   *                           It must not be <code>null</code>.
   * 
   * @return                   A new cuboid created from the given
   *                           position, alignment and dimensions.
   * 
   * @throws NullPointerException  If the <code>referencePoint</code>
   *                               equals <code>null</code>.
   * @throws NullPointerException  If the <code>referencePosition</code>
   *                               equals <code>null</code>.
   * @throws NullPointerException  If the <code>dimensions</code>
   *                               equal <code>null</code>.
   */
  public static Cuboid createFromReferencePositionAndDimensions
  (
    Position       referencePoint,
    CuboidPosition referencePosition,
    Dimensions     dimensions
  )
  {
    Cuboid cuboid = null;
    
    cuboid = new Cuboid (referencePoint, referencePosition, dimensions);
    
    return cuboid;
  }
  
  public static Cuboid createCubeAtPosition (Position position, double sideLength)
  {
    return Cuboid.createFromPositionAndDimensions (position, Dimensions.createFromUniformSize (sideLength));
  }
  
  public static Cuboid createCubeAtOrigin (double sideLength)
  {
    return Cuboid.createFromPositionAndDimensions (new Position (), Dimensions.createFromUniformSize (sideLength));
  }
  
  /**
   * <div class="introMethod">
   *   Creates a cuboid at the origin, and with width, height and
   *   depth set to one.
   * </div>
   * 
   * @return  A new cuboid as unit cube centered at the coordinate
   *          system origin.
   */
  public static Cuboid createUnitCubeAtOrigin ()
  {
    return Cuboid.createFromWidthHeightDepthAroundOrigin (1.0, 1.0, 1.0);
  }
  
  /**
   * <div class="introMethod">
   *   Creates a cuboid at the origin, and with width, height and
   *   depth set to zero.
   * </div>
   * 
   * @return  A new cuboid as zero side length cube centered at the
   *          coordinate system origin.
   */
  public static Cuboid createZeroCubeAtOrigin ()
  {
    return Cuboid.createFromWidthHeightDepthAroundOrigin (0.0, 0.0, 0.0);
  }
  
  public static Cuboid createFromBoundingBox (BoundingBox boundingBox)
  {
    Cuboid   cuboid        = null;
    Point3d  upperPoint    = null;
    Point3d  lowerPoint    = null;
    Position lowerPosition = null;
    Position upperPosition = null;
    
    if (boundingBox != null)
    {
      upperPoint = new Point3d ();
      lowerPoint = new Point3d ();
      boundingBox.getUpper     (upperPoint);
      boundingBox.getLower     (lowerPoint);
      
      lowerPosition = new Position (lowerPoint);
      upperPosition = new Position (upperPoint);
      cuboid        = new Cuboid   (lowerPosition, upperPosition);
    }
    else
    {
      cuboid = null;
    }
    
    return cuboid;
  }
  
  public static Cuboid createFromBounds (Bounds bounds)
  {
    CheckingUtils.checkForNull (bounds, "Bounds is null.");
    
    Cuboid      cuboid        = null;
    Point3d     upperPoint    = null;
    Point3d     lowerPoint    = null;
    Position    lowerPosition = null;
    Position    upperPosition = null;
    BoundingBox boundingBox   = null;
    
    boundingBox = new BoundingBox (bounds);
    upperPoint  = new Point3d     ();
    lowerPoint  = new Point3d     ();
    boundingBox.getUpper          (upperPoint);
    boundingBox.getLower          (lowerPoint);
    
    lowerPosition = new Position (lowerPoint);
    upperPosition = new Position (upperPoint);
    cuboid        = new Cuboid   (lowerPosition, upperPosition);
    
    return cuboid;
  }
  
  
  public Scaling getScalingToMatch (Cuboid otherCuboid)
  {
    CheckingUtils.checkForNull (otherCuboid, "Other cuboid is null.");
    
    Scaling    scaling               = null;
    Dimensions myDimensions          = null;
    Dimensions destinationDimensions = null;
    
    myDimensions          = this.getDimensions             ();
    destinationDimensions = otherCuboid.getDimensions      ();
    scaling               = myDimensions.getScalingToMatch (destinationDimensions);
    
    return scaling;
  }
  
  
  
  
  public Box createBox (int primflags, Appearance appearance)
  {
    Box box = null;
    
    box = new Box
    (
      new Double (width  / 2.0).floatValue (),
      new Double (height / 2.0).floatValue (),
      new Double (depth  / 2.0).floatValue (),
      primflags,
      appearance
    );
    
    return box;
  }
  
  // ++ 04.09.2014
  /// Generates a Box inside of a TransformGroup with given position (translation) and dimensions.
  public TransformGroup createTransformGroupWithBox
  (
    int        primflags,
    Appearance appearance
  )
  {
    TransformGroup transformGroup = null;
    Transform3D    transform3D    = null;
    Translation    translation    = null;
    Box            box            = null;
    
    transformGroup = new TransformGroup            ();
    translation    = createTranslationFromPosition ();
    transform3D    = translation.getAsTransform3D  ();
    box            = createBox (primflags, appearance);
    
    transformGroup.setTransform (transform3D);
    transformGroup.addChild     (box);
    
    return transformGroup;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "Solid".                    -- //
  //////////////////////////////////////////////////////////////////////
  
  public GeometryArray createGeometry ()
  {
    IndexedQuadArray geometry              = null;
    int              vertexFormat          = 0;
    int              indicesCount          = 0;
    Position         pointLeftTopFront     = null;
    Position         pointLeftBottomFront  = null;
    Position         pointRightBottomFront = null;
    Position         pointRightTopFront    = null;
    Position         pointLeftTopBack      = null;
    Position         pointLeftBottomBack   = null;
    Position         pointRightBottomBack  = null;
    Position         pointRightTopBack     = null;
    GeometryInfo     geometryInfo          = null;
    NormalGenerator  normalGenerator       = null;
    
    vertexFormat = IndexedQuadArray.COORDINATES |
                   IndexedQuadArray.NORMALS;
    // Each cuboid corner is connected to three others: 8 * 3 = 24.
    indicesCount = (NUMBER_OF_CORNERS * 3);
    geometry = new IndexedQuadArray
    (
      NUMBER_OF_CORNERS,
      vertexFormat,
      indicesCount
    );
    
    pointLeftTopFront     = getPointAtPosition (CuboidPosition.LEFT_TOP_FRONT);
    pointLeftBottomFront  = getPointAtPosition (CuboidPosition.LEFT_BOTTOM_FRONT);
    pointRightBottomFront = getPointAtPosition (CuboidPosition.RIGHT_BOTTOM_FRONT);
    pointRightTopFront    = getPointAtPosition (CuboidPosition.RIGHT_TOP_FRONT);
    pointLeftTopBack      = getPointAtPosition (CuboidPosition.LEFT_TOP_BACK);
    pointLeftBottomBack   = getPointAtPosition (CuboidPosition.LEFT_BOTTOM_BACK);
    pointRightBottomBack  = getPointAtPosition (CuboidPosition.RIGHT_BOTTOM_BACK);
    pointRightTopBack     = getPointAtPosition (CuboidPosition.RIGHT_TOP_BACK);
    
    // Set the eight cuboid points.
    geometry.setCoordinate (0, pointLeftTopFront.getAsPoint3d     ());
    geometry.setCoordinate (1, pointLeftBottomFront.getAsPoint3d  ());
    geometry.setCoordinate (2, pointRightBottomFront.getAsPoint3d ());
    geometry.setCoordinate (3, pointRightTopFront.getAsPoint3d    ());
    geometry.setCoordinate (4, pointLeftTopBack.getAsPoint3d      ());
    geometry.setCoordinate (5, pointLeftBottomBack.getAsPoint3d   ());
    geometry.setCoordinate (6, pointRightBottomBack.getAsPoint3d  ());
    geometry.setCoordinate (7, pointRightTopBack.getAsPoint3d     ());
    
    // Create front side.
    geometry.setCoordinateIndex ( 0, 0);
    geometry.setCoordinateIndex ( 1, 1);
    geometry.setCoordinateIndex ( 2, 2);
    geometry.setCoordinateIndex ( 3, 3);
    
    // Create back side.
    geometry.setCoordinateIndex ( 4, 4);
    geometry.setCoordinateIndex ( 5, 7);
    geometry.setCoordinateIndex ( 6, 6);
    geometry.setCoordinateIndex ( 7, 5);
    
    // Create top side.
    geometry.setCoordinateIndex ( 8, 4);
    geometry.setCoordinateIndex ( 9, 0);
    geometry.setCoordinateIndex (10, 3);
    geometry.setCoordinateIndex (11, 7);
    
    // Create bottom side.
    geometry.setCoordinateIndex (12, 6);
    geometry.setCoordinateIndex (13, 2);
    geometry.setCoordinateIndex (14, 1);
    geometry.setCoordinateIndex (15, 5);
    
    // Create left side.
    geometry.setCoordinateIndex (16, 4);
    geometry.setCoordinateIndex (17, 5);
    geometry.setCoordinateIndex (18, 1);
    geometry.setCoordinateIndex (19, 0);
    
    // Create right side.
    geometry.setCoordinateIndex (20, 3);
    geometry.setCoordinateIndex (21, 2);
    geometry.setCoordinateIndex (22, 6);
    geometry.setCoordinateIndex (23, 7);
    
    geometryInfo = new GeometryInfo (geometry);
    
    normalGenerator = new NormalGenerator ();
    normalGenerator.generateNormals       (geometryInfo);
    
    return geometryInfo.getGeometryArray ();
  }
  
  
  public Map<CuboidPosition, Position> getPointMap ()
  {
    Map<CuboidPosition, Position> pointMap = null;
    
    pointMap = new EnumMap<CuboidPosition, Position> (CuboidPosition.class);
    
    for (CuboidPosition cuboidPosition : CuboidPosition.values ())
    {
      Position point = getPointAtPosition (cuboidPosition);
      
      pointMap.put (cuboidPosition, point);
    }
    
    return pointMap;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the coordinates of a position on this cuboid.
   * </div>
   * 
   * @param cuboidPosition  The cuboid position identifier.
   * 
   * @return  The coordinates of the given <code>cuboidPosition</code>.
   */
  public Position getPointAtPosition (CuboidPosition cuboidPosition)
  {
    Position pointAtPosition = null;
    
    pointAtPosition = cuboidPosition.getPositionAtCuboid (this);
    
    return pointAtPosition;
  }
  
  
  /**
   * <div class="introMethod">
   *   "Merges" this cuboid with a second one, returning a new cuboid
   *   around both.
   * </div>
   * 
   * @param otherCuboid  The cuboid to merge with.
   *                     The <code>null</code> value is prohibited.
   * 
   * @return             A new cuboid created by merging with one with
   *                     a second cuboid.
   * 
   * @throws NullPointerException  If the other cuboid equals
   *                               <code>null</code>.
   */
  public Cuboid createMergedCuboid (Cuboid otherCuboid)
  {
    CheckingUtils.checkForNull (otherCuboid, "Other cuboid is null.");
    
    Cuboid mergedCuboid = null;
    double minimumX     = 0.0;
    double maximumX     = 0.0;
    double minimumY     = 0.0;
    double maximumY     = 0.0;
    double minimumZ     = 0.0;
    double maximumZ     = 0.0;
    
    minimumX = Math.min (getPositionX (), otherCuboid.getPositionX ());
    minimumY = Math.min (getPositionY (), otherCuboid.getPositionY ());
    minimumZ = Math.min (getPositionZ (), otherCuboid.getPositionZ ());
    maximumX = Math.max (getPositionX (), otherCuboid.getPositionX ());
    maximumY = Math.max (getPositionY (), otherCuboid.getPositionY ());
    maximumZ = Math.max (getPositionZ (), otherCuboid.getPositionZ ());
    
    mergedCuboid = Cuboid.createFromSpaceDiagonal
    (
      new Position (minimumX, minimumY, minimumZ),
      new Position (maximumX, maximumY, maximumZ)
    );
    
    return mergedCuboid;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = String.format
    (
      "Cuboid(centerX=%s, centerY=%s, centerZ=%s, " +
             "width=%s, height=%s, depth=%s)",
      getPositionX (),
      getPositionY (),
      getPositionZ (),
      getWidth     (),
      getHeight    (),
      getDepth     ()
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initCuboid
  (
    Position position,
    double   width,
    double   height,
    double   depth
  )
  {
    if (position != null)
    {
      this.position = new Position (position);
    }
    else
    {
      this.position = new Position ();
    }
    
    this.width  = width;
    this.height = height;
    this.depth  = depth;
  }
}