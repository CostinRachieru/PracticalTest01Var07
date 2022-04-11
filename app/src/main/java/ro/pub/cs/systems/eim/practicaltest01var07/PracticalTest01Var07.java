package ro.pub.cs.systems.eim.practicaltest01var07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var07 extends AppCompatActivity {

    EditText text1;
    EditText text2;
    EditText text3;
    EditText text4;
    Button setButton;

    Intent lastIntent = null;
    int serviceStatus = 0;
    private IntentFilter intentFilter = new IntentFilter();

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.MESSAGE_EXTRA));
            text1.setText(String.valueOf(intent.getIntExtra(Constants.NUMBER_1_EXTRA, 0)));
            text2.setText(String.valueOf(intent.getIntExtra(Constants.NUMBER_2_EXTRA, 0)));
            text3.setText(String.valueOf(intent.getIntExtra(Constants.NUMBER_3_EXTRA, 0)));
            text4.setText(String.valueOf(intent.getIntExtra(Constants.NUMBER_4_EXTRA, 0)));
        }
    }

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        public void onClick(View view) {
            String text1Value = text1.getText().toString();
            String text2Value = text2.getText().toString();
            String text3Value = text3.getText().toString();
            String text4Value = text4.getText().toString();

            switch(view.getId()) {
                case R.id.set_button:
                    if (text1Value.isEmpty() || text2Value.isEmpty() || text3Value.isEmpty() || text4Value.isEmpty()) {
                        return;
                    }
                    Intent serviceIntent = new Intent();
                    if (lastIntent != null)
                        getApplicationContext().stopService(lastIntent);
                    serviceIntent = new Intent(getApplicationContext(), MainActivityService.class);
                    getApplicationContext().startService(serviceIntent);
                    lastIntent = serviceIntent;
                    serviceStatus = 1;

                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var07SecondaryActivity.class);
                    intent.putExtra(Constants.TEXT_1_INTENT, text1Value);
                    intent.putExtra(Constants.TEXT_2_INTENT, text2Value);
                    intent.putExtra(Constants.TEXT_3_INTENT, text3Value);
                    intent.putExtra(Constants.TEXT_4_INTENT, text4Value);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var07_main);

        Log.d(Constants.FLOW_TAG, "onCreate STARTED screen 1");

        setButton = (Button)findViewById(R.id.set_button);
        setButton.setOnClickListener(buttonClickListener);

        text1 = (EditText)findViewById(R.id.edit_text_1);
        text2 = (EditText)findViewById(R.id.edit_text_2);
        text3 = (EditText)findViewById(R.id.edit_text_3);
        text4 = (EditText)findViewById(R.id.edit_text_4);

        intentFilter.addAction(Constants.ACTION);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(Constants.TEXT_1_SAVED)) {
                text1.setText(savedInstanceState.getString(Constants.TEXT_1_SAVED));
            } else {
                text1.setText("");
            }

            if (savedInstanceState.containsKey(Constants.TEXT_2_SAVED)) {
                text2.setText(savedInstanceState.getString(Constants.TEXT_2_SAVED));
            } else {
                text2.setText("");
            }
            if (savedInstanceState.containsKey(Constants.TEXT_3_SAVED)) {
                text3.setText(savedInstanceState.getString(Constants.TEXT_3_SAVED));
            } else {
                text3.setText("");
            }
            if (savedInstanceState.containsKey(Constants.TEXT_4_SAVED)) {
                text4.setText(savedInstanceState.getString(Constants.TEXT_4_SAVED));
            } else {
                text4.setText("");
            }
        } else {
            text1.setText("");
            text2.setText("");
            text3.setText("");
            text4.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result: " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        Log.d(Constants.FLOW_TAG, "onSaveInstanceState STARTED");

        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.TEXT_1_SAVED, text1.getText().toString());
        savedInstanceState.putString(Constants.TEXT_2_SAVED, text2.getText().toString());
        savedInstanceState.putString(Constants.TEXT_3_SAVED, text3.getText().toString());
        savedInstanceState.putString(Constants.TEXT_4_SAVED, text4.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        Log.d(Constants.FLOW_TAG, "onRestoreInstanceState STARTED");

        if (savedInstanceState.containsKey(Constants.TEXT_1_SAVED)) {
            text1.setText(savedInstanceState.getString(Constants.TEXT_1_SAVED));
        } else {
            text1.setText("");
        }

        if (savedInstanceState.containsKey(Constants.TEXT_2_SAVED)) {
            text2.setText(savedInstanceState.getString(Constants.TEXT_2_SAVED));
        } else {
            text2.setText("");
        }
        if (savedInstanceState.containsKey(Constants.TEXT_3_SAVED)) {
            text3.setText(savedInstanceState.getString(Constants.TEXT_3_SAVED));
        } else {
            text3.setText("");
        }
        if (savedInstanceState.containsKey(Constants.TEXT_4_SAVED)) {
            text4.setText(savedInstanceState.getString(Constants.TEXT_4_SAVED));
        } else {
            text4.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, MainActivityService.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}