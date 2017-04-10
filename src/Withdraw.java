import java.io.IOException;

public class Withdraw implements Runnable {

	private Thread t;
	private Slave slave;
	private String threadName;
	private Mule mule;
	private int amountofCash;

	Withdraw(Slave slave, int amountOfCash) {
		this.slave = slave;
		this.mule = slave.getMule();
		this.threadName = slave.getName() + " " + mule.getName();
		this.amountofCash = amountOfCash;
	}

	public void run() {
		System.out.println("Running " + threadName);
		try {
			slave.getWriter().println(Protocol.ASK_IF_SHOULD_RUN);
			String slaveMessage = slave.getReader().readLine();
			mule.getWriter().println(Protocol.ASK_IF_SHOULD_RUN);
			String muleMessage = mule.getReader().readLine();
			while (!slaveMessage.equals(Protocol.BREAK) && !muleMessage.equals(Protocol.BREAK)) {
				System.out.println("Thread: " + threadName);
				slave.getWriter().println(Protocol.ASK_IF_SHOULD_RUN);
				mule.getWriter().println("Thread: " + threadName + "......Time: " + System.currentTimeMillis());
				// Let the thread sleep for a while.
				Thread.sleep(500);
				System.out.println("Waiting for slave");
				slaveMessage = slave.getReader().readLine();
				System.out.println("Waiting for mule");
				muleMessage = mule.getReader().readLine();
				
				if(slaveMessage.equals(Protocol.BREAK) || muleMessage.equals(Protocol.BREAK)){
					slave.getMule().removeSlave();
					break;
				}
				System.out.println(slaveMessage);
			}
		} catch (Exception exception) {
			slave.getMule().removeSlave();
			System.out.println("Thread " + threadName + " interrupted.");
			System.out.println("Thread " + threadName + " exiting.");
		}
		System.out.println("Thread " + threadName + " exiting.");
		slave.getMule().removeSlave();
		try {
			slave.getSocket().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void start() {
		System.out.println("Starting " + threadName);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}