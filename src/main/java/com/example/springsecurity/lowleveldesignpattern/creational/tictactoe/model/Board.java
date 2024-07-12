package com.example.springsecurity.lowleveldesignpattern.creational.tictactoe.model;


import java.util.ArrayList;
import java.util.List;

public class Board {
    public int size;
    public PlayingSymbol[][] board;

    public Board(int size) {
        this.size = size;
        this.board = new PlayingSymbol[size][size];
    }
    public boolean addSymbol(int row, int col,PlayingSymbol playingSymbol){
        if(board[row][col] != null) return false;
        board[row][col] = playingSymbol;
        return true;
    }
    public List<Cell> getFreeCells(){
        List<Cell> freeCells = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == null) {
                    freeCells.add(new Cell(i, j));
                }
            }
        }

        return freeCells;

    }
    public void printBoard() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                    System.out.print(board[i][j].symbolType.name() + "   ");
                } else {
                    System.out.print("    ");

                }
                System.out.print(" | ");
            }
            System.out.println();

        }
    }

}
