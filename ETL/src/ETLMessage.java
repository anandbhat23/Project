import java.io.Serializable;

public class ETLMessage implements Serializable {
  private static final long serialVersionUID = 1L;
  private MessageType type;
  private Object obj;
  private Object arg;
  
  public ETLMessage(MessageType type, Object obj, Object arg) {
    this.type = type;
    this.obj = obj;
    this.arg = arg;
  }

  public MessageType getType() {
    return type;
  }

  public Object getObj() {
    return obj;
  }

  public Object getArg() {
    return arg;
  }

}