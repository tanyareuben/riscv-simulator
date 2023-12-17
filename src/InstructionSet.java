import java.util.HashMap;
import java.util.Map;

public class InstructionSet {


	private static final Map<Integer, String> opcodeToMnemonicMap = new HashMap<>();
    private static final Map<String, Integer> mnemonicToOpcodeMap = new HashMap<>();


	public static final int LUI = 0x37; 
	public static final int AUIPC = 0x17; 
	public static final int JAL = 0x6F; 
	public static final int JALR = 0x67; 

	public static final int BEQ = 0x63 | (0x0 << 12); //funct3 = 0
	public static final int BNE =  0x63 | (0x1 << 12); //funct3 = 1
	public static final int BLT =  0x63 | (0x4 << 12); //funct3 = 4
	public static final int BGE =  0x63 | (0x5 << 12); //funct3 = 5
	public static final int BLTU =  0x63 | (0x6 << 12); //funct3 = 6
	public static final int BGEU =  0x63 | (0x7 << 12); //funct3 = 7
	
	public static final int LB = 0x03 | (0x0 << 12); //funct3 = 0
	public static final int LH = 0x03 | (0x1 << 12); //funct3 = 1
	public static final int LW = 0x03 | (0x2 << 12); //funct3 = 2
	public static final int LBU = 0x03 | (0x4 << 12); //funct3 = 4
	public static final int LHU = 0x03 | (0x5 << 12); //funct3 = 5

	public static final int SB = 0x23 | (0x0 << 12); //funct3 = 0
	public static final int SH = 0x23  | (0x1 << 12); //funct3 = 1
	public static final int SW = 0x23 | (0x2 << 12);  //funct3 = 2

	public static final int ADDI = 0x13 | (0x0 << 12);
	public static final int SLTI = 0x13 | (0x2 << 12);
	public static final int SLTIU = 0x13 | (0x3 << 12);
	public static final int XORI = 0x13 | (0x4 << 12);
	public static final int ORI = 0x13 | (0x6 << 12);
	public static final int ANDI = 0x13 | (0x7 << 12);
	
	public static final int SLLI = 0x13 | (0x1 << 12);
	public static final int SRLI = 0x13 | (0x5 << 12);
	public static final int SRAI = 0x13 | (0x17 << 12);
	
//R Type Instructions	
	public static final int ADD = 0x33 | (0x0 << 12);
	public static final int SUB = 0x33 | (0x400000 >> 12); // funct7 = 0x400000
	public static final int SLL = 0x33 | (0x1 << 12);
	public static final int SLT = 0x33 | (0x2 << 12);
	public static final int SLTU = 0x33 | (0x3 << 12);
	public static final int XOR = 0x33 | (0x4 << 12);
	public static final int SRL = 0x33 | (0x5 << 12);
	public static final int SRA = 0x33 | (0x20 << 25); // funct7 = 0x20
	public static final int OR = 0x33 | (0x6 << 12);
	public static final int AND = 0x33 | (0x7 << 12);
	
//	public static final int ADD = 0x33;
//    public static final int SUB = 0x40000033;
	

	static {
        // Initialize opcodeToMnemonicMap and mnemonicToOpcodeMap with R-type instructions
        
		//U- TYpe Instruction
		addMapping(LUI, "LUI");
        addMapping(AUIPC, "AUIPC");
        
        
        //J - TYpe Instruction
        addMapping(JAL, "JAL");

        //B-Type Instructions
        addMapping(BEQ, "BEQ");
        addMapping(BNE, "BNE");
        addMapping(BLT, "BLT");
        addMapping(BGE, "BGE");
        addMapping(BLTU, "BLTU");
        addMapping(BGEU, "BGEU");

        // I - Type Instructions
        addMapping(JALR, "JALR");
        addMapping(LB, "LB");
        addMapping(LH, "LH");
        addMapping(LW, "LW");
        addMapping(LBU, "LBU");
        addMapping(LHU, "LHU");

        addMapping(SB, "SB");
        addMapping(SH, "SH");
        addMapping(SW, "SW");

        addMapping(ADDI, "ADDI");
        addMapping(SLTI, "SLTI");
        addMapping(SLTIU,"SLTIU");
        addMapping(XORI,"XORI");
        addMapping(ORI,"ORI");
        addMapping(ANDI,"ANDI");
        
        addMapping(SLLI,"SLLI");
        addMapping(SRLI,"SRLI");
        addMapping(SRAI,"SRAI");
        
        
        //R-Type Instructions
        addMapping(ADD,"ADD");
        addMapping(SUB,"SUB");
        addMapping(SLL,"SLL");
        addMapping(SLT,"SLT");
        addMapping(SLTU,"SLTU");
        addMapping(XOR,"XOR");
        addMapping(SRL,"SRL");
        addMapping(SRA,"SRA");
        addMapping(OR,"OR");
        addMapping(AND,"AND");
    }

	private static void addMapping(int opcode, String mnemonic) {
        opcodeToMnemonicMap.put(opcode, mnemonic);
        mnemonicToOpcodeMap.put(mnemonic, opcode);
    }

    public static String getMnemonic(int opcode) {
        return opcodeToMnemonicMap.get(opcode);
    }

    public static Integer getOpcode(String mnemonic) {
        return mnemonicToOpcodeMap.get(mnemonic);
    }
}
