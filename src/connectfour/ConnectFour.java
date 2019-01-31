/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectfour;

/**
 *
 * @author Wife
 */
public class ConnectFour {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Board b = new Board();
        //b.displayBoard();
        //System.out.println("\n\n");
        boolean quit = false;
        
        while(true){
            b.displayBoard();
            if(quit){
                break;
            }
            b.selectColumn();
            b.enterColumnSelected();
            quit = b.charStringCol();
            
        }
    }    
}
