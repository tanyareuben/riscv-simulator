public class ITypeInstruction extends Instruction {
    private int opcode;
    private int rd;
    private int funct3;
    private int rs1;
    private int rs2;
    private int immediate;

    public ITypeInstruction(int opcode, int rd, int funct3, int rs1, int rs2, int immediate) {
        this.opcode = opcode;
        this.rd = rd;
        this.funct3 = funct3;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.immediate = immediate;
    }

    @Override
    public void execute(Memory dataMemory, Registers registers, int programCounter) {
        switch (opcode) {
		    case InstructionSet.LB: // Load byte  
		        int lbAddress = registers.readFromRegister(rs1) + immediate; 
		        byte lbValue = (byte) dataMemory.readByte(lbAddress); // Casting value as a byte 
		        registers.writeToRegister(rd, lbValue);
		        break;

		    case InstructionSet.LH: // LH: Load halfword 
		        int lhAddress = registers.readFromRegister(rs1) + immediate; 
		        short lhValue = (short) dataMemory.readShort(lhAddress); // Casting value as a short 
		        registers.writeToRegister(rd, lhValue);
		        break;
	            
		    case InstructionSet.LW: // LW: Load word  
		        int lwAddress = registers.readFromRegister(rs1) + immediate; 
		        int lwValue = dataMemory.readWord(lwAddress); 
		        registers.writeToRegister(rd, lwValue);
		        break;
	            
		    case InstructionSet.LBU: // LBU: Load byte unsigned
		        int lbuAddress = registers.readFromRegister(rs1) + immediate; 
		        short lbuValue = (short) (dataMemory.readByte(lbuAddress) & 0xFF); 
		        registers.writeToRegister(rd, lbuValue); 
		        break;
	            
		    case InstructionSet.LHU: // LHU: Load halfword unsigned
		        int lhuAddress = registers.readFromRegister(rs1) + immediate; 
		        int lhuValue = dataMemory.readHalfword(lhuAddress) & 0xFFFF; 
		        registers.writeToRegister(rd, lhuValue);
		        break;
                
            case InstructionSet.JALR: // Jump and Link Register
                int jalrRes = programCounter + 4; // Store the address of the next instruction
                int jumpAddress = (registers.readFromRegister(rs1) + immediate) & 0xFFFFFFFE; // Calculate the jump address
                programCounter = jumpAddress; // Set the program counter to the jump address
                break;
                
            case InstructionSet.BGEU: // Branch if greater than or equal to (unsigned)
                if (Integer.compareUnsigned(registers.readFromRegister(rs1), registers.readFromRegister(rs2)) >= 0) {
                    programCounter += immediate;
                }
                break;
                
            case InstructionSet.ADDI:
                int resultAddi = registers.readFromRegister(rs1) + immediate;
                registers.writeToRegister(rd, resultAddi);
                break;
                
            case InstructionSet.SLTI:
                int sltiValue = (registers.readFromRegister(rs1) < immediate) ? 1 : 0;
                registers.writeToRegister(rd, sltiValue);
                break;
                
            case InstructionSet.SLTIU:
                int rs1Value = registers.readFromRegister(rs1);
                int comparisonResult = (Integer.compareUnsigned(rs1Value, immediate) < 0) ? 1 : 0;
                registers.writeToRegister(rd, comparisonResult);
                break;
                
            case InstructionSet.XORI:
                int rs1_XORI = registers.readFromRegister(rs1);
                int imm_XORI = immediate;
                int result_XORI = rs1_XORI ^ imm_XORI;
                registers.writeToRegister(rd, result_XORI);
                break;
                
            case InstructionSet.ORI:
                int sourceRegister_ORI = registers.readFromRegister(rs1);
                int immediateValue_ORI = immediate;
                int result_ORI = sourceRegister_ORI | immediateValue_ORI;
                registers.writeToRegister(rd, result_ORI);
                break;
                
            case InstructionSet.ANDI:
                int sourceRegister_ANDI = registers.readFromRegister(rs1);
                int immediateValue_ANDI = immediate;
                int result_ANDI = sourceRegister_ANDI & immediateValue_ANDI;
                registers.writeToRegister(rd, result_ANDI);
                break;
                
            case InstructionSet.SLLI:
                int sourceRegister_SLLI = registers.readFromRegister(rs1);
                int shiftAmount_SLLI = immediate & 0x1F; // Masking immediate value to fit shamt range (0-31)
                int result_SLLI = sourceRegister_SLLI << shiftAmount_SLLI;
                registers.writeToRegister(rd, result_SLLI);
                break;
                
            case InstructionSet.SRLI:
                int sourceRegister_SRLI = registers.readFromRegister(rs1);
                int shiftAmount_SRLI = immediate & 0x1F; // Masking immediate value to fit shamt range (0-31)
                int result_SRLI = sourceRegister_SRLI >>> shiftAmount_SRLI;
                registers.writeToRegister(rd, result_SRLI);
                break;
                
            case InstructionSet.SRAI:
                int sourceRegister_SRAI = registers.readFromRegister(rs1);
                int shiftAmount_SRAI = immediate & 0x1F; // Masking immediate value to fit shamt range (0-31)
                int result_SRAI = sourceRegister_SRAI >> shiftAmount_SRAI;
                registers.writeToRegister(rd, result_SRAI);
                break;
                
            case InstructionSet.BEQ: //branch if equal
				if(registers.readFromRegister(rs1) == registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				} 
				break; 
				
            case InstructionSet.BNE: //branch if not equal
				if(registers.readFromRegister(rs1) != registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				}
				break; 
				
            case InstructionSet.BLT: //branch if less than
				if(registers.readFromRegister(rs1) < registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				}
				break;
                
            case InstructionSet.BGE:  //branch if greater than or equal to 
				if(registers.readFromRegister(rs1) >= registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				}
				break; 
				
            case InstructionSet.BLTU:  //Branch if less than (unsigned) 
				if (Integer.compareUnsigned(registers.readFromRegister(rs1), registers.readFromRegister(rs2)) < 0) {
					programCounter += immediate; 
				}
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
