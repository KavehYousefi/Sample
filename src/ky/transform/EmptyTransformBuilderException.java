// 12.08.2015

package ky.transform;


public class EmptyTransformBuilderException
extends      RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  
  public EmptyTransformBuilderException (String message)
  {
    super (message);
  }
  
  public EmptyTransformBuilderException ()
  {
    super ();
  }
}
