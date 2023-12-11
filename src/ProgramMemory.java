import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProgramMemory extends Memory {
    private int programCounter;

    public ProgramMemory(int startAddress, int size) {
        super(startAddress, size);
        this.programCounter = startAddress;
    }

    public int getNextInstruction() {
        checkAddressValidity(programCounter);
        int instruction = readWord(programCounter);
        programCounter += 4;
        return instruction;
    }

    public int getInstructionAt(int address) {
        checkAddressValidity(address);
        return readWord(address);
    }

    public void setProgramCounter(int address) {
        checkAddressValidity(address);
        this.programCounter = address;
    }
    public void loadInstructionsFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int address = getStartAddress();
            while ((line = reader.readLine()) != null) {
                int instruction = Integer.parseInt(line, 16);
                writeWord(address, instruction);
                address += 4;
            }
            System.out.println("Program loading complete.");
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading program: " + e.getMessage());
        }
    }
}
