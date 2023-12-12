import java.io.File;
import java.util.Scanner;

public class RISCVSimulator {

    private static final int INSTRUCTION_MEMORY_START = 0x00000000;
    private static final int DATA_MEMORY_START = 0x80000000;
    private static final int MEMORY_SIZE = 1024;  // Adjust the size based on your requirements

    private Program program;

    Registers registers;

    private boolean isRunning;
    private boolean isDone;
    private boolean isBreakpointHit;
    private Scanner scanner;

    public RISCVSimulator() {
        // Initialize registers with a default value of 0
        registers = new Registers(32, 0);

        // Initialize the program with the start addresses, memory sizes, and registers
        program = new Program(INSTRUCTION_MEMORY_START, MEMORY_SIZE, DATA_MEMORY_START, MEMORY_SIZE, registers);

        scanner = new Scanner(System.in);
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void runSimulator() {
        while (!isDone) {
            displayMenu();
            String userInput = scanner.nextLine().trim();

            // Process user input
            processUserInput(userInput);
        }
    }

    private void displayMenu() {
        System.out.println("RISC-V Simulator Menu:");
        System.out.println("'r' - Run the entire program");
        System.out.println("'s' - Run the next instruction and stop");
        System.out.println("'rg' - 'x0' to 'x31' - View register contents");
        System.out.println("'m' - View data memory contents at the specified address");
        System.out.println("'pc' - View the value of the Program Counter");
        System.out.println("'insn' - Print the assembly of the next instruction");
        System.out.println("'b' - Set a breakpoint at a specified PC address");
        System.out.println("'c' - Continue execution until the next breakpoint or exit");
        System.out.println("'q' - Quit the simulator");
        System.out.print("Enter your choice: ");
    }

    private void processUserInput(String userInput) {
        // Process user input and perform corresponding actions
        switch (userInput) {
            case "r":
                runProgram();
                break;
            case "s":
                runNextInstruction();
                break;
            case "rg":
                printRegisterContent();
                break;
            case "m":
                printMemoryLocationContent();
                break;
            case "pc":
                printProgramCounterValue();
                break;
            case "insn":
                printNextInstruction();
                break;
            case "b":
                setBreakPointAtASpecifiedPCAddress();
                break;
            case "c":
                continueExecution();
                break;
            case "q":
                handleQuit();
                break;
            // Add cases for other commands...
            default:
                System.out.println("Invalid choice. Try again.");
        }
    }
    
    private void handleQuit() {
        System.out.println("Exiting the simulator. Goodbye!");
        System.exit(0);
    }
    private void setBreakPointAtASpecifiedPCAddress() {
        System.out.println("Enter the PC address where you want to set a breakpoint (in hexadecimal, e.g., 0x12345678): ");
        String userInput = scanner.nextLine().trim().toLowerCase();
    
        try {
            int pcAddress = Integer.parseInt(userInput, 16);
            program.setBreakpoint(pcAddress);
        } catch (NumberFormatException e) {
            System.out.println("Invalid address format. Please enter a valid hexadecimal address.");
        }
    }
    

    private void printProgramCounterValue() {
        int pcContent = program.getProgramCounter();
        System.out.printf("Program Counter: 0x%08X\n", pcContent);
    }
    

    public void printMemoryLocationContent() {
        System.out.print("Enter the memory address in the form 0x12345678: ");
        String addressInput = scanner.nextLine().trim();

        // Check if the input starts with "0x"
        if (!addressInput.toLowerCase().startsWith("0x")) {
            System.out.println("Invalid input format. Please enter the address in the form 0x12345678.");
            return;
        }

        try {
            // Parse the address string to an integer
            int memoryAddress = Integer.parseInt(addressInput.substring(2), 16);

            // Read the word from memory at the specified address
            int memoryValue = program.getMemoryLocationContent(memoryAddress);

            // Display the result
            System.out.printf("Memory at address %s: 0x%08X\n", addressInput, memoryValue);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid hexadecimal address.");
        }
    }

    public void printRegisterContent() {
        System.out.print("Enter the register number (0-31): ");
        int registerNumber;

        try {
            registerNumber = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            return;
        }

        int registerValue = registers.readFromRegister(registerNumber);
        System.out.printf("Register x%d: 0x%08X\n", registerNumber, registerValue);
    }

    private void printNextInstruction() {
        program.printNextInstruction();
    }

    private void continueExecution() {
        //continue where it left off, since programs keeps teh state of registers and memory and programCounter
        program.runProgram(); 
    }

    private void runNextInstruction() {
        program.executeNextInstruction();
    }

    private void getProgramPathAndLoadProgram() {
        boolean bLoaded = false;
        while(!bLoaded) {
            System.out.print("Enter the path to the RISC-V program file: ");
            String filePath = scanner.nextLine().trim();

            if(filePath.compareToIgnoreCase("quit") == 0) {
                System.out.println("Quitting ...");
                System.exit(0);
            }

            File file = new File(filePath);

            if (file.exists() && file.isFile()) {
                // Load the program if the file is valid
                loadProgram(filePath);
                bLoaded = true;
                System.out.println("Program loaded successfully.");
            } else {
                System.out.println("Invalid file path. Please try again Or type 'quit' to end program");
            }
        }
    }

    // Load the program to the Instruction memory
    public void loadProgram(String filePath) {
        program.loadProgramFromFile(filePath);
    }

    // Run the program
    public void runProgram() {
        program.runProgram();
        printState();
    }

    // Print the final state (memory, registers, etc.)
    public void printState() {
        System.out.println("Final State:");
        System.out.println("Registers:");
        program.printRegisters();
        System.out.println("\nProgram Memory:");
        program.printProgramMemory();
        System.out.println("\nData Memory:");
        program.printDataMemory();
    }

    public static void main(String[] args) {
        RISCVSimulator simulator = new RISCVSimulator();
        Scanner scanner = simulator.getScanner();
        simulator.getProgramPathAndLoadProgram();
    
        while (true) {
            simulator.displayMenu();
            String choice = scanner.nextLine().trim().toLowerCase();
            simulator.processUserInput(choice);
        }
    }
    
}

