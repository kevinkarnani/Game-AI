package ai.abstraction.ruleBasedSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class RuleParser {
    String fileName;
    HashMap<String, String[]> rulesAndConditions;

    public RuleParser(String fileName) {
        this.fileName = fileName;
        this.rulesAndConditions = new HashMap<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String rule = line.substring(0, line.indexOf(":-")).trim();

                    String[] conditions = line.substring(line.indexOf("-") + 1, line.length() - 1).split(",");
                    Arrays.parallelSetAll(conditions, i -> conditions[i].trim());
                    this.rulesAndConditions.put(rule, conditions);
                }
            }
            // this.rulesAndConditions.forEach((k, v) -> System.out.println(k + ": " + Arrays.toString(v)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
