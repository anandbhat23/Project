import java.io.Serializable;

public interface ETL extends Serializable{
  public Message importer();
  public Message transformer(Message m);
  public void exporter(Message m);
  public void setUp(String conf);
}
