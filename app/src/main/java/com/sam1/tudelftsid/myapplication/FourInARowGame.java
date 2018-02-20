package com.sam1.tudelftsid.myapplication;

public class FourInARowGame {

    private int[] numberOfEmptyColumnCells={6,6,6,6,6,6,6};
    private int[][] board = new int[6][7];
    private int playerId = 1, playerWhoWon = 0,emptyColumnCells,column;
    private int[][] winningDiscs = new int[4][2];
    private int counter = 0;

    private void showBoard() {
        System.out.println("Four in a row game board:");
        for (int rowindex=0;rowindex<6;rowindex++) {
            for(int columnindex=0;columnindex<7;columnindex++)
                System.out.print(String.valueOf(board[rowindex][columnindex]));
            System.out.println();
        }
    }

    private boolean currentPlayerWon() {
       // if (fourInARowHorizontally()||fourInARowVertically()||fourInARowReverseDiagonally()||fourInARowDiagonally()) return true;
        if (fourInARowHorizontally()) return true;
        //if (fourInARowVertically()) return true;
       // if (fourInARowDiagonally()) return true;
       // if (fourInARowReverseDiagonally()) return true;
        return false;
    }
    private boolean isBoardFull() {
        for (int columnindex = 6;columnindex>=0;columnindex--)
            if (numberOfEmptyColumnCells[columnindex]>0)
                return false;
        return true;
    }
    private void checkForEndOfGame() {
        if (currentPlayerWon()) playerWhoWon = playerId;
        else
        if (isBoardFull()) playerWhoWon = 3;
        System.out.println("player who won: "+playerWhoWon+".");
    }
        // public methods
        public int getResult() {
            return playerWhoWon;
    }
    private void registerDisc(int rowindex, int columnindex){
        if (winningDiscs==null){
            winningDiscs=new int[4][2];
            counter = 0;
        }
        winningDiscs[counter][0] = rowindex;
        winningDiscs[counter][1] = columnindex;
        counter++;
    }
    // checkFOrEndOfGame() helper methods
    private boolean fourInARowHorizontally(){
        int lefttofind=3, rowindex=emptyColumnCells-1;
        // check to the left
        int columnindex=column-2; // first column index to the left
        registerDisc(rowindex,columnindex+1);
        while (lefttofind>0 && columnindex>=0 && board[rowindex][columnindex]==playerId) {
            registerDisc(rowindex,columnindex);
            columnindex--;
            lefttofind--;
        }
        if (lefttofind==0) return true;

        // check to the right
        columnindex=column; // first column index to the right
        while (lefttofind>0 && columnindex<=6 && board[rowindex][columnindex]==playerId) {
            registerDisc(rowindex,columnindex);
            columnindex++;
            lefttofind--;
        }

        if (lefttofind!=0) winningDiscs = null;

        return (lefttofind==0);
    }

    public int[][] getWinningDiscs() {
        return winningDiscs;
    }

    private boolean fourInARowVertically(){
        int lefttofind=3, columnindex=column-1;
        // check up
        int rowindex=emptyColumnCells-2;
        while (lefttofind>0 && rowindex>=0 && board[rowindex][columnindex]==playerId) {
            rowindex--;
            lefttofind--;
        }

        if (lefttofind==0) return true;
        // check down
        rowindex=emptyColumnCells; // first column index to the right
        while (lefttofind>0 && rowindex<=5 && board[rowindex][columnindex]==playerId) {
            rowindex++;
            lefttofind--;
        }
        return (lefttofind==0);
    }

    private boolean fourInARowDiagonally() {
        int lefttofind=3, rowindex, columnindex;
        // check left and up
        rowindex=emptyColumnCells-2; columnindex=column-2;
        while (lefttofind>0 && rowindex>=0 && columnindex>=0 &&
                board[rowindex][columnindex]==playerId) {
            rowindex--;
            columnindex--;
            lefttofind--;
        }
        if (lefttofind==0) return true;
        // check right and down
        rowindex=emptyColumnCells; columnindex=column;
        while (lefttofind>0 && rowindex<=5 && columnindex<=6 &&
                board[rowindex][columnindex]==playerId) {
            rowindex++;
            columnindex++;
            lefttofind--;
        }
        return (lefttofind==0);
    }
    private boolean fourInARowReverseDiagonally() {
        int lefttofind=3, rowindex, columnindex;
        // check right and up
        rowindex=emptyColumnCells-2; columnindex=column;
        while (lefttofind>0 && rowindex>=0 && columnindex<=6 &&
                board[rowindex][columnindex]==playerId) {
            rowindex--;
            columnindex++;
            lefttofind--;
        }
        if (lefttofind==0) return true;
        // check left and down
        rowindex=emptyColumnCells; columnindex=column-2;
        while (lefttofind>0 && rowindex<=5 && columnindex>=0 &&
                board[rowindex][columnindex]==playerId) {
            rowindex++;
            columnindex--;
            lefttofind--;
        }
        return (lefttofind==0);
    }

    public int dropDisc(int column){
        if (column>=1 && column<=7){
            this.column = column;
            emptyColumnCells=numberOfEmptyColumnCells[column-1]; // get number of empty column cells
            if (emptyColumnCells>0) { // column is NOT full yet
                numberOfEmptyColumnCells[column - 1] = emptyColumnCells - 1; //one less to go
                board[emptyColumnCells - 1][column - 1] = playerId;
                checkForEndOfGame();
                showBoard();
                playerId = (playerId == 1 ? 2 : 1);
            }
            return emptyColumnCells; // return the number of empty column cells we started out with
        }
        // some error
        return 0;
    }

}
