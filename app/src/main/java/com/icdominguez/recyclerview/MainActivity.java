package com.icdominguez.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {

    CustomRecyclerView adapter = null;
    RecyclerView recyclerView;

    ArrayList<Team> arrayList = new ArrayList<Team>();

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        arrayList.add(new Team("Barcelona", R.drawable.escudo_barcelona));
        arrayList.add(new Team("Real Madrid", R.drawable.escudo_real_madrid));
        arrayList.add(new Team("Atlético De Madrid",R.drawable.escudo_atleti));
        arrayList.add(new Team("Sevilla", R.drawable.escudo_sevilla));
        arrayList.add(new Team("Real Sociedad", R.drawable.escudo_real_sociedad));
        arrayList.add(new Team("Athletic Club De Bilbao", R.drawable.escudo_bilbao));
        arrayList.add(new Team("Betis",R.drawable.escudo_betis));
        arrayList.add(new Team("Celta",R.drawable.escudo_celta));
        arrayList.add(new Team("Getafe",R.drawable.escudo_getafe));
        arrayList.add(new Team("Málaga",R.drawable.escudo_malaga));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        adapter = new CustomRecyclerView(arrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor);
        swipeRefreshLayout.setProgressBackgroundColorSchemeResource(R.color.secondaryColor);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                cargarDatos();
            }
        });
    }

    private void cargarDatos() {
        new UnaTarea().execute();
    }

    private class UnaTarea extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            arrayList = new ArrayList<>();
            arrayList.add(new Team("Barcelona", R.drawable.escudo_barcelona));
            arrayList.add(new Team("Real Madrid", R.drawable.escudo_real_madrid));
            arrayList.add(new Team("Atlético De Madrid",R.drawable.escudo_atleti));
            arrayList.add(new Team("Sevilla", R.drawable.escudo_sevilla));
            arrayList.add(new Team("Real Sociedad", R.drawable.escudo_real_sociedad));
            arrayList.add(new Team("Athletic Club De Bilbao", R.drawable.escudo_bilbao));
            arrayList.add(new Team("Betis",R.drawable.escudo_betis));
            arrayList.add(new Team("Celta",R.drawable.escudo_celta));
            arrayList.add(new Team("Getafe",R.drawable.escudo_getafe));
            arrayList.add(new Team("Málaga",R.drawable.escudo_malaga));

            swipeRefreshLayout.setRefreshing(false);
            adapter.notifyDataSetChanged();
        }
    }

    Team deletedTeam = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP |ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            Collections.swap(arrayList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:
                    deletedTeam = arrayList.get(position);
                    Snackbar.make(recyclerView, "Deleted item: " + deletedTeam.getNombre(), BaseTransientBottomBar.LENGTH_SHORT)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    arrayList.add(position, deletedTeam);
                                    adapter.notifyItemInserted(position);
                                }
                            }).show();
                    arrayList.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;

                case ItemTouchHelper.RIGHT:

                    break;
            }

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                    .setSwipeLeftLabelColor(ContextCompat.getColor(MainActivity.this, R.color.secondaryTextColor))
                    .addSwipeLeftLabel("Delete")
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.green))
                    .addSwipeRightActionIcon(R.drawable.ic_archive)
                    .addSwipeRightLabel("Archive")
                    .setSwipeRightLabelColor(ContextCompat.getColor(MainActivity.this, R.color.secondaryTextColor))
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

}