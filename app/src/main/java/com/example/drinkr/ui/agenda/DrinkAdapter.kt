package com.example.drinkr.ui.agenda

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drinkr.*

class DrinkAdapter(private val context: Context, drinkModelArrayList: ArrayList<DrinkModel>) :
RecyclerView.Adapter<DrinkAdapter.Viewholder>() {
    private val drinkModelArrayList: ArrayList<DrinkModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkAdapter.Viewholder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.drink_card, parent, false)
        return Viewholder(view)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model: DrinkModel = drinkModelArrayList[position]
        holder.drinkTitle.text = model.getDrink_name()
        holder.drinkAmount.text = model.getDrink_amount()
        holder.drinkType.text = model.getDrink_type()
        holder.drinkTime.text = model.getDrink_time()
        holder.drinkDate.text = model.getDrink_date()

        holder.deleteButton.setOnClickListener {
            val removeline = model.getDrink_date() + ";" + model.getDrink_time() + ";" +
                    model.getDrink_name() + ";" + model.getDrink_type() +
                    ";" + model.getDrink_amount()
            removeLineFromFile(context, "drinkStorage.txt", model,
                drinkModelArrayList, removeline)
            notifyItemRemoved(position)
            //notifyItemRangeRemoved(position, drinkModelArrayList.size)
        }
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return drinkModelArrayList.size
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val drinkTitle: TextView
        val drinkAmount: TextView
        val drinkType: TextView
        val drinkTime: TextView
        val drinkDate: TextView
        val deleteButton: Button
        init {
            drinkTitle = itemView.findViewById(R.id.text_drink_name)
            drinkAmount = itemView.findViewById(R.id.text_drink_amount)
            drinkType = itemView.findViewById(R.id.text_drink_type)
            drinkTime = itemView.findViewById(R.id.text_drink_time)
            drinkDate = itemView.findViewById(R.id.text_drink_date)
            deleteButton = itemView.findViewById(R.id.delete_drink)
        }
    }

    // Constructor
    init {
        this.drinkModelArrayList = drinkModelArrayList
    }
}