
public class Program {
    private boolean isRunning;
    private RISCVSimulator theSimulator;
    

    public Program(RISCVSimulator simulator) {
        isRunning = false;
        this.theSimulator = simulator;
    }

}
