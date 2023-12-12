public class Registers {
    private int[] registers;
    private int defaultValue;

    public Registers(int numRegisters, int defaultValue) {
        if (numRegisters <= 0) {
            throw new IllegalArgumentException("Number of registers must be greater than 0.");
        }

        this.defaultValue = defaultValue;
        registers = new int[numRegisters];
        resetRegisters();
    }

    private void resetRegisters() {
        for (int i = 0; i < registers.length; i++) {
            registers[i] = defaultValue;
        }
    }

    public void resetRegister(int index) {
        validateIndex(index);
        registers[index] = defaultValue;
    }

    public int readFromRegister(int index) {
        validateIndex(index);
        return registers[index];
    }

    public void writeToRegister(int index, int value) {
        validateIndex(index);
        registers[index] = value;
    }

    public void printRegisters() {
        for (int i = 0; i < registers.length; i++) {
            System.out.println("Register x" + i + ": " + registers[i]);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < registers.length; i++) {
            builder.append("Register x").append(i).append(": ").append(registers[i]).append("\n");
        }
        return builder.toString();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= registers.length) {
            throw new IndexOutOfBoundsException("Invalid register index: " + index);
        }
    }
}
