package bridge;

import java.io.*; 
import java.net.*; 

public class Server implements Runnable{ 
	
	@Override
	public void run() { 
		
		ServerSocket sersock = null;
		try {
			sersock = new ServerSocket(3000);
		} catch (IOException e) {
			System.out.println("Not able to create the socket!");
		} 
		System.out.println("Server ready for chatting"); 
		
		Socket sock = null;
		try {
			sock = sersock.accept( );
		} catch (IOException e) {
			System.out.println("Error in accept call");
		} 
		
		// reading from keyboard (keyRead object) 
		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
		
		// sending to client (pwrite object) 
		OutputStream ostream = null;
		try {
			ostream = sock.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		PrintWriter pwrite = new PrintWriter(ostream, true);   
		
		// receiving from server ( receiveRead object) 
		InputStream istream = null;
		try {
			istream = sock.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
		
		String receiveMessage, sendMessage = null;
		
		while(true) { 
			try {
				if((receiveMessage = receiveRead.readLine()) != null) {
					System.out.println(receiveMessage); 
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				sendMessage = keyRead.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pwrite.println(sendMessage);
			System.out.flush();
		}
	}
}
