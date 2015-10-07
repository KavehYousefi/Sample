package ky.angle;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <div class="introClass">
 *   An <code>AngleParser</code> interprets a string a an angle, that
 *   is, an angle size and angle unit combination.
 * </div>
 * 
 * <div>
 *   Following source were helpful:
 *   <ul>
 *     <li>
 *       <a href="http://stackoverflow.com/questions/3512471/non-capturing-group">
 *         http://stackoverflow.com/questions/3512471/non-capturing-group
 *       </a>
 *       <br />
 *       Topic: Using "?:" for non-capturing groups in regular
 *              expressions under Java.
 *       <br />
 *       Accessed: 15.03.2015
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
 *       AngleParser.java
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
 *       15.03.2015
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
public class AngleParser
{
  // Maps String -> AngleUnit.
  private Map<String, AngleUnit> stringToUnitMap;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an angle string parser with default unit recognition.
   * </div>
   */
  public AngleParser ()
  {
    this.stringToUnitMap = createDefaultStringUnitMap ();
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the string-to-unit map.
   * </div>
   * 
   * @return  The map containing the
   *          <code>String</code>-<code>AngleUnit</code> associations.
   */
  public Map<String, AngleUnit> getStringToUnitMap ()
  {
    return stringToUnitMap;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the string-to-unit map, which allows recognizing angle units
   *   by string tokens.
   * </div>
   * 
   * @param stringToUnitMap  The map containing the new
   *                         <code>String</code>-<code>AngleUnit</code>
   *                         associations.
   */
  public void setStringToUnitMap (Map<String, AngleUnit> stringToUnitMap)
  {
    this.stringToUnitMap = stringToUnitMap;
  }
  
  
  /**
   * <div class="introMethod">
   *   Parses a string and tries to interpret it as an angle.
   * </div>
   * 
   * @param inputString  The non-<code>null</code> string to parse.
   * 
   * @return             An angle interpreted from the input string or
   *                     <code>null</code>, if it was invalid.
   * 
   * @throws NullPointerException  If <code>inputString</code> equals
   *                               <code>null</code>.
   */
  public Angle parseAngle (String inputString)
  {
    Angle   angle         = null;
    String  patternString = null;
    Pattern pattern       = null;
    Matcher matcher       = null;
    
    patternString = "^([+-]?\\d*(?:\\.\\d+)?)\\s*(.*)?$";
    pattern       = Pattern.compile (patternString);
    matcher       = pattern.matcher (inputString);
    
    if (matcher.find ())
    {
      double    angleSize  = 0.0;
      AngleUnit angleUnit  = null;
      String    sizeString = null;
      String    unitString = null;
      
      sizeString = matcher.group       (1);
      unitString = matcher.group       (2);
      angleSize  = Double.parseDouble  (sizeString);
      angleUnit  = stringToUnitMap.get (unitString);
      angle      = new Angle           (angleUnit, angleSize);
    }
    else
    {
      angle = null;
    }
    
    return angle;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static Map<String, AngleUnit> createDefaultStringUnitMap ()
  {
    Map<String, AngleUnit> angleUnitMap = null;
    
    angleUnitMap = new HashMap<String, AngleUnit> ();
    
    angleUnitMap.put ("°",        AngleUnit.DEGREE);
    angleUnitMap.put ("deg",      AngleUnit.DEGREE);
    angleUnitMap.put ("degree",   AngleUnit.DEGREE);
    angleUnitMap.put ("degrees",  AngleUnit.DEGREE);
    angleUnitMap.put ("",         AngleUnit.RADIAN);
    angleUnitMap.put ("c",        AngleUnit.RADIAN); // Actually superscript "c".
    angleUnitMap.put ("rad",      AngleUnit.RADIAN);
    angleUnitMap.put ("g",        AngleUnit.GON);
    angleUnitMap.put ("gon",      AngleUnit.GON);
    angleUnitMap.put ("grad",     AngleUnit.GON);
    angleUnitMap.put ("turn",     AngleUnit.TURN);
    angleUnitMap.put ("h",        AngleUnit.HOUR_ANGLE);  // Actually superscript "h".
    angleUnitMap.put ("hour",     AngleUnit.HOUR_ANGLE);
    angleUnitMap.put ("quadrant", AngleUnit.QUADRANT);
    angleUnitMap.put ("quad",     AngleUnit.QUADRANT);
    
    return angleUnitMap;
  }
}