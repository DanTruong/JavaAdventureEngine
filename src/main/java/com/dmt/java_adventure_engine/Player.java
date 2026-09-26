package com.dmt.java_adventure_engine;

import java.util.Scanner;

public class Player extends Entity {

    public Player(String name, String description) {
        super(name, description);
        health = 20;
    }

    public int getHealth() {
        return health;
    }

    public void increaseHealth() {
        health++;
    }

    public void decreaseHealth() {
        health--;
    }

    @Override
    public void react(String action) {
        //Do nothing at the moment
    }

    public String displayHelp() {
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
        return helpText;
    }

    public String processInput(String userInput) {
        userInput = userInput.toLowerCase();

        if (userInput.contains(":")) {
            String[] command = userInput.split(":");
            if (getCurrentSector().getEntity(command[0]) != null) {
                switch (command[1]) {
                    case "n", "e", "s", "w" -> {
                        try {
                            String output = getCurrentSector().getEntity(command[0])
                                    + " is going to " + getCurrentSector().getNeighbor(command[1]).getName();
                            getCurrentSector().getEntity(command[0]).move(getCurrentSector().getNeighbor(command[1]));
                            return output;
                        } catch (NullPointerException npe) {
                            return getCurrentSector().getEntity(command[0]) + " is not going anywhere.";
                        }
                    }
                    case "warm" -> {
                        return getCurrentSector()
                                .getEntity(command[0])
                                .changeSectorTemperature(true);
                    }
                    case "cool" -> {
                        return getCurrentSector()
                                .getEntity(command[0])
                                .changeSectorTemperature(false);
                    }
                    default -> {
                        return "Command not found";
                    }
                }
            } else {
                return "Creature not found";
            }
        } else {
            switch (userInput) {
                case "n", "e", "s", "w" -> {
                    try {
                        move(getCurrentSector().getNeighbor(userInput));
                        return "";
                    } catch (NullPointerException npe) {
                        return "Sector doesn't exist";
                    }
                }
                case "warm" -> {
                    return changeSectorTemperature(true);
                }
                case "cool" -> {
                    return changeSectorTemperature(false);
                }
                case "look" -> {
                    return getCurrentSector()
                            + "\n\nYour current health is "
                            + health;
                }
                case "help" -> {
                    return displayHelp();
                }

                case "exit" -> {
                    return "You are now exiting the game...\nGoodbye!";
                }
                default -> {
                    return "Command not found";
                }
            }
        }
    }

    private int health;

}
