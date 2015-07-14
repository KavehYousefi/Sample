// 29.05.2015
// -> "http://devernay.free.fr/cours/opengl/materials.html"
// -> "http://cs.boisestate.edu/~tcole/cs221/fall08/vg/cgslides09.ppt"

package commies;

import java.util.EnumMap;
import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Color3f;


public class OpenGLMaterials
{
  public static enum MaterialName
  {
    /* Material table from -> "http://devernay.free.fr/cours/opengl/materials.html"
     * Create by the method "createDevernayMaterialMap ()".
     */
    EMERALD,
    JADE,
    OBSIDIAN,
    PEARL,
    RUBY,
    TURQUOISE,
    BRASS,
    BRONZE,
    CHROME,
    COPPER,
    GOLD,
    SILVER,
    BLACK_PLASTIC,
    CYAN_PLASTIC,
    GREEN_PLASTIC,
    RED_PLASTIC,
    WHITE_PLASTIC,
    YELLOW_PLASTIC,
    BLACK_RUBBER,
    CYAN_RUBBER,
    GREEN_RUBBER,
    RED_RUBBER,
    WHITE_RUBBER,
    YELLOW_RUBBER,
    
    /* Materials from -> "http://cs.boisestate.edu/~tcole/cs221/fall08/vg/cgslides09.ppt"
     * Create by the method "createBoisestateMaterialMap ()".
     */
    ALUMINIUM,
    BLACK_ONYX,
    BLUE_PLASTIC,
    COPPER_2,
    GOLD_2,
    RED_ALLOY;
    
    
    private MaterialName ()
    {
    }
  }
  
  
  private static Map<MaterialName, Material> materialMap = createMaterialMap ();
  
  
  private OpenGLMaterials ()
  {
  }
  
  
  public static Material getMaterial (MaterialName materialName)
  {
    return materialMap.get (materialName);
  }
  
  public static Appearance getAppearance (MaterialName materialName)
  {
    Appearance        appearance        = null;
    PolygonAttributes polygonAttributes = null;
    Material          material          = null;
    
    appearance        = new Appearance        ();
    polygonAttributes = new PolygonAttributes ();
    polygonAttributes.setCullFace (PolygonAttributes.CULL_NONE);
    
    material = getMaterial     (materialName);
    material.setLightingEnable (true);
    
    appearance.setPolygonAttributes (polygonAttributes);
    appearance.setMaterial          (material);
    
    return appearance;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private static Map<MaterialName, Material> createMaterialMap ()
  {
    Map<MaterialName, Material> materialMap = null;
    
    materialMap = new EnumMap<MaterialName, Material> (MaterialName.class);
    
    materialMap.putAll (createDevernayMaterialMap   ());
    materialMap.putAll (createBoisestateMaterialMap ());
    
    return materialMap;
  }
  
  private static Map<MaterialName, Material> createDevernayMaterialMap ()
  {
    Map<MaterialName, Material> materialMap = null;
    
    materialMap = new EnumMap<MaterialName, Material> (MaterialName.class);
    
    materialMap.put
    (
      MaterialName.EMERALD,
      new Material
      (
        new Color3f (0.02150f, 0.174500f, 0.02150f),
        new Color3f (0.00000f, 0.000000f, 0.00000f),
        new Color3f (0.07568f, 0.614240f, 0.07568f),
        new Color3f (0.63300f, 0.727811f, 0.63300f),
        76.8f
      )
    );
    
    materialMap.put
    (
      MaterialName.JADE,
      new Material
      (
        new Color3f (0.135000f, 0.222500f, 0.157500f),
        new Color3f (0.000000f, 0.000000f, 0.000000f),
        new Color3f (0.540000f, 0.890000f, 0.630000f),
        new Color3f (0.316228f, 0.316228f, 0.316228f),
        12.8f
      )
    );
    
    materialMap.put
    (
      MaterialName.OBSIDIAN,
      new Material
      (
        new Color3f (0.053750f, 0.050000f, 0.066250f),
        new Color3f (0.000000f, 0.000000f, 0.000000f),
        new Color3f (0.182750f, 0.170000f, 0.225250f),
        new Color3f (0.332741f, 0.328634f, 0.346435f),
        38.4f
      )
    );
    
    materialMap.put
    (
      MaterialName.PEARL,
      new Material
      (
        new Color3f (0.250000f, 0.207250f, 0.207250f),
        new Color3f (0.000000f, 0.000000f, 0.000000f),
        new Color3f (1.000000f, 0.829000f, 0.829000f),
        new Color3f (0.296648f, 0.296648f, 0.296648f),
        11.264f
      )
    );
    
    materialMap.put
    (
      MaterialName.RUBY,
      new Material
      (
        new Color3f (0.174500f, 0.011750f, 0.011750f),
        new Color3f (0.000000f, 0.000000f, 0.000000f),
        new Color3f (0.614240f, 0.041360f, 0.041360f),
        new Color3f (0.727811f, 0.626959f, 0.626959f),
        76.8f
      )
    );
    
    materialMap.put
    (
      MaterialName.TURQUOISE,
      new Material
      (
        new Color3f (0.100000f, 0.187250f, 0.174500f),
        new Color3f (0.000000f, 0.000000f, 0.000000f),
        new Color3f (0.396000f, 0.741510f, 0.691020f),
        new Color3f (0.297254f, 0.308290f, 0.306678f),
        12.8f
      )
    );
    
    materialMap.put
    (
      MaterialName.BRASS,
      new Material
      (
        new Color3f
        (
          0.329412f,
          0.223529f,
          0.027451f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.780392f,
          0.568627f,
          0.113725f
        ),
        new Color3f
        (
          0.992157f,
          0.941176f,
          0.807843f
        ),
        27.89743616f
      )
    );
    
    materialMap.put
    (
      MaterialName.BRONZE,
      new Material
      (
        new Color3f
        (
          0.2125f,
          0.1275f,
          0.054f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.714f,
          0.4284f,
          0.18144f
        ),
        new Color3f
        (
          0.393548f,
          0.271906f,
          0.166721f
        ),
        25.6f
      )
    );
    
    materialMap.put
    (
      MaterialName.CHROME,
      new Material
      (
        new Color3f
        (
          0.25f,
          0.25f,
          0.25f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.4f,
          0.4f,
          0.4f
        ),
        new Color3f
        (
          0.774597f,
          0.774597f,
          0.774597f
        ),
        76.8f
      )
    );
    
    materialMap.put
    (
      MaterialName.COPPER,
      new Material
      (
        new Color3f
        (
          0.19125f,
          0.0735f,
          0.0225f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.7038f,
          0.27048f,
          0.0828f
        ),
        new Color3f
        (
          0.256777f,
          0.137622f,
          0.086014f
        ),
        12.8f
      )
    );
    
    materialMap.put
    (
      MaterialName.GOLD,
      new Material
      (
        new Color3f
        (
          0.24725f,
          0.1995f,
          0.0745f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.75164f,
          0.60648f,
          0.22648f
        ),
        new Color3f
        (
          0.628281f,
          0.555802f,
          0.366065f
        ),
        51.2f
      )
    );
    
    materialMap.put
    (
      MaterialName.SILVER,
      new Material
      (
        new Color3f
        (
          0.19225f,
          0.19225f,
          0.19225f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.50754f,
          0.50754f,
          0.50754f
        ),
        new Color3f
        (
          0.508273f,
          0.508273f,
          0.508273f
        ),
        51.2f
      )
    );
    
    materialMap.put
    (
      MaterialName.BLACK_PLASTIC,
      new Material
      (
        new Color3f
        (
          0.0f,
          0.0f,
          0.0f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.01f,
          0.01f,
          0.01f
        ),
        new Color3f
        (
          0.50f,
          0.50f,
          0.50f
        ),
        32f
      )
    );
    
    materialMap.put
    (
      MaterialName.CYAN_PLASTIC,
      new Material
      (
        new Color3f
        (
          0.0f,
          0.1f,
          0.06f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.0f,
          0.50980392f,
          0.50980392f
        ),
        new Color3f
        (
          0.50196078f,
          0.50196078f,
          0.50196078f
        ),
        32f
      )
    );
    
    materialMap.put
    (
      MaterialName.GREEN_PLASTIC,
      new Material
      (
        new Color3f (0.00f, 0.00f, 0.00f),
        new Color3f (0.00f, 0.00f, 0.00f),
        new Color3f (0.10f, 0.35f, 0.10f),
        new Color3f (0.45f, 0.55f, 0.45f),
        32.0f
      )
    );
    
    materialMap.put
    (
      MaterialName.RED_PLASTIC,
      new Material
      (
        new Color3f
        (
          0.0f,
          0.0f,
          0.0f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.5f,
          0.0f,
          0.0f
        ),
        new Color3f
        (
          0.7f,
          0.6f,
          0.6f
        ),
        32f
      )
    );
    
    materialMap.put
    (
      MaterialName.WHITE_PLASTIC,
      new Material
      (
        new Color3f
        (
          0.0f,
          0.0f,
          0.0f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.55f,
          0.55f,
          0.55f
        ),
        new Color3f
        (
          0.70f,
          0.70f,
          0.70f
        ),
        32f
      )
    );
    
    materialMap.put
    (
      MaterialName.YELLOW_PLASTIC,
      new Material
      (
        new Color3f
        (
          0.0f,
          0.0f,
          0.0f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.5f,
          0.5f,
          0.0f
        ),
        new Color3f
        (
          0.60f,
          0.60f,
          0.50f
        ),
        32f
      )
    );
    
    materialMap.put
    (
      MaterialName.BLACK_RUBBER,
      new Material
      (
        new Color3f
        (
          0.02f,
          0.02f,
          0.02f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.01f,
          0.01f,
          0.01f
        ),
        new Color3f
        (
          0.4f,
          0.4f,
          0.4f
        ),
        10f
      )
    );
    
    materialMap.put
    (
      MaterialName.CYAN_RUBBER,
      new Material
      (
        new Color3f
        (
          0.0f,
          0.05f,
          0.05f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.4f,
          0.5f,
          0.5f
        ),
        new Color3f
        (
          0.04f,
          0.7f,
          0.7f
        ),
        10f
      )
    );
    
    materialMap.put
    (
      MaterialName.GREEN_RUBBER,
      new Material
      (
        new Color3f
        (
          0.0f,
          0.05f,
          0.0f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.4f,
          0.5f,
          0.4f
        ),
        new Color3f
        (
          0.04f,
          0.7f,
          0.04f
        ),
        10f
      )
    );
    
    materialMap.put
    (
      MaterialName.RED_RUBBER,
      new Material
      (
        new Color3f
        (
          0.05f,
          0.0f,
          0.0f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.5f,
          0.4f,
          0.4f
        ),
        new Color3f
        (
          0.7f,
          0.04f,
          0.04f
        ),
        10.0f
      )
    );
    
    materialMap.put
    (
      MaterialName.WHITE_RUBBER,
      new Material
      (
        new Color3f
        (
          0.05f,
          0.05f,
          0.05f
        ),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f
        (
          0.5f,
          0.5f,
          0.5f
        ),
        new Color3f
        (
          0.7f,
          0.7f,
          0.7f
        ),
        10.0f
      )
    );
    
    materialMap.put
    (
      MaterialName.YELLOW_RUBBER,
      new Material
      (
        new Color3f (0.05f, 0.05f, 0.00f),
        new Color3f (0.00f, 0.00f, 0.00f),
        new Color3f (0.50f, 0.50f, 0.40f),
        new Color3f (0.70f, 0.70f, 0.04f),
        10.0f
      )
    );
    
    return materialMap;
  }
  
  
  private static Map<MaterialName, Material> createBoisestateMaterialMap ()
  {
    Map<MaterialName, Material> materialMap   = null;
    Color3f                     ambDiffColor  = null;
    Color3f                     specularColor = null;
    Color3f                     emissiveColor = null;
    float                       shininess     = 1.0f;
    
    materialMap = new EnumMap<MaterialName, Material> (MaterialName.class);
    
    // Copper:
    ambDiffColor  = new Color3f (0.30f, 0.10f, 0.00f);
    specularColor = new Color3f (0.75f, 0.30f, 0.00f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 10.0f;
    materialMap.put
    (
      MaterialName.COPPER_2,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Red alloy:
    ambDiffColor  = new Color3f (0.34f, 0.00f, 0.34f);
    specularColor = new Color3f (0.84f, 0.00f, 0.00f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 15.0f;
    materialMap.put
    (
      MaterialName.RED_ALLOY,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Gold:
    ambDiffColor  = new Color3f (0.49f, 0.34f, 0.00f);
    specularColor = new Color3f (0.89f, 0.79f, 0.00f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 17.0f;
    materialMap.put
    (
      MaterialName.GOLD_2,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Aluminium:
    ambDiffColor  = new Color3f (0.37f, 0.37f, 0.37f);
    specularColor = new Color3f (0.89f, 0.89f, 0.89f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 17.0f;
    materialMap.put
    (
      MaterialName.ALUMINIUM,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Black onyx:
    ambDiffColor  = new Color3f (0.00f, 0.00f, 0.00f);
    specularColor = new Color3f (0.72f, 0.72f, 0.72f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 23.0f;
    materialMap.put
    (
      MaterialName.BLACK_ONYX,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Blue plastic:
    ambDiffColor  = new Color3f (0.20f, 0.20f, 0.70f);
    specularColor = new Color3f (0.85f, 0.85f, 0.85f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 22.0f;
    materialMap.put
    (
      MaterialName.BLUE_PLASTIC,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    return materialMap;
  }
  
  private static Material createMaterial
  (
    Color3f ambDiffColor,
    Color3f specularColor,
    Color3f emissiveColor,
    float   shininess
  )
  {
    Material          material          = null;
    PolygonAttributes polygonAttributes = null;
    
    material          = new Material          ();
    polygonAttributes = new PolygonAttributes ();
    polygonAttributes.setCullFace (PolygonAttributes.CULL_NONE);
    
    material = new Material
    (
      ambDiffColor,
      emissiveColor,
      ambDiffColor,
      specularColor,
      shininess
    );
    material.setLightingEnable (true);
    
    return material;
  }
}