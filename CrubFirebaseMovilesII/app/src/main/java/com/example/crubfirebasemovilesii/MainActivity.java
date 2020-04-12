package com.example.crubfirebasemovilesii;

import android.content.Intent;
import android.os.Bundle;

import com.example.crubfirebasemovilesii.adapters.TituloAdapter;
import com.example.crubfirebasemovilesii.models.TituloModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mlv_main_titulos;
    private FloatingActionButton mfab_main_nuevo;
    private ArrayList<TituloModel> list;
    private TituloModel model;


    private final String text_reference = "titulos";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        mfab_main_nuevo = findViewById(R.id.fab_main_nuevo);
        mlv_main_titulos = findViewById(R.id.lv_main_titulos);
        list = new ArrayList<>();
        model = new TituloModel();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    model = child.getValue(TituloModel.class);
                    if(model != null){
                        list.add(model);
                    }
                }
                mlv_main_titulos.setAdapter(new TituloAdapter(MainActivity.this,list));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error Firebase", Toast.LENGTH_SHORT).show();
            }
        });

        mlv_main_titulos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model = (TituloModel) adapterView.getItemAtPosition(i);
                if(model != null){
                    if(!model.getMid().equals("") && model.getMid() != null){
                        Intent detalle = new Intent(MainActivity.this,DetalleActivity.class);
                        detalle.putExtra("id",model.getMid());
                        startActivity(detalle);
                    }
                }

            }
        });

        mfab_main_nuevo .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevo = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(nuevo);
            }
        });
    }
}
