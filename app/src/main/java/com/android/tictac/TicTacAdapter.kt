package com.android.tictac

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tictac.MainActivity.Companion.EMPTY
import com.android.tictac.MainActivity.Companion.ROBOT
import com.android.tictac.MainActivity.Companion.USER
import kotlinx.android.synthetic.main.item_box.view.*


class TicTacAdapter(
    var context: Context,
    var arrayList: ArrayList<Int>,
    val listener: ClickListener
) : RecyclerView.Adapter<TicTacAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_box,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayList[position], listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            item: Int,
            listener: ClickListener
        ) {
            when (item) {
                USER -> {
                    itemView.imageView.setImageResource(R.drawable.ic_heart)
                }
                ROBOT -> {
                    itemView.imageView.setImageResource(R.drawable.ic_cross)
                }
                else -> {
                    itemView.imageView.setImageDrawable(null)
                }
            }
            itemView.setOnClickListener {
                if (item != EMPTY) return@setOnClickListener
                listener.onClick(adapterPosition)
            }
        }
    }

    interface ClickListener {
        fun onClick(position: Int)
    }
}