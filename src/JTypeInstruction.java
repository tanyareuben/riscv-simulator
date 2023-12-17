public class JTypeInstruction extends Instruction {
    private int opcode;
    private int rd;
    private int immediate;

    public JTypeInstruction(int opcode, int rd, int immediate) {
        this.opcode = opcode;
        this.rd = rd;
        this.immediate = immediate;
    }

    @Override
    public void execute(Memory dataMemory, Registers registers, int programCounter) {
        switch (opcode) {
            case InstructionSet.JAL:
            	int jalRes = programCounter + 4; 
            	programCounter += immediate; 
				break; 

            default:
                throw new UnsupportedOperationException("Unsupported J-Type opcode: " + opcode);
        }
    }

    @Override
    public String toString() {
        // Implement conversion to assembly format
        return "J-Type Instruction: " + opcode + " " + rd + " " + immediate;
    }

    @Override
    public String getAssemblyString() {
        return String.format("%s x%d, %d", getMnemonic(), rd, immediate);
    }

    @Override
    public String getMnemonic() {
        // Replace 0x6F with the actual opcode for J-type instructions
        return InstructionSet.getMnemonic(0x6F);
    }
}
