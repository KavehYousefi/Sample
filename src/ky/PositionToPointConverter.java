// 21.06.2015

package ky;

import safercode.CheckingUtils;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;


public class PositionToPointConverter
{
  // If parameter of type List<Position> equals null, ...
  public static enum NullInputPolicy
  {
    RETURN_NULL,       // ... return a null value.
    THROW_EXCEPTION;   // ... throw a NullPointerException.
    
    private NullInputPolicy ()
    {
    }
  }
  
  
  // If one list element in parameter of type List<Position> equals null, ...
  public static enum NullElementPolicy
  {
    REPLACE_BY_DEFAULT,   // ... place a replacement value in the Point3d[] array to return.
    THROW_EXCEPTION;      // ... throw a NullPointerException.
    
    private NullElementPolicy ()
    {
    }
  };
  
  
  private NullInputPolicy   nullInputPolicy;
  private NullElementPolicy nullElementPolicy;
  private Point3d           nullElementReplacement;
  
  
  public PositionToPointConverter
  (
    NullInputPolicy   nullInputPolicy,
    NullElementPolicy nullElementPolicy
  )
  {
    this.nullInputPolicy        = nullInputPolicy;
    this.nullElementPolicy      = nullElementPolicy;
    this.nullElementReplacement = null;
  }
  
  public PositionToPointConverter ()
  {
    this.nullInputPolicy        = NullInputPolicy.THROW_EXCEPTION;
    this.nullElementPolicy      = NullElementPolicy.THROW_EXCEPTION;
    this.nullElementReplacement = null;
  }
  
  
  public List<Point3d> convertPositionListToPoint3dList
  (
    List<Position> positionList
  )
  {
    if (nullInputPolicy.equals (NullInputPolicy.THROW_EXCEPTION))
    {
      CheckingUtils.checkForNull (positionList, "The positionList is null.");
    }
    else
    {
      if (positionList == null)
      {
        return null;
      }
    }
    
    List<Point3d> point3dList = new ArrayList<Point3d> ();
    
    for (Position currentPosition : positionList)
    {
      Point3d positionAsPoint3d = null;
      
      if (currentPosition != null)
      {
        positionAsPoint3d = currentPosition.getAsPoint3d ();
      }
      else
      {
        if (nullElementPolicy.equals (NullElementPolicy.REPLACE_BY_DEFAULT))
        {
          positionAsPoint3d = nullElementReplacement;
        }
        else
        {
          throw new NullPointerException ("Position is null.");
        }
      }
      
      point3dList.add (positionAsPoint3d);
    }
    
    return point3dList;
  }
  
  /**
   * <div class="introMethod">
   *   Creates an array of <code>Point3d</code> objects from a list of
   *   <code>Position</code>s.
   * </div>
   * 
   * @param positionList  The positions to convert to a
   *                   <code>Point3d[]</code> array.
   * 
   * @return           An array of <code>Point3d</code>s from  the given
   *                   <code>pointList</code>.
   */
  public Point3d[] convertPositionListToPoint3dArray
  (
    List<Position> positionList
  )
  {
    Point3d[]     point3dArray = new Point3d[0];
    List<Point3d> point3dList  = null;
    
    point3dList  = convertPositionListToPoint3dList (positionList);
    
    if (point3dList != null)
    {
      point3dArray = point3dList.toArray (point3dArray);
    }
    else
    {
      point3dArray = null;
    }
    
    return point3dArray;
  }
}
