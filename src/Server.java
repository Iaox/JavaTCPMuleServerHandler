import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	public static MuleList MULE_HANDLER = new MuleList();
	Boolean running = true;
	private ServerSocket serverSocket;

	public Server() {
		try {
			serverSocket = new ServerSocket(Config.PORT);
			System.out.println("Server is available at port: " + Config.PORT);
			while (running) {
				cleanMules();
				String message = "";
				Socket clientSocket = serverSocket.accept();
				
				System.out.println("We have a connection with: " + clientSocket);
				
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				System.out.println(" lets ask for account type");
				out.println(Protocol.ASK_FOR_ACCOUNT_TYPE);
				
				try{
				message = in.readLine();
				
				if (message.equals(Protocol.MULE)) {
					out.println(Protocol.ASK_FOR_NAME);
					String name = in.readLine();
					
					MULE_HANDLER.add(new Mule(name, clientSocket,out, in));
					
					System.out.println("mule added");
					
				} else if (message.equals(Protocol.SLAVE)) {
					
					out.println(Protocol.ASK_FOR_NAME);
					System.out.println(" lets ask for name");
					String name = in.readLine();
					
					Mule availableMule = MULE_HANDLER.getAvailableMule();
					if (availableMule != null) {
						out.println(Protocol.ASK_FOR_ASSIGNMENT);
						switch(in.readLine()){
							case Protocol.WITHDRAW:
								out.println(Protocol.ASK_FOR_AMOUNT_OF_CASH);
								int amountOfCash = Integer.parseInt(in.readLine());	
								int world = 0;
								while(world < 300){
									out.println(Protocol.ASK_FOR_WORLD);
									int testworld = Integer.parseInt(in.readLine());
									if(testworld>300){
										world = testworld;
									}
								}
								Slave slave = new Slave(name, clientSocket, availableMule, world);
								availableMule.addSlave(slave, amountOfCash);
								slave.getWriter().println("Mule:" + availableMule.getName());
								availableMule.getWriter().println("Slave:" + slave.getName());
								availableMule.getReader().readLine();
								availableMule.getWriter().println("World:" + slave.getWorld());
								availableMule.getReader().readLine();
								availableMule.getWriter().println("Coins:" + amountOfCash);
								Withdraw withdraw = new Withdraw(slave, amountOfCash);
								withdraw.start();
								break;
							case Protocol.DEPOSIT:
								//not done yet 
								break;
							}
					} else {
						out.println(Protocol.MULE_NOT_AVAILABLE);
						clientSocket.close();
					}

				}else{
					System.out.println("naa");
					clientSocket.close();
				}

				MULE_HANDLER.printNames();
				}catch(Exception e){
					System.out.println("something went wrong lets start over");
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void cleanMules() {
		MULE_HANDLER.clearMules();	
	}

	public static void main(String[] args) {
		new Server();
	}

}
