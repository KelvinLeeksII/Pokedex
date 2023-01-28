/*
*@Author Kelvin Leeks II
* CIS 2818
* Project 4
* If you're reading this then you're awesome:)
 */

package com.project4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity(),SecondFragment.OnFragmentInteractionListener {

    private  var current : String = "Random"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
    //sets up menu
    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }
    //sets clicklistener for menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.searchMon -> {
                    if(current != item.title.toString()){//if user is not on page already
                        Navigation.findNavController(this.findViewById(R.id.navHostFragment)).navigate(R.id.action_mainFragment_to_secondFragment)
                        current = item.title.toString()
                        true
                    }else false
            }//end searchMon

            R.id.randomMon ->{
                if(current != item.title.toString()){//if user is not on page already
                    Navigation.findNavController(this.findViewById(R.id.navHostFragment)).navigate(R.id.action_secondFragment_to_mainFragment)
                    current = item.title.toString()
                    true
                }else false
            }//end randomMon
            else -> false
        }
    }

    override fun onFragmentInteraction(uri: Uri){

    }
}