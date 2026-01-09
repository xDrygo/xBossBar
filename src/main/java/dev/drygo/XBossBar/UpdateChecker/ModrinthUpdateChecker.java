package dev.drygo.XBossBar.UpdateChecker;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ModrinthUpdateChecker {
    private static final String apiUrl = "https://api.modrinth.com/v2/project/iHPrzEKR/version";

    public static String isUpdateAvailable(String currentVersion) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            JsonParser parser = new JsonParser();
            JsonArray response = (JsonArray) parser.parse(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)
            );

            if (response.isEmpty()) return "false";

            JsonObject latestVersion = (JsonObject) response.get(0);
            String latestVersionNumber = latestVersion.get("version_number").getAsString();

            return !currentVersion.equals(latestVersionNumber) ? latestVersionNumber : "false";

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}