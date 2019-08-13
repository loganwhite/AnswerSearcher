package com.example.answersearcher;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private EditText et_keyword, et_answer;
    private Button btn_search;
    private static String filename = "file.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // bind the controls
        et_answer = findViewById(R.id.et_answer);
        et_keyword = findViewById(R.id.et_keywords);
        btn_search = findViewById(R.id.btn_search);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyword = et_keyword.getText().toString();
                try {
                    String result = search(keyword, filename);
                    et_answer.setText(result);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String search(String keyword, String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename)));
//                new FileInputStream(filename), "UTF8")

        List<String> result_list = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null)
        {
            String[] str_list = line.split("？");
            if (str_list[0].contains(keyword)) {
                result_list.add(line);
            }
        }
        reader.close();
        String result = constructResult(result_list);
        return result;
    }

    private String constructResult(List<String> results) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < results.size(); i++) {
            String[] str_list = results.get(i).split("？");
            sb.append(str_list[0]);
            sb.append("?\n");
            for (int j = 1; j < str_list.length; j++)
                sb.append(str_list[j]);
            sb.append("\n\n");
        }
        return sb.toString();
    }

}
