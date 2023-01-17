package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class MainView : AppCompatActivity(){
    private val db = Firebase.firestore
    private lateinit var recyclerAdapter: LocationRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycledviewver)
        val recyclerView = findViewById<RecyclerView>(R.id.viewer)

        db.collection("places")
            .get()
            .addOnSuccessListener { result ->
                val locationList: ArrayList<MyLocation> = ArrayList()
                for (data in result.documents) {
                    val onelocation = data.toObject(MyLocation::class.java)
                    if (onelocation != null) {
                        onelocation.id = data.id
                        locationList.add(onelocation)
                    }
                }
                val sortedList: ArrayList<MyLocation> =
                    ArrayList(locationList.sortedWith(compareBy {
                        it.name
                    }))
                recyclerAdapter = LocationRecyclerAdapter(sortedList as ArrayList<MyLocation>)


                val btn = findViewById<Button>(R.id.search)
                btn.setOnClickListener() {
                    val sortedList2: ArrayList<MyLocation> = ArrayList()
                    val unos = findViewById<EditText>(R.id.TypeLocationName)
                    val unos2=unos.text.toString().replaceFirstChar { it.lowercase() }
                    val unos3=unos.text.toString().replaceFirstChar { it.uppercase() }

                    if (unos.text.toString() != "") {
                        unos
                        for (data in locationList) {
                            if (data.name.contains(unos3) || data.name.contains(unos2) )
                                sortedList2.add(data)
                        }
                        if(sortedList2.isEmpty()){

                            Toast.makeText(this,"No targeted location found",Toast.LENGTH_LONG).show()
                        }
                        else{
                        recyclerAdapter = LocationRecyclerAdapter(sortedList2 as ArrayList<MyLocation>)
                        recyclerView.apply {
                            layoutManager = LinearLayoutManager(this@MainView)
                            adapter = recyclerAdapter
                        }
                        }

                    }
                    else{
                        val sortedList: ArrayList<MyLocation> =
                            ArrayList(locationList.sortedWith(compareBy {
                                it.name
                            }))

                        recyclerAdapter = LocationRecyclerAdapter(sortedList as ArrayList<MyLocation>)

                        recyclerView.apply {
                            layoutManager = LinearLayoutManager(this@MainView)
                            adapter = recyclerAdapter
                        }
                    }

                }

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainView)
                    adapter = recyclerAdapter
                }

            }


            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.",
                    exception)
            }






    }



}

