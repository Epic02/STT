package com.example.speechtotext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Intent startlistening;
    ListView a;
    ArrayList<String> words,listv;
    Button lock;
    float[] confid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a=findViewById(R.id.listview);
        lock=findViewById(R.id.button);
        startlistening=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startlistening.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startlistening.putExtra(RecognizerIntent.EXTRA_PROMPT,"RECORDING");
        startActivityForResult(startlistening,1234);
        listv=new ArrayList<>();
        lock.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                listv=new ArrayList<>();
                startActivityForResult(startlistening,1234);
            }
        });
    }
    @Override
    public void onActivityResult(int requestcode,int resultcode,Intent data){
        String l="NOTHING";
        if(requestcode==1234&&resultcode==RESULT_OK) {
            words = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            confid = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);
            if (words.size() > 0 && confid.length > 0) {

                for (int i = 0; i < words.size(); i++) {
                    if(words.get(i)!=null){
                        l=words.get(i)  +"\n"+"Confidence score : "+ (confid[i]*100)+"%";
                    }
                    else{
                        l="NOTHING";
                    }
                    if (listv != null) {
                        listv.add(i, l);
                    }

                }
                if(listv!=null) {
                    ArrayAdapter adapter = new ArrayAdapter(this, R.layout.entry, listv);//array adapter is passed to listview's setAdapter() constructor is used to add the values to the adapter
                    a.setAdapter(adapter);
                }
            }

        }
    }
   @Override
    protected void onResume(){
        super.onResume();
       lock.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               // Code here executes on main thread after user presses button
               listv=new ArrayList<>();
               startActivityForResult(startlistening,1234);
           }
       });
   }

}
