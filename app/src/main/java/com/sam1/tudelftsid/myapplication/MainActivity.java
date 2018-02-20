package com.sam1.tudelftsid.myapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Handler;


public class MainActivity extends AppCompatActivity {

    private Handler blinker0 = new Handler();
    private Handler blinker1 = new Handler();
    private Handler blinker2 = new Handler();
    private Handler blinker3 = new Handler();
    private class Blinker implements Runnable{
        public void run() {
            // blinking
            int j = 0;
            System.out.println(winningDiscs);
            while (j < 4) {
                ImageView cell = getBoardImageView((winningDiscs[j][0]),(winningDiscs[j][1]));
                cell.setVisibility(cell.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
                if (j==0) blinker0.postDelayed(this, 700);
                if (j==1) blinker1.postDelayed(this, 700);
                if (j==2) blinker2.postDelayed(this, 700);
                if (j==3) blinker3.postDelayed(this, 700);
                j++;
            }
        }
    }

    private TextView infoTextView;
    private TableLayout boardTableLayout;
    private int [][] winningDiscs = new int[4][2];
    int column;
    int mycolor= Color.RED;
    Button columnButton;
    private FourInARowGame fourInARowGame= new FourInARowGame();

    public void info(String info){
        infoTextView.setText(info);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTextView = (TextView) findViewById(R.id.info_textview);
        boardTableLayout = (TableLayout)findViewById(R.id.board_tablelayout);
        info("Player 1 to play first!");
    }
    private ImageView getBoardImageView(int rowIndex, int columnIndex){
        TableRow tableRow=(TableRow)boardTableLayout.getChildAt(rowIndex);
        return (ImageView)tableRow.getChildAt(columnIndex);
    }

    private void showDisc(int row, int column, int color){
        // set the background colour
        getBoardImageView(row-1,column-1).setBackgroundColor(color);
    }
    private void endOfGame(int result) {
        // disable all buttons
        TableRow tableButtonRow = (TableRow) boardTableLayout.getChildAt(6);
        for (int columnindex = 6; columnindex >= 0; columnindex--)
            tableButtonRow.getChildAt(columnindex).setEnabled(false);
        // inform user
        if (result == 3) info("Nobody won!");
        else{
            winningDiscs = fourInARowGame.getWinningDiscs();
            info("Player " + result + " won!");
            blinker0.post(new Blinker());
            blinker1.post(new Blinker());
            blinker2.post(new Blinker());
            blinker3.post(new Blinker());
        }
    }


    public void columnButtonClicked(View view){
        columnButton=(Button)view;
        column=Integer.parseInt(columnButton.getText().toString());
        // further code to make the move and process the row returned
        int row = fourInARowGame.dropDisc(column);
        /*
        while(row>0 && ((ColorDrawable)getBoardImageView(row-1,
                column-1).getBackground()).getColor()!=Color.parseColor("#bbbbbb"))
            row--; //decrement row
        */
        if (row==1) columnButton.setEnabled(false);
        showDisc(row,column,mycolor);
        int gameResult = fourInARowGame.getResult();
        if (gameResult>0) {
            endOfGame(gameResult);
            // add blinkers
            return;
        }
        if (mycolor==Color.RED){
            //showDisc(row, column, mycolor);
            mycolor=Color.BLUE;
            info("Next move by Player2");
        } else {
            //showDisc(row,column,mycolor);
            mycolor=Color.RED;
            info("Next move by Player1");
        }
    }
}
