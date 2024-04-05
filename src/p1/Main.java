package p1;

public class Main {
    
    static boolean running = false;
    
    static Thread cpuThread;
    static Thread gpuThread;
    
    public static void main(String[] args) {
        GUI g = new GUI(800, 800);
    }
    
    public static void startTest() {
        if (!running) {
            running = true;

            cpuThread = new Thread(new Runnable() {
                public void run() {
                	CPUTest cpuTest = new CPUTest();
                }
            });

            gpuThread = new Thread(new Runnable() {
                public void run() {
                	GPUTest gpuTest = new GPUTest();
                }
            });

            cpuThread.setPriority(Thread.MIN_PRIORITY);
            gpuThread.setPriority(Thread.MIN_PRIORITY);

            cpuThread.start();
            gpuThread.start();

            try {
                cpuThread.join();
                gpuThread.join();
            } catch (InterruptedException ignored) {}
        }
    }
    
    public static void stopTest() {
        if (running) {
            try {
                cpuThread.interrupt();
                gpuThread.interrupt();
            } catch (Exception ignored) {}
        }
    }
}
