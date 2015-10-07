package ky.gamelogic;


public enum Element
{
  NONE  ("none"),
  FIRE  ("fice"),
  ICE   ("ice"),
  WATER ("water");
  
  
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
