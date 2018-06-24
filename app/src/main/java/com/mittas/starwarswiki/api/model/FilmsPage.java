package com.mittas.starwarswiki.api.model;

import com.mittas.starwarswiki.data.entity.Film;

import java.util.List;

/**
 * In Swapi, results array has the list of films
 */
public class FilmsPage extends BasicPage {
    public List<Film> results;
}
