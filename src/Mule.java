import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Mule {
	
	private String name;
	private PrintWriter writer;
	private BufferedReader reader;
	private Slave slave;
	private Socket socket;
	private int amountOfCash;
	
	public Mule(String name, Socket socket, PrintWriter writer, BufferedReader reader) {
		this.name = name;
		this.writer = writer;
		this.reader = reader;
		this.socket = socket;
	}

	public String getName() {
		return name;
	}
	
	public Socket getSocket(){
		return socket;
	}
	
	public PrintWriter getWriter(){
		return writer;
	}

	public BufferedReader getReader(){
		return reader;
	}
	
	public Boolean isFree(){
		return slave == null;
	}
	
	public Slave getSlave(){
		return slave;
	}
	
	public boolean addSlave(Slave slave, int amountOfCash){
		if(isFree() && slave != null){
			this.slave = slave;
			this.amountOfCash = amountOfCash;
			getWriter().println("new slave assigned: " + slave.getName() + ". We will give him " + amountOfCash + " coins.");
			return true;
		}
		return false;
	}

	public void removeSlave() {
		System.out.println("should be removed");
		slave = null;		
		getWriter().println(Protocol.TRADE_DONE);
	}
}
