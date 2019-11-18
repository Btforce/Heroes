package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Hero implements Parcelable, Comparable<Hero>{
    private int ranking;
    private String description;
    private String name;
    private String superpower;
    private String image;

    Hero(int rank, String description, String name){
        this.ranking = rank;
        this.description = description;
        this.name = name;
    }


    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ranking);
        dest.writeString(this.description);
        dest.writeString(this.name);
        dest.writeString(this.superpower);
        dest.writeString(this.image);
    }

    protected Hero(Parcel in) {
        this.ranking = in.readInt();
        this.description = in.readString();
        this.name = in.readString();
        this.superpower = in.readString();
        this.image = in.readString();
    }

    public static final Creator<Hero> CREATOR = new Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel source) {
            return new Hero(source);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };


    @Override
    public int compareTo(Hero hero) {
        return this.getRanking() - hero.getRanking();
    }
}


