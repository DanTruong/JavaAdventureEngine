package com.dmt.java_adventure_engine;

public class NPC extends Entity {

    /**
     * Constructor for Non Playable Character object.
     *
     * @param name Name given to the Non Playable Character object.
     * @param description Description of the Non Playable Character.
     */
    public NPC(String name, String description) {
        super(name, description);
    }

    /**
     * Perform an action based on change of the Sector's temperature.
     *
     * @param action The action that was taken in the Sector.
     */
    @Override
    public void react(String action) {
        if (action.equals("warming")) {
            getCurrentSector().getGameWorld().getPlayer().decreaseHealth();
            attemptMove();
        } else {
            getCurrentSector().getGameWorld().getPlayer().increaseHealth();
        }
    }

}
