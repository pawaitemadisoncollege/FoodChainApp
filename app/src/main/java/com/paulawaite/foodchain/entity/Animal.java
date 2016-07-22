package com.paulawaite.foodchain.entity;

import java.util.ArrayList;

/**
 * Created by paulawaite on 7/18/16.
 */
public class Animal {

    String name;
    int id;
    ArrayList<Integer> prey;
    ArrayList<Integer> predators;
    String gender;
    String image; // location of the image file
    String sound; // location of sound file for the animal

    public Animal(String name, int id, ArrayList<Integer> prey, ArrayList<Integer> predators, String gender, String image, String sound) {
        this.name = name;
        this.id = id;
        this.prey = prey;
        this.predators = predators;
        this.gender = gender;
        this.image = image;
        this.sound = sound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Integer> getPrey() {
        return prey;
    }

    public void setPrey(ArrayList<Integer> prey) {
        this.prey = prey;
    }

    public ArrayList<Integer> getPredators() {
        return predators;
    }

    public void setPredators(ArrayList<Integer> predators) {
        this.predators = predators;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public boolean isPrey(int id) {
        if (prey.contains(id)) {
            return true;
        }
        return false;
    }

    public boolean isPredator(int id) {
        if (predators.contains(id)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", prey=" + prey +
                ", predators=" + predators +
                ", gender='" + gender + '\'' +
                ", image='" + image + '\'' +
                ", sound='" + sound + '\'' +
                '}';
    }
}
