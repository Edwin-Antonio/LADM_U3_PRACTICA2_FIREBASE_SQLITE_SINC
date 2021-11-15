package mx.tecnm.tepic.ladm_u3_practica2_fb_sqlite

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    var dbRemota = FirebaseFirestore.getInstance()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        //DB Remota
        dbRemota.collection("notas")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    mensaje(error.message!!)
                    return@addSnapshotListener
                }
            }
        btnAceptar.setOnClickListener {
            insertarDBSQLite()
        }
        btn_regresarCrear.setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun insertarDBSQLite() {
        val notas = Notas(this)

        //Insertar SQLite
        notas.titulo = AgregarTitulo.text.toString()
        notas.contenido = AgregarContenido.text.toString()
        notas.horafecha = notas.getDateTime().toString()

        val resultado = notas.insertarNote()

        if (resultado) {
            Toast.makeText(this, "SE AGREGÓ CON ÉXITO", Toast.LENGTH_LONG).show()
            AgregarTitulo.text.clear()
            AgregarContenido.text.clear()
        } else {
            Toast.makeText(this, "ERROR NO SE HA AGREGADO", Toast.LENGTH_LONG).show()
        }

        //Insertar en Firestore
        var datosInsertar = hashMapOf (
            "titulo" to notas.titulo,
            "contenido" to notas.contenido,
            "horafecha" to notas.horafecha )

        dbRemota.collection("notas")
            .add(datosInsertar as Any)
            .addOnSuccessListener {
                alerta("SE AGREGÓ EN LA NUBE")
            }
            .addOnFailureListener {
                mensaje("ERROR: ${it.message!!}")
            }
    }

    private fun alerta(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    private fun mensaje(s: String) {
        AlertDialog.Builder(this)
            .setTitle("¡ATENCIÓN!")
            .setMessage(s)
            .setPositiveButton("OK"){d,i->}
            .show()
    }
}