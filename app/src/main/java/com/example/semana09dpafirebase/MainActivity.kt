package com.example.semana09dpafirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = FirebaseFirestore.getInstance()  //IMPORTAR EL FIREBASEFIRESTORE
        val tvCurso: TextView = findViewById(R.id.tvCurso) //IMPORTAR EL TEXTVIEW
        val tvNota: TextView = findViewById(R.id.tvNota)

        db.collection("courses")
            .addSnapshotListener { snapshots, e ->
                if(e!=null) {
                    Snackbar.make(findViewById(android.R.id.content)
                        , "Ocurrio un error al obtener la coleccion"
                        , Snackbar.LENGTH_LONG).show() //IMPORTO EL SNACKBAR
                return@addSnapshotListener

            }
            for (dc in snapshots!!.documentChanges){
                when(dc.type){
                    DocumentChange.Type.ADDED, DocumentChange.Type.MODIFIED->{
                        Snackbar.make(findViewById(android.R.id.content) //COPIA DE ARRIBA
                            , "Se agrego o modifico un documento" //CAMBIO EL TEXTO
                            , Snackbar.LENGTH_LONG).show() //IMPORTO EL SNACKBAR
                        tvCurso.text = dc.document.data["descripcion"]. toString()
                        tvNota.text = dc.document.data["score"]. toString()
                    }
                DocumentChange.Type.REMOVED ->{
                    Snackbar.make(findViewById(android.R.id.content) //COPIA DE ARRIBA
                        , "Se elimino un documento" //CAMBIO EL TEXTO
                        , Snackbar.LENGTH_LONG).show() //IMPORTO EL SNACKBAR
                    }
                }
            }

        }
    }
}