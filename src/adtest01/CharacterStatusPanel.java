package adtest01;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import ky.gamelogic.Statistics;


@SuppressWarnings ("serial")
public class CharacterStatusPanel
extends      JPanel
{
  private static final String DECEASED_CHARACTER = "{\u271D}";
  private static final int    LEFT_PADDING       = 5;
  private static final int    TOP_PADDING        = 5;
  
  private DetectMarkers detectMarkers;
  private int           panelWidth;
  private int           panelHeight;
  
  
  public CharacterStatusPanel
  (
    DetectMarkers detectMarkers,
    int           panelWidth,
    int           panelHeight
  )
  {
    super ();
    
    this.detectMarkers = detectMarkers;
    this.panelWidth    = panelWidth;
    this.panelHeight   = panelHeight;
    
    update ();
  }
  
  
  public JPanel getPanel ()
  {
    return this;
  }
  
  public void update ()
  {
    repaint ();
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "JComponent".                   -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public Dimension getPreferredSize ()
  {
    return new Dimension (panelWidth, panelHeight);
  }
  
  @Override
  public void paint (Graphics graphics)
  {
    Graphics2D graphics2D = (Graphics2D) graphics;
    int        leftX      = 0;
    int        topY       = 0;
    
    clearImage (graphics2D);
    
    leftX = LEFT_PADDING;
    topY  = TOP_PADDING;
    
    for (MarkerModel markerModel : detectMarkers.getMarkerModelsForPlayer (Player.PLAYER_1))
    {
      if (markerModel.isCharacter ())
      {
        drawCharacterInfo (markerModel, graphics2D, leftX, topY);
        
        topY = topY + markerModel.getIcon ().getHeight () + 5;
      }
    }
    
    leftX = (this.getWidth () / 2) + 5;
    topY  = TOP_PADDING;
    
    for (MarkerModel markerModel : detectMarkers.getMarkerModelsForPlayer (Player.PLAYER_2))
    {
      if (markerModel.isCharacter ())
      {
        drawCharacterInfo (markerModel, graphics2D, leftX, topY);
        
        topY = topY + markerModel.getIcon ().getHeight () + 5;
      }
    }
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void clearImage (Graphics2D graphics2D)
  {
    Paint oldColor   = null;
    Paint clearColor = null;
    
    oldColor   = graphics2D.getPaint ();
    clearColor = Color.DARK_GRAY;
    
    graphics2D.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
                                 RenderingHints.VALUE_ANTIALIAS_ON);
    graphics2D.setPaint (clearColor);
    graphics2D.fillRect (0, 0, this.getWidth (), this.getHeight ());
    graphics2D.setPaint (oldColor);
  }
  
  private void drawCharacterInfo
  (
    MarkerModel markerModel,
    Graphics2D  graphics2D,
    int         leftX,
    int         topY
  )
  {
    BufferedImage icon       = null;
    String        text       = null;
    Statistics    statistics = null;
    Paint         textPaint  = null;
    
    icon       = markerModel.getIcon       ();
    statistics = markerModel.getStatistics ();
    text       = String.format
    (
      "%s %s Pow[%s/%s] | Att[%s/%s] | Def[%s/%s] Status%s %s",
      markerModel.getCharacterInfo ().isAlive () ? "" : DECEASED_CHARACTER,
      markerModel.getModelName     (),
      statistics.getCurrentPower   (), statistics.getMaximumPower   (),
      statistics.getCurrentAttack  (), statistics.getMaximumAttack  (),
      statistics.getCurrentDefense (), statistics.getMaximumDefense (),
      markerModel.getProperties    (),
      markerModel.getCharacterInfo ().isDefending () ? "(defensive)" : ""
    );
    
    if (markerModel.getCharacterInfo ().isAlive ())
    {
      if (markerModel.canAct ())
      {
        textPaint = Color.GREEN;
      }
      else
      {
        textPaint = Color.WHITE;
      }
    }
    else
    {
      textPaint = Color.RED;
    }
    
    graphics2D.drawImage  (icon, leftX, topY, null);
    graphics2D.setPaint   (textPaint);
    graphics2D.drawString (text, leftX + icon.getWidth () + 2, topY + 12);
  }
}
