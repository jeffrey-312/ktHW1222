package com.example.calculate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    lateinit var ed_height: EditText
    lateinit var ed_weight: EditText
    lateinit var ed_age: EditText
    lateinit var btn_boy: RadioButton
    lateinit var btn_girl: RadioButton
    lateinit var tv_weight: TextView
    lateinit var tv_bmi: TextView
    lateinit var tv_fat: TextView
    lateinit var tv_progress: TextView
    lateinit var ll_progress: LinearLayout
    lateinit var progressBar2: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ed_height = findViewById(R.id.ed_height)
        ed_weight = findViewById(R.id.ed_weight)
        ed_age = findViewById(R.id.ed_age)
        btn_boy = findViewById(R.id.btn_boy)
        btn_girl = findViewById(R.id.btn_girl)
        tv_weight = findViewById(R.id.tv_weight)
        tv_fat = findViewById(R.id.tv_fat)
        tv_bmi = findViewById(R.id.tv_bmi)
        tv_progress = findViewById(R.id.tv_progress)
        ll_progress = findViewById(R.id.ll_progress)
        progressBar2 = findViewById(R.id.progressBar2)

        findViewById<View>(R.id.btn_calculate).setOnClickListener {
            when
            {
                ed_height.length()<1 -> Toast.makeText(this@MainActivity, "請輸入身高", Toast.LENGTH_SHORT).show()
                ed_weight.length()<1 -> Toast.makeText(this@MainActivity, "請輸入體重", Toast.LENGTH_SHORT).show()
                ed_age.length()<1 -> Toast.makeText(this@MainActivity, "請輸入年齡", Toast.LENGTH_SHORT).show()
                else -> runCoroutines()
            }
        }
    }

    private fun runCoroutines() {

        tv_weight.text = "標準體重\n無"
        tv_fat.text = "體脂肪\n無"
        tv_bmi.text = "BMI\n無"
        progressBar2.progress = 0
        tv_progress.text = "0%"
        ll_progress.visibility = View.VISIBLE
        GlobalScope.launch(Dispatchers.Main) {
            var progress = 0
            while (progress < 100) {
                delay(50)
                progressBar2.progress = progress
                tv_progress.text = "$progress%"
                progress++
            }
            ll_progress.visibility=View.GONE;

            val height = ed_height.text.toString().toDouble()
            val weight = ed_weight.text.toString().toDouble()
            val age = ed_age.text.toString().toDouble()
            val bmi = weight/((height/100).pow(2))

            val (stand_weight,body_fat)=if(btn_boy.isChecked){
                Pair((height-80)*0.7,1.39*bmi+0.16*age-19.34)
            }else{
                Pair((height-70)*0.6,1.39*bmi+0.16*age-9)
            }
            tv_weight.text="標準體重 \n${String.format("%.2f",stand_weight)}"
            tv_fat.text="體脂肪 \n${String.format("%.2f",body_fat)}"
            tv_bmi.text="BMI \n${String.format("%.2f",bmi)}"
        }
    }
}
