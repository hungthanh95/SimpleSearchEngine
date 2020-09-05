package com.thanhle;

import java.util.*;

public class StrategySearchNoneImpl implements StrategySearch {
    @Override
    public List<Integer> search(Map<String, List<Integer>> inverted, String keyQuery) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> indexExclude = new HashSet<>();
        List<String> keys = Arrays.asList(keyQuery.split("\\s+"));
        keys.forEach(key -> {
            List<Integer> abc = inverted.get(key.toLowerCase());
            abc.forEach(a -> indexExclude.add(a));
        });
        inverted.forEach((key, value) -> {
            value.forEach(index -> {
                result.add(index);
            });
        });
        indexExclude.forEach(x -> result.remove(x));
        return List.copyOf(result);
    }
}
