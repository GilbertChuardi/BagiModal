package com.example.bagimodal.ui.main.home.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.R
import com.example.bagimodal.databinding.ActivityDetailBinding
import com.example.bagimodal.ui.SharePreference
import com.example.bagimodal.ui.main.home.DataModel

class DetailActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var sharePreference: SharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)
        sharePreference = SharePreference(this)

        title = "Detail"

        val dataUser = intent.getParcelableExtra(EXTRA_DATA) as DataModel?

        activityDetailBinding.tvEmailDetail.text = dataUser?.email
        activityDetailBinding.tvJudulDetail.text = dataUser?.judul
        activityDetailBinding.tvDescriptionDetail.text = dataUser?.description
        activityDetailBinding.btnDonate.setOnClickListener(this)
        activityDetailBinding.btnSubmitDonate.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_donate -> showDonate()
            R.id.btn_submit_donate -> doDonate()
        }
    }

    private fun showDonate(){
        activityDetailBinding.edDonate.visibility = View.VISIBLE
        activityDetailBinding.btnSubmitDonate.visibility = View.VISIBLE
    }

    private fun doDonate(){
        val moneyrn = sharePreference.getValueInt("money")
        val donatehm = activityDetailBinding.edDonate.text.toString().toInt()
        val left = moneyrn - donatehm

        if(left >= 0){
            sharePreference.save("money",left)
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Not enough money",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}