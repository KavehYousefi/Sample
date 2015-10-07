package adtest01;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.Group;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import j3dextraction.SceneGraphDataExtractor;
import ky.appearance.ColorAppearance;
import ky.appearance.GoldAppearance;
import ky.gamelogic.Effect;
import ky.gamelogic.Statistics;


public class MarkerModelsXMLLoader
{
  private EffectFactory             effectFactory;
  private TransformHierarchyFactory transformHierarchyFactory;
  
  
  public MarkerModelsXMLLoader
  (
    EffectFactory             effectFactory,
    TransformHierarchyFactory transformHierarchyFactory
  )
  {
    this.effectFactory             = effectFactory;
    this.transformHierarchyFactory = transformHierarchyFactory;
  }
  
  
  public Map<String, MarkerModel> loadFromPath (Path filePath)
  throws ParserConfigurationException, SAXException, IOException
  {
    Map<String, MarkerModel> nameModelMapping       = null;
    File                     inputFile              = null;
    DocumentBuilderFactory   documentBuilderFactory = null;
    DocumentBuilder          documentBuilder        = null;
    Document                 document               = null;
    
    nameModelMapping       = new HashMap<String, MarkerModel>          ();
    inputFile              = filePath.toFile                           ();
    documentBuilderFactory = DocumentBuilderFactory.newInstance        ();
    documentBuilder        = documentBuilderFactory.newDocumentBuilder ();
    document               = documentBuilder.parse (inputFile);
    
    document.getDocumentElement ().normalize ();
    
    processRootNode (document, nameModelMapping);
    
    return nameModelMapping;
  }
  
  
  
  //////////////////////////////////////////////////////////////////////
  // -- Implementation of auxiliary methods.                       -- //
  //////////////////////////////////////////////////////////////////////
  
  private void processRootNode
  (
    Document                 document,
    Map<String, MarkerModel> nameModelMapping
  )
  {
    NodeList modelNodes = document.getElementsByTagName ("model");
    
    for (int nodeIndex = 0; nodeIndex < modelNodes.getLength ();
                            nodeIndex++)
    {
      Node modelNode = modelNodes.item (nodeIndex);
      
      if (modelNode.getNodeType () == Node.ELEMENT_NODE)
      {
        Element modelElement = (Element) modelNode;
        
        processModelNode (document, modelElement, nameModelMapping);
      }
    }
  }
  
  private void processModelNode
  (
    Document                 document,
    Element                  modelNode,
    Map<String, MarkerModel> nameModelMapping
  )
  {
    String               modelKey           = null;
    String               modelName          = null;
    MarkerModel          markerModel        = null;
    Path                 markerFilePath     = null;
    TransformHierarchy   transformHierarchy = null;
    NodeList             childNodes         = null;
    MarkerModelType      markerModelType    = null;
    ky.gamelogic.Element markerElement      = null;
    Statistics           statistics         = null;
    BufferedImage        icon               = null;
    Effect               effect             = null;
    boolean              canCollide         = true;
    Integer              playerNumber       = null;
    
    modelKey   = modelNode.getAttribute  ("key");
    modelName  = modelNode.getAttribute  ("name");
    childNodes = modelNode.getChildNodes ();
    
    for (int nodeIndex = 0; nodeIndex < childNodes.getLength (); nodeIndex++)
    {
      Node childNode = childNodes.item (nodeIndex);
      
      if (childNode.getNodeType () == Node.ELEMENT_NODE)
      {
        Element childElement = (Element) childNode;
        String  elementName  = childElement.getTagName ();
        
        switch (elementName)
        {
          case "marker-file" :
          {
            markerFilePath = processMarkerFilePathNode (document, childElement);
            break;
          }
          case "graphics" :
          {
            transformHierarchy = processGraphicsNode (document, childElement);
            break;
          }
          case "marker-type" :
          {
            markerModelType = processMarkerTypeNode (document, childElement);
            break;
          }
          case "element" :
          {
            markerElement = processMarkerElementNode (document, childElement);
            break;
          }
          case "statistics" :
          {
            statistics = processStatisticsNode (document, childElement);
            break;
          }
          case "icon" :
          {
            icon = processIconNode (document, childElement);
            break;
          }
          case "effect" :
          {
            effect = processEffectNode (document, childElement);
            break;
          }
          case "collision" :
          {
            canCollide = processCollisionNode (document, childElement);
            break;
          }
          case "player" :
          {
            playerNumber = processPlayerNode (document, childElement);
            break;
          }
          default :
          {
            break;
          }
        }
      }
    }
    
    if (transformHierarchy == null)
    {
      transformHierarchy = new TransformHierarchy ();
    }
    
    markerModel = new MarkerModel
    (
      markerFilePath,
      modelName,
      transformHierarchy
    );
    
    markerModel.setCanCollide (canCollide);
    
    if (markerModelType != null)
    {
      markerModel.setMarkerObjectType (markerModelType);
    }
    if (markerElement != null)
    {
      markerModel.getCharacterInfo ().setElement (markerElement);
    }
    if (statistics != null)
    {
      markerModel.getCharacterInfo ().setStatistics (statistics);
    }
    if (icon != null)
    {
      markerModel.setIcon (icon);
    }
    if (effect != null)
    {
      markerModel.setEffect       (effect);
      markerModel.setPlayerNumber (Player.NO_PLAYER);
    }
    if (markerModel.isCharacter ())
    {
      markerModel.setPlayerNumber (Player.SEARCHING_PLAYER);
    }
    if (playerNumber != null)
    {
      markerModel.setPlayerNumber (playerNumber);
    }
    
    nameModelMapping.put (modelKey, markerModel);
  }
  
  private Path processMarkerFilePathNode
  (
    Document document,
    Element  markerFilePathNode
  )
  {
    Path   filePath       = null;
    String filePathString = null;
    
    filePathString = markerFilePathNode.getAttribute ("path");
    filePath       = Paths.get                       (filePathString);
    
    return filePath;
  }
  
  private TransformHierarchy processGraphicsNode
  (
    Document document,
    Element  graphicsNode
  )
  {
    TransformHierarchy transformHierarchy = null;
    String             graphicsType       = null;
    NodeList           childNodes         = null;
    Appearance         appearance         = null;
    
    graphicsType = graphicsNode.getAttribute  ("type");
    childNodes   = graphicsNode.getChildNodes ();
    
    switch (graphicsType)
    {
      case "external-model" :
      {
        transformHierarchy = processExternalModelGraphicsNode (document, graphicsNode);
        break;
      }
      case "custom-3d-model" :
      {
        transformHierarchy = processCustom3DModelGraphicsNode (document, graphicsNode);
        break;
      }
      default :
      {
        transformHierarchy = new TransformHierarchy ();
        break;
      }
    }
    
    for (int childIndex = 0; childIndex < childNodes.getLength ();
                             childIndex++)
    {
      Node childNode = childNodes.item (childIndex);
      
      if (childNode.getNodeType () == Node.ELEMENT_NODE)
      {
        Element childElement = (Element) childNode;
        
        switch (childElement.getTagName ())
        {
          case "appearance" :
          {
            appearance = processAppearanceNode (document, childElement);
            break;
          }
          default :
          {
            break;
          }
        }
      }
    }
    
    if (appearance != null)
    {
      applyAppearance (transformHierarchy.getRootTransformGroup (),
                       appearance);
    }
    
    return transformHierarchy;
  }
  
  private TransformHierarchy processExternalModelGraphicsNode
  (
    Document document,
    Element  graphicsNode
  )
  {
    NodeList childNodes = null;
    
    childNodes = graphicsNode.getChildNodes ();
    
    for (int childIndex = 0; childIndex < childNodes.getLength ();
                             childIndex++)
    {
      Node childNode = childNodes.item (childIndex);
      
      if (childNode.getNodeType () == Node.ELEMENT_NODE)
      {
        Element childElement = (Element) childNode;
        
        if (childElement.getTagName ().equals ("external-model-file"))
        {
          String filePathString = childElement.getAttribute ("path");
          String scalingString  = childElement.getAttribute ("scaling");
          
          return loadModel (Paths.get (filePathString), Double.parseDouble (scalingString));
        }
      }
    }
    
    return null;
  }
  
  private TransformHierarchy processCustom3DModelGraphicsNode
  (
    Document document,
    Element  graphicsNode
  )
  {
    NodeList childNodes = null;
    
    childNodes = graphicsNode.getChildNodes ();
    
    for (int childIndex = 0; childIndex < childNodes.getLength ();
                             childIndex++)
    {
      Node childNode = childNodes.item (childIndex);
      
      if (childNode.getNodeType () == Node.ELEMENT_NODE)
      {
        Element childElement = (Element) childNode;
        
        if (childElement.getTagName ().equals ("custom-model"))
        {
          String name = childElement.getAttribute ("name");
          
          return transformHierarchyFactory.getTransformHierarchyByName (name);
        }
      }
    }
    
    return null;
  }
  
  private Appearance processAppearanceNode
  (
    Document document,
    Element  appearanceNode
  )
  {
    Appearance appearance       = null;
    String     appearanceType = null;
    
    appearanceType = appearanceNode.getAttribute ("type");
    
    switch (appearanceType)
    {
      case "color" :
      {
        String  colorString = appearanceNode.getAttribute ("color");
        Color3f color       = parseStringAsColor3f (colorString);
        
        appearance = new ColorAppearance (color);
        break;
      }
      case "gold" :
      {
        appearance = new GoldAppearance ();
        break;
      }
      default :
      {
        break;
      }
    }
    
    return appearance;
  }
  
  private MarkerModelType processMarkerTypeNode
  (
    Document document,
    Element  markerTypeNode
  )
  {
    String markerTypeString = null;
    
    markerTypeString = markerTypeNode.getAttribute ("type");
    
    return getMarkerModelTypeForString (markerTypeString);
  }
  
  private ky.gamelogic.Element processMarkerElementNode
  (
    Document document,
    Element  markerElementNode
  )
  {
    String markerTypeString = null;
    
    markerTypeString = markerElementNode.getAttribute ("type");
    
    return getMarkerElementForString (markerTypeString);
  }
  
  private Statistics processStatisticsNode
  (
    Document document,
    Element  statisticsNode
  )
  {
    Statistics statistics = null;
    
    statistics = new Statistics
    (
      Integer.parseInt (statisticsNode.getAttribute ("power")),
      Integer.parseInt (statisticsNode.getAttribute ("attack")),
      Integer.parseInt (statisticsNode.getAttribute ("defense"))
    );
    
    return statistics;
  }
  
  private BufferedImage processIconNode
  (
    Document document,
    Element  markerFilePathNode
  )
  {
    BufferedImage icon           = null;
    String        filePathString = null;
    
    filePathString = markerFilePathNode.getAttribute ("path");
    icon           = loadImageOrNull (Paths.get (filePathString));
    
    return icon;
  }
  
  private Effect processEffectNode
  (
    Document document,
    Element  effectNode
  )
  {
    Effect effect     = null;
    String effectName = null;
    
    effectName = effectNode.getAttribute       ("name");
    effect     = effectFactory.getEffectByName (effectName);
    
    return effect;
  }
  
  private boolean processCollisionNode
  (
    Document document,
    Element  collisionNode
  )
  {
    boolean canCollide             = true;
    String  collisionAllowedString = null;
    
    collisionAllowedString = collisionNode.getAttribute ("allowed");
    
    switch (collisionAllowedString)
    {
      case "true" :
      {
        canCollide = true;
        break;
      }
      case "false" :
      {
        canCollide = false;
        break;
      }
      default :
      {
        canCollide = true;
        break;
      }
    }
    
    return canCollide;
  }
  
  private Integer processPlayerNode
  (
    Document document,
    Element  playerNode
  )
  {
    Integer playerNumber       = null;
    String  playerNumberString = null;
    
    playerNumberString = playerNode.getAttribute ("number");
    
    if (! playerNumberString.isEmpty ())
    {
      playerNumber = Integer.parseInt (playerNumberString);
    }
    else
    {
      playerNumber = null;
    }
    
    return playerNumber;
  }
  
  
  private BufferedImage loadImageOrNull (Path imageFilePath)
  {
    try
    {
      return ImageIO.read (imageFilePath.toFile ());
    }
    catch (Exception e)
    {
      return null;
    }
  }
  
  private TransformHierarchy loadModel
  (
    Path   modelFilePath,
    double scale
  )
  {
    TransformHierarchy transformHierarchy = null;
    TransformGroup     propTG             = null;
    PropManager        propMan            = null;
    Transform3D        modelT3d           = null;
    Vector3d           scaleVec           = null;
    
    propMan            = new PropManager (modelFilePath);
    transformHierarchy = propMan.getTransformHierarchy            ();
    propTG             = transformHierarchy.getMoveTransformGroup ();
    
    // rotate and scale the prop
    modelT3d = new Transform3D ();
    modelT3d.rotX              (Math.PI/2.0);    
    // the prop lies flat on the marker; rotate forwards 90 degrees so it is standing
    scaleVec = calcScaleFactor (propTG, scale);   // scale the prop
    modelT3d.setScale          (scaleVec);
    
    transformHierarchy.getRootTransformGroup ()
                      .setTransform          (modelT3d);
    
    return transformHierarchy;
  }  // end of loadModel()
  
  private Vector3d calcScaleFactor (TransformGroup modelTG, double scale)
  // Scale the prop based on its original bounding box size
  {
     BoundingBox boundbox = new BoundingBox (modelTG.getBounds ());
     Point3d     lower    = null;
     Point3d     upper    = null;
     
     // obtain the upper and lower coordinates of the box
     lower = new Point3d ();
     boundbox.getLower   (lower);
     upper = new Point3d ();
     boundbox.getUpper   (upper);
     
     // store the largest X, Y, or Z dimension and calculate a scale factor
     double max = 0.0;
     if (Math.abs (upper.x - lower.x) > max)
     {
       max = Math.abs (upper.x - lower.x);
     }
     
     if (Math.abs (upper.y - lower.y) > max)
     {
       max = Math.abs (upper.y - lower.y);
     }
     
     if( Math.abs (upper.z - lower.z) > max)
     {
       max = Math.abs (upper.z - lower.z);
     }
     
     double scaleFactor = scale/max;
     
     // limit the scaling so that a big model isn't scaled too much
     if (scaleFactor < 0.0005)
     {
       scaleFactor = 0.0005;
     }
     
     return new Vector3d (scaleFactor, scaleFactor, scaleFactor);
  }  // end of calcScaleFactor()
  
  private MarkerModelType getMarkerModelTypeForString (String markerModelTypeString)
  {
    switch (markerModelTypeString)
    {
      case "character" :
      {
        return MarkerModelType.CHARACTER;
      }
      case "defense" :
      {
        return MarkerModelType.DEFENSE_MARKER;
      }
      case "effect" :
      {
        return MarkerModelType.EFFECT;
      }
      default :
      {
        return null;
      }
    }
  }
  
  private ky.gamelogic.Element getMarkerElementForString (String elementString)
  {
    switch (elementString)
    {
      case "fire" :
      {
        return ky.gamelogic.Element.FIRE;
      }
      case "ice" :
      {
        return ky.gamelogic.Element.ICE;
      }
      case "none" :
      {
        return ky.gamelogic.Element.NONE;
      }
      case "water" :
      {
        return ky.gamelogic.Element.WATER;
      }
      default :
      {
        return null;
      }
    }
  }
  
  private Color3f parseStringAsColor3f (String colorString)
  {
    Color3f  color           = null;
    String[] tokens          = colorString.split ("\\s+");
    float [] colorComponents = new float[tokens.length];
    
    for (int tokenIndex = 0; tokenIndex < tokens.length; tokenIndex++)
    {
      colorComponents[tokenIndex] = Float.parseFloat (tokens[tokenIndex]);
    }
    
    color = new Color3f (colorComponents);
    
    return color;
  }
  
  private void applyAppearance (Group group, Appearance appearance)
  {
    SceneGraphDataExtractor sceneDataExtractor = null;
    
    sceneDataExtractor = new SceneGraphDataExtractor ();
    sceneDataExtractor.examine (group);
    
    for (Shape3D shape : sceneDataExtractor.getShape3Ds ())
    {
      shape.setAppearance (appearance);
    }
  }
}
