package ro.pub.cs.systems.eim.practicaltest01var07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var07SecondaryActivity extends AppCompatActivity {

    EditText text1;
    EditText text2;
    EditText text3;
    EditText text4;
    Button sumButton;
    Button productButton;

    private PracticalTest01Var07SecondaryActivity.ButtonClickListener buttonClickListener = new PracticalTest01Var07SecondaryActivity.ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            int text1Value = Integer.parseInt(text1.getText().toString());
            int text2Value = Integer.parseInt(text2.getText().toString());
            int text3Value = Integer.parseInt(text3.getText().toString());
            int text4Value = Integer.parseInt(text4.getText().toString());

            switch(view.getId()) {
                case R.id.product_button:
                    setResult(text1Value * text2Value * text3Value * text4Value, null);
                    break;
                case R.id.sum_button:
                    setResult(text1Value + text2Value + text3Value + text4Value, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_secondary);

        sumButton = (Button)findViewById(R.id.sum_button);
        sumButton.setOnClickListener(buttonClickListener);

        productButton = (Button)findViewById(R.id.product_button);
        productButton.setOnClickListener(buttonClickListener);

        text1 = (EditText)findViewById(R.id.text_1);
        text2 = (EditText)findViewById(R.id.text_2);
        text3 = (EditText)findViewById(R.id.text_3);
        text4 = (EditText)findViewById(R.id.text_4);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getExtras().containsKey(Constants.TEXT_1_INTENT)) {
                text1.setText(intent.getStringExtra(Constants.TEXT_1_INTENT));
            }
            if (intent.getExtras().containsKey(Constants.TEXT_2_INTENT)) {
                text2.setText(intent.getStringExtra(Constants.TEXT_2_INTENT));
            }
            if (intent.getExtras().containsKey(Constants.TEXT_3_INTENT)) {
                text3.setText(intent.getStringExtra(Constants.TEXT_3_INTENT));
            }
            if (intent.getExtras().containsKey(Constants.TEXT_4_INTENT)) {
                text4.setText(intent.getStringExtra(Constants.TEXT_4_INTENT));
            }
        }
    }
}