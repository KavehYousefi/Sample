package owntest;

import java.awt.Dimension;

import javax.media.j3d.BranchGroup;

import jp.nyatla.nyartoolkit.core.NyARCode;
import jp.nyatla.nyartoolkit.core.NyARException;


public class MarkerModel
{
  private String          markerName;
  private Dimension       size;
  private CharacterObject characterObject;
  
  
  public MarkerModel (String markerName)
  {
    this.markerName      = markerName;
    this.size            = new Dimension (16, 16);
    this.characterObject = null;
  }
  
  
  public String getMarkerName ()
  {
    return markerName;
  }
  
  public Dimension getMarkerSize ()
  {
    return size;
  }
  
  public CharacterObject getCharacterObject ()
  {
    return characterObject;
  }
  
  public void setCharacterObject (CharacterObject object)
  {
    this.characterObject = object;
  }
  
  public BranchGroup getRootNode ()
  {
    BranchGroup rootNode = null;
    
    rootNode = new BranchGroup ();
    
    if (characterObject != null)
    {
      rootNode.addChild (characterObject.getBranchGroup ());
    }
    
    return rootNode;
  }
  
  
  public NyARCode getNyARCode ()
  {
    NyARCode nyARCode = null;
    
    try
    {
      nyARCode = new NyARCode (size.width, size.height);
    }
    catch (NyARException e)
    {
      e.printStackTrace();
    }
    
    return nyARCode;
  }
}