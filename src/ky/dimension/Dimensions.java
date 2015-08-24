// 02.04.2013

package ky.dimension;

import commies.angle.Angle;
import ky.transform.Scaling;
import safercode.CheckingUtils;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.vecmath.Tuple3d;
import javax.vecmath.Vector3d;


/**
 * <div class="introClass">
 *   Defines 2D or 3D dimensions with double precision floating point
 *   numbers, that is, width, height and depth.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Dimensions.java
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
 *       02.04.2013
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
 *     <td>02.04.2013</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class Dimensions
{
  private double width;
  private double height;
  private double depth;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a three-dimensional size object.
   * </div>
   * 
   * @param width   The width.
   * @param height  The height.
   * @param depth   The depth.
   */
  public Dimensions (double width, double height, double depth)
  {
    initDimensions (width, height, depth);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a dimensions object by copying an existing one.
   * </div>
   * 
   * @param dimensionsToCopy  The optional dimensions object to copy.
   */
  public Dimensions (Dimensions dimensionsToCopy)
  {
    double widthToCopy  = 0.0;
    double heightToCopy = 0.0;
    double depthToCopy  = 0.0;
    
    if (dimensionsToCopy != null)
    {
      widthToCopy  = dimensionsToCopy.getWidth ();
      heightToCopy = dimensionsToCopy.getHeight ();
      depthToCopy  = dimensionsToCopy.getDepth ();
    }
    else
    {
      widthToCopy  = 0.0;
      heightToCopy = 0.0;
      depthToCopy  = 0.0;
    }
    
    initDimensions (widthToCopy, heightToCopy, depthToCopy);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a dimensions object based on a <code>Tuple3d</code>.
   * </div>
   * 
   * @param tuple3d  An optional tuple to use as base.
   */
  public Dimensions (Tuple3d tuple3d)
  {
    if (tuple3d != null)
    {
      initDimensions (tuple3d.getX (),
                      tuple3d.getY (),
                      tuple3d.getZ ());
    }
    else
    {
      initDimensions (0.0, 0.0, 0.0);
    }
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a two-dimensional size object.
   * </div>
   * 
   * @param width   The width.
   * @param height  The height.
   */
  public Dimensions (double width, double height)
  {
    initDimensions (width, height, 0.0);
  }
  
  public Dimensions (Rectangle2D rectangle2D)
  {
    if (rectangle2D != null)
    {
      initDimensions (rectangle2D.getWidth  (),
                      rectangle2D.getHeight (),
                      0.0);
    }
    else
    {
      initDimensions (0.0, 0.0, 0.0);
    }
  }
  
  public Dimensions (double dimension)
  {
    initDimensions (dimension, dimension, dimension);
  }
  
  public Dimensions ()
  {
    initDimensions (0.0, 0.0, 0.0);
  }
  
  
  public Vector3d getAsVector3d ()
  {
    return new Vector3d (width, height, depth);
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
  
  
  public void set (double width, double height, double depth)
  {
    this.width  = width;
    this.height = height;
    this.depth  = depth;
  }
  
  public void set (double width, double height)
  {
    set (width, height, this.depth);
  }
  
  public void setAllTo (double dimension)
  {
    set (dimension, dimension, dimension);
  }
  
  
  // 12.06.2013
  public void setDimensionsFromShape (Shape shape, boolean keepDepth)
  {
    CheckingUtils.checkForNull (shape, "Shape is null.");
    
    Rectangle2D shapeBoundingRectangle = shape.getBounds ();
    
    width  = shapeBoundingRectangle.getWidth  ();
    height = shapeBoundingRectangle.getHeight ();
    
    if (! keepDepth)
    {
      depth  = 0.0;
    }
  }
  
  public void setDimensionsFromShape (Shape shape, double depth)
  {
    CheckingUtils.checkForNull (shape, "Shape is null.");
    
    Rectangle2D shapeBoundingRectangle = shape.getBounds ();
    
    this.width  = shapeBoundingRectangle.getWidth  ();
    this.height = shapeBoundingRectangle.getHeight ();
    this.depth  = depth;
  }
  
  // 01.10.2013
  public void setDimensionsFrom (Dimensions otherDimensions)
  {
    if (otherDimensions != null)
    {
      this.width  = otherDimensions.getWidth  ();
      this.height = otherDimensions.getHeight ();
      this.depth  = otherDimensions.getDepth  ();
    }
  }
  
  public void setFromTuple3d (Tuple3d tuple3d)
  {
    CheckingUtils.checkForNull
    (
      tuple3d,
      "No Tuple3d given."
    );
    this.width  = tuple3d.getX ();
    this.height = tuple3d.getY ();
    this.depth  = tuple3d.getZ ();
  }
  
  // 01.11.2013
  public void setDimensionsFromRectangle2D
  (
    Rectangle2D rectangle2D,
    boolean     keepDepth
  )
  {
    if (rectangle2D != null)
    {
      this.width = rectangle2D.getWidth  ();
      this.height  = rectangle2D.getHeight ();
      
      if (! keepDepth)
      {
        this.depth = 0.0;
      }
    }
  }
  
  public void setDimensionsFromBufferedImage
  (
    BufferedImage image,
    boolean       keepDepth
  )
  {
    if (image != null)
    {
      width  = new Double (image.getWidth  ()).doubleValue ();
      height = new Double (image.getHeight ()).doubleValue ();
      
      if (! keepDepth)
      {
        depth = 0.0;
      }
    }
  }
  
  
  // 24.08.2013
  public void scale (double scalingFactor)
  {
    scale (scalingFactor, scalingFactor, scalingFactor);
  }
  
  // 24.08.2013
  public void scale
  (
    double scalingFactorX,
    double scalingFactorY
  )
  {
    scale (scalingFactorX, scalingFactorY, 1.0);
  }
  
  // 24.08.2013
  public void scale
  (
    double scalingFactorX,
    double scalingFactorY,
    double scalingFactorZ
  )
  {
    width  = (width  * scalingFactorX);
    height = (height * scalingFactorY);
    depth  = (depth  * scalingFactorZ);
  }
  
  public void scale (Vector3d scalingFactors)
  {
    scale (scalingFactors.getX (),
           scalingFactors.getY (),
           scalingFactors.getZ ());
  }
  
  public void scale (Scaling scaling)
  {
    scale (scaling.getAsVector3d ());
  }
  
  // 26-07-2014
  public Dimensions getScaledDimensionsFromThis
  (
    double scalingFactorX,
    double scalingFactorY,
    double scalingFactorZ
  )
  {
    Dimensions scaledDimensions = new Dimensions (this);
    
    scaledDimensions.scale
    (
      scalingFactorX,
      scalingFactorY,
      scalingFactorZ
    );
    
    return scaledDimensions;
  }
  
  public Dimensions getScaledDimensionsFromThis (Scaling scaling)
  {
    Dimensions scaledDimensions = null;
    
    scaledDimensions = getScaledDimensionsFromThis
    (
      scaling.getScalingX (),
      scaling.getScalingY (),
      scaling.getScalingZ ()
    );
    
    return scaledDimensions;
  }
  
  // 26-07-2014
  public Dimensions getScaledDimensionsFromThis
  (
    double scalingFactorX,
    double scalingFactorY
  )
  {
    Dimensions scaledDimensions = new Dimensions (this);
    
    scaledDimensions.scale
    (
      scalingFactorX,
      scalingFactorY,
      1.0
    );
    
    return scaledDimensions;
  }
  
  // 26-07-2014
  public Dimensions getScaledDimensionsFromThis (double scalingFactor)
  {
    Dimensions scaledDimensions = new Dimensions (this);
    
    scaledDimensions.scale
    (
      scalingFactor,
      scalingFactor,
      scalingFactor
    );
    
    return scaledDimensions;
  }
  
  public Dimensions getAddedDimensions (Dimensions otherDimensions)
  {
    Dimensions resultingDimensions = new Dimensions ();
    double     resultingWidth      = 0.0;
    double     resultingHeight     = 0.0;
    double     resultingDepth      = 0.0;
    
    resultingWidth  = (this.width  + otherDimensions.getWidth  ());
    resultingHeight = (this.height + otherDimensions.getHeight ());
    resultingDepth  = (this.depth  + otherDimensions.getDepth  ());
    
    resultingDimensions.setWidth  (resultingWidth);
    resultingDimensions.setHeight (resultingHeight);
    resultingDimensions.setDepth  (resultingDepth);
    
    return resultingDimensions;
  }
  
  public Dimensions getSubtractedDimensions (Dimensions otherDimensions)
  {
    Dimensions resultingDimensions = new Dimensions ();
    double     resultingWidth      = 0.0;
    double     resultingHeight     = 0.0;
    double     resultingDepth      = 0.0;
    
    resultingWidth  = (this.width  - otherDimensions.getWidth  ());
    resultingHeight = (this.height - otherDimensions.getHeight ());
    resultingDepth  = (this.depth  - otherDimensions.getDepth  ());
    
    resultingDimensions.setWidth  (resultingWidth);
    resultingDimensions.setHeight (resultingHeight);
    resultingDimensions.setDepth  (resultingDepth);
    
    return resultingDimensions;
  }
  
  public Dimensions getDividedDimensions
  (
    double divisorBreite,
    double divisorHoehe,
    double divisorTiefe
  )
  {
    Dimensions neueDimensionen = new Dimensions ();
    
    neueDimensionen.setWidth  (this.getWidth  () / divisorBreite);
    neueDimensionen.setHeight (this.getHeight () / divisorHoehe);
    neueDimensionen.setDepth  (this.getDepth  () / divisorTiefe);
    
    return neueDimensionen;
  }
  
  
  public Dimension2D getAsDimension2D ()
  {
    Dimension2D alsDimension2D = getAsDimension ();
    
    return alsDimension2D;
  }
  
  public Dimension getAsDimension ()
  {
    Dimension asDimension = null;
    int       widthAsInt  = new Double (width).intValue  ();
    int       heightAsInt = new Double (height).intValue ();
    
    asDimension = new Dimension (widthAsInt, heightAsInt);
    
    return asDimension;
  }
  
  // 20.08.2013
  public double[] getAsArray ()
  {
    double[] tupel = {width, height, depth};
    
    return tupel;
  }
  
  // 20.08.2013
  public boolean areOfEqualSize (Dimensions otherDimensions)
  {
    double[] meinTupel   = getAsArray ();
    double[] seinTupel   = otherDimensions.getAsArray ();
    int      anzahlWerte = seinTupel.length;
    
    for (int dimensionNr = 0; dimensionNr < anzahlWerte; dimensionNr++)
    {
      double  meinWert            = meinTupel[dimensionNr];
      double  seinWert            = seinTupel[dimensionNr];
      boolean sindUnterschiedlich = (meinWert != seinWert);
      
      if (sindUnterschiedlich)
      {
        return false;
      }
    }
    
    return true;
  }
  
  
  // 24-08-2013
  public double getDiagonalXY ()
  {
    double diagonaleXY = Math.hypot (width, height);
    
    return diagonaleXY;
  }
  
  // 24-08-2013
  public double getDiagonalXZ ()
  {
    double diagonaleXZ = Math.hypot (width, depth);
    
    return diagonaleXZ;
  }
  
  // 24-08-2013
  public double getDiagonalYZ ()
  {
    double diagonaleXZ = Math.hypot (height, depth);
    
    return diagonaleXZ;
  }
  
  
  
  // 14-12-2013
  public double getLongestDimension ()
  {
    double longestDimension = 0.0;
    
    longestDimension = Math.max (width,            height);
    longestDimension = Math.max (longestDimension, depth );
    
    return longestDimension;
  }
  
  //14-12-2013
  public double getShortestDimension ()
  {
    double shortestDimension = 0.0;
    
    shortestDimension = Math.min (width,             height);
    shortestDimension = Math.min (shortestDimension, depth );
    
    return shortestDimension;
  }
  
  
  // 18-12-2013
//  public Rectangle2D createRectangle2D (Position position)
//  {
//    Rectangle2D rectangle2D = new Rectangle2D.Double
//    (
//      position.getX (), position.getY (),
//      width, height
//    );
//    
//    return rectangle2D;
//  }
//  
//  public Rectangle2D createRectangle2D (double x, double y)
//  {
//    Rectangle2D rectangle2D = null;
//    Position    position    = new Position (x, y);
//    
//    rectangle2D = createRectangle2D (position);
//    
//    return rectangle2D;
//  }
  
//  public Rectangle2D createRectangle2D ()
//  {
//    Rectangle2D rectangle2D = createRectangle2D (0.0, 0.0);
//    
//    return rectangle2D;
//  }
  
  
  // 22-02-2014
  public boolean istLessOrEqualToZero ()
  {
    boolean istNull =
    (
      (width  <= 0.0) &&
      (height <= 0.0) &&
      (depth  <= 0.0)
    );
    
    return istNull;
  }
  
  // 22-02-2014
  public boolean isXYLessOrEqualToZero ()
  {
    boolean hasZeroDimension =
    (
      (width  <= 0.0) ||
      (height <= 0.0)
    );
    
    return hasZeroDimension;
  }
  
  
  // 09.08.2014
  public Scaling getScalingToMatch (Dimensions zielDimensionen)
  {
    Scaling scaling           = null;
    double  skalierFaktor     = 0.0;
    double  maxZielDimension  = 0.0;
    double  maxMeineDimension = 0.0;
    
    if (zielDimensionen != null)
    {
      scaling           = new Scaling ();
      maxZielDimension  = zielDimensionen.getLongestDimension ();
      maxMeineDimension = getLongestDimension                 ();
      skalierFaktor     = (maxZielDimension / maxMeineDimension);
      
      scaling.setAllTo (skalierFaktor);
    }
    else
    {
      scaling = null;
    }
    
    return scaling;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the scaling factors along the axes, which would scale
   *   this dimensions object to a given one.
   * </div>
   * 
   * <div>
   *   A problem arises, if a dimension of this object equals zero, as
   *   a scaling would be impossible. This case is being handled by
   *   setting the respective scaling factor to zero.
   * </div>
   * 
   * @param otherDimensions  The dimensions object whose dimensions
   *                         shall be reached by this one.
   * @return                 A vector with the necessary scaling factors
   *                         to scale this dimensions object to the
   *                         sizes of the <code>otherDimensions</code>.
   */
  public Vector3d getScalingFactorsToMatch (Dimensions otherDimensions)
  {
    CheckingUtils.checkForNull (otherDimensions, "No dimensions given.");
    
    Vector3d scalingFactors = new Vector3d ();
    
    if (this.width != 0.0)
    {
      scalingFactors.setX (otherDimensions.getWidth  () / this.width);
    }
    else
    {
      scalingFactors.setX (0.0);
    }
    
    if (this.height != 0.0)
    {
      scalingFactors.setY (otherDimensions.getHeight () / this.height);
    }
    else
    {
      scalingFactors.setY (0.0);
    }
    
    if (this.depth != 0.0)
    {
      scalingFactors.setZ (otherDimensions.getDepth  () / this.depth);
    }
    else
    {
      scalingFactors.setZ (0.0);
    }
    
    return scalingFactors;
  }
  
  
//  public static Scaling getScalingToMatchSourceToDestinationDimensions
//  (
//    Dimensions sourceDimensions,       // Dimensions to scale.
//    Dimensions destinationDimensions   // Dimensions to reach.
//  )
//  {
//    Scaling scaling = null;
//    
//    scaling = new DimensionsMatching ().getScalingToMatchSourceToDestinationDimensions
//    (
//      sourceDimensions,
//      destinationDimensions
//    );
//    
//    return scaling;
//  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static creation methods.                 -- //
  //////////////////////////////////////////////////////////////////////
  
  public static Dimensions createFromWidthHeightDepth
  (
    double width,
    double height,
    double depth
  )
  {
    return new Dimensions (width, height, depth);
  }
  
  public static Dimensions createFromWidthHeight
  (
    double width,
    double height
  )
  {
    return new Dimensions (width, height);
  }
  
  public static Dimensions createFromUniformSize (double dimension)
  {
    return new Dimensions (dimension);
  }
  
  public static Dimensions createZeroDimensions ()
  {
    return new Dimensions ();
  }
  
  public static Dimensions createFromOtherDimensions (Dimensions otherDimensions)
  {
    return new Dimensions (otherDimensions);
  }
  
  public static Dimensions createFromDimension2D (Dimension2D dimension2D)
  {
    CheckingUtils.checkForNull (dimension2D, "Dimension2D is null.");
    
    Dimensions dimensions = new Dimensions ();
    
    dimensions.setWidth  (dimension2D.getWidth  ());
    dimensions.setHeight (dimension2D.getHeight ());
    
    return dimensions;
  }
  
  /**
   * <div class="introMethod">
   *   Creates a <code>Dimensions</code> object by copying an image's
   *   width and height, setting the depth to zero.
   * </div>
   * 
   * @param image  The image whose dimensions shall be copied. It must
   *               not be <code>null</code>.
   * 
   * @return       The dimensions of the given image.
   * 
   * @throws NullPointerException  If <code>image</code> equals
   *                               <code>null</code>.
   */
  public static Dimensions createFromBufferedImage (BufferedImage image)
  {
    Dimensions dimensions = new Dimensions ();
    
    dimensions.setDimensionsFromBufferedImage (image, false);
    
    return dimensions;
  }
  
  public static Dimensions createFromShape (Shape shape)
  {
    Dimensions dimensions = new Dimensions ();
    
    dimensions.setDimensionsFromShape (shape, 0.0);
    
    return dimensions;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public Dimensions clone ()
  {
    try
    {
      return (Dimensions) super.clone ();
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
      if (object instanceof Dimensions)
      {
        Dimensions otherDimensions = (Dimensions) object;
        double     hisWidth        = otherDimensions.getWidth  ();
        double     hisHeight       = otherDimensions.getHeight ();
        double     hisDepth        = otherDimensions.getDepth  ();
        
        areEqual =
        (
          (hisWidth  == this.width ) &&
          (hisHeight == this.height) &&
          (hisDepth  == this.depth )
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
  
  @Override
  public int hashCode ()
  {
    int hashCode          = 0;
    int hashCodeForWidth  = new Double (width ).hashCode ();
    int hashCodeForHeight = new Double (height).hashCode ();
    int hashCodeForDepth  = new Double (depth ).hashCode ();
    
    hashCode = 1;
    hashCode = ((hashCode * 17) + hashCodeForWidth );
    hashCode = ((hashCode * 31) + hashCodeForHeight);
    hashCode = ((hashCode * 13) + hashCodeForDepth );
    
    return hashCode;
  }
  
  @Override
  public String toString ()
  {
    String asString = String.format
    (
      "Dimensions(width=%s, height=%s, depth=%s)",
      width, height, depth
    );
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initDimensions (double width, double height, double depth)
  {
    this.width  = width;
    this.height = height;
    this.depth  = depth;
  }
}