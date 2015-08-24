package ky.math;


/**
 * <div class="introClass">
 *   The <code>ComputerGraphicsMath</code> class provides solutions to
 *   mathematical problems commonly encountered in computer graphics.
 * </div>
 * 
 * <div>
 *   Following sources have been used:
 *   <ul>
 *     <li>
 *       <a href="http://en.wikipedia.org/wiki/Wrapping_%28graphics%29">
 *         http://en.wikipedia.org/wiki/Wrapping_%28graphics%29
 *       </a>
 *       <br />
 *       Topic: Wrapping values (see method
 *              <code>simpleWrap (...)</code>)..
 *       <br />
 *       Accessed: 18.03.2015
 *     </li>
 *     <li>
 *       <a href="http://stackoverflow.com/questions/707370/clean-efficient-algorithm-for-wrapping-integers-in-c">
 *         http://stackoverflow.com/questions/707370/clean-efficient-algorithm-for-wrapping-integers-in-c
 *       </a>
 *       <br />
 *       Direct answer link: <a href="http://stackoverflow.com/a/707589">http://stackoverflow.com/a/707589</a>
 *       <br />
 *       Topic: Wrapping values (see method
 *              <code>wrapByMartinStettner (...)</code>).
 *       <br />
 *       Accessed: 18.03.2015
 *     </li>
 *     <li>
 *       <a href="http://stackoverflow.com/questions/707370/clean-efficient-algorithm-for-wrapping-integers-in-c">
 *         http://stackoverflow.com/questions/707370/clean-efficient-algorithm-for-wrapping-integers-in-c
 *       </a>
 *       <br />
 *       Direct answer link: <a href="http://stackoverflow.com/a/707426">http://stackoverflow.com/a/707426</a>
 *       <br />
 *       Topic: Wrapping values (see method
 *              <code>wrapByMartinCharlesBailey (...)</code>).
 *       <br />
 *       Accessed: 18.03.2015
 *     </li>
 *     <li>
 *       <a href="http://en.wikipedia.org/wiki/Clamping_%28graphics%29">
 *         http://en.wikipedia.org/wiki/Clamping_%28graphics%29
 *       </a>
 *       <br />
 *       Topic: Clamping values.
 *       <br />
 *       Accessed: 18.03.2015
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
 *       ComputerGraphicsMath.java
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
 *       18.03.2015
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
 *     <td>18.03.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class ComputerGraphicsMath
{
  /**
   * <div class="introConstructor">
   *   A non-functional constructor.
   * </div>
   */
  private ComputerGraphicsMath ()
  {
  }
  
  
  public static double clamp (double value, double minimum, double maximum)
  {
    double clampedValue = 0.0;
    
    if (value > maximum)
    {
      clampedValue = maximum;
    }
    else if (value < minimum)
    {
      clampedValue = minimum;
    }
    else
    {
      clampedValue = value;
    }
    
    return clampedValue;
  }
  
  public static int clamp (int value, int minimum, int maximum)
  {
    int clampedValue = 0;
    
    if (value < minimum)
    {
      clampedValue = minimum;
    }
    else if (value > maximum)
    {
      clampedValue = maximum;
    }
    else
    {
      clampedValue = value;
    }
    
    return clampedValue;
  }
  
  public static double simpleWrap (double value, double minimum, double maximum)
  {
    double wrappedValue                 = 0.0;
    double differenceOfValueAndMinimum  = (value   - minimum);
    double range                        = (maximum - minimum);
    
    wrappedValue = (value - (Math.floor (differenceOfValueAndMinimum / range)) * range);
    
    return wrappedValue;
  }
  
  public static double wrapByMartinStettner
  (
    double value,
    double lowerBound,
    double upperBound
  )
  {
    double wrappedValue = 0.0;
    double range        = (upperBound - lowerBound + 1.0);
    
    wrappedValue = ((value - lowerBound) % range);
    
    if (wrappedValue < 0.0)
    {
      wrappedValue = upperBound + 1.0 + wrappedValue;
    }
    else
    {
      wrappedValue = lowerBound + wrappedValue;
    }
    
    return wrappedValue;
  }
  
  public static double wrapByCharlesBailey
  (
    double value,
    double lowerBound,
    double upperBound
  )
  {
    double wrappedValue = value;
    double range        = (upperBound - lowerBound + 1.0);
    
    if (value < lowerBound)
    {
      wrappedValue = value
                     + range * ((lowerBound - value) / range + 1.0);
    }
    
    wrappedValue = lowerBound + (wrappedValue - lowerBound) % range;
    
    return wrappedValue;
  }
}