package ky;

import ky.Position;


public enum CuboidPosition
{
  LEFT_TOP_FRONT       (CuboidPositionX.LEFT, CuboidPositionY.TOP,    CuboidPositionZ.FRONT),
  LEFT_TOP_MIDDLE      (CuboidPositionX.LEFT, CuboidPositionY.TOP,    CuboidPositionZ.MIDDLE),
  LEFT_TOP_BACK        (CuboidPositionX.LEFT, CuboidPositionY.TOP,    CuboidPositionZ.BACK),
  LEFT_MIDDLE_FRONT    (CuboidPositionX.LEFT, CuboidPositionY.MIDDLE, CuboidPositionZ.FRONT),
  LEFT_MIDDLE_MIDDLE   (CuboidPositionX.LEFT, CuboidPositionY.MIDDLE, CuboidPositionZ.MIDDLE),
  LEFT_MIDDLE_BACK     (CuboidPositionX.LEFT, CuboidPositionY.MIDDLE, CuboidPositionZ.BACK),
  LEFT_BOTTOM_FRONT    (CuboidPositionX.LEFT, CuboidPositionY.BOTTOM, CuboidPositionZ.FRONT),
  LEFT_BOTTOM_MIDDLE   (CuboidPositionX.LEFT, CuboidPositionY.BOTTOM, CuboidPositionZ.MIDDLE),
  LEFT_BOTTOM_BACK     (CuboidPositionX.LEFT, CuboidPositionY.BOTTOM, CuboidPositionZ.BACK),
  
  MIDDLE_TOP_FRONT     (CuboidPositionX.MIDDLE, CuboidPositionY.TOP,    CuboidPositionZ.FRONT),
  MIDDLE_TOP_MIDDLE    (CuboidPositionX.MIDDLE, CuboidPositionY.TOP,    CuboidPositionZ.MIDDLE),
  MIDDLE_TOP_BACK      (CuboidPositionX.MIDDLE, CuboidPositionY.TOP,    CuboidPositionZ.BACK),
  MIDDLE_MIDDLE_FRONT  (CuboidPositionX.MIDDLE, CuboidPositionY.MIDDLE, CuboidPositionZ.FRONT),
  MIDDLE_MIDDLE_MIDDLE (CuboidPositionX.MIDDLE, CuboidPositionY.MIDDLE, CuboidPositionZ.MIDDLE),
  MIDDLE_MIDDLE_BACK   (CuboidPositionX.MIDDLE, CuboidPositionY.MIDDLE, CuboidPositionZ.BACK),
  MIDDLE_BOTTOM_FRONT  (CuboidPositionX.MIDDLE, CuboidPositionY.BOTTOM, CuboidPositionZ.FRONT),
  MIDDLE_BOTTOM_MIDDLE (CuboidPositionX.MIDDLE, CuboidPositionY.BOTTOM, CuboidPositionZ.MIDDLE),
  MIDDLE_BOTTOM_BACK   (CuboidPositionX.MIDDLE, CuboidPositionY.BOTTOM, CuboidPositionZ.BACK),
  
  RIGHT_TOP_FRONT      (CuboidPositionX.RIGHT, CuboidPositionY.TOP,    CuboidPositionZ.FRONT),
  RIGHT_TOP_MIDDLE     (CuboidPositionX.RIGHT, CuboidPositionY.TOP,    CuboidPositionZ.MIDDLE),
  RIGHT_TOP_BACK       (CuboidPositionX.RIGHT, CuboidPositionY.TOP,    CuboidPositionZ.BACK),
  RIGHT_MIDDLE_FRONT   (CuboidPositionX.RIGHT, CuboidPositionY.MIDDLE, CuboidPositionZ.FRONT),
  RIGHT_MIDDLE_MIDDLE  (CuboidPositionX.RIGHT, CuboidPositionY.MIDDLE, CuboidPositionZ.MIDDLE),
  RIGHT_MIDDLE_BACK    (CuboidPositionX.RIGHT, CuboidPositionY.MIDDLE, CuboidPositionZ.BACK),
  RIGHT_BOTTOM_FRONT   (CuboidPositionX.RIGHT, CuboidPositionY.BOTTOM, CuboidPositionZ.FRONT),
  RIGHT_BOTTOM_MIDDLE  (CuboidPositionX.RIGHT, CuboidPositionY.BOTTOM, CuboidPositionZ.MIDDLE),
  RIGHT_BOTTOM_BACK    (CuboidPositionX.RIGHT, CuboidPositionY.BOTTOM, CuboidPositionZ.BACK);
  
  
  private CuboidPositionX cuboidPositionX;
  private CuboidPositionY cuboidPositionY;
  private CuboidPositionZ cuboidPositionZ;
  
  
  /**
   * <div class="introConstructor">
   *   Defines an interesting point on a cuboid.
   * </div>
   * 
   * @param cuboidPositionX  The positioning along the <i>x</i>-axis.
   * @param cuboidPositionY  The positioning along the <i>y</i>-axis.
   * @param cuboidPositionZ  The positioning along the <i>z</i>-axis.
   */
  private CuboidPosition
  (
    CuboidPositionX cuboidPositionX,
    CuboidPositionY cuboidPositionY,
    CuboidPositionZ cuboidPositionZ
  )
  {
    this.cuboidPositionX = cuboidPositionX;
    this.cuboidPositionY = cuboidPositionY;
    this.cuboidPositionZ = cuboidPositionZ;
  }
  
  
  public Position getPositionAtCuboid (Cuboid cuboid)
  {
    Position position  = null;
    double   positionX = 0.0;
    double   positionY = 0.0;
    double   positionZ = 0.0;
    
    positionX = cuboidPositionX.getCoordinateAtCuboid (cuboid);
    positionY = cuboidPositionY.getCoordinateAtCuboid (cuboid);
    positionZ = cuboidPositionZ.getCoordinateAtCuboid (cuboid);
    position  = new Position (positionX, positionY, positionZ);
    
    return position;
  }
}