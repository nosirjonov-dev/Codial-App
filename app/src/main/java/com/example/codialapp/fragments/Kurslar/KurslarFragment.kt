package com.example.codialapp.fragments.Kurslar

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.R
import com.example.codialapp.adapters.KurslarAdapter
import com.example.codialapp.databinding.CustomDialogBinding
import com.example.codialapp.databinding.FragmentKurslarBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar


class KurslarFragment : Fragment() {
    lateinit var kurslarAdapter: KurslarAdapter
    lateinit var myDb: MyDb
    private val binding by lazy { FragmentKurslarBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDb(requireContext())
        binding.add.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val customDialogBinding = CustomDialogBinding.inflate(layoutInflater)
            customDialogBinding.qoshish.setOnClickListener {
                val kurs = Kurslar(
                    1,
                    customDialogBinding.name.text.toString(),
                    customDialogBinding.about.text.toString()
                )
                myDb.addKurs(kurs)
                onResume()
                dialog.cancel()
            }
            customDialogBinding.yopish.setOnClickListener {
                dialog.cancel()
            }
            dialog.setView(customDialogBinding.root)
            dialog.show()
        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val list = myDb.showKurs()
        kurslarAdapter = KurslarAdapter(requireContext(),object :KurslarAdapter.RvAction{
            override fun onClick(position: Int,kurslar: Kurslar) {
                findNavController().navigate(R.id.dataFragment, bundleOf("position" to position))
            }
        },list)
        binding.rv.adapter = kurslarAdapter
    }

}