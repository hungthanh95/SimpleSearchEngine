package com.thanhle;

import java.util.*;

public class StrategySearchAnyImpl implements StrategySearch {
    @Override
    public List<Integer> search(Map<String, List<Integer>> inverted, String keyQuery) {
        Set<Integer> result = new HashSet<>();
        List<String> keys = Arrays.asList(keyQuery.split("\\s+"));
        keys.forEach(key -> {
            if (inverted.containsKey(key.toLowerCase())) {
                List<Integer> abc = inverted.get(key.toLowerCase());
                abc.forEach(index -> result.add(index));
            }
        });
        return List.copyOf(result);
    }
}
