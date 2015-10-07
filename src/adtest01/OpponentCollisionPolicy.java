package adtest01;

public class OpponentCollisionPolicy
implements   CollisionPolicy
{
  public OpponentCollisionPolicy ()
  {
  }
  
  
  @Override
  public boolean canCollideWith
  (
    MarkerModel activeModel,
    MarkerModel targetModel
  )
  {
    if (activeModel.canCollide  () && targetModel.canCollide  ())
    {
      return
      (
        (activeModel.getPlayerNumber () != targetModel.getPlayerNumber ()) ||
        // Only the defense marker may have the same player number.
        ((activeModel.isDefenseMarker () && targetModel.isCharacter     ()) &&
         (activeModel.getPlayerNumber () == targetModel.getPlayerNumber ()))
      );
    }
    else
    {
      return false;
    }
  }
}
