package com.trymad.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.trymad.App;
import com.trymad.api.JsonUtil;

public class JsonFileUtil implements JsonUtil {

    @Override
    public JSONObject getOperatorJson(String opFormattedName) {
        final InputStream inputStream = App.class.getResourceAsStream(
            DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/" + opFormattedName + "/data.json");
        final StringBuilder content = new StringBuilder();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch(IOException e) {
            return new JSONObject();
        }

        return new JSONObject(content.toString());
    }

    @Override
    public JSONObject getOperatorNames() {
        final InputStream inputStream = App.class.getResourceAsStream(
            DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/operatorNames.json");
        final StringBuilder content = new StringBuilder();

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch(IOException e) {
            System.out.println(e);
        }

        final JSONObject jsonInfo = new JSONObject(content.toString());
        return jsonInfo;
    }
    
}
