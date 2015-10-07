// 22.06.2015

package jav3d.offloader.processor;


public class ParserUtils
{
  private ParserUtils ()
  {
  }
  
  
  public static String getLinePartBeforeCharacter (String line, char character)
  {
    String linePartBeforeComment = null;
    int    indexOfCommentChar    = -1;
    
    indexOfCommentChar = line.indexOf (character);
    
    if (indexOfCommentChar != -1)
    {
      linePartBeforeComment = line.substring (0, indexOfCommentChar);
    }
    else
    {
      linePartBeforeComment = line;
    }
    
    return linePartBeforeComment;
  }
  
  public static String getLinePartBeforeComment (String line)
  {
    String linePartBeforeComment = null;
    
    linePartBeforeComment = getLinePartBeforeCharacter (line, '#');
    
    return linePartBeforeComment;
  }
}
