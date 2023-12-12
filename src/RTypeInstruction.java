public class RTypeInstruction extends Instruction {
    private int opcode;
    private int rd;
    private int funct3;
    private int rs1;
    private int rs2;
    private int funct7;

    public RTypeInstruction(int opcode, int rd, int funct3, int rs1, int rs2, int funct7) {
        this.opcode = opcode;
        this.rd = rd;
        this.funct3 = funct3;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.funct7 = funct7;
    }

    @Override
    public void execute(Memory dataMemory, Registers registers, int programCounter) {
        switch (opcode) {
            case InstructionSet.ADD:
                // Actual logic for ADD operation
                // Example: register[rd] = register[rs1] + register[rs2];
                break;

            case InstructionSet.SUB:
                // Actual logic for SUB operation
                // Example: register[rd] = register[rs1] - register[rs2];
                break;

            // Add cases for other R-Type instructions as needed

            default:
                throw new UnsupportedOperationException("Unsupported R-Type opcode: " + opcode);
        }
    }

    @Override
    public String toString() {
        // Implement conversion to assembly format
        return "R-Type Instruction: " + opcode + " " + rd + " " + funct3 + " " + rs1 + " " + rs2 + " " + funct7;
    }

    @Override
    public String getAssemblyString() {
        return String.format("%s x%d, x%d, x%d", getMnemonic(), rd, rs1, rs2);
    }

    @Override
    public String getMnemonic() {
        // Assuming the opcode is 0x33 for R-type instructions
        return InstructionSet.getMnemonic(0x33);
    }
}
