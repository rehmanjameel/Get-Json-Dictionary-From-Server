package org.deskconn.jsonapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import pk.codebase.requests.HttpError
import pk.codebase.requests.HttpRequest
import pk.codebase.requests.HttpResponse


class MainActivity : AppCompatActivity() {
    private val URL_BASE = "http://45.61.49.214:8000/api/data/"
    private val storedValue = "47e3b3064569c3c2d6eb7915c314e083"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getButton = findViewById<Button>(R.id.getResponseButton)
        val birthTextView = findViewById<TextView>(R.id.birthTextView)
        val createdDate = findViewById<TextView>(R.id.dateTextView)
        val fNameTextView = findViewById<TextView>(R.id.fNameTextView)
        val lNameTextView = findViewById<TextView>(R.id.lNameTextView)
        val nfcDataKeyTV = findViewById<TextView>(R.id.nfcDataKey)
        val nfcCardNumberTV = findViewById<TextView>(R.id.nfcCardNumber)
        val nfcCreatedDateTv = findViewById<TextView>(R.id.nfcCreatedDate)
        val nfcPrimaryKeyTV = findViewById<TextView>(R.id.nfcPrimaryKey)
        val shcDataKey = findViewById<TextView>(R.id.shcDataKey)
        val nfcStoredValueTV = findViewById<TextView>(R.id.nfcStoredValue)
        val primaryKey = findViewById<TextView>(R.id.primaryKeyTextView)

        val request = HttpRequest()
        getButton.setOnClickListener setOnResponseListener@{
            try {
                request.setOnResponseListener { response ->
                    if (response.code == HttpResponse.HTTP_OK) {
                        val jsonResponse: JSONObject = response.toJSONObject()
                        println("here ${jsonResponse.get("birth_year")}")
                        birthTextView.text = jsonResponse.get("birth_year").toString()
                        createdDate.text = jsonResponse.getString("created_date")
                        fNameTextView.text = jsonResponse.getString("first_name")
                        lNameTextView.text = jsonResponse.getString("last_name")
                        nfcDataKeyTV.text = jsonResponse.getString("nfc_datakey")
                        val jsonObject2 : JSONObject = jsonResponse.getJSONObject("nfc_datakey")

                        println("here $jsonObject2")
                        nfcCardNumberTV.text = jsonObject2.getString("card_number")
                        nfcCreatedDateTv.text = jsonObject2.getString("created_date")
                        nfcPrimaryKeyTV.text = jsonObject2.getString("primary_key")
                        shcDataKey.text = jsonObject2.getString("shc_datakey")
                        nfcStoredValueTV.text = jsonObject2.getString("stored_value")
                        primaryKey.text = jsonResponse.getString("primary_key")

                        Toast.makeText(this, response.text, Toast.LENGTH_LONG).show()
                        println("Response code is: "+response.code)
                    } else if (response.code != HttpResponse.HTTP_OK) {
                        println("Error code is: "+response.code)
                        //Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e: JSONException){
                println(e.message)
            }

            request.setOnErrorListener {
                // There was an error, deal with it
                println("Error is here")
            }

            request.get(URL_BASE)

            val json: JSONObject
            try {
                json = JSONObject()
                json.put("stored_value", storedValue)
            } catch (ignore: JSONException) {
                return@setOnResponseListener
            }
            request.post(URL_BASE, json)
        }
    }
}


