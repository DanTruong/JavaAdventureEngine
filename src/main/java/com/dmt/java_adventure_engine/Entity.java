package com.dmt.java_adventure_engine;

public abstract class Entity {

    /**
     * Creates new Entity object and gives it a name and description.
     *
     * @param name String to identify the Entity.
     * @param description Description to give to the Entity.
     */
    public Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Command to relocate the Entity from one Sector to another.
     *
     * @param nextSector The Sector for the Entity to move to.
     */
    public void move(Sector nextSector) {
        nextSector.addEntity(this);
        getCurrentSector().removeEntity(this);
        setCurrentSector(nextSector);
    }

    /**
     * Attempt to move in a random direction. Mainly used by the Non Playable
     * and Adversarial entities.
     */
    public void attemptMove() {
        int rand = (int) (Math.random() * 4);
        switch (rand) {
            case 0 -> {
                try {
                    move(getCurrentSector().getNeighbor("n"));
                } catch (NullPointerException npe) {
                }
            }
            case 1 -> {
                try {
                    move(getCurrentSector().getNeighbor("e"));
                } catch (NullPointerException npe) {
                }
            }
            case 2 -> {
                try {
                    move(getCurrentSector().getNeighbor("s"));
                } catch (NullPointerException npe) {
                }
            }
            default -> {
                try {
                    move(getCurrentSector().getNeighbor("w"));
                } catch (NullPointerException npe) {
                }
            }
        }
    }

    /**
     * Change the state of the Sector's temp by increasing or decreasing the
     * temperature.
     *
     * @param increase Boolean value; true if the Sector is being facing
     * temperature increase, false if decrease in temperature.
     */
    public String changeSectorTemperature(boolean increase) {
        String returnStatement = "";
        if (increase) {
            switch (getCurrentSector().getTemperature()) {
                case "cold" -> {
                    getCurrentSector().setTemperature("cool");
                    getCurrentSector().notifyAllEntities("warming");
                }
                case "cool" -> {
                    getCurrentSector().setTemperature("warm");
                    getCurrentSector().notifyAllEntities("warming");
                }
                case "warm" -> {
                    getCurrentSector().setTemperature("hot");
                    getCurrentSector().notifyAllEntities("warming");              
                }
                default ->
                    returnStatement = "Sector already hot";
            }
        } else {
            switch (getCurrentSector().getTemperature()) {
                case "hot" -> {
                    getCurrentSector().setTemperature("warm");
                    getCurrentSector().notifyAllEntities("cooling");
                }
                case "warm" -> {
                    getCurrentSector().setTemperature("cool");
                    getCurrentSector().notifyAllEntities("cooling");
                }
                case "cool" -> {
                    getCurrentSector().setTemperature("cold");
                    getCurrentSector().notifyAllEntities("cooling");
                }
                default ->
                    returnStatement = "Sector already cold";
            }
        }
        return returnStatement;
    }

    /**
     * Changes the Sector that the Entity will be going to.
     *
     * @param sector The sector that the Entity will be moving to.
     */
    public void setCurrentSector(Sector sector) {
        currentSector = sector;
    }

    /**
     * Gets the Sector that the Entity is currently located.
     *
     * @return Sector that the Entity is located in.
     */
    public Sector getCurrentSector() {
        return currentSector;
    }

    /**
     * String method to return name of the Entity.
     *
     * @return Name of the Entity.
     */
    public String getName() {
        return name;
    }

    /**
     * String method to return the description of the Entity.
     *
     * @return Description of the Entity.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the Living Entity object.
     *
     * @return String name of the Entity.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * Abstract method to trigger actions based on Sector temperature change.
     *
     * @param action
     */
    public abstract void react(String action);

    /**
     * String variables to hold the name and description of the Entity. Cannot
     * be changed afterwards.
     */
    private final String name, description;

    /**
     * Sector object to reference where the Entity is located.
     */
    private Sector currentSector;

}
