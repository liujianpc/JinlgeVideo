package com.example.jinglevideo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OptionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_option);
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        SharedPreferences sharedPreferences = this.getSharedPreferences("playlink", MODE_PRIVATE);
        int index = sharedPreferences.getInt("playlink", 0);
        radioGroup.check(index);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub
                int radioButtonId = arg0.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(radioButtonId);
                radioGroup.check(radioButton.getId());
                /*
                 * if (!radioButton.isChecked()) { radioButton.setChecked(true);
				 * }
				 */
                Intent intent = new Intent();
                intent.putExtra("resource", radioButtonId);
                setResult(1, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
