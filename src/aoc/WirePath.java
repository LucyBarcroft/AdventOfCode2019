package aoc;

/**
 * Contains all information pertaining to a single wire path (from a single instruction)
 * 
 * @author Lucy Barcroft
 */
public class WirePath
{  
  /**
   * The direction this wire path is travelling. 
   */
  private Direction mDirection;
  
  /**
   * The starting co-ordinates of the wirepath
   */
  private int mXPositionStart;
  private int mYPositionStart;
  
  /**
   * The number of steps the wire has taken before this path
   */
  private int mNumSteps;
  
  /**
   * The distance of this wire path
   */
  private int mDistance;
  
  /**
   * The final co-ordinates of the wirepath
   */
  private int mXPositionFinal;
  private int mYPositionFinal;
  
  /**
   * Constructor
   * 
   * Constructs a wirepath. Calculates the final x and y co-ordinate positions of the wire. 
   * 
   * @param xPositionStart   the starting x co-ordinate position of this wirepath
   * @param yPositionStart   the starting y co-ordinate position of this wirepath
   * @param direction        the direction of the wirepath
   * @param distance         the distance of the wirepath
   * @param numSteps         the number of steps the wire has taken before this path
   */
  public WirePath(int xPositionStart, int yPositionStart, Direction direction, int distance, int numSteps)
  {
    mXPositionStart = xPositionStart;
    mYPositionStart = yPositionStart;
    mDirection = direction;
    mDistance = distance;
    mNumSteps = numSteps;
    mXPositionFinal = calculateXPositionFinal();
    mYPositionFinal = calculateYPositionFinal();
  }

  //-------------------------------------------------------------------------
  // PUBLIC METHODS
  //-------------------------------------------------------------------------
  
  public int getXPositionStart()
  {
    return mXPositionStart;
  }
  
  public int getYPositionStart()
  {
    return mYPositionStart;
  }
  
  public int getNumSteps()
  {
    return mNumSteps;
  }
  
  public int getXPositionFinal()
  {
    return mXPositionFinal;
  }
  
  public int getYPositionFinal()
  {
    return mYPositionFinal;
  }
  
  /**
   * Determine whether the specified position is on this wire path.
   * 
   * @param xPosition    the x-coordinate of the position
   * @param yPosition    the y-coordinate of the position
   * 
   * @return      whether the position is on this wire path
   */
  public boolean isOnWirePath(int xPosition, int yPosition)
  {
    boolean onWirePath = false;
    int xPositionMin = Math.min(mXPositionStart, mXPositionFinal);
    int xPositionMax = Math.max(mXPositionStart, mXPositionFinal);
    int yPositionMin = Math.min(mYPositionStart, mYPositionFinal);
    int yPositionMax = Math.max(mYPositionStart, mYPositionFinal);
    
    if ((xPosition >= xPositionMin) && (xPosition <= xPositionMax) &&
        (yPosition >= yPositionMin) && (yPosition <= yPositionMax))
    {
      onWirePath = true;
    }

    return onWirePath;
  }
  
  /**
   * Get the number of steps this wire has taken to reach the specified position
   * This method should only be used for a position which is on this wire path
   * 
   * @param xPosition   the x co-ordinate of the position
   * @param yPosition   the y co-ordinate of the position
   * 
   * @return    the number of steps for wire to reach this position
   */
  public int getNumStepsForPosition(int xPosition, int yPosition)
  {
    int numSteps;
    if (isOnWirePath(xPosition, yPosition))
    {
      numSteps = mNumSteps + Math.abs(xPosition - mXPositionStart) + Math.abs(yPosition - mYPositionStart);
    }
    else 
    {
      throw new RuntimeException("position isn't on wire path!");
    }
    
    return numSteps;
  }

  //-------------------------------------------------------------------------
  // PRIVATE METHODS
  //-------------------------------------------------------------------------
  
  /**
   * @return   final x co-ordinate of the wire after the taking this wirepath.
   */
  private int calculateXPositionFinal()
  {
    int xPositionFinal;
    switch (mDirection)
    {
      case U:
      case D:
      {
        // A wire moving up or down doesn't change x co-ordinate
        xPositionFinal = mXPositionStart;
        break;
      }
      case L:
      {
        // A wire moving left reduces its x co-ordinate
        xPositionFinal = mXPositionStart - mDistance;
        break;
      }
      case R:
      {
        // A wire moving right increases its x co-ordinate
        xPositionFinal = mXPositionStart + mDistance;
        break;
      }
      default:
      {
        throw new RuntimeException("invalid direction: " + mDirection);
      }
    }
      
    return xPositionFinal;
  }
  
  /**
   * @return   final y co-ordinate of the wire after the taking this wirepath.
   */
  private int calculateYPositionFinal()
  {
    int yPositionFinal;
    switch (mDirection)
    {
      case L:
      case R:
      {
        // A wire moving left or right doesn't change y co-ordinate
        yPositionFinal = mYPositionStart;
        break;
      }
      case D:
      {
        // A wire moving down decreases its y co-ordinate
        yPositionFinal = mYPositionStart - mDistance;
        break;
      }
      case U:
      {
        // A wire moving up increases its y co-ordinate
        yPositionFinal = mYPositionStart + mDistance;
        break;
      }
      default:
      {
        throw new RuntimeException("invalid direction: " + mDirection);
      }
    }
      
    return yPositionFinal;
  }
}
