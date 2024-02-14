package com.trymad.api;

import com.trymad.model.OperatorData;
import com.trymad.model.OperatorSide;

public interface OperatorRandomizer {
    
    public OperatorData getRandomOperatorData();
    public void setSide(OperatorSide side);
    public OperatorSide getSide();
}
