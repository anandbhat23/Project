public enum MessageType {

  MsgOK,

  MsgHeartBeat,

  MsgData, MsgTaskStart, MsgTaskFinish, MsgTask,

  MsgTaskFailure, MsgJobFailure,
  
  MsgElection, MsgAnswer, MsgVictory,
  
  MsgNewJob, MsgNewJobClient,
  
  MsgNewSlaveRequest, MsgNewSlaveUpdate, MsgNewSlaveRemove,
  
  MsgTaskStatus, MsgJobStatus,MsgSlaveStatus,
  
  MsgNewMaster,
  
  MsgMasterAddrRequest, MsgMasterAddrResponse,
  

}