// 29.05.2015
// -> "http://devernay.free.fr/cours/opengl/materials.html"
// -> "http://cs.boisestate.edu/~tcole/cs221/fall08/vg/cgslides09.ppt"

package adtest01;

import java.util.EnumMap;
import java.util.Map;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.PolygonAttributes;
import javax.vecmath.Color3f;


public class DefaultMaterials
{
  public static enum MaterialName
  {
    /* Materials from -> "http://cs.boisestate.edu/~tcole/cs221/fall08/vg/cgslides09.ppt", page 22
     * Create by the method "createBoisestateMaterialMap ()".
     * Author: Teresa Cole (?)
     */
    COLE_ALUMINIUM,
    COLE_BLACK_ONYX,
    COLE_BLUE_PLASTIC,
    COLE_COPPER,
    COLE_GOLD,
    COLE_RED_ALLOY,
    
    /* Material table from -> "http://devernay.free.fr/cours/opengl/materials.html"
     * Create by the method "createDevernayMaterialMap ()".
     * Author: Mark J. Kilgard
     */
    KILGARD_EMERALD,
    KILGARD_JADE,
    KILGARD_OBSIDIAN,
    KILGARD_PEARL,
    KILGARD_RUBY,
    KILGARD_TURQUOISE,
    KILGARD_BRASS,
    KILGARD_BRONZE,
    KILGARD_CHROME,
    KILGARD_COPPER,
    KILGARD_GOLD,
    KILGARD_SILVER,
    KILGARD_BLACK_PLASTIC,
    KILGARD_CYAN_PLASTIC,
    KILGARD_GREEN_PLASTIC,
    KILGARD_RED_PLASTIC,
    KILGARD_WHITE_PLASTIC,
    KILGARD_YELLOW_PLASTIC,
    KILGARD_BLACK_RUBBER,
    KILGARD_CYAN_RUBBER,
    KILGARD_GREEN_RUBBER,
    KILGARD_RED_RUBBER,
    KILGARD_WHITE_RUBBER,
    KILGARD_YELLOW_RUBBER,
    
    /* Materials from -> "http://web.archive.org/web/20100725103839/http://www.cs.utk.edu/~kuck/materials_ogl.htm"
     * Create by the method "createHansenMaterialMap ()".
     * Author: Charles Hansen
     */
    HANSEN_BRASS,
    HANSEN_BRONZE,
    HANSEN_POLISHED_BRONZE,
    HANSEN_CHROME,
    HANSEN_COPPER,
    HANSEN_POLISHED_COPPER,
    HANSEN_GOLD,
    HANSEN_POLISHED_GOLD,
    HANSEN_PEWTER,
    HANSEN_SILVER,
    HANSEN_POLISHED_SILVER,
    HANSEN_EMERALD,
    HANSEN_JADE,
    HANSEN_OBSIDIAN,
    HANSEN_PEARL,
    HANSEN_RUBY,
    HANSEN_TURQUOISE,
    HANSEN_BLACK_PLASTIC,
    HANSEN_BLACK_RUBBER;
    
    
    private MaterialName ()
    {
    }
  }
  
  
  private static Map<MaterialName, Material> materialMap = createMaterialMap ();
  
  
  private DefaultMaterials ()
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
    materialMap.putAll (createHansenMaterialMap     ());
    
    return materialMap;
  }
  
  private static Map<MaterialName, Material> createDevernayMaterialMap ()
  {
    Map<MaterialName, Material> materialMap = null;
    
    materialMap = new EnumMap<MaterialName, Material> (MaterialName.class);
    
    materialMap.put
    (
      MaterialName.KILGARD_EMERALD,
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
      MaterialName.KILGARD_JADE,
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
      MaterialName.KILGARD_OBSIDIAN,
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
      MaterialName.KILGARD_PEARL,
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
      MaterialName.KILGARD_RUBY,
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
      MaterialName.KILGARD_TURQUOISE,
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
      MaterialName.KILGARD_BRASS,
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
      MaterialName.KILGARD_BRONZE,
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
      MaterialName.KILGARD_CHROME,
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
      MaterialName.KILGARD_COPPER,
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
      MaterialName.KILGARD_GOLD,
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
      MaterialName.KILGARD_SILVER,
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
      MaterialName.KILGARD_BLACK_PLASTIC,
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
      MaterialName.KILGARD_CYAN_PLASTIC,
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
      MaterialName.KILGARD_GREEN_PLASTIC,
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
      MaterialName.KILGARD_RED_PLASTIC,
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
      MaterialName.KILGARD_WHITE_PLASTIC,
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
      MaterialName.KILGARD_YELLOW_PLASTIC,
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
      MaterialName.KILGARD_BLACK_RUBBER,
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
      MaterialName.KILGARD_CYAN_RUBBER,
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
      MaterialName.KILGARD_GREEN_RUBBER,
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
      MaterialName.KILGARD_RED_RUBBER,
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
      MaterialName.KILGARD_WHITE_RUBBER,
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
      MaterialName.KILGARD_YELLOW_RUBBER,
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
      MaterialName.COLE_COPPER,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Red alloy:
    ambDiffColor  = new Color3f (0.34f, 0.00f, 0.34f);
    specularColor = new Color3f (0.84f, 0.00f, 0.00f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 15.0f;
    materialMap.put
    (
      MaterialName.COLE_RED_ALLOY,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Gold:
    ambDiffColor  = new Color3f (0.49f, 0.34f, 0.00f);
    specularColor = new Color3f (0.89f, 0.79f, 0.00f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 17.0f;
    materialMap.put
    (
      MaterialName.COLE_GOLD,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Aluminium:
    ambDiffColor  = new Color3f (0.37f, 0.37f, 0.37f);
    specularColor = new Color3f (0.89f, 0.89f, 0.89f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 17.0f;
    materialMap.put
    (
      MaterialName.COLE_ALUMINIUM,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Black onyx:
    ambDiffColor  = new Color3f (0.00f, 0.00f, 0.00f);
    specularColor = new Color3f (0.72f, 0.72f, 0.72f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 23.0f;
    materialMap.put
    (
      MaterialName.COLE_BLACK_ONYX,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    // Blue plastic:
    ambDiffColor  = new Color3f (0.20f, 0.20f, 0.70f);
    specularColor = new Color3f (0.85f, 0.85f, 0.85f);
    emissiveColor = new Color3f (0.00f, 0.00f, 0.00f);
    shininess     = 22.0f;
    materialMap.put
    (
      MaterialName.COLE_BLUE_PLASTIC,
      createMaterial (ambDiffColor, specularColor, emissiveColor, shininess)
    );
    
    return materialMap;
  }
  
  private static Map<MaterialName, Material> createHansenMaterialMap ()
  {
    Map<MaterialName, Material> materialMap = null;
    
    materialMap = new EnumMap<MaterialName, Material> (MaterialName.class);
    
    materialMap.put
    (
      MaterialName.HANSEN_BRASS,
      new Material
      (
        new Color3f (0.329412f, 0.223529f, 0.027451f),
        new Color3f (0.000000f, 0.000000f, 0.000000f),
        new Color3f (0.780392f, 0.568627f, 0.113725f),
        new Color3f (0.992157f, 0.941176f, 0.807843f),
        27.8974f
      )
    );
    
    materialMap.put
    (
      MaterialName.HANSEN_BRONZE,
      new Material
      (
        new Color3f (0.2125f, 0.1275f, 0.054f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.714f, 0.4284f, 0.18144f),
        new Color3f (0.393548f, 0.271906f, 0.166721f),
        25.6f
      )
    );
    
    materialMap.put
    (
      MaterialName.HANSEN_POLISHED_BRONZE,
      new Material
      (
        new Color3f (0.25f, 0.148f, 0.06475f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.4f, 0.2368f, 0.1036f),
        new Color3f (0.774597f, 0.458561f, 0.200621f),
        76.8f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_CHROME,
      new Material
      (
        new Color3f (0.25f, 0.25f, 0.25f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.4f, 0.4f, 0.4f),
        new Color3f (0.774597f, 0.774597f, 0.774597f),
        76.8f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_COPPER,
      new Material
      (
        new Color3f (0.19125f, 0.0735f, 0.0225f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.7038f, 0.27048f, 0.0828f),
        new Color3f (0.256777f, 0.137622f, 0.086014f),
        12.8f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_POLISHED_COPPER,
      new Material
      (
        new Color3f (0.2295f, 0.08825f, 0.0275f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.5508f, 0.2118f, 0.066f),
        new Color3f (0.580594f, 0.223257f, 0.0695701f),
        51.2f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_GOLD,
      new Material
      (
        new Color3f (0.24725f, 0.1995f, 0.0745f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.75164f, 0.60648f, 0.22648f),
        new Color3f (0.628281f, 0.555802f, 0.366065f),
        51.2f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_POLISHED_GOLD,
      new Material
      (
        new Color3f (0.24725f, 0.2245f, 0.0645f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.34615f, 0.3143f, 0.0903f),
        new Color3f (0.797357f, 0.723991f, 0.208006f),
        83.2f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_PEWTER,
      new Material
      (
        new Color3f (0.105882f, 0.058824f, 0.113725f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.427451f, 0.470588f, 0.541176f),
        new Color3f (0.333333f, 0.333333f, 0.521569f),
        9.84615f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_SILVER,
      new Material
      (
        new Color3f (0.19225f, 0.19225f, 0.19225f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.50754f, 0.50754f, 0.50754f),
        new Color3f (0.508273f, 0.508273f, 0.508273f),
        51.2f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_POLISHED_SILVER,
      new Material
      (
        new Color3f (0.23125f, 0.23125f, 0.23125f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.2775f, 0.2775f, 0.2775f),
        new Color3f (0.773911f, 0.773911f, 0.773911f),
        89.6f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_EMERALD,
      new Material
      (
        new Color3f (0.0215f, 0.1745f, 0.0215f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.07568f, 0.61424f, 0.07568f),
        new Color3f (0.633f, 0.727811f, 0.633f),
        76.8f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_JADE,
      new Material
      (
        new Color3f (0.135f, 0.2225f, 0.1575f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.54f, 0.89f, 0.63f),
        new Color3f (0.316228f, 0.316228f, 0.316228f),
        12.8f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_OBSIDIAN,
      new Material
      (
        new Color3f (0.05375f, 0.05f, 0.06625f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.18275f, 0.17f, 0.22525f),
        new Color3f (0.332741f, 0.328634f, 0.346435f),
        38.4f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_PEARL,
      new Material
      (
        new Color3f (0.25f, 0.20725f, 0.20725f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (1.0f, 0.829f, 0.829f),
        new Color3f (0.296648f, 0.296648f, 0.296648f),
        11.264f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_RUBY,
      new Material
      (
        new Color3f (0.1745f, 0.01175f, 0.01175f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.61424f, 0.04136f, 0.04136f),
        new Color3f (0.727811f, 0.626959f, 0.626959f),
        76.8f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_TURQUOISE,
      new Material
      (
        new Color3f (0.1f, 0.18725f, 0.1745f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.396f, 0.74151f, 0.69102f),
        new Color3f (0.297254f, 0.30829f, 0.306678f),
        12.8f
      )
    );
  
   
    
    materialMap.put
    (
      MaterialName.HANSEN_BLACK_PLASTIC,
      new Material
      (
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.01f, 0.01f, 0.01f),
        new Color3f (0.50f, 0.50f, 0.50f),
        32.0f
      )
    );
    
    materialMap.put
    (
      MaterialName.HANSEN_BLACK_RUBBER,
      new Material
      (
        new Color3f (0.02f, 0.02f, 0.02f),
        new Color3f (0.0f, 0.0f, 0.0f),
        new Color3f (0.01f, 0.01f, 0.01f),
        new Color3f (0.4f, 0.4f, 0.4f),
        10.0f
      )
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