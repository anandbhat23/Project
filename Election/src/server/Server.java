package server;

public class Server {
	public static void main(String[] args) {
		TaskTracker taskTraker = new TaskTracker(args[0], args[1]);
		taskTraker.startHeartBeatSend();
		taskTraker.startHeartBeatCheck();
	}
}
