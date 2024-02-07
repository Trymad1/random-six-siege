package com.trymad.api;

import java.util.List;

import com.trymad.model.Operator;
import com.trymad.model.OperatorData;

public interface OperatorAPI {
    List<OperatorData> getOperators();

    Operator getOperator(String opFormattedName);

    Loadout getLoadout(String opFormattedName);

    OperatorData getOperatorData(String opFormattedName);
}
