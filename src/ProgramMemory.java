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

    //return the size of the Program
    public int loadInstructionsFromFile(String filePath) {
        String line;
        int address = getStartAddress();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            while ((line = reader.readLine()) != null) {
                int instructionByte = Integer.parseInt(line, 2);
                writeByte(address, instructionByte);
                address++;
            }
            System.out.println("Program loading complete.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading program: " + e.getMessage());
        }
		return (address/4);
	}

    @Override
    public int getEndAddress() {
        return getStartAddress() + getSize() - 1;
    }

//    public void printMemory() {
//        int currentAddress = getStartAddress();
//        while (currentAddress < getEndAddress()) {
//            int instruction = readWord(currentAddress);
//            System.out.printf("0x%08X : 0x%08X\n", currentAddress, instruction);
//            currentAddress += 4;
//        }
//    }
    
    public void printMemory() {
        int currentAddress = getStartAddress();
        while (currentAddress < getEndAddress()) {
            int word = readWord(currentAddress);
            System.out.printf("0x%08X :\n", currentAddress);

            String binaryInstruction = String.format("%32s", Integer.toBinaryString(word)).replace(' ', '0');
            System.out.println("Binary Instruction: " + binaryInstruction);
            
            for (int i = 3; i >= 0; i--) {
                int byteValue = (word >> (i * 8)) & 0xFF;
                String binaryString = String.format("%8s", Integer.toBinaryString(byteValue)).replace(' ', '0');
                System.out.printf("    Byte %d: %s\n", i, binaryString);
            }

            currentAddress += 4;
        }
    }
    
}
