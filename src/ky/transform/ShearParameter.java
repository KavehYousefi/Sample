// 24-12-2013

package ky.transform;

public enum ShearParameter
{
  SHEARING_XY (ShearSide.TOP_BOTTOM, ShearAxis.X_AXIS),
  SHEARING_XZ (ShearSide.FRONT_BACK, ShearAxis.X_AXIS),
  SHEARING_YX (ShearSide.LEFT_RIGHT, ShearAxis.Y_AXIS),
  SHEARING_YZ (ShearSide.FRONT_BACK, ShearAxis.Y_AXIS),
  SHEARING_ZX (ShearSide.LEFT_RIGHT, ShearAxis.Z_AXIS),
  SHEARING_ZY (ShearSide.TOP_BOTTOM, ShearAxis.Z_AXIS);
  
  
  private ShearSide side;
  private ShearAxis axis;
  
  
  /**
   * <div class="introConstructor">
   *   Defines a shear parameter from a plane (side) to shear and the
   *   axis to shear along.
   * </div>
   * 
   * @param side  The plane to be sheared.
   * @param axis  The axis to shear the <code>side</code> along.
   */
  private ShearParameter (ShearSide side, ShearAxis axis)
  {
    this.side = side;
    this.axis = axis;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the affected plane.
   * </div>
   * 
   * @return  The affected plane.
   */
  public ShearSide getSide ()
  {
    return side;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the affected axis.
   * </div>
   * 
   * @return  The affected axis.
   */
  public ShearAxis getAxis ()
  {
    return axis;
  }
  
  
  /**
   * <div class="introMethod">
   *   Finds and returns the matching <code>ShearParameter</code> for
   *   a given shear side (plane) and axis.
   * </div>
   * 
   * @return  The corresponding shear parameter for the given
   *          shear <code>side</code> and <code>axis</code>.
   */
  public static ShearParameter getShearParameterFromSideAndAxis
  (
    ShearSide side,
    ShearAxis axis
  )
  {
    for (ShearParameter parameter : ShearParameter.values ())
    {
      ShearSide currentSide = parameter.getSide ();
      ShearAxis currentAxis = parameter.getAxis ();
      boolean   isMatching  = (currentSide.equals (side) &&
                               currentAxis.equals (axis));
      
      if (isMatching)
      {
        return parameter;
      }
    }
    
    return null;
  }
}