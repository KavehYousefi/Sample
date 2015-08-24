package commies.angle;

import safercode.CheckingUtils;

import java.lang.Comparable;


/**
 * <div class="introClass">
 *   An <code>Angle</code> object represents a combination of an
 *   angle size and an angle unit.
 * </div>
 * 
 * <div>
 *   This class can be regarded as an abstraction of angle handling;
 *   in fact, it started with the purpose of avoiding repeated
 *   conversions between degree and
 *   radian.
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
 *       Accessed: 08.03.2013
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
 *       Angle.java
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
 *       08.03.2013
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
public class Angle
implements   Comparable<Angle>
{
  private static final AngleUnit DEFAULT_UNIT = AngleUnit.DEGREE;
  private static final double    DEFAULT_SIZE = 0.0;
  
  private AngleUnit angleUnit;
  private double    angleSize;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an angle of given unit and size.
   * </div>
   * 
   * @param unit  The angle unit. It must not be <code>null</code>.
   * @param size  The angle size.
   * 
   * @throws NullPointerException  If the <code>unit</code> equals
   *                               <code>null</code>.
   */
  public Angle (AngleUnit unit, double size)
  {
    initAngle (unit, size);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates an angle of the given size in degrees
   *   (<code>AngleUnit.DEGREE</code>).
   * </div>
   */
  public Angle (double size)
  {
    initAngle (DEFAULT_UNIT, size);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a new angle by copying a given one.
   * </div>
   * 
   * @param otherAngle  The angle to copy.
   * 
   * @throws NullPointerException  If the <code>otherAngle</code>
   *                               equals <code>null</code>.
   */
  public Angle (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    AngleUnit hisAngleUnit  = otherAngle.getUnit ();
    double    thisAngleSize = otherAngle.getSize ();
    
    initAngle (hisAngleUnit, thisAngleSize);
  }
  
  /**
   * <div class="introConstructor">
   *   Given a source unit and source angle size, a new angle of the
   *   desired destination unit is created.
   * </div>
   * 
   * @param destinationUnit  The angle unit for the angle to create.
   *                         It may not be <code>null</code>.
   * @param sourceUnit       The angle unit of the
   *                         <code>sourceSize</code>.
   *                         It may not be <code>null</code>.
   * @param sourceSize       The angle size to convert from the
   *                         <code>sourceUnit</code> to the
   *                         <code>destinationUnit</code>.
   * 
   * @throws NullPointerException  If <code>destinationUnit</code> or
   *                               <code>sourceUnit</code> equal
   *                               <code>null</code>.
   */
  public Angle
  (
    AngleUnit destinationUnit,
    AngleUnit sourceUnit,
    double    sourceSize
  )
  {
    CheckingUtils.checkForNull
    (
      destinationUnit,  "No destination angle unit given."
    );
    CheckingUtils.checkForNull
    (
      sourceUnit, "No source angle unit given."
    );
    
    // Convert the angle size from the source to the destination unit.
    double destinationSize = AngleUtils.convert
    (
      sourceSize,
      sourceUnit,
      destinationUnit
    );
    
    initAngle (destinationUnit, destinationSize);
  }
  
  /**
   * <div class="introConstructor">
   *   Creates an angle of default unit degrees and default size zero.
   * </div>
   */
  public Angle ()
  {
    initAngle (DEFAULT_UNIT, DEFAULT_SIZE);
  }
  
  /**
   * <div class="introMethod">
   *   Returns the angle unit.
   * </div>
   * 
   * @return  The unit.
   */
  public AngleUnit getUnit ()
  {
    return angleUnit;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the angle unit, keeping the angle size.
   * </div>
   * 
   * <div>
   *   This method does <strong>not</strong> convert the angle, thus
   *   the angle size remains unchanged. If you want to convert the
   *   angle, use the method <code>convertToUnit (...)</code>.
   * </div>
   * 
   * @param angleUnit  The new angle unit.
   */
  public void setUnit (AngleUnit angleUnit)
  {
    this.angleUnit = angleUnit;
  }
  
  /**
   * <div class="introMethod">
   *   Converts the angle to the given angle unit.
   * </div>
   * 
   * <div>
   *   This method usually changes the angle size. If you only need to
   *   change the angle unit, while keeping the size, use the method
   *   <code>setAngleUnit (...)</code>.
   * </div>
   * 
   * @param destinationUnit  The angle unit to convert this angle to.
   *                         It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>destinationUnit</code>
   *                               equals <code>null</code>.
   */
  public void convertToUnit (AngleUnit destinationUnit)
  {
    checkAngleUnit (destinationUnit);
    
    AngleUnit formerAngleUnit = null;
    
    formerAngleUnit = this.angleUnit;
    this.angleUnit  = destinationUnit;
    angleSize       = AngleUtils.convert
    (
      angleSize,
      formerAngleUnit,
      this.angleUnit
    );
  }
  
  /**
   * <div class="introMethod">
   *   Creates a new angle by taking this one and converting it to the
   *   given angle unit.
   * </div>
   * 
   * @param destinationUnit  The angle unit to convert this angle to.
   *                         It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>destinationUnit</code>
   *                               equals <code>null</code>.
   */
  public Angle getConvertedAngle (AngleUnit destinationUnit)
  {
    checkAngleUnit (destinationUnit);
    
    Angle convertedAngle = new Angle (this);
    
    convertedAngle.convertToUnit (destinationUnit);
    
    return convertedAngle;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the angle size in the current angle unit.
   * </div>
   * 
   * @return  The size in the current angle unit.
   */
  public double getSize ()
  {
    return angleSize;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the angle size.
   * </div>
   * 
   * @param angleSize  The new angle size.
   */
  public void setSize (double angleSize)
  {
    this.angleSize = angleSize;
  }
  
  /**
   * <div class="introMethod">
   *   Sets the angle size from the given value in radians, translating
   *   it into its own unit.
   * </div>
   * 
   * @param angleSizeInRadians  The angle size in radians to be
   *                            translated into this angle's unit.
   */
  public void setSizeFromRadians (double angleSizeInRadians)
  {
    angleSize = angleUnit.setFromRadians (angleSizeInRadians);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the angle size from the given value in degrees, translating
   *   it into its own unit.
   * </div>
   * 
   * @param angleSizeInDegrees  The angle size in degrees to be
   *                            translated into this angle's unit.
   */
  public void setSizeFromDegrees (double angleSizeInDegrees)
  {
    angleSize = angleUnit.setFromDegrees (angleSizeInDegrees);
  }
  
  /**
   * <div class="introMethod">
   *   Sets the angle size by translating the given size from the
   *   source unit to this one's.
   * </div>
   * 
   * @param sourceUnit  The unit to translate the
   *                    <code>sourceSize</code> from.
   *                    It may not be <code>null</code>.
   * @param sourceSize  The size to translate from the
   *                    <code>sourceUnit</code> into this angle's unit.
   * 
   * @throws NullPointerException  If <code>sourceUnit</code> equals
   *                               <code>null</code>.
   */
  public void setSizeFromUnitAndSize
  (
    AngleUnit sourceUnit,
    double    sourceSize
  )
  {
    checkAngleUnit (sourceUnit);
    
    angleSize = AngleUtils.convert
    (
      sourceSize,
      sourceUnit,
      this.angleUnit
    );
  }
  
  /**
   * <div class="introMethod">
   *   Sets the angle size from the given angle by translating its
   *   size from its unit to this angle's unit.
   * </div>
   * 
   * @param otherAngle  The angle whose size should be translated from
   *                    from its unit to this one's. It may not be
   *                    <code>null</code>.
   * @throws NullPointerException  If <code>otherAngle</code> equals
   *                               <code>null</code>.
   */
  public void setSizeFromAngle (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    AngleUnit hisUnit = otherAngle.getUnit ();
    double    hisSize = otherAngle.getSize ();
    
    setSizeFromUnitAndSize (hisUnit, hisSize);
  }
  
  
  
  /**
   * <div class="introMethod">
   *   Returns this angle's size in degrees.
   * </div>
   * 
   * @return  The angle size in degrees.
   */
  public double getSizeInDegrees ()
  {
    double sizeInDegrees  = 0.0;
    
    if (angleUnit != AngleUnit.DEGREE)
    {
      sizeInDegrees = angleUnit.getAsDegrees (angleSize);
    }
    else
    {
      sizeInDegrees = angleSize;
    }
    
    return sizeInDegrees;
  }
  
  /**
   * <div class="introMethod">
   *   Returns this angle's size in radians.
   * </div>
   * 
   * @return  The angle size in radians.
   */
  public double getSizeInRadians ()
  {
    double sizeInRadians = 0.0;
    
    if (angleUnit != AngleUnit.RADIAN)
    {
      sizeInRadians = angleUnit.getAsRadians (angleSize);
    }
    else
    {
      sizeInRadians = angleSize;
    }
    
    return sizeInRadians;
  }
  
  /**
   * <div class="introMethod">
   *   Returns this angle's size in a given angle unit.
   * </div>
   * 
   * @param destinationUnit  The angle unit to return this angle's
   *                         size in.
   *                         It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>destinationUnit</code>
   *                               equals <code>null</code>.
   */
  public double getSizeIn (AngleUnit destinationUnit)
  {
    checkAngleUnit (destinationUnit);
    
    double    sizeInDestinationUnit = 0.0;
    AngleUnit myAngleUnit           = this.angleUnit;
    
    // Wanted unit and own unit differ? => Convert and return.
    if (! myAngleUnit.equals (destinationUnit))
    {
      sizeInDestinationUnit = AngleUtils.convert
      (
        angleSize,
        myAngleUnit,
        destinationUnit
      );
    }
    // Want unit equals own unit? => Simply return the size.
    else
    {
      sizeInDestinationUnit = angleSize;
    }
    
    return sizeInDestinationUnit;
  }
  
  // +/ 04.04.2013
  // Formatiert als Winkel in dem angegebenen "winkelmass".
  public String getAsFormattedStringInUnit (AngleUnit angleUnit)
  {
    checkAngleUnit (angleUnit);
    
    String alsZeiket    = null;
    double winkelInMass = getSizeIn (angleUnit);
    
    alsZeiket = angleUnit.getFormattedString (winkelInMass);
    
    return alsZeiket;
  }
  
  
  // + 19.08.2013
  // Erzeugt neuen Winkel mit entsprechender Groesse in anderem Winkelmass.
//  public Angle schaffeWinkelIn (AngleUnit winkelmass)
//  {
//    Angle  neuerWinkel  = null;
//    double seineGroesse = getSizeIn (winkelmass);
//    
//    neuerWinkel = new Angle (winkelmass, seineGroesse);
//    
//    return neuerWinkel;
//  }
  
  
  //+/ 05.04.2013
  // Liefert als Zeiket wie "Winkelmass.formatiere (...)".
  public String getAsFormattedString ()
  {
    String alsZeiket = angleUnit.getFormattedString (angleSize);
    
    return alsZeiket;
  }
  
  // Get other angle's size in this angle's unit.
  public double getOtherAngleSizeInThisUnit (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    double hisSizeInMyUnit = 0.0;
    
    hisSizeInMyUnit = otherAngle.getSizeIn (angleUnit);
    
    return hisSizeInMyUnit;
  }
  
  
  //+|| 10.07.2013
  //     Sind die Winkelmasse gleich? -[Winkelgroesse wird ignoriert]-
  // Other name: "areOfEqualUnit"
  public boolean hasSameUnit (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    boolean   hasSameUnit = false;
    AngleUnit hisUnit     = otherAngle.getUnit ();
    
    hasSameUnit = angleUnit.equals (hisUnit);
    
    return hasSameUnit;
  }
  
  public boolean hasSameSize (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    boolean hasSameSize = false;
    double  hisSize     = 0.0;
    
    hisSize     = otherAngle.getSize ();
    hasSameSize = (this.angleSize == hisSize);
    
    return hasSameSize;
  }
  
  //+/ 11.03.2013
  //   Sind Winkelmass UND Winkelgroesse gleich?
  public boolean hasThisUnitAndSize
  (
    AngleUnit unitToCompare,
    double    sizeToCompare
  )
  {
    checkAngleUnit (unitToCompare);
    
    boolean entsprichtIhm = false;
    boolean massGleich    = this.angleUnit.equals (unitToCompare);
    boolean groesseGleich = (this.angleSize == sizeToCompare);
    
    entsprichtIhm = (massGleich && groesseGleich);
    
    return entsprichtIhm;
  }
  
  //+/ 11.03.2013
  //   Sind Winkelmass UND Winkelgroesse gleich?
  /**
   * <div class="einleitungMethode">
   *   Es wird gepr&uuml;ft, ob dieser und ein zweiter Winkel gleich
   *   sind, also von identischer Gr&ouml;&szlig;e und identischem
   *   Winkelma&szlig;.
   * </div>
   * 
   * @param otherAngle  Der auf Gleichheit zu pr&uuml;fende Winkel. Er darf
   *                nicht <code>null</code> sein.
   * 
   * @return        Einen Boole'schen Wert, welcher mitteilt, ob die
   *                beiden Winkel gleich sind:
   *                <table>
   *                  <tr>
   *                    <td>
   *                      <code>true</code>
   *                    </td>
   *                    <td>
   *                      Die beiden Winkel sind gleich gro&szlig; und
   *                      besitzen dasselbe Winkelma&szlig;.
   *                    </td>
   *                  </tr>
   *                  <tr>
   *                    <td>
   *                      <code>false</code>
   *                    </td>
   *                    <td>
   *                      Die beiden Winkel sind unterschiedlich auf
   *                      Grund ihrer Gr&ouml;&szlig;e und/oder ihres
   *                      Winkelma&szlig;es.
   *                    </td>
   *                  </tr>
   *                </table>
   * 
   * @throws NullPointerException  Bei Angabe von <code>null</code>
   *                               f&uuml;r das Argument
   *                               <code>winkel</code>.
   */
  public boolean hasSameUnitAndSize (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    boolean   entsprichtIhm = false;
    AngleUnit seinMass      = otherAngle.getUnit ();
    double    seineGroesse  = otherAngle.getSize ();
    
    entsprichtIhm = hasThisUnitAndSize (seinMass, seineGroesse);
    
    return entsprichtIhm;
  }
  
  // +|| 10.07.2013
  //     Sind die Winkelgroessen gleich? -[Winkelmass wird ignoriert]-
  /**
   * <div class="einleitungMethode">
   *   Es wird gepr&uuml;ft, ob die Winkelgr&ouml;&szlig;e dieses
   *   Winkels - unabh&auml;gig vom Winkelma&szlig; - gleich jenem
   *   eines zweiten Winkels ist.
   * </div>
   * 
   * @param otherAngle  Der bez&uuml;glich Winkelgr&ouml;&szlig;e zu
   *                vergleichende Winkel. Er darf nicht
   *                <code>null</code> sein.
   * 
   * @return        Einen Boole'schen Wert, welcher mitteilt, ob die
   *                beiden Winkel gleich gro&szlig; sind:
   *                <table>
   *                  <tr>
   *                    <td>
   *                      <code>true</code>
   *                    </td>
   *                    <td>
   *                      Die beiden Winkel sind gleich gro&szlig;.
   *                    </td>
   *                  </tr>
   *                  <tr>
   *                    <td>
   *                      <code>false</code>
   *                    </td>
   *                    <td>
   *                      Die beiden Winkel sind von unterschiedlicher
   *                      Gr&ouml;&szlig;e.
   *                    </td>
   *                  </tr>
   *                </table>
   * 
   * @throws NullPointerException  Bei Angabe von <code>null</code>
   *                               f&uuml;r das Argument
   *                               <code>winkel</code>.
   */
  public boolean isSameAngle (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    boolean isSameAngle       = false;
    double  myAngleInDegrees  = getSizeInDegrees            ();
    double  hisAngleInDegrees = otherAngle.getSizeInDegrees ();
    
    isSameAngle = (myAngleInDegrees == hisAngleInDegrees);
    
    return isSameAngle;
  }
  
  
  
  // 13.03.2013
  public void addAngle (Angle angleToAdd)
  {
    checkTheOtherAngle (angleToAdd);
    
    double hisSizeInMyUnit = 0.0;
    
    hisSizeInMyUnit = getOtherAngleSizeInThisUnit (angleToAdd);
    angleSize       = (angleSize + hisSizeInMyUnit);
  }
  
  // this - angleToSubtract.
  public void subtractAngle (Angle angleToSubtract)
  {
    checkTheOtherAngle (angleToSubtract);
    
    double hisSizeInMyUnit = 0.0;
    
    hisSizeInMyUnit  = getOtherAngleSizeInThisUnit (angleToSubtract);
    angleSize        = (angleSize - hisSizeInMyUnit);
  }
  
  public void multiplyAngle (Angle angleToMultiply)
  {
    checkTheOtherAngle (angleToMultiply);
    
    double hisSizeInMyUnit = 0.0;
    
    hisSizeInMyUnit = getOtherAngleSizeInThisUnit (angleToMultiply);
    angleSize       = (angleSize * hisSizeInMyUnit);
  }
  
  public void divideByAngle (Angle angleToDivideInto)
  {
    checkTheOtherAngle (angleToDivideInto);
    
    double hisSizeInMyUnit = 0.0;
    
    hisSizeInMyUnit = getOtherAngleSizeInThisUnit (angleToDivideInto);
    angleSize       = (angleSize / hisSizeInMyUnit);
  }
  
  // Nennt Differenz in einem bestimmten Winkelmass.
  public double getDistanceInUnit
  (
    Angle     otherAngle,
    AngleUnit destinationUnit
  )
  {
    checkTheOtherAngle (otherAngle);
    
    double differenz    = 0.0;
    double meineGroesse = this.getSizeIn       (destinationUnit);
    double seineGroesse = otherAngle.getSizeIn (destinationUnit);
    
    differenz = (meineGroesse - seineGroesse);
    
    return differenz;
  }
  
  // Nennt Differenz in meinem Winkelmass.
  public double getDistance (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    double differenz = getDistanceInUnit (otherAngle, angleUnit);
    
    return differenz;
  }
  
  // Nennt stets positive Differenz in einem bestimmten Winkelmass.
  public double getAbsoluteDistance
  (
    Angle     otherAngle,
    AngleUnit destinationUnit
  )
  {
    double absoluteDifferenz = 0.0;
    double differenz         = getDistanceInUnit (otherAngle, destinationUnit);
    
    absoluteDifferenz = Math.abs (differenz);
    
    return absoluteDifferenz;
  }
  
  // Nennt stets positive Differenz in meinem Winkelmass.
  public double getAbsoluteDistance (Angle otherAngle)
  {
    double absoluteDifferenz = 0.0;
    double differenz         = getDistance (otherAngle);
    
    absoluteDifferenz = Math.abs (differenz);
    
    return absoluteDifferenz;
  }
  
  // Differenz als neuer Winkel in Winkelmass.
  public Angle getDistanceAsAngle
  (
    Angle     otherAngle,
    AngleUnit destinationUnit
  )
  {
    checkTheOtherAngle (otherAngle);
    
    Angle differenzWinkel = null;
    double differenz       = 0.0;
    double meineGroesse    = this.getSizeIn   (destinationUnit);
    double seineGroesse    = otherAngle.getSizeIn (destinationUnit);
    
    differenz       = (meineGroesse - seineGroesse);
    differenzWinkel = new Angle (destinationUnit, differenz);
    
    return differenzWinkel;
  }
  
  // Differenz als neuer Winkel in meinem Winkelmass.
  public Angle getDistanceAsAngle (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    Angle differenzWinkel = getDistanceAsAngle (otherAngle, angleUnit);
    
    return differenzWinkel;
  }
  
  // Immer positive Differenz als neuer Winkel in Winkelmass.
  public Angle getAbsoluteDistanceAsAngle
  (
    Angle     otherAngle,
    AngleUnit destinationUnit
  )
  {
    checkTheOtherAngle (otherAngle);
    
    Angle  differenzWinkel = null;
    double differenz       = 0.0;
    double meineGroesse    = this.getSizeIn       (destinationUnit);
    double seineGroesse    = otherAngle.getSizeIn (destinationUnit);
    
    differenz       = (meineGroesse - seineGroesse);
    differenz       = Math.abs (differenz);
    differenzWinkel = new Angle (destinationUnit, differenz);
    
    return differenzWinkel;
  }
  
  // Immer positive Differenz als neuer Winkel in meinem Winkelmass.
  public Angle getAbsoluteDistanceAsAngle (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    Angle differenceAngle = null;
    
    differenceAngle = getAbsoluteDistanceAsAngle
    (
      otherAngle,
      angleUnit
    );
    
    return differenceAngle;
  }
  
  // >> TESTED + OK.
  /**
   * <div class="introMethod">
   *   Calculates the angle at the given fraction between this one
   *   and a second one.
   * </div>
   * 
   * <div>
   *   This operation's result is essentially a linear interpolation
   *   between this angle and the second one.
   * </div>
   * 
   * @param otherAngle  The other angle as "end point". It must not be
   *                    <code>null</code>.
   * @param fraction    The fraction of the angle ("position") between
   *                    this angle and the <code>otherAngle</code>.
   *                    
   * @return            Returns the angle at the given fraction between
   *                    this angle and the given one.
   * 
   * @throws NullPointerException  If <code>otherAngle</code> equals
   *                               <code>null</code>.
   */
  public Angle getAngleAtFraction (Angle otherAngle, double fraction)
  {
    Angle angleAtFraction = null;
    
    angleAtFraction = getDistanceAsAngle (otherAngle);
    angleAtFraction.multiplyWithSize     (fraction);
    
    return angleAtFraction;
  }
  
  //+| 10.07.2013
  // Andere Namen: "setzeAufRestwinkel ()", "dreheUmHalbkreis ()", "spiegele ()".
  /**
   * <div class="einleitungMethode">
   *   Die Winkelgr&ouml;&szlig;e wird um einen gestreckten Winkel,
   *   also einen halben Kreis, erweitert, sodass er "in die andere
   *   Richtung zeigt".
   * </div>
   * 
   * <div class="beispiel">
   *   Beispiel:
   *   <br />
   *   Der aktuelle Winkel ist &alpha; = 90,00�.<br />
   *   So ergibt sich f&uuml;r den Restwinkel:
   *   &beta; = &alpha; + 180,00° = 270,00°.
   * </div>
   */
  public void invertDirection ()
  {
    double oldAngleInDegrees = getSizeInDegrees ();
    double newAngleInDegrees = ((oldAngleInDegrees + 180.0) % 360.0);
    
    setSizeFromDegrees (newAngleInDegrees);
  }
  
  // 08-09-2013
  // -> "http://wiki.math.se/wikis/2009/bridgecourse1-TU-Berlin/index.php/4.3_Trigonometrische_Eigenschaften"
  /**
   * <div class="introMethod">
   *   Reflects the angle along the <i>x</i>-axis, that means
   *   vertically.
   * </div>
   * 
   * <div>
   *   This operation's result is equal to changing the angle size's
   *   sign.
   * </div>
   */
  public void reflectAtXAxis ()
  {
    double oldAngleInDegrees = getSizeInDegrees ();
    double newAngleInDegrees = -oldAngleInDegrees;
    
    setSizeFromDegrees (newAngleInDegrees);
  }
  
  //08-09-2013
  // -> "http://wiki.math.se/wikis/2009/bridgecourse1-TU-Berlin/index.php/4.3_Trigonometrische_Eigenschaften"
  /* NOCH NICHT GETESTET!!!
   * 
   */
  /**
   * <div class="introMethod">
   *   Reflects the angle along the <i>y</i>-axis, that means
   *   horizontally.
   * </div>
   * 
   * <div>
   *   This operation's result is equal to calculating
   *   <code>180° - angleSize</code>.
   * </div>
   */
  public void reflectAtYAxis ()
  {
    double alterWinkelInGrad = getSizeInDegrees ();
    double neuerWinkelInGrad = (180.0 - alterWinkelInGrad);
    
    setSizeFromDegrees (neuerWinkelInGrad);
  }
  
  
  // 01.09.2013
  public void addToSize (double summand)
  {
    angleSize = (angleSize + summand);
  }
  
  // 01.09.2013
  public void subtractFromSize (double subtrahend)
  {
    angleSize = (angleSize - subtrahend);
  }
  
  // 01.09.2013
  public void multiplyWithSize (double factor)
  {
    angleSize = (angleSize * factor);
  }
  
  // 01.09.2013
  public void divideSizeBy (double divisor)
  {
    angleSize = (angleSize / divisor);
  }
  
  
  // 27-08-2013
  /**
   * <div class="introMethod">
   *   Adds another angle to this one and returns the result as a new
   *   <code>Angle</code> object.
   * </div>
   * 
   * @param otherAngle  The angle to add to this one. It may not be
   *                    <code>null</code>.
   * @return            A new angle as result of the addition.
   * @throws NullPointerException  If <code>otherAngle</code> equals
   *                               <code>null</code>.
   */
  public Angle getAddedAngle (Angle otherAngle)
  {
    Angle result = new Angle (this);
    
    result.addAngle (otherAngle);
    
    return result;
  }
  
  // 27-08-2013
  public Angle getSubtractedAngle (Angle otherAngle)
  {
    Angle result = new Angle (this);
    
    result.subtractAngle (otherAngle);
    
    return result;
  }
  
  // 27-08-2013
  public Angle getMultipliedAngle (Angle otherAngle)
  {
    Angle result = new Angle (this);
    
    result.multiplyAngle (otherAngle);
    
    return result;
  }
  
  // 27-08-2013
  public Angle getDividedAngle (Angle otherAngle)
  {
    Angle result = new Angle (this);
    
    result.divideByAngle (otherAngle);
    
    return result;
  }
  
  
  // 01.09.2013
  public Angle getAddedToSizeAngle (double summand)
  {
    Angle  newAngle = null;
    double newSize  = (angleSize + summand);
    
    newAngle = new Angle (angleUnit, newSize);
    
    return newAngle;
  }
  
  // 01.09.2013
  public Angle getSubtractedFromSizeAngle (double subtrahend)
  {
    Angle  newAngle = null;
    double newSize  = (angleSize - subtrahend);
    
    newAngle = new Angle (angleUnit, newSize);
    
    return newAngle;
  }
  
  // 01.09.2013
  // Alternative name: "getScaledByScalar (...)"
  public Angle getMultipliedByScalar (double factor)
  {
    Angle  newAngle = null;
    double newSize  = (angleSize * factor);
    
    newAngle = new Angle (angleUnit, newSize);
    
    return newAngle;
  }
  
  // 01.09.2013
  public Angle getDividedBySizeAngle (double divisor)
  {
    Angle  newAngle = null;
    double newSize  = (angleSize / divisor);
    
    newAngle = new Angle (angleUnit, newSize);
    
    return newAngle;
  }
  
  // 08.05.2015
  public Angle getAngleAfterModulo (double moduloDivisor)
  {
    Angle  newAngle = null;
    double newSize  = 0.0;
    
    newSize  = (angleSize % moduloDivisor);
    newAngle = new Angle (angleUnit, newSize);
    
    return newAngle;
  }
  
  
  // 27-08-2013
  // Tauscht Winkelmass und Winkelgroesse.
  public void swapUnitAndSizeWith (Angle otherAngle)
  {
    checkTheOtherAngle (otherAngle);
    
    AngleUnit myUnit  = this.angleUnit;
    double    mySize  = this.angleSize;
    AngleUnit hisUnit = otherAngle.getUnit ();
    double    hisSize = otherAngle.getSize ();
    
    angleUnit = hisUnit;
    angleSize = hisSize;
    
    otherAngle.convertToUnit (myUnit);
    otherAngle.setSize       (mySize);
  }
  
  
  
  
  //+/ 06.04.2013
  /**
   * <div class="introMethod">
   *   Returns the angle type.
   * </div>
   * 
   * @return  The type.
   */
  public AngleType getAngleType ()
  {
    AngleType winkelart = AngleUtils.getAngleType (this);
    
    return winkelart;
  }
  
  // 30.09.2013
  // Ist Winkel von dieser Winkelart?
  public boolean isOfAngleType (AngleType angleType)
  {
    boolean   isOfThisAngleType = false;
    // Die tatsaechliche Winkelart dieses Winkels.
    AngleType myAngleType       = getAngleType ();
    
    isOfThisAngleType = myAngleType.equals (angleType);
    
    return isOfThisAngleType;
  }
  
  
  
  // 22.07.2013
  /**
   * <div class="einleitungMethode">
   *   Es wird ermittelt, ob ein bestimmtes Verh&auml;ltnis zwischen
   *   diesem und einem zweiten Winkel vorliegt.
   * </div>
   * 
   * @param relation       Die Relation zwischen diesem und dem zweiten
   *                       Winkel. Sie darf nicht <code>null</code>
   *                       sein.
   * @param otherAngle  Der zu vergleichende Winkel. Er darf nicht
   *                       <code>null</code> sein.
   * 
   * @return               Einen Boole'schen Wert zur Bestimmung, ob die
   *                       Relation wahr ist:
   *                       <table>
   *                         <tr>
   *                           <td>
   *                             <code>true</code>
   *                           </td>
   *                           <td>
   *                             Die Relation trifft f&uuml;r die beiden
   *                             Winkel zu.
   *                           </td>
   *                         </tr>
   *                         <tr>
   *                           <td>
   *                             <code>false</code>
   *                           </td>
   *                           <td>
   *                             Die Relation trifft f&uuml;r die beiden
   *                             Winkel nicht zu.
   *                           </td>
   *                         </tr>
   *                       </table>
   * 
   * @throws NullPointerException Falls <code>relation</code> oder
   *                               <code>andererWinkel</code> den Wert
   *                               <code>null</code> erhalten.
   */
  public boolean satisfiesRelation
  (
    Relation relation,
    Angle    otherAngle
  )
  {
    CheckingUtils.checkForNull (relation, "Given relation is null.");
    checkTheOtherAngle         (otherAngle);
    
    boolean isSatisfied = false;
    double  mySize      = getSizeInDegrees            ();
    double  hisSize     = otherAngle.getSizeInDegrees ();
    
    isSatisfied = relation.isTrueForNumber (mySize, hisSize);
    
    return isSatisfied;
  }
  
  
  /* NOCH NICHT GETESTET!
   * 
   * In das "normale" Intervall bringen,
   * z. B. bei Gradmass: [0; 360) bzw. (-360; +360).
   * 
   */
  public void normalize (boolean keepSign)
  {
    double  groesseNormalisiert   = 0.0;
    double  groesseInGrad         = getSizeInDegrees ();
    boolean istGroesseNegativ     = (groesseInGrad < 0.0);
    // Soll immer positiv sein, ist aber zurzeit negativ? => Aendern.
    boolean sollVorzeichenAendern = ((! keepSign) &&
                                     (istGroesseNegativ));
    
    groesseNormalisiert = (groesseInGrad % 360.0);
    
    // Winkel ist negativ und soll positiv sein? => Komplementieren.
    if (sollVorzeichenAendern)
    {
      groesseNormalisiert = (360.0 + groesseNormalisiert);
    }
    
    setSizeFromDegrees (groesseNormalisiert);
  }
  
  public void normalize ()
  {
    normalize (true);
  }
  
  /* NOCH NICHT GETESTET!
   * 
   * In das "normale" Intervall bringen, z. B. bei Gradmass: [0; 360).
   * 
   */
  public Angle getNormalizedAngle (boolean keepSign)
  {
    Angle     normalisierterWinkel  = null;
    AngleUnit seinWinkelmass        = angleUnit;
    double    groesseNormalisiert   = 0.0;
    double    groesseInGrad         = getSizeInDegrees ();
    boolean   istGroesseNegativ     = (groesseInGrad < 0.0);
    boolean   sollVorzeichenAendern = ((! keepSign) &&
                                       (istGroesseNegativ));
    
    groesseNormalisiert = (groesseInGrad % 360.0);
    
    if (sollVorzeichenAendern)
    {
      groesseNormalisiert = (360.0 + groesseNormalisiert);
    }
    
    // Uebertrage den normalisierten Winkel in "seinWinkelmass".
    groesseNormalisiert = AngleUtils.convert
    (
      groesseNormalisiert,
      AngleUnit.DEGREE,
      seinWinkelmass
    );
    normalisierterWinkel = new Angle
    (
      seinWinkelmass,
      groesseNormalisiert
    );
    
    return normalisierterWinkel;
  }
  
  public void negateAngle ()
  {
    angleSize = (angleSize * -1.0);
  }
  
  // -1 * angle size.
  public Angle getNegatedAngle ()
  {
    Angle     negatedAngle = null;
    AngleUnit myUnit       = getUnit ();
    double    mySize       = getSize ();
    
    negatedAngle = new Angle (myUnit, mySize * -1.0);
    
    return negatedAngle;
  }
  
  
  // 21.03.2015
  /**
   * <div>
   *   Wraps this angle's size value around the angle range and returns
   *   the thus created object.
   * </div>
   * 
   * @param minimum  The inclusive minimum angle.
   *                 It may not be <code>null</code>.
   * @param maximum  The inclusive maximum angle.
   *                 It may not be <code>null</code>.
   * 
   * @return         A new angle object with its value being equal to
   *                 this one's wrapped around the given range.
   */
  public Angle getWrappedAngle (Angle minimum, Angle maximum)
  {
    Angle wrappedAngle = null;
    
    wrappedAngle = new Angle (this);
    wrappedAngle.wrap (minimum, maximum);
    
    return wrappedAngle;
  }
  
  public void wrap (Angle minimum, Angle maximum)
  {
    double wrappedDegrees = 0.0;
    double minimumDegrees = 0.0;
    double maximumDegrees = 0.0;
    
    minimumDegrees = minimum.getSizeInDegrees ();
    maximumDegrees = maximum.getSizeInDegrees ();
    wrappedDegrees = ky.math.ComputerGraphicsMath.wrapByMartinStettner
    (
      this.getSizeInDegrees (),
      minimumDegrees,
      maximumDegrees
    );
    setSizeFromDegrees (wrappedDegrees);
  }
  
  
  // 29.09.2013
  public Quadrant getQuadrant ()
  {
    for (Quadrant quadrant : Quadrant.values ())
    {
      if (quadrant.containsAngle (this))
      {
        return quadrant;
      }
    }
    
    return null;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of static methods.                          -- //
  //////////////////////////////////////////////////////////////////////
  
  public static Angle createZeroAngle (AngleUnit angleUnit)
  {
    Angle angle = null;
    
    angle = new Angle (angleUnit, 0.0);
    
    return angle;
  }
  
  public static Angle createZeroDegreeAngle ()
  {
    return createZeroAngle (AngleUnit.DEGREE);
  }
  
  // 15.03.2015
  public static Angle createFromString (String inputString)
  {
    Angle       angle  = null;
    AngleParser parser = null;
    
    parser = new AngleParser   ();
    angle  = parser.parseAngle (inputString);
    
    return angle;
  }
  
  // 06.08.2015
  /**
   * <div class="introMethod">
   *   Given a source angle size and unit, a new angle of the
   *   desired destination unit is created.
   * </div>
   * 
   * @param angleSizeInSourceUnit  The angle size to convert from the
   *                               <code>sourceUnit</code> to the
   *                               <code>destinationUnit</code>.
   * @param sourceUnit             The angle unit of the
   *                               <code>sourceSize</code>.
   *                               It may not be <code>null</code>.
   * @param destinationUnit        The angle unit for the angle to
   *                               create.
   *                               It may not be <code>null</code>.
   * 
   * @throws NullPointerException  If <code>destinationUnit</code> or
   *                               <code>sourceUnit</code> equal
   *                               <code>null</code>.
   */
  public static Angle createByConvertingFromSourceToDestinationUnit
  (
    double    angleSizeInSourceUnit,
    AngleUnit sourceUnit,
    AngleUnit destinationUnit
  )
  {
    Angle newAngle = null;
    
    newAngle = new Angle
    (
      destinationUnit,
      sourceUnit,
      angleSizeInSourceUnit
    );
    
    return newAngle;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "Comparable".               -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public int compareTo (Angle otherAngle)
  {
    int result = 0;
    
    if (otherAngle != null)
    {
      double myAngleInDegrees  = getSizeInDegrees            ();
      double hisAngleInDegrees = otherAngle.getSizeInDegrees ();
      
      if (myAngleInDegrees > hisAngleInDegrees)
      {
        result = 1;
      }
      else if (myAngleInDegrees == hisAngleInDegrees)
      {
        result = 0;
      }
      else
      {
        result = -1;
      }
    }
    else
    {
      throw new ClassCastException ("No angle to compare to given.");
    }
    
    return result;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public Angle clone ()
  {
    try
    {
      return (Angle) super.clone ();
    }
    catch (CloneNotSupportedException cnse)
    {
      cnse.printStackTrace ();
      
      return null;
    }
  }
  
  @Override
  public boolean equals (Object object)
  {
    if (object != null)
    {
      if (object instanceof Angle)
      {
        Angle   otherAngle = (Angle) object;
        boolean areEqual   = hasSameUnitAndSize (otherAngle);
        
        return areEqual;
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
  }
  
  @Override
  public int hashCode ()
  {
    int hashCode        = 0;
    int hashCodeForSize = new Double (angleSize).hashCode ();
    int hashCodeForUnit = angleUnit.hashCode              ();
    
    hashCode = 1;
    hashCode = ((hashCode * 17) + hashCodeForSize);
    hashCode = ((hashCode * 31) + hashCodeForUnit);
    
    return hashCode;
  }
  
  @Override
  public String toString ()
  {
    String asString      = null;
    String angleAsString = angleUnit.getFormattedString (angleSize);
    
    asString = String.format ("Angle(%s)", angleAsString);
    
    return asString;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initAngle (AngleUnit angleUnit, double angleSize)
  {
    checkAngleUnit (angleUnit);
    
    this.angleUnit = angleUnit;
    this.angleSize = angleSize;
  }
  
  private void checkAngleUnit (AngleUnit angleUnit)
  {
    CheckingUtils.checkForNull (angleUnit, "Angle unit is null.");
  }
  
  private void checkTheOtherAngle (Angle angle)
  {
    CheckingUtils.checkForNull (angle, "Second angle is null.");
  }
}