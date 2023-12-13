import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ProgramMemory extends Memory {

    public ProgramMemory(int startAddress, int size) {
        super(startAddress, size);
    }

    public int getNextInstruction(int programCounter) {
        checkAddressValidity(programCounter);
        int instruction = readWord(programCounter);
        programCounter += 4;
        return instruction;
    }

    public int getInstructionAt(int address) {
        checkAddressValidity(address);
        return readWord(address);
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

    @Override
    public int getEndAddress() {
        return getStartAddress() + getSize() - 1;
    }

    public void printMemory() {
        int currentAddress = getStartAddress();
        while (currentAddress < getEndAddress()) {
            int instruction = readWord(currentAddress);
            System.out.printf("0x%08X : 0x%08X\n", currentAddress, instruction);
            currentAddress += 4;
        }
    }

    // Override readWord and writeWord methods to use Byte values

    @Override
    public int readWord(int address) {
        checkAddressValidity(address);
        int word = 0;
        for (int i = 0; i < 4; i++) {
            word |= (readByte(address + i) & 0xFF) << (i * 8);
        }
        return word;
    }

    @Override
    public void writeWord(int address, int value) {
        checkAddressValidity(address);
        for (int i = 0; i < 4; i++) {
            writeByte(address + i, (byte) ((value >> (i * 8)) & 0xFF));
        }
    }
}
