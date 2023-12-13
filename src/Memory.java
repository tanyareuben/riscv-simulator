import java.util.HashMap;
import java.util.Map;

public class Memory {
    private final int startAddress;
    private final int size;
    private Map<Integer, Byte> data;

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
        for (int i = startAddress; i < startAddress + size; i += 4) {
            data.put(i, 0);  // Assuming the initial value at each address is 0
        }
    }

    public void checkAddressValidity(int address) {
        if (address < startAddress || address >= startAddress + size || address % 4 != 0) {
            throw new IllegalArgumentException("Invalid memory address: " + address);
        }
    }

    public byte readByte(int address) {
        checkAddressValidity(address);
        return data.getOrDefault(address, (byte) 0);
    }

    public short readHalfword(int address) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        return (short) ((word >> offset) & 0xFFFF);
    }

    public short readShort(int address) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        return (short) ((word >> offset) & 0xFFFF);
    }

    public int readWord(int address) {
        checkAddressValidity(address);
        return (data.getOrDefault(address, (byte) 0) & 0xFF) |
               ((data.getOrDefault(address + 1, (byte) 0) & 0xFF) << 8) |
               ((data.getOrDefault(address + 2, (byte) 0) & 0xFF) << 16) |
               ((data.getOrDefault(address + 3, (byte) 0) & 0xFF) << 24);
    }

    public void writeByte(int address, byte value) {
        checkAddressValidity(address);
        data.put(address, value);
    }

    public void writeHalfword(int address, short value) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        word &= ~(0xFFFF << offset);
        word |= (value & 0xFFFF) << offset;
        writeWord(address & 0xFFFFFFFC, word);
    }
    
    public void writeShort(int address, short value) {
        checkAddressValidity(address);
        int word = readWord(address & 0xFFFFFFFC);
        int offset = (address % 4) * 8;
        word &= ~(0xFFFF << offset);
        word |= (value & 0xFFFF) << offset;
        writeWord(address & 0xFFFFFFFC, word);
    }

    public void writeWord(int address, int value) {
        checkAddressValidity(address);

        for (int i = 0; i < 4; i++) {
            int byteValue = (value >> (i * 8)) & 0xFF;
            data.put(address + i, (byte) byteValue);
        }
    }



    public int getEndAddress() {
        return getStartAddress() + getSize() - 1;
    }

    public void printMemory() {
        int currentAddress = getStartAddress();
        while (currentAddress < getEndAddress()) {
            int data = readWord(currentAddress);
            System.out.printf("0x%08X : 0x%08X\n", currentAddress, data);
            currentAddress += 4;
        }
    }
}
