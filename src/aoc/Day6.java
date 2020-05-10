package aoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Day6 
{
  
  private static final String S_INPUT_FILE = "inputFiles\\aoc6input.txt";
  
  private static HashMap<String, Planet> planets = new HashMap<String, Planet>();

  public static void main(String[] args)
  {
    // INITIALISE PLANETS
    File inputFile = new File (S_INPUT_FILE);
    BufferedReader reader;
    String orbit;
    int numSteps = 0;
    
    try
    {
      reader = new BufferedReader(new FileReader(inputFile));
      
      while ((orbit = reader.readLine()) != null)
      {
        String[] parentAndChild = orbit.split("\\)");
        String parentName = parentAndChild[0];
        String childName = parentAndChild[1];
        Planet parentPlanet = planets.get(parentName);
        Planet childPlanet = planets.get(childName);
        if (parentPlanet == null)
        {
          parentPlanet = new Planet(parentName);
          planets.put(parentName, parentPlanet);
        }
        if (childPlanet == null)
        {
          childPlanet = new Planet(childName);
          planets.put(childName, childPlanet);
        }
        
        parentPlanet.addChildPlanet(childPlanet);
      }
      
      // PART 1
      Collection<Planet> planetsList = planets.values();
     
      for (Planet planet : planetsList)
      {
        int numStepsForPlanet = planet.getDistanceToCentreOfMass();
      
        numSteps += numStepsForPlanet;
      }
      
      System.out.println("Total num steps: " + numSteps);
      
      
      // PART 2
      Planet youPlanet = planets.get("YOU");
      Planet sanPlanet = planets.get("SAN");
      
      int distanceToSanta = youPlanet.getDistanceToPlanet(sanPlanet);
      System.out.println("distance to santa: " + distanceToSanta);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

}
