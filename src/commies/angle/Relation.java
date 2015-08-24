package commies.angle;


/**
 * <div class="introClass">
 *   Relations describe basic comparison operations.
 * </div>
 * 
 * <table class="classInfoTable">
 *   <tr>
 *     <td>
 *       File:
 *     </td>
 *     <td>
 *       Relation.java
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
 *       22.07.2013
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
 *     <td>22.07.2013</td>
 *     <td>1.0</td>
 *     <td>The enum type has been created.</td>
 *   </tr>
 * </table>
 * 
 * @author   Kaveh Yousefi
 * @version  1.0
 */
public enum Relation
{
  LESS ("<")
  {
    @Override
    public boolean isTrueForNumber (Number zahl1, Number zahl2)
    {
      return (zahl1.doubleValue () < zahl2.doubleValue ());
    }
    
    @Override
    public boolean isTrueForString (String zeiket1, String zeiket2)
    {
      boolean istWahr   = false;
      int     vergleich = zeiket1.compareTo (zeiket2);
      
      istWahr = (vergleich < 0);
      
      return istWahr;
    }
  },
  
  LESS_OR_EQUAL ("<=")
  {
    @Override
    public boolean isTrueForNumber (Number zahl1, Number zahl2)
    {
      return (zahl1.doubleValue () <= zahl2.doubleValue ());
    }
    
    @Override
    public boolean isTrueForString (String zeiket1, String zeiket2)
    {
      boolean istWahr   = false;
      int     vergleich = zeiket1.compareTo (zeiket2);
      
      istWahr = (vergleich <= 0);
      
      return istWahr;
    }
  },
  
  EQUAL ("=")
  {
    @Override
    public boolean isTrueForNumber (Number zahl1, Number zahl2)
    {
      return (zahl1.doubleValue () == zahl2.doubleValue ());
    }
    
    @Override
    public boolean isTrueForString (String zeiket1, String zeiket2)
    {
      boolean istWahr   = false;
      int     vergleich = zeiket1.compareTo (zeiket2);
      
      istWahr = (vergleich == 0);
      
      return istWahr;
    }
  },
  
  NOT_EQUAL ("!=")
  {
    @Override
    public boolean isTrueForNumber (Number zahl1, Number zahl2)
    {
      return (zahl1.doubleValue () != zahl2.doubleValue ());
    }
    
    @Override
    public boolean isTrueForString (String zeiket1, String zeiket2)
    {
      boolean istWahr   = false;
      int     vergleich = zeiket1.compareTo (zeiket2);
      
      istWahr = (vergleich != 0);
      
      return istWahr;
    }
  },
  
  GREATER (">")
  {
    @Override
    public boolean isTrueForNumber (Number zahl1, Number zahl2)
    {
      return (zahl1.doubleValue () > zahl2.doubleValue ());
    }
    
    @Override
    public boolean isTrueForString (String zeiket1, String zeiket2)
    {
      boolean istWahr   = false;
      int     vergleich = zeiket1.compareTo (zeiket2);
      
      istWahr = (vergleich > 0);
      
      return istWahr;
    }
  },
  
  GREATER_OR_EQUAL (">=")
  {
    @Override
    public boolean isTrueForNumber (Number zahl1, Number zahl2)
    {
      return (zahl1.doubleValue () >= zahl2.doubleValue ());
    }
    
    @Override
    public boolean isTrueForString (String zeiket1, String zeiket2)
    {
      boolean istWahr   = false;
      int     vergleich = zeiket1.compareTo (zeiket2);
      
      istWahr = (vergleich >= 0);
      
      return istWahr;
    }
  };
  
  
  private String symbol;
  
  
  private Relation (String symbol)
  {
    this.symbol = symbol;
  }
  
  
  /**
   * <div class="introMethod">
   *   Returns the relation's symbol.
   * </div>
   * 
   * @return  A symbol for representing this relation.
   */
  public String getSymbol ()
  {
    return symbol;
  }
  
  
  abstract public boolean isTrueForNumber (Number zahl1, Number zahl2);
  
  abstract public boolean isTrueForString (String zeiket1, String zeiket2);
}