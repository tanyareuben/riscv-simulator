public class InstructionFactory {
    public static Instruction create(int binaryInstruction) {
        int opcode = parseOpcode(binaryInstruction);
		
        
        switch (opcode) {
            case InstructionSet.LUI:
            case InstructionSet.AUIPC:
                return createUTypeInstruction(binaryInstruction);

            case InstructionSet.JAL:
            case InstructionSet.JALR:                
            	return createJTypeInstruction(binaryInstruction);


            case InstructionSet.LB:
            case InstructionSet.LH:
            case InstructionSet.LW:
            case InstructionSet.LBU:
            case InstructionSet.LHU:
            case InstructionSet.ADDI:
            case InstructionSet.SLTI:
            case InstructionSet.SLTIU:
            case InstructionSet.XORI:
            case InstructionSet.ORI:
            case InstructionSet.ANDI:
            case InstructionSet.SLLI:
            case InstructionSet.SRLI:
            case InstructionSet.SRAI:
                return createITypeInstruction(binaryInstruction);
                
            case InstructionSet.BEQ:
            case InstructionSet.BNE:
            case InstructionSet.BLT:
            case InstructionSet.BGE:
            case InstructionSet.BLTU:
            case InstructionSet.BGEU:
                return createBTypeInstruction(binaryInstruction);

            case InstructionSet.SB:
            case InstructionSet.SH:
            case InstructionSet.SW:
                return createSTypeInstruction(binaryInstruction);

            case InstructionSet.ADD:
            case InstructionSet.SUB:
            case InstructionSet.SLL:
            case InstructionSet.SLT:
            case InstructionSet.SLTU:
            case InstructionSet.XOR:
            case InstructionSet.SRL:
            case InstructionSet.SRA:
            case InstructionSet.OR:
            case InstructionSet.AND:
                return createRTypeInstruction(binaryInstruction);

            default:
                throw new UnsupportedOperationException("Unsupported opcode: " + opcode);
        }
    }

    private static int parseOpcode(int instruction) {
        //return instruction & 0x7F;
    	return (instruction >> 0) & 0x7F; // Extract opcode
    }

    private static Instruction createUTypeInstruction(int binaryInstruction) {
        
		int opcode = (binaryInstruction >> 0) & 0x7F; // Extract opcode
        int rd = (binaryInstruction >> 7) & 0x1F; // Extract destination register
        int funct3 = (binaryInstruction >> 12) & 0x07; // Extract function code 3
        int funct7 = (binaryInstruction >> 25) & 0x7F; // Extract function code 7
        int rs1 = (binaryInstruction >> 15) & 0x1F; // Extract source register 1
        int rs2 = (binaryInstruction >> 20) & 0x1F; // Extract source register 2
        int imm = ((binaryInstruction >> 20) & 0xFFF) | ((binaryInstruction & 0x80000000) >> 11); // Extract immediate value


        return new UTypeInstruction(opcode, rd, imm);
    }

    private static Instruction createJTypeInstruction(int binaryInstruction) {
		int opcode = (binaryInstruction >> 0) & 0x7F; // Extract opcode
        int rd = (binaryInstruction >> 7) & 0x1F; // Extract destination register
        int funct3 = (binaryInstruction >> 12) & 0x07; // Extract function code 3
        int funct7 = (binaryInstruction >> 25) & 0x7F; // Extract function code 7
        int rs1 = (binaryInstruction >> 15) & 0x1F; // Extract source register 1
        int rs2 = (binaryInstruction >> 20) & 0x1F; // Extract source register 2
        int imm = ((binaryInstruction >> 20) & 0xFFF) | ((binaryInstruction & 0x80000000) >> 11); // Extract immediate value

        return new JTypeInstruction(opcode, rd, imm);
    }

    private static Instruction createITypeInstruction(int binaryInstruction) {
		int opcode = (binaryInstruction >> 0) & 0x7F; // Extract opcode
        int rd = (binaryInstruction >> 7) & 0x1F; // Extract destination register
        int funct3 = (binaryInstruction >> 12) & 0x07; // Extract function code 3
        int funct7 = (binaryInstruction >> 25) & 0x7F; // Extract function code 7
        int rs1 = (binaryInstruction >> 15) & 0x1F; // Extract source register 1
        int rs2 = (binaryInstruction >> 20) & 0x1F; // Extract source register 2
        int imm = ((binaryInstruction >> 20) & 0xFFF) | ((binaryInstruction & 0x80000000) >> 11); // Extract immediate value

        return new ITypeInstruction(opcode, rd, funct3, rs1, rs2, imm);
    }
    
    private static Instruction createBTypeInstruction(int binaryInstruction) {
		int opcode = (binaryInstruction >> 0) & 0x7F; // Extract opcode
        int rd = (binaryInstruction >> 7) & 0x1F; // Extract destination register
        int funct3 = (binaryInstruction >> 12) & 0x07; // Extract function code 3
        int funct7 = (binaryInstruction >> 25) & 0x7F; // Extract function code 7
        int rs1 = (binaryInstruction >> 15) & 0x1F; // Extract source register 1
        int rs2 = (binaryInstruction >> 20) & 0x1F; // Extract source register 2
        int imm = ((binaryInstruction >> 20) & 0xFFF) | ((binaryInstruction & 0x80000000) >> 11); // Extract immediate value
        
        return new BTypeInstruction(opcode, funct3, rs1, rs2, funct7, imm);
    }

    private static Instruction createSTypeInstruction(int binaryInstruction) {
		int opcode = (binaryInstruction >> 0) & 0x7F; // Extract opcode
        int rd = (binaryInstruction >> 7) & 0x1F; // Extract destination register
        int funct3 = (binaryInstruction >> 12) & 0x07; // Extract function code 3
        int funct7 = (binaryInstruction >> 25) & 0x7F; // Extract function code 7
        int rs1 = (binaryInstruction >> 15) & 0x1F; // Extract source register 1
        int rs2 = (binaryInstruction >> 20) & 0x1F; // Extract source register 2
        int imm = ((binaryInstruction >> 20) & 0xFFF) | ((binaryInstruction & 0x80000000) >> 11); // Extract immediate value

        return new STypeInstruction(opcode, funct3, rs1, rs2, imm);
    }

    private static Instruction createRTypeInstruction(int binaryInstruction) {
		int opcode = (binaryInstruction >> 0) & 0x7F; // Extract opcode
        int rd = (binaryInstruction >> 7) & 0x1F; // Extract destination register
        int funct3 = (binaryInstruction >> 12) & 0x07; // Extract function code 3
        int funct7 = (binaryInstruction >> 25) & 0x7F; // Extract function code 7
        int rs1 = (binaryInstruction >> 15) & 0x1F; // Extract source register 1
        int rs2 = (binaryInstruction >> 20) & 0x1F; // Extract source register 2
        int imm = ((binaryInstruction >> 20) & 0xFFF) | ((binaryInstruction & 0x80000000) >> 11); // Extract immediate value

        return new RTypeInstruction(opcode, rd, funct3, rs1, rs2, funct7);
    }
    
    
}
