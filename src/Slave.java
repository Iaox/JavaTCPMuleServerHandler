import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Slave {

	private String name;
	private PrintWriter writer;
	private BufferedReader reader;
	private Mule mule;
	private Socket socket;
	private int world;
	
	public Slave(String name, Socket socket,  Mule mule, int world) {
		this.name = name;
		try {
			this.writer =  new PrintWriter(socket.getOutputStream(), true);
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.socket = socket;
		this.mule = mule;
		this.world = world;
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
	
	public Mule getMule(){
		return mule;
	}
	
	public int getWorld(){
		System.out.println("asked for world: " + world);
		return world;
	}
}
