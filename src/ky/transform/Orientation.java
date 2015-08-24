// 24.09.2013

package ky.transform;

import commies.angle.Angle;
import commies.angle.AngleUnit;
import safercode.CheckingUtils;

import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;


public class Orientation
{
  private Angle angleX;
  private Angle angleY;
  private Angle angleZ;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a set of Euler angles around the given axes.
   * </div>
   * 
   * @param angleX  The rotation around the <i>x</i>-axis.
   *                It must not be <code>null</code>.
   * @param angleY  The rotation around the <i>y</i>-axis.
   *                It must not be <code>null</code>.
   * @param angleZ  The rotation around the <i>z</i>-axis.
   *                It must not be <code>null</code>.
   */
  public Orientation (Angle angleX, Angle angleY, Angle angleZ)
  {
    if (angleX != null)
    {
      this.angleX = new Angle (angleX);
    }
    else
    {
      this.angleX = new Angle (0.0);
    }
    
    if (angleY != null)
    {
      this.angleY = new Angle (angleY);
    }
    else
    {
      this.angleY = new Angle (0.0);
    }
    
    if (angleZ != null)
    {
      this.angleZ = new Angle (angleZ);
    }
    else
    {
      this.angleZ = new Angle (0.0);
    }
  }
  
  public Orientation
  (
    AngleUnit angleUnit,
    double    angleSizeX,
    double    angleSizeY,
    double    angleSizeZ
  )
  {
    this.angleX = new Angle (angleUnit, angleSizeX);
    this.angleY = new Angle (angleUnit, angleSizeY);
    this.angleZ = new Angle (angleUnit, angleSizeZ);
  }
  
  public Orientation (Orientation andereOrientierung)
  {
    if (andereOrientierung != null)
    {
      Angle winkelX = new Angle (andereOrientierung.getAngleX ());
      Angle winkelY = new Angle (andereOrientierung.getAngleY ());
      Angle winkelZ = new Angle (andereOrientierung.getAngleZ ());
      
      this.angleX   = winkelX;
      this.angleY   = winkelY;
      this.angleZ   = winkelZ;
    }
    else
    {
      this.angleX = new Angle (0.0);
      this.angleY = new Angle (0.0);
      this.angleZ = new Angle (0.0);
    }
  }
  
  /**
   * <div class="introConstructor">
   *   Creates a set of Euler angles with all angles set to
   *   zero degrees.
   * </div>
   */
  public Orientation ()
  {
    this.angleX = new Angle (0.0);
    this.angleY = new Angle (0.0);
    this.angleZ = new Angle (0.0);
  }
  
  
  public Angle[] getAnglesAsArray ()
  {
    Angle[] angleArray = null;
    
    angleArray = new Angle[]
    {
      angleX, angleY, angleZ
    };
    
    return angleArray;
  }
  
  public Vector3d getAnglesAsVector3d (AngleUnit angleUnit)
  {
    Vector3d anglesAsVector = null;
    
    anglesAsVector = new Vector3d ();
    anglesAsVector.setX (angleX.getSizeIn (angleUnit));
    anglesAsVector.setY (angleY.getSizeIn (angleUnit));
    anglesAsVector.setZ (angleZ.getSizeIn (angleUnit));
    
    return anglesAsVector;
  }
  
  public double[] getAngleSizesInGivenUnitAsArray (AngleUnit angleUnit)
  {
    double[] angleSizes = new double[3];
    
    angleSizes[0] = angleX.getSizeIn (angleUnit);
    angleSizes[1] = angleY.getSizeIn (angleUnit);
    angleSizes[2] = angleZ.getSizeIn (angleUnit);
    
    return angleSizes;
  }
  
  public Angle getAngleX ()
  {
    return angleX;
  }
  
  public void setAngleX (Angle winkelX)
  {
    this.angleX = winkelX;
  }
  
  
  public Angle getAngleY ()
  {
    return angleY;
  }
  
  public void setAngleY (Angle winkelY)
  {
    this.angleY = winkelY;
  }
  
  
  public Angle getAngleZ ()
  {
    return angleZ;
  }
  
  public void setAngleZ (Angle winkelZ)
  {
    this.angleZ = winkelZ;
  }
  
  
  public void setAnglesFromArray (Angle[] angleArray)
  {
    CheckingUtils.checkForNull (angleArray, "Angle array is null.");
    
    int numberOfAngles = angleArray.length;
    
    if (numberOfAngles >= 1)
    {
      this.angleX = angleArray[0];
    }
    
    if (numberOfAngles >= 2)
    {
      this.angleY = angleArray[1];
    }
    
    if (numberOfAngles >= 3)
    {
      this.angleZ = angleArray[2];
    }
  }
  
  public void setAllAnglesTo (Angle angle)
  {
    angleX = angle;
    angleY = angle;
    angleZ = angle;
  }
  
  public void setFromOrientation (Orientation otherOrientierung)
  {
    if (otherOrientierung != null)
    {
      angleX = otherOrientierung.getAngleX ();
      angleY = otherOrientierung.getAngleY ();
      angleZ = otherOrientierung.getAngleZ ();
    }
  }
  
  
  public boolean areEqual (Orientation otherOrientation)
  {
    boolean areEqual = false;
    
    if (otherOrientation != null)
    {
      Angle otherAngleX = otherOrientation.getAngleX ();
      Angle otherAngleY = otherOrientation.getAngleY ();
      Angle otherAngleZ = otherOrientation.getAngleZ ();
      
      areEqual =
      (
        (angleX.isSameAngle (otherAngleX)) &&
        (angleY.isSameAngle (otherAngleY)) &&
        (angleZ.isSameAngle (otherAngleZ))
      );
    }
    // Other orientation is null? => Not equal.
    else
    {
      areEqual = false;
    }
    
    return areEqual;
  }
  
  
  public Angle[] getAngleDifferencesAsArray (Orientation otherOrientation)
  {
    checkOtherOrientation (otherOrientation);
    
    Angle[] angleDifferences = null;
    
    Angle otherAngleX = otherOrientation.getAngleX ();
    Angle otherAngleY = otherOrientation.getAngleY ();
    Angle otherAngleZ = otherOrientation.getAngleZ ();
    
    angleDifferences = new Angle[]
    {
      angleX.getDistanceAsAngle (otherAngleX),
      angleY.getDistanceAsAngle (otherAngleY),
      angleZ.getDistanceAsAngle (otherAngleZ)
    };
    
    return angleDifferences;
  }
  
  // Erzeugt neue Orientierung aus this - andereOrientierung.
  public Orientation getAngleDifferencesAsOrientation
  (
    Orientation otherOrientation
  )
  {
    checkOtherOrientation (otherOrientation);
    
    Orientation differenceOrientation = null;
    Angle[]     angleDifferencesArray = null;
    
    angleDifferencesArray = getAngleDifferencesAsArray
    (
      otherOrientation
    );
    differenceOrientation = new Orientation
    (
      angleDifferencesArray[0],
      angleDifferencesArray[1],
      angleDifferencesArray[2]
    );
    
    return differenceOrientation;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Overriding of methods from "Object".                       -- //
  //////////////////////////////////////////////////////////////////////
  
  @Override
  public String toString ()
  {
    String asString = null;
    
    asString = String.format
    (
      "Orientation(angleX=%s; angleY=%s; angleZ=%s)",
      angleX, angleY, angleZ
    );
    
    return asString;
  }
  
  private static void checkOtherOrientation (Orientation otherOrientation)
  {
    CheckingUtils.checkForNull (otherOrientation, "Other orientation is null.");
  }
}