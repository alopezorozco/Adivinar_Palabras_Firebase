package com.example.adivinar_palabras_firebase

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

class CategoriaAdapter (context: Context):
    RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder>(){

    //Paso 1. escribir lineas desde la 12 hasta la 26
    //creamos un objeto de tipo Resources
    private val listaCategorias : List<String>

    init{
        listaCategorias = context.resources.getStringArray(R.array.categorias).toList()
    }

    /**
     * Proveemos una referencia a las vistas necesarias para mostrar la lista
     * de items
     */
    class CategoriaViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val button = view.findViewById<Button>(R.id.button_item)
    }

    //Paso 2. implementar los métodos abstractos

    /**
     * Crea nuevas views con R.layout.item_view como template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        var layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)

        //copiamos el código que esta antes del final
        layout.accessibilityDelegate = Accessibility

        return CategoriaViewHolder(layout)
    }

    /**
     * Reemplaza el contenido de un existente view con nuevos datos
     */
    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val item = listaCategorias.get(position)
        holder.button.text = item.toString()

        holder.button.setOnClickListener {
            val context = holder.view.context

            val intent = Intent(context, DetailActivity::class.java)

            intent.putExtra(DetailActivity.CATEGORIA, holder.button.text.toString())

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listaCategorias.size
    }



    // Setup custom accessibility delegate to set the text read with
    // an accessibility service
    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View?,
            info: AccessibilityNodeInfo?
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customString = host?.context?.getString(R.string.buscar_palabras)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }

}

