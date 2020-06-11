package company.domain.retrofitstudyapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import company.domain.retrofitstudyapp.R
import company.domain.retrofitstudyapp.models.User

class UserAdapter(val mContext: Context?, val userList: List<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_users, parent, false)

        return UserViewHolder(view)

    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        holder.textViewName.text = user.name
        holder.textViewEmail.text = user.email
        holder.textViewSchool.text = user.school

    }

    override fun getItemCount(): Int = userList.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewName = itemView.findViewById<TextView>(R.id.textViewName)
        val textViewEmail = itemView.findViewById<TextView>(R.id.textViewEmail)
        val textViewSchool = itemView.findViewById<TextView>(R.id.textViewSchool)

//        var textViewName: TextView
//        var textViewEmail: TextView
//        var textViewSchool: TextView
//
//        init {
//            textViewName = itemView.findViewById(R.id.textViewName)
//            textViewEmail = itemView.findViewById(R.id.textViewEmail)
//            textViewSchool = itemView.findViewById(R.id.textViewSchool)
//
//        }
    }
}