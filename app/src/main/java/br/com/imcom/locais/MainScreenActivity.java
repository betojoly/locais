package br.com.imcom.locais;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainScreenActivity extends AppCompatActivity {

    Button btnViewLocais;
    Button btnCreateLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        // Buttons
        btnViewLocais = (Button) findViewById(R.id.btnViewLocais);
        btnCreateLocal = (Button) findViewById(R.id.btnCreateLocal);

        // view locais click event
        btnViewLocais.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launching All Locais Activity
                Intent i = new Intent(getApplication(), AllLocalActivity.class);
                startActivity(i);
            }
        });

        // New Local click event
        btnCreateLocal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Launching create new product activity
                Intent addlocal = new Intent(getApplication(), NewLocalActivity.class);
                startActivity(addlocal);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
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
}