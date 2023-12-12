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
		        int resultADD = registers.getRegister(rs1) + registers.getRegister(rs2);
		        registers.writeToRegister(rd, resultADD);
		        break;
		        
	        case InstructionSet.SUB:
	            int resultSUB = registers.getRegister(rs1) - registers.getRegister(rs2);
	            registers.writeToRegister(rd, resultSUB);
	            break;

	        case InstructionSet.SLL:
	            int shiftAmount = registers.getRegister(rs2) & 0x1F; // Extract lower 5 bits
	            int resultSLL = registers.getRegister(rs1) << shiftAmount;
	            registers.writeToRegister(rd, resultSLL);
	            break;
	            
	        case InstructionSet.SLT:
	            int resultSLT = (registers.getRegister(rs1) < registers.getRegister(rs2)) ? 1 : 0;
	            registers.writeToRegister(rd, resultSLT);
	            break;
	            
	        case InstructionSet.SLTU:
	            int resultSLTU = (Integer.compareUnsigned(registers.getRegister(rs1), registers.getRegister(rs2)) < 0) ? 1 : 0;
	            registers.writeToRegister(rd, resultSLTU);
	            break;
	            
	        case InstructionSet.XOR:
	            int resultXOR = registers.getRegister(rs1) ^ registers.getRegister(rs2);
	            registers.writeToRegister(rd, resultXOR);
	            break;
	            
	        case InstructionSet.SRL:
	            int shiftValue = (registers.getRegister(rs2) & 0x1F); // Extract the shift amount
	            int sourceValue = registers.getRegister(rs1); // Value to be shifted
	            int shiftedResult = sourceValue >>> shiftValue; // Perform logical right shift
	            registers.writeToRegister(rd, shiftedResult); // Store the result in the destination register
	            break;
	            
            case InstructionSet.SRA:
                int sraShiftValue = (registers.getRegister(rs2) & 0x1F); // Extract the shift amount
                int sraSourceValue = registers.getRegister(rs1); // Value to be shifted
                int sraResult = sraSourceValue >> sraShiftValue; // Perform arithmetic right shift
                registers.writeToRegister(rd, sraResult); // Store the result in the destination register
                break;
                
	        case InstructionSet.OR:
	            int orFirstValue = registers.getRegister(rs1); // First value for bitwise OR
	            int orSecondValue = registers.getRegister(rs2); // Second value for bitwise OR
	            int orResult = orFirstValue | orSecondValue; // Perform bitwise OR operation
	            registers.writeToRegister(rd, orResult); // Store the result in the destination register
	            break;
	            
	        case InstructionSet.AND:
	            int andFirstValue = registers.getRegister(rs1); // First value for bitwise AND
	            int andSecondValue = registers.getRegister(rs2); // Second value for bitwise AND
	            int andResult = andFirstValue & andSecondValue; // Perform bitwise AND operation
	            registers.writeToRegister(rd, andResult); // Store the result in the destination register
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
