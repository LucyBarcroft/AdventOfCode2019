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
		  
		  xPosition = 0;
			yPosition = 0;
			numSteps = 0;
			
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
