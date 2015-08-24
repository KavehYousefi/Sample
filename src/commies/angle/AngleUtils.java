// 26.01.2013
// 03.03.2013: Aufzaehlungstyp "Winkelmass" in eigene Datei ausgelagert.

package commies.angle;

import safercode.CheckingUtils;


public class AngleUtils
{
  /**
   * <div class="introConstructor">
   *   A private constructor without functionality.
   * </div>
   */
  private AngleUtils ()
  {
  }
  
  
  /**
   * <div class="introMethod">
   *   Converts an angle size from one unit to another.
   * </div>
   * 
   * @param sourceAngleSize  The angle size to be converted. It is
   *                         interpreted as being in the angle unit
   *                         <code>sourceAngleUnit</code>.
   * @param sourceAngleUnit  The angle unit of the
   *                         <code>sourceAngleSize</code>.
   * @param destinationUnit  The unit to convert the
   *                         <code>sourceAngleSize</code> to.
   * 
   * @return                 The new angle size, obtained by converting
   *                         <code>sourceAngleSize</code> from
   *                         <code>sourceAngleUnit</code> to
   *                         <code>destinationUnit</code>.
   * 
   * @throws NullPointerException  If <code>sourceAngleUnit</code> or
   *                               <code>destinationUnit</code> are
   *                               <code>null</code>.
   */
  public static double convert
  (
    double    sourceAngleSize,
    AngleUnit sourceAngleUnit,
    AngleUnit destinationUnit
  )
  {
    double angleInDestUnit = 0.0;
    // "sourceAngleSize" im Gradmass.
    double angleInDegrees  = 0.0;
    
    // Wandle zunaechst "sourceAngleSize" in Altgrad um.
    angleInDegrees  = sourceAngleUnit.getAsDegrees (sourceAngleSize);
    // Wandle nun den Altgrad-Winkel in das Zielsystem um.
    angleInDestUnit = destinationUnit.setFromDegrees (angleInDegrees);
    
    return angleInDestUnit;
  }
  
  
  //+/ 06.04.2013
  /**
   * <div class="introMethod">
   *   Determines an angle's type.
   * </div>
   * 
   * @param angle  The angle type to inspect. It may not be
   *               <code>null</code>.
   * 
   * @return       The angle type of the given <code>angle</code>.
   * 
   * @throws NullPointerException  If <code>angle</code> is
   *                               <code>null</code>.
   */
  public static AngleType getAngleType (Angle angle)
  {
    CheckingUtils.checkForNull (angle, "Angle is null.");
    
    AngleType angleType      = null;
    double    angleInDegrees = 0.0;
    
    angleInDegrees = angle.getSizeInDegrees ();
    // Clamp angle in degrees into the range [0.0, 360.0).
    angleInDegrees = (angleInDegrees % 360.0);
    
    // Convert negative angle to positive.
    if (angleInDegrees < 0.0)
    {
      angleInDegrees = (360.0 + angleInDegrees);
    }
    
    boolean isAcute    = ((angleInDegrees >   0.0)  &&
                          (angleInDegrees <   90.0));
    boolean isRight    = ( angleInDegrees ==  90.0);
    boolean isObtuse   = ((angleInDegrees >   90.0) &&
                          (angleInDegrees <  180.0));
    boolean isStraight = ( angleInDegrees == 180.0);
    boolean isReflex   = ((angleInDegrees >  180.0) &&
                          (angleInDegrees <  360.0));
    boolean isFull     = ((angleInDegrees == 360.0) ||
                          (angleInDegrees ==   0.0));
    
    if (isAcute)
    {
      angleType = AngleType.ACUTE_ANGLE;
    }
    else if (isRight)
    {
      angleType = AngleType.RIGHT_ANGLE;
    }
    else if (isObtuse)
    {
      angleType = AngleType.OBTUSE_ANGLE;
    }
    else if (isStraight)
    {
      angleType = AngleType.STRAIGHT_ANGLE;
    }
    else if (isReflex)
    {
      angleType = AngleType.REFLEX_ANGLE;
    }
    else if (isFull)
    {
      angleType = AngleType.FULL_ANGLE;
    }
    else
    {
      angleType = null;
    }
    
    return angleType;
  }
}