package com.senderman.simplestbullsandcows;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // подготавливаем переменные
    private TextView historyTv;
    private EditText guessEt;
    private final StringBuilder builder = new StringBuilder(); // StringBuilder - удобная вещь для создания строк
    private final int MAX_COUNT = 4;
    private int attempts = 10;
    private int cows = 0;
    private int bulls = 0;
    private final int answer = generateRandom();
    private final int[] computer = split(answer);


    @Override
    protected void onCreate(Bundle savedInstanceState) { // метод исполняется когда запускаешь прогу
        super.onCreate(savedInstanceState); // хз зачем, но пусть будет.
        setContentView(R.layout.activity_main); // соединяем этот java код с xml UI
        historyTv = (TextView) findViewById(R.id.historyTv);
        Button checkButton = (Button) findViewById(R.id.checkButton);
        guessEt = (EditText) findViewById(R.id.guessEt); // присваеваем id из хмл в java объекты
        Toast.makeText(this, R.string.generated, Toast.LENGTH_SHORT).show();
        checkButton.setOnClickListener(this); // ставим на кнопку обработчик событий
    }

    @Override
    public void onClick(View v) { // вызывается при нажатии на то, на что поставлен обработчик
        if (v.getId() == R.id.checkButton) {
            if (guessEt.getText().length() == 4) {
                int input = Integer.parseInt(guessEt.getText().toString()); // строку в int
                guessEt.setText(""); // очищаем поле
                if (attempts != 0) {
                    int[] player = split(input);
                    for (int i = 0; i < MAX_COUNT; i++) {
                        if (computer[i] == player[i]) {
                            bulls++;
                        } else {
                            for (int j = 0; j < MAX_COUNT; j++) {
                                if (player[i] == computer[j] && player[j] != computer[j]) {
                                    cows++;
                                }
                            }
                        }
                    }
                    if (bulls == MAX_COUNT) {
                        Toast.makeText(this, R.string.win, Toast.LENGTH_SHORT).show(); // Toast - всплывающее сообщение
                    } else {
                        builder.append(String.format(getString(R.string.history), bulls, cows, attempts, input)); // добавляем строчку истории
                        historyTv.setText(builder.toString()); // выводим историю
                        cows = 0;
                        bulls = 0;
                        attempts--;
                    }
                } else
                    Toast.makeText(this, R.string.no_attempts, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, R.string.no_input, Toast.LENGTH_SHORT).show();
        }
    }

    private int generateRandom() {
        int random;
        do {
            random = 1000 + (int) (Math.random() * ((9999 - 1000) + 1));
        } while(matchFound(split(random)));
        return random;
    }

    private int[] split(int num) {
        int[] result = new int[MAX_COUNT];
        for (int i = 0; i < MAX_COUNT; i++) {
            int t = num % 10;
            result[MAX_COUNT - 1 - i] = t;
            num /= 10;
        }
        return result;
    }

    private boolean matchFound(int[] array) {
        for (int i = 0; i < MAX_COUNT; i++) {
            for (int j = i + 1; j < MAX_COUNT; j++) {
                if (array[i] == array[j]) {
                    return true;
                }
            }
        }
        return false;
    }

}
