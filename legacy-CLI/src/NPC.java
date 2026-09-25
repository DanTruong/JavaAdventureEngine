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
 * Child class of Entity. Used for handling Non Playable Character
 * objects. Think of this class as an Entity that can assist the player or
 * forward the plot.
 *
 * @author Dan Truong
 */
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
