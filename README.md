# JavaAdventureEngine

JavaAdventureEngine is a Java-based text adventure engine built with Spring Boot. It provides a browser-based terminal interface for exploring an XML-defined game world, interacting with entities, moving between locations, and changing the environment.

The project is a modernization of **jayDungeon**, a command-line Java text adventure originally developed in 2023. The original CLI version has been preserved in the [`legacy-CLI`](./legacy-CLI) directory for reference.

## About

JavaAdventureEngine is primarily an experimental text-adventure engine rather than a story-driven game. The included `game.xml` provides a sample world used to demonstrate the engine's functionality.

The game world consists of interconnected **Sectors** containing a Player and other Entities. Players can navigate between Sectors, inspect their surroundings, change environmental conditions, and issue commands to other entities.

The current Spring Boot version replaces the original command-line interface with a browser-based terminal while retaining the core Java game model.

### Spring Boot Architecture

The original CLI application used `Scanner` and `System.out` for interaction:

```text
Terminal
   ↓
Scanner
   ↓
Player
   ↓
GameWorld / Sector / Entity
   ↓
System.out
```

JavaAdventureEngine 1.0 moves that interaction into a Spring MVC application:

```text
Browser
   ↓
WebController
   ↓
Player
   ↓
GameWorld / Sector / Entity
   ↓
WebController
   ↓
Thymeleaf
   ↓
Browser Terminal
```

Each browser session maintains its own `GameWorld` using an HTTP session. User commands are submitted to the Spring MVC controller, processed by the Player and underlying game objects, and returned to the browser terminal.

## Technologies

The current web version uses:

- Java 21
- Spring Boot 4
- Spring MVC
- Thymeleaf
- Maven
- Bootstrap 5
- SAX XML parsing

Game-world data is defined in `src/main/resources/game.xml` and parsed when a new game session is created.

## Running JavaAdventureEngine

### Requirements

To run the Spring Boot version locally, you will need:

- JDK 21
- An internet connection when Maven dependencies need to be downloaded

The project includes the Maven Wrapper, so a separate Maven installation is not required.

### Windows

From the project root:

```powershell
.\mvnw.cmd spring-boot:run
```

### Linux / macOS

From the project root:

```bash
./mvnw spring-boot:run
```

Once Spring Boot has started, open:

```text
http://localhost:8080/
```

The game will load `game.xml` and display the interactive terminal in your browser.

## Commands

Commands are entered through the browser terminal.

| Command | Description |
| --- | --- |
| `n` | Move the player to the Sector to the north |
| `s` | Move the player to the Sector to the south |
| `e` | Move the player to the Sector to the east |
| `w` | Move the player to the Sector to the west |
| `warm` | Increase the temperature of the current Sector |
| `cool` | Decrease the temperature of the current Sector |
| `look` | Display information about the current Sector and player health |
| `help` | Display available commands |
| `exit` | End the current game interaction |
| `Entity Name:action` | Direct another Entity to move or change the Sector temperature |

For example:

```text
House Fly #3:n
```

directs the specified Entity to attempt to move to the Sector north of its current location.

## Game Mechanics

The included game demonstrates several basic text-adventure mechanics:

- A world composed of interconnected Sectors
- Directional movement between Sectors
- Player, NPC, and Enemy entities
- Entity movement between locations
- Environmental temperature states
- Entity reactions to environmental changes
- Player health
- Commands directed toward other entities
- XML-defined world configuration

The player's health begins at 20. Changing a Sector's temperature causes entities within that Sector to react, which can affect the player's health.

The goal of the included example game is to increase the player's health above 30 without allowing it to fall below 0.

## Project Structure

```text
JavaAdventureEngine/
├── legacy-CLI/                 # Original jayDungeon CLI application
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/dmt/java_adventure_engine/
│   │   │       ├── JavaAdventureEngineApplication.java
│   │   │       ├── WebController.java
│   │   │       ├── GameWorld.java
│   │   │       ├── Player.java
│   │   │       ├── Entity.java
│   │   │       ├── NPC.java
│   │   │       ├── Enemy.java
│   │   │       └── Sector.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── game.xml
│   │       └── templates/
│   │           └── index.html
│   └── test/
├── pom.xml
├── mvnw
└── mvnw.cmd
```

## Legacy CLI Version

The original **jayDungeon** command-line implementation is preserved under:

```text
legacy-CLI/
```

This version uses `Scanner` for interactive input and writes game output directly to the console.

A prebuilt JAR is included at:

```text
legacy-CLI/dist/jayDungeon.jar
```

### Running the Legacy Version

The legacy application does not require Spring Boot, Maven, or Docker.

With a compatible Java Runtime Environment installed, navigate to the `legacy-CLI` directory:

```powershell
cd legacy-CLI
```

Then run:

```powershell
java -jar .\dist\jayDungeon.jar
```

The legacy application expects `game.xml` to be available as a normal filesystem resource. If running the JAR from another working directory, ensure the appropriate `game.xml` file is available there.

The original project files, including its Ant/NetBeans build configuration and source code, have been retained in `legacy-CLI` for historical reference.

## Project History

The project began in 2023 as **jayDungeon** (previously `jDungeonCrawler`), a Java command-line text adventure inspired by traditional interactive-fiction games such as *Zork*.

The original version handled the entire interaction through a CLI loop using `Scanner` for user input and `System.out` for game output.

JavaAdventureEngine modernizes that project by separating the game logic from the user interface and adapting the application to Spring Boot. The original Java game model remains the foundation of the project, while Spring MVC and Thymeleaf provide a browser-based interface.

Version 1.0 represents the first functional Spring Boot port of the original CLI engine.
