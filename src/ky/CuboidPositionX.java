package ky;


public enum CuboidPositionX
{
  LEFT
  {
    @Override
    public double getCoordinateAtCuboid (Cuboid cuboid)
    {
      double   coordinate   = 0.0;
      Position cuboidCenter = cuboid.getPosition ();
      
      coordinate = cuboidCenter.getX () - (cuboid.getWidth () / 2.0);
      
      return coordinate;
    }
  },
  MIDDLE
  {
    @Override
    public double getCoordinateAtCuboid (Cuboid cuboid)
    {
      double   coordinate   = 0.0;
      Position cuboidCenter = cuboid.getPosition ();
      
      coordinate = cuboidCenter.getX ();
      
      return coordinate;
    }
  },
  RIGHT
  {
    @Override
    public double getCoordinateAtCuboid (Cuboid cuboid)
    {
      double   coordinate   = 0.0;
      Position cuboidCenter = cuboid.getPosition ();
      
      coordinate = cuboidCenter.getX () + (cuboid.getWidth () / 2.0);
      
      return coordinate;
    }
  };
  
  private CuboidPositionX ()
  {
  }
  
  
  abstract public double getCoordinateAtCuboid (Cuboid cuboid);
}