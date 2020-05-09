package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Advent Of Code Day 2, part 1 and part 2
 * 
 * @author lebar
 */
public class Day2 
{
	private static final Logger LOGGER = Logger.getLogger(Day2.class.getName());
    private static final ArrayList<Integer> S_INPUT_LIST = new ArrayList<Integer>(Arrays.asList(1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,5,19,23,1,23,6,27,1,5,27,31,1,31,6,35,1,9,35,39,2,10,39,43,1,43,6,47,2,6,47,51,1,5,51,55,1,55,13,59,1,59,10,63,2,10,63,67,1,9,67,71,2,6,71,75,1,5,75,79,2,79,13,83,1,83,5,87,1,87,9,91,1,5,91,95,1,5,95,99,1,99,13,103,1,10,103,107,1,107,9,111,1,6,111,115,2,115,13,119,1,10,119,123,2,123,6,127,1,5,127,131,1,5,131,135,1,135,6,139,2,139,10,143,2,143,9,147,1,147,6,151,1,151,13,155,2,155,9,159,1,6,159,163,1,5,163,167,1,5,167,171,1,10,171,175,1,13,175,179,1,179,2,183,1,9,183,0,99,2,14,0,0));
    private static final int S_NOUN = 12;
    private static final int S_VERB = 2;
    private static final int S_OUTPUT = 19690720;
	
    /**
     * This first does Part 1 - calculates the output of the computer with 12 and 2 as noun and verb
     * It then does Part 2 - calculates the noun and verb needed to get the required output
     * @param args
     */
	public static void main(String[] args) 
	{
		LOGGER.setLevel(Level.INFO);
		ArrayList<Integer> intCodeProgram = initialiseIntCodeProgram(S_NOUN, S_VERB);
		IntCodeComputer computer = new IntCodeComputer(intCodeProgram);
		int output = computer.run();
		LOGGER.log(Level.INFO, "output: " + output);
		
		int noun = 0;
		int verb = 0;
		nounloop: while (noun <= 99)
		{
			verbloop: while (verb <= 99)
			{
				intCodeProgram = initialiseIntCodeProgram(noun, verb);
				computer = new IntCodeComputer(intCodeProgram);
				output = computer.run();

				if (output > S_OUTPUT)
				{
					break verbloop;
				}
				else if (output == S_OUTPUT)
				{
					break nounloop;
				}
				verb += 1;
			}
		    verb = 0;
		    noun += 1;
		}
		
		output = 100 * noun + verb;
		LOGGER.log(Level.INFO, 
				   "noun: " + noun + "\n"
		         + "verb: " + verb + "\n"
				 + "output: " + output);
	}
	
	/**
	 * Initialise an int code program from the input list with noun and verb at
	 * positions 1 and 2 respectively.
	 * 
	 * @param noun 
	 * @param verb
	 * @return intCodeProgram
	 */
	private static ArrayList<Integer> initialiseIntCodeProgram(int noun, int verb)
	{
		LOGGER.log(Level.FINE,
				   "Entering intialiseIntCodeProgram: \n"
				 + "noun: " + noun + "\n"
				 + "verb: " + verb);
		ArrayList<Integer> intCodeProgram = (ArrayList<Integer>) S_INPUT_LIST.clone();
		intCodeProgram.set(1, noun);
		intCodeProgram.set(2, verb);
		
		LOGGER.log(Level.FINE,
				   "returning intCodeProgram: " + intCodeProgram);
		return intCodeProgram;
	}
}
