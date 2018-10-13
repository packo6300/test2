package com.packo.iptv;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;
import com.packo.iptv.beans.UserBean;
import com.packo.iptv.helpers.Hash;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    TextView recupera;
    Button entrar;
    Button registro;
    private String url;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLoged();
        init();
        initEvents();

    }
    private void init(){
        this.user=findViewById(R.id.username);
        this.pass=findViewById(R.id.password);
        this.recupera= findViewById(R.id.recpas);
        this.entrar=findViewById(R.id.loginBtn);
        this.registro=findViewById(R.id.registerBtn);
        this.url=getResources().getString(R.string.url);
    }
    private void isLoged(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String key = sharedPref.getString("user", "");
        if(!key.isEmpty()){
            Intent registro = new Intent(MainActivity.this, CanalesActivity.class);
            registro.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(registro);
            finish();
        }
        Intent registro = new Intent(MainActivity.this, CanalesActivity.class);
        registro.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(registro);
        finish();
    }
    public void initEvents(){
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient httpClient = new AsyncHttpClient() ;
                RequestParams params = new RequestParams();
                Hash hash = new Hash();
                String passw=hash.md5(String.valueOf(pass.getText()));
                params.put("t","login");
                params.put("u",String.valueOf(user.getText()));
                params.put("p",passw);
                httpClient.post(url, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            String response = new String(responseBody);
                            JSONArray jsonArray = new JSONArray(response);
                            UserBean usr= new UserBean();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject json = jsonArray.getJSONObject(i);
                                  usr.setCorreo(json.optString("usuario"));
                                  usr.setLastLogin(json.getString("last"));
                            }
                            if(!usr.getCorreo().isEmpty()){
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("user", usr.getCorreo());
                                editor.putString("lastLogin",usr.getLastLogin());
                                editor.apply();
                                Intent registro = new Intent(MainActivity.this, CanalesActivity.class);
                                startActivity(registro);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(registro);
            }
        });
        recupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
