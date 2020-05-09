package aoc;

public class Day4 
{
  private static final int S_START_INTEGER = 108457;
  private static final int S_END_INTEGER = 562041;
  
  public static void main(String[] args) 
  {
  int numValidPasswords = 0;
  
  for (Integer i = S_START_INTEGER; i <= S_END_INTEGER; i++)
  {
    Integer secondPrevInteger = null;
    Integer prevInteger = null;
    String integerAsString = i.toString();
    boolean containsMatch = false;
    boolean isIncreasing = true;
    Integer matchingNumber = null;
    charLoop: for (int ii = 0; ii < integerAsString.length(); ii++)
    {
      int thisInteger = Integer.parseInt(integerAsString.substring(ii, ii+1));
      
      if (prevInteger != null && thisInteger < prevInteger)
      {
        isIncreasing = false;
        break charLoop;
      }
      
      if (prevInteger != null && thisInteger == prevInteger)
      {
        if (matchingNumber == null || matchingNumber == thisInteger)
        {
          if (secondPrevInteger != null && thisInteger == secondPrevInteger)
          {
            containsMatch = false;
            matchingNumber = null;
          }
          else
          {
              containsMatch = true;
              matchingNumber = thisInteger;
          }
        }
      }
      secondPrevInteger = prevInteger;
      prevInteger = thisInteger;
    }
    
    if (isIncreasing && containsMatch)
    {
      numValidPasswords += 1;
    }  
  }
  
  System.out.println(numValidPasswords);
  }
}
