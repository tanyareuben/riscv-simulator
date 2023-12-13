public class InstructionFactory {
    public static Instruction create(int binaryInstruction) {
        int opcode = parseOpcode(binaryInstruction);

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
                throw new UnsupportedOperationException("Unsupported opcode: " + InstructionSet.getMnemonic(opcode));
        }
    }

    private static int parseOpcode(int instruction) {
        return instruction & 0x7F;
    }

    private static Instruction createUTypeInstruction(int binaryInstruction) {
        int opcode = parseOpcode(binaryInstruction);
        int rd = (binaryInstruction >> 7) & 0x1F;
        int immediate = binaryInstruction & 0xFFFFF000;

        return new UTypeInstruction(opcode, rd, immediate);
    }

    private static Instruction createJTypeInstruction(int binaryInstruction) {
        int opcode = parseOpcode(binaryInstruction);
        int rd = (binaryInstruction >> 7) & 0x1F;
        int immediate = ((binaryInstruction >> 12) & 0xFF800) | ((binaryInstruction >> 11) & 0x1000) | (binaryInstruction & 0xFF);

        return new JTypeInstruction(opcode, rd, immediate);
    }

    private static Instruction createITypeInstruction(int binaryInstruction) {
        int opcode = parseOpcode(binaryInstruction);
        int rd = (binaryInstruction >> 7) & 0x1F;
        int funct3 = (binaryInstruction >> 12) & 0x7;
        int rs1 = (binaryInstruction >> 15) & 0x1F;
        int rs2 = (binaryInstruction >> 20) & 0x1F;
        int immediate = (int) (binaryInstruction >> 20);

        return new ITypeInstruction(opcode, rd, funct3, rs1, rs2, immediate);
    }

    private static Instruction createSTypeInstruction(int binaryInstruction) {
        int opcode = parseOpcode(binaryInstruction);
        int funct3 = (binaryInstruction >> 12) & 0x7;
        int rs1 = (binaryInstruction >> 15) & 0x1F;
        int rs2 = (binaryInstruction >> 20) & 0x1F;
        int immediate = ((binaryInstruction >> 7) & 0x1F) | ((binaryInstruction >> 25) & 0xFE0);

        return new STypeInstruction(opcode, funct3, rs1, rs2, immediate);
    }

    private static Instruction createRTypeInstruction(int binaryInstruction) {
        int opcode = parseOpcode(binaryInstruction);
        int rd = (binaryInstruction >> 7) & 0x1F;
        int funct3 = (binaryInstruction >> 12) & 0x7;
        int rs1 = (binaryInstruction >> 15) & 0x1F;
        int rs2 = (binaryInstruction >> 20) & 0x1F;
        int funct7 = (binaryInstruction >> 25) & 0x7F;

        return new RTypeInstruction(opcode, rd, funct3, rs1, rs2, funct7);
    }
}
