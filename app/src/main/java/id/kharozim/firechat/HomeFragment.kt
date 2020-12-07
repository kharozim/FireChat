package id.kharozim.firechat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import id.kharozim.firechat.adapters.UserAdapter
import id.kharozim.firechat.clients.NotifClient
import id.kharozim.firechat.databinding.FragmentHomeBinding
import id.kharozim.firechat.models.DataModel
import id.kharozim.firechat.models.PayloadModelBody
import id.kharozim.firechat.models.UserModel
import id.kharozim.firechat.utils.Constant
import id.kharozim.firechat.utils.PreferencesHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), UserAdapter.UserListener {

//    private lateinit var binding: FragmentHomeBinding
//    private lateinit var action: ListenerRegistration
//    private val sharepref by lazy { PreferencesHelper(requireContext()) }
//    private val adapter by lazy { UserAdapter(requireContext(), this) }
//    private val db by lazy { Firebase.firestore }
//    private val service by lazy { NotifClient.service }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var action: ListenerRegistration
    private val adapter by lazy { UserAdapter(requireContext(), this) }
    private val sharepref by lazy { PreferencesHelper(requireContext()) }
    private val service by lazy { NotifClient.service }
    private val db by lazy { Firebase.firestore }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            rvUser.adapter = adapter
        }

        action = db.collection(Constant.COLLECTION).addSnapshotListener { value, error ->
            if (error != null) {
                error.printStackTrace()
                return@addSnapshotListener
            }

            val users = mutableListOf<UserModel>()

            value?.let {
                for (doc in it) {
                    doc.toObject(UserModel::class.java).let { model ->
                        if (model.uid != sharepref.uid) {
                            users.add(model)
                        }
                    }
                }
            }

            adapter.list = users

            println(users.map { it.email })
        }

        FirebaseMessaging.getInstance().token.addOnSuccessListener {
//            db.collection(Constant.COLLECTION).document(sharepref.uid)
//                .update(mapOf("token" to it))
            Log.e("TOKEN", it) }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()

        action.remove()
    }

    override fun onStart() {
        super.onStart()

        if (sharepref.uid.isEmpty()) requireActivity().onBackPressed()
    }


    override fun onSend(userModel: UserModel, message: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val payload = PayloadModelBody(
                    DataModel("New message", message, userModel.email),
                    userModel.token
                )
                val response = service.sendNotification(payload)
                println(response)
            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }
    }

}