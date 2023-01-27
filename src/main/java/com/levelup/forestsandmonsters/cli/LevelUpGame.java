package com.levelup.forestsandmonsters.cli;

import java.util.ArrayList;
import java.util.List;

import com.levelup.forestsandmonsters.GameController;
import com.levelup.forestsandmonsters.GameController.GameStatus;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.standard.commands.Quit;

@ShellComponent
public class LevelUpGame implements Quit.Command {

  private final GameController gameController;
  private List<GameStatus> gameHistory;
  private boolean isGameStarted = false;

  public LevelUpGame() {
    super();
    this.gameController = new GameController();
    this.gameHistory = new ArrayList<GameStatus>();
  }

  @ShellMethodAvailability("notStartedCheck")
  @ShellMethod(value = "Create a character (characterName)", key = { "create-character", "create" })
  public void createCharacter(@ShellOption(defaultValue = "Character") String characterName) {
    gameController.createCharacter(characterName);
    GameStatus status = gameController.getStatus();

    System.out.println("Your character, " + status.characterName + " is created!");
  }

  @ShellMethodAvailability("notStartedCheck")
  @ShellMethod("Start the game")
  public void startGame() {
    isGameStarted = true;
    gameController.startGame();
    // TODO: Update this prompt. Also, do you want to get the game status and tell
    // the character where their character is?
    System.out.println("Welcome to Forests and Monsters! You have entered a mysterious place.");
    System.out.println("Would you like to go North(N), South(S), East(E), West(W) or Exit(X)?");
  }

  @ShellMethod(value = "Move North", key = { "N", "n" }, group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveNorth() {
    gameController.move(GameController.DIRECTION.NORTH);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value = "Move South", key = { "S", "s" }, group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveSouth() {
    gameController.move(GameController.DIRECTION.SOUTH);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value = "Move East", key = { "E", "e" }, group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveEast() {
    gameController.move(GameController.DIRECTION.EAST);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value = "Move West", key = { "W", "w" }, group = "Move")
  @ShellMethodAvailability("startedCheck")
  public void moveWest() {
    gameController.move(GameController.DIRECTION.WEST);
    updateStatus(gameController.getStatus());
  }

  @ShellMethod(value = "End the game", key = { "X", "x" })
  public void endGame() {
    System.out.println("You exit the mysterious world.");
    printSummary();
    System.exit(0);
  }

  private void printSummary() {
    System.out.println("Exiting the mysterious land!");
    int startX, startY, endX, endY;
    int totalNumberOfMoves = 0;
    //for (GameStatus status : gameHistory) {
    //  startX = status.currentPosition.getX();
    //  startY = status.currentPosition.getY();
    startX = (int) gameHistory.get(0).currentPosition.x;
    startY = (int) gameHistory.get(0).currentPosition.y;

    String Charactername = gameHistory.get(0).characterName;

    endY = (int) gameHistory.get(gameHistory.size() - 1).currentPosition.y;
    endX = (int) gameHistory.get(gameHistory.size() - 1).currentPosition.x;

    totalNumberOfMoves = gameHistory.get(gameHistory.size() - 1).moveCount;
      System.out.println("Congratulations " + Charactername + "!");
      //System.out.println("You have made your way past the mysterious forest moving " + status.moveCount + " times.");
      System.out.println("Starting Position: (" + startX + ", " + startY + ")"); //(3,9)
      System.out.println("Ending Position: (" + endX + ", " + endY + ")"); 
      System.out.println("Total number of moves: " + totalNumberOfMoves);
      //System.out.println("Ending Position: (" + status.currentPosition.x + "," + status.currentPosition.y + ")");
    //}
    // TODO: Print anything else you committed to in your mockup
  }

  private void updateStatus(GameStatus status) {
    this.gameHistory.add(status);
  }

  public Availability startedCheck() {
    return isGameStarted
        ? Availability.available()
        : Availability.unavailable("game not started");
  }

  public Availability notStartedCheck() {
    return !isGameStarted
        ? Availability.available()
        : Availability.unavailable("game already started");
  }

}
