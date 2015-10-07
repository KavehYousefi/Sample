// 20.08.2015
// 
// Read URL:           -> "https://docs.oracle.com/javase/tutorial/networking/urls/readingURL.html"
// Iterating a Stream: -> "http://www.java2s.com/Tutorials/Java_Streams/Tutorial/Streams/Stream_iterate_.htm"

package jav3d.offloader.processor;

import safercode.CheckingUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;


public class OFFFileReader
{
  private OFFLineReader lineReader;
  
  
  public OFFFileReader (OFFLineReader lineReader)
  {
    this.lineReader = lineReader;
  }
  
  
  public OFFLineReader getOFFLineReader ()
  {
    return lineReader;
  }
  
  public void setOFFLineReader (OFFLineReader lineReader)
  {
    this.lineReader = lineReader;
  }
  
  
  public void readFileFromFileName (String fileName)
  throws IOException
  {
    FileReader fileReader = null;
    
    fileReader = new FileReader (fileName);
    
    readFileWithReader (fileReader);
  }
  
  public void readFileWithReader (Reader reader)
  throws IOException
  {
    CheckingUtils.checkForNull (reader, "Reader is null.");
    
    BufferedReader bufferedReader = null;
    String         readLine       = null;
    
    bufferedReader = new BufferedReader (reader);
    
    do
    {
      readLine = bufferedReader.readLine ();
      
      lineReader.readLine (readLine);
    }
    while (readLine != null);
    
    bufferedReader.close ();
    reader.close         ();
  }
  
  public void readFileFromURL (URL url)
  throws IOException
  {
    CheckingUtils.checkForNull (url, "URL is null.");
    
    InputStream       inputStream       = null;
    InputStreamReader inputStreamReader = null;
    
    inputStream       = url.openStream        ();
    inputStreamReader = new InputStreamReader (inputStream);
    
    readFileWithReader (inputStreamReader);
  }
  
  public void readFileFromPath (Path path)
  throws IOException
  {
    CheckingUtils.checkForNull (path, "Path is null.");
    
    Stream<String> lineStream = null;
    
    lineStream = Files.lines (path);
    
    lineStream.forEach (line -> lineReader.readLine (line));
    lineStream.close   ();
  }
}
