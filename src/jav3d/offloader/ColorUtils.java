package jav3d.offloader;

import java.util.List;

import javax.vecmath.Color4f;


public class ColorUtils
{
  /**
   * <div class="introConstructor">
   *   A non-functional constructor.
   * </div>
   */
  private ColorUtils ()
  {
  }
  
  
  /**
   * <div class="introMethod">
   *   Creates a <code>Color4f</code> object from a list of double
   *   precision component values.
   * </div>
   * 
   * <div>
   *   The basic color is a <code>Color4f (0.0f, 0.0f, 0.0f, 1.0)</code>
   *   instance, which will be filled with the given color components,
   *   as far as possible.
   * </div>
   * 
   * @param colorComponent  A list of double precision floating values
   *                        to convert to a <code>Color4f</code>
   *                        instance.
   *                        It must not be <code>null</code>.
   * 
   * @throws NullPointerException  If the <code>colorComponents</code>
   *                               list equals <code>null</code>.
   */
  public static Color4f getColor4fFromColorComponentList (List<Double> colorComponents)
  {
    Color4f color          = null;
    float[] floatArray     = null;
    int     componentCount = 0;
    
    floatArray     = new float[] {0.0f, 0.0f, 0.0f, 1.0f};
    componentCount = colorComponents.size ();
    
    for (int componentIndex = 0; componentIndex < componentCount; componentIndex++)
    {
      double component = colorComponents.get (componentIndex);
      
      floatArray[componentIndex] = new Double (component).floatValue ();
    }
    
    color = new Color4f (floatArray);
    
    return color;
  }
}
