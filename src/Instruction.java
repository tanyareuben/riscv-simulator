public abstract class Instruction {
    abstract public void execute(Memory dataMemory, Registers registers, int programCounter);
    abstract public String getAssemblyString();
    abstract public String getMnemonic();
}
