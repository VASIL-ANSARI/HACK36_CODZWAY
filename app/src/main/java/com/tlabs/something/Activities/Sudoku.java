package com.tlabs.something.Activities;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tlabs.something.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class Sudoku extends AppCompatActivity {



    private class Cell{
        int value;
        boolean fixed;
        Button bt;

        public Cell(int initvalue, Context THIS)
        {
            value=initvalue;
            fixed= value != 0;
            bt=new Button(THIS);
            if(fixed) bt.setText(String.valueOf(value));
            else bt.setTextColor(Color.BLUE);
            bt.setOnClickListener(view -> {
                if(fixed) return;
                value++;
                if(value>9) value=1;
                bt.setText(String.valueOf(value));
                if(correct()){
                    tv.setText("");
                }else {

                    tv.setText("There is a repeated digit");

                }
            });
        }
    }

    boolean incorrect(int i1, int j1, int i2, int j2)
    {
        boolean[] seen=new boolean[10];
        for(int i=1;i<=9;i++)
            seen[i]=false;
        for(int i=i1;i<i2;i++) {
            for (int j = j1; j < j2; j++)
            {
                int value=table[i][j].value;
                if(value!=0)
                {
                    if(seen[value]) return true;
                    seen[value]=true;
                }
            }
        }
        return false;
    }
    boolean correct()
    {
        for(int i=0;i<9;i++)
            if(incorrect(i, 0, i + 1, 9)) return false;
        for(int j=0;j<9;j++)
            if(incorrect(0, j, 9, j + 1)) return false;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(incorrect(3 * i, 3 * j, 3 * i + 3, 3 * j + 3))
                    return false;
        return true;
    }
    Sudoku.Cell[][] table;
    String input1,input2,input3,input4;
    int upperbound = 4;
    TableLayout tl;
    TextView tv,timer;
    LinearLayout linlay;
    String timer_time="00:00:00";
    Handler hand = new Handler();
    long seconds=0;

    Runnable run = this::updateTime;

    public void updateTime() {
        seconds++;
        Date d = new Date(seconds * 1000L);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        String time = df.format(d);
        timer.setText("Timer Running: "+time);
        hand.postDelayed(run, 1000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer=new TextView(this);
        LinearLayout.LayoutParams nlparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width Of The TextView
                LinearLayout.LayoutParams.WRAP_CONTENT); // Height Of The TextView
        timer.setLayoutParams(nlparams);
        timer.setPadding(50,0,0,0);
        timer.setTextColor(Color.parseColor("#0000FF"));
        timer.setTextSize(30);
        timer.setText("Timer Running: "+timer_time);
        hand.postDelayed(run, 1000);


        input1= "2 9 ? 7 4 3 8 6 1 "+
                "4 ? 1 8 6 5 9 ? 7 "+
                "8 7 6 1 9 2 5 4 3 "+
                "3 8 7 4 5 9 2 1 6 "+
                "6 1 2 3 ? 7 4 ? 5 "+
                "? 4 9 2 ? 6 7 3 8 "+
                "? ? 3 5 2 4 1 8 9 "+
                "9 2 8 6 7 1 ? 5 4 "+
                "1 5 4 9 3 ? 6 7 2 ";
        input2= "? ? ? 2 6 ? 7 ? 1 "+
                "6 8 ? ? 7 ? ? 9 ? "+
                "1 9 ? ? ? 4 5 ? ? "+
                "3 8 ? 1 ? ? ? 4 ? "+
                "8 ? 4 6 ? 2 9 ? ? "+
                "? 5 ? ? ? 3 ? 2 8 "+
                "? ? 9 3 ? ? ? 7 4 "+
                "? 4 ? ? 5 ? ? 3 6 "+
                "7 ? 3 ? 1 8 ? ? ? ";
        input3= "1 ? ? 4 8 9 ? ? 6 "+
                "7 3 ? ? ? ? ? 4 ? "+
                "? ? ? ? ? 1 2 9 5 "+
                "? ? 7 1 2 ? 6 ? ? "+
                "5 ? ? 7 ? 3 ? ? 8 "+
                "? ? 6 ? 9 5 7 ? ? "+
                "9 1 4 6 ? ? ? ? ? "+
                "? 2 ? ? ? ? ? 3 7 "+
                "8 ? ? 5 1 2 ? ? 4 ";
        input4= "2 ? ? 3 ? ? ? ? ? "+
                "8 ? 4 ? 6 2 ? ? 3 "+
                "? 1 3 8 ? ? 2 ? ? "+
                "? ? ? ? 2 ? 3 9 ? "+
                "5 ? 7 ? ? ? 6 2 1 "+
                "? 3 2 ? ? 6 ? ? ? "+
                "? 2 ? ? ? 9 1 4 ? "+
                "6 ? 1 2 5 ? 8 ? 9 "+
                "? ? ? ? ? 1 ? ? 2 ";

        Random rand = new Random();
        String[] split;
        int int_random = rand.nextInt(upperbound);
        if(int_random==3)
        {split=input3.split(" ");}
        else
        {split=input4.split(" ");}
        table=new Sudoku.Cell[9][9];
        tl=new TableLayout(this);
        for (int i=0;i<9;i++)
        {
            TableRow tr=new TableRow(this);
            for(int j=0;j<9;j++)
            {
                String s=split[i*9+j];
                char c=s.charAt(0);
                table[i][j]=new Sudoku.Cell(c=='?'?0:c-'0',this);
                tr.addView(table[i][j].bt);
            }
            tl.addView(tr);
        }
        tl.setShrinkAllColumns(true);
        tl.setStretchAllColumns(true);
        tv=new TextView(this);
        tv.setTextColor(Color.parseColor("#000000"));
        tv.setPadding(10,10,0,10);
        linlay=new LinearLayout(this);
        linlay.addView(tl);
        linlay.addView(tv);
        linlay.addView(timer);
        linlay.setPadding(2,20,2,0);
        linlay.setOrientation(LinearLayout.VERTICAL);
        setContentView(linlay);
    }
}