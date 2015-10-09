package adtest01;

import java.util.HashMap;
import java.util.Map;


public class TransformHierarchyFactory
{
  private Map<String, TransformHierarchy> hierarchies;
  
  
  public TransformHierarchyFactory ()
  {
    this.hierarchies = new HashMap<String, TransformHierarchy> ();
  }
  
  
  public void addTransformHierarchy (String name, TransformHierarchy hierarchy)
  {
    hierarchies.put (name, hierarchy);
  }
  
  public TransformHierarchy getTransformHierarchyByName (String name)
  {
    return hierarchies.get (name);
  }
}
