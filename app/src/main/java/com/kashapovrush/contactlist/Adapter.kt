package com.kashapovrush.contactlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kashapovrush.contactlist.UserList.deleteList

class Adapter(var onChangedVisibility: () -> Boolean) : ListAdapter<User, Adapter.ViewHolder>(DiffUtilCallback()) {

    var onClickListener: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.firstName.text = user.firstName
        holder.lastName.text = user.lastName
        holder.phoneNumber.text = user.phoneNumber
        holder.checkBox.isChecked = user.deletionState

        if (onChangedVisibility()) {
            holder.deletionState.visibility = View.VISIBLE
        } else {
            holder.deletionState.visibility = View.INVISIBLE
        }

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                deleteList.add(user)
            } else {
                deleteList.remove(user)
            }
        }

        holder.view.setOnClickListener {
            onClickListener?.invoke(user)
        }
    }


    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val firstName = view.findViewById<TextView>(R.id.first_name)
        val lastName = view.findViewById<TextView>(R.id.last_name)
        val phoneNumber = view.findViewById<TextView>(R.id.phone_number)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val deletionState = view.findViewById<LinearLayout>(R.id.deletionMark)
    }
}