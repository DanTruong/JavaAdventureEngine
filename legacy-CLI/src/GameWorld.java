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
 * Class to create the game world by parsing XML game data.
 *
 * @author Dan Truong
 */
import java.util.HashMap;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

public class GameWorld extends DefaultHandler {

    /**
     * Default constructor for the GameWorld.
     */
    public GameWorld() {
        sector = null;
        sectorMap = new HashMap();
    }

    /**
     * Return Sector based on string input.
     *
     * @param sectorName Name of the Sector to search for.
     * @return Sector (based on String name).
     */
    public Sector getSector(String sectorName) {
        return sectorMap.get(sectorName);
    }

    /**
     * Creates Entity object and adds it to the current Sector object.
     *
     * @param qName Class of Entity to create.
     * @param name Name of the Entity.
     * @param description Description of the Entity.
     */
    private void createEntity(String qName, String name, String description) {
        Entity entity = null;
        switch (qName) {
            case "PlayerCharacter" -> {
                entity = new Player(name, description);
                player = (Player) entity;
            }
            case "AdversarialCharacter" ->
                entity = new Enemy(name, description);
            default ->
                entity = new NPC(name, description);
        }
        sector.addEntity(entity);
        entity.setCurrentSector(sector);
    }

    /**
     * Creates Sector object, add it to Sector array and sort the array
     * afterwards.
     *
     * @param name Name of the Sector.
     * @param description Description of the Sector.
     * @param state Initial temperature state of the Sector.
     * @param neighbors Directional references to neighboring Sectors.
     */
    private void createSector(String name, String description, String state,
            String[] neighbors) {
        sector = new Sector(name, description, state, neighbors, this);
        sectorMap.put(name, sector);
    }

    /**
     * Overrides default SAXParser startElement method to read in game world
     * elements.
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attr
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attr) {
        switch (qName) {
            case "Sector" ->
                createSector(attr.getValue("name"),
                        attr.getValue("description"),
                        attr.getValue("state"),
                        new String[]{
                            attr.getValue("north"),
                            attr.getValue("east"),
                            attr.getValue("south"),
                            attr.getValue("west")
                        });
            case "AdversarialCharacter", "NonPlayableCharacter", "PlayerCharacter" ->
                createEntity(qName,
                        attr.getValue("name"),
                        attr.getValue("description"));
        }
    }

    /**
     * Returns the player character that is generated for this game.
     *
     * @return The player character object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Used for holding the Sectors in the Game World.
     */
    private HashMap<String, Sector> sectorMap;

    /**
     * Player character being created for the game session.
     */
    private Player player;

    /**
     * The current Sector being created.
     */
    private Sector sector;

}
