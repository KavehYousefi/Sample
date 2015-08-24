package commies.angle;


/**
 * <div class="introClass">
 *   This enumeration defines the various angle types.
 * </div>
 * 
 * <div>
 *   Following sources were consulted for implementing this class:
 *   <ul>
 *     <li>
 *       <a href="http://en.wikipedia.org/wiki/Angle#Types_of_angles">
 *         http://en.wikipedia.org/wiki/Angle#Types_of_angles
 *       </a>
 *       <br />
 *       Accessed: 06.04.2013
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
 *       AngleType.java
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
 *       06.04.2013
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
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum AngleType
{
  ACUTE_ANGLE    ("acute angle"   ),   // [0°; 90°).
  RIGHT_ANGLE    ("right angle"   ),   // = 90°.
  OBTUSE_ANGLE   ("obtuse angle"  ),   // (90°; 180°).
  STRAIGHT_ANGLE ("straight angle"),   // = 180°.
  REFLEX_ANGLE   ("reflex angle"  ),   // (180°; 360°).
  FULL_ANGLE     ("full angle"    );   // = 360°.
  
  
  private String typeName;
  
  
  /**
   * <div class="introConstructor">
   *   A new angle type is created.
   * </div>
   * 
   * @param typeName  The optional name for this type of angle.
   */
  private AngleType (String typeName)
  {
    this.typeName = typeName;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the angle type's name.
   * </div>
   * 
   * @return  The type name.
   */
  public String getName ()
  {
    return typeName;
  }
}