package com.levelup.forestsandmonsters;

import com.levelup.forestsandmonsters.GameController.DIRECTION;

public class Character {

    public static final String DEFAULT_NAME = "Erin";
    String name;
    GameMap map = null;
    Position currentPosition = null;
    int moveCount = 0;

    
    public Character(){
        this.name = DEFAULT_NAME;
    }
    
    public Character(String name) {
        if(name.trim().equals(""))
            this.name = DEFAULT_NAME;
        else
            this.name = name;
    }
    
    public String getName() {
        return name;
    }

    //set character's starting position
    public void enterMap(GameMap map) {
        this.map = map;
        this.currentPosition = map.startingPosition;
    }

    //get character's current position
    public Position getPosition() {
        return currentPosition;
    }

    //return num of moves the player has made
    public int getMoveCount() {
        return moveCount;
    }

    //move in a direction and calculate position
    public void move(DIRECTION direction) {
        this.currentPosition = map.calculatePosition(this.currentPosition, direction);
        moveCount++;
    }

    



}