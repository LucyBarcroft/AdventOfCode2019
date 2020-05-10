package aoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day3 
{
  private static final String S_INPUT_FILE = "inputFiles\\aoc3input.txt";
	 
  public static void main(String[] args) 
  {
    Integer lowestCombinedSteps = null;
    Integer lowestCrossingDistance = null;
		File inputFile = new File (S_INPUT_FILE);
		BufferedReader reader;
		List<WirePath> wire1FullPath = new ArrayList<WirePath>();
		int xPosition = 0;
		int yPosition = 0;
		int numSteps = 0;
		 
		try
		{
			reader = new BufferedReader(new FileReader(inputFile));
			List<String> wire1Input = Arrays.asList(reader.readLine().split(","));
			List<String> wire2Input = Arrays.asList(reader.readLine().split(","));
			reader.close();
			
			// Construct a full list of wire paths for wire 1
			// One wire path corresponds to a single instruction.
			// Has a starting point, direction and distance. 
		  for (String instruction : wire1Input)
			{
				Direction direction = Direction.valueOf(instruction.substring(0,1));
				int distance = Integer.parseInt(instruction.substring(1,instruction.length()));
				WirePath wirePath = new WirePath(xPosition, yPosition, direction, distance, numSteps);
				wire1FullPath.add(wirePath);
				
				numSteps += distance;
				xPosition = wirePath.getXPositionFinal();
				yPosition = wirePath.getYPositionFinal();
			}
		  
		  // reset values to starting position for second wire.
		  xPosition = 0;
			yPosition = 0;
			numSteps = 0;
			
			// for each point wire 2 goes through, determine whether wire 1 has passed this point.
			// Crossing distance/combined steps is calculated for points which cross paths
			// Only the minimum crossing distance/combined steps value is kept 
			for (String instruction : wire2Input)
			{
				Direction direction = Direction.valueOf(instruction.substring(0,1));
				int distance = Integer.parseInt(instruction.substring(1,instruction.length()));
				
				switch (direction)
				{
					case U:
					{
						for (int i = 0; i < distance; i++)
						{
							yPosition += 1;
							numSteps +=1;
							//lowestCrossingDistance = getCurrentLowestCrossingDistance(xPosition, yPosition, lowestCrossingDistance, wire1FullPath);
							lowestCombinedSteps = getCurrentLowestCombinedSteps(xPosition, yPosition, numSteps, lowestCombinedSteps, wire1FullPath);
						}
						break;
					}
					case D:
					{
						for (int i = 0; i < distance; i++)
						{
							yPosition -= 1;
							numSteps +=1;
							//lowestCrossingDistance = getCurrentLowestCrossingDistance(xPosition, yPosition, lowestCrossingDistance, wire1FullPath);
							lowestCombinedSteps = getCurrentLowestCombinedSteps(xPosition, yPosition, numSteps, lowestCombinedSteps, wire1FullPath);
						}
						break;
					}
					case L:
					{
						for (int i = 0; i < distance; i++)
						{
							xPosition -= 1;
							numSteps += 1;
							//lowestCrossingDistance = getCurrentLowestCrossingDistance(xPosition, yPosition, lowestCrossingDistance, wire1FullPath);
							lowestCombinedSteps = getCurrentLowestCombinedSteps(xPosition, yPosition, numSteps, lowestCombinedSteps, wire1FullPath);
						}
						break;
					}
					case R:
					{
						for (int i = 0; i < distance; i++)
						{
							xPosition += 1;
							numSteps += 1;
							//lowestCrossingDistance = getCurrentLowestCrossingDistance(xPosition, yPosition, lowestCrossingDistance, wire1FullPath);
							lowestCombinedSteps = getCurrentLowestCombinedSteps(xPosition, yPosition, numSteps, lowestCombinedSteps, wire1FullPath);
						}
						break;
					}
					default:
					{
						throw new RuntimeException("invalid direction: " + direction);
					}
				}
			}
			//System.out.println("lowest crossing distance: " + lowestCrossingDistance);
			System.out.println("lowest combined steps: " + lowestCombinedSteps);
		}
		catch (Exception e)
		{
		  //TODO
		}
   }
  
  /**
   * Get the lowest number of combined steps the wire has taken so far to reach a crossing point
   * 
   * @param xPositionWire2
   * @param yPositionWire2
   * @param numSteps
   * @param previousLowestCombinedSteps
   * @param wire1FullPath
   * @return
   */
  private static Integer getCurrentLowestCombinedSteps(int xPositionWire2, int yPositionWire2, int numSteps, Integer previousLowestCombinedSteps, List<WirePath> wire1FullPath)
	{
		Integer currentLowestCombinedSteps = previousLowestCombinedSteps;
		
		positionLoop: for (WirePath wirePath : wire1FullPath)
		{
			if (wirePath.isOnWirePath(xPositionWire2, yPositionWire2))	
			{
				int combinedSteps =  numSteps + wirePath.getNumStepsForPosition(xPositionWire2, yPositionWire2);
				
				if (currentLowestCombinedSteps == null || combinedSteps < currentLowestCombinedSteps)
				{
					currentLowestCombinedSteps = combinedSteps;
				}
				break positionLoop;
			}
		}
		
		return currentLowestCombinedSteps;
	}
  
  /**
   * Get the lowest distance so far from the centre that lies a crossing point
   * 
   * @param xPositionWire2
   * @param yPositionWire2
   * @param previousLowestCrossingDistance
   * @param wire1FullPath
   * @return
   */
  private static Integer getCurrentLowestCrossingDistance(int xPositionWire2, int yPositionWire2, Integer previousLowestCrossingDistance, List<WirePath> wire1FullPath)
	{
  	Integer currentLowestCrossingDistance = previousLowestCrossingDistance;
    int distanceFromCentre = Math.abs(xPositionWire2) + Math.abs(yPositionWire2);
    
    if (currentLowestCrossingDistance == null || distanceFromCentre < currentLowestCrossingDistance)
    {
    	positionLoop: for (WirePath wirePath : wire1FullPath)
  		{
  			if (wirePath.isOnWirePath(xPositionWire2, yPositionWire2))
				{
  				currentLowestCrossingDistance = distanceFromCentre;
  				break positionLoop;
				}
  		}
    }
  	
  	return currentLowestCrossingDistance;
	}
}
