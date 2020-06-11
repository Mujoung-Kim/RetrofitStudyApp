package company.domain.retrofitstudyapp.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

import company.domain.retrofitstudyapp.R
import company.domain.retrofitstudyapp.fragment.HomeFragment
import company.domain.retrofitstudyapp.fragment.SettingsFragment
import company.domain.retrofitstudyapp.fragment.UsersFragment
import company.domain.retrofitstudyapp.models.User
import company.domain.retrofitstudyapp.storage.SharedPrefManager

import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

//        val user: User = SharedPrefManager.getInstance(this)!!.user

//        textView.text = "Welcome back ${user.name}"
        bottom_nav.setOnNavigationItemSelectedListener(this)
        displayFragment(HomeFragment())

    }

    private fun displayFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.relativeLayout, fragment)
            .commit()

    }

    override fun onStart() {
        super.onStart()

        if (!SharedPrefManager.getInstance(this)?.isLoggedIn!!) {
            val intent = Intent(this, MainActivity::class.java)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.menu_home -> fragment = HomeFragment()
            R.id.menu_users -> fragment = UsersFragment()
            R.id.menu_settings -> fragment = SettingsFragment()

        }

        if (fragment != null) displayFragment(fragment)

        return false

    }
}
