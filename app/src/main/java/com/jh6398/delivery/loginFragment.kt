package com.jh6398.delivery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.security.MessageDigest
import kotlin.experimental.and

class loginFragment : Fragment() {

    lateinit var idPassTv : TextView
    lateinit var loginTv : TextView
    lateinit var loginBt : Button
    lateinit var idEt : EditText
    lateinit var passEt : EditText
    var token =""

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login, container, false)
        idPassTv = view.findViewById(R.id.idPassTv)
        loginTv = view.findViewById(R.id.loginTv)
        id
        loginBt = view.findViewById(R.id.loginBt)
        idEt = view.findViewById(R.id.idEt)
        passEt = view.findViewById(R.id.passEt)

        loginTv.setOnClickListener(){
            (activity as MainActivity?)!!.setFragment(MainActivity.JOIN)
        }

        loginBt.setOnClickListener(){
            var id = idEt.text.toString()
            var pass = passEt.text.toString()
            login(id,pass)
//            (activity as MainActivity).setFragment(MainActivity.JOIN)
        }

        idPassTv.setOnClickListener(){
            (activity as MainActivity).setFragment(MainActivity.IDPASS)
        }

        return view
    }


    fun login(userid: String,pass : String){
        var url = "https://wlghks1767.cafe24.com/myserver/phpfile/login.php"
        val requestQueue = Volley.newRequestQueue(context)

        val request: StringRequest = object : StringRequest(
            Request.Method.POST, url,request,fail ) {

            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params.put("userid",userid)
                var encryptpass = setHash(pass)
                params.put("pass",encryptpass)



                return params
            }
        }

        requestQueue.add(request)
    }

    var request = object  : Response.Listener<String> {
        override fun onResponse(response: String) {
            var jsonObject =  JSONObject(response)
            var login = jsonObject.getString("result")
            if (login == "OK"){
                token = jsonObject.getString("token")
//                (activity as MainActivity?)!!.setFragment(MainActivity.MENU)
//                var intent : Intent = Intent(context,SubActivity::class.java)
//                intent.putExtra("token",token)
//                startActivity(intent)

                var menuFragment = menuFragment()
                var bundle = Bundle()
                bundle.putString("token",token)
                menuFragment.arguments = bundle //fragment의 arguments에 데이터를 담은 bundle을 넘겨줌


                (activity as MainActivity?)!!.setFragment2(menuFragment)



            }

            else if(login=="NK")
                Toast.makeText(context,"아이디나 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()
//            Log.d("aabb","msg: "+token)


        }
    }


    var fail = object  : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError?) {
            Log.d("aabb","서버 연결 실패 : $error")
        }
    }




    fun setHash(pass : String): String{
        val encoder = MessageDigest.getInstance("SHA-256")
        val byteArray = encoder.digest(pass.toByteArray())
        val encryptedPass = StringBuffer()
        for(byte in byteArray){
            val hashByte = (byte.and(0xff.toByte())+0x100).toString(16)
            if(hashByte.length>2)
                encryptedPass.append(hashByte.substring(1))
            else
                encryptedPass.append(hashByte)
        }
        return encryptedPass.toString()
    }




}