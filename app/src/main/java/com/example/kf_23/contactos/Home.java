package com.example.kf_23.contactos;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    private static final String TAG = "Mensaje";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText usuario, contraseña, nombres, apellidos;
    TextView mensaje;
    Button verificar, actualizar;
    Boolean existe = false;
    String nUsuario, idUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        nombres = findViewById(R.id.nombres);
        apellidos = findViewById(R.id.apellidos);

        mensaje = findViewById(R.id.mensaje);

        verificar = findViewById(R.id.verificar);
        actualizar = findViewById(R.id.actualizar);

        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("usuarios")
                        .whereEqualTo("usuario", usuario.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        existe = true;
                                        nUsuario = document.getString("usuario");
                                        idUsuario = document.getId();
//                                        Log.d("Usuario",nUsuario);
                                    }
                                    if (existe){
                                        mensaje.setText(nUsuario);
                                        usuario.setVisibility(View.GONE);
                                        contraseña.setVisibility(View.GONE);
                                        nombres.setVisibility(View.VISIBLE);
                                        apellidos.setVisibility(View.VISIBLE);
                                        verificar.setVisibility(View.GONE);
                                        actualizar.setVisibility(View.VISIBLE);
                                    }else {
                                        mensaje.setText("No existe un usuario");
                                    }


                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());

                                }
                            }
                        });


            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference user = db.collection("usuarios").document(nUsuario);

                // Add a new document with a generated id.
                Map<String, Object> data = new HashMap<>();
                data.put("nombres", nombres.getText().toString());
                data.put("apellidos", apellidos.getText().toString());


                user
                        .update(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Usuario "+nUsuario+" actualizado correctamente");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error al actualizar "+nUsuario, e);
                            }
                        });

            }
        });




//        CollectionReference cities = db.collection("cities");
//
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("name", "San Francisco");
//        data1.put("state", "CA");
//        data1.put("country", "USA");
//        data1.put("capital", false);
//        data1.put("population", 860000);
//        cities.document("SF").set(data1);
//
//        Map<String, Object> data2 = new HashMap<>();
//        data2.put("name", "Los Angeles");
//        data2.put("state", "CA");
//        data2.put("country", "USA");
//        data2.put("capital", false);
//        data2.put("population", 3900000);
//        cities.document("LA").set(data2);
//
//        Map<String, Object> data3 = new HashMap<>();
//        data3.put("name", "Washington D.C.");
//        data3.put("state", null);
//        data3.put("country", "USA");
//        data3.put("capital", true);
//        data3.put("population", 680000);
//        cities.document("DC").set(data3);
//
//        Map<String, Object> data4 = new HashMap<>();
//        data4.put("name", "Tokyo");
//        data4.put("state", null);
//        data4.put("country", "Japan");
//        data4.put("capital", true);
//        data4.put("population", 9000000);
//        cities.document("TOK").set(data4);
//
//        Map<String, Object> data5 = new HashMap<>();
//        data5.put("name", "Beijing");
//        data5.put("state", null);
//        data5.put("country", "China");
//        data5.put("capital", true);
//        data5.put("population", 21500000);
//        cities.document("BJ").set(data5);
//
//
//        DocumentReference docRef = db.collection("cities").document("BJ");
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                City city = documentSnapshot.toObject(City.class);
//                Log.d("City",city.getName());
//            }
//        });
//
//        final ArrayList<City> cities1 = new ArrayList<>();

    }
}
