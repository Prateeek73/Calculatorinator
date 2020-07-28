package com.example.calulator_inator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int IS_NUMBER = 0;
    private final static int IS_OPERAND = 1;
    private final static int IS_OPEN_PARENTHESIS = 2;
    private final static int IS_CLOSE_PARENTHESIS = 3;
    private final static int IS_DOT = 4;
    private static final String TAG = "MainActivity";
    Button buttonNumber0;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    Button buttonNumber4;
    Button buttonNumber5;
    Button buttonNumber6;
    Button buttonNumber7;
    Button buttonNumber8;
    Button buttonNumber9;
    Button buttonClear;
    Button buttonParentheses;
    Button buttonPercent;
    Button buttonDivision;
    Button buttonMultiplication;
    Button buttonSubtraction;
    Button buttonAddition;
    Button buttonEqual;
    Button buttonDot;
    TextView textViewInputNumbers;
    ImageButton backspace;
    ScriptEngine scriptEngine;
    private int openParenthesis = 0;
    private boolean equalClicked = false;
    private String lastExpression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        scriptEngine = new ScriptEngineManager().getEngineByName("rhino");
        initializeWidgets();
        setOnClickListeners();
    }

    //initializing all the buttons
    private void initializeWidgets() {
        Log.d(TAG, "initializeWidgets: Initializing Widgets");
        //initialization of number buttons
        buttonNumber0 = findViewById(R.id.button_zero);
        buttonNumber1 = findViewById(R.id.button_one);
        buttonNumber2 = findViewById(R.id.button_two);
        buttonNumber3 = findViewById(R.id.button_three);
        buttonNumber4 = findViewById(R.id.button_four);
        buttonNumber5 = findViewById(R.id.button_five);
        buttonNumber6 = findViewById(R.id.button_six);
        buttonNumber7 = findViewById(R.id.button_seven);
        buttonNumber8 = findViewById(R.id.button_eight);
        buttonNumber9 = findViewById(R.id.button_nine);

        //initialization of other buttons
        buttonClear = findViewById(R.id.button_clear);
        buttonParentheses = findViewById(R.id.button_parentheses);
        buttonPercent = findViewById(R.id.button_percent);
        buttonDivision = findViewById(R.id.button_division);
        buttonMultiplication = findViewById(R.id.button_multiplication);
        buttonSubtraction = findViewById(R.id.button_subtraction);
        buttonAddition = findViewById(R.id.button_addition);
        buttonEqual = findViewById(R.id.button_equal);
        buttonDot = findViewById(R.id.button_dot);
        textViewInputNumbers = findViewById(R.id.textView_input_numbers);
        backspace = findViewById(R.id.button_backspace);
    }

    //setting on click listeners for  all the buttons
    private void setOnClickListeners() {
        Log.d(TAG, "setOnClickListeners: Initializing OnClickListeners");
        //setting on click listeners for all the numbers
        buttonNumber0.setOnClickListener(this);
        buttonNumber1.setOnClickListener(this);
        buttonNumber2.setOnClickListener(this);
        buttonNumber3.setOnClickListener(this);
        buttonNumber4.setOnClickListener(this);
        buttonNumber5.setOnClickListener(this);
        buttonNumber6.setOnClickListener(this);
        buttonNumber7.setOnClickListener(this);
        buttonNumber8.setOnClickListener(this);
        buttonNumber9.setOnClickListener(this);

        //setting on click listeners for all other buttons
        buttonClear.setOnClickListener(this);
        buttonParentheses.setOnClickListener(this);
        buttonPercent.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonMultiplication.setOnClickListener(this);
        buttonSubtraction.setOnClickListener(this);
        buttonAddition.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
        backspace.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: OnClick Activated for " + view.getId());
        switch (view.getId()) {
            case R.id.button_zero:
                if (addNumber("0")) equalClicked = false;
                break;
            case R.id.button_one:
                if (addNumber("1")) equalClicked = false;
                break;
            case R.id.button_two:
                if (addNumber("2")) equalClicked = false;
                break;
            case R.id.button_three:
                if (addNumber("3")) equalClicked = false;
                break;
            case R.id.button_four:
                if (addNumber("4")) equalClicked = false;
                break;
            case R.id.button_five:
                if (addNumber("5")) equalClicked = false;
                break;
            case R.id.button_six:
                if (addNumber("6")) equalClicked = false;
                break;
            case R.id.button_seven:
                if (addNumber("7")) equalClicked = false;
                break;
            case R.id.button_eight:
                if (addNumber("8")) equalClicked = false;
                break;
            case R.id.button_nine:
                if (addNumber("9")) equalClicked = false;
                break;
            case R.id.button_addition:
                if (addOperand("+")) equalClicked = false;
                break;
            case R.id.button_subtraction:
                if (addOperand("-")) equalClicked = false;
                break;
            case R.id.button_multiplication:
                if (addOperand("x")) equalClicked = false;
                break;
            case R.id.button_division:
                if (addOperand("\u00F7")) equalClicked = false;
                break;
            case R.id.button_percent:
                if (addOperand("%")) equalClicked = false;
                break;
            case R.id.button_dot:
                if (addDot()) equalClicked = false;
                break;
            case R.id.button_parentheses:
                if (addParenthesis()) equalClicked = false;
                break;
            case R.id.button_clear:
                textViewInputNumbers.setText("");
                openParenthesis = 0;
                equalClicked = false;
                break;
            case R.id.button_backspace:
                if (textViewInputNumbers.getText().length() > 0)
                    textViewInputNumbers.setText(textViewInputNumbers.getText().toString().substring(0, textViewInputNumbers.getText().length() - 1));
            case R.id.button_equal:
                if (textViewInputNumbers.getText() != null && !textViewInputNumbers.getText().equals(""))
                    calculate(textViewInputNumbers.getText().toString());
                break;
        }
    }

    //adding dot based on rules of maths
    @SuppressLint("SetTextI18n")
    private boolean addDot() {
        if (textViewInputNumbers.getText().length() == 0) {
            textViewInputNumbers.setText("0.");
            return true;
        } else if (checkLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_OPERAND) {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + "0.");
            return true;
        } else if (checkLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_NUMBER) {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + ".");
            return true;
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    private boolean addParenthesis() {
        int operationLength = textViewInputNumbers.getText().length();
        if (operationLength == 0) {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
            openParenthesis++;
            return true;
        } else if (openParenthesis > 0 && operationLength > 0) {
            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            switch (checkLastCharacter(lastInput)) {
                case IS_NUMBER:
                case IS_CLOSE_PARENTHESIS:
                    textViewInputNumbers.setText(textViewInputNumbers.getText() + ")");
                    openParenthesis--;
                    return true;
                case IS_OPERAND:
                case IS_OPEN_PARENTHESIS:
                    textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
                    openParenthesis++;
                    return true;
            }
        } else if (openParenthesis == 0 && operationLength > 0) {
            if (checkLastCharacter(textViewInputNumbers.getText().charAt(operationLength - 1) + "") == IS_OPERAND) {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + "(");
                openParenthesis++;
            } else {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + "x(");
                openParenthesis++;
            }
            return true;
        }
        return false;
    }

    //adding operand according to rules of mathematics
    @SuppressLint("SetTextI18n")
    private boolean addOperand(String operand) {
        int operationLength = textViewInputNumbers.getText().length();
        if (operationLength > 0) {
            String lastInput = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            if ((lastInput.equals("+") || lastInput.equals("-") || lastInput.equals("*") || lastInput.equals("\u00F7") || lastInput.equals("%") || lastInput.equals(("("))))
                Toast.makeText(getApplicationContext(), "Wrong format", Toast.LENGTH_LONG).show();
            else {
                textViewInputNumbers.setText(textViewInputNumbers.getText() + operand);
                equalClicked = false;
                lastExpression = "";
            }
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Format. Operand Without any numbers?", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    //adding numbers according to rules of mathematics
    @SuppressLint("SetTextI18n")
    private boolean addNumber(String number) {
        int operationLength = textViewInputNumbers.getText().length();
        if (operationLength > 0) {
            String lastCharacter = textViewInputNumbers.getText().charAt(operationLength - 1) + "";
            if (operationLength == 1 && checkLastCharacter(lastCharacter) == IS_NUMBER && lastCharacter.equals("0"))
                textViewInputNumbers.setText(number);
            else if (checkLastCharacter(lastCharacter) == IS_OPEN_PARENTHESIS)
                textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
            else if (checkLastCharacter(lastCharacter) == IS_CLOSE_PARENTHESIS || lastCharacter.equals("%"))
                textViewInputNumbers.setText(textViewInputNumbers.getText() + "x" + number);
            else if (checkLastCharacter(lastCharacter) == IS_NUMBER || checkLastCharacter(lastCharacter) == IS_OPERAND || checkLastCharacter(lastCharacter) == IS_DOT)
                textViewInputNumbers.setText(textViewInputNumbers.getText() + number);
        } else textViewInputNumbers.setText(number);
        return true;
    }


    private void calculate(String input) {
        String result;
        try {
            String temp = input;
            if (equalClicked) temp = input + lastExpression;
            else saveLastExpression(input);
            result = scriptEngine.eval(temp.replaceAll("%", "/100").replaceAll("x", "*").replaceAll("[^\\x00-\\x7F]", "/")).toString();
            BigDecimal decimal = new BigDecimal(result);
            result = decimal.setScale(8, BigDecimal.ROUND_HALF_UP).toPlainString();
            equalClicked = true;

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Wrong Format", Toast.LENGTH_SHORT).show();
            return;
        }

        if (result.equals("Infinity")) {
            Toast.makeText(getApplicationContext(), "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
            textViewInputNumbers.setText(input);

        } else if (result.contains(".")) {
            result = result.replaceAll("\\.?0*$", "");
            textViewInputNumbers.setText(result);
        }
    }

    private void saveLastExpression(String input) {
        String lastOfExpression = input.charAt(input.length() - 1) + "";
        if (input.length() > 1) {
            if (lastOfExpression.equals(")")) {
                lastExpression = ")";
                int numberOfCloseParenthesis = 1;

                for (int i = input.length() - 2; i >= 0; i--) {
                    if (numberOfCloseParenthesis > 0) {
                        String last = input.charAt(i) + "";
                        if (last.equals(")")) {
                            numberOfCloseParenthesis++;
                        } else if (last.equals("(")) {
                            numberOfCloseParenthesis--;
                        }
                        lastExpression = last.concat(lastExpression);
                    } else if (checkLastCharacter(input.charAt(i) + "") == IS_OPERAND) {
                        lastExpression = input.charAt(i) + lastExpression;
                        break;
                    } else {
                        lastExpression = "";
                    }
                }
            } else if (checkLastCharacter(lastOfExpression + "") == IS_NUMBER) {
                lastExpression = lastOfExpression;
                for (int i = input.length() - 2; i >= 0; i--) {
                    String last = input.charAt(i) + "";
                    if (checkLastCharacter(last) == IS_NUMBER || checkLastCharacter(last) == IS_DOT) {
                        lastExpression = last.concat(lastExpression);
                    } else if (checkLastCharacter(last) == IS_OPERAND) {
                        lastExpression = last + lastExpression;
                        break;
                    }
                    if (i == 0) {
                        lastExpression = "";
                    }
                }
            }
        }
    }

    //checking last character of expression
    private int checkLastCharacter(String lastCharacter) {
        try {
            Integer.parseInt(lastCharacter);
            return IS_NUMBER;
        } catch (NumberFormatException ignored) {
        }
        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("x") || lastCharacter.equals("\u00F7") || lastCharacter.equals("%")))
            return IS_OPERAND;
        if (lastCharacter.equals("("))
            return IS_OPEN_PARENTHESIS;
        if (lastCharacter.equals(")"))
            return IS_CLOSE_PARENTHESIS;
        if (lastCharacter.equals("."))
            return IS_DOT;
        return -1;
    }
}