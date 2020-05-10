package aoc;

import java.util.ArrayList;
import java.util.List;

public class Planet
{
  private String mName;
  private Planet mParentPlanet;
  private List<Planet> mChildrenPlanets = new ArrayList<Planet>();
  
  public Planet(String name)
  {
    mName = name;
  }
  
  public void setParentPlanet(Planet parentPlanet)
  {
    mParentPlanet = parentPlanet;
  }
  
  public void addChildPlanet(Planet childPlanet)
  {
    childPlanet.setParentPlanet(this);
    this.mChildrenPlanets.add(childPlanet);
  }
  
  public Planet getParentPlanet()
  {
    return mParentPlanet;
  }
  
  public List<Planet> getChildrenPlanets()
  {
    return mChildrenPlanets;
  }
  
  public Boolean hasParent()
  {
    return (mParentPlanet != null);
  }
  
  public String getName()
  {
    return mName;
  }
  
  public List<String> getPathToCentreOfMass()
  {
    List<String> path = new ArrayList<String>();
    Planet planet = this;
    while (planet.hasParent())
    {
      planet = planet.getParentPlanet();
      path.add(planet.getName());
    }
    
   return path;
  }
  
  public int getDistanceToCentreOfMass()
  {
    return getPathToCentreOfMass().size();
  }
  
  public int getDistanceToPlanet(Planet planet)
  {
    int distance = 0;
    
    List<String> pathToCentreOfMass = getPathToCentreOfMass();
    List<String> pathToCentreOfMassOtherPlanet = planet.getPathToCentreOfMass();
    
    planetLoop: for (String planetName : pathToCentreOfMass)
    {
      if (pathToCentreOfMassOtherPlanet.contains(planetName))
      {
        // Found intersect planet
        int distance1 = pathToCentreOfMass.indexOf(planetName);
        int distance2 = pathToCentreOfMassOtherPlanet.indexOf(planetName);

        distance = distance1 + distance2;
        
        break planetLoop;
      }
    }
    
    return distance;
  }
}
