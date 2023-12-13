import java.util.HashMap;
import java.util.Map;

public class Memory {
    private final int startAddress;
    private final int size;
    private Map<Integer, Integer> data;

    public Memory(int startAddress, int size) {
        this.startAddress = startAddress;
        this.size = size;
        this.data = new HashMap<>();

        initializeMemory();
    }

    public int getStartAddress() {
        return startAddress;
    }

    public int getSize() {
        return size;
    }

    private void initializeMemory() {
        for (int i = startAddress; i < startAddress + size; i++) {
            data.put(i, 0);  // Assuming the initial value at each address is 0
        }
    }

    public void checkAddressValidity(int address) {
        if (address < startAddress || address >= startAddress + size) {
            throw new IllegalArgumentException("Invalid memory address: " + address);
        }
    }

    public int readByte(int address) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        return (word >> offset) & 0xFF;
    }

    public int readHalfword(int address) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        return (word >> offset) & 0xFFFF;
    }

    public int readShort(int address) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        return (short) ((word >> offset) & 0xFFFF);
    }

    public int readWord(int address) {
        checkAddressValidity(address);
        return data.getOrDefault(address, 0);
    }

    public void writeByte(int address, int value) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        word &= ~(0xFF << offset);
        word |= (value & 0xFF) << offset;
        writeWord(address & 0xFFFFFFFC, word);
    }

    public void writeHalfword(int address, int value) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        word &= ~(0xFFFF << offset);
        word |= (value & 0xFFFF) << offset;
        writeWord(address & 0xFFFFFFFC, word);
    }

    public void writeShort(int address, int value) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        word &= ~(0xFFFF << offset);
        word |= (value & 0xFFFF) << offset;
        writeWord(address & 0xFFFFFFFC, word);
    }

    public void writeWord(int address, int value) {
        checkAddressValidity(address);
        data.put(address, value);
    }

    public int getEndAddress() {
        return getStartAddress() + getSize() - 1;
    }

//    public void printMemory() {
//        int currentAddress = getStartAddress();
//        while (currentAddress < getEndAddress()) {
//            int data = readWord(currentAddress);
//            System.out.printf("0x%08X : 0x%08X\n", currentAddress, data);
//            currentAddress += 4;
//        }
//    }
    
    
    public void printMemory() {
        int currentAddress = getStartAddress();
        while (currentAddress < getEndAddress()) {
            int word = readWord(currentAddress);
            System.out.printf("0x%08X :\n", currentAddress);

            for (int i = 3; i >= 0; i--) {
                int byteValue = (word >> (i * 8)) & 0xFF;
                System.out.printf("    Byte %d: 0x%02X\n", i, byteValue);
            }

            currentAddress += 4;
        }
    }

}
