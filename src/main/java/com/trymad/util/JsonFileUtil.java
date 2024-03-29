package com.trymad.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.trymad.api.JsonUtil; 

public class JsonFileUtil implements JsonUtil {

    @Override
    public JSONObject getOperatorJson(String opFormattedName) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(
                DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/" + opFormattedName + "/data.json"));
        } catch (FileNotFoundException e) {
            throw new OperatorsDirectoryNotFound();
        }
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
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(
                DirectoryUtils.RELATIVE_DIRECTORY_PATH + "/operatorNames.json"));
        } catch (FileNotFoundException e) {
            throw new OperatorsDirectoryNotFound();
        }
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
