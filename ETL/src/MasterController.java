import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

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

          Yaml yaml = new Yaml();
          String classname = null;
          try {
            InputStream is = new FileInputStream(new File(conf_name));
            @SuppressWarnings("unchecked")
            Map<String, ArrayList<Map<String, Object>>> map = (Map<String, ArrayList<Map<String, Object>>>) yaml
                .load(is);
            ArrayList<Map<String, Object>> srcmap = map.get("classType");
            for (Map<String, Object> inmap : srcmap) {
              classname = (String) inmap.get("type");
            }
          } catch (Exception e) {
            e.printStackTrace();
          }

          // get job instance
          ETL etl_ins = (ETL) Class.forName(classname).newInstance();
          etl_ins.setUp(conf_name);

          jobTracker.newETLJob(etl_ins, classname);

          System.out.println(classname + " started!");

          continue;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

    }

  }
}
