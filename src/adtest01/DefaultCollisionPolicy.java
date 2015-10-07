// 05.10.2015

package adtest01;


public class DefaultCollisionPolicy
implements   CollisionPolicy
{
  public DefaultCollisionPolicy ()
  {
  }
  
  
  @Override
  public boolean canCollideWith
  (
    MarkerModel activeModel,
    MarkerModel targetModel
  )
  {
    return (activeModel.canCollide () && activeModel.canAct ());
  }
}
