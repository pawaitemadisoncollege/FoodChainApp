package com.paulawaite.foodchain;

import com.paulawaite.foodchain.entity.Animal;

import java.util.ArrayList;

/**
 * Used to create animals for the
 * Created by paulawaite on 7/18/16.
 */
public class DataSetUp {
    private ArrayList<Animal> animals;
    private Animal userCharacter;

    public DataSetUp() {
        createUserCharacter();
        createAnimals();
        userCharacter.setPredators(createPredators());
        userCharacter.setPrey(createPrey());
        createPredators();
    }

    public void createAnimals() {
        animals = new ArrayList<Animal>();
        Animal beeBalm = new Animal("beebalm", 2, null, null, "n/a", "beebalm", null);
        Animal worm = new Animal("worm", 3, null, null, "n/a", "nightcrawler", null);
        Animal sharpShinnedHawk = new Animal("sharpShinnedHawk", 4, null, null, "n/a", "sharpshinnedhawk", "sharpshinned.mp3");
        animals.add(beeBalm);
        animals.add(worm);
        animals.add(sharpShinnedHawk);
    }

    private void createUserCharacter() {
        userCharacter = new Animal("hummingbird", 1, null, null, "female", "rubythroatedfemale", "hummingbird.mp3");
    }

    private ArrayList<Integer> createPrey() {
        ArrayList<Integer> preyIds = new ArrayList<Integer>();
        preyIds.add(2);
        return preyIds;
    }

    private ArrayList<Integer> createPredators() {
        ArrayList<Integer> predatorIds = new ArrayList<Integer>();
        predatorIds.add(4);
        return predatorIds;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public Animal getUserCharacter() {
        return userCharacter;
    }

    public void setUserCharacter(Animal userCharacter) {
        this.userCharacter = userCharacter;
    }
}
