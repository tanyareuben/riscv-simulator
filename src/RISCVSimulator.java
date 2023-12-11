
public class RISCVSimulator {

    private static final int INSTRUCTION_MEMORY_START = 0x00000000;
    private static final int DATA_MEMORY_START = 0x80000000;
    private static final int MEMORY_SIZE = 1024;  // Adjust the size based on your requirements

    private ProgramMemory instructionMemory;
    private Memory dataMemory;

    public RISCVSimulator() {
        instructionMemory = new ProgramMemory(INSTRUCTION_MEMORY_START, MEMORY_SIZE);
        dataMemory = new Memory(DATA_MEMORY_START, MEMORY_SIZE);

        // ... other initializations ...
    }

    //load the program to the Instruction memory
    public void loadProgram(String filePath) {
        instructionMemory.loadInstructionsFromFile(filePath);
    }
    
}
