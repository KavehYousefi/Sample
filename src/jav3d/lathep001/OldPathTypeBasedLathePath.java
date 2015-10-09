package jav3d.lathep001;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point2d;

import ky.Position;


public class OldPathTypeBasedLathePath
implements   LathePath
{
  private List<LathePathPoint> pathPoints;
  
  
  public OldPathTypeBasedLathePath ()
  {
    pathPoints = new ArrayList<LathePathPoint> ();
  }
  
  
  public List<LathePathPoint> getPathPoints ()
  {
    return new ArrayList<LathePathPoint> (pathPoints);
  }
  
  public LathePathPoint getPathPointAtIndex (int index)
  {
    return pathPoints.get (index);
  }
  
  public int getPathPointCount ()
  {
    return pathPoints.size ();
  }
  
  public void addPathPoint (double x, double y, LathePathType typ)
  {
    LathePathPoint neuerPunkt = new LathePathPoint (x, y, typ);
    
    pathPoints.add (neuerPunkt);
  }
  
  public List<Point2d> getPoint2dList ()
  {
    List<Point2d> point2dListe = new ArrayList<Point2d> ();
    
    for (LathePathPoint punkt : pathPoints)
    {
      Point2d point2d = punkt.nennePoint2d ();
      
      point2dListe.add (point2d);
    }
    
    return point2dListe;
  }
  
  
  @Override
  public List<Position> getPoints ()
  {
    List<Position> positionList = new ArrayList<Position> ();
    List<Point2d>  point2dListe = getPoint2dList          ();
    
    for (Point2d point2d : point2dListe)
    {
      positionList.add (new Position (point2d));
    }
    
    return positionList;
  }
}