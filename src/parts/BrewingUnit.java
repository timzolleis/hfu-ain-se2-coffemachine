package parts;

public class BrewingUnit {
    private boolean brewing = false;
    private Thread brewingThread;

    public void startBrewing() {
        if (!brewing) {
            brewing = true;
            brewingThread = new Thread(() -> {
                try {
                    Thread.sleep(3000); // Simulate 3 second brewing time
                    brewing = false;
                    System.out.println("Brewing process completed!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            brewingThread.start();
        }
    }

    public void stopBrewing() {
        brewing = false;
        if (brewingThread != null && brewingThread.isAlive()) {
            brewingThread.interrupt();
        }
    }

    public boolean isBrewingComplete() {
        return !brewing;
    }
}
