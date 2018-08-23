package com.labinfo.thiago.pokemonapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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
    public void findPokemon() {
        int pokeNumber = Integer.parseInt(editPokeNumber.getText().toString());

        if (pokeNumber <= 0 || pokeNumber > 255) {
            Toast.makeText(this, "Número do pokemon inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implementar chamada à API do Pokemon, pegar o nome do Pokemon e exibir na InfoPokemonActivity
    }
}
