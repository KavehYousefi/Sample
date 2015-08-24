// 13-01-2014

package ky.math;


public class IntervallWerkz
{
  /**
   * <div class="introConstructor">
   *   A constructor without function.
   * </div>
   */
  private IntervallWerkz ()
  {
  }
  
  
  // 20.05.2012
  /**
   * <div class="einleitungMethode">
   *   Der Anteil an einem Wertebereich wird als Zahl geliefert.
   * </div>
   * 
   * @param min     Die inklusive Untergrenze des Intervalls.
   * @param max     Die inklusive Obergrenze  des Intervalls.
   * @param anteil  Der an dem Intervall
   *                [<code>min</code> ; <code>max</code>].
   * 
   * @return  Jene dem <code>anteil</code> an dem Intervall
   *          [<code>min</code> ; <code>max</code>] entsprechende
   *          Zahl.
   */
  public static double getRangeValueFromFraction
  (
    double min,
    double max,
    double anteil
  )
  {
    double wert       = 0.0;
    // Die Spannweite des Intervalls.
    double spannweite = (max - min);
    double bruchteil  = (spannweite * anteil);
    
    wert = (min + bruchteil);
    
    return wert;
  }
  
  
  /**
   * <div class="einleitungMethode">
   *   Diese Methode ermittelt den Anteil eines Wertes an einem
   *   Wertebereich.
   * </div>
   * 
   * <div>
   *   <code>minWert</code> ist der niedrigste m&ouml;gliche Wert, welcher
   *   angenommen werden kann.
   * </div>
   * <div>
   *   <code>hoeWert</code> stellt den h&ouml;chstm&ouml;glichen Wert dar.
   * </div>
   * <div>
   *   <code>wert</code> ist der Wert, dessen Anteil am Ganzen - genauer:
   *   an dem durch <code>minWert</code> und <code>hoeWert</code>
   *   beschriebenen Intervall - errechnet werden soll.
   * </div>
   * <div>
   *   Ergebnis dieser Methode ist eine Gleitkommazahl, welche dem
   *   prozentualen Anteil des Wertes <code>wert</code> an der mittels
   *   den  Grenzwerten <code>minWert</code> und <code>hoeWert</code>
   *   geschaffenen Gr&ouml;&szlig;e entspricht.
   * </div>
   * <div>
   *   Beispiel: Der Wertebereich sei [20; 30], folglich ist
   *   <code>minWert</code> = 20, <code>hoeWert</code> = 30. Der
   *   angegebene Wert ist gleich 22. Folglich ist der Anteil des Wertes
   *   gleich 0,2.
   * </div>
   * 
   * <div>
   *   Quelle:
   *   <ul>
   *     <li>
   *       "http://www.designscripting.com/2011/03/maths-and-actionscript-3-code-using-as3-with-math/"
   *       <br />
   *       Stand: ?
   *     </li>
   *   </ul>
   * </div>
   * 
   * @param minWert  Der m&ouml;gliche Mindestwert. Versteht sich als
   *                 einschlie&szlig;lich.
   * @param hoeWert  Der m&ouml;gliche H&ouml;chstwert. Versteht sich
   *                 als einschlie&szlig;lich.
   * @param wert     Der vorliegende Wert in dem Bereich zwischen
   *                 <code>minWert</code> und <code>hoeWert</code>.
   * 
   * @return         Den Anteil von <code>wert</code> an der durch
   *                 <code>minWert</code> und <code>hoeWert</code>
   *                 gebildeten Gr&ouml;&szlig;e.
   * 
   * @throws AusnahmeVertauschteGrenzen  Falls <code>minWert</code>
   *                                     gr&ouml;&szlig;er als
   *                                     <code>hoeWert</code> ist.
   */
  public static double getRangeFractionFromValue
  (
    double minWert,
    double hoeWert,
    double wert
  )
  {
    double alsAnteil  = 0.0;
    // Wertebereich.
    double spannweite = (hoeWert - minWert);
    double differenz  = (wert    - minWert);
    
    alsAnteil = (differenz / spannweite);
    
    return alsAnteil;
  }
  
  
  
  /* FUNKTIONIERT ANSCHEINEND GUT!!!!
   * 13-01-2014
   *   -> "http://stackoverflow.com/questions/14415753/wrap-value-into-range-min-max-without-division"
   *       |-> Direkt-Link: "http://stackoverflow.com/a/14416133"
   */
  /**
   * <div class="introMethod">
   *   Wraps a value in a range.
   * </div>
   * 
   * @param value    The value to wrap.
   * @param minimum  The inclusive lower bound.
   * @param maximum  The inclusive upper bound.
   * 
   * @return         The <code>value</code> wrapped in the range
   *                 [<code>minimum</code>, <code>maximum</code>].
   */
  public static double wrapNachIserni
  (
    double value,
    double minimum,
    double maximum
  )
  {
    double wrappedValue       = 0.0;
    double distanceMinToValue = 0.0;
    double range              = 0.0;
    
    distanceMinToValue = (value    - minimum);
    range              = (maximum - minimum);
    wrappedValue       = ((distanceMinToValue % range) + range) % range + minimum;
    
    return wrappedValue;
  }
  
  
  /* ZUVERLAESSIG + GEPRUEFT!!!!!!!!!!!!!!!!!!!!!!!!!!!
   */
  // 20.05.2012
  /**
   * <div class="einleitungMethode">
   *   Ein Wert wird aus seinem alten Wertebereich in einen neuen
   *   &uuml;bertragen und dabei entsprechend konvertiert.
   * </div>
   * 
   * <div>
   *   Beispiel:
   *   <br />
   *   Der Wert 9 wird aus dem Intervall [5 ; 11] nach [17; 21]
   *   &uuml;bertragen.
   * </div>
   * 
   * @param oldMinimum  Das Minimum des alten Wertebereichs.
   * @param oldMaximum  Das Maximum des alten Wertebereichs.
   * @param value Der Wert zum &Uuml;bertragen aus dem alten
   *                Wertebereich
   *                [<code>minAlt</code> ; <code>maxAlt</code>] nach
   *                [<code>minNeu</code> ; <code>maxNeu</code>]
   * @param newMinimum  Das Minimum des neuen Wertebereichs.
   * @param newMaximum  Das Maximum des neuen Wertebereichs.
   * 
   * @return        Den neuen Wert des aus dem alten Wertebereich
   *                [<code>minAlt</code> ; <code>maxAlt</code>] in den
   *                neuen [<code>minNeu</code> ; <code>maxNeu</code>]
   *                &uuml;bertragenen Wertes <code>wertAlt</code>.
   */
  public static double translateBetweenRanges
  (
    double oldMinimum,
    double oldMaximum,
    double value,
    double newMinimum,
    double newMaximum
  )
  {
    double newValue = 0.0;
    double fraction = 0.0;
    
    fraction = getRangeFractionFromValue (oldMinimum, oldMaximum, value);
    newValue = getRangeValueFromFraction (newMinimum, newMaximum, fraction);
    
    return newValue;
  }


  // 17.08.2013
  public static boolean isInRange
  (
    double  linkeGrenze,
    boolean linksInklusiv,
    double  rechteGrenze,
    boolean rechtsInklusiv,
    double  wert
  )
  {
    boolean liegtInGrenze   = false;
    boolean inLinkerGrenze  = false;
    boolean inRechterGrenze = false;
    
    if (linksInklusiv)
    {
      inLinkerGrenze = (wert >= linkeGrenze);
    }
    else
    {
      inLinkerGrenze = (wert > linkeGrenze);
    }
    
    if (rechtsInklusiv)
    {
      inRechterGrenze = (wert <= rechteGrenze);
    }
    else
    {
      inRechterGrenze = (wert < rechteGrenze);
    }
    
    liegtInGrenze = (inLinkerGrenze && inRechterGrenze);
    
    return liegtInGrenze;
  }
}