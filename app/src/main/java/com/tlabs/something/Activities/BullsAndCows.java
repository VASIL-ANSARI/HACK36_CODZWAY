package com.tlabs.something.Activities;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tlabs.something.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BullsAndCows extends AppCompatActivity implements View.OnClickListener{

    //    private static final int MAX_GUESS = 7;
    private int guessNumber , secretOnesDigit,secretTensDigit,secretHundreadDigit,onesDigit,tensDigit,hundreadDigit,score,cows,bulls;
    private TextView setOnesDigit,setTensDigit,setHundreadDigit,setScore;
    private final int[] hasNumber = new int[10];
    private final TextView[] guesses  = new TextView[7];

    List<List<Integer>> A=new ArrayList<>();
    void solve(int[] vis,int l,List<Integer> curr){
        if(l==3){
            A.add(curr);
            return;
        }
        for (int i=0;i<10;i++){
            if(vis[i]==0){
                vis[i]=1;
                List<Integer> tmp = new ArrayList<>(curr);
                tmp.add(i);
                solve(vis, l+1, tmp);
                vis[i]=0;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulls_and_cows);

        List<Integer> curr=new ArrayList<>();
        int[] vis=new int[10];
        Arrays.fill(vis,0);
        solve(vis,0,curr);


        guessNumber=1;
        onesDigit=0;
        tensDigit=0;
        hundreadDigit=0;
        score=0;
        Button incH = findViewById(R.id.inc_1);
        Button incT = findViewById(R.id.inc_2);
        Button incO = findViewById(R.id.inc_3);
        Button decH = findViewById(R.id.dec_1);
        Button decT = findViewById(R.id.dec_2);
        Button decO = findViewById(R.id.dec_3);
        setOnesDigit = findViewById(R.id.dig_o);
        setTensDigit = findViewById(R.id.dig_t);
        setHundreadDigit = findViewById(R.id.dig_h);
        Button restart = findViewById(R.id.restart);
        Button submit = findViewById(R.id.submitGuess);
        setScore = findViewById(R.id.score);

        for(int i=0;i<10;i++)
            hasNumber[i]=0;

        findRandomNumber();
        updateScore();
        updateInputField();


        for(int i=0;i<7;i++){
            String textViewID  ="guess_" + (i + 1);
            int resourceID = getResources().getIdentifier(textViewID,"id" , getPackageName());
            guesses[i]= findViewById(resourceID);
            guesses[i].setOnClickListener(this);
            guesses[i].setText("");

        }

        setHundreadDigit.setText(Integer.toString(hundreadDigit));
        setTensDigit.setText(Integer.toString(tensDigit));
        setOnesDigit.setText(Integer.toString(onesDigit));

        incH.setOnClickListener(v -> {
            hundreadDigit=(hundreadDigit+1)%10;
            setHundreadDigit.setText(Integer.toString(hundreadDigit));
        });

        decH.setOnClickListener(v -> {
            hundreadDigit = hundreadDigit == 0 ? 9 : hundreadDigit-1 ;
            setHundreadDigit.setText(Integer.toString(hundreadDigit));
        });
        incT.setOnClickListener(v -> {
            tensDigit=(tensDigit+1)%10;
            setTensDigit.setText(Integer.toString(tensDigit));
        });

        decT.setOnClickListener(v -> {
            tensDigit = tensDigit == 0 ? 9 : tensDigit-1 ;
            setTensDigit.setText(Integer.toString(tensDigit));
        });
        incO.setOnClickListener(v -> {
            onesDigit=(onesDigit+1)%10;
            setOnesDigit.setText(Integer.toString(onesDigit));
        });

        decO.setOnClickListener(v -> {
            onesDigit = onesDigit == 0 ? 9 : onesDigit-1 ;
            setOnesDigit.setText(Integer.toString(onesDigit));
        });

        restart.setOnClickListener(v -> {
            onesDigit=0;
            tensDigit=0;
            hundreadDigit=0;
            score=0;
            guessNumber=1;
            updateInputField();
            updateScore();
            for(int i=0;i<10;i++)
                hasNumber[i]=0;
            for(int i=0;i<7;i++)
                guesses[i].setText("");
            findRandomNumber();
        });

        submit.setOnClickListener(v -> {
            if(onesDigit==tensDigit||onesDigit==hundreadDigit||tensDigit==hundreadDigit){
                Toast.makeText(getApplicationContext(),"Please provide valid input !!! All digits must be unique",Toast.LENGTH_SHORT).show();
            }
            else {
                countCowsAndBulls();
                if(bulls==3){
                    // WIN
                    Toast.makeText(getApplicationContext(),"YOU WIN !",Toast.LENGTH_SHORT).show();
                    score++;
                    onesDigit=0;
                    tensDigit=0;
                    hundreadDigit=0;
                    guessNumber=1;
                    updateInputField();
                    updateScore();
                    for(int i=0;i<10;i++)
                        hasNumber[i]=0;
                    for(int i=0;i<7;i++)
                        guesses[i].setText("");
                    findRandomNumber();

                }
                else {
                    String currentGuess = hundreadDigit + Integer.toString(tensDigit) + onesDigit;
                    guesses[guessNumber-1].setText(currentGuess+"   "+"Cows = "+Integer.toString(cows)+"   "+"Bulls = "+Integer.toString(bulls));

                    guessNumber++;
                    if(guessNumber>7){
                        // RESTART
                        String secretNumber = hundreadDigit +Integer.toString(tensDigit)+ onesDigit;
                        Toast.makeText(getApplicationContext(),"YOU LOSE ! The Secret number was "+secretNumber,Toast.LENGTH_SHORT).show();
                        onesDigit=0;
                        tensDigit=0;
                        hundreadDigit=0;
                        score=0;
                        guessNumber=1;
                        updateInputField();
                        updateScore();
                        for(int i=0;i<10;i++)
                            hasNumber[i]=0;
                        for(int i=0;i<7;i++)
                            guesses[i].setText("");
                        findRandomNumber();

                    }
                }
            }
        });


    }

    private void updateScore(){
        setScore.setText(Integer.toString(score));
    }
    private void updateInputField(){
        setOnesDigit.setText(Integer.toString(onesDigit));
        setTensDigit.setText(Integer.toString(tensDigit));
        setHundreadDigit.setText(Integer.toString(hundreadDigit));
    }
    private void findRandomNumber(){
        secretOnesDigit=3;
        secretTensDigit=2;
        secretHundreadDigit=1;

        int min =0,max=A.size();



        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        secretOnesDigit= A.get(random_int).get(0) ;
        secretTensDigit= A.get(random_int).get(1) ;
        secretHundreadDigit=  A.get(random_int).get(2) ;


        hasNumber[secretHundreadDigit]=1;
        hasNumber[secretTensDigit]=1;
        hasNumber[secretOnesDigit]=1;
    }
    private void countCowsAndBulls(){
        bulls=0;
        cows=0;
        if(onesDigit==secretOnesDigit)bulls++;
        else if(hasNumber[onesDigit]==1)cows++;

        if(tensDigit==secretTensDigit)bulls++;
        else if(hasNumber[tensDigit]==1)cows++;

        if(hundreadDigit==secretHundreadDigit)bulls++;
        else if(hasNumber[hundreadDigit]==1)cows++;
    }

    @Override
    public void onClick(View v) {
    }
}