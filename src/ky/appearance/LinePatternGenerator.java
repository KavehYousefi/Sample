/**
 * <div class="einleitungKlasse">
 *   <code>LinePatternGenerator</code> vereinfacht die Erzeugung von
 *   Strichmustern (<i>pattern masks</i>) f&uuml;r die Klasse
 *   <code>LineAttributes</code>.
 * </div>
 * 
 * <table id="infos">
 *   <tr>
 *     <td>
 *       Datei
 *     </td>
 *     <td>
 *       LinePatternGenerator.java
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Verfasser
 *     </td>
 *     <td>
 *       Kaveh Yousefi
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Datum
 *     </td>
 *     <td>
 *       27.02.2013
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Fassung
 *     </td>
 *     <td>
 *       1.1
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       &Auml;nderungsverlauf
 *     </td>
 *     <td>
 *       <ul>
 *         <li>
 *           Fassung 1.0, 19.02.2013, Kaveh Yousefi:
 *           <ul>
 *             <li>
 *               Die Klasse <code>LinePatternGenerator</code> wurde
 *               angelegt.
 *             </li>
 *           </ul>
 *         </li>
 *         <li>
 *           Fassung 1.1, 27.02.2013, Kaveh Yousefi:
 *           <ul>
 *             <li>
 *               Zwei neue Konstruktoren wurden eingef&uuml;hrt:
 *               <ul>
 *                 <li>
 *                   <code>public LinePatternGenerator
 *                   (boolean[] bitArray)</code>
 *                 </li>
 *                 <li>
 *                   <code>public LinePatternGenerator
 *                   (String patternZeiket)</code>.
 *                 </li>
 *               </ul>
 *             </li>
 *           </ul>
 *         </li>
 *       </ul>
 *     </td>
 *   </tr>
 * </table>
 */


package ky.appearance;

import java.util.BitSet;


/**
 * <div class="introClass">
 *   <code>LinePatternGenerator</code> is a utility class for creating
 *   <i>pattern masks</i>, which are compatible with <i>Java 3D</i>'s
 *   <code>LineAttributes</code> class.
 * </div>
 */
public class LinePatternGenerator
{
  // A string pattern always uses 16 bits.
  public static final int  NUMBER_OF_BITS       = 16;
  public static final int  MAX_BIT_INDEX        = (NUMBER_OF_BITS - 1);
  // Default character for an active (on) bit in a string pattern.
  public static final char DEFAULT_ON_CHARACTER = '-';
  public static final char DEFAULT_ON_BIT_CHAR  = '1';
  
  private BitSet bitSet;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a <code>LinePatternGenerator</code> with all of its
   *   bits either turned on or off.
   * </div>
   * 
   * @param areBitsOn  A boolean value to turn all pixels on or off:
   *                   <table>
   *                     <tr>
   *                       <th><code>areBitsOn</code> value</th>
   *                       <th>Interpretation</th>
   *                     </tr>
   *                     <tr>
   *                       <td><code>true</code></td>
   *                       <td>All pixels are turned on.</td>
   *                     </tr>
   *                     <tr>
   *                       <td><code>false</code></td>
   *                       <td>All pixels are turned off.</td>
   *                     </tr>
   *                   </table>
   */
  public LinePatternGenerator (boolean areBitsOn)
  {
    bitSet = new BitSet (NUMBER_OF_BITS);
    
    setPixels (0, MAX_BIT_INDEX, areBitsOn);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates <code>LinePatternGenerator</code> based on a 16-bit
   *   pattern, represented by an integer value.
   * </div>
   * 
   * @param linePattern  An integer value whose lowest 16 bits define
   *                     the line pattern.
   */
  public LinePatternGenerator (int linePattern)
  {
    bitSet = new BitSet (NUMBER_OF_BITS);
    
    String patternBinaryString = Integer.toBinaryString (linePattern);
    
    setPatternMaskFromBinaryString (patternBinaryString);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates line pattern generator based on a binary string of
   *   zeroes and ones.
   * </div>
   * 
   * <div>
   *   A maximum of 16 digits will be interpreted, the rest, if any,
   *   will be ignored.
   * </div>
   * 
   * @param binaryPatternString  The binary pattern as string of
   *                             zeroes and ones.
   * 
   * @throws IllegalArgumentException  If the
   *                                   <code>binaryPatternString</code>
   *                                   contains invalid characters,
   *                                   that is, everything except for
   *                                   "0" and "1".
   *                                   
   */
  public LinePatternGenerator (String binaryPatternString)
  {
    bitSet = new BitSet (NUMBER_OF_BITS);
    
    setPatternMaskFromBinaryString (binaryPatternString);
  }
  
  /**
   * <div class="introConstructor">
   *   Generates a <code>LinePatternGenerator</code> based on an array
   *   of bits.
   * </div>
   * 
   * <div>
   *   If given less than 16 bits, the remaining spaces will be filled
   *   up with bits set to <code>false</code>. Given more than 16 bits
   *   will result in ignoring the surplus elements.
   * </div>
   * 
   * @param bitArray  An optional array of bits to use as pattern mask.
   */
  public LinePatternGenerator (boolean[] bitArray)
  {
    bitSet = new BitSet (NUMBER_OF_BITS);
    
    if (bitArray != null)
    {
      int bitArrayLength     = bitArray.length;
      int numberOfBitsToCopy = Math.min (bitArrayLength, NUMBER_OF_BITS);
      
      for (int bitIndex = 0; bitIndex < numberOfBitsToCopy; bitIndex++)
      {
        boolean isOn = bitArray[bitIndex];
        
        setPixel (bitIndex, isOn);
      }
    }
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a <code>LinePatternGenerator</code> with all of its
   *   bits turned off.
   * </div>
   */
  public LinePatternGenerator ()
  {
    bitSet = new BitSet (NUMBER_OF_BITS);
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns an integer value representing the
   *   <i>line pattern mask</i>, which is compatible with the
   *   <code>javax.media.j3d.LineAttributes.setPatternMask
   *   (int mask)</code> method.
   * </div>
   * 
   * @return  The <i>line pattern mask</i> as integer.
   * 
   * @see     javax.media.j3d.LineAttributes
   */
  public int getPatternMask ()
  {
    int patternMask  = 0;
    
    // Iterate the bits from "back" to "front" (right to left).
    for (int bitIndex = MAX_BIT_INDEX; bitIndex >= 0; bitIndex--)
    {
      boolean isBitOn = bitSet.get (bitIndex);
      
      if (isBitOn)
      {
        int potenz = powerOfInt (2, bitIndex);
        
        patternMask = (patternMask + potenz);
      }
    }
    
    return patternMask;
  }
  
  
  /**
   * <div class="introMethod">
   *   Sets the whole line pattern from a string containing the binary
   *   data as bits, which are read <strong>from back to front</strong>.
   * </div>
   * 
   * <div>
   *   The pattern string should be comprised only of zeros and ones,
   *   but actually only the ones are checked.
   * </div>
   * 
   * <div>
   *   Example:
   *   <p>
   *     <code><pre>11011000100110111</pre></code>
   *   </p>
   * </div>
   * 
   * @param patternString  The string acting as line pattern.
   */
  public void setPatternMaskFromBinaryString (String patternString)
  {
    int patternLength = patternString.length ();
    int lastIndex     = (patternLength - 1);
    int pixelIndex    = MAX_BIT_INDEX;
    
    // A maximum of 16 bits of the binary number should be iterated.
    lastIndex = Math.min (lastIndex, MAX_BIT_INDEX);
    
    // Iterate the binary number's digits "back to front".
    for (int bitIndex = lastIndex; bitIndex >= 0; bitIndex--)
    {
      // The current position's bit.
      char    currentBit = patternString.charAt (bitIndex);
      // Is the current bit equal to the "active" ("on") character?
      boolean isBitOn    = (currentBit == DEFAULT_ON_BIT_CHAR);
      
      setPixel (pixelIndex, isBitOn);
      pixelIndex--;
    }
  }
  
  // GETESTET
  // 28-12-2013
  /**
   * <div class="introMethod">
   *   Sets the whole line pattern from a string acting as a "graphical"
   *   representation, where "-" replaces an active (on) bit.
   * </div>
   * 
   * <div>
   *   Example:
   *   <p>
   *     <code><pre>-- --   -  -- ---</pre></code>
   *     <code><pre>11011000100110111</pre></code>
   *   </p>
   * </div>
   * 
   * @param patternString  The string acting as line pattern.
   */
  public void setPatternMaskFromGraphicString (String patternString)
  {
    setPatternMaskFromGraphicString (patternString, DEFAULT_ON_CHARACTER);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the whole line pattern from a string acting as a "graphical"
   *   representation.
   * </div>
   * 
   * @param patternString  The string acting as line pattern.
   * @param activeChar     A character which represents an active bit
   *                       in the <code>patternString</code>.
   */
  public void setPatternMaskFromGraphicString
  (
    String patternString,
    char   activeChar
  )
  {
    if (patternString != null)
    {
      int zeiketLaenge  = patternString.length ();
      // Maximal 16 Stellen der "grafikZeiket" verwenden.
      int anzahlStellen = Math.min (NUMBER_OF_BITS, zeiketLaenge);
      
      for (int pixelNr = 0; pixelNr < anzahlStellen; pixelNr++)
      {
        char    zeichenAnIndex = patternString.charAt (pixelNr);
        boolean istAn          = (zeichenAnIndex == activeChar);
        
        setPixel (pixelNr, istAn);
      }
    }
  }
  
  
  /**
   * <div class="introMethod">
   *   Checks whether a given pixel is activated.
   * </div>
   * 
   * @param pixelIndex  The index of the pixel to check.
   * 
   * @return            A boolean value to determine, if the pixel is
   *                    on or off:
   *                    <table>
   *                      <tr>
   *                        <th>Return value</th>
   *                        <th>Interpretation</th>
   *                      </tr>
   *                      <tr>
   *                        <td><code>true</code></td>
   *                        <td>
   *                          The pixel at index <code>pixelIndex</code>
   *                          is activated (on).
   *                        </td>
   *                      </tr>
   *                      <tr>
   *                        <td><code>false</code></td>
   *                        <td>
   *                          The pixel at index <code>pixelIndex</code>
   *                          is deactivated (off).
   *                        </td>
   *                      </tr>
   *                    </table>
   * 
   * @throws IllegalArgumentException  When given an invalid
   *                                   <code>pixelIndex</code>.
   */
  public boolean isOn (int pixelIndex)
  {
    boolean isOn = bitSet.get (pixelIndex);
    
    return isOn;
  }
  
  /**
   * <div class="einleitungMethode">
   *   Der Wert einer zusammenh&auml;ngenden Folge von Pixeln wird auf
   *   den angegebenen Wert gesetzt.
   * </div>
   * 
   * @param pixelNr  Der Index des zu setzenden Pixels.
   * @param istAn    Der Wert, auf jenen der Pixel an dem Index
   *                 <code>pixelNr</code> gesetzt werden soll:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Pixels werden
   *                     eingeschaltet.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Pixels werden
   *                     ausgeschaltet.
   *                   </li>
   *                 </ul>
   * 
   * @throws IllegalArgumentException  Bei Angabe eines ung&uuml;ltigen
   *                                   Index' f&uuml;r
   *                                   <code>pixelNr</code>.
   */
  public void setPixel (int pixelNr, boolean istAn)
  {
    bitSet.set (pixelNr, istAn);
  }
  
  /**
   * <div class="einleitungMethode">
   *   Der Wert einer zusammenh&auml;ngenden Folge von Pixeln wird auf
   *   den angegebenen Wert gesetzt.
   * </div>
   * 
   * @param abPixelNr   Der inklusive Index des ersten Pixels.
   * @param bisPixelNr  Der exklusive Index des letzten Pixels.
   * @param istAn       Der Wert, auf jenen die Pixels zwischen
   *                    [<code>abPixelNr</code>;
   *                    <code>bisPixelNr</code>) gesetzt werden sollen:
   *                    <ul>
   *                      <li>
   *                        <code>true</code>: Die Pixels werden
   *                        eingeschaltet.
   *                      </li>
   *                      <li>
   *                        <code>false</code>: Die Pixels werden
   *                        ausgeschaltet.
   *                      </li>
   *                    </ul>
   * 
   * @throws IllegalArgumentException    Falls <code>abPixelNr</code>
   *                                     oder <code>bisPixelNr</code>
   *                                     einen ung&uuml;ltigen Index
   *                                     ansprechen.
   * @throws AusnahmeVertauschteGrenzen  Falls <code>abPixelNr</code>
   *                                     gr&ouml;&szlig;er als
   *                                     <code>bisPixelNr</code> ist.
   */
  public void setPixels (int abPixelNr, int bisPixelNr, boolean istAn)
  {
    bitSet.set (abPixelNr, bisPixelNr, istAn);
  }
  
  /**
   * <div class="introMethod">
   *   Sets all pixels of the line pattern to a given state (on or off).
   * </div>
   * 
   * @param isOn  A boolean value to describe the new pixel states.
   *              <table>
   *                <tr>
   *                  <th><code>isOn</code> value</th>
   *                  <th>Interpretation</th>
   *                </tr>
   *                <tr>
   *                  <td><code>true</code></td>
   *                  <td>All pixels are to be activated (on).</td>
   *                </tr>
   *                <tr>
   *                  <td><code>false</code></td>
   *                  <td>All pixels are to be deactivated (off).</td>
   *                </tr>
   *              </table>
   */
  public void setAllPixels (boolean isOn)
  {
    bitSet.set (0, NUMBER_OF_BITS, isOn);
  }
  
  public void invert (int pixelNr)
  {
    boolean alterWert = bitSet.get (pixelNr);
    boolean neuerWert = (! alterWert);
    
    bitSet.set (pixelNr, neuerWert);
  }
  
  public void invert (int fromIndex, int toIndex)
  {
    for (int pixelNr = fromIndex; pixelNr <= toIndex; pixelNr++)
    {
      invert (pixelNr);
    }
  }
  
  /**
   * <div class="introMethod">
   *   Inverts each bit's state.
   * </div>
   */
  public void invertAll ()
  {
    for (int bitIndex = 0; bitIndex < NUMBER_OF_BITS; bitIndex++)
    {
      bitSet.flip (bitIndex);
    }
  }
  
  /**
   * <div class="introMethod">
   *   Returns the number of bits in the pattern mask.
   * </div>
   * 
   * <div>
   *   The bit count always equals sixteen.
   * </div>
   * 
   * @return  The number of bits in the pattern mask: sixteen.
   */
  public int getLength ()
  {
    return NUMBER_OF_BITS;
  }
  
  
  /**
   * <div class="introMethod">
   *   Calculates the value of an integer base raised to the power of
   *   an integer exponent.
   * </div>
   * 
   * @param basis     The base.
   * @param exponent  The exponent.
   * @return          The result of
   *                  <code>base<sup>exponent</sub></code>.
   */
  public static int powerOfInt (int basis, int exponent)
  {
    int    potenzWert   = 0;
    double potenzDouble = Math.pow (basis, exponent);
    
    potenzWert = new Double (potenzDouble).intValue ();
    
    return potenzWert;
  }
}