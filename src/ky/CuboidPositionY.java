package ky;


public enum CuboidPositionY
{
  TOP
  {
    @Override
    public double getCoordinateAtCuboid (Cuboid cuboid)
    {
      double   coordinate   = 0.0;
      Position cuboidCenter = cuboid.getPosition ();
      
      coordinate = cuboidCenter.getY () + (cuboid.getHeight () / 2.0);
      
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
      
      coordinate = cuboidCenter.getY ();
      
      return coordinate;
    }
  },
  BOTTOM
  {
    @Override
    public double getCoordinateAtCuboid (Cuboid cuboid)
    {
      double   coordinate   = 0.0;
      Position cuboidCenter = cuboid.getPosition ();
      
      coordinate = cuboidCenter.getY () - (cuboid.getHeight () / 2.0);
      
      return coordinate;
    }
  };
  
  private CuboidPositionY ()
  {
  }
  
  
  abstract public double getCoordinateAtCuboid (Cuboid cuboid);
}