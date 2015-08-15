// 23.07.2015

package j3dextraction;

import safercode.CheckingUtils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.media.j3d.Geometry;
import javax.media.j3d.Group;
import javax.media.j3d.Leaf;
import javax.media.j3d.Link;
import javax.media.j3d.Shape3D;


// Better name: "GroupNodeInspector"
public class SceneGraphDataExtractor
{
  private List<Group>    groups;
  private List<Link>     links;
  private List<Shape3D>  shapes;
  private List<Geometry> geometries;
  private List<Leaf>     leaves;
  private StringBuilder  reportBuffer;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an empty group node inspector.
   * </div>
   */
  public SceneGraphDataExtractor ()
  {
    this.groups       = new ArrayList<Group>    ();
    this.links        = new ArrayList<Link>     ();
    this.shapes       = new ArrayList<Shape3D>  ();
    this.geometries   = new ArrayList<Geometry> ();
    this.leaves       = new ArrayList<Leaf>     ();
    this.reportBuffer = new StringBuilder       ();
  }
  
  
  // Better name: "inspect()" or "analyze()"?
  /**
   * <div class="introMethod">
   *   Executes a traversal and examination of the given group node.
   * </div>
   * 
   * <div>
   *   <strong>Note:</strong>
   *   All previous data will be lost and replaced by the examination
   *   results; this includes scene graph nodes as well as the
   *   report buffer.
   * </div>
   * 
   * @param root  The <code>Group</code> node to start the inspection
   *              from. This node will be included in the results.
   */
  public void examine (Group root)
  {
    CheckingUtils.checkForNull (root, "Root node is null.");
    reset     ();                       // Clear all collections.
    visitNode (root);                   // Collect new data.
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns a list of the last examination run's <code>Group</code>
   *   objects in the order they were encountered, with the root
   *   node being included.
   * </div>
   * 
   * @return  A list of the traversed <code>Group</code> nodes.
   */
  public List<Group> getGroups ()
  {
    return groups;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a list of the last examination run's <code>Link</code>
   *   objects in the order they were encountered.
   * </div>
   * 
   * @return  A list of the traversed <code>Link</code> nodes.
   */
  public List<Link> getLinks ()
  {
    return links;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a list of the last examination run's <code>Shape3D</code>
   *   objects in the order they were encountered.
   * </div>
   * 
   * @return  A list of the traversed <code>Shape3D</code> nodes.
   */
  public List<Shape3D> getShape3Ds ()
  {
    return shapes;
  }
  
  /**
   * <div class="introMethod">
   *   Returns a list of the last examination run's
   *   <code>Geometry</code> objects in the order they were encountered.
   * </div>
   * 
   * @return  A list of the traversed <code>Geometry</code>
   *          node components.
   */
  public List<Geometry> getGeometries ()
  {
    return geometries;
  }
  
  public List<Leaf> getLeaves ()
  {
    return leaves;
  }
  
  /**
   * <div class="introMethod">
   *   Returns the <code>StringBuilder</code> containing the last
   *   traversal's report data.
   * </div>
   * 
   * @return  A <code>StringBuilder</code> with the traversal
   *          report data.
   */
  public StringBuilder getTraversalReportBuffer ()
  {
    return reportBuffer;
  }
  
  /**
   * <div class="introMethod">
   *   Resets all examined data, setting the object back to its initial
   *   state.
   * </div>
   */
  public void reset ()
  {
    this.groups       = new ArrayList<Group>    ();
    this.links        = new ArrayList<Link>     ();
    this.shapes       = new ArrayList<Shape3D>  ();
    this.geometries   = new ArrayList<Geometry> ();
    this.leaves       = new ArrayList<Leaf>     ();
    this.reportBuffer = new StringBuilder       ();
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void visitNode (Group root)
  {
    Enumeration<?> children = root.getAllChildren ();
    
    appendToReportBuffer ("Inspecting %s%n", root);
    
    while (children.hasMoreElements ())
    {
      Object childObject = children.nextElement ();
      
      // Very important: XJ3D loaded files use "Link" objects!
      if (childObject instanceof Link)
      {
        Link link = (Link) childObject;
        
        appendToReportBuffer ("  - Link : %s --> %s%n",
                              childObject,
                              link.getSharedGroup ());
        
        links.add ((Link) childObject);
        visitNode (link.getSharedGroup ());
      }
      else if (childObject instanceof Shape3D)
      {
        Shape3D shape3D = (Shape3D) childObject;
        
        appendToReportBuffer ("  # Shape: %s%n", childObject);
        
        shapes.add                 (shape3D);
        collectGeometriesOfShape3D (shape3D);
      }
      else if (childObject instanceof Leaf)
      {
        appendToReportBuffer ("  - Leaf : %s%n", childObject);
        
        leaves.add ((Leaf) childObject);
      }
      else if (childObject instanceof Group)
      {
        appendToReportBuffer ("  + Group: %s%n", childObject);
        
        groups.add ((Group) childObject);
        visitNode  ((Group) childObject);
      }
      else
      {
        appendToReportBuffer ("??> %s", childObject);
      }
    }
  }
  
  /**
   * <div class="introMethod">
   *   Iterates through a given <code>Shape3D</code>'s geometries and
   *   stores them in the respective collection.
   * </div>
   * 
   * @param shape3D  The <code>Shape3D</code> whose geometries should
   *                 be collected.
   *                 It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>shape3D</code> equals
   *                               <code>null</code>.
   */
  private void collectGeometriesOfShape3D (Shape3D shape3D)
  {
    CheckingUtils.checkForNull (shape3D, "Shape3D is null.");
    
    Enumeration<?> geometriesOfShape = shape3D.getAllGeometries ();
    
    while (geometriesOfShape.hasMoreElements ())
    {
      Object   element  = geometriesOfShape.nextElement ();
      Geometry geometry = (Geometry) element;
      
      geometries.add (geometry);
      
      appendToReportBuffer ("    * Geometry: %s%n", geometry);
    }
  }
  
  private void appendToReportBuffer (String format, Object... arguments)
  {
    String textToAppend = String.format (format, arguments);
    
    reportBuffer.append (textToAppend);
  }
}
