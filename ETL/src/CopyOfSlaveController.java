import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import common.YamlParser;

import core.ETLJob;

public class CopyOfSlaveController {
  public static void main(String args[]) {
    BufferedReader reader = new BufferedReader(new
    InputStreamReader(System.in));
    SlaveTaskTracker taskTracker = null;
    try {

        int port = 15819;
        taskTracker = new SlaveTaskTracker(port);
        taskTracker.addNode(port);
        System.out.println("Job started!");
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    while (true) {
      // read input
      try {
        String input = reader.readLine();
        String[] jobArgs = input.split(" ");

        if (jobArgs[0].equals("start")) {
          if (jobArgs.length != 2) {
            System.out.println("Format: start (conf_file) ");
            continue;
          }
          String conf_name = jobArgs[1];
          taskTracker.sendJobToMaster(conf_name);
          System.out.println("Job started!");
          continue;
        }
        
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
