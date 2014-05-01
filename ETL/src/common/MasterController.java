package common;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import common.YamlParser;

import core.ETLJob;

public class MasterController {

  public static void main(String args[]) {
    MasterJobTracker jobTracker = new MasterJobTracker();
    jobTracker.start();

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

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
          List<ETLJob> etlJobs = YamlParser.parse(conf_name);
          for(ETLJob etlJob : etlJobs){
        	  jobTracker.newETLJob(etlJob, conf_name);
          }
          System.out.println("Job started!");
          continue;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  }
}
