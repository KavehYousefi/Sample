package adtest01;


public interface CollisionPolicy
{
  abstract public boolean canCollideWith
  (
    MarkerModel activeModel,
    MarkerModel targetModel
  );
}
