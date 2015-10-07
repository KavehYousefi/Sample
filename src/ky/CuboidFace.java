package ky;

public enum CuboidFace
{
  FRONT  (0, 1, 2, 3),
  BACK   (4, 5, 6, 7),
  TOP    (4, 0, 3, 7),
  BOTTOM (6, 2, 1, 5),
  LEFT   (4, 5, 1, 0),
  RIGHT  (3, 2, 6, 7);
  
  
  private int[] pointIndices;
  
  
  /**
   * <div class="introConstructor">
   *   Creates a cuboid face by defining the four indices of the
   *   vertices which comprise them.
   * </div>
   * 
   * @param index1  The first  cuboid vertex' index.
   * @param index2  The second cuboid vertex' index.
   * @param index3  The third  cuboid vertex' index.
   * @param index4  The fourth cuboid vertex' index.
   */
  private CuboidFace (int index1, int index2, int index3, int index4)
  {
    this.pointIndices = new int[] {index1, index2, index3, index4};
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the indices of the four vertices defining this face.
   * </div>
   * 
   * @return  An array of the four vertex indices.
   */
  public int[] getPointIndices ()
  {
    return pointIndices;
  }
}