package id.kharozim.firechat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.kharozim.firechat.databinding.FragmentHomeBinding
import id.kharozim.firechat.databinding.FragmentLoginBinding
import id.kharozim.firechat.utils.PreferencesHelper

class HomeFragment : Fragment() {

    private val sharepref by lazy { PreferencesHelper(requireContext()) }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false).apply {


        }
        return binding.root
    }

}