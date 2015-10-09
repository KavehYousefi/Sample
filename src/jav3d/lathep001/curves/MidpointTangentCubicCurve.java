// 19.05.2014

package jav3d.lathep001.curves;

import ky.Position;
import jav3d.lathep001.ParameterSchleife;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3d;


// Constructs curve from TWO ENDPOINTS, A MIDPOINT AND THE TANGENT AT THE MIDPOINT.
// -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
// Unnamed example curve under the paragraph "A First Example".
public class MidpointTangentCubicCurve
{
  private Position          startPoint;
  private Position          midPoint;
  private Vector3d          midPointTangent;
  private Position          endPoint;
  private ParameterSchleife schleife;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a midpoint tangent cubic curve with default values.
   * </div>
   * 
   * <div>
   *   <table>
   *     <tr>
   *       <th>Property</th>
   *       <th>Default value</th>
   *     </tr>
   *     <tr>
   *       <td>Start point</td>
   *       <td><code>Position (0.0, 0.0, 0.0)</code></td>
   *     </tr>
   *     <tr>
   *       <td>Midpoint</td>
   *       <td><code>Position (0.0, 0.0, 0.0)</code></td>
   *     </tr>
   *     <tr>
   *       <td>Tangent at midpoint</td>
   *       <td><code>Vector3d (0.0, 0.0, 0.0)</code></td>
   *     </tr>
   *     <tr>
   *       <td>End point</td>
   *       <td><code>Position (0.0, 0.0, 0.0)</code></td>
   *     </tr>
   *     <tr>
   *       <td>Parameter iteration</td>
   *       <td>Interval: [0,0; 1,0]; step size: 0,1</td>
   *     </tr>
   *   </table>
   * </div>
   */
  public MidpointTangentCubicCurve ()
  {
    this.startPoint      = new Position ();
    this.midPoint        = new Position ();
    this.midPointTangent = new Vector3d ();
    this.endPoint        = new Position ();
    this.schleife        = new ParameterSchleife (0.0, 1.0, 0.1);
  }
  
  
  public Position getStartPoint ()
  {
    return new Position (startPoint);
  }
  
  public void setStartPoint (double x, double y, double z)
  {
    startPoint = new Position (x, y, z);
  }
  
  
  public Position getMidpoint ()
  {
    return new Position (midPoint);
  }
  
  public void setMidpoint (double x, double y, double z)
  {
    midPoint = new Position (x, y, z);
  }
  
  
  public Position getMidpointTangent ()
  {
    return new Position (midPointTangent);
  }
  
  public void setMidpointTangent (double x, double y, double z)
  {
    midPointTangent = new Vector3d (x, y, z);
  }
  
  
  public Position getEndPoint ()
  {
    return new Position (endPoint);
  }
  
  public void setEndPoint (double x, double y, double z)
  {
    endPoint = new Position (x, y, z);
  }
  
  
  public ParameterSchleife nenneParameterSchleife ()
  {
    return schleife;
  }
  
  public void setzeParameterSchleife (ParameterSchleife schleife)
  {
    this.schleife = schleife;
  }
  
  
  public List<Position> getPointsOnCurve ()
  {
    List<Position> punkteListe = new ArrayList<Position>      ();
    double[]       tValues     = schleife.nenneParameterWerte ();
    
    for (double t : tValues)
    {
      Position pointOnCurve = calculatePointOnCurve
      (
        t,
        startPoint,
        midPoint,
        midPointTangent,
        endPoint
      );
      
      punkteListe.add (pointOnCurve);
    }
    
    return punkteListe;
  }
  
  // Mapping: t -> Point3d.
  public Map<Double, Position> getParameterPointMapForPointsOnCurve ()
  {
    Map<Double, Position> punkteMap = new LinkedHashMap<Double, Position> ();
    double[]              tValues   = schleife.nenneParameterWerte ();
    
    for (double t : tValues)
    {
      Position pointOnCurve = calculatePointOnCurve
      (
        t,
        startPoint,
        midPoint,
        midPointTangent,
        endPoint
      );
      
      punkteMap.put (t, pointOnCurve);
    }
    
    return punkteMap;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of the static methods.                      -- //
  //////////////////////////////////////////////////////////////////////
  
  // Blending function for the start point (in source: P0).
  public static double basisFunctionF1 (double t)
  {
    double ergebnis       = 0.0;
    double zwei_t         = (t + t);           // 2t
    double vier_t         = (zwei_t + zwei_t); // 4t
    double fuenf_t        = (vier_t + t);      // 5t
    double t_hoch_2       = (t * t);           //  t^2
    double acht_t_hoch_2  = (8.0 * t_hoch_2);  // 8t^2
    double t_hoch_3       = (t_hoch_2 * t);    //  t^3
    double vier_t_hoch_3  = (4.0 * t_hoch_3);  // 4t^3
    
    ergebnis = -vier_t_hoch_3 + acht_t_hoch_2  - fuenf_t + 1.0;
    
    return ergebnis;
  }
  
  // Blending function for the midpoint (in source: P0.5).
  public static double basisFunctionF2 (double t)
  {
    double ergebnis      = 0.0;
    double zwei_t        = (t + t);           // 2t
    double vier_t        = (zwei_t + zwei_t); // 4t
    double t_hoch_2      = (t * t);           //  t^2
    double vier_t_hoch_2 = (4.0 * t_hoch_2);  // 4t^2
    
    ergebnis = -vier_t_hoch_2  + vier_t;
    
    return ergebnis;
  }
  
  // Blending function for the tangent at the midpoint (in source: T0.5).
  public static double basisFunctionF3 (double t)
  {
    double ergebnis       = 0.0;
    double zwei_t         = (t + t);           // 2t
    double t_hoch_2       = (t * t);           //  t^2
    double sechs_t_hoch_2 = (6.0 * t_hoch_2);  // 6t^2
    double t_hoch_3       = (t_hoch_2 * t);    //  t^3
    double vier_t_hoch_3  = (4.0 * t_hoch_3);  // 4t^3
    
    ergebnis = -vier_t_hoch_3 + sechs_t_hoch_2 - zwei_t;
    
    return ergebnis;
  }
  
  // Blending function for the end point (in source: P1).
  // ATTENTION: The source mistakenly uses the last term "1", not "t".
  public static double basisFunctionF4 (double t)
  {
    double ergebnis      = 0.0;
    double t_hoch_2      = (t * t);           //  t^2
    double vier_t_hoch_2 = (4.0 * t_hoch_2);  // 4t^2
    double t_hoch_3      = (t_hoch_2 * t);    //  t^3
    double vier_t_hoch_3 = (4.0 * t_hoch_3);  // 4t^3
    
    ergebnis = (vier_t_hoch_3) - (vier_t_hoch_2) + (t);
    
    return ergebnis;
  }
  
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  /* ACHTUNG: In der Formel der Webseite ist ein Fehler in der 
   *          Blending-Function f4:
   *            f4 = 4t^3 - 4t^2 + 1
   *                               ^
   *                               |- Muss "t" lauten!!!
   *          
   *          Der Fehler kann erkannt werden, wenn man sich die
   *          darueber stehende, korrekte Matrix anschaut:
   *                [ -4  0 -4  4 ]  <- Zeile fuer Koeffizienten von t^3
   *            M = [  8 -4  6 -4 ]  <- Zeile fuer Koeffizienten von t^2
   *                [ -5  4 -2  1 ]  <- Zeile fuer Koeffizienten von t^1 (*)
   *                [  1  0  0  0 ]  <- Zeile fuer Koeffizienten von t^0 (*)
   *          
   *          Jede Spalte steht fuer eine Blending-Function. Die vierte
   *          Spalte gehoert also zu "f1".
   *          Jede Zeile steht fuer den Koeffizienten einer t-Potenz:
   *            Erste  Zeile: t^3
   *            Zweite Zeile: t^2
   *            Dritte Zeile: t^1 = t
   *            Vierte Zeile: t^0 = 1
   *          Der in der Spalte vier, Zeile drei bedeutet also 1 * t.
   *          Stattdessen weist die falsche Formel 1 zu.
   *          
   *          Die falsche  Formel sei wiederholt:
   *            f4 = 4t^3 - 4t^2 + 1
   *          ********************************
   *          Die richtige Formel lautet also:
   *            f4 = 4t^3 - 4t^2 + t
   *          ********************************
   */
  public static double[] basisFunctionsF1ToF4 (double t)
  {
    double[] fWerte         = null;
    double   f1             = 0.0;
    double   f2             = 0.0;
    double   f3             = 0.0;
    double   f4             = 0.0;
    double   zwei_t         = (t + t);            // 2t
    double   vier_t         = (zwei_t + zwei_t);  // 4t
    double   fuenf_t        = (vier_t + t);       // 5t
    double   t_hoch_2       = (t * t);            //  t^2
    double   vier_t_hoch_2  = (4.0 * t_hoch_2);   // 4t^2
    double   sechs_t_hoch_2 = (6.0 * t_hoch_2);   // 6t^2
    double   acht_t_hoch_2  = (8.0 * t_hoch_2);   // 8t^2
    double   t_hoch_3       = (t_hoch_2 * t);     //  t^3
    double   vier_t_hoch_3  = (4.0 * t_hoch_3);   // 4t^3
    
    f1     = (-vier_t_hoch_3) + ( acht_t_hoch_2 ) - (fuenf_t) + 1.0;
    f2     =                    (-vier_t_hoch_2 ) + (vier_t );
    f3     = (-vier_t_hoch_3) + ( sechs_t_hoch_2) - (zwei_t );
    f4     = ( vier_t_hoch_3) - ( vier_t_hoch_2 ) + (t      );
    fWerte = new double[] {f1, f2, f3, f4};
    
    return fWerte;
  }
  
  
  /* GETESTET -- FUNKTIONIERT */
  /**
   * <div class="einleitungMethode">
   *   Berechnet einen Punkt auf einer &uuml;ber Startpunkt,
   *   Mittelpunkt, Mittelpunkt-Tangente und Endpunkt definierten
   *   kubischen Kurve im dreidimensionalen Raum.
   * </div>
   * 
   * @param t   Der laufende Parameter       <i>t</i>.
   * @param p1  Der Startpunkt               <i>p1</i>.
   * @param m   Der Mittelpunkt              <i>m</i>.
   * @param tm  Die Tangente zum Mittelpunkt <i>tm</i>.
   * @param p2  Der Endpunkt                 <i>p2</i>
   * 
   * @return    Einen auf der Kurve liegenden Punkt
   *            P(<i>x</i>; <i>y</i>; <i>z</i>) im dreidimensionalen
   *            Raum.
   */
  public static Position calculatePointOnCurve
  (
    double   t,
    Position p1,  // Startpunkt  P0.
    Position m,   // Mittelpunkt P0.5.
    Vector3d tm,  // Tangente    T0.5.
    Position p2   // Endpunkt    P1.
  )
  {
    Position pAuf   = null;
    double   xAuf   = 0.0;
    double   yAuf   = 0.0;
    double   zAuf   = 0.0;
    double   p1_x   = p1.getX ();
    double   p1_y   = p1.getY ();
    double   p1_z   = p1.getZ ();
    double   m_x    = m.getX ();
    double   m_y    = m.getY ();
    double   m_z    = m.getZ ();
    double   tm_x   = tm.getX ();
    double   tm_y   = tm.getY ();
    double   tm_z   = tm.getZ ();
    double   p2_x   = p2.getX ();
    double   p2_y   = p2.getY ();
    double   p2_z   = p2.getZ ();
    double[] fWerte = basisFunctionsF1ToF4 (t);
    double   f1     = fWerte[0];
    double   f2     = fWerte[1];
    double   f3     = fWerte[2];
    double   f4     = fWerte[3];
    
    xAuf = (f1 * p1_x) +
           (f2 * m_x ) +
           (f3 * tm_x) +
           (f4 * p2_x);
    yAuf = (f1 * p1_y) +
           (f2 * m_y ) +
           (f3 * tm_y) +
           (f4 * p2_y);
    zAuf = (f1 * p1_z) +
           (f2 * m_z ) +
           (f3 * tm_z) +
           (f4 * p2_z);
     
    pAuf = new Position (xAuf, yAuf, zAuf);
    
    return pAuf;
  }
}