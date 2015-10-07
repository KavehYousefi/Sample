package adtest01;


public class DistanceCollisionPolicy
implements   CollisionPolicy
{
  private double                 minimumDistance;
  private MarkerDistanceMeasurer distanceMeasurer;
  
  
  public DistanceCollisionPolicy (double minimumDistance)
  {
    this.minimumDistance  = minimumDistance;
    this.distanceMeasurer = new MarkerDistanceMeasurer ();
  }
  
  
  @Override
  public boolean canCollideWith
  (
    MarkerModel activeModel,
    MarkerModel targetModel
  )
  {
    double distance = 0.0;
    
    distance = distanceMeasurer.getMarkerDistance (activeModel, targetModel);
    
//    System.out.format ("DistanceCollisionPolicy: %s%n", distance);
    
    return (distance <= minimumDistance);
  }
}
