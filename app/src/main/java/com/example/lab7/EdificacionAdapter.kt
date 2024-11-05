package com.example.lab7

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EdificacionAdapter(private var edificaciones: List<Edificacion>) :
    RecyclerView.Adapter<EdificacionAdapter.EdificacionViewHolder>(), Filterable {

    private var edificacionListFiltered = edificaciones

    inner class EdificacionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Referencias a los elementos visuales del layout item_edificacion
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val title: TextView = itemView.findViewById(R.id.title)
        val category: TextView = itemView.findViewById(R.id.category)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    // Crea una nueva vista para cada elemento cuando no hay vistas recicladas disponibles
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EdificacionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_edificacion, parent, false)
        return EdificacionViewHolder(view)
    }

    // Vincula los datos de un objeto Edificacion a los elementos visuales de la vista
    override fun onBindViewHolder(holder: EdificacionViewHolder, position: Int) {
        val edificacion = edificacionListFiltered[position] // Obtenemos el objeto Edificacion en la posición actual

        // Asignamos los valores de Edificacion a los elementos visuales del ViewHolder
        holder.title.text = edificacion.title
        holder.category.text = edificacion.category
        holder.description.text = edificacion.description
        holder.imageView.setImageResource(edificacion.imageResId)
    }

    override fun getItemCount(): Int = edificacionListFiltered.size

    override fun getFilter(): Filter {
        return object : Filter() {
            // Realiza el filtrado en segundo plano basado en el texto ingresado
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                // Filtramos la lista según si el texto está vacío o coincide con el título
                edificacionListFiltered = if (charString.isEmpty()) {
                    edificaciones   // Sin filtro, se muestra la lista completa
                } else {
                    edificaciones.filter { it.title.contains(charString, ignoreCase = true) }
                }
                val filterResults = FilterResults()
                filterResults.values = edificacionListFiltered
                return filterResults
            }

            // "Publica" los resultados del filtrado en la interfaz de usuario
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                // Actualiza la lista filtrada con los resultados obtenidos
                edificacionListFiltered = results?.values as List<Edificacion>
                notifyDataSetChanged()  // Notifica al adaptador que la lista ha cambiado para actualizar la vista
            }
        }
    }
}
