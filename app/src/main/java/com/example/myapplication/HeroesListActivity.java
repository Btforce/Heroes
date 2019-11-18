package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeroesListActivity extends AppCompatActivity {

    private Gson gson = new Gson();
    private Hero[] hero;
    private List<Hero> heroesList;
    private ListView listView;
    private TextView textViewRank;
    private TextView textViewDescription;
    private TextView textViewName;
    private HeroAdapter heroAdapter;
    private Comparator<Hero> comparator;

    public static final String TAG = HeroesListActivity.class.getSimpleName();

    public static final String EXTRA_HERO = "Hero";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroes_list);
        wireWidgets();
        InputStream jsonFileInputStream = getResources().openRawResource(R.raw.heroes);
        String jsonString = readTextFile(jsonFileInputStream);


        hero = gson.fromJson(jsonString, Hero[].class);

        heroesList = Arrays.asList(hero);

        comparator = new Comparator<Hero>() {
            @Override
            public int compare(Hero hero, Hero t1) {
                return hero.getName().compareTo(t1.getName());
            }
        };

        Collections.sort(heroesList);

        heroAdapter = new HeroAdapter(heroesList);

        listView.setAdapter(heroAdapter);



        setListeners();

    }



    private void setListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent heroDetailIntent =
                        new Intent(HeroesListActivity.this, HeroDetailActivity.class);

                heroDetailIntent.putExtra(EXTRA_HERO, heroesList.get(i));

                startActivity(heroDetailIntent);

            }
        });
    }

    private void wireWidgets() {
        listView = findViewById(R.id.listView_heroeslist_listview);
        textViewDescription = findViewById(R.id.textView_heroitem_description);
        textViewName = findViewById(R.id.textView_heroitem_name);
        textViewRank = findViewById(R.id.textView_heroitem_rank);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.hero_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.menu_item_sort_by_rank:
                sortByRank();
                heroAdapter.notifyDataSetChanged();
                return true;

            case R.id.menu_item_sort_by_alphabetical:
                sortByAlphabetical();
                heroAdapter.notifyDataSetChanged();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


}

    private void sortByAlphabetical() {
        Collections.sort(heroesList, comparator);
    }

    private void sortByRank() {
        Collections.sort(heroesList);
    }

    //reading the text file from
    //https://stackoverflow.com/questions/15912825/how-to-read-file-from-res-raw-by-name
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }
    private class HeroAdapter extends ArrayAdapter<Hero> {
        private List<Hero> heroesList;
        private int position;
        public HeroAdapter(List<Hero> heroesList) {
            //since we are in the HeroListActivity class, we already have the context
            //we are hardcoding in a particular layout, so do not need to put it in
            //the contructor is either
            //we will send a placeholder resource to the superclass of -1
            super(HeroesListActivity.this , -1, heroesList);
            this.heroesList = heroesList;
        }

        //The goal of the adapter is to link the your list to the listview
        //and tell the listview where each aspect of the list item goes
        //so we override a method called getView


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            this.position = position;

            //1. inflate a layout
            LayoutInflater inflater = getLayoutInflater();

            //check if convertView is null, if so, replace it
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_hero, parent, false);
            }

            //2. wire widgets and link the hero to those widgets

            textViewName = convertView.findViewById(R.id.textView_heroitem_name);

            textViewRank = convertView.findViewById(R.id.textView_heroitem_rank);

            textViewDescription = convertView.findViewById(R.id.textView_heroitem_description);

            //set the values for each widget use the position parameter variable
            //to get the hero that you need out of the list
            //and set the values for widgets

            textViewDescription.setText(heroesList.get(position).getDescription());

            textViewName.setText(heroesList.get(position).getName());

            textViewRank.setText(String.valueOf(heroesList.get(position).getRanking()));

            Log.d(TAG, "getView: " + heroesList.get(position).getRanking());

            

            //3. return inflated view
            return convertView;
        }
    }


}