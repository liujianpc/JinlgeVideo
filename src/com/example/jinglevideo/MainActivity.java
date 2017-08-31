package com.example.jinglevideo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button search = (Button) findViewById(R.id.search);
        input = (EditText) findViewById(R.id.input);
        if (savedInstanceState != null) {
            input.setText(savedInstanceState.getString("filmName"));
        }
        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String filmName = input.getText().toString();

                if (!TextUtils.isEmpty(filmName)) {
                    Intent intent = new Intent(MainActivity.this,
                            LinkListActivity.class);
                    intent.putExtra("fileName", filmName);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "毛线，输入关键字再搜索！",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        String tempData = input.getText().toString();
        outState.putString("filmName", tempData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about_menu) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.exit_menu) {
            ActivityCollector.finishAllActivity();
        }
        return super.onOptionsItemSelected(item);
    }
}
