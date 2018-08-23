package com.labinfo.thiago.pokemonapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoPokemonActivity extends AppCompatActivity {

    @BindView(R.id.text_pokeName)
    TextView textPokeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pokemon);

        ButterKnife.bind(this);
    }
}
