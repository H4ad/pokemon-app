package com.labinfo.thiago.pokemonapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.labinfo.thiago.pokemonapi.Interfaces.PokemonService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final static String URI_POKEMON = "http://pokeapi.co/api/v2/pokemon/";
    private final static String POKE_API_NAME = "name";

    private Retrofit retrofit;

    @BindView(R.id.button_find)
    Button buttonFind;

    @BindView(R.id.edit_pokeNumber)
    EditText editPokeNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        retrofit = makeRetrofit();
    }

    public Retrofit makeRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(MainActivity.URI_POKEMON)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.button_find)
    public void findPokemon() {
        String pokeNumberString = editPokeNumber.getText().toString();

        if(pokeNumberString.trim().isEmpty())
        {
            Toast.makeText(this, R.string.digite_num_antes, Toast.LENGTH_SHORT).show();
            return;
        }

        int pokeNumber = Integer.parseInt(pokeNumberString);

        if (pokeNumber <= 0 || pokeNumber > 255) {
            Toast.makeText(this, R.string.pokemon_invalido, Toast.LENGTH_SHORT).show();
            return;
        }

        PokemonService service = retrofit.create(PokemonService.class);

        service.getPokeInfo(pokeNumber)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new Observer<PokemonInfo>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(PokemonInfo pokemonInfo) {
                    Intent intent = new Intent(MainActivity.this, InfoPokemonActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra(InfoPokemonActivity.POKE_NAME_KEY, pokemonInfo.getName());

                    getApplicationContext().startActivity(intent);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    buttonFind.setText(getString(R.string.procurar_pokemon));
                    buttonFind.setEnabled(true);
                }
            });

        buttonFind.setEnabled(false);
        buttonFind.setText(R.string.procurando);
    }
}
