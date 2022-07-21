package com.example.challengechapter5.UI.SignInUp

import android.app.ProgressDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.challengechapter5.Data
import com.example.challengechapter5.Database.DatabaseUser
import com.example.challengechapter5.R
import com.example.challengechapter5.UI.Movie.MainActivityViewModel
import com.example.challengechapter5.UI.Movie.MovieActivityViewModel
import com.example.challengechapter5.databinding.ActivityMainBinding
import com.example.challengechapter5.databinding.ActivityProfileBinding
import com.google.android.material.snackbar.Snackbar

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    private val progressDialog: ProgressDialog by lazy { ProgressDialog(this) }
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        bindViewModel()

        val db = DatabaseUser.getInstance(this)
        val pref = this.getSharedPreferences(
            Data.Preferences.PREF_NAME,
            AppCompatActivity.MODE_PRIVATE
        )
        viewModel.onViewLoaded(db = db, preferences = pref)
    }

    private fun bindView() {
        binding.tvLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun bindViewModel() {
        viewModel.shouldShowError.observe(this) {
            val snackbar = Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
            snackbar.view.setBackgroundColor(Color.RED)
            snackbar.show()
        }

        viewModel.shouldShowLoading.observe(this) {
            if (it) {
                progressDialog.setMessage("Loading...")
                progressDialog.show()
            } else {
                progressDialog.hide()
            }
        }

        viewModel.shouldShowImageProfile.observe(this) {
            Glide.with(binding.root)
                .load(it)
                .circleCrop()
                .into(binding.rivProfileUser)
        }

        viewModel.shouldShowName.observe(this) { shouldShowName ->
            binding.etFullNameProfile.setText(shouldShowName)
        }

        viewModel.shouldShowJob.observe(this) { shouldShowJob ->
            binding.etJobProfile.setText(shouldShowJob)
        }

        viewModel.shouldShowEmail.observe(this) { shouldShowEmail ->
            binding.etEmailProfile.setText(shouldShowEmail)
        }
    }
}