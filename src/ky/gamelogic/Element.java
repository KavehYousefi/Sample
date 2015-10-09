package ky.gamelogic;


public enum Element
{
  NONE  ("none"),
  FIRE  ("fire"),
  ICE   ("ice"),
  METAL ("metal");
  
  
  private String name;
  
  
  private Element (String name)
  {
    this.name = name;
  }
  
  
  public String getName ()
  {
    return name;
  }
}
