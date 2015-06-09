// 09.06.2015

package commies;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.Node;
import javax.media.j3d.SceneGraphPath;
import javax.media.j3d.WakeupCriterion;
import javax.media.j3d.WakeupOnCollisionEntry;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.media.j3d.WakeupOnCollisionMovement;
import javax.media.j3d.WakeupOr;
import javax.vecmath.Point3d;


public class CollisionDetector
extends      Behavior
{
  private Node                    armingNode;
  private WakeupOr                wakeupOr;
  private List<CollisionListener> listeners;
  
  
  public CollisionDetector (Node armingNode)
  {
    super ();
    
    this.armingNode = armingNode;
    this.wakeupOr   = null;
    this.listeners  = new ArrayList<CollisionListener> ();
    
    this.setSchedulingBounds (new BoundingSphere (new Point3d (), 1000.0));
  }
  
  
  public void addCollisionListener (CollisionListener listener)
  {
    if (listener != null)
    {
      listeners.add (listener);
    }
  }
  
  public void removeCollisionListener (CollisionListener listener)
  {
    listeners.remove (listener);
  }
  
  
  @Override
  public void initialize ()
  {
    WakeupCriterion[] wakeupCriterionArray = null;
    
    wakeupCriterionArray = new WakeupCriterion[]
    {
      new WakeupOnCollisionEntry    (armingNode),
      new WakeupOnCollisionExit     (armingNode),
      new WakeupOnCollisionMovement (armingNode)
    };
    wakeupOr             = new WakeupOr (wakeupCriterionArray);
    
    wakeupOn (wakeupOr);
  }
  
  @Override
  @SuppressWarnings ({"rawtypes"})
  public void processStimulus (Enumeration criteria)
  {
    while (criteria.hasMoreElements ())
    {
      WakeupCriterion wakeupCriterion = null;
      
      wakeupCriterion = (WakeupCriterion) criteria.nextElement ();
      
      if (wakeupCriterion instanceof WakeupOnCollisionEntry)
      {
        WakeupOnCollisionEntry wakeupOnEntry = null;
        
        wakeupOnEntry = (WakeupOnCollisionEntry) wakeupCriterion;
        
        
        System.out.format ("COLLISION ENTRY!%n");
      }
      else if (wakeupCriterion instanceof WakeupOnCollisionExit)
      {
        WakeupOnCollisionExit wakeupOnExit = null;
        
        wakeupOnExit = (WakeupOnCollisionExit) wakeupCriterion;
        
        System.out.format ("COLLISION EXIT!%n");
      }
      else if (wakeupCriterion instanceof WakeupOnCollisionMovement)
      {
        WakeupOnCollisionMovement wakeupOnMove = null;
        
        wakeupOnMove = (WakeupOnCollisionMovement) wakeupCriterion;
        
        System.out.format ("COLLISION MOVED!%n");
      }
    }
    
    wakeupOn (wakeupOr);
  }
  
  private Node findMarkerModelNode (SceneGraphPath sceneGraphPath)
  {
    Node modelNode = null;
    
    for (int nodeIndex = 0; nodeIndex < sceneGraphPath.nodeCount (); nodeIndex++)
    {
      Node node = sceneGraphPath.getNode (nodeIndex);
      
      if ((node != null) && (node.getUserData () != null))
      {
        System.out.println (node.getUserData ());
        
        //
      }
    }
    
    return modelNode;
  }
  
  protected void fireCollisionEntryEvent (CollisionEvent event)
  {
    for (CollisionListener listener : listeners)
    {
      if (listener != null)
      {
        listener.collisionEntered (event);
      }
    }
  }
  
  protected void fireCollisionMoveEvent (CollisionEvent event)
  {
    for (CollisionListener listener : listeners)
    {
      if (listener != null)
      {
        listener.collisionMoved (event);
      }
    }
  }
  
  protected void fireCollisionExitEvent (CollisionEvent event)
  {
    for (CollisionListener listener : listeners)
    {
      if (listener != null)
      {
        listener.collisionExited (event);
      }
    }
  }
}
