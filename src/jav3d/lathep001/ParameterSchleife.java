package jav3d.lathep001;

public class ParameterSchleife
{
  private static final int MINIMUM_SCHRITTE_ANZAHL = 0;
  
  private double startWert;
  private double endWert;
  private double schrittWeite;
  
  
  /**
   * <div class="einleitungKonstruktor">
   *   Generiert eine Parameter-Schleife mit Standardwerten.
   * </div>
   * 
   * <div>
   *   <table>
   *     <tr>
   *       <th>Attribut</th>
   *       <th>Standardwert</th>
   *     </tr>
   *     <tr>
   *       <td>Startwert</td>
   *       <td>0.0</td>
   *     </tr>
   *     <tr>
   *       <td>Endwert</td>
   *       <td>1.0</td>
   *     </tr>
   *     <tr>
   *       <td>Schrittweite</td>
   *       <td>0.1</td>
   *     </tr>
   *   </table>
   * </div>
   */
  public ParameterSchleife ()
  {
    initParameterSchleife (0.0, 1.0, 0.1);
  }
  
  /**
   * <div class="einleitungKonstruktor">
   *   Generiert eine Parameter-Schleife &uuml;ber das gew&uuml;nschte
   *   Intervall mit bestimmter Schrittweite.
   * </div>
   * 
   * @param startWert     Der Startwert des Intervalls.
   * @param endWert       Der Endwert   des Intervalls.
   * @param schrittWeite  Die Schrittweite.
   */
  public ParameterSchleife
  (
    double startWert,
    double endWert,
    double schrittWeite
  )
  {
    initParameterSchleife (startWert, endWert, schrittWeite);
  }
  
  /**
   * <div class="einleitungKonstruktor">
   *   Generiert eine Parameter-Schleife &uuml;ber das gew&uuml;nschte
   *   Intervall mit bestimmter Anzahl an Schritten.
   * </div>
   * 
   * @param startWert      Der Startwert des Intervalls.
   * @param endWert        Der Endwert   des Intervalls.
   * @param schrittAnzahl  Die Anzahl an Schritten. Sie muss
   *                       gr&ouml;&szlig;er gleich 0 sein.
   */
  public ParameterSchleife
  (
    double startWert,
    double endWert,
    int    schritteAnzahl
  )
  {
    initParameterSchleife (startWert, endWert, schritteAnzahl);
  }
  
  
  public double nenneStartWert ()
  {
    return startWert;
  }
  
  public void setzeStartWert (double startWert)
  {
    this.startWert = startWert;
  }
  
  
  public double nenneEndWert ()
  {
    return endWert;
  }
  
  public void setzeEndWert (double endWert)
  {
    this.endWert = endWert;
  }
  
  
  public double nenneSpannweite ()
  {
    double spannweite = (endWert - startWert);
    
    return spannweite;
  }
  
  public void setzeSpannweite (double spannweite)
  {
    endWert = (startWert + spannweite);
  }
  
  
  public double nenneSchrittWeite ()
  {
    return schrittWeite;
  }
  
  public void setzeSchrittWeite (double schrittWeite)
  {
//    if (schrittWeite == 0.0)
//    {
//      String ausnahmeText = "Eine Schrittweite von 0 ist ungueltig.";
//      throw new IllegalArgumentException (ausnahmeText);
//    }
    
    this.schrittWeite = schrittWeite;
  }
  
  
  public int nenneSchritteAnzahl ()
  {
    int    schritteAnzahl = 0;
    double schritteDouble = 0.0;
    double spannweite     = nenneSpannweite ();
    
    if (schrittWeite != 0.0)
    {
      // Anzahl an Abschnitten (immer um Eins kleiner als Schrittezahl).
      schritteDouble = Math.ceil  (spannweite / schrittWeite);
      schritteAnzahl = new Double (schritteDouble).intValue ();
      schritteAnzahl = (schritteAnzahl + 1);
    }
    else
    {
      schritteAnzahl = 0;
    }
    
    return schritteAnzahl;
  }
  
  // Muss >= 0 sein.
  public void setzeSchritteAnzahl (int schritteAnzahl)
  {
    if (schritteAnzahl < MINIMUM_SCHRITTE_ANZAHL)
    {
      String ausnahmeText = String.format
      (
        "Der Wert %d ist als Schritteanzahl zu gering. Er muss " +
        "mindestens %d betragen.",
        schritteAnzahl, MINIMUM_SCHRITTE_ANZAHL
      );
      throw new IllegalArgumentException (ausnahmeText);
    }
    
    int    zerlegungen    = (schritteAnzahl - 1);
    double schritteDouble = new Integer (zerlegungen).doubleValue ();
    double spannweite     = nenneSpannweite ();
    
    /* Verhindert Division durch 0.
     * Ausserdem: Falls Schritteanzahl 0 (oder sogar 1) ist, hat die
     *            Schrittweite keine Auswirkung.
     */
    if (zerlegungen >= 1)
    {
      schrittWeite = (spannweite / schritteDouble);
    }
  }
  
  
  public double[] nenneParameterWerte ()
  {
    double[] parameterWerte = null;
    int      schritteAnzahl = nenneSchritteAnzahl ();
    double   wert           = startWert;
    
    parameterWerte = new double[schritteAnzahl];
    
    for (int schrittIndex = 0; schrittIndex < schritteAnzahl;
             schrittIndex++)
    {
      parameterWerte[schrittIndex] = wert;
      wert                         = (wert + schrittWeite);
      
      
      //////////////////////////////////////////////////////////////////
      boolean letzterWertFolgt = (schrittIndex == (schritteAnzahl - 2));
      // Korrigiert Rundungsfehler, die Endwert nicht erreichen lassen.
      if (letzterWertFolgt)
      {
        wert = endWert;
      }
      //////////////////////////////////////////////////////////////////
    }
    
    return parameterWerte;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String alsZeiket = String.format
    (
      "ParameterSchleife(start=%f; ende=%f; schrittweite=%f; " +
                        "schritteAnzahl=%d)",
      startWert, endWert, schrittWeite, nenneSchritteAnzahl ()
    );
    
    return alsZeiket;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initParameterSchleife
  (
    double startWert,
    double endWert,
    double schrittWeite
  )
  {
    this.startWert    = startWert;
    this.endWert      = endWert;
    this.schrittWeite = schrittWeite;
  }
  
  private void initParameterSchleife
  (
    double startWert,
    double endWert,
    int    schritteAnzahl
  )
  {
    initParameterSchleife (startWert, endWert, 0.1);
    setzeSchritteAnzahl   (schritteAnzahl);
  }
  
  
  
  
  
  public static void main (String[] args)
  {
    ParameterSchleife schleife = new ParameterSchleife ();
    
    schleife.setzeStartWert      (0.0);
    schleife.setzeEndWert        (1.0);
    schleife.setzeSchrittWeite   (0.2);
    schleife.setzeSchritteAnzahl (4);
    
    System.out.println (schleife.nenneSchritteAnzahl ());
    
    double[] paramWerte = schleife.nenneParameterWerte ();
    
    for (double wert : paramWerte)
    {
      System.out.println (wert);
    }
    
    System.out.println (schleife);
  }
}