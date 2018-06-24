package com.mittas.starwarswiki.api.model;

import com.mittas.starwarswiki.data.entity.Vehicle;

import java.util.List;

/**
 * In Swapi, results array has the list of vehicles
 */
public class VehiclesPage extends BasicPage {
    public List<Vehicle> results;
}
