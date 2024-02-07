package com.trymad.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;

import com.trymad.api.JsonUtil;
import com.trymad.api.Loadout;
import com.trymad.api.OperatorAPI;
import com.trymad.api.OperatorDataRepository;
import com.trymad.model.Operator;
import com.trymad.model.OperatorData;
import com.trymad.repository.FileOperatorDataRepository;
import com.trymad.util.JsonFileUtil;
import com.trymad.util.LoadoutNotFoundException;
import com.trymad.util.OperatorNotFoundException;

public class OperatorDataService implements OperatorAPI {

    private final OperatorDataRepository extractor;
    private final JsonUtil jsonUtil = new JsonFileUtil();

    public OperatorDataService(OperatorDataRepository extractor) {
        this.extractor = extractor;
    }

    public OperatorDataService() {
        this.extractor = new FileOperatorDataRepository();
    }

    @Override
    public Operator getOperator(String opFormattedName) {
        Optional<Operator> operator = 
            extractor.extractOperatorByName(opFormattedName);
        if (operator.isEmpty()) 
            throw new OperatorNotFoundException(opFormattedName);

        return operator.get();
        
    }

    @Override
    public Loadout getLoadout(String opFormattedName) {
        Optional<Loadout> loadout = 
            extractor.extractLoadoutByName(opFormattedName);
        if (loadout.isEmpty()) {
            throw new LoadoutNotFoundException(opFormattedName);
        }

        return loadout.get();

    }

    @Override
    public OperatorData getOperatorData(String opFormattedName) {
        return new OperatorData(getOperator(opFormattedName), getLoadout(opFormattedName));
    }

    @Override
    public List<OperatorData> getOperators() {
        final JSONObject jsonInfo = jsonUtil.getOperatorNames();

        Iterator<String> keys = jsonInfo.keys();
        List<String> names = new ArrayList<>();
        while((keys.hasNext())) {
            names.add(jsonInfo.getString(keys.next()));
        }

        final List<OperatorData> operators = new ArrayList<>();
        names.forEach(name -> operators.add(this.getOperatorData(name)));

        return operators;
    }

    
}
