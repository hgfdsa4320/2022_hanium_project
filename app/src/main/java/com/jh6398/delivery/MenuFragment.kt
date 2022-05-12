package com.jh6398.delivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.jh6398.delivery.menu.SubActivity

class menuFragment : Fragment() {
    lateinit var delivery : RelativeLayout



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.menu,container,false)
        delivery = view.findViewById(R.id.delivery)
        val token = arguments?.getString("token")
        Log.d("aabb","token : $token")

        delivery.setOnClickListener(){
            var intent : Intent = Intent(context, SubActivity::class.java)
                intent.putExtra("token",token)
                startActivity(intent)
        }







        return view
    }







}