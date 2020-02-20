package com.maayanisraelovitz.connect3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 0= yellow, 1= red
    int activePlayer = 0;
    boolean gameIsActive = true;
    // 2 is unplayed
    int[] gameState= {2,2,2,2,2,2,2,2,2};
    int [][] winningPossitions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

    public void dropIn (View view){
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameIsActive && gameState[tappedCounter] == 2) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            for (int[] winningPosition: winningPossitions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    //someone has won
                    gameIsActive = false;
                    String winner = "Red";
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Yellow";
                    }

                    TextView winnerMessage = (TextView) findViewById(R.id.winner);
                    winnerMessage.setText(winner + " has won!");
                    LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    playAgainLayout.setVisibility(View.VISIBLE);

                }
            }
            if (gameIsActive) {
                boolean gameIsOver = true;
                for (int i=0 ; i<gameState.length ; i++)
                    if (gameState[i]==2)
                        gameIsOver = false;

                if (gameIsOver) {
                    TextView winnerMessage = (TextView) findViewById(R.id.winner);
                    winnerMessage.setText("It's a draw");
                    LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
                    playAgainLayout.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    public void playAgain (View view){
        LinearLayout playAgainLayout = (LinearLayout) findViewById(R.id.playAgainLayout);
        playAgainLayout.setVisibility(View.INVISIBLE);
        gameIsActive = true;
        activePlayer = 0;
        for (int i=0 ; i<gameState.length ; i++)
            gameState[i]= 2;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for (int i=0 ; i<gridLayout.getChildCount(); i++)
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
