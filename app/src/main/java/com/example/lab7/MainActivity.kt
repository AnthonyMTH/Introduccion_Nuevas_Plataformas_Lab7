package com.example.lab7

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: EdificacionAdapter
    private lateinit var edificaciones: List<Edificacion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val searchEditText: EditText = findViewById(R.id.searchEditText)

        // Cargar datos desde un archivo de texto o cualquier fuente
        edificaciones = loadEdificaciones()

        adapter = EdificacionAdapter(edificaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Configurar el filtro en tiempo real
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun loadEdificaciones(): List<Edificacion> {
        // Aquí lee el archivo de texto o define los elementos manualmente
        // Ejemplo de datos
        return listOf(
            Edificacion(R.drawable.edificio_a, "Catedral de la Ciudad de Arequipa", "Residencial", "Descripción del Edificio A"),
            Edificacion(R.drawable.edificio_b, "Monasterio Santa Catalina", "Comercial", "Descripción del Edificio B"),
            Edificacion(R.drawable.edificio_c, "Iglesia y Complejo de la Compañía", "Industrial", "Descripción del Edificio C"),
            Edificacion(R.drawable.edificio_d, "Los Tambos", "Comercial", "Descripción del Edificio D"),
            Edificacion(R.drawable.edificio_e, "Sabandia", "Industrial", "Descripción del Edificio E"),
            )
    }
}
