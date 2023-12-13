public class InstructionFactory {
    public static Instruction create(int binaryInstruction) {
//        int opcode = parseOpcode(binaryInstruction);

        int opcode = (binaryInstruction >> 0) & 0x7F; // Extract opcode
        int rd = (binaryInstruction >> 7) & 0x1F; // Extract destination register
        int funct3 = (binaryInstruction >> 12) & 0x07; // Extract function code 3
        int funct7 = (binaryInstruction >> 25) & 0x7F; // Extract function code 7
        int rs1 = (binaryInstruction >> 15) & 0x1F; // Extract source register 1
        int rs2 = (binaryInstruction >> 20) & 0x1F; // Extract source register 2
        int imm = ((binaryInstruction >> 20) & 0xFFF) | ((binaryInstruction & 0x80000000) >> 11); // Extract immediate value
		
        String binaryString = String.format("%32s", Integer.toBinaryString(binaryInstruction)).replace(' ', '0');

        parseAndPrintInstruction(binaryInstruction);
        
        switch (opcode) {
            case InstructionSet.LUI:
            case InstructionSet.AUIPC:
                return createUTypeInstruction(binaryInstruction);

            case InstructionSet.JAL:
                return createJTypeInstruction(binaryInstruction);

            case InstructionSet.JALR:
            case InstructionSet.LB:
            case InstructionSet.LH:
            case InstructionSet.LW:
            case InstructionSet.LBU:
            case InstructionSet.LHU:
                return createITypeInstruction(binaryInstruction);

            case InstructionSet.BEQ:
            case InstructionSet.BNE:
            case InstructionSet.BLT:
            case InstructionSet.BGE:
            case InstructionSet.BLTU:
            case InstructionSet.BGEU:
            case InstructionSet.ADDI:
                return createITypeInstruction(binaryInstruction);

            case InstructionSet.SB:
            case InstructionSet.SH:
            case InstructionSet.SW:
                return createSTypeInstruction(binaryInstruction);

            case InstructionSet.ADD:
            case InstructionSet.SUB:
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
    
    private static void parseAndPrintInstruction(int instruction) {

        // Extract opcode (bits 0-6)
        int opcode = (instruction >> 26) & 0x7F;

        // Extract rd (bits 7-11)
        int rd = (instruction >> 20) & 0x1F;

        // Extract funct3 (bits 12-14)
        int funct3 = (instruction >> 12) & 0x7;

        // Extract funct7 (bits 15-20) for R-type instructions or imm[11:0] for I-type instructions
        int funct7 = (instruction >> 15) & 0x7F;

        // Extract rs1 (bits 15-19)
        int rs1 = (instruction >> 15) & 0x1F;

        // Extract rs2 (bits 20-24)
        int rs2 = (instruction >> 20) & 0x1F;

        // Extract immediate value (imm[11:0]) for I-type instructions
        int imm = instruction & 0xFFFFF;

        // Print extracted information
        System.out.println("Opcode: " + opcode);
        System.out.println("rd: " + rd);
        System.out.println("funct3: " + funct3);
        System.out.println("funct7: " + funct7);
        System.out.println("rs1: " + rs1);
        System.out.println("rs2: " + rs2);
        System.out.println("imm: " + imm);
    }
    
}
