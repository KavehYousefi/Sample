// 03.05.2015

package jav3d.lathep001.segment;

import ky.Position;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.List;


public class LathePath2DSegment
implements   LathePathSegment
{
  public static enum InitialMoveHandling
  {
    // Use the Path2D's first "moveTo(...)" command.
    TAKE_OVER,
    /* Create a new Path2D with the first "moveTo(...)" to the last
     * lathe path segment point.
     */
    SET_TO_LAST_SEGMENT_POINT;
    
    private InitialMoveHandling ()
    {
    }
  }
  
  
  private Path2D              path2D;
  private AffineTransform     transform;
  private double              flatness;
  /* Set "path2D"'s first "moveTo()" to last point in "previousPoints".
   * Should best be set to
   * InitialMoveHandling.SET_TO_LAST_SEGMENT_POINT, otherwise we get an
   * segments starting from the path2D's start point.
   */
  private InitialMoveHandling initialMoveHandling;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a lathe path segment based on a <code>Path2D</code>.
   * </div>
   * 
   * @param path2D               The path to convert into a lathe
   *                             path segment.
   * @param transform            An optional transformation for the
   *                             path.
   * @param flatness             The path's flatness.
   * @param initialMoveHandling  Determines, whether the
   *                             <code>path2D</code>'s first move-to
   *                             point should be used or replaced by a
   *                             move to the last lathe path segment
   *                             point. It must not be
   *                             <code>null</code>.
   * 
   * @throws NullPointerException  If <code>path2D</code> or
   *                               <code>initialMoveHandling</code>
   *                               equals <code>null</code>.
   */
  public LathePath2DSegment
  (
    Path2D              path2D,
    AffineTransform     transform,
    double              flatness,
    InitialMoveHandling initialMoveHandling
  )
  {
    this.path2D              = path2D;
    this.flatness            = flatness;
    this.transform           = transform;
    this.initialMoveHandling = initialMoveHandling;
  }
  
  
  @Override
  public List<Position> getPointsOnSegment (List<Position> previousPoints)
  {
    List<Position> points       = new ArrayList<Position> ();
    PathIterator   pathIterator = null;
    double[]       pathCoords   = new double[6];
    
    if (initialMoveHandling.equals (InitialMoveHandling.SET_TO_LAST_SEGMENT_POINT))
    {
      Path2D   copyOfPath2D = null;
      int      lastIndex    = (previousPoints.size () - 1);
      Position lastPoint    = previousPoints.get (lastIndex);
      
      // ???
      /*
      if (transform != null)
      {
        lastPoint = new Position (transform.transform (lastPoint.getAsPoint2D (), null));
      }
      */
      
      copyOfPath2D = copyPath2DWithNewStartPoint  (lastPoint, path2D);
      pathIterator = copyOfPath2D.getPathIterator (transform, flatness);
    }
    else
    {
      pathIterator = path2D.getPathIterator (transform, flatness);
    }
    
    while (! pathIterator.isDone ())
    {
      int segmentType = 0;
      
      segmentType = pathIterator.currentSegment (pathCoords);
      
      switch (segmentType)
      {
        case PathIterator.SEG_MOVETO :
        {
//          points.add (createPositionFromCoords (pathCoords, 0, 1));
          break;
        }
        case PathIterator.SEG_LINETO :
        {
          points.add (createPositionFromCoords (pathCoords, 0, 1));
          break;
        }
        case PathIterator.SEG_CLOSE :
        {
          break;
        }
        default :
        {
          break;
        }
      }
      
      pathIterator.next ();
    }
    
    return points;
  }
  
  @Override
  public List<Position> processPoints (List<Position> previousPoints)
  {
    List<Position> newPoints = getPointsOnSegment (previousPoints);
    
    previousPoints.addAll (newPoints);
    
    return previousPoints;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  // Same Path2D, but first moveTo() is moveTo(firstPoint.x, firstPoint.y).
  private static Path2D copyPath2DWithNewStartPoint
  (
    Position firstPoint,
    Path2D   originalPath2D
  )
  {
    Path2D       copyOfPath2D   = new Path2D.Double ();
    boolean      isFirstSegment = true;
    PathIterator pathIterator   = originalPath2D.getPathIterator (null);
    double[]     pathCoords     = new double[6];
    
    while (! pathIterator.isDone ())
    {
      if (isFirstSegment)
      {
        copyOfPath2D.moveTo (firstPoint.getX (), firstPoint.getY ());
        isFirstSegment = false;
      }
      else
      {
        int segmentType = pathIterator.currentSegment (pathCoords);
        
        switch (segmentType)
        {
          case PathIterator.SEG_MOVETO :
          {
            copyOfPath2D.moveTo (pathCoords[0], pathCoords[1]);
            break;
          }
          case PathIterator.SEG_LINETO :
          {
            copyOfPath2D.lineTo (pathCoords[0], pathCoords[1]);
            break;
          }
          case PathIterator.SEG_QUADTO :
          {
            copyOfPath2D.quadTo (pathCoords[0], pathCoords[1],
                                 pathCoords[2], pathCoords[3]);
            break;
          }
          case PathIterator.SEG_CUBICTO :
          {
            copyOfPath2D.curveTo (pathCoords[0], pathCoords[1],
                                  pathCoords[2], pathCoords[3],
                                  pathCoords[4], pathCoords[5]);
            break;
          }
          case PathIterator.SEG_CLOSE :
          {
            copyOfPath2D.closePath ();
          }
        }
      }
      
      pathIterator.next ();
    }
    
    return copyOfPath2D;
  }
  
  private static Position createPositionFromCoords
  (
    double[] coords,
    int      xIndex,
    int      yIndex
  )
  {
    Position position = new Position ();
    
    position.setX (coords[xIndex]);
    position.setY (coords[yIndex]);
    
    return position;
  }
}