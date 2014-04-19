package merge;

//Base for merging
public class TaskTracker {

	// Common
	private String sync = "SYNC";

	public TaskTracker(String configuration_filename, String localName) {
	}

	public void handleClientConnection() {
		synchronized (sync) {
			try {
				// Calling wait() will block this thread until another thread
				// calls notify() on the object.
				sync.wait();
				System.out.println("Got notified");
			} catch (InterruptedException e) {
				// Happens if someone interrupts your thread.
			}
		}
	}

	public void start() {
		ListenerThread listener = new ListenerThread();
		listener.start();
	}

	public class ListenerThread extends Thread {

		public ListenerThread() {
		}

		public void run() {
			System.out.println("Entered new thread");
			System.out.println("Sleeping..");
			try {
				Thread.sleep(10000);
				System.out.println("Woke Up Notifying..");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized (sync) {
				sync.notify();
			}
		}
	}
}