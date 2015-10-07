// 22.06.2015

package jav3d.offloader.properties;


public enum NormalAutoGenerationPolicy
{
  /**
   * <div class="introEnumInstance">
   *   Generate normals, if no normal data exists, even if the
   *   file format does not include normals.
   */
  ALWAYS_GENERATE_NORMALS,
  /**
   * <div class="introEnumInstance">
   *   Generate normals, if no normal data exists and the file
   *   format specifies normals.
   * </div>
   */
  ONLY_IF_SPECIFIED_IN_HEADER,
  /**
   * <div class="introEnumInstance">
   *   Do not generate normals for faces without normal data.
   * </div>
   */
  NEVER_GENERATE_NORMALS;
  
  
  /**
   * <div class="introConstructor">
   *   Defines the behavior of the normal assignment.
   * </div>
   */
  private NormalAutoGenerationPolicy ()
  {
  }
}
