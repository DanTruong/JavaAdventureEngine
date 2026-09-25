/*
 * The MIT License
 *
 * Copyright 2023 Dan Truong.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/**
 * Class to define the physical sectors that Living Entity objects will reside
 * in.
 *
 * @author Dan Truong
 */
import java.util.TreeMap;

public class Sector {

    /**
     * Creates new Sector object and gives it a name.
     *
     * @param name String to identify the Sector.
     * @param description Description of the Sector.
     * @param temperature Initial state of the Sector's temperature.
     * @param neighbors References to neighboring Sector's names.
     * @param gw GameWorld object linked to the Sector.
     */
    public Sector(String name, String description, String temperature,
            String[] neighbors, GameWorld gw) {
        this.name = name;
        this.description = description;
        this.temperature = temperature;
        this.populationTree = new TreeMap();
        this.northRef = neighbors[0];
        this.eastRef = neighbors[1];
        this.southRef = neighbors[2];
        this.westRef = neighbors[3];
        this.activeGameWorld = gw;
    }

    /**
     * Searches for Entity based on it's name.
     *
     * @param name Name of the Entity to search for
     * @return Entity in the population array.
     */
    public Entity getEntity(String name) {
        return populationTree.get(name);
    }

    /**
     * Method for adding Entities to the Sector.
     *
     * @param le Entity object being added to the Sector.
     */
    public void addEntity(Entity le) {
        if (populationTree.size() <= 10) {
            populationTree.put(le.getName(), le);
        }
    }

    /**
     * Remove the Entity object from the Sector.
     *
     * @param le Entity to remove from the Sector.
     */
    public void removeEntity(Entity le) {
        populationTree.remove(le.getName());
    }

    /**
     * Alerts all Entities in the Sector of changes in the temperature state.
     *
     * @param action The action that was taken to change the Sector's
     * temperature state.
     */
    public void notifyAllEntities(String action) {
        for (String key : populationTree.keySet()) {
            populationTree.get(key).react(action);
        }
    }

    /**
     *
     * @param temperature
     */
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    /**
     * Get the neighboring Sector, based on direction.
     *
     * @param direction String direction (N = North, E = East, S = South, W =
     * West) to get neighbor from.
     * @return Neighboring Sector that corresponds to the requested direction.
     */
    public Sector getNeighbor(String direction) {
        return switch (direction.toLowerCase()) {
            case "n" ->
                activeGameWorld.getSector(northRef);
            case "e" ->
                activeGameWorld.getSector(eastRef);
            case "s" ->
                activeGameWorld.getSector(southRef);
            default ->
                activeGameWorld.getSector(westRef);
        };
    }

    /**
     * String method to return name of the Sector.
     *
     * @return Name of the Sector object.
     */
    public String getName() {
        return name;
    }

    /**
     * String method to return the description of the Sector.
     *
     * @return Description of the Sector object.
     */
    public String getDescription() {
        return description;
    }

    /**
     * String method to return the state of the Sector's temperature.
     *
     * @return Sector's current temperature.
     */
    public String getTemperature() {
        return temperature;
    }

    /**
     * Returns the name of the Sector and the entities that reside in it.
     *
     * @return Name of the Sector and list of entity names.
     */
    @Override
    public String toString() {
        String listOfEntities = "", neighbors = "";

        for (String key : populationTree.keySet()) {
            listOfEntities += key + " (" + populationTree.get(key).getClass().getName() + "), ";
        }

        try {
            neighbors += "\nNorth: " + getNeighbor("N").getName();
        } catch (NullPointerException npe) {
        }
        try {
            neighbors += "\nEast: " + getNeighbor("E").getName();
        } catch (NullPointerException npe) {
        }
        try {
            neighbors += "\nSouth: " + getNeighbor("S").getName();
        } catch (NullPointerException npe) {
        }
        try {
            neighbors += "\nWest: " + getNeighbor("W").getName();
        } catch (NullPointerException npe) {
        }

        return "Sector: " + getName()
                + ". " + getDescription()
                + "\nCurrent Temperature: " + getTemperature()
                + neighbors
                + "\nEntities: " + listOfEntities;
    }

    /**
     * Returns the game world that the Sector resides in. Needed for locating
     * neighboring objects.
     *
     * @return Game world that the Sector is part of.
     */
    public GameWorld getGameWorld() {
        return activeGameWorld;
    }

    /**
     * String variables to hold the name and description of the Sector. Cannot
     * be changed afterwards.
     */
    private final String name, description;

    /**
     * String variable to hold the state of the Sector's temperature.
     */
    private String temperature;

    /**
     * TreeMap to hold Entity objects in the Sector.
     */
    private TreeMap<String, Entity> populationTree;

    /**
     * String names of the neighboring Sectors.
     */
    private final String northRef, eastRef, southRef, westRef;

    /**
     * GameWorld object to call back to.
     */
    private final GameWorld activeGameWorld;

}
