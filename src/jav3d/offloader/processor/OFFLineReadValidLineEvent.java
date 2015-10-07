package jav3d.offloader.processor;

import java.util.List;


public class OFFLineReadValidLineEvent
{
  private OFFLineReader reader;
  private String        line;
  private List<String>  tokens;
  private LineType      lineType;
  
  
  public OFFLineReadValidLineEvent
  (
    OFFLineReader reader,
    String        line,
    List<String>  tokens,
    LineType      lineType
  )
  {
    this.reader   = reader;
    this.line     = line;
    this.tokens   = tokens;
    this.lineType = lineType;
  }
  
  
  public OFFLineReader getOFFLineReader ()
  {
    return reader;
  }
  
  public String getLine ()
  {
    return line;
  }
  
  public List<String> getTokens ()
  {
    return tokens;
  }
  
  public LineType getLineType ()
  {
    return lineType;
  }
}
