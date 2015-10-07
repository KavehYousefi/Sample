package ky;


public enum CuboidPositionZ
{
  FRONT
  {
    @Override
    public double getCoordinateAtCuboid (Cuboid cuboid)
    {
      double   coordinate   = 0.0;
      Position cuboidCenter = cuboid.getPosition ();
      
      coordinate = cuboidCenter.getZ () + (cuboid.getDepth () / 2.0);
      
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
      
      coordinate = cuboidCenter.getZ ();
      
      return coordinate;
    }
  },
  BACK
  {
    @Override
    public double getCoordinateAtCuboid (Cuboid cuboid)
    {
      double   coordinate   = 0.0;
      Position cuboidCenter = cuboid.getPosition ();
      
      coordinate = cuboidCenter.getZ () - (cuboid.getDepth () / 2.0);
      
      return coordinate;
    }
  };
  
  private CuboidPositionZ ()
  {
  }
  
  
  abstract public double getCoordinateAtCuboid (Cuboid cuboid);
}