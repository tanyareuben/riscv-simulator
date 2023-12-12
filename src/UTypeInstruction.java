
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
    public void execute(Memory dataMemory, Registers registers, int programCounter) {
        switch (opcode) {
	        case InstructionSet.LUI: // Load Upper Immediate
	            int luiRes = immediate << 12; // Shift immediate value left by 12 bits
	            registers.writeToRegister(rd, luiRes); // Write the result to the specified destination register
	            break;

	        case InstructionSet.AUIPC: // Add Upper Immediate to PC
	            int auipcRes = programCounter + (immediate << 12); // Add immediate value shifted left by 12 bits to the program counter
	            registers.writeToRegister(rd, auipcRes); // Write the result to the specified destination register
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
