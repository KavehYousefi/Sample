package ky;

import safercode.CheckingUtils;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.media.j3d.Transform3D;
import javax.vecmath.Point2d;
import javax.vecmath.Point3d;
import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;

import ky.angle.Angle;
import ky.angle.AngleUnit;


/**
 * <div class="introClass">
 *   A position is a 2D or 3D cartesian point defined by double
 *   precision floating point numbers.
 * </div>
 * 
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Position.java
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
 *       17.03.2013
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
 *     <td>17.03.2013</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Position
{
  public static final int    COORD_COUNT  = 3;
  /**
   * <div class="introConstant">
   *   Index of the <i>x</i>-coordinate.
   * </div>
   */
  public static final int    X_COORDINATE = 0;
  /**
   * <div class="introConstant">
   *   Index of the <i>y</i>-coordinate.
   * </div>
   */
  public static final int    Y_COORDINATE = 1;
  /**
   * <div class="introConstant">
   *   Index of the <i>z</i>-coordinate.
   * </div>
   */
  public static final int    Z_COORDINATE = 2;
  
  public static final double DEFAULT_X    = 0.0;
  public static final double DEFAULT_Y    = 0.0;
  public static final double DEFAULT_Z    = 0.0;
  
  private Point3d coords;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a position with all coordinates set to a given value.
   * </div>
   * 
   * @param coordinate  The value to use for all coordinates.
   */
  public Position (double coordinate)
  {
    initPosition (coordinate, coordinate, coordinate);
  }
  
  public Position (double x, double y)
  {
    initPosition (x, y, DEFAULT_Z);
  }
  
  public Position (double x, double y, double z)
  {
    initPosition (x, y, z);
  }
  
  public Position (Position position)
  {
    if (position != null)
    {
      double x = position.getX ();
      double y = position.getY ();
      double z = position.getZ ();
      
      initPosition (x, y, z);
    }
    else
    {
      initPosition (DEFAULT_X, DEFAULT_Y, DEFAULT_Z);
    }
  }
  
  
  /**
   * <div class="introConstructor">
   *   Creates a position based on the coordinates defined in a given
   *   <code>Tuple3d</code>.
   * </div>
   * 
   * @param tuple3d  The <code>Tuple3d</code> object to use.
   *                 It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If <code>tuple3d</code> equals
   *                               <code>null</code>.
   */
  public Position (Tuple3d tuple3d)
  {
    CheckingUtils.checkForNull (tuple3d, "Tuple3d is null.");
    
    initPosition (tuple3d.getX (), tuple3d.getY (), tuple3d.getZ ());
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a position based on the coordinates defined in a given
   *   <code>Tuple2d</code>.
   * </div>
   * 
   * @param tuple3d  The <code>Tuple2d</code> object to use.
   *                 It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If <code>tuple2d</code> equals
   *                               <code>null</code>.
   */
  public Position (Tuple2d tuple2d)
  {
    CheckingUtils.checkForNull (tuple2d, "Tuple2d is null.");
    
    initPosition (tuple2d.getX (), tuple2d.getY (), 0.0);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a position based on the coordinates defined in a given
   *   <code>Point2D</code>.
   * </div>
   * 
   * @param point2D  The <code>Point2D</code> object to use.
   *                 It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If <code>point2D</code> equals
   *                               <code>null</code>.
   */
  public Position (Point2D point2D)
  {
    CheckingUtils.checkForNull (point2D, "Point2D is null.");
    
    initPosition (point2D.getX (), point2D.getY (), DEFAULT_Z);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a position with all coordinates set to the default
   *   value zero.
   * </div>
   */
  public Position ()
  {
    initPosition (DEFAULT_X, DEFAULT_Y, DEFAULT_Z);
  }
  
  
  public void setFromPosition (Position otherPosition)
  {
    CheckingUtils.checkForNull (otherPosition, "Other position is null.");
    
    coords.setX (otherPosition.getX ());
    coords.setY (otherPosition.getY ());
    coords.setZ (otherPosition.getZ ());
  }
  
  public void setFromXYZ (double x, double y, double z)
  {
    coords.setX (x);
    coords.setY (y);
    coords.setZ (z);
  }
  
  public void setFromPoint2D (Point2D point2D, boolean keepCurrentZCoordinate)
  {
    double x = point2D.getX ();
    double y = point2D.getY ();
    double z = 0.0;
    
    if (keepCurrentZCoordinate)
    {
      z = coords.getZ ();
    }
    else
    {
      z = DEFAULT_Z;
    }
    
    coords.setX (x);
    coords.setY (y);
    coords.setZ (z);
  }
  
  public void setFromTuple3d (Tuple3d tuple3d)
  {
    double x = tuple3d.getX ();
    double y = tuple3d.getY ();
    double z = tuple3d.getZ ();
    
    coords.setX (x);
    coords.setY (y);
    coords.setZ (z);
  }
  
  public void setFromArray (double[] coordinateArray)
  {
    Vector3d vector3d = new Vector3d (coordinateArray);
    
    setFromXYZ
    (
      vector3d.getX (),
      vector3d.getY (),
      vector3d.getZ ()
    );
  }
  
  public void setRelative
  (
    double deltaX,
    double deltaY,
    double deltaZ
  )
  {
    coords.setX (coords.getX () + deltaX);
    coords.setY (coords.getY () + deltaY);
    coords.setZ (coords.getZ () + deltaZ);
  }
  
  public void setRelative (Vector3d vector3d)
  {
    CheckingUtils.checkForNull (vector3d, "Vector is null.");
    
    setRelative (vector3d.getX (), vector3d.getY (), vector3d.getZ ());
  }
  
  /**
   * <div class="introMethod">
   *   Creates and returns a new position based on this one, but being
   *   translated by the given distances.
   * </div>
   * 
   * <div>
   *   The new position is equal to this one:
   *   <br />
   *   <pre>
   *     new Position (this.getX () + deltaX,
   *                   this.getY () + deltaY,
   *                   this.getZ () + deltaZ)
   *   </pre>
   * </div>
   * 
   * @param deltaX  The translation along the <i>x</i> axis.
   * @param deltaY  The translation along the <i>y</i> axis.
   * @param deltaZ  The translation along the <i>z</i> axis.
   * 
   * @return        A new position with the coordinates of this one
   *                being translated by the vector (<code>deltaX</code>,
   *                <code>deltaY</code>, <code>deltaZ</code>).
   */
  public Position getPositionRelativeToThis
  (
    double deltaX,
    double deltaY,
    double deltaZ
  )
  {
    Position newPosition = new Position (this);
    
    newPosition.setRelative
    (
      deltaX,
      deltaY,
      deltaZ
    );
    
    return newPosition;
  }
  
  public Position getPositionRelativeToThis (Vector3d translationVector)
  {
    Position neuePosition = null;
    
    if (translationVector != null)
    {
      neuePosition = getPositionRelativeToThis
      (
        translationVector.getX (),
        translationVector.getY (),
        translationVector.getZ ()
      );
    }
    else
    {
      
    }
    
    return neuePosition;
  }
  
  public Position getPositionRelativeToThis (Position translation)
  {
    Position neuePosition = null;
    
    if (translation != null)
    {
      double xDifferenz = translation.getX ();
      double yDifferenz = translation.getY ();
      double zDifferenz = translation.getZ ();
      
      neuePosition = getPositionRelativeToThis
      (
        xDifferenz,
        yDifferenz,
        zDifferenz
      );
    }
    
    return neuePosition;
  }
  
  
  public double getX ()
  {
    double x = coords.getX ();
    
    return x;
  }
  
  public void setX (double x)
  {
    coords.setX (x);
  }
  
  public void setXRelative (double umX)
  {
    coords.setX (coords.getX () + umX);
  }
  
  
  public double getY ()
  {
    double y = coords.getY ();
    
    return y;
  }
  
  public void setY (double y)
  {
    coords.setY (y);
  }
  
  public void setYRelative (double umY)
  {
    coords.setY (coords.getY () + umY);
  }
  
  
  public double getZ ()
  {
    double z = coords.getZ ();
    
    return z;
  }
  
  public void setZ (double z)
  {
    coords.setZ (z);
  }
  
  public void setZRelative (double umZ)
  {
    coords.setZ (coords.getZ () + umZ);
  }
  
  public double getCoordinate (int coordinateIndex)
  {
    double[] asArray = getAsArray ();
    
    return asArray[coordinateIndex];
  }
  
  public void setCoordinate (int coordinateIndex, double value)
  {
    double[] asArray = getAsArray ();
    
    asArray[coordinateIndex] = value;
    setFromArray (asArray);
  }
  
  
  public void add (Tuple3d tuple3d)
  {
    coords.add (tuple3d);
  }
  
  public void add (Position position)
  {
    add (position.getAsVector3d ());
  }
  
  public void add (double x, double y, double z)
  {
    add (new Vector3d (x, y, z));
  }
  
  public void scale (double scalingFactor)
  {
    coords.scale (scalingFactor);
  }
  
  public void add (double value)
  {
    add (value, value, value);
  }
  
  
  public Point2D getAsPoint2D ()
  {
    Point2D point2D = null;
    double  x       = coords.getX ();
    double  y       = coords.getY ();
    
    point2D         = new Point2D.Double (x, y);
    
    return point2D;
  }
  
  public Point3d getAsPoint3d ()
  {
    Point3d point3d = new Point3d (coords);
    
    return point3d;
  }
  
  public Point2d getAsPoint2d ()
  {
    Point2d point2d = null;
    
    point2d = new Point2d (coords.getX (), coords.getY ());
    
    return point2d;
  }
  
  // 20-12-2013
  public Point getAsPoint ()
  {
    Point point  = null;
    int   pointX = new Double (coords.getX ()).intValue ();
    int   pointY = new Double (coords.getY ()).intValue ();
    
    point = new Point (pointX, pointY);
    
    return point;
  }
  
  public double[] getAsArray ()
  {
    double[] tupel = new double[COORD_COUNT];
    
    coords.get (tupel);
    
    return tupel;
  }
  
  public Vector3d getAsVector3d ()
  {
    return new Vector3d (coords);
  }
  
  
  
  // 05.07.2013
  public void setCoordinatesFromMouseEvent (MouseEvent mouseEvent)
  {
    if (mouseEvent != null)
    {
      int mouseX = mouseEvent.getX ();
      int mouseY = mouseEvent.getY ();
      
      coords.setX (mouseX);
      coords.setY (mouseY);
    }
  }
  
  // 01.11.2013
  public void setCoordinatesFromRectangle2D
  (
    Rectangle2D rectangle2D,
    boolean     keepZCoordinate
  )
  {
    if (rectangle2D != null)
    {
      double rectangleX = rectangle2D.getX ();
      double rectangleY = rectangle2D.getY ();
      
      coords.setX (rectangleX);
      coords.setY (rectangleY);
      
      if (! keepZCoordinate)
      {
        coords.setZ (0.0);
      }
    }
  }
  
  // 25-12-2013
  public void setCoordinatesFromShape
  (
    Shape   shape,
    boolean keepZCoordinate
  )
  {
    if (shape != null)
    {
      Rectangle2D shapeBounds2D = shape.getBounds2D ();
      
      setCoordinatesFromRectangle2D (shapeBounds2D, keepZCoordinate);
    }
  }
  
  
  // 05.07.2013
  public double getEuclideanDistance2DFrom (Position position)
  {
    CheckingUtils.checkForNull (position, "No point given.");
    
    double  abstand       = 0.0;
    Point2D ichAlsPoint2D = getAsPoint2D          ();
    Point2D erAlsPoint2D  = position.getAsPoint2D ();
    
    abstand = ichAlsPoint2D.distance (erAlsPoint2D);
    
    return abstand;
  }
  
  // 21.07.2014
  public double getEuclideanDistance3DFrom (Position otherPosition)
  {
    CheckingUtils.checkForNull (otherPosition, "No point given.");
    
    double  distance       = 0.0;
    Point3d meAsPoint3d    = this.getAsPoint3d          ();
    Point3d otherAsPoint3d = otherPosition.getAsPoint3d ();
    
    distance = meAsPoint3d.distance (otherAsPoint3d);
    
    return distance;
  }
  
  // 05.07.2013
  public Angle getAzimutAngleBetween (Position position)
  {
    CheckingUtils.checkForNull (position, "Other position is null.");
    
    Angle  azimut        = null;
    double azimutRadiant = 0.0;
    double meinX         = getX ();
    double meinY         = getY ();
    double seinX         = position.getX ();
    double seinY         = position.getY ();
    double differenzX    = meinX - seinX;
    double differenzY    = meinY - seinY;
    
    azimutRadiant = Math.atan2 (differenzY, differenzX);
    azimut        = new Angle  (AngleUnit.RADIAN, azimutRadiant);
    
    return azimut;
  }
  
  
  // {+} 03.03.2014
  /**
   * <div class="introMethod">
   *   Returns a direction vector from another position to this one.
   * </div>
   * 
   * <div>
   *   The formula is as follows:
   *   <p>
   *     <code>thisPosition - otherPosition</code>
   *   </p>
   * </div>
   * 
   * @param otherPosition  The position to subtract from this one.
   * 
   * @return               A 3D vector from the other position to this
   *                       one.
   */
  public Vector3d getVector3dFromOtherPointToThis (Position otherPosition)
  {
    CheckingUtils.checkForNull (otherPosition, "Other point is null.");
    
    Vector3d abstaende = null;
    double   abstandX  = 0.0;
    double   abstandY  = 0.0;
    double   abstandZ  = 0.0;
    double   seinX     = 0.0;
    double   seinY     = 0.0;
    double   seinZ     = 0.0;
    
    if (otherPosition != null)
    {
      seinX     = otherPosition.getX ();
      seinY     = otherPosition.getY ();
      seinZ     = otherPosition.getZ ();
      abstandX  = (coords.getX () - seinX);
      abstandY  = (coords.getY () - seinY);
      abstandZ  = (coords.getZ () - seinZ);
      abstaende = new Vector3d (abstandX, abstandY, abstandZ);
    }
    else
    {
      abstaende = null;
    }
    
    return abstaende;
  }
  
  // otherPosition - thisPosition
  public Vector3d getVector3dFromThisToOtherPoint (Position otherPoint)
  {
    CheckingUtils.checkForNull (otherPoint, "Other point is null.");
    
    Vector3d directionVector    = null;
    Vector3d thisPointAsVector  = null;
    Vector3d otherPointAsVector = null;
    
    directionVector    = new Vector3d             ();
    thisPointAsVector  = this.getAsVector3d       ();
    otherPointAsVector = otherPoint.getAsVector3d ();
    
    directionVector.sub (otherPointAsVector, thisPointAsVector);
    
    return directionVector;
  }
  
  public Vector3d getDistancesXY (Position toPoint)
  {
    Vector3d distances = new Vector3d ();
    
    distances.setX (this.getX () - toPoint.getX ());
    distances.setY (this.getY () - toPoint.getY ());
    distances.setZ (0.0);
    
    return distances;
  }
  
  
  /**
   * <div class="introMethod">
   *   Adds the coordinates of a given 3D vector to this point,
   *   creating a new position object.
   * </div>
   * 
   * @param vector3d  The vector whose coordinates shall be added to
   *                  this position to gain a result. It must not be
   *                  <code>null</code>.
   * 
   * @return          A new <code>Position</code> resulting from the
   *                  given vector's coordinates being added to this
   *                  position's coordinates.
   * 
   * @throws NullPointerException  If the <code>vector3d</code> equals
   *                               <code>null</code>.
   */
  public Position getAddedPosition (Vector3d vector3d)
  {
    CheckingUtils.checkForNull (vector3d, "Vector3d is null.");
    
    Position resultingPosition = null;
    Vector3d resultingVector3d = new Vector3d  ();
    Vector3d myVector3d        = getAsVector3d ();
    
    resultingVector3d.add (myVector3d, vector3d);
    
    resultingPosition = new Position (resultingVector3d);
    
    return resultingPosition;
  }
  
  public Position getAddedPosition (Position position)
  {
    Position resultingPosition = null;
    Vector3d vector3d          = position.getAsVector3d ();
    
    resultingPosition = getAddedPosition (vector3d);
    
    return resultingPosition;
  }
  
  public Position getAddedPosition (double[] coordinateArray)
  {
    Position neuePosition = null;
    Vector3d seinVektor   = new Vector3d (coordinateArray);
    
    neuePosition = getAddedPosition (seinVektor);
    
    return neuePosition;
  }
  
  /**
   * <div class="introMethod">
   *   Adds the given coordinates to this position, creating a new
   *   one.
   * </div>
   * 
   * <div>
   *   The new position is equal to this one:
   *   <br />
   *   <pre>
   *     new Position (this.getX () + x,
   *                   this.getY () + y,
   *                   this.getZ () + z)
   *   </pre>
   * </div>
   * 
   * @param x  The <i>x</i> value to add.
   * @param y  The <i>y</i> value to add.
   * @param z  The <i>z</i> value to add.
   * 
   * @return   A new position with given coordinates added to this
   *           position.
   */
  public Position getAddedPosition (double x, double y, double z)
  {
    Position resultingPosition = null;
    Vector3d vector            = new Vector3d (x, y, z);
    
    resultingPosition = getAddedPosition (vector);
    
    return resultingPosition;
  }
  
  
  public Position getSubtractedPosition (Vector3d vector3d)
  {
    Position resultingPosition = null;
    Vector3d resultingVector3d = new Vector3d  ();
    Vector3d myVector3d        = getAsVector3d ();
    
    resultingVector3d.sub (myVector3d, vector3d);
    
    resultingPosition = new Position (resultingVector3d);
    
    return resultingPosition;
  }
  
  public Position getSubtractedPosition (Position positionToSubtract)
  {
    Position resultingPosition = null;
    Vector3d vectorToSubtract  = positionToSubtract.getAsVector3d ();
    
    resultingPosition = getSubtractedPosition (vectorToSubtract);
    
    return resultingPosition;
  }
  
  public Position getSubtractedPosition (double[] koordinaten)
  {
    Position neuePosition  = new Position ();
    Vector3d neuerVector3d = new Vector3d ();
    Vector3d meinVector3d  = getAsVector3d ();
    Vector3d seinVector3d  = new Vector3d (koordinaten);
    
    neuerVector3d.sub (meinVector3d, seinVector3d);
    
    neuePosition = new Position (neuerVector3d);
    
    return neuePosition;
  }
  
  public Position getSubtractedPosition (double x, double y, double z)
  {
    Position resultingPosition = null;
    Vector3d vector            = new Vector3d (x, y, z);
    
    resultingPosition = getSubtractedPosition (vector);
    
    return resultingPosition;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a new point by scaling this one's coordinates by the
   *   given factors.
   * </div>
   * 
   * <div>
   *   The new position is equal to this one:
   *   <br />
   *   <pre>
   *     new Position (this.getX () * scaleX,
   *                   this.getY () * scaleY,
   *                   this.getZ () * scaleZ)
   *   </pre>
   * </div>
   * 
   * @param scaleX  The factor to scale the <i>x</i>-coordinate by.
   * @param scaleY  The factor to scale the <i><</i>-coordinate by.
   * @param scaleZ  The factor to scale the <i>z</i>-coordinate by.
   * 
   * @return        A new position obtained by scaling this position's
   *                coordinates by the given factors.
   */
  public Position getScaledPosition
  (
    double scaleX,
    double scaleY,
    double scaleZ
  )
  {
    Position scaledPosition = new Position ();
    
    scaledPosition.setX (this.getX () * scaleX);
    scaledPosition.setY (this.getY () * scaleY);
    scaledPosition.setZ (this.getZ () * scaleZ);
    
    return scaledPosition;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a new point by scaling this one's coordinates uniformly
   *   by the given scaling factor.
   * </div>
   * 
   * <div>
   *   The new position is equal to this one:
   *   <br />
   *   <pre>
   *     new Position (this.getX () * scaling,
   *                   this.getY () * scaling,
   *                   this.getZ () * scaling)
   *   </pre>
   * </div>
   * 
   * @param scaling  The factor to scale the coordinates by.
   * 
   * @return        A new position obtained by scaling this position's
   *                coordinates by the given factor.
   */
  public Position getScaledPosition (double scaling)
  {
    Position scaledPosition = null;
    
    scaledPosition = getScaledPosition (scaling, scaling, scaling);
    
    return scaledPosition;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns a position with the coordinates of this one being
   *   negated, that means, their signs are inverted.
   * </div>
   * 
   * <div>
   *   The new position is equal to this one:
   *   <br />
   *   <pre>
   *     new Position (this.getX () * -1.0,
   *                   this.getY () * -1.0,
   *                   this.getZ () * -1.0)
   *   </pre>
   * </div>
   * 
   * @return  A new position with the negated coordinates of this one.
   */
  public Position getNegatedPosition ()
  {
    Position negatedPosition = null;
    
    negatedPosition = getScaledPosition (-1.0, -1.0, -1.0);
    
    return negatedPosition;
  }
  
  
  // <+> 03-03-2014
  // Checks whether each coordinate (x, y, z) equals zero.
  public boolean isZeroPosition ()
  {
    boolean isZeroPosition =
    (
      (coords.getX () == 0.0) &&
      (coords.getY () == 0.0) &&
      (coords.getZ () == 0.0)
    );
    
    return isZeroPosition;
  }
  
  
  // 05.04.2014
  public Line2D createLine2D (Position otherPosition)
  {
    Line2D line2D = null;
    
    if (otherPosition != null)
    {
      Point2D startPoint = getAsPoint2D               ();
      Point2D endPoint   = otherPosition.getAsPoint2D ();
      
      line2D = new Line2D.Double (startPoint, endPoint);
    }
    else
    {
      line2D = null;
    }
    
    return line2D;
  }
  
  // 28-05-2014
//  public Rectangle2D createRectangle2D (Dimensions dimensionen)
//  {
//    Rectangle2D rectangle2D = null;
//    double      rectangleX  = coords.getX ();
//    double      rectangleY  = coords.getY ();
//    
//    if (dimensionen != null)
//    {
//      rectangle2D = dimensionen.createRectangle2D (this);
//    }
//    else
//    {
//      rectangle2D = new Rectangle2D.Double (rectangleX, rectangleY, 0.0, 0.0);
//    }
//    
//    return rectangle2D;
//  }
  
  // Elongates towards the point "to" (type of scaling).
  public Position getWithAddedDistance (Position to, double extraDistance)
  {
    Position resultingPosition = null;
    
    resultingPosition = createWithAddedDistance (this, to, extraDistance);
    
    return resultingPosition;
  }
  
  public void addDistance (Position to, double extraDistance)
  {
    Position resultingPosition = null;
    
    resultingPosition = getWithAddedDistance (to, extraDistance);
    
    setFromPosition (resultingPosition);
  }
  
  
  // 14.03.2015
  // fraction = parameter t.
  // Examples: t = 0 => this; t = 0.5 => midpoint; t = 1 => to.
  /**
   * <div class="introMethod">
   *   Returns a point at a certain fraction of an edge between this
   *   point and a second one.
   * </div>
   * 
   * <div>
   *   <p>
   *     The <code>fraction</code> parameter is a scalar value typically
   *     in the range [0.0, 1.0] which determines the ratio of the
   *     resulting point from this position as starting point to the
   *     end position <code>to</code>.
   *   </p>
   *   <p>
   *     Common fraction values and their meaning are:
   *     
   *     <table>
   *       <tr>
   *         <th><code>fraction</code></th>
   *         <th>Point</th>
   *       </tr>
   *       <tr>
   *         <td>0.0</td>
   *         <td>start point (<code>this</code>)</td>
   *       </tr>
   *       <tr>
   *         <td>0.5</td>
   *         <td>
   *           midpoint between <code>this</code> and <code>to</code>
   *         </td>
   *       </tr>
   *       <tr>
   *         <td>1.0</td>
   *         <td>end point (<code>to</code>)</td>
   *       </tr>
   *     </table>
   *   </p>
   * </div>
   * 
   * @param to        The second end point to form an edge with and
   *                  return its point at a given fraction.
   * @param fraction  The resulting point's fraction as ratio of
   *                  this point and the <code>to</code> point.
   * 
   * @return          The point on the edge between this point and the
   *                  given <code>to</code> point at the
   *                  <code>fraction</code>.
   */
  public Position getPositionOnLine (Position to, double fraction)
  {
    Position positionOnLine = null;
    Vector3d vector         = null;
    
    vector = to.getVector3dFromOtherPointToThis (this);
    vector.scale                            (fraction);
    positionOnLine = this.getAddedPosition  (vector);
    
    return positionOnLine;
  }
  
  // Formula: P + v * t.
  public Position getPositionOnLine (Vector3d vector, double fraction)
  {
    Position positionOnLine = null;
    Vector3d scaledVector   = null;
    
    scaledVector   = new Vector3d          (vector);
    scaledVector.scale                     (fraction);
    positionOnLine = this.getAddedPosition (scaledVector);
    
    return positionOnLine;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the midpoint between this point and a second one.
   * </div>
   * 
   * @param to  The second end point to form an edge with and return
   *            its midpoint.
   * 
   * @return    The midpoint of the edge between this point and the
   *            given <code>to</code> point.
   */
  public Position getMidpoint (Position to)
  {
    Position midpoint = null;
    
    midpoint = getPositionOnLine (to, 0.5);
    
    return midpoint;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static methods.                          -- //
  //////////////////////////////////////////////////////////////////////
  
  public static Position createFromXYZ (double x, double y, double z)
  {
    Position position = null;
    
    position = new Position (x, y, z);
    
    return position;
  }
  
  public static Position createFromXY (double x, double y)
  {
    Position position = null;
    
    position = new Position (x, y);
    
    return position;
  }
  
  public static Position createWithAllCoordinatesSetTo (double coordinate)
  {
    return new Position (coordinate);
  }
  
  public static Position createByCopying (Position positionToCopy)
  {
    CheckingUtils.checkForNull
    (
      positionToCopy,
      "Position to copy is null."
    );
    
    return new Position (positionToCopy);
  }
  
  public static Position createFromTuple3d (Tuple3d tuple3d)
  {
    Position position = null;
    
    position = new Position ();
    position.setFromTuple3d (tuple3d);
    
    return position;
  }
  
  public static Position createFromTransform3D (Transform3D transform3D)
  {
    return null;
  }
  
  public static Position createFromPoint2D (Point2D point2D)
  {
    return new Position (point2D);
  }
  
  public static Position createDefault ()
  {
    return new Position ();
  }
  
  
  
  public static Position createWithAddedDistance
  (
    Position from,
    Position to,
    double   extraDistance
  )
  {
    Position resultingPosition = null;
    double   distance          = 0.0;
    Vector3d distancesVector   = null;
    double   scaling           = 0.0;
    
    distance = to.getEuclideanDistance3DFrom (from);
    
    if (distance != 0.0)
    {
      distancesVector   = to.getVector3dFromOtherPointToThis (from);
      scaling           = ((distance + extraDistance) / distance);
      resultingPosition = from.getAddedPosition
      (
        distancesVector.getX () * scaling,
        distancesVector.getY () * scaling,
        distancesVector.getZ () * scaling
      );
    }
    // Both points are equal? => Add distance along positive x-axis.
    else
    {
      resultingPosition = to.getAddedPosition (extraDistance, 0.0, 0.0);
    }
    
    return resultingPosition;
  }
  
  public static Vector3d getDistancesXY
  (
    Position fromPoint,
    Position toPoint
  )
  {
    Vector3d distances = new Vector3d ();
    
    distances.setX (toPoint.getX () - fromPoint.getX ());
    distances.setY (toPoint.getY () - fromPoint.getY ());
    distances.setZ (0.0);
    
    return distances;
  }
  
  
  // PARAM2 - PARAM1
  public static Vector3d getVectorFromTo (Position fromPosition, Position toPosition)
  {
    return fromPosition.getVector3dFromThisToOtherPoint (toPosition);
  }
  
  // PARAM1 - PARAM2
  public static Vector3d getVectorToFrom (Position toPosition, Position fromPosition)
  {
    return fromPosition.getVector3dFromThisToOtherPoint (toPosition);
  }
  
  
  public Vector3d getSignums ()
  {
    Vector3d signums = new Vector3d ();
    
    signums.setX (Math.signum (coords.getX ()));
    signums.setY (Math.signum (coords.getY ()));
    signums.setZ (Math.signum (coords.getZ ()));
    
    return signums;
  }
  
  /* Example
   * 
   * changeCoordinates (Z_COORDINATE, X_COORDINATE, X_COORDINATE)
   * => sets x-coordinate to z-coordinate
   *    sets y-coordinate to x-coordinate
   *    sets z-coordinate to x-coordinate
   */
  public void rearrangeCoordinates
  (
    int coordIndexToUseAsXCoordinate,
    int coordIndexToUseAsYCoordinate,
    int coordIndexToUseAsZCoordinate
  )
  {
    double coordinateAsXCoord = 0.0;
    double coordinateAsYCoord = 0.0;
    double coordinateAsZCoord = 0.0;
    
    coordinateAsXCoord = getCoordinate (coordIndexToUseAsXCoordinate);
    coordinateAsYCoord = getCoordinate (coordIndexToUseAsYCoordinate);
    coordinateAsZCoord = getCoordinate (coordIndexToUseAsZCoordinate);
    
    setCoordinate (X_COORDINATE, coordinateAsXCoord);
    setCoordinate (Y_COORDINATE, coordinateAsYCoord);
    setCoordinate (Z_COORDINATE, coordinateAsZCoord);
  }
  
  public Position getRearrangedCoordinates
  (
    int coordIndexToUseAsXCoordinate,
    int coordIndexToUseAsYCoordinate,
    int coordIndexToUseAsZCoordinate
  )
  {
    Position newPosition = new Position (this);
    
    newPosition.rearrangeCoordinates
    (
      coordIndexToUseAsXCoordinate,
      coordIndexToUseAsYCoordinate,
      coordIndexToUseAsZCoordinate
    );
    
    return newPosition;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public Position clone ()
  {
    try
    {
      return (Position) super.clone ();
    }
    catch (CloneNotSupportedException cnse)
    {
      cnse.printStackTrace ();
      
      return null;
    }
  }
  
  @Override
  public boolean equals (Object object)
  {
    boolean areEqual = false;
    
    if (object != null)
    {
      if (object instanceof Position)
      {
        Position otherPosition = (Position) object;
        double   otherX        = otherPosition.getX ();
        double   otherY        = otherPosition.getY ();
        double   otherZ        = otherPosition.getZ ();
        
        areEqual =
        (
          (otherX == this.getX ()) &&
          (otherY == this.getY ()) &&
          (otherZ == this.getZ ())
        );
      }
      else
      {
        areEqual = false;
      }
    }
    else
    {
      areEqual = false;
    }
    
    return areEqual;
  }
  
  // -> "http://en.wikipedia.org/wiki/Java_hashCode%28%29"
  // -> "http://stackoverflow.com/questions/9650798/hash-a-double-in-java"
  @Override
  public int hashCode ()
  {
    int hashCode     = 0;
    int hashCodeForX = new Double (getX ()).hashCode ();
    int hashCodeForY = new Double (getY ()).hashCode ();
    int hashCodeForZ = new Double (getZ ()).hashCode ();
    
    hashCode = 1;
    hashCode = ((hashCode * 17) + hashCodeForX);
    hashCode = ((hashCode * 31) + hashCodeForY);
    hashCode = ((hashCode * 13) + hashCodeForZ);
    
    return hashCode;
  }
  
  @Override
  public String toString ()
  {
    String asString = null;
    double x        = getX ();
    double y        = getY ();
    double z        = getZ ();
    
    asString = String.format ("Position(x=%s, y=%s, z=%s)", x, y, z);
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initPosition (double x, double y, double z)
  {
    this.coords = new Point3d (x, y, z);
  }
}