package com.example.anymessagetranslator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.Locale;


public class MainActivity4 extends AppCompatActivity {
    Button btnmenu;
    //Button say;
    TextView textView;
    EditText editText;

    TextToSpeech t1,t2,t3,t4,t5,t6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        textView = findViewById(R.id.textView);
        editText =(EditText) findViewById(R.id.editText);
        btnmenu = (Button) findViewById(R.id.btnmenu);
        btnmenu.setOnClickListener(view -> ShowMenu());

        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    String languagePref = "ta-IN";
                    t1.setLanguage(Locale.forLanguageTag("ta-IN"));
                }
            }
        });

        t2=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    String languagePref = "hi-IN";
                    t2.setLanguage(Locale.forLanguageTag("hi-IN"));
                }
            }
        });

        t3=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    String languagePref = "te-IN";
                    t3.setLanguage(Locale.forLanguageTag("te-IN"));
                }
            }
        });

        t4=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    String languagePref = "ka-IN";
                    t4.setLanguage(Locale.forLanguageTag("ka-IN"));
                }
            }
        });

        t5=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    String languagePref = "ma-IN";
                    t5.setLanguage(Locale.forLanguageTag("ma-IN"));
                }
            }
        });

        t6=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t6.setLanguage(Locale.FRENCH);
                }
            }
        });

        btnmenu.setOnClickListener(view -> ShowMenu());

      //  say = (Button) findViewById(R.id.say);
       // say.setOnClickListener(view -> ShowMenu());
      //  say.setOnClickListener(new View.OnClickListener() {
      /*      @Override
            public void onClick(View v) {
                String toSpeak = textView.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            } */
     //   });
    }

    //extra

    public void onClick(View v) {
        String toSpeak = textView.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onClick1(View v) {
        String toSpeak = textView.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t2.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void onClick2(View v) {
        String toSpeak = textView.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t3.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void onClick3(View v) {
        String toSpeak = textView.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t4.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void onClick4(View v) {
        String toSpeak = textView.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t5.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onClick5(View v) {
        String toSpeak = textView.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
        t6.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void translateText(View view) {

        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.TAMIL)
                        .build();

        final Translator englishTamilTranslator =
                Translation.getClient(options);

        String text = String.valueOf(editText.getText());
        DownloadConditions conditions = new DownloadConditions.Builder()
                //  .requireWifi()
                .build();


        englishTamilTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) v -> {
                            englishTamilTranslator.translate(text)
                                    .addOnSuccessListener(
                                            (OnSuccessListener) translatedText -> {
                                                textView.setText("");
                                                textView.append((String) translatedText);

                                                Log.i("TAG", "Translation is " + (String) translatedText);
                                            })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Error.
                                                    Log.e("Error", "Translation faliled " + e);
                                                }
                                            });
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.e("Error", "Model could n’t be downloaded " + e);

                            }
                        });


    }

    public void translateText1(View view) {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.HINDI)
                        .build();

        final Translator englishHindiTranslator =
                Translation.getClient(options);

        String text = String.valueOf(editText.getText());
        DownloadConditions conditions = new DownloadConditions.Builder()
                //  .requireWifi()
                .build();


        englishHindiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) v -> {
                            englishHindiTranslator.translate(text)
                                    .addOnSuccessListener(
                                            (OnSuccessListener) translatedText -> {
                                                textView.setText("");
                                                textView.append((String) translatedText);

                                                Log.i("TAG", "Translation is " + (String) translatedText);
                                            })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Error.
                                                    Log.e("Error", "Translation faliled " + e);
                                                }
                                            });
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.e("Error", "Model could n’t be downloaded " + e);

                            }
                        });


    }

    public void translateText2(View view) {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.TELUGU)
                        .build();

        final Translator englishTeluguTranslator =
                Translation.getClient(options);

        String text = String.valueOf(editText.getText());
        DownloadConditions conditions = new DownloadConditions.Builder()
                // .requireWifi()
                .build();


        englishTeluguTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) v -> {
                            englishTeluguTranslator.translate(text)
                                    .addOnSuccessListener(
                                            (OnSuccessListener) translatedText -> {
                                                textView.setText("");
                                                textView.append((String) translatedText);

                                                Log.i("TAG", "Translation is " + (String) translatedText);
                                            })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Error.
                                                    Log.e("Error", "Translation faliled " + e);
                                                }
                                            });
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.e("Error", "Model could n’t be downloaded " + e);

                            }
                        });


    }

    public void translateText3(View view) {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.KANNADA)
                        .build();

        final Translator englishKannadaTranslator =
                Translation.getClient(options);

        String text = String.valueOf(editText.getText());
        DownloadConditions conditions = new DownloadConditions.Builder()
                // .requireWifi()
                .build();


        englishKannadaTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) v -> {
                            englishKannadaTranslator.translate(text)
                                    .addOnSuccessListener(
                                            (OnSuccessListener) translatedText -> {
                                                textView.setText("");
                                                textView.append((String) translatedText);

                                                Log.i("TAG", "Translation is " + (String) translatedText);
                                            })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Error.
                                                    Log.e("Error", "Translation faliled " + e);
                                                }
                                            });
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.e("Error", "Model could n’t be downloaded " + e);

                            }
                        });


    }

    public void translateText4(View view) {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.MARATHI)
                        .build();

        final Translator englishMarathiTranslator =
                Translation.getClient(options);

        String text = String.valueOf(editText.getText());
        DownloadConditions conditions = new DownloadConditions.Builder()
                // .requireWifi()
                .build();


        englishMarathiTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) v -> {
                            englishMarathiTranslator.translate(text)
                                    .addOnSuccessListener(
                                            (OnSuccessListener) translatedText -> {
                                                textView.setText("");
                                                textView.append((String) translatedText);

                                                Log.i("TAG", "Translation is " + (String) translatedText);
                                            })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Error.
                                                    Log.e("Error", "Translation faliled " + e);
                                                }
                                            });
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.e("Error", "Model could n’t be downloaded " + e);

                            }
                        });


    }

    public void translateText5(View view) {
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.ENGLISH)
                        .setTargetLanguage(TranslateLanguage.FRENCH)
                        .build();

        final Translator englishFrenchTranslator =
                Translation.getClient(options);

        String text = String.valueOf(editText.getText());
        DownloadConditions conditions = new DownloadConditions.Builder()
                // .requireWifi()
                .build();


        englishFrenchTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        (OnSuccessListener) v -> {
                            englishFrenchTranslator.translate(text)
                                    .addOnSuccessListener(
                                            (OnSuccessListener) translatedText -> {
                                                textView.setText("");
                                                textView.append((String) translatedText);

                                                Log.i("TAG", "Translation is " + (String) translatedText);
                                            })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Error.
                                                    Log.e("Error", "Translation faliled " + e);
                                                }
                                            });
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                Log.e("Error", "Model could n’t be downloaded " + e);

                            }
                        });


    }

    public void ShowMenu() {
        PopupMenu popupMenu = new PopupMenu(this, btnmenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_main1, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_english:

                        Toast.makeText(MainActivity4.this, "This is English", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.action_tamil:

                        translateText(editText);
                        onClick(textView);

                        Toast.makeText(MainActivity4.this, "This is Tamil", Toast.LENGTH_LONG).show();


                        break;

                    case R.id.action_hindi:

                        translateText1(editText);
                        onClick1(textView);
                        Toast.makeText(MainActivity4.this, "This is Hindi", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.action_telugu:

                        translateText2(editText);
                        onClick2(textView);
                        Toast.makeText(MainActivity4.this, "This is Telugu", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.action_kannada:

                        translateText3(editText);
                        onClick3(textView);
                        Toast.makeText(MainActivity4.this, "This is Kannada", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.action_marathi:

                        translateText4(editText);
                        onClick4(textView);
                        Toast.makeText(MainActivity4.this, "This is Marathi", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.action_french:

                        translateText5(editText);
                        onClick5(textView);
                        Toast.makeText(MainActivity4.this, "This is French", Toast.LENGTH_LONG).show();
                        break;


                }
                return false;
            }
        });
        popupMenu.show();
    }

}