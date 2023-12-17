import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Program {
    private ProgramMemory programMemory;
    private Memory dataMemory; // Assuming data memory is separate from program memory
    private Registers registers;
    private int programCounter;
    private FileWriter assemblyFileWriter; //to create the .asm file of the program

    private Set<Integer> breakpoints = new HashSet<>();

    private int programSize = 0; //Actual Program Size in words
    private int currentExecutingLine = 0;
    //program state
    private boolean isDone = false;
    
    
    public Program(int instructionsStartAddress, int maxProgramSize, int dataStartAddress, int maxDataSize, Registers registers) {
        programMemory = new ProgramMemory(instructionsStartAddress, maxProgramSize);
        dataMemory = new Memory(dataStartAddress, maxDataSize);
        this.registers = registers;

        try {
            assemblyFileWriter = new FileWriter("assembly.asm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProgramCounter(int address) {
        this.programCounter = address;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public  int getMemoryLocationContent(int address){
        return dataMemory.readWord(address);
    }

    public void setBreakpoint(int address) {
        if (address >= programMemory.getStartAddress() && address < programSize) {
            if (breakpoints.size() < 5) {
                breakpoints.add(address);
                System.out.println("Breakpoint set at address " + Integer.toHexString(address));
            } else {
                System.out.println("Cannot set more than 5 breakpoints.");
            }
        } else {
            System.out.println("Invalid address. Please provide an address within the program memory range.");
        }
    }
    

    private boolean isBreakpoint(int address) {
        return breakpoints.contains(address);
    }

    public void loadProgramFromFile(String filePath) {
    	programSize = programMemory.loadInstructionsFromFile(filePath);
    }

    public void printRegisters() {
        registers.printRegisters();
    }

    //just read and print the next instruction, no need to advance programCounter
    public void printNextInstruction() {
        int instruction = programMemory.getInstructionAt(programCounter);
        Instruction parsedInstruction = InstructionFactory.create(instruction);
        // Get and log the assembly representation before execution
        String assemblyString = parsedInstruction.getAssemblyString();
        System.out.println(assemblyString); 
    }

    public void executeNextInstruction() {

        int instruction = programMemory.getInstructionAt(programCounter);
        Instruction parsedInstruction = InstructionFactory.create(instruction);

        // Get and log the assembly representation before execution
        String assemblyString = parsedInstruction.getAssemblyString();
        System.out.println(assemblyString); //TODO remove after debugging
        logAssembly(assemblyString);

        parsedInstruction.execute(dataMemory, registers, programCounter);
        programCounter += 4; // Increment program counter after successful execution
        currentExecutingLine++;
    	
    }

    public void printProgramMemory() {
        programMemory.printMemory();
    }

    public void printDataMemory() {
        dataMemory.printMemory();
    }

    //runs the program from the instruction from the address the PC is holding
    public void runProgram() {

        while (!isDone) {
            executeNextInstruction();

            if (programCounter >= programMemory.getEndAddress() || currentExecutingLine >= programSize) {
                isDone = true;
                break;
            }

            // Check for breakpoints
            if (isBreakpoint(programCounter)) {
                System.out.println("Breakpoint reached at address " + Integer.toHexString(programCounter));
                break;
            }
        }
        if (isDone) {
            closeAssemblyFile();
        }
    }

    private void logAssembly(String instructionString) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("assembly.asm", true))) {
            writer.println(String.format("PC: %08X - %s", programCounter, instructionString));
        } catch (IOException e) {
            System.err.println("Error writing to assembly file: " + e.getMessage());
        }
    }

    // Close the file writer when done
    public void closeAssemblyFile() {
        try {
            assemblyFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
}
