import java.io.BufferedReader;
import java.io.IOException;

public class ExampleETL implements ETL {

  private static final long serialVersionUID = 1L;
  BufferedReader br;

  public void setUp(String config_file) {
    br = Helper.getReader(config_file);
  }

  // HTTPImporter

  @Override
  public Message importer() {

    try {
      String line = br.readLine();
      if (line == null) {
        br.close();
        return null;
      } else {
        Message m = new Message(MessageType.MsgData, line, null);
        return m;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public Message transformer(Message m) {
    // We are free to do something here!
    return m;
  }

  @Override
  public void exporter(Message m) {
    String data = (String) m.getObj();
    // Go to any data destination.
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Here we load " + data + " into dest.");
  }

}
