// 24-12-2013

package ky.transform2;

/* Alternativer Name: "ScherEbene".
 *   ScherSeite.LINKS_RECHTS => ScherEbene.YZ
 *   ScherSeite.VORNE_HINTEN => ScherEbene.XY
 *   ScherSeite.OBEN_UNTEN   => ScherEbene.XZ
 */
public enum ShearSide
{
  /**
   * <div class="introEnumInstance">
   *   The <i>y</i>-<i>z</i> shearing plane.
   * </div>
   */
  LEFT_RIGHT,
  /**
   * <div class="introEnumInstance">
   *   The <i>x</i>-<i>y</i> shearing plane.
   * </div>
   */
  FRONT_BACK,
  /**
   * <div class="introEnumInstance">
   *   The <i>x</i>-<i>z</i> shearing plane.
   * </div>
   */
  TOP_BOTTOM;
  
  
  /**
   * <div class="introConstructor">
   *   Defines a pair of sides being sheared in opposite direction.
   * </div>
   */
  private ShearSide ()
  {
  }
}