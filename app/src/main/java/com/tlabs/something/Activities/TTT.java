package com.tlabs.something.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tlabs.something.R;

public class TTT extends AppCompatActivity implements View.OnClickListener {

    private TextView playerOneScore , playesrTwoScore, playerStatus;
    private int playerOneScoreCount,playerTwoScoreCount,roundCount;
    private final Button [] buttons = new Button[9];
    private Button resetGame ;
    boolean activePlayer;
    // player 1 => 0
    // player 2=> 1
    // empty => 2
    int [] gameState  = {2,2,2,2,2,2,2,2,2};
    int [][] winningPositions = {
            {0,1,2},{3,4,5},{6,7,8}, // rows
            {0,3,6},{1,4,7},{2,5,8}, // Columns
            {0,4,8},{2,4,6} // diag
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);


        playerOneScore = findViewById(R.id.playerOneScore);
        playesrTwoScore = findViewById(R.id.playerTwoScore);
        resetGame = findViewById(R.id.resetGame);
        playerStatus = findViewById(R.id.playerStatus);

        for(int i=0;i<buttons.length;++i){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID,"id" , getPackageName());
            buttons[i]= findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        roundCount = 0;
        activePlayer = true;
    }

    @Override
    public void onClick(View v) {
        //Log.i("test" , "btn Clicked");
        if(!((Button)v).getText().toString().equals("")){
            return;
        }

        String buttonID  = v.getResources().getResourceEntryName(v.getId());  // btn_2
        int gameStatePointer =Integer.parseInt(buttonID.substring(buttonID.length()-1));

        if(activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0 ;
        }
        else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1 ;
        }
        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player One Won !",Toast.LENGTH_SHORT).show();
            }
            else {
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this,"Player Two Won !",Toast.LENGTH_SHORT).show();
            }
            playAgain();

        }

        else if(roundCount == 9){
            Toast.makeText(this,"No Winner !",Toast.LENGTH_SHORT).show();
            playAgain();
        }
        else {
            activePlayer=!activePlayer;
        }

        if(playerTwoScoreCount > playerOneScoreCount){
            playerStatus.setText("Player Two is winning !");
        }
        else if(playerTwoScoreCount < playerOneScoreCount){
            playerStatus.setText("Player One is Winning !");
        }
        else {
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(v1 -> {
            playAgain();
            playerOneScoreCount = 0;
            playerTwoScoreCount = 0;
            playerStatus.setText("");
            updatePlayerScore();
        });



    }

    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition :winningPositions){
            if(gameState[winningPosition[0]]!=2 &&
                    gameState[winningPosition[0]]==gameState[winningPosition[1]]&&
                    gameState[winningPosition[1]]==gameState[winningPosition[2]]){
                winnerResult = true ;
                break;
            }
        }
        return winnerResult;

    }

    public  void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playesrTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        roundCount = 0 ;
        activePlayer = true ;

        for(int i=0;i<buttons.length;++i){
            gameState[i] = 2 ;
            buttons[i].setText("");
        }
    }

}