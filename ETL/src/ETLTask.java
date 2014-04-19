import java.io.Serializable;

import protobuf.ProtoMessageConfig.ProtoMessage;

public class ETLTask implements Serializable, Task {

  private static final long serialVersionUID = -12345678L;

  private int taskId;
  private int jobId;
  private int slaveId;
  // name of the etl class
  private String etljob;
  // message data
  private ProtoMessage message;
  // used for fault tolerance.
  private long ts;
  // used for fault tolerance.
  private int retry = 0;

  public ETLTask(int taskId, int jobId, String job, ProtoMessage m) {
    this.taskId = taskId;
    this.jobId = jobId;
    this.etljob = job;
    this.message = m;
  }

  public int getTaskId() {
    return taskId;
  }

  public void setTaskId(int taskId) {
    this.taskId = taskId;
  }

  public int getJobId() {
    return jobId;
  }

  public void setJobId(int jobId) {
    this.jobId = jobId;
  }

  public String getJob() {
    return etljob;
  }

  public void setJob(String job) {
    this.etljob = job;
  }

  public int getSlaveId() {
    return slaveId;
  }

  public void setSlaveId(int slaveId) {
    this.slaveId = slaveId;
  }

  public ProtoMessage getMessage() {
    return message;
  }

  public void setMessage(ProtoMessage m) {
    this.message = m;
  }

  public void setTimestamp(long ts) {
    this.ts = ts;
  }

  public long getTimestamp() {
    return ts;
  }

  public int hashCode() {
    return jobId * 10000 + taskId;
  }

  public boolean equals(Object obj) {
    if (!(obj instanceof Task)) {
      return false;
    }

    int obj_jid = ((Task) obj).getJobId();
    int obj_tid = ((Task) obj).getTaskId();

    if (obj_jid == jobId && obj_tid == taskId) {
      return true;
    }
    return false;

  }

  public String toString() {
    return "[" + jobId + "," + taskId + "]";
  }

  public int getRetry() {
    return retry;
  }

  public void setRetry(int r) {
    retry = r;
  }
}
