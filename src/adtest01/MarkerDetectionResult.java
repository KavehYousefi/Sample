/* Saves result of searching index and confidence of marker with
 * highest (best) confidence.
 */

package adtest01;


public class MarkerDetectionResult
{
  public static final int NO_MARKER_INDEX = -1;
  
  private int    markerIndex;
  private double confidence;
  
  
  public MarkerDetectionResult ()
  {
    this.markerIndex = NO_MARKER_INDEX;
    this.confidence  = -1.0;
  }
  
  public int getIndexOfBestMarker ()
  {
    return markerIndex;
  }
  
  public void setIndexOfBestMarker (int markerIndex)
  {
    this.markerIndex = markerIndex;;
  }
  
  public double getConfidenceOfBestMarker ()
  {
    return confidence;
  }
  
  public void setConfidenceOfBestMarker (double confidence)
  {
    this.confidence = confidence;
  }
  
  public boolean hasResult ()
  {
    return (markerIndex != NO_MARKER_INDEX);
  }
}
