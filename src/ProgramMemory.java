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
                for (int i = line.length() - 2; i >= 0; i -= 2) {
                    String byteHex = line.substring(i, i + 2);
                    int instructionByte = Integer.parseInt(byteHex, 16);
                    writeByte(address, (byte) instructionByte); // Convert to byte
                    address++;
                }
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
}
