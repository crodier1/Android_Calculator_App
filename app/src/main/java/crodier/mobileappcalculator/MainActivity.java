package crodier.mobileappcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText newNumber, result;

    TextView displayOperation;

    Double operand1 = null;

    String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);

        newNumber = findViewById(R.id.newNumber);

        displayOperation = findViewById(R.id.Operaton);

        Button button0 = findViewById(R.id.button0),
                button1 = findViewById(R.id.button1),
                button2 = findViewById(R.id.button2),
                button3 = findViewById(R.id.button3),
                button4 = findViewById(R.id.button4),
                button5 = findViewById(R.id.button5),
                button6 = findViewById(R.id.button6),
                button7 = findViewById(R.id.button7),
                button8 = findViewById(R.id.button8),
                button9 = findViewById(R.id.button9),
                buttonDot = findViewById(R.id.buttonDot),

                buttonMultiply = findViewById(R.id.buttonMulitiply),
                buttonClear = findViewById(R.id.buttonClear),
                buttonPlus = findViewById(R.id.buttonPlus),
                buttonSubtract = findViewById(R.id.buttonMinus),
                buttonDivide = findViewById(R.id.buttonDivide),
                buttonNegative = findViewById(R.id.buttonNeg),
                buttonEquals = findViewById(R.id.buttonEquals);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());

            }
        };

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString(),
                        value = newNumber.getText().toString();

                try {

                    performOperation(Double.valueOf(value), op);

                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }


                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);
        buttonSubtract.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);

        buttonNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();

                if (value.length() == 0){
                    newNumber.setText("-");
                } else {

                    try{
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e){
                        newNumber.setText("");
                    }

                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                operand1 = null;

                result.setText("");

                newNumber.setText("");

                displayOperation.setText("=");
            }
        });


    }

    private void performOperation(Double value, String operation) {
        if (operand1 == null) {
            operand1 = value;
        } else {
            operand1 = value;

            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }

            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;

                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;

                case "*":
                    operand1 *= value;
                    break;

                case "-":
                    operand1 -= value;
                    break;

                case "+":
                    operand1 += value;
                    break;

            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (operand1 != null){
            outState.putDouble("operand1", operand1);
        }

        outState.putString("pendingOperation", pendingOperation);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        operand1 = savedInstanceState.getDouble("operand1");

        pendingOperation = savedInstanceState.getString("pendingOperation");
        displayOperation.setText(pendingOperation);


    }
}