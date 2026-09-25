
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
 * Child class of Entity. Used for handling Playable Character objects.
 *
 * @author Dan Truong
 */
import java.util.Scanner;

public class Player extends Entity {

    /**
     * Constructor for Player Character object.
     *
     * @param name Name to give to the Player.
     * @param description Description of the Player.
     */
    public Player(String name, String description) {
        super(name, description);
        health = 20;
    }

    /**
     * Gets the player's current health points.
     *
     * @return Current health of the player.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Increase the player's health.
     */
    public void increaseHealth() {
        health++;
    }

    /**
     * Decrease the player's health.
     */
    public void decreaseHealth() {
        health--;
    }

    /**
     * This method is currently programmed to do nothing at the moment. This
     * must exist because it is an abstract method for this object's parent
     * class.
     *
     * @param action N/A
     */
    @Override
    public void react(String action) {
        //Do nothing at the moment
    }

    /**
     * Display commands for the Player to use.
     */
    private void displayHelp() {
        String helpText = """
                          The goal of this game is to increase your health 
                          points over 30, while not letting it go below 0. To do 
                          this, you change the temperature of the Sector that 
                          you are currently in. You can either "warm" or "cool"  
                          the Sector that you are currently in. Doing either 
                          action will affect the people or enemies that are in 
                          there. Their reaction will either increase or decrease 
                          your health points.
                          
                          Commands:
                          look: Display information about the Sector.
                          warm: Increase the temperature of the Sector.
                          cool: Decrease the temperature of the Sector.
                          N, E, S, W: Move to the Sector of the indicated direction.
                          exit: Exit the game.
                          """;
        System.out.println(helpText);
    }

    /**
     * Method to handle and parse user input commands.
     *
     * @param sc Scanner object to handle keyboard input with.
     */
    public void gameLoop(Scanner sc) {
        String userInput = "";
        while (!userInput.equalsIgnoreCase("exit")) {
            System.out.print("Type in a command > ");
            userInput = sc.nextLine().toLowerCase();
            if (userInput.contains(":")) {
                String command[] = userInput.split(":");
                if (getCurrentSector().getEntity(command[0]) != null) {
                    switch (command[1]) {
                        case "n", "e", "s", "w" -> {
                            try {
                                System.out.println(getCurrentSector().getEntity(command[0]) + " is going to " + getCurrentSector().getNeighbor(command[1]).getName());
                                getCurrentSector().getEntity(command[0]).move(getCurrentSector().getNeighbor(command[1]));
                            } catch (NullPointerException npe) {
                                System.out.println(getCurrentSector().getEntity(command[0]) + " is not going anywhere.");
                            }
                        }
                        case "warm" ->
                            getCurrentSector().getEntity(command[0]).changeSectorTemperature(true);
                        case "cool" ->
                            getCurrentSector().getEntity(command[0]).changeSectorTemperature(false);
                        default ->
                            System.out.println("Command not found");
                    }
                } else {
                    System.out.println("Creature not found");
                }
            } else {
                switch (userInput) {
                    case "n", "e", "s", "w" -> {
                        try {
                            move(getCurrentSector().getNeighbor(userInput));
                        } catch (NullPointerException npe) {
                            System.out.println("Sector doesn't exist");
                        }
                    }
                    case "warm" ->
                        changeSectorTemperature(true);
                    case "cool" ->
                        changeSectorTemperature(false);
                    case "look" ->
                        System.out.println(getCurrentSector() + "\n\nYour current health is " + health);
                    case "help" ->
                        displayHelp();
                    case "exit" ->
                        System.out.println("You are now exiting the game...");
                    default ->
                        System.out.println("Command not found");
                }
            }

        }
    }

    /**
     * Variable used to measure the player's health points.
     */
    private int health;

}
