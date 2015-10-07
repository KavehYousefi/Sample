package jav3d.offloader.processor;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color4f;


/**
 * <div class="introClass">
 *   A face index group combines faces with equal number of vertices
 *   into a single collection.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       FaceIndexGroup.java
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Author:
 *     </td>
 *     <td>
 *       Kaveh Yousefi
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Date:
 *     </td>
 *     <td>
 *       24.06.2015
 *     </td>
 *   </tr>
 *   <tr>
 *     <td>
 *       Version:
 *     </td>
 *     <td>
 *       1.0
 *     </td>
 *   </tr>
 * </table>
 * 
 * <div class="classHistoryTitle">History:</div>
 * <table class="classHistoryTable">
 *   <tr>
 *     <th>Date</th>
 *     <th>Version</th>
 *     <th>Changes</th>
 *   </tr>
 *   <tr>
 *     <td>24.06.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class FaceIndexGroup
{
  private List<Face> faces;
  private int        indexCountPerFace;
  
  
  /**
   * <div class="introConstructor">
   *   Creates an empty collection of faces.
   * </div>
   */
  public FaceIndexGroup ()
  {
    this.faces             = new ArrayList<Face> ();
    this.indexCountPerFace = 0;
  }
  
  
  public List<Face> getFaces ()
  {
    return faces;
  }
  
  public Face getFaceAtIndex (int faceIndex)
  {
    return faces.get (faceIndex);
  }
  
  public int getFaceCount ()
  {
    return faces.size ();
  }
  
  public void addFace (Face face)
  {
    faces.add (face);
  }
  
  public List<Integer> getVertexIndices ()
  {
    List<Integer> vertexIndices = new ArrayList<Integer> ();
    
    for (Face face : faces)
    {
      vertexIndices.addAll (face.getVertexIndices ());
    }
    
    return vertexIndices;
  }
  
  /* TODO:
   * [Source: "http://www.holmes3d.net/graphics/offfiles/offdesc.txt"]
   *   
   *   HANDLE CASE OF COLORCOMPONENT_COUNT = 2 (?)
   *   
   *   HANDLE CASE OF SOME FACES HAVING A COLOR AND SOME NOT
   *   => DEFAULT COLOR (FOR UNCOLORED FACES?): R = G = B = A = 0.666.
   */
  public List<Double> getColorComponents ()
  {
    List<Double> colorComponents = new ArrayList<Double> ();
    
    for (Face face : faces)
    {
      Color4f color4f = face.getColor4f ();
      
      if (color4f != null)
      {
        colorComponents.add (new Double (color4f.getX ()));
        colorComponents.add (new Double (color4f.getY ()));
        colorComponents.add (new Double (color4f.getZ ()));
        colorComponents.add (new Double (color4f.getW ()));
      }
    }
    
    return colorComponents;
  }
  
  // How many indices (= vertices) does one face have?
  public int getIndexCountPerFace ()
  {
    return indexCountPerFace;
  }
  
  public void setIndexCountPerFace (int indexCountPerFace)
  {
    this.indexCountPerFace = indexCountPerFace;
  }
}
