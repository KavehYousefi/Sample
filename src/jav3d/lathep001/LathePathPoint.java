package jav3d.lathep001;

import javax.vecmath.Point2d;


public class LathePathPoint
{
  private Point2d       koordinaten;
  private LathePathType typ;
  
  
  public LathePathPoint (double x, double y, LathePathType typ)
  {
    koordinaten = new Point2d (x, y);
    this.typ    = typ;
  }
  
  
  public Point2d nennePoint2d ()
  {
    return new Point2d (koordinaten);
  }
  
  public double nenneX ()
  {
    return koordinaten.getX ();
  }
  
  public double nenneY ()
  {
    return koordinaten.getY ();
  }
  
  public LathePathType nenneLathePfadTyp ()
  {
    return typ;
  }
}