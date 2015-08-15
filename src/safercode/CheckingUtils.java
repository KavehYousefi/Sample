/**
 * Die Klasse <code>Pruefer</code> stellt einige Methoden zur
 * Untersuchung auf unerw&uuml;nschte Werte zur Verf&uuml;gung.
 * <br /><br />
 * 
 * Datei:      Pruefer.java<br />
 * Verfasser: Kaveh Yousefi<br />
 * Datum:      22.08.2011<br />
 * Fassung:    1.0
 * <br /><br />
 * 
 * &Auml;nderungsverlauf:
 * <ul>
 *   <li>
 *     Fassung 1.0, 15.12.2010, Kaveh Yousefi:
 *     <ul>
 *       <li>
 *         Die Klasse <code>Pruefer</code> wurde angelegt.
 *       </li>
 *     </ul>
 *   </li>
 *   <li>
 *     Fassung 1.1, 22.08.2011, Kaveh Yousefi:
 *     <ul>
 *       <li>
 *         Zwei neue Methoden wurden eingef&uuml;hrt, beide zum
 *         Pr&uuml;fen auf Wertebereiche:
 *         <ul>
 *           <li>
 *             <code>public static void pruefeAufWerteBereich (int wert,
 *             int minWert, boolean minSamt, int hoeWert,
 *             boolean hoeSamt)</code>
 *           </li>
 *           <li>
 *             <code>public static void pruefeAufWerteBereich
 *             (double wert, double minWert, boolean minSamt,
 *             double hoeWert, boolean hoeSamt)</code>.
 *           </li>
 *         </ul>
 *       </li>
 *     </ul>
 *   </li>
 * </ul>
 */


package safercode;


public class CheckingUtils
{
  private static final String STANDARD_ZEIKET_MIN_SAMT   = "samt";
  private static final String STANDARD_ZEIKET_MIN_SONDER = "sonder";
  private static final String STANDARD_ZEIKET_MAX_SAMT   = "samt";
  private static final String STANDARD_ZEIKET_MAX_SONDER = "sonder";
  
  
  /**
   * <div class="introConstructor">
   *   A utility class without object methods, this constructor is of
   *   no use.
   * </div>
   */
  private CheckingUtils ()
  {
  }
  
  
  /**
   * <div class="introMethod">
   *   Checks an object for being the <code>null</code> value and
   *   throws a <code>NullPointerException</code> if this is true.
   * </div>
   * 
   * @param object  The object to check. This being <code>null</code>
   *                will cause a <code>NullPointerException</code>.
   * @param text    The possible <code>NullPointerException</code>'s
   *                message string.
   * 
   * @throws NullPointerException  If <code>object</code> equals
   *                               <code>null</code>.
   */
  public static void checkForNull (Object object, String text)
  {
    boolean isNull     = (object == null);
    String  ersatzText = "The object is null.";
    String  ausnText   = waehleText (text, ersatzText);
    
    if (isNull)
    {
      throw new NullPointerException (ausnText);
    }
  }
  
  /**
   * <div class="introMethod">
   *   Checks if an integer number is below a lower bound, throwing
   *   an <code>IllegalArgumentException</code>, if positive.
   * </div>
   * 
   * @param minimum  The still valid lower bound to check against.
   * @param number   The number to check if less than the minimum.
   * @param text     The message of the exception, if thrown.
   * @throws IllegalArgumentException  If <code>number</code> is less
   *                                   than <code>minimum</code>.
   */
  public static void checkIfLessThan
  (
    int    minimum,
    int    number,
    String text
  )
  {
    if (number < minimum)
    {
      throw new IllegalArgumentException (text);
    }
  }
  
  /**
   * <div class="introMethod">
   *   Checks if an integer number is below a lower bound, throwing
   *   an <code>IllegalArgumentException</code>, if positive.
   * </div>
   * 
   * @param minimum  The still valid lower bound to check against.
   * @param number   The number to check if less than the minimum.
   * @param text     The message of the exception, if thrown.
   * @throws IllegalArgumentException  If <code>number</code> is less
   *                                   than <code>minimum</code>.
   */
  public static void checkIfLessThan
  (
    long   minimum,
    long   number,
    String text
  )
  {
    if (number < minimum)
    {
      throw new IllegalArgumentException (text);
    }
  }
  
  /**
   * <div class="introMethod">
   *   Checks if an integer number is above an upper bound, throwing
   *   an <code>IllegalArgumentException</code>, if positive.
   * </div>
   * 
   * @param maximum  The still valid upper bound to check against.
   * @param number   The number to check if greater than the maximum.
   * @param text     The message of the exception, if thrown.
   * @throws IllegalArgumentException  If <code>number</code> is greater
   *                                   than <code>maximum</code>.
   */
  public static void checkIfGreaterThan
  (
    int    maximum,
    int    number,
    String text
  )
  {
    if (number > maximum)
    {
      throw new IllegalArgumentException (text);
    }
  }
  
  
  /**
   * Ein ganzzahliger Wert wird darauf gepr&uuml;ft, ob er innerhalb
   * eines vorgegebenen Wertebereiches liegt.
   * 
   * @param wert    Der zu untersuchende Wert.
   * @param minWert  Der Mindestwert des Wertebereiches, also die
   *                 Untergrenze.
   * @param minSamt  Eine Boole'sche Angabe darob, ob <code>minWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Untergrenze
   *                     <code>minWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>gr&ouml;&szlig;er gleich</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Untergrenze
   *                     <code>minWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>gr&ouml;&szlig;er als</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                 </ul>
   * @param hoeWert  Der H&ouml;chstwert des Wertebereiches, also die
   *                 Obergrenze.
   * @param hoeSamt  Eine Boole'sche Angabe darob, ob <code>hoeWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>kleiner gleich</b> <code>hoeWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>kleiner als</b> <code>hoeWert</code>
   *                     sein.
   *                   </li>
   *                 </ul>
   * 
   * @throws IllegalArgumentException  Falls <code>wert</code> den mit
   *                                   <code>minWert</code> und
   *                                   <code>hoeWert</code> definierten
   *                                   Wertebereich verletzt.
   */
  public static void pruefeAufWerteBereich (int wert, int minWert,
                                            boolean minSamt, int hoeWert,
                                            boolean hoeSamt)
  {
    boolean  zuGering  = false;
    boolean  zuGross    = false;
    
    // Untergrenze behandeln.
    if (minSamt)
    {
      zuGering  = (wert < minWert);
    }
    else
    {
      zuGering  = (wert <= minWert);
    }
    
    // Obergrenze untersuchen.
    if (hoeSamt)
    {
      zuGross  = (wert > hoeWert);
    }
    else
    {
      zuGross  = (wert >= hoeWert);
    }
    
    if (zuGering)
    {
      String  text  = "Der Wert " + wert + " ist zu gering. " +
                      schaffeWerteBereichAngabe (wert, minWert, minSamt,
                                                  hoeWert, hoeSamt);
      throw new IllegalArgumentException (text);
    }
    else if (zuGross)
    {
      String  text  = "Der Wert " + wert + " ist zu gro�. " +
                      schaffeWerteBereichAngabe (wert, minWert, minSamt,
                                                  hoeWert, hoeSamt);
      throw new IllegalArgumentException (text);
    }
  }
  
  /**
   * Ein ganzzahliger Wert wird darauf gepr&uuml;ft, ob er innerhalb
   * eines vorgegebenen Wertebereiches liegt.
   * 
   * @param wert    Der zu untersuchende Wert.
   * @param minWert  Der Mindestwert des Wertebereiches, also die
   *                 Untergrenze.
   * @param minSamt  Eine Boole'sche Angabe darob, ob <code>minWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Untergrenze
   *                     <code>minWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>gr&ouml;&szlig;er gleich</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Untergrenze
   *                     <code>minWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>gr&ouml;&szlig;er als</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                 </ul>
   * @param hoeWert  Der H&ouml;chstwert des Wertebereiches, also die
   *                 Obergrenze.
   * @param hoeSamt  Eine Boole'sche Angabe darob, ob <code>hoeWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>kleiner gleich</b> <code>hoeWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>kleiner als</b> <code>hoeWert</code>
   *                     sein.
   *                   </li>
   *                 </ul>
   * 
   * @throws IllegalArgumentException  Falls <code>wert</code> den mit
   *                                   <code>minWert</code> und
   *                                   <code>hoeWert</code> definierten
   *                                   Wertebereich verletzt.
   */
  public static void pruefeAufWerteBereich (int wert, int minWert,
                                        boolean minSamt, String minText,
                                        int hoeWert, boolean hoeSamt,
                                        String hoeText)
  {
    boolean  zuGering  = false;
    boolean  zuGross    = false;
    
    // Untergrenze behandeln.
    if (minSamt)
    {
      zuGering  = (wert < minWert);
    }
    else
    {
      zuGering  = (wert <= minWert);
    }
    
    // Obergrenze untersuchen.
    if (hoeSamt)
    {
      zuGross  = (wert > hoeWert);
    }
    else
    {
      zuGross  = (wert >= hoeWert);
    }
    
    if (zuGering)
    {
      throw new IllegalArgumentException (minText);
    }
    else if (zuGross)
    {
      throw new IllegalArgumentException (hoeText);
    }
  }
  
  /**
   * Ein Gleik-Wert mit <code>double</code>-Genauigkeit wird darauf
   * gepr&uuml;ft, ob er innerhalb eines vorgegebenen Wertebereiches
   * liegt.
   * 
   * @param wert    Der zu untersuchende Wert.
   * @param minWert  Der Mindestwert des Wertebereiches, also die
   *                 Untergrenze.
   * @param minSamt  Eine Boole'sche Angabe darob, ob <code>minWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Untergrenze
   *                     <code>minWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>gr&ouml;&szlig;er gleich</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Untergrenze
   *                     <code>minWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>gr&ouml;&szlig;er als</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                 </ul>
   * @param hoeWert  Der H&ouml;chstwert des Wertebereiches, also die
   *                 Obergrenze.
   * @param hoeSamt  Eine Boole'sche Angabe darob, ob <code>hoeWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>kleiner gleich</b> <code>hoeWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>kleiner als</b> <code>hoeWert</code>
   *                     sein.
   *                   </li>
   *                 </ul>
   * 
   * @throws IllegalArgumentException  Falls <code>wert</code> den mit
   *                                   <code>minWert</code> und
   *                                   <code>hoeWert</code> definierten
   *                                   Wertebereich verletzt.
   */
  public static void pruefeAufWerteBereich
  (
    double  wert,
    double  minWert,
    boolean  minSamt,
    double  hoeWert,
    boolean  hoeSamt
  )
  {
    boolean  zuGering  = false;
    boolean  zuGross    = false;
    
    // Untergrenze behandeln.
    if (minSamt)
    {
      zuGering  = (wert < minWert);
    }
    else
    {
      zuGering  = (wert <= minWert);
    }
    
    // Obergrenze untersuchen.
    if (hoeSamt)
    {
      zuGross  = (wert > hoeWert);
    }
    else
    {
      zuGross  = (wert >= hoeWert);
    }
    
    if (zuGering)
    {
      String  text  = "Der Wert " + wert + " ist zu gering. " +
                      schaffeWerteBereichAngabe (wert, minWert, minSamt,
                                                  hoeWert, hoeSamt);
      throw new IllegalArgumentException (text);
    }
    else if (zuGross)
    {
      String  text  = "Der Wert " + wert + " ist zu gro�. " +
                      schaffeWerteBereichAngabe (wert, minWert, minSamt,
                                                  hoeWert, hoeSamt);
      throw new IllegalArgumentException (text);
    }
  }
  
  
  /**
   * Ein Gleik-Wert mit <code>double</code>-Genauigkeit wird darauf
   * gepr&uuml;ft, ob er innerhalb eines vorgegebenen Wertebereiches
   * liegt.
   * 
   * @param wert    Der zu untersuchende Wert.
   * @param minWert  Der Mindestwert des Wertebereiches, also die
   *                 Untergrenze.
   * @param minSamt  Eine Boole'sche Angabe darob, ob <code>minWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Untergrenze
   *                     <code>minWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>gr&ouml;&szlig;er gleich</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Untergrenze
   *                     <code>minWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>gr&ouml;&szlig;er als</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                 </ul>
   * @param hoeWert  Der H&ouml;chstwert des Wertebereiches, also die
   *                 Obergrenze.
   * @param hoeSamt  Eine Boole'sche Angabe darob, ob <code>hoeWert</code>
   *                 als inklusive zu betrachten ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>kleiner gleich</b> <code>hoeWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>kleiner als</b> <code>hoeWert</code>
   *                     sein.
   *                   </li>
   *                 </ul>
   * 
   * @throws IllegalArgumentException  Falls <code>wert</code> den mit
   *                                   <code>minWert</code> und
   *                                   <code>hoeWert</code> definierten
   *                                   Wertebereich verletzt.
   */
  public static void pruefeAufWerteBereich (double wert, double minWert,
                                        boolean minSamt, String minText,
                                        double hoeWert, boolean hoeSamt,
                                        String hoeText)
  {
    boolean  zuGering  = false;
    boolean  zuGross    = false;
    
    // Untergrenze behandeln.
    if (minSamt)
    {
      zuGering  = (wert < minWert);
    }
    else
    {
      zuGering  = (wert <= minWert);
    }
    
    // Obergrenze untersuchen.
    if (hoeSamt)
    {
      zuGross  = (wert > hoeWert);
    }
    else
    {
      zuGross  = (wert >= hoeWert);
    }
    
    if (zuGering)
    {
      throw new IllegalArgumentException (minText);
    }
    else if (zuGross)
    {
      throw new IllegalArgumentException (hoeText);
    }
  }
  
  
  
  /**
   * <div class="einleitungMethode">
   *   Ein Gleik-Wert mit <code>double</code>-Genauigkeit wird darauf
   *   gepr&uuml;ft, ob er innerhalb eines vorgegebenen Wertebereiches
   *   liegt.
   * </div>
   * 
   * @param wert    Der zu untersuchende Wert.
   * @param minWert  Der Mindestwert des Wertebereiches, also die
   *                 Untergrenze.
   * @param minSamt  Eine Boole'sche Angabe darob, ob
   *                 <code>minWert</code> als inklusive zu betrachten
   *                 ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Untergrenze
   *                     <code>minWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>gr&ouml;&szlig;er gleich</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Untergrenze
   *                     <code>minWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>gr&ouml;&szlig;er als</b>
   *                     <code>minWert</code> sein.
   *                   </li>
   *                 </ul>
   * @param hoeWert  Der H&ouml;chstwert des Wertebereiches, also die
   *                 Obergrenze.
   * @param hoeSamt  Eine Boole'sche Angabe darob, ob
   *                 <code>hoeWert</code> als inklusive zu betrachten
   *                 ist:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als inklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     <b>kleiner gleich</b> <code>hoeWert</code>
   *                     sein.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: Die Obergrenze
   *                     <code>hoeWert</code> ist als exklusive zu
   *                     verstehen. Der <code>wert</code> muss also
   *                     unbedingt <b>kleiner als</b>
   *                     <code>hoeWert</code> sein.
   *                   </li>
   *                 </ul>
   * @return        Einen Boole'schen Wert zur Bestimmung, ob der
   *                 <code>wert</code> innerhalb der g&uuml;ltigen
   *                 Wertebereiches liegt:
   *                 <ul>
   *                   <li>
   *                     <code>true</code>: <code>wert</code> liegt in
   *                     dem Wertebereich.
   *                   </li>
   *                   <li>
   *                     <code>false</code>: <code>wert</code>
   *                     au&szlig;erhalb des Wertebereiches.
   *                   </li>
   *                 </ul>
   * 
   * @throws AusnahmeVertauschteGrenzen  Falls <code>minWert</code>
   *                                     gr&ouml;&szlig;er als
   *                                     <code>maxWert</code> ist.
   */
  public static boolean liegtInWerteBereich
  (
    double  wert,
    double  minWert,
    boolean  minSamt,
    double  hoeWert,
    boolean  hoeSamt
  )
  {
    boolean  inBereich      = false;
    boolean  inUntergrenze  = false;
    boolean  inObergrenze  = false;
    
    // Untergrenze behandeln.
    if (minSamt)
    {
      inUntergrenze  = (wert >= minWert);
    }
    else
    {
      inUntergrenze  = (wert > minWert);
    }
    
    // Obergrenze untersuchen.
    if (hoeSamt)
    {
      inObergrenze  = (wert <= hoeWert);
    }
    else
    {
      inObergrenze  = (wert < hoeWert);
    }
    
    inBereich  = (inUntergrenze && inObergrenze);
    
    return inBereich;
  }
  
  
  
  // 13.09.2011
  public static <T> void pruefeObEnthalten (T wert, T[] werte, String ausnText)
  {
    boolean  enthalten  = false;
    
    CheckingUtils.checkForNull (werte, "Es wurde kein Werte-Array angegeben.");
    
    for (T ggwWert : werte)
    {
      boolean  gleich  = wert.equals (ggwWert);
      
      if (gleich)
      {
        enthalten  = true;
        
        return;
      }
    }
    
    enthalten  = false;
    
    if (! enthalten)
    {
      throw new IllegalArgumentException (ausnText);
    }
  }
  
  
  
  
  ///////////////////////////////////////////////////////////////////////
  // -- Umsetzung von Methoden bezueglich Klassenzugehoerigkeit.    -- //
  ///////////////////////////////////////////////////////////////////////
  
  //+/ 24.03.2013 (aus "spiel.bitcrpg3xt.BewegungParameter") hierher verschoben.
  public static boolean istVonDieserKlasse
  (
    Object    objekt,
    Class<?>  klasse
  )
  {
    boolean    klassePasst    = false;
    Class<?>  objektKlasse  = objekt.getClass ();
    
    klassePasst              = (klasse.equals (objektKlasse));
    
    return klassePasst;
  }
  
  //+/ 24.03.2013 (aus "spiel.bitcrpg3xt.BewegungParameter") hierher verschoben.
  public static boolean sindVonGleicherKlasse
  (
    Object  objekt1,
    Object  objekt2
  )
  {
    boolean    klassePasst    = false;
    Class<?>  objekt1Klasse  = objekt1.getClass ();
    Class<?>  objekt2Klasse  = objekt2.getClass ();
    
    klassePasst              = (objekt1Klasse.equals (objekt2Klasse));
    
    return klassePasst;
  }
  
  //+/ 24.03.2013 (aus "spiel.bitcrpg3xt.BewegungParameter") hierher verschoben.
  /**
   * <div class="einleitungMethode">
   *   Diese Methode pr&uuml;ft, ob ein angegebenes Objekt Instanz einer
   *   bestimmten Klasse ist und wirft bei negativem Ergebnis eine
   *   <code>ClassCastException</code>.
   * </div>
   * 
   * @param objekt        Das zu untersuchende Objekt.
   * @param klasse        Die Klasse, gegen jene das
   *                       <code>objekt</code> gepr&uuml;ft werden soll.
   * @param ausnahmeText  Ein optionaler, durch die
   *                       <code>ClassCastException</code> anzuzeigender
   *                       Text, sofern das Objekt nicht Instanz der
   *                       Klasse ist.
   */
  public static void pruefeObjektKlasse
  (
    Object    objekt,
    Class<?>  klasse,
    String    ausnahmeText
  )
  {
    boolean klasseStimmt = istVonDieserKlasse (objekt, klasse);
    
    if (! klasseStimmt)
    {
      throw new ClassCastException (ausnahmeText);
    }
  }
  
  
  
  ///////////////////////////////////////////////////////////////////////
  // -- Umsetzung der Hilfsmethoden.                                -- //
  ///////////////////////////////////////////////////////////////////////
  
  // Ergebnis: "text", wenn angegeben, ansonsten "ersatzText".
  private static String waehleText (String text, String ersatzText)
  {
    String  gewaehlterText  = null;
    boolean  textAngegeben    = (text != null);
    
    if (textAngegeben)
    {
      gewaehlterText  = text;
    }
    else
    {
      gewaehlterText  = ersatzText;
    }
    
    return gewaehlterText;
  }
  
  private static String schaffeWerteBereichAngabe
  (
    int      wert,
    int      minWert,
    boolean  minSamt,
    int      hoeWert,
    boolean  hoeSamt
  )
  {
    String  text              = null;
    // Fuer "minWert": Entweder "samt" oder "sonder".
    String  tokenUntergrenze  = null;
    // Fuer "hoeWert": Entweder "samt" oder "sonder".
    String  tokenObergrenze    = null;
    
    if (minSamt)
    {
      tokenUntergrenze  = STANDARD_ZEIKET_MIN_SAMT;
    }
    else
    {
      tokenUntergrenze  = STANDARD_ZEIKET_MIN_SONDER;
    }
    
    if (hoeSamt)
    {
      tokenObergrenze  = STANDARD_ZEIKET_MAX_SAMT;
    }
    else
    {
      tokenObergrenze  = STANDARD_ZEIKET_MAX_SONDER;
    }
    
    text  = String.format ("Er muss zwischen %s %d und %s %d liegen.",
                           tokenUntergrenze, minWert,
                           tokenObergrenze, hoeWert);
    
    return text;
  }
  
//Nennt g�ltigen Wertebereich.
  private static String schaffeWerteBereichAngabe
  (
    double  wert,
    double  minWert,
    boolean  minSamt,
    double  hoeWert,
    boolean  hoeSamt
  )
  {
    String  text              = null;
    // Fuer "minWert": Entweder "samt" oder "sonder".
    String  tokenUntergrenze  = null;
    // Fuer "hoeWert": Entweder "samt" oder "sonder".
    String  tokenObergrenze    = null;
    
    if (minSamt)
    {
      tokenUntergrenze  = STANDARD_ZEIKET_MIN_SAMT;
    }
    else
    {
      tokenUntergrenze  = STANDARD_ZEIKET_MIN_SONDER;
    }
    
    if (hoeSamt)
    {
      tokenObergrenze  = STANDARD_ZEIKET_MAX_SAMT;
    }
    else
    {
      tokenObergrenze  = STANDARD_ZEIKET_MAX_SONDER;
    }
    
    text  = String.format ("Er muss zwischen %s %f und %s %f liegen.",
                           tokenUntergrenze, minWert,
                           tokenObergrenze, hoeWert);
    
    return text;
  }
}