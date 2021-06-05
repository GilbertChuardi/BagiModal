package com.example.bagimodal.ui.main.home.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bagimodal.R
import com.example.bagimodal.databinding.ActivityDetailBinding
import com.example.bagimodal.ui.SharePreference
import com.example.bagimodal.ui.main.home.DataModel
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var sharePreference: SharePreference
    private lateinit var db: FirebaseFirestore
    private lateinit var dataUser: DataModel
    private var moneyleft: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)
        sharePreference = SharePreference(this)

        title = "Detail"
        db = FirebaseFirestore.getInstance()

        dataUser = intent.getParcelableExtra(EXTRA_DATA)!!

        db.collection("moneyUser")
            .document(sharePreference.getValueString("email")!!)
            .get()
            .addOnSuccessListener { document ->
                moneyleft = document.get("money").toString().toInt()
            }

        activityDetailBinding.tvEmailDetail.text = dataUser.email
        activityDetailBinding.tvJudulDetail.text = dataUser.judul
        activityDetailBinding.tvDescriptionDetail.text = dataUser.description
        activityDetailBinding.tvTotaldonation.text = dataUser.totaldonation.toString()
        activityDetailBinding.btnDonate.setOnClickListener(this)
        activityDetailBinding.btnSubmitDonate.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_donate -> showDonate()
            R.id.btn_submit_donate -> doDonate()
        }
    }

    private fun showDonate() {
        activityDetailBinding.edDonate.visibility = View.VISIBLE
        activityDetailBinding.btnSubmitDonate.visibility = View.VISIBLE
    }

    private fun hideDonate() {
        activityDetailBinding.edDonate.visibility = View.INVISIBLE
        activityDetailBinding.btnSubmitDonate.visibility = View.INVISIBLE
    }

    private fun doDonate() {


        val donatehm = activityDetailBinding.edDonate.text.toString().toInt()

        moneyleft -= donatehm

        if (moneyleft >= 0) {
            dataUser.totaldonation += donatehm

            db.collection("projectUser")
                .document(dataUser.judul)
                .update(
                    mapOf(
                        "totaldonation" to dataUser.totaldonation
                    )
                )

            db.collection("moneyUser")
                .document(sharePreference.getValueString("email")!!)
                .update(
                    mapOf(
                        "money" to moneyleft
                    )
                )

            hideDonate()

            Toast.makeText(this, "Donation Success", Toast.LENGTH_SHORT).show()
        } else {
            hideDonate()
            Toast.makeText(this, "Not enough money", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}