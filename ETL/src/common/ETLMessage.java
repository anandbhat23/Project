package common;
import java.io.Serializable;

public class ETLMessage implements Serializable {
  private static final long serialVersionUID = 1L;
  private MessageType type;
  private Object obj;
  private Object arg;
  private Object arg2;
  
  public ETLMessage(MessageType type, Object obj, Object arg) {
    this.type = type;
    this.obj = obj;
    this.arg = arg;
  }
  
  public ETLMessage(MessageType type, Object obj, Object arg, Object arg2) {
    this.type = type;
    this.obj = obj;
    this.arg = arg;
    this.arg2 = arg2;
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
  
  public Object getArg2() {
    return arg;
  }

}