package com.mittas.starwarswiki.api.model;

import java.util.List;

import com.mittas.starwarswiki.data.entity.Character;

/**
 * In Swapi, results array has the list of characters
 */
public class CharactersPage extends BasicPage {
    public List<Character> results;
}
