package id.kharozim.firechat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.kharozim.firechat.databinding.ItemUserNotificationBinding
import id.kharozim.firechat.models.UserModel

class UserAdapter(private val context: Context, private val listener: UserListener) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    interface UserListener {
        fun onSend(userModel: UserModel, message: String)
    }

    inner class ViewHolder(val binding: ItemUserNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(userModel: UserModel) {
            binding.tvEmail.text = userModel.email
        }
    }

    var list = mutableListOf<UserModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserNotificationBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        holder.bindData(model)
        holder.binding.btnSend.setOnClickListener {
            listener.onSend(model, holder.binding.tieMessage.text.toString())

            holder.binding.tieMessage.setText("")
        }
    }

    override fun getItemCount(): Int = list.size
}