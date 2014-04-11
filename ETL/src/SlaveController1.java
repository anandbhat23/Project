//import java.io.BufferedReader;
//import java.io.InputStreamReader;

public class SlaveController1 {
  public static void main(String args[]) {
    // BufferedReader reader = new BufferedReader(new
    // InputStreamReader(System.in));
    try {
      int port = 15619;// Integer.parseInt(reader.readLine());
      int sid = 0;

      SlaveTaskTracker taskTracker = new SlaveTaskTracker(port, sid);
      taskTracker.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
