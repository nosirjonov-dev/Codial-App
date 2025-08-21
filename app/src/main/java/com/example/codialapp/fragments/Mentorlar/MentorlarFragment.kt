package com.example.codialapp.fragments.Mentorlar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.codialapp.R
import com.example.codialapp.adapters.KurslarAdapter
import com.example.codialapp.databinding.FragmentMentorlarBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar


class MentorlarFragment : Fragment() {
    lateinit var kurslarAdapter: KurslarAdapter
    lateinit var myDb: MyDb
    private val binding by lazy { FragmentMentorlarBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDb(requireContext())
        val list = myDb.showKurs()
        kurslarAdapter = KurslarAdapter(requireContext(),object : KurslarAdapter.RvAction{
            override fun onClick(position: Int,kurslar: Kurslar) {
                findNavController().navigate(R.id.royxatFragment, bundleOf("p" to kurslar))
            }
        },list)
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.rv.adapter = kurslarAdapter
        return binding.root
    }
}