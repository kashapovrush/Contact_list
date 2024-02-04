package com.kashapovrush.contactlist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.kashapovrush.contactlist.UserList.resultList
import com.kashapovrush.contactlist.databinding.ActivityUserBinding

class UserActivity() : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private var screenMode: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        screenMode = intent.getStringExtra(EXTRA_SCREEN_MOD)

        when (screenMode) {
            MODE_ADD -> {
                addUserMode()
            }

            MODE_EDIT -> {
                editUserMode(intent.getIntExtra(USER_ID, 0))
            }
        }
    }

    private fun editUserMode(id: Int) {
        resultList.forEach { user ->
            if (resultList.indexOf(user) == id) {
                binding.etFirstName.setText(user.firstName)
                binding.etLastName.setText(user.lastName)
                binding.etPhoneNumber.setText(user.phoneNumber)
            }
        }

        binding.buttonSave.setOnClickListener {
            val user = resultList[id]
            resultList.remove(user)

            resultList.add(
                User(
                    id = user.id,
                    firstName = binding.etFirstName.text.toString(),
                    lastName = binding.etLastName.text.toString(),
                    phoneNumber = binding.etPhoneNumber.text.toString()
                )
            )
            startActivity(MainActivity.newIntent(this))
        }


    }

    private fun addUserMode() {
        var lastIndex = resultList[resultList.size - 1]
        var lastUserId = lastIndex.id
        binding.buttonSave.setOnClickListener {
            resultList.add(
                User(
                    lastUserId?.plus(1),
                    binding.etFirstName.text.toString(),
                    binding.etLastName.text.toString(),
                    binding.etPhoneNumber.text.toString()
                )
            )
            startActivity(MainActivity.newIntent(this))
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MOD = "extra_mod"
        private const val MODE_EDIT = "mod_edit"
        private const val MODE_ADD = "mod_add"
        private const val USER_ID = "user_id"

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MOD, MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, id: Int): Intent {
            val intent = Intent(context, UserActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MOD, MODE_EDIT)
            intent.putExtra(USER_ID, id)
            return intent
        }
    }
}