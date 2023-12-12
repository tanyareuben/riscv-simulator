public class ITypeInstruction extends Instruction {
    private int opcode;
    private int rd;
    private int funct3;
    private int rs1;
    private int immediate;

    public ITypeInstruction(int opcode, int rd, int funct3, int rs1, int immediate) {
        this.opcode = opcode;
        this.rd = rd;
        this.funct3 = funct3;
        this.rs1 = rs1;
        this.immediate = immediate;
    }

    @Override
    public void execute(Memory dataMemory, Registers registers) {
        switch (opcode) {
            case InstructionSet.LB:
                // Actual logic for LB operation
                // Example: register[rd] = signExtend(memory[rs1 + immediate]);
                break;

            case InstructionSet.LH:
                // Actual logic for LH operation
                // Example: register[rd] = signExtend(memory[rs1 + immediate]);
                break;

            // Add cases for other I-Type instructions as needed

            default:
                throw new UnsupportedOperationException("Unsupported I-Type opcode: " + opcode);
        }
    }

    @Override
    public String toString() {
        // Implement conversion to assembly format
        return "I-Type Instruction: " + opcode + " " + rd + " " + funct3 + " " + rs1 + " " + immediate;
    }

    @Override
    public String getAssemblyString() {
        return String.format("%s x%d, x%d, %d", getMnemonic(), rd, rs1, immediate);
    }

    @Override
    public String getMnemonic() {
        // Replace 0x13 with the actual opcode for I-type instructions
        return InstructionSet.getMnemonic(0x13);
    }
}
