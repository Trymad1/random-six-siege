package com.trymad.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.trymad.App;
import com.trymad.api.JsonUTil;

public class JsonFileUtil implements JsonUTil {

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
    
}
