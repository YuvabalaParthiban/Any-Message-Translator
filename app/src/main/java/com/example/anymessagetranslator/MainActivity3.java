package com.example.anymessagetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity3 extends AppCompatActivity {

    private Button button;
    private Button button1;
    private Button button2;
   // private Button button3;
    private Button button4;

    TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
     //   button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity1();
            }
        });
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity2();
                    }
                });

       button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
       });

     /*    button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity4();
            }
        }); */

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        // Adding OnClickListener
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak("Thank You for using our app",TextToSpeech.QUEUE_FLUSH,null);
            }
        });
    }


    public void openActivity1()
    {
        Intent intent=new Intent(this,MainActivity1.class);
        startActivity(intent);
    }

    public void openActivity2()
    {
        Intent intent=new Intent(this,MainActivity4.class);
        startActivity(intent);
    }

   public void openActivity3()
    {
        Intent intent=new Intent(this,MainActivity5.class);
        startActivity(intent);
    }

   /*  public void openActivity4()
    {
        Intent intent=new Intent(this,MainActivity6.class);
        startActivity(intent);
    }

    <Button
        android:id="@+id/button3"
        style="?android:attr/buttonStyleSmall"
        android:layout_width="200dp"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="500dp"
        android:layout_marginLeft="100dp"
        android:text="Text to Speech" />
    */
}