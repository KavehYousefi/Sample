// 04.08.2015

package ky.dimensionsmatching;

import ky.Dimensions;
import ky.transform2.Scaling;


// Alternative name: "UniformMatchToEnclose".
/**
 * <div class="introClass">
 *   The <code>ScaleUniformlyToFitAround</code> matching policy
 *   calculates the <strong>uniform</strong> scaling necessary to fit
 *   the source dimensions around the destination dimensions.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       ScaleUniformlyToFitAround.java
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
public class ScaleUniformlyToFitAround
implements   DimensionsMatchingPolicy
{
  /**
   * <div class="introConstructor">
   *   Creates a <code>ScaleUniformlyToFitAround</code> matching policy.
   * </div>
   */
  public ScaleUniformlyToFitAround ()
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
//    
    Scaling  scaling            = null;
    double   widthScaleFactor   = 0.0;
    double   heightScaleFactor  = 0.0;
    double   depthScaleFactor   = 0.0;
    double   sourceWidth        = 0.0;
    double   sourceHeight       = 0.0;
    double   sourceDepth        = 0.0;
    double   destinationWidth   = 0.0;
    double   destinationHeight  = 0.0;
    double   destinationDepth   = 0.0;
    double   maximumScaleFactor = 0.0;
    
    scaling            = new Scaling                     ();
    sourceWidth        = sourceDimensions.getWidth       ();
    sourceHeight       = sourceDimensions.getHeight      ();
    sourceDepth        = sourceDimensions.getDepth       ();
    destinationWidth   = destinationDimensions.getWidth  ();
    destinationHeight  = destinationDimensions.getHeight ();
    destinationDepth   = destinationDimensions.getDepth  ();
    widthScaleFactor   = (destinationWidth  / sourceWidth);
    heightScaleFactor  = (destinationHeight / sourceHeight);
    depthScaleFactor   = (destinationDepth  / sourceDepth);
    maximumScaleFactor = Math.max (widthScaleFactor, Math.max (heightScaleFactor, depthScaleFactor));
    
    scaling.setAllTo (maximumScaleFactor);
    
    return scaling;
  }
}
