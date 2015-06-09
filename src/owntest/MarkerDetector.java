package owntest;

import java.util.ArrayList;
import java.util.List;

import jp.nyatla.nyartoolkit.core.NyARCode;
import jp.nyatla.nyartoolkit.core.NyARException;
import jp.nyatla.nyartoolkit.detector.NyARDetectMarker;
import jp.nyatla.nyartoolkit.java3d.utils.J3dNyARRaster_RGB;


public class MarkerDetector
{
  private NyARDetectMarker  nyARDetectMarker;
  private J3dNyARRaster_RGB rasterRGB;
  private List<MarkerModel> markerModels;
  
  
  public MarkerDetector ()
  {
    this.nyARDetectMarker = null;
    this.rasterRGB        = null;
    this.markerModels     = new ArrayList<MarkerModel> ();
  }
  
  
  public void addMarkerModel (MarkerModel markerModel)
  {
    if (markerModel != null)
    {
      this.markerModels.add (markerModel);
    }
    else
    {
      throw new NullPointerException ("MarkerModel is null.");
    }
  }
  
  
  public void detectMarkers ()
  {
    NyARCode[] nyARCodes   = null;
    double[]   widths      = null;
    int        markerCount = 0;
    
    markerCount = markerModels.size ();
    nyARCodes   = new NyARCode[markerCount];
    widths      = new double  [markerCount];
    
    for (int markerIndex = 0; markerIndex < markerCount; markerIndex++)
    {
      MarkerModel markerModel = null;
      
      markerModel            = markerModels.get (markerIndex);
      nyARCodes[markerIndex] = markerModel.getNyARCode ();
      widths   [markerIndex] = markerModel.getMarkerSize ().width;
    }
    
    try
    {
      nyARDetectMarker = new NyARDetectMarker
      (
        null,
        nyARCodes,
        widths,
        rasterRGB.getBufferType ()
      );
    }
    catch (NyARException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}