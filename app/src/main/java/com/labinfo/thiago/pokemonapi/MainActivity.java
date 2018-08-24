package com.labinfo.thiago.pokemonapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DebugUtils;
import android.util.JsonReader;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static String URI_POKEMON = "http://pokeapi.co/api/v2/pokemon/";
    private final static String POKE_API_NAME = "name";

    @BindView(R.id.button_find)
    Button buttonFind;

    @BindView(R.id.edit_pokeNumber)
    EditText editPokeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_find)
    public void findPokemon()
    {
        String pokeNumberString = editPokeNumber.getText().toString();

        if(pokeNumberString.trim().isEmpty())
        {
            Toast.makeText(this, "Você precisa digitar um número antes!", Toast.LENGTH_SHORT).show();
            return;
        }

        int pokeNumber = Integer.parseInt(pokeNumberString);

        if (pokeNumber <= 0 || pokeNumber > 255) {
            Toast.makeText(this, "Número do pokemon inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL pokemonEndpoint = new URL(MainActivity.URI_POKEMON + editPokeNumber.getText().toString());

                    HttpURLConnection pokeConnection = (HttpURLConnection) pokemonEndpoint.openConnection();
                    Log.i("Status code: ", String.valueOf(pokeConnection.getResponseCode()));

                    pokeConnection.setRequestMethod("GET");

                    InputStream in = pokeConnection.getInputStream();
                    InputStreamReader streamReader =  new InputStreamReader(in,"UTF-8");
                    JsonReader jsonReader = new JsonReader(streamReader);

                    jsonReader.beginObject();
                    while (jsonReader.hasNext()) {
                        if (!jsonReader.nextName().equals(MainActivity.POKE_API_NAME)) {
                            jsonReader.skipValue();
                            continue;
                        }

                        String value = jsonReader.nextString();

                        Intent intent = new Intent(MainActivity.this, InfoPokemonActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        intent.putExtra(InfoPokemonActivity.POKE_NAME_KEY, value);

                        getApplicationContext().startActivity(intent);

                        break;
                    }
                    jsonReader.close();
                    pokeConnection.disconnect();
                } catch (IOException e) {
                    showErrorMessage(e);
                }
            }

            public void showErrorMessage(Exception e) {
                Log.i("Erro: ", e.getMessage());
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Não foi possível obter o nome do pokemon!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
