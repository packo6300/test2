package com.packo.iptv;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.packo.iptv.Adapters.CanalesAdapter;
import com.packo.iptv.beans.ItemBean;
import com.packo.iptv.helpers.AppExist;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class CanalesActivity extends AppCompatActivity {
    GridView simpleList;
    List<ItemBean> list = new ArrayList<>();
    CanalesAdapter canalesAdapter;
    EditText buscar;
    int val=0;
    int count=0;
    int clik=5;
    String url;
    private InterstitialAd mInterstitialAd;
    private String[] listas={
            "http://srregio.xyz/IPTV/regioflix.m3u",
            "http://srregio.xyz/IPTV/regiotv.m3u",
            "http://srregio.xyz/IPTV/smartv.m3u",
            "http://srregio.xyz/IPTV/deportes.m3u",
            "http://tecnotv.xyz/claro.m3u",
            "http://tecnotv.xyz/lista2.m3u",
            "http://tecnotv.xyz/lista3.m3u",
            "http://tvpremiumhd.club/tv.m3u",
            "http://tecnotv.xyz/peliculas.m3u",
            "http://tecnotv.xyz/peliculas1.m3u",
            "https://dl.dropboxusercontent.com/s/0ojzn3qmwo5p5s5/pelisnuevas4mil.m3u?dl=0",
            "http://app.checktime.com.mx/listaPelis.php",
            "http://app.checktime.com.mx/iptv.php"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canales);
        simpleList =(GridView) findViewById(R.id.simpleListView2);
        buscar = (EditText) findViewById(R.id.search);
        isListLoaded();
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-6064776652287686/4393230644");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (canalesAdapter.getItem(position)==null){}
                else {

                        /*clik++;
                        if(clik==5){
                            if(mInterstitialAd.isLoaded()){
                                mInterstitialAd.show();
                            }
                            clik=1;
                        }*/
                        Object linked = canalesAdapter.getItem(position);
                        ItemBean item= (ItemBean) linked;
                        Intent intent = new Intent(CanalesActivity.this, VideoActivity.class);
                        intent.putExtra("url",item.getUrl());
                        startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_canales);
    }
    public void listaCanales() {
        try{
            AsyncHttpClient httpClient = new AsyncHttpClient() ;
            RequestParams params = new RequestParams();
            int con=listas.length-1;
            for(int i=0;i<=con;i++){
                Log.e("lista",listas[i]);
                url="file"+i+".txt";
                val=i;
                httpClient.get(listas[i], params, new AsyncHttpResponseHandler() {
                    String url2="file"+val+".txt";
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String response =new String(responseBody);
                        writeToFile(response,CanalesActivity.this,url2);
                        readFromFile(CanalesActivity.this,url2);
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("LISTA_E",String.valueOf(statusCode));
                    }
                });
            }
        }
        catch (Exception e){
          Log.e("",e.getMessage());
        }

    }
    private void writeToFile(String data,Context context,String file) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    private void readFromFile(Context context,String file) {

        String receiveString="n/d";
        try {
            InputStream inputStream = context.openFileInput(file);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String urlimg="";
                String nombre="";
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    if(receiveString!= null || receiveString != ""){
                        if(receiveString.startsWith("#EXTINF")){
                            String img;
                            String[] datos = receiveString.split(",");
                            img=datos[0];
                            String[] imgArray=img.split("=");
                            for(int i=0; i<imgArray.length; i++){
                                if(i==1){
                                    String[] imgArray2=imgArray[1].split(" ");
                                    urlimg=imgArray2[0].substring(1, imgArray2[0].length()-1);
                                    nombre=datos[1];
                                }
                            }
                        }
                        if (receiveString.startsWith("http")){
                            if(!receiveString.contains("http://srregio.com") && !receiveString.contains("127.0.0.1")&&
                                    !receiveString.contains("google.com") && !receiveString.contains("/error")
                                    && !receiveString.contains("para ver como")&& !receiveString.contains("/rede")){
                                count++;
                                if(nombre.isEmpty()){
                                    nombre="item"+count;
                                }
                                if(urlimg.isEmpty()){
                                    urlimg="http://app.checktime.com.mx/pelicula.png";
                                }
                                this.list.add(new ItemBean(urlimg,nombre,receiveString));
                                urlimg="";
                                nombre="";
                            }
                            else{
                                Log.i("pelicula",nombre+" - "+receiveString);
                            }
                        }
                    }
                }
                inputStream.close();
                canalesAdapter= new CanalesAdapter(CanalesActivity.this, R.layout.item_list, list);
                //Toast.makeText(CanalesActivity.this,"Se han cargado: "+list.size()+" Canales y películas",Toast.LENGTH_SHORT).show();
                simpleList.setAdapter(canalesAdapter);
            }
        }
        catch (FileNotFoundException e) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(CanalesActivity.this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("fecha","");
            editor.apply();
            listaCanales();
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }catch (ArrayIndexOutOfBoundsException e){
            Log.e("login activity", receiveString);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void isListLoaded(){
        try{
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(CanalesActivity.this);
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String today=dateFormat.format(date);
            String lastUpdate = sharedPref.getString("fecha", "");
            if(today.equals(lastUpdate)){
                int con=listas.length-1;
                for(int i=0;i<=con;i++){
                    String url2="file"+i+".txt";
                    readFromFile(CanalesActivity.this,url2);
                }
            }
            else {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("fecha",today);
                editor.apply();
                listaCanales();
            }
            buscar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String param= new String(String.valueOf(s));
                    List<ItemBean> list2 = new ArrayList<ItemBean>();
                    for(ItemBean item : list ){
                        if(item.getNombre().toLowerCase().contains(param)){
                            list2.add(item);
                        }
                    }
                    CanalesAdapter canalesAdapter2= new CanalesAdapter(CanalesActivity.this, R.layout.item_list, list2);
                    simpleList.setAdapter(canalesAdapter2);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
        catch (Exception e){

        }
    }
}
