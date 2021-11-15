package mx.tecnm.tepic.ladm_u3_practica2_fb_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity : AppCompatActivity() {
    var dbRemota = FirebaseFirestore.getInstance()
    var datalista = ArrayList<String>()
    var listaID = ArrayList<String>()
    var notaHF = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Actualiza las notas en el ListView
        notasActualizar()

        //Listo prro
        crear.setOnClickListener {
            val intentoAgregar = Intent(this, MainActivity3::class.java)
            startActivity(intentoAgregar)
        }

        //Listo prro 2
        abrir.setOnClickListener {
            val intentoAbrir = Intent(this, MainActivity2::class.java)
            startActivity(intentoAbrir)
        }

        //Listo prro 3
        borrar.setOnClickListener {
            val intentoBorrar = Intent(this, MainActivity4::class.java)
            startActivity(intentoBorrar)
        }
        dbRemota.collection("notas")
            .addSnapshotListener { querySnapshot, error ->
                datalista.clear()
                listaID.clear()
                for (document in querySnapshot!!){
                    var cadena = "${document.getString("horafecha")}\n${document.getString("titulo")}\n${document.get(("contenido"))}"
                    datalista.add(cadena)
                    listaID.add(document.id.toString())
                    notaHF = "${document.getString("horafecha")}"
                }
                listaFB.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, datalista)
                listaFB.setOnItemClickListener { adapterView, view, posicion, l ->
                    eventAction(posicion)
                }
            }
    }

    private fun notasActualizar() {
        val notas = Notas(this).getNotas()
        listaFB.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,notas)
    }

    private fun eventAction(posicion: Int) {
        var idElegido = listaID.get(posicion)
        AlertDialog.Builder(this)
            .setTitle("ATENCION")
            .setMessage("¿QUÉ HACER CON ESTA NOTA?")
            .setPositiveButton("ELIMINAR"){d,i -> eliminar(idElegido)}
            .setNegativeButton("CANCELAR"){d,i-> d.cancel()}
            .show()
    }
    private fun eliminar(idElegido: String) {
        //SQLite
        Notas(this).eliminarNotasHF(notaHF)
        //Nota(this).insertNota()
        //DB Firestore
        dbRemota.collection("notas")
            .document(idElegido)
            .delete()
            .addOnSuccessListener { alerta("SE ELIMINÓ CON ÉXITO") }
            .addOnFailureListener { mensaje("ERROR: ${it.message!!}") }
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