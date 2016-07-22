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
        Animal mosquito = new Animal("mosquito", 5, null, null, "n/a", "mosquito", null);
        Animal spider = new Animal("spider", 6, null, null, "n/a", "spider", null);
        Animal lobelia = new Animal("redlobelia", 7, null, null, "n/a", "redlobelia", null);
        Animal mouse = new Animal("mouse", 8, null, null, "n/a", "mouse", null);
        Animal cat = new Animal("cat", 9, null, null, "n/a", "cat", null);
        animals.add(beeBalm);
        animals.add(worm);
        animals.add(sharpShinnedHawk);
        animals.add(mosquito);
        animals.add(spider);
        animals.add(lobelia);
        animals.add(mouse);
        animals.add(cat);
    }

    private void createUserCharacter() {
        userCharacter = new Animal("hummingbird", 1, null, null, "female", "rubythroatedfemale", "hummingbird.mp3");
    }

    private ArrayList<Integer> createPrey() {
        ArrayList<Integer> preyIds = new ArrayList<Integer>();
        preyIds.add(2);
        preyIds.add(5);
        preyIds.add(6);
        preyIds.add(7);
        return preyIds;
    }

    private ArrayList<Integer> createPredators() {
        ArrayList<Integer> predatorIds = new ArrayList<Integer>();
        predatorIds.add(4);
        predatorIds.add(9);
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
