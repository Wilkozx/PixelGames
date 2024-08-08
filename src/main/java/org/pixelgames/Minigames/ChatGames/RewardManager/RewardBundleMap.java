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

    public static Reward getRandomReward(RewardBundle bundle) {
        int randomIndex = (int) Math.floor(Math.random() * bundle.getRewards().length);
        return bundle.getRewards()[randomIndex];
    }

    public static Reward getRandomWeightedReward(RewardBundle bundleName) {
        List<Double> cumulativeWeights = new ArrayList<>();
        double totalWeight = 0;

        for (Reward reward : bundleName.getRewards()) {
            totalWeight += reward.getWeighting();
            cumulativeWeights.add(totalWeight);
        }

        double randomWeight = Math.random() * totalWeight;

        for (int i = 0; i < cumulativeWeights.size(); i++) {
            if (randomWeight < cumulativeWeights.get(i)) {
                return bundleName.getRewards()[i];
            }
        }
        return null;
    }


}
