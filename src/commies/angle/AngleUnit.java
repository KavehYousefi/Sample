package commies.angle;


/* +/08.03.2013:
 *   + "inBogenmass (double winkel)"
 *   + "ausBogenmass (double winkelBogenmass)"
 *   + Mitglied "VOLLWINKEL".
 * 
 * +/09.03.2013:
 *   + Mitglied "NAUTISCHER_STRICH"
 *   + Mitglied "ARTILLERISTISCHER_STRICH"
 *   + Mitglied "ZEITMASS".
 * 
 * +/10.03.2013:
 *   + Mitglied "RECHTER_WINKEL".
 * 
 * +/22.03.2013
 *   + Mitglied "SEGA_GENESIS_MEGA_DRIVE".
 */
// TODO: Winkelmasse und Winkelarten (und eventuell Kreiseinteilungen) tabellarisch auffuehren.
// -> Buch "Mathematik verstaendlich", Seite 177.
/**
 * <div class="introClass">
 *   This enumerated type defines the valid angle units.
 * </div>
 * 
 * <div>
 *   Following sources were consulted for implementing this class:
 *   <ul>
 *     <li>
 *       <a href="https://en.wikipedia.org/wiki/Angle">
 *         https://en.wikipedia.org/wiki/Angle
 *       </a>
 *       <br />
 *       accessed: 08.03.2013
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
 *       AngleUnit.java
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
 *       26.01.2013
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
public enum AngleUnit
{
  DEGREE
  {
    @Override
    public double getAsDegrees (double winkel)
    {
      return winkel;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double  inBogenmass  = Math.toRadians (winkel);
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      return winkelGrad;
    }
    
    public double setFromRadians (double winkelBogenmass)
    {
      double  ausBogenmass  = Math.toDegrees (winkelBogenmass);
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String  alsZeiket  = String.format ("%f°", winkel);
      
      return alsZeiket;
    }
    
    public double normalisiereMitRichtung (double winkel)
    {
      double winkelNormalisiert = (winkel % 360.0);
      
      return winkelNormalisiert;
    }
    
    // Normalisiert auch Vorzeichen.
    public double normalisiereOhneRichtung (double winkel)
    {
      double winkelNormalisiert = (winkel % 360.0);
      
      if (winkelNormalisiert < 0.0)
      {
        winkelNormalisiert = (360.0 - winkelNormalisiert);
      }
      
      return winkelNormalisiert;
    }
  },
  
  RADIAN
  {
    private static final double VOLLWINKEL_BOGENMASS = (2.0 * Math.PI);
    
    @Override
    public double getAsDegrees (double winkel)
    {
      double  inGrad  = Math.toDegrees (winkel);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double  inBogenmass  = winkel;
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      double  ausGrad  = Math.toRadians (winkelGrad);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
      double  ausBogenmass  = winkelBogenmass;
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String  alsZeiket  = String.format ("%f rad", winkel);
      
      return alsZeiket;
    }
    
    public double normalisiereMitRichtung (double winkel)
    {
      double winkelNormalisiert = (winkel % VOLLWINKEL_BOGENMASS);
      
      return winkelNormalisiert;
    }
    
    // Normalisiert auch Vorzeichen.
    public double normalisiereOhneRichtung (double winkel)
    {
      double winkelNormalisiert = (winkel % VOLLWINKEL_BOGENMASS);
      
      if (winkelNormalisiert < 0.0)
      {
        winkelNormalisiert = (VOLLWINKEL_BOGENMASS - winkelNormalisiert);
      }
      
      return winkelNormalisiert;
    }
  },
  
  // Im Englischen eher: "gradian".
  /**
   * <div class="einleitungEnumInstanz">
   *   Das Ma&szlig; "Gon" oder "Neugrad" wird beschrieben.
   * </div>
   * 
   * <div>
   *   Dieses Winkelsystem teilt einen Vollkreis in 400
   *   gleichm&auml;&szlig;ige Abschnitte ein.
   * </div>
   * 
   * <div>
   *   Besondere Winkelarten sind:
   *   <table>
   *     <tr>
   *       <th>
   *         Bezeichnung
   *       </th>
   *       <th>
   *         Wert
   *       </th>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         Nullwinkel
   *       </td>
   *       <td>
   *         0 gon
   *       </td>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         rechter Winkel
   *       </td>
   *       <td>
   *         100 gon
   *       </td>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         gestreckter Winkel
   *       </td>
   *       <td>
   *         200 gon
   *       </td>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         Vollwinkel
   *       </td>
   *       <td>
   *         400 gon
   *       </td>
   *     </tr>
   *   </table>
   * </div>
   */
  GON
  {
    @Override
    public double getAsDegrees (double winkel)
    {
      double inGrad = ((360.0 / 400.0) * winkel);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double inBogenmass = ((Math.PI / 200.0) * winkel);
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      double ausGrad = ((400.0 / 360.0) * winkelGrad);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
      /* Moeglicherweise schnellere Berechnung:
       * ---------------------------------------
       * Die eigentliche Formel lautet:  (400 / (2 * PI)).
       * Dies kann man aber kuerzen:    (200 /      PI ):
       */
      double ausBogenmass = ((200.0 / Math.PI) * winkelBogenmass);
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String alsZeiket = String.format ("%f gon", winkel);
      
      return alsZeiket;
    }
  },
  
  // Im Englischen: "Turn".
  // -> "http://de.wikipedia.org/wiki/Vollwinkel"
  // -> "http://en.wikipedia.org/wiki/Turn_%28geometry%29"
  /**
   * <div class="einleitungEnumInstanz">
   *   Die Winkeleinheit <i>Vollwinkel</i>, im Englischen als
   *   <i>turn</i> bekannt, wird beschrieben.
   * </div>
   * 
   * <div>
   *   Ein Vollwinkel teilt einen Kreis in 100 gleichm&auml;&szlig;ige
   *   St&uuml;cke auf.
   * </div>
   * 
   * <div>
   *   Besondere Winkelarten sind:
   *   <table>
   *     <tr>
   *       <th>
   *         Bezeichnung
   *       </th>
   *       <th>
   *         Wert
   *       </th>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         Nullwinkel
   *       </td>
   *       <td>
   *         0,00
   *       </td>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         rechter Winkel
   *       </td>
   *       <td>
   *         0,25
   *       </td>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         gestreckter Winkel
   *       </td>
   *       <td>
   *         0,50
   *       </td>
   *     </tr>
   * 
   *     <tr>
   *       <td>
   *         Vollwinkel
   *       </td>
   *       <td>
   *         1,00
   *       </td>
   *     </tr>
   *   </table>
   * </div>
   */
  TURN
  {
    @Override
    public double getAsDegrees (double winkel)
    {
      double inGrad = (winkel * 360.0);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double inBogenmass = (winkel * (2.0 * Math.PI));
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      double ausGrad = (winkelGrad / 360.0);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
      double ausBogenmass = (winkelBogenmass / (2.0 * Math.PI));
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String alsZeiket = String.format ("%f", winkel);
      
      return alsZeiket;
    }
  },
  
  // Rechter Winkel.
  // -> "http://de.wikipedia.org/wiki/Winkelma%C3%9F#Vollkreis_und_rechter_Winkel"
  QUADRANT
  {
    public static final double ONE_DEGREE = 90.0;
    public static final double ONE_RADIAN = (Math.PI / 2.0);
    
    @Override
    public double getAsDegrees (double winkel)
    {
      double inGrad = (winkel * ONE_DEGREE);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double inBogenmass = (winkel * ONE_RADIAN);
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      double ausGrad = (winkelGrad / ONE_DEGREE);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
      double  ausBogenmass  = (winkelBogenmass / ONE_RADIAN);
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String alsZeiket = String.format ("%f \u221F", winkel);
      
      return alsZeiket;
    }
  },
  
  // -> "http://en.wikipedia.org/wiki/Angle#Units"
  SEXTANT
  {
    public static final double ONE_DEGREE = 60.0;
    public static final double ONE_RADIAN = (Math.PI / 3.0);
    
    @Override
    public double getAsDegrees (double systemAngle)
    {
      double asDegrees = (ONE_DEGREE * systemAngle);
      
      return asDegrees;
    }
    
    @Override
    public double getAsRadians (double systemAngle)
    {
      double asRadians = (ONE_RADIAN * systemAngle);
      
      return asRadians;
    }
    
    @Override
    public double setFromDegrees (double angleInDegrees)
    {
      double asSystemAngle = (angleInDegrees / ONE_DEGREE);
      
      return asSystemAngle;
    }
    
    @Override
    public double setFromRadians (double angleInRadians)
    {
      double asSystemAngle = (angleInRadians / ONE_RADIAN);
      
      return asSystemAngle;
    }
    
    @Override
    public String getFormattedString (double systemAngle)
    {
      String formattedString = String.format
      (
        "%f Babylonian unit",
        systemAngle
      );
      
      return formattedString;
    }
  },
  
  // -> "http://en.wikipedia.org/wiki/Angle#Units"
  // Pechus, n=180 => 1 pechus = 2°.
  PECHUS_180
  {
    public static final double ONE_DEGREE = 2.0;
    public static final double ONE_RADIAN = 0.03490658503988659;
    
    @Override
    public double getAsDegrees (double systemAngle)
    {
      double asDegrees = (ONE_DEGREE * systemAngle);
      
      return asDegrees;
    }
    
    @Override
    public double getAsRadians (double systemAngle)
    {
      double asRadians = (ONE_RADIAN * systemAngle);
      
      return asRadians;
    }
    
    @Override
    public double setFromDegrees (double angleInDegrees)
    {
      double asSystemAngle = (angleInDegrees / ONE_DEGREE);
      
      return asSystemAngle;
    }
    
    @Override
    public double setFromRadians (double angleInRadians)
    {
      double asSystemAngle = (angleInRadians / ONE_RADIAN);
      
      return asSystemAngle;
    }
    
    @Override
    public String getFormattedString (double systemAngle)
    {
      String formattedString = String.format
      (
        "%f pechus (n=180)",
        systemAngle
      );
      
      return formattedString;
    }
  },
  
  // -> "http://en.wikipedia.org/wiki/Angle#Units"
  // Pechus, n=144 => 1 pechus = 2,5°.
  PECHUS_144
  {
    public static final double ONE_DEGREE = 2.5;
    public static final double ONE_RADIAN = 0.04363323129985824;
    
    @Override
    public double getAsDegrees (double systemAngle)
    {
      double asDegrees = (ONE_DEGREE * systemAngle);
      
      return asDegrees;
    }
    
    @Override
    public double getAsRadians (double systemAngle)
    {
      double asRadians = (ONE_RADIAN * systemAngle);
      
      return asRadians;
    }
    
    @Override
    public double setFromDegrees (double angleInDegrees)
    {
      double asSystemAngle = (angleInDegrees / ONE_DEGREE);
      
      return asSystemAngle;
    }
    
    @Override
    public double setFromRadians (double angleInRadians)
    {
      double asSystemAngle = (angleInRadians / ONE_RADIAN);
      
      return asSystemAngle;
    }
    
    @Override
    public String getFormattedString (double systemAngle)
    {
      String formattedString = String.format
      (
        "%f pechus (n=144)",
        systemAngle
      );
      
      return formattedString;
    }
  },
  
  // -> "http://de.wikipedia.org/wiki/Strich_%28Winkeleinheit%29"
  // -> "http://de.wikipedia.org/wiki/Rechter_Winkel"
  NAUTISCHER_STRICH
  {
    @Override
    public double getAsDegrees (double winkel)
    {
      double  inGrad  = ((180.0 / 16.0) * winkel);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double  inBogenmass  = ((Math.PI / 16.0) * winkel);
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      double  ausGrad  = ((16.0 / 180.0) * winkelGrad);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
      double  ausBogenmass  = ((16.0 / Math.PI) * winkelBogenmass);
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String  alsZeiket  = String.format ("%f�", winkel);
      
      return alsZeiket;
    }
  },
  
  // -> "http://de.wikipedia.org/wiki/Strich_%28Winkeleinheit%29"
  // -> "http://de.wikipedia.org/wiki/Rechter_Winkel"
  ARTILLERISTISCHER_STRICH
  {
    @Override
    public double getAsDegrees (double winkel)
    {
      double  inGrad  = ((180.0 / 3200.0) * winkel);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double  inBogenmass  = ((Math.PI / 3200.0) * winkel);
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      double  ausGrad  = ((3200.0 / 180.0) * winkelGrad);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
      double  ausBogenmass  = ((3200.0 / Math.PI) * winkelBogenmass);
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String  alsZeiket  = String.format ("%f mil", winkel);
      
      return alsZeiket;
    }
  },
  
  // German: "Zeitmass".
  // -> "http://de.wikipedia.org/wiki/Zeitma%C3%9F_%28Winkel%29"
  // -> "http://de.wikipedia.org/wiki/Rechter_Winkel"
  HOUR_ANGLE
  {
    public static final double ONE_DEGREE = 15.0;
    public static final double ONE_RADIAN = (Math.PI / 12.0);
    
    @Override
    public double getAsDegrees (double winkel)
    {
      // 15 = (180.0 / 12.0).
      double  inGrad  = (ONE_DEGREE * winkel);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      double inBogenmass = (ONE_RADIAN * winkel);
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
//      double ausGrad = ((12.0 / 180.0) * winkelGrad);
      double ausGrad = (winkelGrad / ONE_DEGREE);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
//      double  ausBogenmass  = ((12.0 / Math.PI) * winkelBogenmass);
      double ausBogenmass = (winkelBogenmass / ONE_RADIAN);
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String alsZeiket = String.format ("%f h", winkel);
      
      return alsZeiket;
    }
  },
  
  // -> "http://info.sonicretro.org/SPG:Solid_Tiles#Notes"
  SEGA_GENESIS_MEGA_DRIVE
  {
    public  static  final  double  UNTERTEILUNGEN  = 256.0;
    /* 1,40625 = 360/256.
     * Ein normaler Kreis hat 360 Teile.
     * Ein Kreis im Sega-System hat 256 Teile.
     */
    public  static  final  double  FAKTOR_IN_GRAD  = 1.40625;
    public  static  final  double  FAKTOR_IN_RAD    = (TWO_PI        / UNTERTEILUNGEN);
    public  static  final  double  FAKTOR_AUS_GRAD  = (UNTERTEILUNGEN  / VOLLKREIS_GRAD);
    public  static  final  double  FAKTOR_AUS_RAD  = (UNTERTEILUNGEN  / TWO_PI        );
    
    @Override
    public double getAsDegrees (double winkel)
    {
      double inGrad = ((UNTERTEILUNGEN - winkel) * FAKTOR_IN_GRAD);
      
      return inGrad;
    }
    
    @Override
    public double getAsRadians (double winkel)
    {
      // Zunaechst in Grad umwandeln.
      double inBogenmass = ((UNTERTEILUNGEN - winkel) * FAKTOR_IN_RAD);
      
      return inBogenmass;
    }
    
    @Override
    public double setFromDegrees (double winkelGrad)
    {
      double ausGrad = ((VOLLKREIS_GRAD - winkelGrad) * FAKTOR_AUS_GRAD);
      
      return ausGrad;
    }
    
    @Override
    public double setFromRadians (double winkelBogenmass)
    {
      double ausBogenmass = ((TWO_PI - winkelBogenmass) * FAKTOR_AUS_RAD);
      
      return ausBogenmass;
    }
    
    @Override
    public String getFormattedString (double winkel)
    {
      String alsZeiket = String.format ("%f�");
      
      return alsZeiket;
    }
  };
  
  
  private static final double VOLLKREIS_GRAD = 360.0;
  private static final double TWO_PI         = (2.0 * Math.PI);
  
  
  /**
   * <div class="einleitungKonstruktor">
   *   Der private Konstruktor.
   * </div>
   */
  private AngleUnit ()
  {
  }
  
  
  /**
   * <div class="einleitungMethode">
   *   Ein angegebener Wert, interpretiert als Winkel im eigenen
   *   System, wird im Gradma&szlig; zur&uuml;ckgegeben.
   * </div>
   * 
   * @param winkel  Der Winkel im eigenen System.
   * 
   * @return        Jener dem <code>winkel</code> entsprechenden Winkel
   *                in Grad.
   */
  abstract public double getAsDegrees (double winkel);
  
  /**
   * <div class="einleitungMethode">
   *   Ein angegebener Wert, interpretiert als Winkel im eigenen
   *   System, wird im Bogenma&szlig; zur&uuml;ckgegeben.
   * </div>
   * 
   * @param winkel  Der Winkel im eigenen System.
   * 
   * @return        Jener dem <code>winkel</code> entsprechenden Winkel
   *                in Bogenma&szlig;.
   */
  abstract public double getAsRadians (double winkel);
  
  /**
   * <div class="einleitungMethode">
   *   Zu einem Winkel im Gradma&szlig; wird der entsprechende des
   *   eigenen Systems zur&uuml;ckgegeben.
   * </div>
   * 
   * @param winkelGrad  Der im Gradma&szlig; vorliegende, umzuwandelnde
   *                    Winkel.
   * 
   * @return            Jener dem <code>grad</code> entsprechenden
   *                    Winkel im eigenen System.
   */
  abstract public double setFromDegrees (double winkelGrad);
  
  /**
   * <div class="einleitungMethode">
   *   Zu einem Winkel im Bogenma&szlig; wird der entsprechende des
   *   eigenen Systems zur&uuml;ckgegeben.
   * </div>
   * 
   * @param winkelBogenmass  Der im Bogenma&szlig; vorliegende,
   *                         umzuwandelnde Winkel.
   * 
   * @return                 Jener dem <code>winkelBogenmass</code>
   *                         entsprechenden Winkel im eigenen System.
   */
  abstract public double setFromRadians (double winkelBogenmass);
  
  /**
   * <div class="einleitungMethode">
   *   Ein Winkel in diesem Winkelma&szlig; wird als Zeichenkette
   *   zur&uuml;ckgegeben.
   * </div>
   * 
   * @param winkel  Der Winkel in diesem Winkelma&szlig;.
   * 
   * @return        Eine Zeichenkette, welche den <code>winkel</code>
   *                repr&auml;sentiert.
   */
  abstract public String getFormattedString (double winkel);
}