package com.paulawaite.foodchain;

import com.paulawaite.foodchain.entity.Animal;

import java.util.ArrayList;

/**
 * Used to create animals for the
 * Created by paulawaite on 7/18/16.
 */
public class DataSetUp {
    ArrayList<Animal> animals;

    public DataSetUp() {
        createAnimals();
    }

    public void createAnimals() {
        Animal hummingbirdFemale = new Animal("hummingbird", 1, null, null, "female", "@drawable/rubythroatedfemale.png", null);
        Animal beeBalm = new Animal("beebalm", 2, null, null, "n/a", null, "@drawable/beebalm.png");


        animals.add(beeBalm);
        animals.add(hummingbirdFemale);

    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }
}
