package core;

import merge.TaskTracker;

public class Server {
  
  public static void main(String args[]) {
    
	TaskTracker jobTracker = new TaskTracker(args[0], args[1]);
	jobTracker.start();
	jobTracker.handleClientConnection();
    /*jobTracker.start();

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
        	  jobTracker.newETLJob(etlJob, "");
          }
          System.out.println("Job started!");
          continue;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }
*/
  }
}
