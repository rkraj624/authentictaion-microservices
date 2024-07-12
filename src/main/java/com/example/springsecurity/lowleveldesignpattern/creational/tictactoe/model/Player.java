package com.example.springsecurity.lowleveldesignpattern.creational.tictactoe.model;

public class Player {
    private String name;
    PlayingSymbol playingSymbol;

    public Player(String name, PlayingSymbol playingSymbol) {
        this.name = name;
        this.playingSymbol = playingSymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayingSymbol getPlayingSymbol() {
        return playingSymbol;
    }

    public void setPlayingSymbol(SymbolType symbolType) {
        this.playingSymbol = playingSymbol;
    }
}
