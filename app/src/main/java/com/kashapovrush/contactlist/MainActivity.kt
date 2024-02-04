package com.kashapovrush.contactlist

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.kashapovrush.contactlist.UserList.deleteList
import com.kashapovrush.contactlist.UserList.resultList
import com.kashapovrush.contactlist.UserList.setUsers
import com.kashapovrush.contactlist.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: Adapter
    private var stateDeleting = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRV(false)

        binding.buttonAddUser.setOnClickListener {
            startActivity(UserActivity.newIntentAdd(this))
        }

        binding.garbageBtn.setOnClickListener {
            if (stateDeleting) {
                binding.deleteMenu.visibility = View.INVISIBLE
                binding.buttonAddUser.visibility = View.VISIBLE
                setupRV(false)
            } else {
                binding.deleteMenu.visibility = View.VISIBLE
                binding.buttonAddUser.visibility = View.INVISIBLE
                setupRV(true)
                adapter.onClickListener = null
            }
            stateDeleting = !stateDeleting
        }

        binding.cancelBtn.setOnClickListener {
            binding.deleteMenu.visibility = View.INVISIBLE
            binding.buttonAddUser.visibility = View.VISIBLE
            setupRV(false)
            deleteList.clear()
        }

        binding.deleteUser.setOnClickListener {
            deleteList.forEach {
                resultList.remove(it)
            }

            binding.deleteMenu.visibility = View.INVISIBLE
            binding.buttonAddUser.visibility = View.VISIBLE
            setupRV(false)
            deleteList.clear()
        }
    }

    private fun setupRV(state: Boolean) {
        val rvList = binding.rvUsers
        adapter = Adapter {
            state
        }
        rvList.adapter = adapter
        setUsers()
        resultList.sortBy {
            it.id
        }

        adapter.onClickListener = {
            startActivity(UserActivity.newIntentEdit(this, resultList.indexOf(it)))
        }

        adapter.submitList(resultList)
    }

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }
}