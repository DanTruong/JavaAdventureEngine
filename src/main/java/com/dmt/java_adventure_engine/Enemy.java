package com.dmt.java_adventure_engine;

public class Enemy extends Entity {

    /**
     * Constructor for the Adversarial Character object.
     *
     * @param name Name given to the Adversarial Character object.
     * @param description Description of the Adversarial Character.
     */
    public Enemy(String name, String description) {
        super(name, description);
    }

    /**
     * Perform an action based on change of the Sector's temperature.
     *
     * @param action The action that was taken in the Sector.
     */
    @Override
    public void react(String action) {
        if (action.equals("cooling")) {
            getCurrentSector().getGameWorld().getPlayer().decreaseHealth();
            attemptMove();
        } else {
            getCurrentSector().getGameWorld().getPlayer().increaseHealth();
        }
    }

}
