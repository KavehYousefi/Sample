// 19.05.2014

package jav3d.lathep001.curves;

import ky.Position;
import jav3d.lathep001.ParameterSchleife;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BezierCurve
{
  public static final int   PUNKTE_ANZAHL = 4;
  
  private Position          startPoint;
  private Position          controlPoint1;
  private Position          controlPoint2;
  private Position          endPoint;
  private ParameterSchleife schleife;
  
  
  /**
   * <div class="einleitungKonstruktor">
   *   Konstruiert eine Bézier-Kurve mit Standardwerten.
   * </div>
   * 
   * <div>
   *   <table>
   *     <tr>
   *       <th>Attribut</th>
   *       <th>Standardwert</th>
   *     </tr>
   *     <tr>
   *       <td>Startpunkt</td>
   *       <td>P(0,0; 0,0; 0,0)</td>
   *     </tr>
   *     <tr>
   *       <td>Erster Kontrollpunkt</td>
   *       <td>P(0,0; 0,0; 0,0)</td>
   *     </tr>
   *     <tr>
   *       <td>Zweiter Kontrollpunkt</td>
   *       <td>P(0,0; 0,0; 0,0)</td>
   *     </tr>
   *     <tr>
   *       <td>Endpunkt</td>
   *       <td>P(0,0; 0,0; 0,0)</td>
   *     </tr>
   *     <tr>
   *       <td>Parameter-Schleife</td>
   *       <td>Intervall: [0,0; 1,0]; Schrittweite: 0,1</td>
   *     </tr>
   *   </table>
   * </div>
   */
  public BezierCurve ()
  {
    this.startPoint    = new Position ();
    this.controlPoint1 = new Position ();
    this.controlPoint2 = new Position ();
    this.endPoint      = new Position ();
    this.schleife      = new ParameterSchleife (0.0, 1.0, 0.1);
  }
  
  
  public Position nenneStartPunkt ()
  {
    return new Position (startPoint);
  }
  
  public void setzeStartPunkt (double x, double y, double z)
  {
    startPoint = new Position (x, y, z);
  }
  
  public void setzeStartPunkt (Position startPunkt)
  {
    this.startPoint = startPunkt;
  }
  
  
  public Position nenneErstenKontrollPunkt ()
  {
    return new Position (controlPoint1);
  }
  
  public void setzeErstenKontrollPunkt (double x, double y, double z)
  {
    controlPoint1 = new Position (x, y, z);
  }
  
  public void setzeErstenKontrollPunkt (Position ersterKontrollPunkt)
  {
    this.controlPoint1 = ersterKontrollPunkt;
  }
  
  
  public Position nenneZweitenKontrollPunkt ()
  {
    return new Position (controlPoint2);
  }
  
  public void setzeZweitenKontrollPunkt (double x, double y, double z)
  {
    controlPoint2 = new Position (x, y, z);
  }
  
  public void setzeZweitenKontrollPunkt (Position zweiterKontrollPunkt)
  {
    this.controlPoint2 = zweiterKontrollPunkt;
  }
  
  
  public Position nenneEndPunkt ()
  {
    return new Position (endPoint);
  }
  
  public void setzeEndPunkt (double x, double y, double z)
  {
    endPoint = new Position (x, y, z);
  }
  
  public void setzeEndPunkt (Position endPunkt)
  {
    this.endPoint = endPunkt;
  }
  
  
  public void setzePunkte (Position[] positionenArray)
  {
    if (positionenArray != null)
    {
      int positionenLaenge = positionenArray.length;
      int punkteAnzahl     = Math.min (positionenLaenge, PUNKTE_ANZAHL);
      
      for (int punktIndex = 0; punktIndex < punkteAnzahl; punktIndex++)
      {
        Position aktuellePosition = positionenArray[punktIndex];
        
        if (aktuellePosition != null)
        {
          switch (punktIndex)
          {
            case 0 :
            {
              startPoint = aktuellePosition;
              break;
            }
            case 1 :
            {
              controlPoint1 = aktuellePosition;
              break;
            }
            case 2 :
            {
              controlPoint2 = aktuellePosition;
            }
            case 3 :
            {
              endPoint = aktuellePosition;
              break;
            }
            default :
            {
              break;
            }
          }
        }
      }
    }
  }
  
  
  public ParameterSchleife nenneParameterSchleife ()
  {
    return schleife;
  }
  
  public void setzeParameterSchleife (ParameterSchleife schleife)
  {
    this.schleife = schleife;
  }
  
  
  public List<Position> nennePunkte ()
  {
    List<Position> punkteListe = new ArrayList<Position>      ();
    double[]       tWerte      = schleife.nenneParameterWerte ();
    
    for (double t : tWerte)
    {
      Position punkt = berechnePunktAufBezierKurve3D
      (
        t,
        startPoint,
        controlPoint1,
        controlPoint2,
        endPoint
      );
      
      punkteListe.add (punkt);
    }
    
    return punkteListe;
  }
  
  // Abbildung: t -> Point3d.
  public Map<Double, Position> nennePunkteMap ()
  {
    Map<Double, Position> punkteMap = new LinkedHashMap<Double, Position> ();
    double[]              tWerte    = schleife.nenneParameterWerte ();
    
    for (double t : tWerte)
    {
      Position punkt = berechnePunktAufBezierKurve3D
      (
        t,
        startPoint,
        controlPoint1,
        controlPoint2,
        endPoint
      );
      
      punkteMap.put (t, punkt);
    }
    
    return punkteMap;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Umsetzung der statischen Methoden.                         -- //
  //////////////////////////////////////////////////////////////////////
  
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  public static double basisFunctionF1 (double t)
  {
    double ergebnis = 0.0;
    double t_hoch_2 = (t * t);
    double t_hoch_3 = (t_hoch_2 * t);
    
    ergebnis = (-t_hoch_3) + (3.0 * t_hoch_2) - (3.0 * t) + 1.0;
    
    return ergebnis;
  }
  
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  public static double basisFunctionF2 (double t)
  {
    double ergebnis = 0.0;
    double t_hoch_2 = (t * t);
    double t_hoch_3 = (t_hoch_2 * t);
    
    ergebnis = (3.0 * t_hoch_3) - (6.0 * t_hoch_2) + (3.0 * t);
    
    return ergebnis;
  }
  
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  public static double basisFunctionF3 (double t)
  {
    double ergebnis = 0.0;
    double t_hoch_2 = (t * t);
    double t_hoch_3 = (t_hoch_2 * t);
    
    ergebnis = (-3.0 * t_hoch_3) + (3.0 * t_hoch_2);
    
    return ergebnis;
  }
  
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  public static double basisFunctionF4 (double t)
  {
    double ergebnis = 0.0;
    double t_hoch_2 = (t * t);
    double t_hoch_3 = (t_hoch_2 * t);
    
    ergebnis = t_hoch_3;
    
    return ergebnis;
  }
  
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  public static double[] basisFunctionsF1ToF4 (double t)
  {
    double[] fWerte         = null;
    double   f1             = 0.0;
    double   f2             = 0.0;
    double   f3             = 0.0;
    double   f4             = 0.0;
    double   drei_t         = (3.0 * t);         // 3t
    double   t_hoch_2       = (t * t);           //  t^2
    double   drei_t_hoch_2  = (3.0 * t_hoch_2);  // 3t^2
    double   sechs_t_hoch_2 = (6.0 * t_hoch_2);  // 6t^2
    double   t_hoch_3       = (t_hoch_2 * t);    //  t^3
    double   drei_t_hoch_3  = (3.0 * t_hoch_3);  // 3t^3
    
    f1     = (     -t_hoch_3) + (drei_t_hoch_2 ) - (drei_t) + 1.0;
    f2     = ( drei_t_hoch_3) - (sechs_t_hoch_2) + (drei_t);
    f3     = (-drei_t_hoch_3) + (drei_t_hoch_2 );
    f4     = (      t_hoch_3);
    fWerte = new double[] {f1, f2, f3, f4};
    
    return fWerte;
  }
  
  /* GETESTET -- FUNKTIONIERT */
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  /**
   * <div class="einleitungMethode">
   *   Berechnet einen Punkt auf einer Bézier-Kurve im
   *   dreidimensionalen Raum.
   * </div>
   * 
   * @param t   Der laufende Parameter   <i>t</i>.
   * @param p1  Der Startpunkt           <i>p1</i>.
   * @param p1  Der erste Kontrollpunkt  <i>p2</i>.
   * @param p3  Der zweite Kontrollpunkt <i>p3</i>.
   * @param p4  Der Endpunkt             <i>p4</i>
   * 
   * @return    Einen auf der Bézier-Kurve liegenden Punkt
   *            P(<i>x</i>; <i>y</i>; <i>z</i>) im dreidimensionalen
   *            Raum.
   */
  public static Position berechnePunktAufBezierKurve3D
  (
    double  t,
    Position p1,  // Startpunkt.
    Position p2,  // Kontrollpunkt 1.
    Position p3,  // Kontrollpunkt 2.
    Position p4   // Endpunkt.
  )
  {
    Position pAuf   = null;
    double   xAuf   = 0.0;
    double   yAuf   = 0.0;
    double   zAuf   = 0.0;
    double   p1_x   = p1.getX ();
    double   p1_y   = p1.getY ();
    double   p1_z   = p1.getZ ();
    double   p2_x   = p2.getX ();
    double   p2_y   = p2.getY ();
    double   p2_z   = p2.getZ ();
    double   p3_x   = p3.getX ();
    double   p3_y   = p3.getY ();
    double   p3_z   = p3.getZ ();
    double   p4_x   = p4.getX ();
    double   p4_y   = p4.getY ();
    double   p4_z   = p4.getZ ();
    double[] fWerte = basisFunctionsF1ToF4 (t);
    double   f1     = fWerte[0];
    double   f2     = fWerte[1];
    double   f3     = fWerte[2];
    double   f4     = fWerte[3];
    
    xAuf = (f1 * p1_x) +
           (f2 * p2_x) +
           (f3 * p3_x) +
           (f4 * p4_x);
    yAuf = (f1 * p1_y) +
           (f2 * p2_y) +
           (f3 * p3_y) +
           (f4 * p4_y);
    zAuf = (f1 * p1_z) +
           (f2 * p2_z) +
           (f3 * p3_z) +
           (f4 * p4_z);
     
    pAuf = new Position (xAuf, yAuf, zAuf);
    
    return pAuf;
  }
}