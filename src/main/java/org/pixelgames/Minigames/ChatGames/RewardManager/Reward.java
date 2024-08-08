package org.pixelgames.Minigames.ChatGames.RewardManager;

public class Reward {
        private String itemName;
        private Double weighting;
        private int quantity;

        public Reward(String itemName, Double weighting, int quantity) {
            this.itemName = itemName;
            this.weighting = weighting;
            this.quantity = quantity;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Double getWeighting() {
            return weighting;
        }

        public void setWeighting(Double weighting) {
            this.weighting = weighting;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

}
