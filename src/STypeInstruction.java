
public class STypeInstruction extends Instruction {
    private int opcode;
    private int funct3;
    private int rs1;
    private int rs2;
    private int immediate;

    public STypeInstruction(int opcode, int funct3, int rs1, int rs2, int immediate) {
        this.opcode = opcode;
        this.funct3 = funct3;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.immediate = immediate;
    }

    @Override
    public void execute(Memory dataMemory, Registers registers, int programCounter) {
        switch (opcode) {
	        case InstructionSet.SW: // Store word
	            int swAddress = registers.getRegister(rs1) + immediate; 
	            int swValue = registers.getRegister(rs2); 
	            dataMemory.writeWord(swAddress, swValue); 
	            break;
                
            case InstructionSet.SB: // Store byte
                int sbAddress = registers.getRegister(rs1) + immediate; 
                byte sbValue = (byte) (registers.getRegister(rs2) & 0xFF); 
                dataMemory.writeByte(sbAddress, sbValue); 
                break;
                
            case InstructionSet.SH: // Store halfword
                int shAddress = registers.getRegister(rs1) + immediate; 
                short shValue = (short) (registers.getRegister(rs2) & 0xFFFF); 
                dataMemory.writeHalfword(shAddress, shValue);
                break;
                
            

            // Add cases for other S-Type instructions as needed

            default:
                throw new UnsupportedOperationException("Unsupported S-Type opcode: " + opcode);
        }
    }

    @Override
    public String toString() {
        // Implement conversion to assembly format
        return "S-Type Instruction: " + opcode + " " + funct3 + " " + rs1 + " " + rs2 + " " + immediate;
    }

    @Override
    public String getAssemblyString() {
        return String.format("%s x%d, %d(x%d)", getMnemonic(), rs2, immediate, rs1);
    }

    @Override
    public String getMnemonic() {
        // Replace 0x23 with the actual opcode for S-type instructions
        return InstructionSet.getMnemonic(0x23);
    }
}