package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HeroDetailActivity extends AppCompatActivity {

    private TextView textView_Description;
    private TextView textView_Name;
    private TextView textView_superpower;
    private ImageView imageView;
    private TextView textView_Ranking;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);

        Intent lastIntent = getIntent();

        Hero hero = lastIntent.getParcelableExtra(HeroesListActivity.EXTRA_HERO);

        wireWidgets();

        textView_Description.setText(hero.getDescription());
        textView_superpower.setText(hero.getSuperpower());
        textView_Name.setText(hero.getName());
        textView_Ranking.setText(String.valueOf(hero.getRanking()));

        int resourceImage = getResources().getIdentifier(hero.getImage(), "drawable", getPackageName());
        imageView.setImageDrawable(getResources().getDrawable(resourceImage));



    }

    private void wireWidgets() {
        textView_Description = findViewById(R.id.textView_herodetail_description);
        textView_Name = findViewById(R.id.textView_herodetail_name);
        textView_superpower = findViewById(R.id.textView_herodetail_superpower);
        imageView = findViewById(R.id.imageView_herodetail_picture);
        textView_Ranking = findViewById(R.id.textView_herodetail_ranking);
    }
}
