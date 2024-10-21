package com.example.la4

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

class AttendeesAdapter(
    private val attendeesList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<AttendeesAdapter.AttendeeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendeeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_attendee, parent, false)
        return AttendeeViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttendeeViewHolder, position: Int) {
        val attendee = attendeesList[position]
        holder.attendeeName.text = attendee
        holder.itemView.setOnClickListener {
            onItemClick(attendee)
        }
    }


    override fun getItemCount(): Int = attendeesList.size

    inner class AttendeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val attendeeName: TextView = itemView.findViewById(R.id.attendee_name)

        fun bind(attendee: String) {
            attendeeName.text = attendee
            itemView.setOnClickListener {
                onItemClick(attendee)
            }
        }
    }
}
