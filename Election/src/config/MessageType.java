package config;
public enum MessageType {

  MsgOK,

  MsgHeartBeat,

  MsgData, MsgTaskStart, MsgTaskFinish, MsgTask,

  MsgTaskFailure, MsgJobFailure,

  MsgElection, MsgAnswer, MsgVictory
}