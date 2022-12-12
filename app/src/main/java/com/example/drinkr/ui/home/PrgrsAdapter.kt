package com.example.drinkr.ui.agenda

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkr.*

class PrgrsAdapter(private val context: Context, drinkprgrsMap: MutableMap<String, Int>) :
    RecyclerView.Adapter<PrgrsAdapter.Viewholder>() {
    private var drinkprgrsMap: MutableMap<String, Int>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrgrsAdapter.Viewholder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.overview_card, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model = drinkprgrsMap.keys.elementAt(position)
        holder.drinktype.text = model
        holder.drinkprgrs.text = "${drinkprgrsMap[model].toString()}cl"
        holder.drinkprgrsbar.progress = drinkprgrsMap[model]!!

        //change the start and end color based on the type of drink and use a gradient
        if (drinkprgrsMap.keys.elementAt(position) == "Water") {
            holder.drinkprgrsbar.progressDrawable =
                AppCompatResources.getDrawable(context, R.drawable.circular_water)
        }

        //animate the progressbar
        itemAnimation(holder)
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return drinkprgrsMap.size
    }

    @SuppressLint("ObjectAnimatorBinding") //else it gives an error but it works flawlessly
    private fun itemAnimation(holder: Viewholder){
        val animator = ObjectAnimator.ofInt(holder.itemView.findViewById(R.id.rcv_progressBar),
            "progress", 0, holder.drinkprgrsbar.progress)
        //"progress"shows an error
        animator.duration = 2500 //in milliseconds
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinktype: TextView
        val drinkprgrs: TextView
        val drinkprgrsbar: ProgressBar
        init {
            drinktype = itemView.findViewById(R.id.text_prgrs_type)
            drinkprgrs = itemView.findViewById(R.id.text_prgrs_amount)
            drinkprgrsbar = itemView.findViewById(R.id.rcv_progressBar)
        }
    }

    // Constructor
    init {
        this.drinkprgrsMap = drinkprgrsMap
    }
}