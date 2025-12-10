package changpic;

public class threadSleep implements Runnable {

    @Override
    public void run() {
        order.fill(true);
    }
}
