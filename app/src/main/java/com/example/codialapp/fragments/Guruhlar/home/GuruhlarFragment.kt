package com.example.codialapp.fragments.Guruhlar.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.R
import com.example.codialapp.adapters.KurslarAdapter
import com.example.codialapp.adapters.MyData
import com.example.codialapp.databinding.FragmentGuruhlarBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar


class GuruhlarFragment : Fragment() {
    private val binding by lazy { FragmentGuruhlarBinding.inflate(layoutInflater) }
    lateinit var kurslarAdapter: KurslarAdapter
    lateinit var myDb: MyDb
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDb(requireContext())
        val list = myDb.showKurs()
        kurslarAdapter = KurslarAdapter(requireContext(),object :KurslarAdapter.RvAction{
            override fun onClick(position: Int, kurslar: Kurslar) {
                for (mentorlar in myDb.showMentor()) {
                    if (mentorlar.kurs_id?.id==kurslar.id){
                        findNavController().navigate(R.id.addGroupFragment, bundleOf("ali" to kurslar))
                        MyData.kurslar = kurslar
                    }else{
                        Toast.makeText(context, "Bu yo'nalishda hali mentor yo'q", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        },list)

        binding.rv.adapter = kurslarAdapter
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

}