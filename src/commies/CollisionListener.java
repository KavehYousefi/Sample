// 09.06.2015

package commies;


public interface CollisionListener
{
  abstract public void collisionEntered (CollisionEvent event);
  
  abstract public void collisionMoved   (CollisionEvent event);
  
  abstract public void collisionExited  (CollisionEvent event);
}
