package adtest01;

import java.util.ArrayList;
import java.util.List;


public class CompoundCollisionPolicy
implements   CollisionPolicy
{
  private List<CollisionPolicy> collisionPolicies;
  
  
  public CompoundCollisionPolicy ()
  {
    this.collisionPolicies = new ArrayList<CollisionPolicy> ();
  }
  
  
  public List<CollisionPolicy> getPolicies ()
  {
    return collisionPolicies;
  }
  
  public void addPolicy (CollisionPolicy policy)
  {
    collisionPolicies.add (policy);
  }
  
  public void removePolicy (CollisionPolicy policy)
  {
    collisionPolicies.remove (policy);
  }
  
  public int getNumberOfPolicies ()
  {
    return collisionPolicies.size ();
  }
  
  
  @Override
  public boolean canCollideWith
  (
    MarkerModel activeModel,
    MarkerModel targetModel
  )
  {
    for (CollisionPolicy policy : collisionPolicies)
    {
      if (! policy.canCollideWith (activeModel, targetModel))
      {
        return false;
      }
    }
    
    return true;
  }
}
