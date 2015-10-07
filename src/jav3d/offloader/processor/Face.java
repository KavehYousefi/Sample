// 24.06.2015

package jav3d.offloader.processor;

import jav3d.offloader.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Color4f;


public class Face
{
  private List<Integer> indices;
  private int           indexCount;
  private List<Double>  colorComponents;
  private List<Integer> integerColorComponents;
  private Integer       colorMapIndex;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a face without any data.
   * </div>
   */
  public Face ()
  {
    this.indices                = new ArrayList<Integer> ();
    this.indexCount             = 0;
    this.colorComponents        = new ArrayList<Double>  ();
    this.integerColorComponents = new ArrayList<Integer> ();
    this.colorMapIndex          = null;
  }
  
  
  public List<Integer> getVertexIndices ()
  {
    return indices;
  }
  
  public void addVertexIndex (Integer vertexIndex)
  {
    indices.add (vertexIndex);
  }
  
  public int getVertexIndexCount ()
  {
    return indexCount;
  }
  
  public void setVertexIndexCount (int indexCount)
  {
    this.indexCount = indexCount;
  }
  
  public List<Double> getColorComponents ()
  {
    return colorComponents;
  }
  
  public void addColorComponent (Double colorComponent)
  {
    colorComponents.add (colorComponent);
  }
  
  // Returns Color4f, if number of color components >= 3 (RGB/RGBA),
  // otherwise return "null".
  public Color4f getColor4f ()
  {
    Color4f color4f             = null;
    int     colorComponentCount = colorComponents.size ();
    
    if (colorComponentCount >= 3)
    {
      color4f = ColorUtils.getColor4fFromColorComponentList (colorComponents);
    }
    else
    {
      color4f = null;
    }
    
    return color4f;
  }
  
  public List<Integer> getIntegerColorComponents ()
  {
    return integerColorComponents;
  }
  
  public void addIntegerColorComponent (int integerColorComponent)
  {
    integerColorComponents.add (integerColorComponent);
  }
  
  public Integer getColorMapIndex ()
  {
    return colorMapIndex;
  }
  
  public void setColorMapIndex (Integer colorMapIndex)
  {
    this.colorMapIndex = colorMapIndex;
  }
}