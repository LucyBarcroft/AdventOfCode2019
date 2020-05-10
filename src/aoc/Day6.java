package aoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Day6 
{
  
  private static final String S_INPUT_FILE = "inputFiles\\aoc6input.txt";

  public static void main(String[] args)
  {
    try
    {
      // INITIALISE PLANETS
      HashMap<String, Planet> planets = initialisePlanetsFromFile(S_INPUT_FILE);
      
      // PART 1
      Collection<Planet> planetsList = planets.values();
      int numSteps = 0;
      
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
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  private static Planet getPlanetByName(String planetName, HashMap<String, Planet> planets)
  {
    Planet planet = planets.get(planetName);
    
    if (planet == null)
    {
      planet = new Planet(planetName);
      planets.put(planetName, planet);
    }
    
    return planet;
  }
  
  private static HashMap<String, Planet> initialisePlanetsFromFile(String fileName) 
                                                 throws IOException, FileNotFoundException
  {
    File inputFile = new File (fileName);
    BufferedReader reader;
    String orbit;
    HashMap<String, Planet> planets = new HashMap<String, Planet>();
   
    reader = new BufferedReader(new FileReader(inputFile));
      
    while ((orbit = reader.readLine()) != null)
    {
      String[] parentAndChild = orbit.split("\\)");
      String parentName = parentAndChild[0];
      String childName = parentAndChild[1];
      Planet parentPlanet = getPlanetByName(parentName, planets);
      Planet childPlanet = getPlanetByName(childName, planets);
      parentPlanet.addChildPlanet(childPlanet);
    }
    
    reader.close();
    
    return planets;
  }

}
