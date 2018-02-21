package com.sam1.tudelftsid.myapplication;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Handler;

import userInterface.MyShadowBuilder;


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
    private Button columnButton;
    private int interactionMode;
    private Button newGameButton;

    private FourInARowGame fourInARowGame= new FourInARowGame();
    View.OnLongClickListener longListen = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {
            if (interactionMode == R.id.drag_and_drop) {
                MyShadowBuilder dragShadow = new MyShadowBuilder(view, mycolor);
                //initiates the class
                view.startDrag(null, dragShadow, view, 0);
                return false;
            } else return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options_menu, menu); // use the xml file
        menu.findItem(R.id.buttons).setChecked(true); // set initial playing mode
        interactionMode = R.id.buttons; // initial interaction mode.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // We use the (unique) id of the menu items to set the selected interaction mode:
        interactionMode = item.getItemId();
        item.setChecked(true); // set menu item selected

        return true;
    }

    public void newGameButtonClicked(View view) {
        recreate();
    }

    public class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    //dropText.setTextColor(Color.GREEN);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    //dropText.setTextColor(Color.RED);
                    break;

                case DragEvent.ACTION_DROP:
                    int column = Integer.parseInt(v.getTag().toString()); //convert tag to number
                    View columnButton =
                            ((ViewGroup) boardTableLayout.getChildAt(6)).getChildAt(column - 1); // pass the button at the bottom of the column as view
                    playDisc(columnButton, column); // play the disc in the column where it was dropped.
                    break;
            }
            return true;
        }

    }

    public void info(String info){
        infoTextView.setText(info);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        infoTextView = (TextView) findViewById(R.id.info_textview);
        boardTableLayout = (TableLayout)findViewById(R.id.board_tablelayout);
        colourButtons(mycolor);
        infoTextView.setOnLongClickListener(longListen);
        MyDragListener dragListener = new MyDragListener();
        findViewById(R.id.imageView1).setOnDragListener(dragListener);
        findViewById(R.id.imageView2).setOnDragListener(dragListener);
        findViewById(R.id.imageView3).setOnDragListener(dragListener);
        findViewById(R.id.imageView4).setOnDragListener(dragListener);
        findViewById(R.id.imageView5).setOnDragListener(dragListener);
        findViewById(R.id.imageView6).setOnDragListener(dragListener);
        findViewById(R.id.imageView7).setOnDragListener(dragListener);
        newGameButton = (Button) findViewById(R.id.new_game_button);
        info("Player 1 to play first!");
    }

    private void colourButtons(int color) {
        TableRow tableButtonRow = (TableRow) boardTableLayout.getChildAt(6);
        for (int columnindex = 6; columnindex >= 0; columnindex--)
            tableButtonRow.getChildAt(columnindex).setBackgroundColor(color);
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
        newGameButton.setVisibility(View.VISIBLE);
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

    public void columnButtonClicked(View view) {
        if (interactionMode == R.id.buttons) {
            columnButton = (Button) view;
            column = Integer.parseInt(columnButton.getText().toString());
            // further code to make the move and process the row returned
            playDisc(columnButton, column);
        }
    }

    public void playDisc(View columnButton, int column) {
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
        colourButtons(mycolor);
    }
}
