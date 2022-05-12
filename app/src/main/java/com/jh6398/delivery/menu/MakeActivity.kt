package com.jh6398.delivery.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.jh6398.delivery.databinding.ActivityMakeBinding
import org.json.JSONObject

class MakeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivityMakeBinding
    var token = ""
    var categoryNum = -1
    var categoryStr: ArrayList<String> = ArrayList()
    var categoryData : ArrayList<Category> = ArrayList()

    //  다날 사업자등록 // 페이히어
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra("token").toString()


        getCategory()

        binding.spinner.onItemSelectedListener = this


        binding.makeBt.setOnClickListener() {
            var title = binding.titleEt.text.toString()
            var content = binding.contentEt.text.toString()
            var charge = binding.chargeEt.text.toString()
            var deadline = binding.deadlineEt.text.toString()
            var number = binding.numberEt.text.toString()

            makeRoom(title, content, charge, deadline, number, token)

        }


    }


    fun getCategory() {
        var url = "https://wlghks1767.cafe24.com/myserver/phpfile/category.php"
        val requestQueue = Volley.newRequestQueue(this)

        val request: StringRequest = object : StringRequest(
            Request.Method.POST, url, request, fail
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()


                return params
            }
        }

        requestQueue.add(request)
    }

    var request = object : Response.Listener<String> {
        override fun onResponse(response: String) {
            var jsonObject = JSONObject(response)
            var result = jsonObject.getString("result")

            if (result == "OK") {
                var jsonArray = jsonObject.getJSONArray("category_list")
                for (i in 0 until jsonArray.length()) {
                    var temp = jsonArray.getJSONObject(i)
                    var idx = temp.getInt("idx")
                    var category = temp.getString("category")


                    var tempCategory = Category()
                    tempCategory.idx = idx
                    tempCategory.category = category

                    categoryData.add(tempCategory)
                    categoryStr.add(category)


                }


                var adapter = ArrayAdapter<String>(
                    this@MakeActivity, android.R.layout.simple_list_item_1,
                    categoryStr
                )
                binding.spinner.adapter = adapter


            } else if (result == "NK")
                Toast.makeText(this@MakeActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()

            Log.d("aabb", "msg: " + response)

        }
    }


    fun makeRoom(title: String, content: String, charge: String, deadline: String, number: String,token: String ) {
        var url = "https://wlghks1767.cafe24.com/myserver/phpfile/makeRoom.php"
        val requestQueue = Volley.newRequestQueue(this)

        val request: StringRequest = object : StringRequest(
            Request.Method.POST, url, makeRoom, fail
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()


                params.put("category", categoryNum.toString())
                params.put("title", title)
                params.put("content", content)
                params.put("charge", charge)
                params.put("deadline", deadline)
                params.put("number", number)
                params.put("token", token)


                return params
            }
        }

        requestQueue.add(request)
    }

    var makeRoom = object : Response.Listener<String> {
        override fun onResponse(response: String) {
            var jsonObject = JSONObject(response)
            var result = jsonObject.getString("result")
            if (result == "OK") {
                Toast.makeText(this@MakeActivity, "글 작성 성공", Toast.LENGTH_SHORT).show()
            } else if (result == "NK")
                Toast.makeText(this@MakeActivity, "글 작성 실패", Toast.LENGTH_SHORT).show()

            Log.d("aabb", "msg: " + response)

        }
    }


    var fail = object : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError?) {
            Log.d("aabb", "서버 연결 실패")
            Log.d("aabb", "msg : $error")
        }
    }


    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        categoryNum = categoryData.get(p2).idx


    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        categoryNum = categoryData.get(0).idx
    }



}