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
        
	        case InstructionSet.JALR: // Jump and Link Register
	            int jalrRes = programCounter + 4; // Store the address of the next instruction
	            int jumpAddress = (registers.readFromRegister(rs1) + immediate) & 0xFFFFFFFE; // Calculate the jump address
	            programCounter = jumpAddress; // Set the program counter to the jump address
	            break;
	
	        case InstructionSet.LB: // Load byte  
	        case InstructionSet.LH: // LH: Load halfword 
	        case InstructionSet.LW: // LW: Load word 
	        case InstructionSet.LBU: // LBU: Load byte unsigned
	        case InstructionSet.LHU: // LHU: Load Halfword Unsigned
	        	switch (funct3) {
		            case 0:
		                // LB: Load Byte
		            	int lbAddress = registers.readFromRegister(rs1) + immediate; 
				        byte lbValue = (byte) dataMemory.readByte(lbAddress); // Casting value as a byte 
				        registers.writeToRegister(rd, lbValue);
				        break;
		            case 1:
		                // LH: Load Halfword
		            	int lhAddress = registers.readFromRegister(rs1) + immediate; 
				        short lhValue = (short) dataMemory.readShort(lhAddress); // Casting value as a short 
				        registers.writeToRegister(rd, lhValue);
				        break;
		            case 2:
		                // LW: Load Word
		            	int lwAddress = registers.readFromRegister(rs1) + immediate; 
				        int lwValue = dataMemory.readWord(lwAddress); 
				        registers.writeToRegister(rd, lwValue);
				        break;
		            case 4:
		                // LBU: Load Byte Unsigned
		            	int lbuAddress = registers.readFromRegister(rs1) + immediate; 
				        short lbuValue = (short) (dataMemory.readByte(lbuAddress) & 0xFF); 
				        registers.writeToRegister(rd, lbuValue); 
				        break;
		            case 5:
		                // LHU: Load Halfword Unsigned
		            	int lhuAddress = registers.readFromRegister(rs1) + immediate; 
				        int lhuValue = dataMemory.readHalfword(lhuAddress) & 0xFFFF; 
				        registers.writeToRegister(rd, lhuValue);
				        break;
		            // Handle other cases...
			           default:
				            throw new UnsupportedOperationException("Unsupported I-Type Funct3: " + funct3);
	        }
	
	
	
	                
	            case InstructionSet.ADDI:
	            case InstructionSet.SLTI:
	            case InstructionSet.SLTIU:
	            case InstructionSet.XORI:
	            case InstructionSet.ORI:
	            case InstructionSet.ANDI:
	            case InstructionSet.SLLI:
	            case InstructionSet.SRLI:
	            case InstructionSet.SRAI:
	            	switch (funct3) {
		                case 0:
		                    // ADDI: Add Immediate
		                	int resultAddi = registers.readFromRegister(rs1) + immediate;
		                    registers.writeToRegister(rd, resultAddi);
		                    break;
		                case 2:
		                    // SLTI: Set Less Than Immediate
		                	int sltiValue = (registers.readFromRegister(rs1) < immediate) ? 1 : 0;
		                    registers.writeToRegister(rd, sltiValue);
		                    break;
		                case 3:
		                    // SLTIU: Set Less Than Immediate Unsigned
		                	int rs1Value = registers.readFromRegister(rs1);
		                    int comparisonResult = (Integer.compareUnsigned(rs1Value, immediate) < 0) ? 1 : 0;
		                    registers.writeToRegister(rd, comparisonResult);
		                    break;
		                case 4:
		                    // XORI: XOR Immediate
		                	int rs1_XORI = registers.readFromRegister(rs1);
		                    int imm_XORI = immediate;
		                    int result_XORI = rs1_XORI ^ imm_XORI;
		                    registers.writeToRegister(rd, result_XORI);
		                    break;
		                case 6:
		                    // ORI: OR Immediate
		                	int sourceRegister_ORI = registers.readFromRegister(rs1);
		                    int immediateValue_ORI = immediate;
		                    int result_ORI = sourceRegister_ORI | immediateValue_ORI;
		                    registers.writeToRegister(rd, result_ORI);
		                    break;
		                case 7:
		                    // ANDI: AND Immediate
		                	int sourceRegister_ANDI = registers.readFromRegister(rs1);
		                    int immediateValue_ANDI = immediate;
		                    int result_ANDI = sourceRegister_ANDI & immediateValue_ANDI;
		                    registers.writeToRegister(rd, result_ANDI);
		                    break;
		                case 1:
		                    // SLLI: Shift Left Logical Immediate
		                	int sourceRegister_SLLI = registers.readFromRegister(rs1);
		                    int shiftAmount_SLLI = immediate & 0x1F; // Masking immediate value to fit shamt range (0-31)
		                    int result_SLLI = sourceRegister_SLLI << shiftAmount_SLLI;
		                    registers.writeToRegister(rd, result_SLLI);
		                    break;
		                case 5:
		                    // SRLI: Shift Right Logical Immediate
		                	int sourceRegister_SRLI = registers.readFromRegister(rs1);
		                    int shiftAmount_SRLI = immediate & 0x1F; // Masking immediate value to fit shamt range (0-31)
		                    int result_SRLI = sourceRegister_SRLI >>> shiftAmount_SRLI;
		                    registers.writeToRegister(rd, result_SRLI);
		                    break;
		                case 17:
		                    // SRAI: Shift Right Arithmetic Immediate
		                	int sourceRegister_SRAI = registers.readFromRegister(rs1);
		                    int shiftAmount_SRAI = immediate & 0x1F; // Masking immediate value to fit shamt range (0-31)
		                    int result_SRAI = sourceRegister_SRAI >> shiftAmount_SRAI;
		                    registers.writeToRegister(rd, result_SRAI);
		                    break;
		                // Handle other cases...
		     	           default:
		     		            throw new UnsupportedOperationException("Unsupported I-Type Funct3: " + funct3);
	            }
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
    	
    	String mnemonic = null;
    	switch (opcode) {
        
        case InstructionSet.JALR: // Jump and Link Register
        	mnemonic = "JALR";
            break;

        case InstructionSet.LB: // Load byte  
        case InstructionSet.LH: // LH: Load halfword 
        case InstructionSet.LW: // LW: Load word 
        case InstructionSet.LBU: // LBU: Load byte unsigned
        case InstructionSet.LHU: // LHU: Load Halfword Unsigned
        	switch (funct3) {
	            case 0:
	                // LB: Load Byte
	            	mnemonic = "LB";
			        break;
	            case 1:
	                // LH: Load Halfword
	            	mnemonic = "LH";
			        break;
	            case 2:
	                // LW: Load Word
	            	mnemonic = "LW";
			        break;
	            case 4:
	                // LBU: Load Byte Unsigned
	            	mnemonic = "LBU";
			        break;
	            case 5:
	                // LHU: Load Halfword Unsigned
	            	mnemonic = "LHU";
			        break;
        }

        case InstructionSet.SB: //Store Byte in memory
        case InstructionSet.SH: // 
        case InstructionSet.SW: 
        	switch (funct3) {
                case 0:
                    // SB: Store Byte
                	mnemonic = "SB";
                    break;
                case 1:
                    // SH: Store Halfword
                	mnemonic = "SH";
                    break;
                case 2:
                    // SW: Store Word
                	mnemonic = "SW";
                    break;
        	}


                
            case InstructionSet.ADDI:
            case InstructionSet.SLTI:
            case InstructionSet.SLTIU:
            case InstructionSet.XORI:
            case InstructionSet.ORI:
            case InstructionSet.ANDI:
            case InstructionSet.SLLI:
            case InstructionSet.SRLI:
            case InstructionSet.SRAI:
            	switch (funct3) {
	                case 0:
	                    // ADDI: Add Immediate
	                	mnemonic = "ADDI";
	                    break;
	                case 2:
	                    // SLTI: Set Less Than Immediate
	                	mnemonic = "SLTI";
	                    break;
	                case 3:
	                    // SLTIU: Set Less Than Immediate Unsigned
	                	mnemonic = "SLTIU";
	                    break;
	                case 4:
	                    // XORI: XOR Immediate
	                	mnemonic = "XORI";
	                    break;
	                case 6:
	                    // ORI: OR Immediate
	                	mnemonic = "ORI";
	                    break;
	                case 7:
	                    // ANDI: AND Immediate
	                	mnemonic = "ANDI";
	                    break;
	                case 1:
	                    // SLLI: Shift Left Logical Immediate
	                	mnemonic = "SLLI";
	                case 5:
	                    // SRLI: Shift Right Logical Immediate
	                	mnemonic = "SRLI";
	                    break;
	                case 17:
	                    // SRAI: Shift Right Arithmetic Immediate
	                	mnemonic = "SARI";
	                    break;
            }
        }
    	return mnemonic;
    }
}
