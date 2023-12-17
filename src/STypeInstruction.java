
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
	        case InstructionSet.SB: // Store byte
	        case InstructionSet.SH: // Store halfword
	        	switch (funct3) {
		            case 0:
		                // SB: Store Byte
		            	int sbAddress = registers.readFromRegister(rs1) + immediate; 
		                byte sbValue = (byte) (registers.readFromRegister(rs2) & 0xFF); 
		                dataMemory.writeByte(sbAddress, sbValue); 
		                break;
		            case 1:
		                // SH: Store Halfword
		            	 int shAddress = registers.readFromRegister(rs1) + immediate; 
		                 short shValue = (short) (registers.readFromRegister(rs2) & 0xFFFF); 
		                 dataMemory.writeHalfword(shAddress, shValue);
		                 break;
		            case 2:
		                // SW: Store Word
		            	int swAddress = registers.readFromRegister(rs1) + immediate; 
			            int swValue = registers.readFromRegister(rs2); 
			            dataMemory.writeWord(swAddress, swValue); 
		            
	        	}
	      /*
	        case InstructionSet.SB: //Store Byte in memory
	        case InstructionSet.SH: // 
	        case InstructionSet.SW: 
	        	switch (funct3) {
	                case 0:
	                    // SB: Store Byte
	                	int baseAddress = registers.readFromRegister(rs1);
	                    int offset = immediate;
	                    int memoryAddress = baseAddress + offset;
	                	// Get the byte value from rs2
	                    int byteToStore = registers.readFromRegister(rs2) & 0xFF;
	                    // Store the byte in memory
	                    dataMemory.writeByte(memoryAddress, byteToStore);
	                    break;
	                case 1:
	                    // SH: Store Halfword
	                	int baseAddressSH = registers.readFromRegister(rs1);
	                    int offsetSH = immediate;
	                    int memoryAddressSH = baseAddressSH + offsetSH;
	
	                    // Get the halfword value from rs2
	                    int halfwordToStore = registers.readFromRegister(rs2) & 0xFFFF;
	
	                    // Store the halfword in memory
	                    dataMemory.writeHalfword(memoryAddressSH, halfwordToStore);
	                    break;
	                case 2:
	                    // SW: Store Word
	                	int baseAddressSW = registers.readFromRegister(rs1);
	                    int offsetSW = immediate;
	                    int memoryAddressSW = baseAddressSW + offsetSW;
	
	                    // Get the word value from rs2
	                    int wordToStore = registers.readFromRegister(rs2);
	
	                    // Store the word in memory
	                    dataMemory.writeWord(memoryAddressSW, wordToStore);
	                    break;
	                // Handle other cases...
	 	           default:
	 		            throw new UnsupportedOperationException("Unsupported I-Type Funct3: " + funct3);
	        	}
	*/        	

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
    	
    	String mnemonic = null;
        switch (opcode) {
        case InstructionSet.SW: // Store word
        case InstructionSet.SB: // Store byte
        case InstructionSet.SH: // Store halfword
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
	            
        	}
        }
        return mnemonic;
    }
}