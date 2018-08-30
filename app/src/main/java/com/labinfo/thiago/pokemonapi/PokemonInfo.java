package com.labinfo.thiago.pokemonapi;

public class PokemonInfo {

    private int id;
    private String name;

    public PokemonInfo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
