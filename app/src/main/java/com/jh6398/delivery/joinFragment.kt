package com.jh6398.delivery

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
import org.json.JSONObject
import java.security.MessageDigest
import kotlin.experimental.and

class joinFragment : Fragment() {

    lateinit var joinBt : Button
    lateinit var idEt : EditText
    lateinit var passEt : EditText
    lateinit var idChk : CheckBox
    lateinit var nameEt : EditText
    lateinit var passChkEt : EditText
    lateinit var addressEt: EditText


    var isCheck = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.join,container,false)

        joinBt = view.findViewById(R.id.joinBt)
        idEt = view.findViewById(R.id.idEt)
        passEt = view.findViewById(R.id.passEt)
        idChk = view.findViewById(R.id.idChk)
        nameEt = view.findViewById(R.id.nameEt)
        addressEt = view.findViewById(R.id.addressEt)
        passChkEt = view.findViewById(R.id.passChkEt)

        idChk.setOnClickListener(){
            idChkJson()
        }

        joinBt.setOnClickListener(){
            if(isCheck){
                if(passEt.text.toString().equals(passChkEt.text.toString()))
                    makeId()
                else
                    Toast.makeText(context, "비밀번호를 확인 해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show()
            }


        }


        return view
    }


    fun idChkJson(){
        var url = "https://wlghks1767.cafe24.com/myserver/phpfile/idChk.php"
        val requestQueue = Volley.newRequestQueue(context)

        val request: StringRequest = object : StringRequest(
            Request.Method.POST, url,checkId,fail ) {
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                var id = idEt.text.toString()

                params.put("userid",id)
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
                var address = addressEt.text.toString()

                params.put("name",name)
                params.put("userid",id)
                var encryptPass = setHash(pass)
                params.put("pass",encryptPass)
                params.put("address",address)

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

            Log.d("aabb","msg: $response")



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

            Log.d("aabb","msg: "+response)

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