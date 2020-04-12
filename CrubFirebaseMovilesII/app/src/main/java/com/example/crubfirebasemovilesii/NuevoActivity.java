package com.example.crubfirebasemovilesii;

import android.content.Intent;
import android.os.Bundle;

import com.example.crubfirebasemovilesii.models.TituloModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NuevoActivity extends AppCompatActivity {

    private EditText met_nuevo_titulo,met_nuevo_plataforma;
    private FloatingActionButton mfab_nuevo_guardar;
    private TituloModel model;

    private final String text_reference = "titulos";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        Toolbar toolbar = findViewById(R.id.toolbar_nuevo);
        setSupportActionBar(toolbar);

        met_nuevo_titulo = findViewById(R.id.et_nuevo_titulo);
        met_nuevo_plataforma = findViewById(R.id.et_nuevo_plataforma);
        mfab_nuevo_guardar = findViewById(R.id.fab_nuevo_guardar);

        mfab_nuevo_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String titulo = met_nuevo_titulo.getText().toString();
                String plataforma = met_nuevo_plataforma.getText().toString();

                if (!titulo.equals("") && !plataforma.equals("")){
                    String id = reference.push().getKey();
                    if(id != null && !id.equals("")){
                    model = new TituloModel(id, titulo, plataforma);
                    reference.child(id).setValue(model)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if(!model.getMid().equals("") && model.getMid() != null){
                                        Intent detalle = new Intent(NuevoActivity.this,DetalleActivity.class);
                                        detalle.putExtra("id",model.getMid());
                                        startActivity(detalle);
                                        finish();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                       Snackbar.make(view, "Error al guardar", Snackbar.LENGTH_SHORT).show();
                                }
                            });
                    }else{
                        Snackbar.make(view, "Error al crear id en base de datos", Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(NuevoActivity.this,"Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
