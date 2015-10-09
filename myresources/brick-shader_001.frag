/* 10.05.2015
 * "Orange Book - OpenGL Shading Language 2nd Edition", PDF-page 155-166
 * "Orange Book - OpenGL Shading Language 2nd Edition", PDF-page 209 (example for setting the uniform variables)
 */

#version 120

uniform vec3  BrickColor;
uniform vec3  MortarColor;
uniform vec2  BrickSize;
uniform vec2  BrickPercentage;

/* Contains the interpolated value for the light intensity, that we
 * computed at each vertex.
 */
varying float LightIntensity;
/* Called "MCposition" in the source.
 * To have the object look the same no matter where it is placed in
 * the scene or how it is rotated, we use the modeling coordinate
 * position which is computed by the vertex shader.
 * Computed for each vertex in the vertex shader and interpolated
 * across the primitive, the fragment shader can use this information
 * to determine, where the fragment location is in relation to the
 * brick pattern.
 */
varying vec2  PositionInModelCoordinates;


void main (void)
{
  vec3 color;
  /* By dividing the fragment's x- and y-position in modeling
   * coordinates by the brick column width and brick row height, we
   * yield a "brick row number" (position.y) and a "brick column
   * number" (position.x). These are floating-point values and can be
   * negative.
   */
  vec2 position;
  vec2 useBrick;
  
  position = PositionInModelCoordinates / BrickSize;
  
  /* We determine, if the fragment is in a row of bricks, which have
   * an offset.
   * Using the "fract()" function, we discard the integer part of
   * (position.y * 0.5), using only its decimal part, for example:
   *   fract (1.3) = 0.3
   * Then we compare this decimal part to 0.5, which yields "true"
   * half the time. If true, we increment the "brick column number"
   * (position.x) by 0.5 to offset the entire row by half the width
   * of a brick.
   */
  if (fract (position.y * 0.5) > 0.5)
  {
    position.x = position.x + 0.5;
  }
  
  /* We compute the fragment's location within the current brick.
   * In doing so, we get the vertical and horizontal position within
   * a single brick; this position will be used to choose whether the
   * brick color or mortar color should be used.
   */
  position = fract (position);
  
  /* We need a function to tell us, whether we should use the 
   * brick color or the mortra color:
   *   1.0 -> use brick  color
   *   0.0 -> use mortar color
   * The "step()" function helps us achieve the desired effect. It
   * takes a threshold and a value, comparing the value against it:
   *   value <  threshold => the function returns 0.0
   *   value >= threshold => the function returns 1.0
   * We compute two calues to tell us whether we are in the brick or
   * in the mortar in the horizontal (useBrick.x) and vertical
   * (useBrick.y) direction. The "step()" function produces
   *    0.0, if BrickPercentage.x <  position
   *    1.0, if BrickPercentage.x >= position
   * Because of the "position" being modified by the "fract()"
   * function above (see line "position = fract(position);", we know
   * that position.x varies from (0, 1).
   * If useBrick.x is 1.0, we use the brick color, if it is 0.0, we
   * use the mortar color. We do the same thing for useBrick.y.
   */
   useBrick = step (position, BrickPercentage);
   
   /* We compute the color of the fragment and store it in the color
    * variable "color".
    * We use the built-in function "mix()" to choose the brick color
    * or mortar color, depending of the value of
    * useBrick.x * useBrick.y.
    * Because useBrick.x and useBrick.y can only be 0.0 (mortar) or
    * 1.0 (brick), we choose the brick color only if both values
    * are 1.0; otherwise, we choose the mortar color:
    *   useBrick.x = 0, useBrick.y = 0 => 0 * 0 = 0 => mortar color
    *   useBrick.x = 0, useBrick.y = 1 => 0 * 1 = 0 => mortar color
    *   useBrick.x = 1, useBrick.y = 0 => 1 * 0 = 0 => mortar color
    *   useBrick.x = 1, useBrick.y = 1 => 1 * 1 = 1 => brick  color
    * This is logical, as we are only in the brick part, if both
    * x- and y-position are in the brick part.
    */
   color    = mix (MortarColor, BrickColor, useBrick.x * useBrick.y);
   // We multiply the resulting color value by the light intensity.
   color    = color * LightIntensity;
   
   gl_FragColor = vec4 (color, 1.0);
}
