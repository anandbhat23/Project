package client;

public class Client {
	private ClientController clientController;
	
	public Client() {
		clientController = new ClientController();
		clientController.run();
	}
	
	public static void main(String[] args) {
		new Client();
	}
}
