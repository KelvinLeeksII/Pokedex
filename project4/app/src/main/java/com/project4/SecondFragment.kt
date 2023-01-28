package com.project4

import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import androidx.lifecycle.Observer
import com.project4.databinding.SecondFragmentBinding



import com.project4.BR.myModel2
class SecondFragment : Fragment() {

    interface OnFragmentInteractionListener{
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        fun newInstance() = SecondFragment()
    }

    private lateinit var viewModel: SecondViewModel
    private lateinit var binding : SecondFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.second_fragment, container, false)
        binding.lifecycleOwner = this

        //sets button click
       binding.searchButton.setOnClickListener{
           //grabs user search from editText
           var searchText = binding.searchText.text.toString()
           searchText = searchText.trim()
           searchText = searchText.lowercase()

           //grabs and displays pokemon data
           binding.myModel2!!.getPokemonData(searchText)
       }
        //clears search bar when clicked
        binding.searchText.setOnFocusChangeListener { view, b ->binding.searchText.setText("")  }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[SecondViewModel::class.java]
        binding.setVariable(myModel2,viewModel)

        // TODO: Use the ViewModel
        //sets observer
        val frontObserver = Observer<String>{ newImage->
            Glide.with(this)
                .load(newImage)
                .into(binding.searchImage2)
        }

        val shinyObserver = Observer<String>{ newImage->
            Glide.with(this)
                .load(newImage)
                .into(binding.searchImage)
        }
        binding.myModel2!!.frontSprite.observe(viewLifecycleOwner,frontObserver)
        binding.myModel2!!.shinySprite.observe(viewLifecycleOwner,shinyObserver)
    }

}

