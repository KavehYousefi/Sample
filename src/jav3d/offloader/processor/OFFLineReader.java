package jav3d.offloader.processor;

import safercode.CheckingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/* <!> CURRENT PROBLEM:
 *       IF FIRST LINE IS NO VALID FORMAT NAME ("OFF", "COFF", etc.),
 *       NOTHING HAPPENS.
 * 
 */
/**
 * <div class="introClass">
 *   An <code>OFFLineReader</code> consumes a line from an OFF file,
 *   tries to interpret its type and notifies attached listeners about
 *   the result of this activity.
 * </div>
 * 
 * <div>
 *   The implementation relied on several sources:
 *   <ul>
 *     <li>
 *       <a href="http://www.holmes3d.net/graphics/offfiles/offdesc.txt">http://www.holmes3d.net/graphics/offfiles/offdesc.txt</a>
 *       <br />
 *       Topic: Specification of the OFF file format.
 *       Accessed: 19.06.2015
 *     </li>
 *     <li>
 *       <a href="https://en.wikipedia.org/wiki/OFF_%28file_format%29">https://en.wikipedia.org/wiki/OFF_%28file_format%29</a>
 *       <br />
 *       Topic: Description and specification of the OFF file format.
 *       Accessed: 19.06.2015
 *     </li>
 *     <li>
 *       <a href="http://segeval.cs.princeton.edu/public/off_format.html">http://segeval.cs.princeton.edu/public/off_format.html</a>
 *       <br />
 *       Topic: Specification of the OFF file format.
 *       Accessed: 19.06.2015
 *     </li>
 *     <li>
 *       <a href="http://people.sc.fsu.edu/~jburkardt/data/off/off.html">http://people.sc.fsu.edu/~jburkardt/data/off/off.html</a>
 *       <br />
 *       Topic: Specification and examples of the OFF file format.
 *       Accessed: 19.06.2015
 *     </li>
 *     <li>
 *       <a href="http://shape.cs.princeton.edu/benchmark/">http://shape.cs.princeton.edu/benchmark/</a>
 *       <br />
 *       Topic: Specification of the OFF file format and
 *              extensive examples for OFF files.
 *       Accessed: 19.06.2015
 *     </li>
 *     <li>
 *       <a href="http://www.holmes3d.net/graphics/offfiles/">http://www.holmes3d.net/graphics/offfiles/</a>
 *       <br />
 *       Topic: Examples for OFF files.
 *       Accessed: 19.06.2015
 *     </li>
 *   </ul>
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       OFFLineReader.java
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
 *       19.06.2015
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
 *     <td>19.06.2015</td>
 *     <td>1.0</td>
 *     <td>The class has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public class OFFLineReader
{
  // What do we search for next (hope to find next)?
  private static enum SearchState
  {
    FORMAT_LINE,   // We expect to find the header ("[...]OFF" token).
    SIZE_LINE,     // We expect to find the vertex, face and edge count.
    VERTEX_LINE,   // We expect to find a line with vertex coordinates.
    FACE_LINE,     // We expect to find a line with face indices.
    COMPLETE;      // We have processed all mandatory information.
    
    private SearchState ()
    {
    }
  }
  
  
  private static final String COMMENT_SYMBOL = "#";
  
  private List<OFFLineReaderListener> listeners;
  private SearchState                 searchState;
  private int                         vertexCount;
  private int                         vertexIndex;
  private int                         faceCount;
  private int                         faceIndex;
  @SuppressWarnings                   ("unused")
  private int                         edgeCount;
  
  
  public OFFLineReader ()
  {
    this.listeners   = new ArrayList<OFFLineReaderListener> ();
    this.searchState = SearchState.FORMAT_LINE;
    this.vertexCount = 0;
    this.vertexIndex = 0;
    this.faceCount   = 0;
    this.faceIndex   = 0;
    this.edgeCount   = 0;
    
    reset ();
  }
  
  
  public void readLine (String line)
  {
    boolean                   isFormatLine  = false;
    boolean                   isCommentLine = false;
    boolean                   isEmptyLine   = false;
    OFFLineReadValidLineEvent event         = null;
    
    isEmptyLine   = isEmptyLine   (line);
    isCommentLine = isCommentLine (line);
    isFormatLine  = isFormatLine  (line);
    
    if (line == null)
    {
      event = createEvent               (line, LineType.END_OF_FILE);
      endOfFileLineEncounteredReadEvent (event);
      searchState = SearchState.COMPLETE;
    }
    else if (isEmptyLine)
    {
      event = createEvent           (line, LineType.EMPTY);
      emptyLineEncounteredReadEvent (event);
    }
    else if (isCommentLine)
    {
      event = createEvent             (line, LineType.COMMENT);
      commentLineEncounteredReadEvent (event);
    }
    else
    {
      switch (searchState)
      {
        case FORMAT_LINE :
        {
          if (isFormatLine)
          {
            event        = createEvent     (line, LineType.FORMAT_TYPE);
            formatLineEncounteredReadEvent (event);
            searchState  = SearchState.SIZE_LINE;
          }
          break;
        }
        case SIZE_LINE :
        {
          storeVertexFaceAndEdgeCountFromSizeLine (splitLineIntoTokens (line));
          event       = createEvent    (line, LineType.SIZES);
          sizeLineEncounteredReadEvent (event);
          searchState = SearchState.VERTEX_LINE;
          break;
        }
        case VERTEX_LINE :
        {
          event       = createEvent      (line, LineType.VERTEX);
          vertexLineEncounteredReadEvent (event);
          vertexIndex = vertexIndex + 1;
          
          if (needsMoreVertices ())
          {
            searchState = SearchState.VERTEX_LINE;
          }
          else
          {
            searchState = SearchState.FACE_LINE;
          }
          break;
        }
        case FACE_LINE :
        {
          event     = createEvent      (line, LineType.FACE);
          faceLineEncounteredReadEvent (event);
          faceIndex = faceIndex + 1;
          
          if (needsMoreFaces ())
          {
            searchState = SearchState.FACE_LINE;
          }
          else
          {
            searchState = SearchState.COMPLETE;
          }
          break;
        }
        case COMPLETE :
        {
          break;
        }
        default :
        {
          throw new IllegalArgumentException ("Invalid search state.");
        }
      }
    }
  }
  
  public void reset ()
  {
    this.listeners   = new ArrayList<OFFLineReaderListener> ();
    this.searchState = SearchState.FORMAT_LINE;
    this.vertexCount = 0;
    this.vertexIndex = 0;
    this.faceCount   = 0;
    this.faceIndex   = 0;
    this.edgeCount   = 0;
  }
  
  public void addOFFLineReaderListener (OFFLineReaderListener listener)
  {
    CheckingUtils.checkForNull
    (
      listener,
      "OFFLineReaderListener is null."
    );
    listeners.add (listener);
  }
  
  
  private boolean needsMoreVertices ()
  {
    return (vertexIndex < vertexCount);
  }
  
  private boolean needsMoreFaces ()
  {
    return (faceIndex < faceCount);
  }
  
  private OFFLineReadValidLineEvent createEvent (String line, LineType lineType)
  {
    return new OFFLineReadValidLineEvent (this, line, splitLineIntoTokens (line), lineType);
  }
  
  protected void commentLineEncounteredReadEvent (OFFLineReadValidLineEvent event)
  {
    for (OFFLineReaderListener listener : listeners)
    {
      if (listener != null)
      {
        listener.commentLineEncountered (event);
      }
    }
  }
  
  protected void emptyLineEncounteredReadEvent (OFFLineReadValidLineEvent event)
  {
    for (OFFLineReaderListener listener : listeners)
    {
      if (listener != null)
      {
        listener.emptyLineEncountered (event);
      }
    }
  }
  
  protected void faceLineEncounteredReadEvent (OFFLineReadValidLineEvent event)
  {
    for (OFFLineReaderListener listener : listeners)
    {
      if (listener != null)
      {
        listener.faceLineEncountered (event);
      }
    }
  }
  
  protected void formatLineEncounteredReadEvent (OFFLineReadValidLineEvent event)
  {
    for (OFFLineReaderListener listener : listeners)
    {
      if (listener != null)
      {
        listener.formatLineEncountered (event);
      }
    }
  }
  
  protected void sizeLineEncounteredReadEvent (OFFLineReadValidLineEvent event)
  {
    for (OFFLineReaderListener listener : listeners)
    {
      if (listener != null)
      {
        listener.sizeLineEncountered (event);
      }
    }
  }
  
  protected void vertexLineEncounteredReadEvent (OFFLineReadValidLineEvent event)
  {
    for (OFFLineReaderListener listener : listeners)
    {
      if (listener != null)
      {
        listener.vertexLineEncountered (event);
      }
    }
  }
  
  protected void endOfFileLineEncounteredReadEvent (OFFLineReadValidLineEvent event)
  {
    for (OFFLineReaderListener listener : listeners)
    {
      if (listener != null)
      {
        listener.endOfFileLineEncountered (event);
      }
    }
  }
  
  private String trimLine (String line)
  {
    if (line != null)
    {
      return line.trim ();
    }
    else
    {
      return null;
    }
  }
  
  private boolean isFormatLine (String line)
  {
//    boolean isFormatLine = false;
    String  trimmedLine  = null;
    
    if (line == null)
    {
      return false;
    }
    else if (line.isEmpty ())
    {
      return false;
    }
    else
    {
      trimmedLine  = trimLine (line);
//      isFormatLine = (trimmedLine.equalsIgnoreCase (FORMAT_NAME));
      
//      return (validFormatNames.contains (trimmedLine));
      return trimmedLine.contains ("OFF");
    }
  }
  
  private boolean isEmptyLine (String line)
  {
    String  trimmedLine = null;
    
    trimmedLine = trimLine (line);
    
    if (trimmedLine == null)
    {
      return true;
    }
    else if (trimmedLine.isEmpty ())
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  private boolean isCommentLine (String line)
  {
    boolean isCommentLine = false;
    String  trimmedLine   = null;
    
    trimmedLine = trimLine (line);
    
    if (trimmedLine == null)
    {
      return false;
    }
    else if (trimmedLine.isEmpty ())
    {
      return false;
    }
    else
    {
      isCommentLine = (trimmedLine.startsWith (COMMENT_SYMBOL));
      
      return isCommentLine;
    }
  }
  
  private List<String> splitLineIntoTokens (String line)
  {
    List<String> tokens      = null;
    String       trimmedLine = trimLine (line);
    
    if (trimmedLine != null)
    {
      tokens = Arrays.asList (trimLine (line).split ("\\s+"));
    }
    else
    {
      tokens = new ArrayList<String> ();
    }
    
    return tokens;
  }
  
  private void storeVertexFaceAndEdgeCountFromSizeLine (List<String> tokens)
  {
    if (tokens.size () >= 3)
    {
      this.vertexCount = Integer.parseInt (tokens.get (0));
      this.vertexIndex = 0;
      this.faceCount   = Integer.parseInt (tokens.get (1));
      this.faceIndex   = 0;
      this.edgeCount   = Integer.parseInt (tokens.get (2));
    }
  }
}
