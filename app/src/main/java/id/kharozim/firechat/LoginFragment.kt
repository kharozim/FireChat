package id.kharozim.firechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.kharozim.firechat.databinding.FragmentLoginBinding
import id.kharozim.firechat.models.UserModel
import id.kharozim.firechat.utils.Constant
import id.kharozim.firechat.utils.PreferencesHelper

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val auth by lazy { Firebase.auth }
    private val sharepref by lazy { PreferencesHelper(requireContext()) }
    private val db by lazy { Firebase.firestore }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {

            btLogin.setOnClickListener {
                if (tieEmail.text.isNullOrEmpty() || tiePassword.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Email dan Password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showLoading(true)
                    auth.signInWithEmailAndPassword(
                        tieEmail.text.toString(),
                        tiePassword.text.toString()
                    )
                        .addOnSuccessListener {
                            if (it.user?.isEmailVerified == true) {
                                it.user?.uid?.let { uidKey ->
                                    sharepref.uid = uidKey
                                    showMessage(sharepref.uid)
                                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

                                }
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Buka Emailmu.! Email Belum di ferivikasi",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            showLoading(false)
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            Toast.makeText(
                                requireContext(),
                                it.message ?: "Opps something wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                }
            }
            btSignup.setOnClickListener {
                if (tieEmail.text.isNullOrEmpty() || tiePassword.text.isNullOrEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        "Email dan Password tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showLoading(true)
                    auth.createUserWithEmailAndPassword(
                        tieEmail.text.toString(),
                        tiePassword.text.toString()
                    )
                        .addOnSuccessListener {
                            it.user?.sendEmailVerification()
                            it.user?.uid?.let { uidKey ->
                                sharepref.uid = uidKey
//                                showMessage(sharepref.uid)
                                db.collection(Constant.COLLECTION).document(uidKey)
                                    .set(UserModel(uidKey, tieEmail.text.toString()))
                                    .addOnSuccessListener {
//                                        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                                    }
                            }
                            showMessage("${it.user?.email} berhasil registrasi.! silahkan cek email untuk ferivikasi")
                            showLoading(false)
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                            Toast.makeText(
                                requireContext(),
                                it.message ?: "Opps something wrong",
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoading(false)
                        }
                }
            }
        }
        return binding.root
    }

       override fun onStart() {
           super.onStart()
           if(sharepref.uid.isNotEmpty())
               findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
       }

    private fun showLoading(isLoading: Boolean) {
        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
        binding.btLogin.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
        binding.btSignup.visibility = if (!isLoading) View.VISIBLE else View.INVISIBLE
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
