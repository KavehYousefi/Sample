// 16.06.2015

package collision;

import adtest01.MarkerModel;


public class CollisionEvent
{
  private MarkerModel observingModel;
  private MarkerModel observedModel;
  private double      modelDistance;
  private long        timestamp;
  
  
  public CollisionEvent
  (
    MarkerModel observingModel,
    MarkerModel observedModel,
    double      modelDistance,
    long        timestamp
  )
  {
    this.observingModel = observingModel;
    this.observedModel  = observedModel;
    this.modelDistance  = modelDistance;
    this.timestamp      = timestamp;
  }
  
  
  public MarkerModel getObservingModel ()
  {
    return observingModel;
  }
  
  public MarkerModel getObservedModel ()
  {
    return observedModel;
  }
  
  public double getModelDistance ()
  {
    return modelDistance;
  }
  
  public long getTimestamp ()
  {
    return timestamp;
  }
}
