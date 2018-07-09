package calculadora.com.calculadora;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalcFrag extends Fragment {


    public CalcFrag() {
        // Required empty public constructor
    }

    Button button1,button2,button3,button4,button5,button6,button7,button8,button9,buttondiv,buttonsum,buttonres,buttonmul,buttoneq,buttonac,button0;
    TextView textViewres,textViewop;
    String num2 = "";
    String num1 = "";
    String op = "";
    Double aux;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        textViewres = rootView.findViewById(R.id.textView2);
        textViewop = rootView.findViewById(R.id.textView);
        button0 = rootView.findViewById(R.id.button13);
        button1 = rootView.findViewById(R.id.button16);
        button2 = rootView.findViewById(R.id.button15);
        button3 = rootView.findViewById(R.id.button9);
        button4 = rootView.findViewById(R.id.button8);
        button5 = rootView.findViewById(R.id.button7);
        button6 = rootView.findViewById(R.id.button6);
        button7 = rootView.findViewById(R.id.button4);
        button8 = rootView.findViewById(R.id.button3);
        button9 = rootView.findViewById(R.id.button2);
        buttondiv = rootView.findViewById(R.id.button1);
        buttonac = rootView.findViewById(R.id.button14);
        buttoneq = rootView.findViewById(R.id.button12);
        buttonres = rootView.findViewById(R.id.button11);
        buttonsum = rootView.findViewById(R.id.button10);
        buttonmul = rootView.findViewById(R.id.button5);
        View.OnClickListener number = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                if (op.equals("")) {
                    num1 += button.getText().toString();
                    aux = 0.0;
                    textViewop.setText(num1);
                } else {
                    if (!Objects.equals(aux,0.0)) num1 = Double.toString(aux);
                    num2 += button.getText().toString();
                    textViewop.append(button.getText());
                }
            }
        };
        button0.setOnClickListener(number);
        button1.setOnClickListener(number);
        button2.setOnClickListener(number);
        button3.setOnClickListener(number);
        button4.setOnClickListener(number);
        button5.setOnClickListener(number);
        button6.setOnClickListener(number);
        button7.setOnClickListener(number);
        button8.setOnClickListener(number);
        button9.setOnClickListener(number);

        View.OnClickListener number2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                textViewop.append(" " + button.getText() + " ");
                op = button.getText().toString();
            }
        };
        buttonsum.setOnClickListener(number2);
        buttonres.setOnClickListener(number2);
        buttonmul.setOnClickListener(number2);
        buttondiv.setOnClickListener(number2);


        View.OnClickListener equals = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Button button = (Button) view;
                double result = performOp();
                op = "";
                num1 = "";
                num2 = "";
                textViewres.setText(Double.toString(result));
            }
        };
        buttoneq.setOnClickListener(equals);

        View.OnClickListener reset = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                op = "";
                num1 = "";
                num2 = "";
                textViewres.setText("0");
                textViewop.setText("");
            }
        };
        buttonac.setOnClickListener(reset);
        return rootView;
    }
    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putString("op", textViewop.getText().toString());
        outstate.putString("result", textViewres.getText().toString());
    }  double performOp() {
        double d1 = Double.parseDouble(this.num1);
        double d2 = Double.parseDouble(this.num2);
        double d;
        if (Objects.equals(op, "+")){
            d = d1 + d2;
        }
        else if (Objects.equals(op, "-")){
            d = d1 - d2;
        }
        else if (Objects.equals(op, "/")){
            d = d1 / d2;
        }
        else {
            d = d1*d2;
        }
        aux = d;
        return d;
    }

    /*

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        textViewop.setText(savedInstanceState.getString("op"));
        textViewres.setText(savedInstanceState.getString("result"));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.option1:
                callmymum();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    void callmymum() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:999999999"));
        startActivity(intent);
    }*/
}
