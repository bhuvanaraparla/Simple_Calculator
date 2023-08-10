package com.example.simple_calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView auxiliaryText; // TextView for auxiliary information
    private double currentValue = 0;
    private double storedValue = 0;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.e1);
        auxiliaryText = findViewById(R.id.e2);

        // Initialize buttons and set onClickListeners
        initializeButtons();
    }

    private void initializeButtons() {
        int[] buttonIds = {
                R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8, R.id.b9,
                R.id.add, R.id.sub, R.id.mul, R.id.div, R.id.modulo, R.id.res, R.id.clear, R.id.decimal,
                R.id.sign
        };

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick(v);
                }
            });
        }
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "0": case "1": case "2": case "3": case "4":
            case "5": case "6": case "7": case "8": case "9":
                onDigitClick(buttonText);
                break;
            case "+": case "-": case "*": case "/": case "%":
                onOperatorClick(buttonText);
                break;
            case "=":
                onEqualClick();
                break;
            case "C":
                onClearClick();
                break;
            case ".":
                onDecimalClick();
                break;
            case "+/-":
                onSignClick();
                break;
        }
    }

    private void onDigitClick(String digit) {
        String currentInput = inputText.getText().toString();

        if (currentInput.equals("0")) {
            inputText.setText(digit);
        } else {
            inputText.append(digit);
        }
    }

    private void onDecimalClick() {
        if (!inputText.getText().toString().contains(".")) {
            inputText.append(".");
        }
    }

    private void onClearClick() {
        inputText.setText("0");
        auxiliaryText.setText(""); // Clear auxiliary text
        currentValue = 0;
        storedValue = 0;
        operator = "";
    }

    private void onOperatorClick(String selectedOperator) {
        operator = selectedOperator;
        storedValue = Double.parseDouble(inputText.getText().toString());
        inputText.setText("0");
        auxiliaryText.setText(""); // Clear auxiliary text
    }

    private void onEqualClick() {
        if (!operator.isEmpty()) {
            currentValue = Double.parseDouble(inputText.getText().toString());

            switch (operator) {
                case "+":
                    currentValue = storedValue + currentValue;
                    break;
                case "-":
                    currentValue = storedValue - currentValue;
                    break;
                case "*":
                    currentValue = storedValue * currentValue;
                    break;
                case "/":
                    if (currentValue != 0) {
                        currentValue = storedValue / currentValue;
                    } else {
                        auxiliaryText.setText("Error");
                        return;
                    }
                    break;
                case "%":
                    if (currentValue != 0) {
                        currentValue = storedValue % currentValue;
                    } else {
                        auxiliaryText.setText("Error");
                        return;
                    }
                    break;
            }

            inputText.setText(String.valueOf(currentValue));
            operator = "";
            auxiliaryText.setText(""); // Clear auxiliary text
        }
    }

    private void onSignClick() {
        double current = Double.parseDouble(inputText.getText().toString());
        inputText.setText(String.valueOf(-current));
    }
}
