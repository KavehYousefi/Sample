// 24.12.2012

package commies;

import javax.media.j3d.Appearance;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.LineAttributes;
import javax.media.j3d.PolygonAttributes;

import javax.vecmath.Color3f;


// -> "http://www.computing.northampton.ac.uk/~gary/csy3019/CSY3019SectionD.html"
public class WireframeAppearance extends Appearance
{
  private static final Color3f VORGABE_FARBE        = new Color3f (0.0f, 1.0f, 0.0f);
  private static final float   VORGABE_DICKE        = 1.0f;
  private static final boolean VORGABE_ANTIALIASING = false;
  
  
  public WireframeAppearance
  (
    float   dicke,
    Color3f farbe,
    boolean aktiviereAntialiasing
  )
  {
    initGitternetzAppearance (dicke, farbe, aktiviereAntialiasing);
  }
  
  /**
   * <div class="einleitungKonstruktor">
   *   Ein Drahtgitter-Erscheinungsbild wird definiert.
   * </div>
   * 
   * @param dicke  Die Dicke der Linien in Pixeln.
   * @param farbe  Die Linienfarbe. Sie darf nicht <code>null</code>
   *               betragen.
   * 
   * @throws NullPointerException  Falls <code>farbe</code> den Wert
   *                               <code>null</code> besitzt.
   */
  public WireframeAppearance (float dicke, Color3f farbe)
  {
    initGitternetzAppearance (dicke, farbe, VORGABE_ANTIALIASING);
  }
  
  public WireframeAppearance (float dicke, boolean aktiviereAntialiasing)
  {
    initGitternetzAppearance (dicke, VORGABE_FARBE, aktiviereAntialiasing);
  }
  
  /**
   * <div class="einleitungKonstruktor">
   *   Ein Drahtgitter-Erscheinungsbild mit angegebener Linienbreite
   *   und gr&uuml;ner Farbe wird definiert.
   * </div>
   * 
   * @param dicke  Die Dicke der Linien in Pixeln.
   */
  public WireframeAppearance (float dicke)
  {
    initGitternetzAppearance (dicke, VORGABE_FARBE, VORGABE_ANTIALIASING);
  }
  
  /**
   * <div class="einleitungKonstruktor">
   *   Ein Drahtgitter-Erscheinungsbild mit einer Standardlinienbreite
   *   von einem Pixel und der angegebenen Farbe wird definiert.
   * </div>
   * 
   * @param farbe  Die Linienfarbe. Sie darf nicht <code>null</code>
   *               betragen.
   * 
   * @throws NullPointerException  Falls <code>farbe</code> den Wert
   *                               <code>null</code> besitzt.
   */
  public WireframeAppearance (Color3f farbe)
  {
    initGitternetzAppearance (VORGABE_DICKE, farbe, VORGABE_ANTIALIASING);
  }
  
  /**
   * <div class="einleitungKonstruktor">
   *   Ein Drahtgitter-Erscheinungsbild mit Vorgabeeigenschaften wird
   *   definiert.
   * </div>
   */
  public WireframeAppearance ()
  {
    initGitternetzAppearance (VORGABE_DICKE, VORGABE_FARBE, VORGABE_ANTIALIASING);
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Umsetzung der Hilfsmethoden.                               -- //
  //////////////////////////////////////////////////////////////////////
  
  private void initGitternetzAppearance
  (
    float   dicke,
    Color3f farbe,
    boolean aktiviereAntialiasing
  )
  {
    PolygonAttributes  polyAttribs  = new PolygonAttributes  ();
    ColoringAttributes colorAttribs = new ColoringAttributes ();
    LineAttributes     lineAttribs  = new LineAttributes     ();
    
    polyAttribs.setCullFace    (PolygonAttributes.CULL_NONE);
    polyAttribs.setPolygonMode (PolygonAttributes.POLYGON_LINE);
    colorAttribs.setColor      (farbe);
    lineAttribs.setLineWidth   (dicke);
    lineAttribs.setLinePattern (LineAttributes.PATTERN_SOLID);
    lineAttribs.setLineAntialiasingEnable (aktiviereAntialiasing);
    
//    lineAttribs.setLinePattern (LineAttributes.PATTERN_USER_DEFINED);
//    LinePatternGenerator linePattGen = new LinePatternGenerator ();
//    linePattGen.setzePatternMaskNachZeiketGrafik ("AAAAAAAA", 'A');
//    linePattGen.invertiere ();
//    linePattGen.invertiere (0, 8);
//    lineAttribs.setPatternMask (linePattGen.nennePatternMask ());
//    lineAttribs.setPatternScaleFactor (5);
    
    setPolygonAttributes  (polyAttribs);
    setColoringAttributes (colorAttribs);
    setLineAttributes     (lineAttribs);
  }
}