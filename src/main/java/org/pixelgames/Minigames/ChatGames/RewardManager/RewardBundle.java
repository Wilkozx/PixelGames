package org.pixelgames.Minigames.ChatGames.RewardManager;

public class RewardBundle {
    private final Reward[] rewards;
    private String bundleName;

    public RewardBundle(String bundleName, Reward[] rewards) {
        this.bundleName = bundleName;
        this.rewards = rewards;
    }

    public void setBundleName(String bundleName) {
        this.bundleName = bundleName;
    }
    public String getBundleName() {
        return bundleName;
    }
    public Reward[] getRewards() {
        return rewards;
    }
    public Reward getReward(int index) {
        return rewards[index];
    }

}
