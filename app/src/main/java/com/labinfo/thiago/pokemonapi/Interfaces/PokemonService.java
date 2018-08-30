package com.labinfo.thiago.pokemonapi.Interfaces;

import com.labinfo.thiago.pokemonapi.PokemonInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonService {
    @GET("{pokeNumber}")
    Observable<PokemonInfo> getPokeInfo(@Path("pokeNumber") int pokeNumber);
}
