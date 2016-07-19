package com.paulawaite.foodchain.entity;

import java.util.ArrayList;

/**
 * Created by paulawaite on 7/18/16.
 */
public class Animal {

    String name;
    int id;
    ArrayList<Animal> prey;
    ArrayList<Animal> predators;
    String gender;
    String image; // location of the image file
    String sound; // location of sound file for the animal

    public Animal(String name, int id, ArrayList<Animal> prey, ArrayList<Animal> predators, String gender, String image, String sound) {
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

    public ArrayList<Animal> getPrey() {
        return prey;
    }

    public void setPrey(ArrayList<Animal> prey) {
        this.prey = prey;
    }

    public ArrayList<Animal> getPredators() {
        return predators;
    }

    public void setPredators(ArrayList<Animal> predators) {
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
