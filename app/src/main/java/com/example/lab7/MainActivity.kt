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
import java.io.BufferedReader
import java.io.InputStreamReader

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
        val edificaciones = mutableListOf<Edificacion>()

        // Abrimos el archivo desde la carpeta raw
        val inputStream = resources.openRawResource(R.raw.edificaciones)
        val reader = BufferedReader(InputStreamReader(inputStream))

        reader.useLines { lines ->
            lines.forEach { line ->
                // Separar cada línea en sus partes
                val parts = line.split("|")
                if (parts.size == 4) {
                    val imageName = parts[0].trim()
                    val title = parts[1].trim()
                    val category = parts[2].trim()
                    val description = parts[3].trim()

                    // Obtener el recurso de la imagen por nombre
                    val imageResId = resources.getIdentifier(imageName, "drawable", packageName)

                    // Crear el objeto Edificacion y añadirlo a la lista
                    edificaciones.add(Edificacion(imageResId, title, category, description))
                }
            }
        }

        return edificaciones
    }
}
