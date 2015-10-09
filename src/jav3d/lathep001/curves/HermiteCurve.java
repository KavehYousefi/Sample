// 18.05.2014

package jav3d.lathep001.curves;

import ky.Position;
import jav3d.lathep001.ParameterSchleife;
import safercode.CheckingUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.vecmath.Vector3d;


public class HermiteCurve
{
  private Position          startPoint;
  private Vector3d          startPointTangent;
  private Position          endPoint;
  private Vector3d          endPointTangent;
  private ParameterSchleife schleife;
  
  
  /**
   * <div class="einleitungKonstruktor">
   *   Konstruiert eine Hermite-Kurve mit Standardwerten.
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
   *       <td>Tangente zum Startpunkt</td>
   *       <td>P(0,0; 0,0; 0,0)</td>
   *     </tr>
   *     <tr>
   *       <td>Endpunkt</td>
   *       <td>P(0,0; 0,0; 0,0)</td>
   *     </tr>
   *     <tr>
   *       <td>Tangente zum Endpunkt</td>
   *       <td>P(0,0; 0,0; 0,0)</td>
   *     </tr>
   *     <tr>
   *       <td>Parameter-Schleife</td>
   *       <td>Intervall: [0,0; 1,0]; Schrittweite: 0,1</td>
   *     </tr>
   *   </table>
   * </div>
   */
  public HermiteCurve ()
  {
    this.startPoint        = new Position ();
    this.startPointTangent = new Vector3d ();
    this.endPoint          = new Position ();
    this.endPointTangent   = new Vector3d ();
    this.schleife          = new ParameterSchleife (0.0, 1.0, 0.1);
  }
  
  
  public Position getStartPoint ()
  {
    return new Position (startPoint);
  }
  
  public void setStartPoint (double x, double y, double z)
  {
    startPoint = new Position (x, y, z);
  }
  
  public void setStartPoint (Position punkt)
  {
    startPoint = punkt;
  }
  
  
  public Position getStartTangent ()
  {
    return new Position (startPointTangent);
  }
  
  public void setStartTangent (double x, double y, double z)
  {
    startPointTangent = new Vector3d (x, y, z);
  }
  
  public void setStartTangent (Vector3d punkt)
  {
    startPointTangent = punkt;
  }
  
  
  public Position getEndPoint ()
  {
    return new Position (endPoint);
  }
  
  public void setEndPoint (double x, double y, double z)
  {
    endPoint = new Position (x, y, z);
  }
  
  public void setEndPoint (Position punkt)
  {
    endPoint = punkt;
  }
  
  
  public Position getEndTangent ()
  {
    return new Position (endPointTangent);
  }
  
  public void setEndTangent (double x, double y, double z)
  {
    endPointTangent = new Vector3d (x, y, z);
  }
  
  public void setEndTangent (Vector3d punkt)
  {
    endPointTangent = punkt;
  }
  
  
  public void setFromPositionList (List<Position> positionList)
  {
    CheckingUtils.checkForNull (positionList, "Position list is null.");
    
    startPoint        = positionList.get (0);
    startPointTangent = positionList.get (1).getAsVector3d ();
    endPoint          = positionList.get (2);
    endPointTangent   = positionList.get (3).getAsVector3d ();
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
      Position punkt = calculatePointOnHermiteCurve3D
      (
        t,
        startPoint, startPointTangent, endPoint, endPointTangent
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
      Position punkt = calculatePointOnHermiteCurve3D
      (
        t,
        startPoint, startPointTangent, endPoint, endPointTangent
      );
      
      punkteMap.put (t, punkt);
    }
    
    return punkteMap;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Umsetzung der statischen Methoden.                         -- //
  //////////////////////////////////////////////////////////////////////
  
  public static double blendingFunctionFh1 (double t)
  {
    double ergebnis = 0.0;
    double tHoch2   = (t      * t);
    double tHoch3   = (tHoch2 * t);
    
    ergebnis = (2.0 * tHoch3) - (3.0 * tHoch2) + 1.0;
    
    return ergebnis;
  }
  
  public static double blendingFunctionFh2 (double t)
  {
    double ergebnis = 0.0;
    double tHoch2   = (t      * t);
    double tHoch3   = (tHoch2 * t);
    
    ergebnis = (-2.0 * tHoch3) + (3.0 * tHoch2);
    
    return ergebnis;
  }
  
  public static double blendingFunctionFh3 (double t)
  {
    double ergebnis = 0.0;
    double tHoch2   = (t      * t);
    double tHoch3   = (tHoch2 * t);
    
    ergebnis = tHoch3 - (2.0 * tHoch2) + t;
    
    return ergebnis;
  }
  
  public static double blendingFunctionFh4 (double t)
  {
    double ergebnis = 0.0;
    double tHoch2   = (t      * t);
    double tHoch3   = (tHoch2 * t);
    
    ergebnis = (tHoch3 - tHoch2);
    
    return ergebnis;
  }
  
  
  // Array mit den obigen Basisfunktionen fh1, fh2, fh3, fh4.
  public static double[] blendingFunctionsFh1To4 (double t)
  {
    double[] fhWerte  = null;
    double   t_hoch_2 = (t * t);
    double   t_hoch_3 = (t_hoch_2 * t);
    double   f1       = (( 2.0 * t_hoch_3) - (3.0 * t_hoch_2) + 1.0);
    double   f2       = ((-2.0 * t_hoch_3) + (3.0 * t_hoch_2)      );
    double   f3       = ((       t_hoch_3) - (2.0 * t_hoch_2) + t  );
    double   f4       = ((       t_hoch_3) - (      t_hoch_2)      );
    
    fhWerte = new double[]
    {
      f1, f2, f3, f4
    };
    
    return fhWerte;
  }
  
  
  
  
  
  
  
  
  
  
  /////
  // UNBENUTZT, ABER ERFOLGREICH GETESTET.
  // AUS "j3dtests.FunktionsGraph001".
  ////
  // 30.12.2012
  // -> "http://fivedots.coe.psu.ac.th/Software.coe/Java%20Games/Java3D/Muscle3D/Hermite/Hermite%20Curve%20Interpolation.htm"
  // -> "http://www.cs.helsinki.fi/group/goa/mallinnus/curves/curves.html"
  // -> "http://www.cs.nyu.edu/~kim7792/java/graphics/spline.html"
  /**
   * <div class="introMethod">
   *   Calculates a point on the Hermite curve.
   * </div>
   * 
   * @param t   The parameter <i>t</i>.
   * @param p1  The start point <i>p1</i>.
   * @param r1  The tangent at the start point <i>p1</i>.
   * @param p2  The end point <i>p2</i>.
   * @param r2  The tangent at the end point <i>p2</i>.
   * 
   * @return    A point on the Hermite curve.
   */
  public static Position calculatePointOnHermiteCurve3D
  (
    double  t,
    Position p1,
    Vector3d r1,
    Position p2,
    Vector3d r2
  )
  {
    Position pAuf   = null;
    double   xAuf   = 0.0;
    double   yAuf   = 0.0;
    double   zAuf   = 0.0;
    double   p1_x   = p1.getX ();
    double   p1_y   = p1.getY ();
    double   p1_z   = p1.getZ ();
    double   r1_x   = r1.getX ();
    double   r1_y   = r1.getY ();
    double   r1_z   = r1.getZ ();
    double   p2_x   = p2.getX ();
    double   p2_y   = p2.getY ();
    double   p2_z   = p2.getZ ();
    double   r2_x   = r2.getX ();
    double   r2_y   = r2.getY ();
    double   r2_z   = r2.getZ ();
    double[] fWerte = blendingFunctionsFh1To4 (t);
    // --> Die vier Basisfunktionen werden angelegt. -------------------
    double   f1     = fWerte[0];
    double   f2     = fWerte[1];
    double   f3     = fWerte[2];
    double   f4     = fWerte[3];
    // <-- Die vier Basisfunktionen wurden angelegt-. ------------------
    
    xAuf = (f1 * p1_x) +
           (f2 * p2_x) +
           (f3 * r1_x) +
           (f4 * r2_x);
    yAuf = (f1 * p1_y) +
           (f2 * p2_y) +
           (f3 * r1_y) +
           (f4 * r2_y);
    zAuf = (f1 * p1_z) +
           (f2 * p2_z) +
           (f3 * r1_z) +
           (f4 * r2_z);
    
    pAuf = new Position (xAuf, yAuf, zAuf);
    
    return pAuf;
  }
}