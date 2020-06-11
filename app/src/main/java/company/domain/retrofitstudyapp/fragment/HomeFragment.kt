package company.domain.retrofitstudyapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import company.domain.retrofitstudyapp.R
import company.domain.retrofitstudyapp.storage.SharedPrefManager

import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewEmail.text = SharedPrefManager.getInstance(context)?.user?.email
        textViewName.text = SharedPrefManager.getInstance(context)?.user?.name
        textViewSchool.text = SharedPrefManager.getInstance(context)?.user?.school

    }
}