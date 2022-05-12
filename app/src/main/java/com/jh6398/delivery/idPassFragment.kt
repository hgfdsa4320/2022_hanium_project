package com.jh6398.delivery.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jh6398.delivery.R
import com.jh6398.delivery.databinding.IdpassBinding

import org.json.JSONObject
import java.security.MessageDigest
import kotlin.experimental.and

class idPassFragment : Fragment() {

    lateinit var _binding : IdpassBinding
    val binding get() = _binding
    lateinit var loginBt : Button
    lateinit var idEt : EditText
    lateinit var passEt : EditText
    lateinit var idChk : CheckBox
    lateinit var nameEt : EditText


    var isCheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = IdpassBinding.inflate(inflater,container,false)
        val view = binding.root

        loginBt = view.findViewById(R.id.loginBt)
        idEt = view.findViewById(R.id.idEt)
        passEt = view.findViewById(R.id.passEt)
        idChk = view.findViewById(R.id.idChk)


        idChk.setOnClickListener(){
            var id = idEt.text.toString()
            idChk(id)
        }

        loginBt.setOnClickListener(){
            if(isCheck){
                makeId()
            }
            else{
                Toast.makeText(context, "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show()
            }


        }


        return view
    }


    fun idChk(userid : String){
        var url = "https://wlghks1767.cafe24.com/myserver/phpfile/idChk.php"
        val requestQueue = Volley.newRequestQueue(context)

        val request: StringRequest = object : StringRequest(
            Request.Method.POST, url,checkId,fail ) {
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()


                params.put("userid",userid)
                return params
            }
        }

        requestQueue.add(request)
    }


    fun makeId(){
        var url = "https://wlghks1767.cafe24.com/myserver/phpfile/makeId.php"
        val requestQueue = Volley.newRequestQueue(context)

        val request: StringRequest = object : StringRequest(
            Request.Method.POST, url,makeId,fail ) {
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                var id = idEt.text.toString()
                var pass = passEt.text.toString()
                var name = nameEt.text.toString()

                params.put("name",name)
                params.put("userid",id)
                var encryptPass = setHash(pass)
                params.put("pass",encryptPass)

                return params
            }
        }

        requestQueue.add(request)
    }

    var checkId = object  : Response.Listener<String> {
        override fun onResponse(response: String) {
            var jsonObject =  JSONObject(response)
            var result = jsonObject.getString("result")
            if(result=="OK") {
                isCheck = true
                Toast.makeText(context, "사용가능한 아이디 입니다.", Toast.LENGTH_SHORT).show()
            }
            else if (result=="NK")
                Toast.makeText(context,"중복된 아이디 입니다.",Toast.LENGTH_SHORT).show()





        }
    }

    var makeId = object  : Response.Listener<String> {
        override fun onResponse(response: String) {
            var jsonObject =  JSONObject(response)
            var result = jsonObject.getString("result")
            if(result=="OK") {
                isCheck = true
                Toast.makeText(context, "회원가입 성공", Toast.LENGTH_SHORT).show()
            }
            else if (result=="NK")
                Toast.makeText(context,"회원가입 실패",Toast.LENGTH_SHORT).show()



        }
    }



    var fail = object  : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError?) {
            Log.d("aabb","서버 연결 실패")
            Log.d("aabb","msg : $error")
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