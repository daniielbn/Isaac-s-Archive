package Adaptadores

import android.content.Context
import android.graphics.Typeface
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.isaacsarchive.R

class AdaptadorSpinner(context: Context, items: List<String>): ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items) {
    private val tipografia: Typeface? = ResourcesCompat.getFont(context, R.font.montserrat_regular)
    override fun getDropDownView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.typeface = tipografia
        return view
    }

    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
        val view = super.getView(position, convertView, parent) as TextView
        view.typeface = tipografia
        return view
    }
}