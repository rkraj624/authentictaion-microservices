package com.example.springsecurity.lowleveldesignpattern.creational.tictactoe;

import com.example.springsecurity.lowleveldesignpattern.creational.tictactoe.model.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {
    Deque<Player> players;
    Board gameBoard;

    public void initializeGame(){
        players = new LinkedList<>();
        PlayingSymbolX playingSymbolX = new PlayingSymbolX();
        Player player1 = new Player("Ravi", playingSymbolX);
        players.add(player1);

        PlayingSymbolO playingSymbolO = new PlayingSymbolO();
        Player player2 = new Player("Raj", playingSymbolO);
        players.add(player2);

        gameBoard = new Board(3);
    }

    public String startGame(){
        boolean noWinner = true;
        while (noWinner){
            Player playerTurn = players.removeFirst();

            gameBoard.printBoard();

            List<Cell> freeCells = gameBoard.getFreeCells();

            if(freeCells.isEmpty()){
                noWinner = false;
                continue;
            }
            System.out.print("Player:" + playerTurn.getName() + " Enter row,column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.valueOf(values[0]);
            int inputColumn = Integer.valueOf(values[1]);

            boolean pieceAddedSuccessfully = gameBoard.addSymbol(inputRow, inputColumn, playerTurn.getPlayingSymbol());
            if(!pieceAddedSuccessfully) {
                //player can not insert the piece into this cell, player has to choose another cell
                System.out.println("Incorrect position chosen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(inputRow, inputColumn, playerTurn.getPlayingSymbol().symbolType);
            if(winner) {
                return playerTurn.getName();
            }



        }
        return "tie";
    }

    private boolean isThereWinner(int row, int column, SymbolType symbolType) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].symbolType != symbolType) {
                rowMatch = false;
            }
        }

        //need to check in column
        for(int i=0;i<gameBoard.size;i++) {

            if(gameBoard.board[i][column] == null || gameBoard.board[i][column].symbolType != symbolType) {
                columnMatch = false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<gameBoard.size;i++,j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].symbolType != symbolType) {
                diagonalMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=gameBoard.size-1; i<gameBoard.size;i++,j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].symbolType != symbolType) {
                antiDiagonalMatch = false;
            }
        }

        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }

}
