package com.example.bagimodal.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bagimodal.R
import com.example.bagimodal.databinding.FragmentProfileBinding
import com.example.bagimodal.ui.SharePreference
import com.example.bagimodal.ui.main.profile.project.CreateProjectActivity
import com.example.bagimodal.ui.main.profile.project.FundedProjectActivity
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var sharePreference: SharePreference
    private lateinit var fragmentProfileBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileBinding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return fragmentProfileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseFirestore.getInstance()

        sharePreference = SharePreference(requireContext())
        fragmentProfileBinding.btnToCreateProject.setOnClickListener(this)
        fragmentProfileBinding.btnToFundedProject.setOnClickListener(this)

        fragmentProfileBinding.tvEmail.text = sharePreference.getValueString("email")

        db.collection("moneyUser")
            .document(sharePreference.getValueString("email")!!)
            .get()
            .addOnSuccessListener { document ->
                fragmentProfileBinding.tvMoney.text =
                    StringBuilder("Rp. " + document.get("money").toString())
            }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_to_create_project -> {
                startActivity(Intent(requireContext(), CreateProjectActivity::class.java))
            }
            R.id.btn_to_funded_project -> {
                startActivity(Intent(requireContext(), FundedProjectActivity::class.java))
            }
        }
    }
}