package com.senderman.simplestbullsandcows;

import android.util.StringBuilderPrinter;
import android.widget.Toast;

/**
 * Created by root on 24.04.17.
 */

public class Game {

    private MainActivity main;
    private final int MAX_COUNT = 4;
    private int attempts;
    private int answer;
    private int[] computer;
    private StringBuilder builder = new StringBuilder();
    private boolean end;


    Game(MainActivity main) {
        this.main = main;
    }

    void start() {
        attempts = 10;
        answer = generateRandom();
        computer = split(answer);
        end = false;
        builder.setLength(0);
        main.historyTv.setText("");
        Toast.makeText(main, R.string.new_game, Toast.LENGTH_SHORT).show();
    }

    void check(int number) {
        if (end) {
            Toast.makeText(main, R.string.need_new_game, Toast.LENGTH_SHORT).show();
            return;
        }
        if (attempts == 1) {
            builder.append(String.format(main.getString(R.string.no_attempts), answer));
            main.historyTv.setText(builder);
            end = true;
            return;
        }
        if (matchFound(split(number))) {
            Toast.makeText(main, R.string.unique_only, Toast.LENGTH_LONG).show();
            return;
        }
        int[] results = calculate(split(number));
        if (results[0] == MAX_COUNT) {
            builder.append(String.format(main.getString(R.string.win), answer));
            main.historyTv.setText(builder);
            end = true;
        } else {
            attempts--;
            builder.append(String.format(main.getString(R.string.history), results[0], results[1], attempts, number));
            main.historyTv.setText(builder);
        }

    }

    private int generateRandom() {
        int random;
        do {
            random = 1000 + (int) (Math.random() * ((9999 - 1000) + 1));
        } while (matchFound(split(random)));
        return random;
    }

    // int to int[] :)
    private int[] split(int num) {
        int[] result = new int[MAX_COUNT];
        for (int i = 0; i < MAX_COUNT; i++) {
            int t = num % 10;
            result[MAX_COUNT - 1 - i] = t;
            num /= 10;
        }
        return result;
    }

    // check that array contains only unique numbers
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

    //calculate bulls and cows
    private int[] calculate(int[] player) {
        int bulls = 0, cows = 0;
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
        return new int[]{bulls, cows};
    }
}
