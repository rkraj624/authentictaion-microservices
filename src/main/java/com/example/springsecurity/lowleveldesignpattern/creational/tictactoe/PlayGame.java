package com.example.springsecurity.lowleveldesignpattern.creational.tictactoe;

public class PlayGame {
    public static void main(String[] args) {
        TicTacToe ticTacToe = new TicTacToe();
        ticTacToe.initializeGame();
        System.out.println("Winner is "+ticTacToe.startGame());
    }
}
