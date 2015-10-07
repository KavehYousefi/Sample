package jav3d.offloader.properties;

import safercode.CheckingUtils;

import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * <div class="introClass">
 *   The <code>OFFLoaderProperties</code> class holds the properties
 *   for reading, processing and interpreting <i>OFF</i> files.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFLoaderProperties.java
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
 *       22.06.2015
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
 *     <td>22.06.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class OFFLoaderProperties
{
  private GeometryTypePolicy                     geometryTypePolicy;
  private GeometryGroupingPolicy                 geometryGroupingPolicy;
  private NormalAutoGenerationPolicy             normalGenerationPolicy;
  private TextureCoordinatesAutoGenerationPolicy texCoordGenerationPolicy;
  private ColorMapHandling                       colorMapHandling;
  private Path                                   colorMapFilePath;
  private DataOrdering                           dataOrder;
  private NormalizationPolicy                    normalizationPolicy;
  
  
  /**
   * <div class="introConstructor">
   *   An instance of the <code>OFFLoaderProperties</code> with default
   *   values is created and returned.
   * </div>
   * 
   * <div>
   *   Following properties are defined by default:
   *   <table>
   *     <tr>
   *       <th>Property</th>
   *       <th>Default value</th>
   *     </tr>
   *     <tr>
   *       <td>geometry type policy</td>
   *       <td><code>GeometryTypePolicy.SELECT_BEST_PRIMITIVE</code></td>
   *     </tr>
   *     <tr>
   *       <td>geometry grouping policy</td>
   *       <td><code>GeometryGroupingPolicy.PER_FACE_INDEX_GROUP</code></td>
   *     </tr>
   *     <tr>
   *       <td>normal generation policy</td>
   *       <td><code>NormalAutoGenerationPolicy.ALWAYS_GENERATE_NORMALS</code></td>
   *     </tr>
   *     <tr>
   *       <td>texture coordinates generation policy</td>
   *       <td><code>TextureCoordinatesAutoGenerationPolicy.ALWAYS</code></td>
   *     </tr>
   *     <tr>
   *       <td>color map handling</td>
   *       <td><code>ColorMapHandling.IGNORE_MISSING_COLORMAP_FILE</code></td>
   *     </tr>
   *     <tr>
   *       <td>color map file path</td>
   *       <td><code>Paths.get ("cmap.fmap")</code></td>
   *     </tr>
   *     <tr>
   *       <td>data ordering</td>
   *       <td><code>DataOrdering.DEFAULT</code></td>
   *     </tr>
   *     <tr>
   *       <td>normalization policy</td>
   *       <td><code>NormalizationPolicy.NO_NORMALIZATION</code></td>
   *     </tr>
   *   </table>
   * </div>
   */
  public OFFLoaderProperties ()
  {
    this.geometryTypePolicy       = GeometryTypePolicy.SELECT_BEST_PRIMITIVE;
    this.geometryGroupingPolicy   = GeometryGroupingPolicy.PER_FACE_INDEX_GROUP;
    this.normalGenerationPolicy   = NormalAutoGenerationPolicy.ALWAYS_GENERATE_NORMALS;
    this.texCoordGenerationPolicy = TextureCoordinatesAutoGenerationPolicy.ALWAYS;
    this.colorMapHandling         = ColorMapHandling.IGNORE_MISSING_COLORMAP_FILE;
    this.colorMapFilePath         = Paths.get ("cmap.fmap");
    this.dataOrder                = DataOrdering.DEFAULT;
    this.normalizationPolicy      = NormalizationPolicy.NO_NORMALIZATION;
  }
  
  
  public GeometryTypePolicy getGeometryTypePolicy ()
  {
    return geometryTypePolicy;
  }
  
  public void setGeometryTypePolicy (GeometryTypePolicy geometryTypePolicy)
  {
    CheckingUtils.checkForNull
    (
      geometryTypePolicy,
      "Geometry type policy is null."
    );
    this.geometryTypePolicy = geometryTypePolicy;
  }
  
  public GeometryGroupingPolicy getGeometryGroupingPolicy ()
  {
    return geometryGroupingPolicy;
  }
  
  public void setGeometryGroupingPolicy (GeometryGroupingPolicy geometryGrouping)
  {
    CheckingUtils.checkForNull
    (
      geometryGrouping,
      "Geometry grouping policy is null."
    );
    this.geometryGroupingPolicy = geometryGrouping;
  }
  
  public NormalAutoGenerationPolicy getNormalGenerationPolicy ()
  {
    return normalGenerationPolicy;
  }
  
  public void setNormalAutoGenerationPolicy (NormalAutoGenerationPolicy normalGeneration)
  {
    this.normalGenerationPolicy = normalGeneration;
  }
  
  public TextureCoordinatesAutoGenerationPolicy getTextureCoordinatesAutoGenerationPolicy ()
  {
    return texCoordGenerationPolicy;
  }
  
  public void setTextureCoordinatesAutoGenerationPolicy (TextureCoordinatesAutoGenerationPolicy textureCoordGeneration)
  {
    this.texCoordGenerationPolicy = textureCoordGeneration;
  }
  
  public ColorMapHandling getColorMapHandling ()
  {
    return colorMapHandling;
  }
  
  public void setColorMapHandling (ColorMapHandling colorMapHandling)
  {
    this.colorMapHandling = colorMapHandling;
  }
  
  public Path getColorMapFilePath ()
  {
    return colorMapFilePath;
  }
  
  // (?) Better use URL or File?
  public void setColorMapFilePath (Path colorMapFilePath)
  {
    this.colorMapFilePath = colorMapFilePath;
  }
  
  public DataOrdering getDataOrdering ()
  {
    return dataOrder;
  }
  
  public void setDataOrdering (DataOrdering dataOrder)
  {
    this.dataOrder = dataOrder;
  }
  
  public NormalizationPolicy getNormalizationPolicy ()
  {
    return normalizationPolicy;
  }
  
  public void setNormalizationPolicy (NormalizationPolicy normalizationPolicy)
  {
    this.normalizationPolicy = normalizationPolicy;
  }
}
