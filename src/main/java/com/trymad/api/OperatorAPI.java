package com.trymad.api;

import com.trymad.model.Operator;
import com.trymad.model.OperatorData;

public interface OperatorAPI {
    Operator getOperator(String opFormattedName);

    Loadout getLoadout(String opFormattedName);

    OperatorData getOperatorData(String opFormattedName);
}
