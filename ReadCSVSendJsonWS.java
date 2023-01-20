import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class CSVtoJSON {

    public static void main(String[] args) {
        String csvFile = "data.csv";
        String webServiceUrl = "https://example.com/api";
        String resultFile = "result.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                JSONObject json = new JSONObject();
                json.put("field1", values[0]);
                json.put("field2", values[1]);
                json.put("field3", values[2]);

                // Send JSON data to web service
                URL url = new URL(webServiceUrl);
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(json.toString());
                out.close();

                // Get response from web service
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response = in.lines().collect(Collectors.joining());
                in.close();

                // Append response to result file
                Files.write(Paths.get(resultFile), (response + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
