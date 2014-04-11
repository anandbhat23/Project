import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class Helper {

  public static BufferedReader getReader(String config_file) {
    Yaml yaml = new Yaml();
    BufferedReader br = null;
    try {
      InputStream is = new FileInputStream(new File(config_file));
      @SuppressWarnings("unchecked")
      Map<String, ArrayList<Map<String, Object>>> map = (Map<String, ArrayList<Map<String, Object>>>) yaml
          .load(is);
      ArrayList<Map<String, Object>> srcmap = map.get("importer");
      String file = null;
      for (Map<String, Object> inmap : srcmap) {
        file = (String) inmap.get("src");
      }
      br = new BufferedReader(new FileReader(file));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return br;

  }

}
