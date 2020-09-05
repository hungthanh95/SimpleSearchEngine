package com.thanhle;

import java.util.List;
import java.util.Map;

public class SelectionSearchType {
    StrategySearch strategySearch;

    public SelectionSearchType(StrategySearch strategySearch) {
        this.strategySearch = strategySearch;
    }

    public void setStrategySearch (StrategySearch strategySearch) {
        this.strategySearch = strategySearch;
    }
    public List<Integer> search(Map<String, List<Integer>> inverted, String keyQuery) {
        return this.strategySearch.search(inverted, keyQuery);
    }
}
