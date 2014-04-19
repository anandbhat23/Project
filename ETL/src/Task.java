import java.io.Serializable;

import protobuf.ConfigProtos.Configuration;
import protobuf.ProtoMessageConfig.ProtoMessage;


public interface Task extends Serializable{
	public int getSlaveId();
	public void setSlaveId(int slaveId);
	public int getTaskId();
	public void setTaskId(int taskId);
	public int getJobId();
	public void setJobId(int jobId);
	public String getJob();
	public void setJob(String job);
	public ProtoMessage getMessage();
	public void setMessage(ProtoMessage m);
	public long getTimestamp();
	public void setTimestamp(long ts);
	public int getRetry();
	public void setRetry(int retry);
}
