package com.thanhle;

import java.util.List;
import java.util.Map;

public interface StrategySearch {
    List<Integer> search(Map<String, List<Integer>> inverted, String keyQuery);
}
