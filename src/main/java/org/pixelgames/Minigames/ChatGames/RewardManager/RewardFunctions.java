package org.pixelgames.Minigames.ChatGames.RewardManager;

import info.pixelmon.repack.org.spongepowered.yaml.internal.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class RewardFunctions {
    private final File rewardsFile = new File("config/PixelGames/rewards.yaml");
    private final Logger logger = Logger.getLogger("PixelGames");

    public void readRewardConfig() throws IOException {
        if (!rewardsFile.exists()) {
            logger.warning("Reward File has been removed or is malformed, please restart the server to generate the file. ");
        } else {
            FileReader fileReader = new FileReader(rewardsFile);
            Yaml yaml = new Yaml();
            Map<String, List<Map<String, Object>>> rewardsData = yaml.load(fileReader);

            RewardBundleMap rewardBundles = RewardBundleMap.getInstance();

            for (String bundleName : rewardsData.keySet()) {
                List<Map<String, Object>> bundleData = rewardsData.get(bundleName);
                Reward[] rewardArray = new Reward[bundleData.size()];

                for (int i = 0; i < bundleData.size(); i++) {
                    Map<String, Object> itemData = bundleData.get(i);
                    String itemName = (String) itemData.get("itemName");
                    double weighting = (Double) itemData.get("weighting");
                    int quantity = (Integer) itemData.get("quantity");

                    Reward reward = new Reward(itemName, weighting, quantity);
                    rewardArray[i] = reward;
                }

                RewardBundle rewardBundle = new RewardBundle(bundleName, rewardArray);
                rewardBundles.addRewardBundle(bundleName, rewardBundle);
            }

            fileReader.close();
        }
    }

    public String[] getRandomReward(String bundleName) {
        RewardBundleMap rewardBundles = RewardBundleMap.getInstance();
        String[] rewardArray = new String[2];
        try {
            RewardBundle bundle = rewardBundles.getRewardBundle(bundleName);
            Reward reward = RewardBundleMap.getRandomReward(bundle);
            rewardArray[0] = reward.getItemName();
            rewardArray[1] = String.valueOf(reward.getQuantity());
            return rewardArray;
        } catch (NullPointerException e) {
            logger.warning("Reward Bundle not found, please check the rewards.yaml file. ");
        }
        return rewardArray;
    }

    public String[] getRandomWeightedReward(String bundleName) {
        RewardBundleMap rewardBundles = RewardBundleMap.getInstance();
        String[] rewardArray = new String[2];
        try {
            RewardBundle bundle = rewardBundles.getRewardBundle(bundleName);
            Reward reward = RewardBundleMap.getRandomWeightedReward(bundle);
            rewardArray[0] = reward.getItemName();
            rewardArray[1] = String.valueOf(reward.getQuantity());
            return rewardArray;
        } catch (NullPointerException e) {
            logger.warning("Reward Bundle not found, please check the rewards.yaml file. ");
        }
        return rewardArray;
    }
}




