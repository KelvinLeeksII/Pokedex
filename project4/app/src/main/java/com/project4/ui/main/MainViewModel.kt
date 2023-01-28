package com.project4.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlin.random.Random

class MainViewModel(application : Application) : AndroidViewModel(application) {

    var title : MutableLiveData<String> = MutableLiveData()
    var type : MutableLiveData<String> = MutableLiveData()
    var id : MutableLiveData<String> = MutableLiveData()
    var frontSprite : MutableLiveData<String> = MutableLiveData()
    var shinySprite : MutableLiveData<String> = MutableLiveData()
    var spriteText: MutableLiveData<String> = MutableLiveData()
    var shinyText: MutableLiveData<String> = MutableLiveData()
    private lateinit var type1:String
    private var type2:String = ""

    /*
    *Makes a request to an API and grabs the name, id, sprite image, and type of a random pokemon
     */
    fun getPokemonData(){

        val num = Random.nextInt(1,897).toString()
        val context = getApplication<Application>().applicationContext
        val url = "https://pokeapi.co/api/v2/pokemon/$num"
        val q = Volley.newRequestQueue(context)


        val request = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                title.value = response.getString("name")
                id.value = num
                frontSprite.value =  response.getJSONObject("sprites").getString("front_default")
                shinySprite.value =  response.getJSONObject("sprites").getString("front_shiny")
                spriteText.value = "Sprite"
                shinyText.value = "Shiny Sprite"

                //sets pokemon type
                type1 = response.getJSONArray("types").getJSONObject(0).getJSONObject("type")
                    .getString("name")
                if(!response.getJSONArray("types").isNull(1)){
                    type2 = response.getJSONArray("types").getJSONObject(1).getJSONObject("type")
                        .getString("name")
                    type.value = "$type1/ $type2"
                }else type.value = type1

            },{ volleyError -> title.value= volleyError.message})

        q.add(request)
    }
}