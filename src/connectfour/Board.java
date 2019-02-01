package connectfour;

import java.util.Scanner;
import java.util.regex.*;
import java.util.Random;


public class Board {
    
    private char [][] boardValues = new char[5][5];
    private int column;
    private int row;
    private int counter = 0;
    Random rand = new Random();
    
    Scanner sc = new Scanner(System.in);
    //StringBuilder str = new StringBuilder();
    
    //constructor will set the initial boardValue as spaces 
   public Board(){
       
       for(int i = 0; i<boardValues.length; i++){
           for(int j = 0; j < boardValues[i].length; j++){
               boardValues[i][j] = '_';
           }
       }
   }
   
   //Display the board and values.  row 1 will be at bottom
   public void displayBoard(){
       //loop through column first
       System.out.println("\b");
       System.out.println(" 0 1 2 3 4");
       System.out.println(" _ _ _ _ _");
       for(int i = boardValues.length - 1; i >= 0; i--){           
           System.out.print("|");
           for(int j = 0; j < boardValues[i].length; j++){
               System.out.print(boardValues[i][j] +"|");
           }
           System.out.println("");
       }
   }
   
      public void displayBoard(char[][] board){
       //loop through column first
       System.out.println("\b");
       System.out.println(" 0 1 2 3 4");
       System.out.println(" _ _ _ _ _");
       for(int i = board.length - 1; i >= 0; i--){           
           System.out.print("|");
           for(int j = 0; j < board[i].length; j++){
               System.out.print(board[i][j] +"|");
           }
           System.out.println("");
       }
   }
 
   public void selectColumn(){
       boolean verify = true;
       while(verify){           
           System.out.print("select a column:  ");           
           try{
               int column = sc.nextInt(); 
               if(column < 0 || column > 4){
                  throw new Exception();
               }
               if(boardValues[4][column] != '_'){
                   throw new Exception();
               }
               this.column = column;
               verify = false;
            }                         
            catch (Exception ex) {               
                System.out.println(".....Incorrect input.....");
                sc.nextLine();
           }
       }     
   }
   
   public void enterColumnSelected(){
       for(int row = 0; row < boardValues.length; row++) {
           if(boardValues[row][column] == '_') {
               this.row = row;
               if(counter % 2 == 0) {
                   boardValues[row][column] = 'O';                   
               }
               else{
                   //boardValues[row][column] = 'X';
                   artificalIntelligenceMove();
               }  
               counter++;
               break;
           }
       }
   }
   
    public int placePiece(char piece, int c, char[][] board) {
        int row = -1;
        //int row = 4;
        for (int r = 0; r < board.length; r++) {
            if (board[r][c] == '_') {
                board[r][c] = piece;
                row = r;
                break;
            }
        }
        
        return row;
    }
   
   private char [][] copyBoard() {
        char[][] board = new char[boardValues.length][];

        for (int i = 0; i < boardValues.length; ++i) {
            char[] aBoard = boardValues[i];
            int aLength = aBoard.length;
            board[i] = new char[aLength];
            System.arraycopy(aBoard, 0, board[i], 0, aLength);
        }
        
        return board;
    }
   
   private boolean isBoardFull(char[][] board) {
       boolean rval = true;
       for (int c = 0; c < board.length; ++c) {
         if (board[4][c] == '_') {
             rval = false;
         }
       }
       
       return rval;
   }
   
   private boolean isValidMove(int column, char[][] board) {
       return board[4][column] == '_';
   }
   
   private int randMove(char[][] board) {
       int move = -1;

       if (!isBoardFull(board)) {
           while (true) {
               move = rand.nextInt(5);
               if (isValidMove(move, board)) {
                   break;
               }
           }
       }
       return move;
   }
   
   private void artificalIntelligenceMove() {
       int bestWins = 0;
       int bestColumn = -1;
       for (int column = 0; column < 5; ++column) {
           if (isValidMove(column, boardValues)) {
               int aiWins = 0;
               for (int runs = 0; runs < 100; ++runs) {
                   System.out.println("run=" + runs);
                   boolean testGameOver = false;
                   char[][] testBoard = copyBoard();
                   
                   int row = placePiece('X', column, testBoard);
                   testGameOver = checkWin(testBoard, row, column);
                   
                   if (testGameOver) {
                       aiWins++;
                   }
                   
                   while (!testGameOver && !isBoardFull(testBoard)) {
                       //displayBoard(testBoard);
                       int plCol = randMove(testBoard);
                       int plRow = placePiece('O', plCol, testBoard);
                       testGameOver = checkWin(testBoard, plRow, plCol);
                       
                       if (testGameOver || isBoardFull(testBoard)) {
                           break;
                       }
                       
                       int aiCol = randMove(testBoard);
                       int aiRow = placePiece('X', aiCol, testBoard);
                       testGameOver = checkWin(testBoard, aiRow, aiCol);
                       
                       if (testGameOver) {
                           aiWins++;
                           break;
                       }
                   }
                   
                   System.out.println("aiWins=" + aiWins);
                   System.out.println("bestWins=" + bestWins);
                   System.out.println("bestColumn=" + bestColumn);
                   System.out.println("column=" + column);

                   if (aiWins > bestWins) {
                       bestWins = aiWins;
                       bestColumn = column;
                   }
                   
                   if (bestWins == 0) {
                       for (int c = 0; c < 5; ++c) {
                           if (isValidMove(c, testBoard)) {
                               bestColumn = column;
                               break;
                           }
                       }
                   }
               }
           }
       }
       
       this.column = bestColumn;
       this.row = placePiece('X', bestColumn, boardValues);
   }
   
   private void evaluateColumn(StringBuilder str, char[][] board, int column){
       str.append(" C ");
       for(int r = 0; r < board.length; r++){
           str.append(board[r][column]);
        }       
   }
   private void evaluateRow(StringBuilder str, char[][] board, int row){
       str.append(" R ");
       for(int c = 0; c < board[row].length; c++){
           str.append(board[row][c]);
       }       
   }
   private void evaluateDiagUp(StringBuilder str, char[][] board, int r, int c){
        int row = r;
        int column = c;

        if (row <= column) {
            int up = column - row;
            row = 0;
            str.append(" U ");
            for (; up < board[row].length; up++) {
                str.append(board[row][up]);
                if (row < 4) {
                    row++;
                }
            }
        } else {
            int up = row - column;
            column = 0;
            str.append(" U ");
            for (; up < board.length; up++) {
                str.append(board[up][column]);
                if (column < 4) {
                    column++;
                }
            }
        }
    }
   
   public void evaluateDiagDown(StringBuilder str, char[][] board){
       int row = this.row;
       int column = this.column;
        
        if(column + row <= 4){
            int down = row + column;         
            column = 0;            
            str.append(" D ");           
            for(; down >=0; down--){
                str.append(board[down][column]);
                if(column < 4){
                    column++;
                }                
            }
        }/*
        else{
            int down = row + column;
            column = 0;
            str.append(" U ");
            for(; up < boardValues.length; up++){
                str.append(boardValues[up][column]);
                if(column<4){
                    column++;
                }
            }
        }  */     
   }

    public boolean checkWin(char[][] board, int r, int c) {
        boolean win = false;
        StringBuilder str = new StringBuilder();

        evaluateColumn(str, board, c);
        evaluateRow(str, board, r);
        evaluateDiagUp(str, board, r, c);
        //evaluateDiagDown(str, boardValues);

        if (Pattern.matches(".*OOOO.*", str) || Pattern.matches(".*XXXX.*", str)) {
            win = true;
            //System.out.println("Winner");
        }

        //System.out.println(str);

        return win;
    }

   public boolean charStringCol(){
       boolean win = false;
       StringBuilder str = new StringBuilder();

       evaluateColumn(str, boardValues, this.column);
       evaluateRow(str, boardValues, this.row);
       evaluateDiagUp(str, boardValues, this.row, this.column);
       //evaluateDiagDown(str, boardValues);

       if (Pattern.matches(".*OOOO.*", str) || Pattern.matches(".*XXXX.*", str)) {
           win = true;
           System.out.println("Winner");
       }

       System.out.println(str);

       return win;       
    }
}
