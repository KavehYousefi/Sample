package jav3d.lathep001;

import ky.angle.Angle;

/* In -> "http://fivedots.coe.psu.ac.th/~ad/jg/ch95/ch9-5.pdf", page 22ff
 * called "subclassing LatheShape3D".
 */
public interface LatheBase
{
  // In source called "xCoord".
  abstract double getXCoordAt (double radius, Angle angle);
  
  // In source called "zCoord".
  abstract double getZCoordAt (double radius, Angle angle);
}