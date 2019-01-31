package connectfour;

import java.util.Scanner;
import java.util.regex.*;


public class Board {
    
    private char [][] boardValues = new char[5][5];
    private int column;
    private int row;
    private int counter = 0;
    private boolean rowConnect = false;
    private boolean colConnect = false;
    
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
               if(counter % 2 == 0){
                   boardValues[row][column] = 'O';                   
               }
               else{
                   boardValues[row][column] = 'X';                   
               }  
               counter++;
               break;
           }
       }
       
   }
   private void evaluateColumn(StringBuilder str){
       str.append(" C ");
       for(int c = 0; c < boardValues.length; c++){
           str.append(boardValues[c][column]);
        }       
   }
   private void evaluateRow(StringBuilder str){
       str.append(" R ");
       for(int r = 0; r < boardValues[row].length; r++){
           str.append(boardValues[row][r]);
       }       
   }
   private void evaluateDiagUp(StringBuilder str){
       int row = this.row;
        int column = this.column;
        
        if(row <= column){
            int up = column - row;            
            row = 0;            
            str.append(" U ");           
            for(; up < boardValues[row].length; up++){
                str.append(boardValues[row][up]);
                if(row <4){
                    row++;
                }                
            }
        }
        else{
            int up = row -column;
            column = 0;
            str.append(" U ");
            for(; up < boardValues.length; up++){
                str.append(boardValues[up][column]);
                if(column<4){
                    column++;
                }
            }
        }       
   }
   
   public void evaluateDiagDown(StringBuilder str){
       int row = this.row;
       int column = this.column;
        
        if(column + row <= 4){
            int down = row + column;         
            column = 0;            
            str.append(" D ");           
            for(; down >=0; down--){
                str.append(boardValues[down][column]);
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
   
   public boolean charStringCol(){
        StringBuilder str = new StringBuilder();
        evaluateColumn(str);
        evaluateRow(str);
        evaluateDiagUp(str);
        evaluateDiagDown(str);
        
        
        if(Pattern.matches(".*OOOO.*", str) || Pattern.matches(".*XXXX.*", str)){
            rowConnect = true;
            System.out.println("Winner");
        };
        System.out.println(str);
        System.out.println(rowConnect);        
        return rowConnect;        
    }
}
