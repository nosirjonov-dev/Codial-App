package com.example.codialapp.fragments.Guruhlar.groups

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.codialapp.adapters.MyData
import com.example.codialapp.databinding.FragmentEditGroupBinding
import com.example.codialapp.db.MyDb
import com.example.codialapp.models.Grupalar
import com.example.codialapp.models.Mentorlar


class EditGroupFragment : Fragment() {
    lateinit var mentorList:ArrayList<String>
    lateinit var myDb: MyDb
    lateinit var abs: Mentorlar
    private val binding by lazy { FragmentEditGroupBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var isOpened = 0
        val id1 = arguments?.getInt("edit")
        myDb = MyDb(requireContext())
        for (grupalar in myDb.showGroup()) {
            if (grupalar.id == id1) {
                binding.nameGroup.setText(grupalar.name)
                isOpened = grupalar.ochilganmi
            }
        }
        vaqti()
        kunlari()
        mentorlar()
        binding.mentor.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,mentorList)
        binding.vaqti.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,vaqti())
        binding.kunlari.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_list_item_1,kunlari())

        var vaqt = ""
        binding.vaqti.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                vaqt = vaqti()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        var kun = ""
        binding.kunlari.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                kun = kunlari()[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
        binding.mentor.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                for (mentorlar in myDb.showMentor()) {
                    if ("${mentorlar.name} ${mentorlar.lastName}"==mentorList[position]){
                        abs = mentorlar
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })
        binding.btnSave.setOnClickListener {
            val grupalar = Grupalar(
                id1,
                binding.nameGroup.text.toString(),
                abs,
                kun,
                vaqt,
                isOpened
            )
            println(grupalar)
            println("12345")
            myDb.editGroup(grupalar)
            Toast.makeText(requireContext(), "Saqlandi", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.chiqish.setOnClickListener {  requireActivity().supportFragmentManager.popBackStack() }

        return binding.root
    }

    fun vaqti():ArrayList<String>{
        val list = ArrayList<String>()
        list.add("8:00 dan 10:00 gacha")
        list.add("10:00 dan 12:00 gacha")
        list.add("14:00 dan 16:00 gacha")
        list.add("16:00 dan 20:00 gacha")
        return list
    }

    fun kunlari():ArrayList<String>{
        val list = ArrayList<String>()
        println("ali")
        list.add("Dushanba, Chorshanba, Juma")
        list.add("Seshanba, Payshanba, Shanba")
        return list
    }
    fun mentorlar(){
        myDb = MyDb(requireContext())
        val p = MyData.kurslar
        mentorList = ArrayList()
        for (mentorlar in myDb.showMentor()) {
            println(mentorlar)
            if (mentorlar.kurs_id?.id==p?.id){
                println(mentorlar)
                mentorList.add("${mentorlar.name} ${mentorlar.lastName}")
            }
        }
    }


}