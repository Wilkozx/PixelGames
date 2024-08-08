package org.pixelgames.Minigames.ChatGames.RewardManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RewardBundleMap {

    private static RewardBundleMap instance = null;
    private final HashMap<String, RewardBundle> rewardBundleMap;

    public RewardBundleMap() {
        rewardBundleMap = new HashMap<>();
    }

    public static RewardBundleMap getInstance() {
        if (instance == null) {
            instance = new RewardBundleMap();
        }
        return instance;
    }

    public void WipeInstance() {
        instance = new RewardBundleMap();
    }

    public void addRewardBundle(String bundleName, RewardBundle rewardBundle) {
        rewardBundleMap.put(bundleName, rewardBundle);
    }

    public Set<String> getAllRewardBundleNames() {
        return rewardBundleMap.keySet();
    }

    public RewardBundle getRewardBundle(String bundleName) {
        return rewardBundleMap.get(bundleName);
    }

}
