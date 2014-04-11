import java.io.Serializable;


public interface Task extends Serializable{
	public int getSlaveId();
	public void setSlaveId(int slaveId);
	public int getTaskId();
	public void setTaskId(int taskId);
	public int getJobId();
	public void setJobId(int jobId);
	public String getJob();
	public void setJob(String job);
	public Message getMessage();
	public void setMessage(Message m);
	public long getTimestamp();
	public void setTimestamp(long ts);
	public int getRetry();
	public void setRetry(int retry);
}
