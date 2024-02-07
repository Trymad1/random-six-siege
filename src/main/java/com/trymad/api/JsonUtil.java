package com.trymad.api;

import org.json.JSONObject;

public interface JsonUtil {
    JSONObject getOperatorJson(String opFormattedName);
    JSONObject getOperatorNames();
}
