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

        	//ADD
        	if(funct3 == 0 && funct7 == 0) {
		        int resultADD = registers.readFromRegister(rs1) + registers.readFromRegister(rs2);
		        registers.writeToRegister(rd, resultADD);
        	}
	        
        	//SUB
        	else if(funct3 == 0 && funct7 == 0x400000) {
	            int resultSUB = registers.readFromRegister(rs1) - registers.readFromRegister(rs2);
	            registers.writeToRegister(rd, resultSUB);
        	}

            //SLL
        	else if(funct3 == 1 && funct7 == 0) {
	            int shiftAmount = registers.readFromRegister(rs2) & 0x1F; // Extract lower 5 bits
	            int resultSLL = registers.readFromRegister(rs1) << shiftAmount;
	            registers.writeToRegister(rd, resultSLL);
        	}
            
        	//SLT
        	else if(funct3 == 2 && funct7 == 0) {
	            int resultSLT = (registers.readFromRegister(rs1) < registers.readFromRegister(rs2)) ? 1 : 0;
	            registers.writeToRegister(rd, resultSLT);
        	}
            
        	//SLTU
        	else if(funct3 == 3 && funct7 == 0) {
	            int resultSLTU = (Integer.compareUnsigned(registers.readFromRegister(rs1), registers.readFromRegister(rs2)) < 0) ? 1 : 0;
	            registers.writeToRegister(rd, resultSLTU);
        	}
            
        	//XOR
        	else if(funct3 == 4 && funct7 == 0) {
	            int resultXOR = registers.readFromRegister(rs1) ^ registers.readFromRegister(rs2);
	            registers.writeToRegister(rd, resultXOR);
        	}
            
        	//SRL
        	else if(funct3 == 5 && funct7 == 0) {
	            int shiftValue = (registers.readFromRegister(rs2) & 0x1F); // Extract the shift amount
	            int sourceValue = registers.readFromRegister(rs1); // Value to be shifted
	            int shiftedResult = sourceValue >>> shiftValue; // Perform logical right shift
	            registers.writeToRegister(rd, shiftedResult); // Store the result in the destination register
        	}
            
        	//SRA
        	else if(funct3 == 5 && funct7 == 0x20) {
                int sraShiftValue = (registers.readFromRegister(rs2) & 0x1F); // Extract the shift amount
                int sraSourceValue = registers.readFromRegister(rs1); // Value to be shifted
                int sraResult = sraSourceValue >> sraShiftValue; // Perform arithmetic right shift
                registers.writeToRegister(rd, sraResult); // Store the result in the destination register
        	}
            
        	//OR
        	else if(funct3 == 6 && funct7 == 0) {
	            int orFirstValue = registers.readFromRegister(rs1); // First value for bitwise OR
	            int orSecondValue = registers.readFromRegister(rs2); // Second value for bitwise OR
	            int orResult = orFirstValue | orSecondValue; // Perform bitwise OR operation
	            registers.writeToRegister(rd, orResult); // Store the result in the destination register
        	}
            
        	//AND
	        else if(funct3 == 7 && funct7 == 0) {
	            int andFirstValue = registers.readFromRegister(rs1); // First value for bitwise AND
	            int andSecondValue = registers.readFromRegister(rs2); // Second value for bitwise AND
	            int andResult = andFirstValue & andSecondValue; // Perform bitwise AND operation
	            registers.writeToRegister(rd, andResult); // Store the result in the destination register
        	}
	        else {
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
    	String mnemonic = null;
    	
    	//ADD
    	if(funct3 == 0 && funct7 == 0) {
    		mnemonic = "ADD";
    	}
        
    	//SUB
    	else if(funct3 == 0 && funct7 == 0x400000) {
    		mnemonic = "SUB";
    	}

        //SLL
    	else if(funct3 == 1 && funct7 == 0) {
    		mnemonic = "SLL";
    	}
        
    	//SLT
    	else if(funct3 == 2 && funct7 == 0) {
    		mnemonic = "SLT";
    	}
        
    	//SLTU
    	else if(funct3 == 3 && funct7 == 0) {
    		mnemonic = "SLTU";
    	}
        
    	//XOR
    	else if(funct3 == 4 && funct7 == 0) {
    		mnemonic = "XOR";
    	}
        
    	//SRL
    	else if(funct3 == 5 && funct7 == 0) {
    		mnemonic = "SRL";
    	}
        
    	//SRA
    	else if(funct3 == 5 && funct7 == 0x20) {
    		mnemonic = "SRA";
    	}
        
    	//OR
    	else if(funct3 == 6 && funct7 == 0) {
    		mnemonic = "OR";
    	}
        
    	//AND
        else if(funct3 == 7 && funct7 == 0) {
        	mnemonic = "AND";
        }
        return mnemonic;
    }
}
