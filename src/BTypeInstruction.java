


public class BTypeInstruction extends Instruction {
    private int opcode;      // Bits 30-26
    private int funct3;      // Bits 14-12
    private int rs1;         // Bits 19-15
    private int rs2;         // Bits 24-20
    private int funct7;      // Bits 31-25
    private int immediate;   // Bits 11-1, 7

    public BTypeInstruction(int opcode, int funct3, int rs1, int rs2, int funct7, int immediate) {
        this.opcode = opcode;
        this.funct3 = funct3;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.funct7 = funct7;
        this.immediate = immediate;
    }

    @Override
    public void execute(Memory dataMemory, Registers registers, int programCounter) {
        switch (funct3) {
        	
        	//BEQ (Branch if Equal):
	        case 000: //branch if equal
				if(registers.readFromRegister(rs1) == registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				} 
				break; 

			//BNE (Branch if Not Equal):
	        case 001: //branch if not equal
				if(registers.readFromRegister(rs1) != registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				}
				break; 
			
			//BLT (Branch if Less Than):
	        case 100: //branch if less than
				if(registers.readFromRegister(rs1) < registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				}
				break;

	        //BGE (Branch if Greater or Equal):
	        case 101:  //branch if greater than or equal to 
				if(registers.readFromRegister(rs1) >= registers.readFromRegister(rs2)) {
					programCounter += immediate; 
				}
				break; 

			//BLTU (Branch if Less Than Unsigned):
	        case 110:  //Branch if less than (unsigned) 
				if (Integer.compareUnsigned(registers.readFromRegister(rs1), registers.readFromRegister(rs2)) < 0) {
					programCounter += immediate; 
				}
				break; 
				
			//BGEU
	        case 111:  // BGEU (Branch if Greater Than or Equal to (unsigned))
	            if (Integer.compareUnsigned(registers.readFromRegister(rs1), registers.readFromRegister(rs2)) >= 0) {
	                programCounter += immediate;
	            }
	            break;

            default:
                throw new UnsupportedOperationException("Unsupported J-Type opcode: " + opcode);
        }
    }

    @Override
    public String getMnemonic() {
    	
    	String menemonic = null;
        switch (funct3) {
    	
	    	//BEQ (Branch if Equal):
	        case 000: //branch if equal
	        	menemonic = "BEQ";
				break; 
	
			//BNE (Branch if Not Equal):
	        case 001: //branch if not equal
	        	menemonic = "BNE";
				break; 
			
			//BLT (Branch if Less Than):
	        case 100: //branch if less than
	        	menemonic = "BLT";
				break;
	
	        //BGE (Branch if Greater or Equal):
	        case 101:  //branch if greater than or equal to 
	        	menemonic = "BGE";
				break; 
	
			//BLTU (Branch if Less Than Unsigned):
	        case 110:  //Branch if less than (unsigned) 
	        	menemonic = "BLTU";
				break; 
				
			//BGEU
	        case 111:  // BGEU (Branch if Greater Than or Equal to (unsigned))
	        	menemonic = "BGEU";
	            break;
	    }
		return menemonic;

    }

    @Override
    public String getAssemblyString() {
        String mnemonic = getMnemonic();

        // Format the B-type instruction assembly string
        return String.format("%s x%d, x%d, %s", mnemonic, rs1, rs2, immediate);
    }

}
