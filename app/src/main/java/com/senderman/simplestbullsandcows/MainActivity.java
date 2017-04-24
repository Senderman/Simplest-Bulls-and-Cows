package com.senderman.simplestbullsandcows;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MenuItem.OnMenuItemClickListener {

    // подготавливаем переменные
    public TextView historyTv;
    private EditText guessEt;
    Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        historyTv = (TextView) findViewById(R.id.historyTv);
        Button checkButton = (Button) findViewById(R.id.checkButton);
        guessEt = (EditText) findViewById(R.id.guessEt);
        checkButton.setOnClickListener(this);
        game = new Game(this);
        game.start();
    }

    @Override
    public void onClick(View v) {
        if (guessEt.getText().length() != 4) {
            Toast.makeText(this, R.string.no_input, Toast.LENGTH_SHORT).show();
        } else {
            String input = guessEt.getText().toString();
            guessEt.setText("");
            game.check(Integer.parseInt(input));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem restartItem = menu.add(R.string.menu_new_game);
        restartItem.setIcon(android.R.drawable.ic_menu_rotate);
        restartItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        restartItem.setOnMenuItemClickListener(this);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        game.start();
        return true;
    }
}
