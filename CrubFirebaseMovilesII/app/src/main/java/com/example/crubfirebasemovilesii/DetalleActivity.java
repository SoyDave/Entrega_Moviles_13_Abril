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
import android.widget.TextView;
import android.widget.Toast;

public class DetalleActivity extends AppCompatActivity {

    private TextView mtv_detalle_titulo, mtv_detalle_plataforma;
    private FloatingActionButton mfab_detalle_editar, mfab_detalle_eliminar;
    private TituloModel model;

    private final String text_reference = "titulos";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);

        mfab_detalle_editar  = findViewById(R.id.fab_detalle_editar);
        mfab_detalle_eliminar = findViewById(R.id.fab_detalle_eliminar);
        mtv_detalle_titulo = findViewById(R.id.tv_detalle_titulo);
        mtv_detalle_plataforma = findViewById(R.id.tv_detalle_plataforma);
        model = new TituloModel();

        String id = getIntent().getStringExtra("id");
        if (id != null && !id.equals("")){
            reference.child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    model = dataSnapshot.getValue(TituloModel.class);
                    if(model != null ){
                        mtv_detalle_titulo.setText(model.getMtitulo());
                        mtv_detalle_plataforma.setText(model.getMplataforma());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DetalleActivity.this,"Error Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }

        mfab_detalle_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model != null) {
                    if (model.getMid() != null && !model.getMid().equals("")) {
                        Intent editar = new Intent(DetalleActivity.this, EditarActivity.class);
                        editar.putExtra("id", model.getMid());
                        startActivity(editar);
                        finish();
                    }
                }
            }
        });

        mfab_detalle_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Snackbar snackbar = Snackbar.make(view, "Eliminaras el registro, ¿Estás Seguro?",Snackbar.LENGTH_LONG);
                snackbar.setAction("¡Sí!", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(model != null) {
                        if(model.getMid() != null && !model.getMid().equals("")){

                            reference.child(model.getMid()).removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                                Intent inicio = new Intent(DetalleActivity.this,MainActivity.class);
                                                 startActivity(inicio);
                                                 finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(view, "Error al Eliminar Registro", Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                             }
                        }
                    }
                });
                snackbar.show();
            }
        });
    }

}
