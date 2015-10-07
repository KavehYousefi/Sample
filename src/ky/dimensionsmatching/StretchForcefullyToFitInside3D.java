package ky.dimensionsmatching;

import ky.Dimensions;
import ky.transform2.Scaling;


/**
 * <div class="introClass">
 *   The <code>StretchForcefullyToFitInside3D</code> matching policy
 *   calculates a <strong>potentially non-uniform</strong> scaling to
 *   exactly match width, height and depth of the source dimensions to
 *   the destination dimensions.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       StretchForcefullyToFitInside3D.java
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
 *       04.08.2015
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
 *     <td>04.08.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class StretchForcefullyToFitInside3D
implements   DimensionsMatchingPolicy
{
  /**
   * <div class="introConstructor">
   *   Creates a <code>StretchForcefullyToFitInside3D</code> matching
   *   policy.
   * </div>
   */
  public StretchForcefullyToFitInside3D ()
  {
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of methods from "DimensionsMatchingPolicy". -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public Scaling getScalingToMatchDimensions
  (
    Dimensions sourceDimensions,
    Dimensions destinationDimensions
  )
  {
//    checkSourceAndDestinationDimensions
//    (
//      sourceDimensions,
//      destinationDimensions
//    );
    
    Scaling scaling           = null;
    double  widthScaleFactor  = 0.0;
    double  heightScaleFactor = 0.0;
    double  depthScaleFactor  = 0.0;
    double  sourceWidth       = 0.0;
    double  sourceHeight      = 0.0;
    double  sourceDepth       = 0.0;
    double  destinationWidth  = 0.0;
    double  destinationHeight = 0.0;
    double  destinationDepth  = 0.0;
    
    scaling           = new Scaling                     ();
    sourceWidth       = sourceDimensions.getWidth       ();
    sourceHeight      = sourceDimensions.getHeight      ();
    sourceDepth       = sourceDimensions.getDepth       ();
    destinationWidth  = destinationDimensions.getWidth  ();
    destinationHeight = destinationDimensions.getHeight ();
    destinationDepth  = destinationDimensions.getDepth  ();
    widthScaleFactor  = (destinationWidth  / sourceWidth);
    heightScaleFactor = (destinationHeight / sourceHeight);
    depthScaleFactor  = (destinationDepth  / sourceDepth);
    
    scaling.setScalingX (widthScaleFactor);
    scaling.setScalingY (heightScaleFactor);
    scaling.setScalingZ (depthScaleFactor);
    
    return scaling;
  }
}
