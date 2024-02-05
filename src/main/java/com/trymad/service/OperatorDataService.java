package com.trymad.service;

import java.util.Optional;

import com.trymad.api.Loadout;
import com.trymad.api.OperatorAPI;
import com.trymad.api.OperatorDataExtractor;
import com.trymad.model.Operator;
import com.trymad.model.OperatorData;
import com.trymad.repository.FileOperatorDataExtractor;
import com.trymad.util.LoadoutNotFoundException;
import com.trymad.util.OperatorNotFoundException;

public class OperatorDataService implements OperatorAPI {

    private final OperatorDataExtractor extractor;

    public OperatorDataService(OperatorDataExtractor extractor) {
        this.extractor = extractor;
    }

    public OperatorDataService() {
        this.extractor = new FileOperatorDataExtractor();
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
    
}