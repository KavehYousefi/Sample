// 02.06.2015

package commies;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;


public class ModelInspection
{
  public ModelInspection ()
  {
    
  }
  
  
  public List<Shape3D> getShape3Ds (Group root)
  {
    List<Shape3D>  shapes   = new ArrayList<Shape3D> ();
    Enumeration<?> children = root.getAllChildren ();
    
    while (children.hasMoreElements ())
    {
      Object childObject = children.nextElement ();
      
      if (childObject instanceof Group)
      {
        shapes.addAll (getShape3Ds ((Group) childObject));
      }
      else if (childObject instanceof Shape3D)
      {
        shapes.add ((Shape3D) childObject);
      }
    }
    
    return shapes;
  }
}