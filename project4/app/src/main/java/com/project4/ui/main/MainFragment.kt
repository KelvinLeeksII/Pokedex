package com.project4.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.project4.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.project4.databinding.MainFragmentBinding
import com.bumptech.glide.Glide

import com.project4.BR.myModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding :MainFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this

        //sets button click
        binding.button.setOnClickListener {
            //sets pokemon data in view
            binding.myModel!!.getPokemonData()
            //removes welcome message
            binding.welcomeMessage.isGone=true

        }
        //enables menu in fragment
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.setVariable(myModel,viewModel)

        //sets observers
        val frontObserver = Observer<String>{ newImage->
            Glide.with(this)
                .load(newImage)
                .into(binding.sprite)
        }

        val shinyObserver = Observer<String>{ newImage->
            Glide.with(this)
                .load(newImage)
                .into(binding.sprite2)
        }
        binding.myModel!!.frontSprite.observe(viewLifecycleOwner,frontObserver)
        binding.myModel!!.shinySprite.observe(viewLifecycleOwner,shinyObserver)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.randomMon ->{
                binding.welcomeMessage.isGone=true
                binding.myModel!!.getPokemonData()
                true
            }
            else ->false
        }
    }

}