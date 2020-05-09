package aoc;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * IntCodeComputer. Can perform int code programs
 * 
 * Current opcodes
 * 1  - add
 * 2  - multiply
 * 99 - halt program
 *
 * @author Lucy Barcroft
 *
 */
public class IntCodeComputer 
{
  private static final Logger LOGGER = Logger.getLogger(IntCodeComputer.class.getName());
  
  /**
   * The memory of this computer
   */
  private ArrayList<Integer> mMemory = null;
   
  /**
   * Constructor
   * 
   * @param intCodeProgram Program to initialise in the computer memory
   */
  IntCodeComputer(ArrayList<Integer> intCodeProgram)
  {
    LOGGER.log(Level.FINE, "creating IntCodeComputer with memory: " + intCodeProgram);
    mMemory = intCodeProgram;
  }
    
  //-------------------------------------------------------------------------
  // PRIVATE METHODS
  //-------------------------------------------------------------------------
    
  /**
   * Do the next instruction, as indicated by the instruction pointer. 
   * 
   * @param instructionPointer    Pointer to the instruction to perform - a position in the computer memory
   *                          
   * @return    Pointer to the instruction to perform after this.
   */
  private int doNextOperation(int instructionPointer)
  {
    LOGGER.log(Level.FINE, 
         "Entering doNextOperation: \n"
       + "instructionPointer: " + instructionPointer + "\n"
       + "memory " + mMemory);
  
    // Get the op code for the next instruction.
    int opCode = mMemory.get(instructionPointer);

    // Perform the operation - dependent on the op code.
    switch(opCode)
    {
      case 1: 
      {
        LOGGER.log(Level.FINE, "opCode 1, doing add operation");
        instructionPointer = doAddOperation(instructionPointer);
        break;
      }
      case 2: 
      {
        LOGGER.log(Level.FINE, "opCode 2, doing multiply operation");
        instructionPointer = doMultiplyOperation(instructionPointer);
        break;
      }
      case 3:
      {
        LOGGER.log(Level.FINE, "opCode 3, doing input operation");
        //  instructionPointer = doInputOperation(instructionPointer);
        break;
      }
      case 4:
      {
        LOGGER.log(Level.FINE, "opCode 4, doing output operation");
        //  instructionPointer = doOutputOperation(instructionPointer);
        break;
      }
      case 99:
      {
        LOGGER.log(Level.FINE, "opCode 99, end of program");
        break;
      }
      default:
      {
        LOGGER.log(Level.SEVERE, "unexpected opcode: " + opCode);
        throw new RuntimeException("Unexpected opcode: " + opCode);
      }
    }
  
    LOGGER.log(Level.FINE, "returning instructionPointer " + instructionPointer);
    return instructionPointer;
  }
    
  /**
   * Perform an add operation, for the instruction at the instruction pointer. 
   * Performs the operation on the computer memory and returns a pointer to the instruction to perform after this. 
   * 
   * @param instructionPointer    Pointer to this instruction - a position in the memory
   * 
   * @return    Pointer to the instruction to perform after this.
   */
  private int doAddOperation(int instructionPointer) 
  {
    LOGGER.log(Level.FINE, 
          "Entering doAddOperation: \n"
        + "instructionPointer: " + instructionPointer + "\n"
        + "memory " + mMemory);
   
    int param1 = mMemory.get(instructionPointer + 1);
    int param2 = mMemory.get(instructionPointer + 2);
    int param3 = mMemory.get(instructionPointer + 3);
    LOGGER.log(Level.FINE, "parameters: " + param1 + " " + param2 + " " + param3);
    
    int summedValue = mMemory.get(param1) + mMemory.get(param2);
    LOGGER.log(Level.FINE, "summed value: " + summedValue);
    
    mMemory.set(param3, summedValue);
    instructionPointer = instructionPointer + 4;
    
    LOGGER.log(Level.FINE, "returning instruction pointer: " + instructionPointer);
    return instructionPointer;
  }

  /**
   * Perform a multiply operation, for the instruction at the instruction pointer. 
   * Performs the operation on the computer memory and returns a pointer to the instruction to perform after this. 
   * 
   * 
   * @param instructionPointer    Pointer to this instruction - a position in the memory
   * @param memory                Memory on which to perform the operation. 
   * 
   * @return    Pointer to the instruction to perform after this.
   */
  private int doMultiplyOperation(int instructionPointer)
  {
    LOGGER.log(Level.FINE, 
               "Entering doMultiplyOperation: \n"
             + "instructionPointer: " + instructionPointer + "\n"
             + "memory " + mMemory);
    
    int param1 = mMemory.get(instructionPointer + 1);
    int param2 = mMemory.get(instructionPointer + 2);
    int param3 = mMemory.get(instructionPointer + 3);
    LOGGER.log(Level.FINE, "parameters: " + param1 + " " + param2 + " " + param3);
    
    int multipliedValue = mMemory.get(param1) * mMemory.get(param2);
    LOGGER.log(Level.FINE, "multiplied value: " + multipliedValue);
    
    mMemory.set(param3, multipliedValue);
    instructionPointer = instructionPointer + 4;
    
    LOGGER.log(Level.FINE, "returning instruction pointer: " + instructionPointer);
    return instructionPointer;
  }
  
  //-------------------------------------------------------------------------
  // PUBLIC METHODS
  //-------------------------------------------------------------------------
  
  /**
   * Run the computer.
   * 
   * return   output of the computer. (First value in memory)
   */
  public int run()
  {
    LOGGER.log(Level.FINE, "Entering run computer: ");
    int instructionPointer = 0;
    int opCode = 0;
    while (opCode != 99)
    {
      opCode = mMemory.get(instructionPointer);
      instructionPointer = doNextOperation(instructionPointer);
    }
    
    LOGGER.log(Level.FINE, "Returning value: " + mMemory.get(0));
    return mMemory.get(0);
  }
}
