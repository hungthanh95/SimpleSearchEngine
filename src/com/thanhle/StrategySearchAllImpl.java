package com.thanhle;

import java.util.*;

public class StrategySearchAllImpl implements StrategySearch {
    @Override
    public List<Integer> search(Map<String, List<Integer>> inverted, String keyQuery) {
        List<String> keys = Arrays.asList(keyQuery.split("\\s+"));
        Set<Integer> finalResult = new HashSet<>(inverted.getOrDefault(keyQuery, new ArrayList<>()));
        for (String key : keys) {
            List<Integer> result = inverted.getOrDefault(key, new ArrayList<>());
            finalResult.retainAll(result);
        }
        return List.copyOf(finalResult);
    }

}
