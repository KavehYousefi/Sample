package adtest01;

public class DefenseMarkerCollisionPolicy
implements   CollisionPolicy
{
  public DefenseMarkerCollisionPolicy ()
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
      boolean areMarkersOfSamePlayer = false;
      
      areMarkersOfSamePlayer = (activeModel.getPlayerNumber () ==
                               targetModel.getPlayerNumber  ());
      
      if (areMarkersOfSamePlayer)
      {
        return (activeModel.isDefenseMarker () &&
                targetModel.isCharacter     ());
      }
      else
      {
        return false;
      }
    }
    else
    {
      return false;
    }
  }
}
