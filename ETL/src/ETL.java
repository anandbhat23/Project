import java.io.Serializable;

import protobuf.ConfigProtos.Configuration;

public interface ETL extends Serializable{
  public Configuration importer();
  public Configuration transformer(Configuration m);
  public void exporter(Configuration m);
  public void setUp(String conf);
}
