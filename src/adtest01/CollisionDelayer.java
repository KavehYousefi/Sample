package adtest01;


public class CollisionDelayer
{
  private static final long DEFAULT_MINIMUM_COLLISION_DELAY = 3000L;
  
  private long minimumCollisionDelay;
  private long lastCollisionTimestamp;
  
  
  public CollisionDelayer (long minimumCollisionDelay)
  {
    this.minimumCollisionDelay  = minimumCollisionDelay;
    this.lastCollisionTimestamp = 0;
  }
  
  public CollisionDelayer ()
  {
    this (DEFAULT_MINIMUM_COLLISION_DELAY);
  }
  
  
  public void start ()
  {
    this.lastCollisionTimestamp = System.currentTimeMillis ();
  }
  
  public boolean isUpdatePossible ()
  {
    long timespanWithoutCollision  = 0L;
    long currentCollisionTimestamp = 0L;
    
    currentCollisionTimestamp = System.currentTimeMillis ();
    timespanWithoutCollision  = (currentCollisionTimestamp - lastCollisionTimestamp);
    
    if (timespanWithoutCollision >= minimumCollisionDelay)
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  public void updateTimestamp ()
  {
    if (isUpdatePossible ())
    {
      this.lastCollisionTimestamp = System.currentTimeMillis ();
    }
  }
}
