package com.example.crubfirebasemovilesii;

import android.content.Intent;
import android.os.Bundle;

import com.example.crubfirebasemovilesii.models.TituloModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {

    private EditText met_editar_titulo, met_editar_plataforma;
    private FloatingActionButton mfab_editar_guardar;

    private TituloModel model;

    private final String text_reference = "titulos";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar_editar);
        setSupportActionBar(toolbar);

        met_editar_titulo = findViewById(R.id.et_editar_titulo);
        met_editar_plataforma = findViewById(R.id.et_editar_plataforma);
        mfab_editar_guardar = findViewById(R.id.fab_editar_guardar);
        model = new TituloModel();

        String id = getIntent().getStringExtra("id");
        if (id != null && !id.equals("")){
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    model = dataSnapshot.getValue(TituloModel.class);
                    if(model != null ){
                        met_editar_titulo.setText(model.getMtitulo());
                        met_editar_plataforma.setText(model.getMplataforma());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(EditarActivity.this,"Error Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }

        mfab_editar_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String titulo = met_editar_titulo.getText().toString();
                String plataforma = met_editar_plataforma.getText().toString();

                if (!titulo.equals("") && !plataforma.equals("")){
                    if(model != null) {
                        String id = model.getMid();

                        if (id != null && !id.equals("")) {
                            model.setMtitulo(titulo);
                            model.setMplataforma(plataforma);

                            reference.child(id).setValue(model)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if (!model.getMid().equals("") && model.getMid() != null) {
                                                Intent detalle = new Intent(EditarActivity.this, DetalleActivity.class);
                                                detalle.putExtra("id", model.getMid());
                                                startActivity(detalle);
                                                finish();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(view, "Error al actualizar", Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Snackbar.make(view, "Error al crear id en base de datos", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(EditarActivity.this,"Complete los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
