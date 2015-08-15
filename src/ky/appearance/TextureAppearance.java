// 19.12.2012

/*
 * -> Transparenz fuer Textur: "http://stackoverflow.com/questions/3794707/setting-transparency-makes-textures-translucent"
 */

package ky.appearance;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import javax.media.j3d.Appearance;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Material;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Texture2D;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Vector4f;

import com.sun.j3d.utils.image.TextureLoader;


public class TextureAppearance extends Appearance
{
  public TextureAppearance
  (
    Color3f   ambientColor,
    Color3f   diffuseColor,
    Color3f   emissiveColor,
    Color3f   specularColor,
    float     shininess,
    String    bildpfad,
    Component komponente
  )
  {
    Material         material    = new Material ();
    TextureLoader    texturLader = new TextureLoader (bildpfad,
                                                       komponente);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D          textur    = new Texture2D
    (
      Texture2D.BASE_LEVEL,
      Texture2D.RGBA,
      texturBild.getWidth (),
      texturBild.getHeight ()
    );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true);
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true          );
//    material.setAmbientColor   (ambientColor  );
//    material.setEmissiveColor   (emissiveColor  );
//    material.setDiffuseColor   (diffuseColor  );
//    material.setSpecularColor   (specularColor  );
    setMaterial                (material      );
    setTexture                 (textur        );
  }
  
  public TextureAppearance
  (
    String bildpfad,
    float  shininess
  )
  {
    Material         material    = new Material ();
    TextureLoader    texturLader = new TextureLoader (bildpfad, null);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D        textur      = new Texture2D
                                   (
                                     Texture2D.BASE_LEVEL,
                                     Texture2D.RGBA,
                                     texturBild.getWidth (),
                                     texturBild.getHeight ()
                                   );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true         );
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true     );
    material.setShininess      (shininess);
    setMaterial                (material );
    setTexture                 (textur   );
  }
  
  public TextureAppearance
  (
    String  bildpfad,
    float   transparenz,
    int     transparenzModus
  )
  {
    Material          material    = new Material ();
    TextureLoader     texturLader  = new TextureLoader (bildpfad, null);
    ImageComponent2D  texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D          textur      = new Texture2D
                                    (
                                      Texture2D.BASE_LEVEL,
                                      Texture2D.RGBA,
                                      texturBild.getWidth (),
                                      texturBild.getHeight ()
                                    );
    TextureAttributes      texturAttribs = new TextureAttributes ();
    TransparencyAttributes transpAttribs = new TransparencyAttributes ();
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true         );
    
    texturAttribs.setTextureMode      (TextureAttributes.MODULATE);
    transpAttribs.setTransparencyMode (transparenzModus);
    transpAttribs.setTransparency     (transparenz);
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true    );
    setMaterial                (material);
    setTexture                 (textur  );
    setTextureAttributes       (texturAttribs);
    setTransparencyAttributes  (transpAttribs);
  }
  
  // 28.07.2013
  public TextureAppearance (String  bildpfad)
  {
    Material         material    = new Material ();
    TextureLoader    texturLader = new TextureLoader (bildpfad, null);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D        textur      = new Texture2D
    (
      Texture2D.BASE_LEVEL,
      Texture2D.RGBA,
      texturBild.getWidth (),
      texturBild.getHeight ()
    );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true         );
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true    );
    setMaterial                (material);
    setTexture                 (textur  );
  }
  
  //+/ 10.03.2013
  public TextureAppearance
  (
    String  bildpfad,
    boolean mitTexCoordGeneration,
    int     generationMode,
    int     textureFormat
  )
  {
    Material          material    = new Material ();
    TextureLoader      texturLader  = new TextureLoader (bildpfad, null);
    ImageComponent2D  texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D          textur      = new Texture2D
                                    (
                                      Texture2D.BASE_LEVEL,
                                      Texture2D.RGBA,
                                      texturBild.getWidth (),
                                      texturBild.getHeight ()
                                    );
    
    // Die Textur wird eingestellt.
    textur.setImage   (0, texturBild);
    textur.setEnable (true         );
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true    );
    setMaterial                 (material);
    setTexture                 (textur  );
    
    
    if (mitTexCoordGeneration)
    {
      TexCoordGeneration  texCoordGen  = new TexCoordGeneration
                                        (
                                          generationMode,
                                          textureFormat
                                        );
      setTexCoordGeneration (texCoordGen);
    }
  }
  
  public TextureAppearance
  (
    String  bildpfad,
    boolean mitTexCoordGeneration
  )
  {
    Material         material    = new Material ();
    TextureLoader    texturLader = new TextureLoader (bildpfad, null);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D        textur      = new Texture2D
    (
      Texture2D.BASE_LEVEL,
      Texture2D.RGBA,
      texturBild.getWidth  (),
      texturBild.getHeight ()
    );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true);
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true);
    setMaterial                (material);
    setTexture                 (textur  );
    
    
    if (mitTexCoordGeneration)
    {
      TexCoordGeneration texCoordGen = new TexCoordGeneration ();
      setTexCoordGeneration (texCoordGen);
    }
  }
  
  public TextureAppearance
  (
    String      bildpfad,
    boolean     mitTexCoordGeneration,
    Transform3D textureTransform
  )
  {
    Material         material    = new Material ();
    TextureLoader    texturLader = new TextureLoader (bildpfad, null);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D        textur      = new Texture2D
    (
      Texture2D.BASE_LEVEL,
      Texture2D.RGBA,
      texturBild.getWidth  (),
      texturBild.getHeight ()
    );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true);
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true);
    setMaterial                (material);
    setTexture                 (textur  );
    
    if (mitTexCoordGeneration)
    {
      TexCoordGeneration texCoordGen = new TexCoordGeneration ();
      setTexCoordGeneration (texCoordGen);
    }
    
    if (textureTransform != null)
    {
      TextureAttributes textureAttribs = new TextureAttributes ();
      
      textureAttribs.setTextureTransform (textureTransform);
      setTextureAttributes               (textureAttribs);
    }
  }
  
  public TextureAppearance
  (
    String      bildpfad,
    boolean     mitTexCoordGeneration,
    Transform3D textureTransform,
    int         textureMode
  )
  {
    Material         material    = new Material ();
    TextureLoader    texturLader = new TextureLoader (bildpfad, null);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D        textur      = new Texture2D
    (
      Texture2D.BASE_LEVEL,
      Texture2D.RGBA,
      texturBild.getWidth  (),
      texturBild.getHeight ()
    );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true);
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true);
    setMaterial                (material);
    setTexture                 (textur  );
    
    if (mitTexCoordGeneration)
    {
      TexCoordGeneration texCoordGen = new TexCoordGeneration ();
      setTexCoordGeneration (texCoordGen);
    }
    
    TextureAttributes textureAttribs = getTextureAttributes ();
    
    if (textureAttribs == null)
    {
      textureAttribs = new TextureAttributes ();
    }
    
    if (textureTransform != null)
    {
      textureAttribs.setTextureTransform (textureTransform);
    }
    
    textureAttribs.setTextureMode (textureMode);
    setTextureAttributes          (textureAttribs);
  }
  
  public TextureAppearance
  (
    BufferedImage grafik,
    boolean       mitTexCoordGeneration
  )
  {
    Material         material    = new Material ();
    String           format      = null;
    TextureLoader    texturLader = new TextureLoader (grafik, format);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D        textur      = new Texture2D
    (
      Texture2D.BASE_LEVEL,
      Texture2D.RGBA,
      texturBild.getWidth (),
      texturBild.getHeight ()
    );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true         );
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true    );
    setMaterial                (material);
    setTexture                 (textur  );
    
    if (mitTexCoordGeneration)
    {
      TexCoordGeneration texCoordGen = new TexCoordGeneration ();
//      texCoordGen.setPlaneS(new Vector4f (0.5f, 0.5f, 0.7f, 0.8f));
      setTexCoordGeneration (texCoordGen);
    }
  }
  
  public TextureAppearance
  (
    BufferedImage grafik,
    boolean       mitTexCoordGeneration,
    boolean       setPlaneS
  )
  {
    Material         material    = new Material ();
    String           format      = null;
    TextureLoader    texturLader = new TextureLoader (grafik, format);
    ImageComponent2D texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D        textur      = new Texture2D
    (
      Texture2D.BASE_LEVEL,
      Texture2D.RGBA,
      texturBild.getWidth (),
      texturBild.getHeight ()
    );
    
    // Die Textur wird eingestellt.
    textur.setImage  (0, texturBild);
    textur.setEnable (true         );
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true    );
    setMaterial                (material);
    setTexture                 (textur  );
    
    if (mitTexCoordGeneration)
    {
      TexCoordGeneration texCoordGen = new TexCoordGeneration ();
      
      if (setPlaneS)
      {
        texCoordGen.setPlaneS(new Vector4f (0.5f, 0.5f, 0.7f, 0.8f));
      }
      
      setTexCoordGeneration (texCoordGen);
    }
  }
  
  public TextureAppearance
  (
    BufferedImage grafik
  )
  {
    Material          material    = new Material ();
    String            format      = null;
    TextureLoader      texturLader  = new TextureLoader (grafik, format);
    ImageComponent2D  texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D          textur      = new Texture2D
                                    (
                                      Texture2D.BASE_LEVEL,
                                      Texture2D.RGBA,
                                      texturBild.getWidth (),
                                      texturBild.getHeight ()
                                    );
    
    // Die Textur wird eingestellt.
    textur.setImage   (0, texturBild);
    textur.setEnable (true         );
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true     );
    material.setShininess       (128.0f   );
    setMaterial                 (material );
    setTexture                 (textur   );
  }
  
  // 01.08.2013
  public TextureAppearance
  (
    Paint    texturFuell,
    double  texturBreite,
    double  texturHoehe,
    float    shininess
  )
  {
    int                bildBreite  = (int) texturBreite;
    int                bildHoehe    = (int) texturHoehe;
    BufferedImage      buffImage    = new BufferedImage
    (
      bildBreite, bildHoehe,
      BufferedImage.TYPE_4BYTE_ABGR
    );
    Graphics2D        graphics    = buffImage.createGraphics ();
    
    graphics.setPaint (texturFuell);
    graphics.fillRect (0, 0, bildBreite, bildHoehe);
    
    Material          material    = new Material ();
    String            format      = null;
    TextureLoader      texturLader  = new TextureLoader (buffImage, format);
    ImageComponent2D  texturBild  = texturLader.getImage ();
    /*
     * ACHTUNG: -------- Der parameterlose Konstruktor von "Texture2D"
     * ist nicht zu empfehlen, da er stets eine Bildgroesse von 0 mal 0
     * Pixeln liefert, was zu einer "IllegalImageSizeException" fuehrt.
     * --> Siehe auch: "http://www.java.net/node/698104".
     */
    Texture2D          textur      = new Texture2D
                                    (
                                      Texture2D.BASE_LEVEL,
                                      Texture2D.RGBA,
                                      texturBild.getWidth (),
                                      texturBild.getHeight ()
                                    );
    
    // Die Textur wird eingestellt.
    textur.setImage   (0, texturBild);
    textur.setEnable (true         );
    
    // Das Aussehen der Figur ("appearance") wird eingestellt.
    material.setLightingEnable (true     );
    material.setShininess       (shininess);
    setMaterial                 (material );
    setTexture                 (textur   );
  }
}