package adtest01;

import java.util.Comparator;

import javax.vecmath.Point3d;


public class MarkerModelXCoordinateComparator
implements   Comparator<MarkerModel>
{
  public MarkerModelXCoordinateComparator ()
  {
  }
  
  
  
  @Override
  public int compare (MarkerModel model1, MarkerModel model2)
  {
    Point3d positionOfModel1 = model1.getPosition ();
    Point3d positionOfModel2 = model2.getPosition ();
    
    if (positionOfModel1 == null)
    {
      return 1;
    }
    else if (positionOfModel2 == null)
    {
      return -1;
    }
    else
    {
      if (positionOfModel1.getX () < positionOfModel2.getX ())
      {
        return 1;
      }
      else if (positionOfModel1.getX () == positionOfModel2.getX ())
      {
        return 0;
      }
      else
      {
        return -1;
      }
    }
  }
}
