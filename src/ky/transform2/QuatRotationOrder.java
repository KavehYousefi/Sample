package ky.transform2;

import javax.vecmath.Quat4d;


public interface QuatRotationOrder
{
  abstract public Quat4d getResultQuat4d (Quat4d quatX, Quat4d quatY, Quat4d quatZ);
  
  abstract public RotationOrdering getRotationOrdering ();
}