package ky.transform;

import java.util.ArrayList;
import java.util.List;

import safercode.CheckingUtils;


public class RotationOrdering
{
  private List<RotationAxis> axes;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an empty rotation ordering.
   * </div>
   */
  public RotationOrdering ()
  {
    axes = new ArrayList<RotationAxis> ();
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation ordering and initializes it with the ordering
   *   data from a given array.
   * </div>
   * 
   * @param axesArray  An optional array of rotation axes to copy.
   */
  public RotationOrdering (RotationAxis[] axesArray)
  {
    this.axes = new ArrayList<RotationAxis> ();
    
    if (axesArray != null)
    {
      for (RotationAxis axis : axesArray)
      {
        this.axes.add (axis);
      }
    }
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a rotation ordering and initializes it with the ordering
   *   data from a given list.
   * </div>
   * 
   * @param axesList  An optional list of rotation axes to copy.
   */
  public RotationOrdering (List<RotationAxis> axesList)
  {
    this.axes = new ArrayList<RotationAxis> ();
    
    if (axesList != null)
    {
      this.axes.addAll (axesList);
    }
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns a list of the rotation axes.
   * </div>
   * 
   * @return  A list of the rotation axes.
   */
  public List<RotationAxis> getAxes ()
  {
    return axes;
  }
  
  /**
   * <div class="introMethod">
   *   Adds a rotation axes.
   * </div>
   * 
   * @param rotationAxis  The rotation axis to add.
   *                      The <code>null</code> value is not permitted.
   * 
   * @throws NullPointerException  If the <code>rotationAxis</code>
   *                               equals <code>null</code>.
   */
  public void addRotationAxis (RotationAxis rotationAxis)
  {
    CheckingUtils.checkForNull (rotationAxis,
                                "No rotation axis given.");
    
    axes.add (rotationAxis);
  }
}