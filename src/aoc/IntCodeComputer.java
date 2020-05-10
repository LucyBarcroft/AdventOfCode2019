package aoc;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * IntCodeComputer. Can perform int code programs
 * 
 * Current opcodes
 * 1  - add
 * 2  - multiply
 * 3  - input
 * 4  - output
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
  
  private int mOutput = 0;
   
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
   * Do the next instruction, as indicated by the current instruction pointer. 
   * 
   * @param instructionPointer    Pointer to the instruction to perform 
   *                              - a position in the computer memory
   *                          
   * @return    Pointer to the instruction to perform after this.
   */
  private int doNextOperation(int instructionPointer)
  {
    LOGGER.setLevel(Level.FINE);
    LOGGER.log(Level.FINE,
               "Entering doNextOperation: \n"
             + "instructionPointer: " + instructionPointer + "\n"
             + "memory " + mMemory);
  
    // Get the op code for the next instruction.
    int initialValue = mMemory.get(instructionPointer);
    int opCode = getTensAndOnes(initialValue);

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
        instructionPointer = doInputOperation(instructionPointer);
        break;
      }
      case 4:
      {
        LOGGER.log(Level.FINE, "opCode 4, doing output operation");
        instructionPointer = doOutputOperation(instructionPointer);
        break;
      }
      case 5:
      {
        LOGGER.log(Level.FINE, "opCode 5, doing jump-if-true operation");
        instructionPointer = doJumpIfTrueOperation(instructionPointer);
        break;
      }
      case 6:
      {
        LOGGER.log(Level.FINE, "opCode 6, doing jump-if-false operation");
        instructionPointer = doJumpIfFalseOperation(instructionPointer);
        break;
      }
      case 7:
      {
        LOGGER.log(Level.FINE, "opCode 7, doing less-than operation");
        instructionPointer = doLessThanOperation(instructionPointer);
        break;
      }
      case 8:
      {
        LOGGER.log(Level.FINE, "opCode 8, doing equals operation");
        instructionPointer = doEqualsOperation(instructionPointer);
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
    LOGGER.setLevel(Level.FINE);
    LOGGER.log(Level.FINE, 
               "Entering doAddOperation: \n"
             + "instructionPointer: " + instructionPointer + "\n"
             + "memory " + mMemory);
   
    
    int initialValue = mMemory.get(instructionPointer);
    int paramMode1 = getHundreds(initialValue);
    int paramMode2 = getThousands(initialValue);
  //  System.out.println(paramMode1 + " " + paramMode2);
    
    int value1 = getValue(instructionPointer + 1, paramMode1);
    int value2 = getValue(instructionPointer + 2, paramMode2);

    int param3 = mMemory.get(instructionPointer + 3);
  //  System.out.println("values: " + value1 + " " + value2 + " " + param3);
    
    int summedValue = value1 + value2;
    LOGGER.log(Level.FINE, "summed value: " + summedValue);
    
    mMemory.set(param3, summedValue);
    instructionPointer += 4;
    
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
    
    int initialValue = mMemory.get(instructionPointer);
    int paramMode1 = getHundreds(initialValue);
    int paramMode2 = getThousands(initialValue);
    
    int value1 = getValue(instructionPointer + 1, paramMode1);
    int value2 = getValue(instructionPointer + 2, paramMode2);
    int param3 = mMemory.get(instructionPointer + 3);
    LOGGER.log(Level.FINE, "parameters: " + value1 + " " + value2 + " " + param3);
    
    int multipliedValue = value1 * value2;
    LOGGER.log(Level.FINE, "multiplied value: " + multipliedValue);
     
    mMemory.set(param3, multipliedValue);
    instructionPointer += 4;
    
    LOGGER.log(Level.FINE, "returning instruction pointer: " + instructionPointer);
    return instructionPointer;
  }
  
  /**
   * Do input operation - ask for input from the user and store it to the
   * relevant place in memory
   * 
   * @param instructionPointer
   * @return
   */
  private int doInputOperation(int instructionPointer)
  {
    int param1 = mMemory.get(instructionPointer + 1);
    
    Scanner input = new Scanner(System.in);
    
    System.out.println("Enter a value: ");
    int value = input.nextInt();
    System.out.println("You entered " + value);
    input.close();
    
    mMemory.set(param1, value);
    
    instructionPointer += 2;
    return instructionPointer;
  }
  
  /**
   * Do output operation - output relevant value from memory to member variable
   * 
   * @param instructionPointer
   * @return
   */
  private int doOutputOperation(int instructionPointer)
  {
    int initialValue = mMemory.get(instructionPointer);
    int paramMode1 = getHundreds(initialValue);
    
    int value1 = getValue(instructionPointer + 1, paramMode1);
    
    mOutput = value1;

    instructionPointer += 2;

    return instructionPointer;
  }
  
  /**
   * Jump if true:
   * If the value indicated by parameter 1 is non-zero, then jump to position indicated 
   * by parameter 2. Else do nothing
   * 
   * @param instructionPointer     pointer to the current instruction in memory.
   * 
   * @return     pointer to instruction after this one
   */
  private int doJumpIfTrueOperation(int instructionPointer)
  {
    int initialValue = mMemory.get(instructionPointer);
    int param1Mode = getHundreds(initialValue);
    int value1 = getValue(instructionPointer + 1, param1Mode);
    
    if (value1 != 0)
    {
      int paramMode2 = getThousands(initialValue);
      int value2 = getValue(instructionPointer + 2, paramMode2);
      instructionPointer = value2;
    }
    else
    {
      instructionPointer += 3;
    }
    
    return instructionPointer;
  }
  
  /**
   * Jump if false:
   * If the value indicated by parameter 1 is zero, then jump to position indicated 
   * by parameter 2. Else do nothing
   * 
   * @param instructionPointer     pointer to the current instruction in memory.
   * 
   * @return     pointer to instruction after this one
   */
  private int doJumpIfFalseOperation(int instructionPointer)
  {
    int initialValue = mMemory.get(instructionPointer);
    int param1Mode = getHundreds(initialValue);
    int value1 = getValue(instructionPointer + 1, param1Mode);
    
    if (value1 == 0)
    {
      int paramMode2 = getThousands(initialValue);
      int value2 = getValue(instructionPointer + 2, paramMode2);
      instructionPointer = value2;
    }
    else
    {
      instructionPointer += 3;
    }
    
    return instructionPointer;
  }
  
  /**
   * Less than:
   * if the value indicated by parameter 1 is less than the value indicated by parameter 2, 
   * then write 1 to location indicated by parameter 3. Else write 0. 
   * 
   * @param instructionPointer     pointer to the current instruction in memory.
   * 
   * @return     pointer to instruction after this one
   */
  private int doLessThanOperation(int instructionPointer)
  {
    int initialValue = mMemory.get(instructionPointer);
    int param1Mode = getHundreds(initialValue);
    int param2Mode = getThousands(initialValue);
    int value1 = getValue(instructionPointer + 1, param1Mode);
    int value2 = getValue(instructionPointer + 2, param2Mode);
    int param3 = mMemory.get(instructionPointer + 3);
    
    if (value1 < value2)
    {
      mMemory.set(param3, 1);
    }
    else
    {
      mMemory.set(param3, 0);
    }
    
    instructionPointer += 4;
    return instructionPointer;
  }
  
  /**
   * Equals:
   * if the value indicated by parameter 1 is equal to the value indicated by parameter 2, 
   * then write 1 to location indicated by parameter 3. Else write 0. 
   * 
   * @param instructionPointer     pointer to the current instruction in memory.
   * 
   * @return     pointer to instruction after this one
   */
  private int doEqualsOperation(int instructionPointer)
  {
    int initialValue = mMemory.get(instructionPointer);
    int param1Mode = getHundreds(initialValue);
    int param2Mode = getThousands(initialValue);
    int value1 = getValue(instructionPointer + 1, param1Mode);
    int value2 = getValue(instructionPointer + 2, param2Mode);
    int param3 = mMemory.get(instructionPointer + 3);
    
    if (value1 == value2)
    {
      mMemory.set(param3, 1);
    }
    else
    {
      mMemory.set(param3, 0);
    }
    
    instructionPointer += 4;
    return instructionPointer;
  }
  
  /**
   * Get value at position pointed to by pointer, using parameter mode specified. 
   * 
   * @param pointer     place in memory to read
   * @param paramMode   parameter mode to use
   * 
   * @return   value
   */
  private int getValue(int pointer, int paramMode)
  {
    int value; 
    
    switch (paramMode)
    {
      case 0:
      {
        // Position Mode
        value = mMemory.get(mMemory.get(pointer));
        break;
        
      }
      case 1:
      {
        // Immediate Mode
        value = mMemory.get(pointer);
        break;
      }
      default:
      {
        throw new RuntimeException("Invalid paramMode: " + paramMode);
      }
    }
    
    return value;
  }
  
  /**
   * get tens and ones column from specified integer
   * 
   * @param value    input value
   *     
   * @return  just tens and ones value 
   */
  private int getTensAndOnes(int value)
  {
    return (value % 100);
  }
  
  /**
   * get the value of the hundreds column of the specified integer
   * 
   * @param value    input value
   *     
   * @return  hundreds column value 
   */
  private int getHundreds(int value)
  {
    return (value % 1000) / 100; 
  }
  
  /**
   * get the value of the thousands column of the specified integer
   * 
   * @param value    input value
   *     
   * @return    thousands column value 
   */
  private int getThousands(int value)
  {
    return (value % 10000) / 1000;
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
      int initialValue = mMemory.get(instructionPointer);
      opCode = getTensAndOnes(initialValue);
    //  if (opCode != 99 && mOutput != 0)
    //  {
    //    throw new RuntimeException("Output not 0: " + mOutput);
    //  }
      instructionPointer = doNextOperation(instructionPointer);
      System.out.println(opCode + " " + instructionPointer);
    }
    
    LOGGER.log(Level.FINE, "Returning value: " + mMemory.get(0));
    System.out.println(mOutput);
    return mMemory.get(0);
  }
}
