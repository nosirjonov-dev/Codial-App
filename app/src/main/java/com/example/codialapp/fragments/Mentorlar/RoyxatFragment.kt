package com.example.codialapp.fragments.Mentorlar

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codialapp.R
import com.example.codialapp.adapters.MentorlarAdapter
import com.example.codialapp.databinding.CustomDialogBinding
import com.example.codialapp.databinding.CustomMentorBinding
import com.example.codialapp.databinding.FragmentRoyxatBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Kurslar
import com.example.codialapp.models.Mentorlar


class RoyxatFragment : Fragment(),MentorlarAdapter.RvAction {
    private val binding by lazy { FragmentRoyxatBinding.inflate(layoutInflater) }
    lateinit var mentorlarAdapter: MentorlarAdapter
    lateinit var myDb: MyDb
    lateinit var list:ArrayList<Mentorlar>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myDb = MyDb(requireContext())
        val mentorId = arguments?.getSerializable("p") as Kurslar
        println("salom")
        println(mentorId)
        println("salom")
        onResume()
        binding.kursName.text = mentorId.name
        binding.add.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val customMentorBinding = CustomMentorBinding.inflate(layoutInflater)
            customMentorBinding.qoshish.setOnClickListener {
                if (customMentorBinding.name.text.isNotEmpty() && customMentorBinding.lastName.text.isNotEmpty() && customMentorBinding.number.text.isNotEmpty()) {
                    val mentor = Mentorlar(
                        1,
                        customMentorBinding.name.text.toString(),
                        customMentorBinding.lastName.text.toString(),
                        customMentorBinding.number.text.toString(),
                        mentorId
                    )
                    myDb.addMentor(mentor)
                }else{
                    Toast.makeText(context, "Bo'limlarni to'liq to'ldiring", Toast.LENGTH_SHORT).show()
                }
                onResume()
                dialog.cancel()
            }

            customMentorBinding.yopish.setOnClickListener {
                dialog.cancel()
            }
            dialog.setView(customMentorBinding.root)
            dialog.show()

        }
        val myLayoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)

        binding.rv.apply {
            layoutManager = myLayoutManager
            addItemDecoration(DividerItemDecoration(requireContext(), myLayoutManager.orientation))
        }
        binding.chiqish.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        myDb = MyDb(requireContext())
        tartibla()
        mentorlarAdapter = MentorlarAdapter(this@RoyxatFragment,list)
        binding.rv.adapter = mentorlarAdapter
    }

    fun tartibla(){
        val mentorId = arguments?.getSerializable("p") as Kurslar
        this.list = ArrayList()
        for (mentorlar in myDb.showMentor()) {
            if (mentorlar.kurs_id?.id ==mentorId.id){
                list.add(mentorlar)
                println(list)
            }
        }
    }

    override fun editClick(mentorlar: Mentorlar) {
        myDb = MyDb(requireContext())
        val mentorId = arguments?.getSerializable("p") as Kurslar
        val dialog = AlertDialog.Builder(requireContext())
            .create()
        val customMentorBinding = CustomMentorBinding.inflate(layoutInflater)
        customMentorBinding.name.setText(mentorlar.name)
        customMentorBinding.lastName.setText(mentorlar.lastName)
        customMentorBinding.number.setText(mentorlar.number)
        customMentorBinding.qoshish.text = "O'zgartirish"
        customMentorBinding.qoshish.setOnClickListener {
            if (customMentorBinding.name.text.isNotEmpty() && customMentorBinding.lastName.text.isNotEmpty() && customMentorBinding.number.text.isNotEmpty()) {
                val mentor = Mentorlar(
                    mentorlar.id,
                    customMentorBinding.name.text.toString(),
                    customMentorBinding.lastName.text.toString(),
                    customMentorBinding.number.text.toString(),
                    mentorId
                )
                myDb.editMentor(mentor)
            }else{
                Toast.makeText(context, "Bo'limlarni to'liq to'ldiring", Toast.LENGTH_SHORT).show()
            }
            onResume()
            dialog.cancel()
        }
        customMentorBinding.yopish.setOnClickListener {
            dialog.cancel()
        }
        dialog.setView(customMentorBinding.root)
        dialog.show()
    }

    override fun deleteClick(mentorlar: Mentorlar) {
        var a = true
        myDb = MyDb(requireContext())
        for (grupalar in myDb.showGroup()) {
            if (grupalar.mentor_id?.id==mentorlar.id){
                a=false
                Toast.makeText(context, "OLdin bu mentor guruhlarini yakunlang", Toast.LENGTH_SHORT).show()
            }
        }
        if (a) {
            myDb.deleteMentor(mentorlar)
            onResume()
        }
    }

}