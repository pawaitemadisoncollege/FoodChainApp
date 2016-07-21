package com.paulawaite.foodchain;

import com.paulawaite.foodchain.entity.Animal;

import java.util.ArrayList;

/**
 * Used to create animals for the
 * Created by paulawaite on 7/18/16.
 */
public class DataSetUp {
    ArrayList<Animal> animals;
    Animal userCharacter;

    public DataSetUp() {
        createUserCharacter();
        createAnimals();
    }

    public void createAnimals() {

        Animal beeBalm = new Animal("beebalm", 2, null, null, "n/a", null, "beebalm");
        Animal worm = new Animal("worm", 3, null, null, "n/a", null, "worm100");
        animals.add(beeBalm);

    }

    private void createUserCharacter() {
        Animal userCharacter = new Animal("hummingbird", 1, null, null, "female", "rubythroatedfemale", null);
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }
}
