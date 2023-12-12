
public class UTypeInstruction extends Instruction {
    
    private int opcode;
    private int rd;
    private int immediate;

    public UTypeInstruction(int opcode, int rd, int immediate) {
        this.opcode = opcode;
        this.rd = rd;
        this.immediate = immediate;
    }

    @Override
    public void execute(Memory dataMemory, Registers registers) {
        switch (opcode) {
            case InstructionSet.LUI:
                // Actual logic for LUI operation
                // Example: register[rd] = immediate;
                break;

            case InstructionSet.AUIPC:
                // Actual logic for AUIPC operation
                // Example: register[rd] = pc + immediate;
                break;

            // Add cases for other U-Type instructions as needed

            default:
                throw new UnsupportedOperationException("Unsupported U-Type opcode: " + opcode);
        }
    }

    @Override
    public String toString() {
        // Implement conversion to assembly format
        return "U-Type Instruction: " + opcode + " " + rd + " " + immediate;
    }

    @Override
    public String getAssemblyString() {
        return String.format("%s x%d, %d", getMnemonic(), rd, immediate);
    }

    @Override
    public String getMnemonic() {
        // Replace 0x17 with the actual opcode for U-type instructions
        return InstructionSet.getMnemonic(0x17);
    }
}
