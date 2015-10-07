package ky.transform2;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Quat4d;


public enum  DefaultQuatRotationOrder
implements   QuatRotationOrder
{
  XYZ
  {
    @Override
    public Quat4d getResultQuat4d (Quat4d quatX, Quat4d quatY, Quat4d quatZ)
    {
      Quat4d resultQuat = null;
      
      resultQuat = new Quat4d (quatX);
      resultQuat.mul          (quatY);
      resultQuat.mul          (quatZ);
      
      return resultQuat;
    }
    
    @Override
    public RotationOrdering getRotationOrdering ()
    {
      RotationOrdering   order = null;
      List<RotationAxis> axes  = new ArrayList<RotationAxis> ();
      
      axes.add (RotationAxis.X_AXIS);
      axes.add (RotationAxis.Y_AXIS);
      axes.add (RotationAxis.Z_AXIS);
      
      order = new RotationOrdering (axes);
      
      return order;
    }
  },
  
  XZY
  {
    @Override
    public Quat4d getResultQuat4d (Quat4d quatX, Quat4d quatY, Quat4d quatZ)
    {
      Quat4d resultQuat = null;
      
      resultQuat = new Quat4d (quatX);
      resultQuat.mul          (quatZ);
      resultQuat.mul          (quatY);
      
      return resultQuat;
    }
    
    @Override
    public RotationOrdering getRotationOrdering ()
    {
      RotationOrdering   order = null;
      List<RotationAxis> axes  = new ArrayList<RotationAxis> ();
      
      axes.add (RotationAxis.X_AXIS);
      axes.add (RotationAxis.Z_AXIS);
      axes.add (RotationAxis.Y_AXIS);
      
      order = new RotationOrdering (axes);
      
      return order;
    }
  },
  
  YXZ
  {
    @Override
    public Quat4d getResultQuat4d (Quat4d quatX, Quat4d quatY, Quat4d quatZ)
    {
      Quat4d resultQuat = null;
      
      resultQuat = new Quat4d (quatY);
      resultQuat.mul          (quatX);
      resultQuat.mul          (quatZ);
      
      return resultQuat;
    }
    
    @Override
    public RotationOrdering getRotationOrdering ()
    {
      RotationOrdering   order = null;
      List<RotationAxis> axes  = new ArrayList<RotationAxis> ();
      
      axes.add (RotationAxis.Y_AXIS);
      axes.add (RotationAxis.X_AXIS);
      axes.add (RotationAxis.Z_AXIS);
      
      order = new RotationOrdering (axes);
      
      return order;
    }
  },
  
  YZX
  {
    @Override
    public Quat4d getResultQuat4d (Quat4d quatX, Quat4d quatY, Quat4d quatZ)
    {
      Quat4d resultQuat = null;
      
      resultQuat = new Quat4d (quatX);
      resultQuat.mul          (quatZ);
      resultQuat.mul          (quatX);
      
      return resultQuat;
    }
    
    @Override
    public RotationOrdering getRotationOrdering ()
    {
      RotationOrdering   order = null;
      List<RotationAxis> axes  = new ArrayList<RotationAxis> ();
      
      axes.add (RotationAxis.Y_AXIS);
      axes.add (RotationAxis.Z_AXIS);
      axes.add (RotationAxis.X_AXIS);
      
      order = new RotationOrdering (axes);
      
      return order;
    }
  },
  
  ZXY
  {
    @Override
    public Quat4d getResultQuat4d (Quat4d quatX, Quat4d quatY, Quat4d quatZ)
    {
      Quat4d resultQuat = null;
      
      resultQuat = new Quat4d (quatZ);
      resultQuat.mul          (quatX);
      resultQuat.mul          (quatY);
      
      return resultQuat;
    }
    
    @Override
    public RotationOrdering getRotationOrdering ()
    {
      RotationOrdering   order = null;
      List<RotationAxis> axes  = new ArrayList<RotationAxis> ();
      
      axes.add (RotationAxis.Z_AXIS);
      axes.add (RotationAxis.X_AXIS);
      axes.add (RotationAxis.Y_AXIS);
      
      order = new RotationOrdering (axes);
      
      return order;
    }
  },
  
  ZYX
  {
    @Override
    public Quat4d getResultQuat4d (Quat4d quatX, Quat4d quatY, Quat4d quatZ)
    {
      Quat4d resultQuat = null;
      
      resultQuat = new Quat4d (quatZ);
      resultQuat.mul          (quatY);
      resultQuat.mul          (quatX);
      
      return resultQuat;
    }
    
    @Override
    public RotationOrdering getRotationOrdering ()
    {
      RotationOrdering   order = null;
      List<RotationAxis> axes  = new ArrayList<RotationAxis> ();
      
      axes.add (RotationAxis.Z_AXIS);
      axes.add (RotationAxis.Y_AXIS);
      axes.add (RotationAxis.X_AXIS);
      
      order = new RotationOrdering (axes);
      
      return order;
    }
  };
  
  
  /**
   * <div class="introConstructor">
   *   Defines a rotation order for three quaternions, each around one
   *   axis.
   * </div>
   */
  private DefaultQuatRotationOrder ()
  {
  }
}