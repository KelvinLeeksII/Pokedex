package com.project4

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class SecondViewModel(application : Application) : AndroidViewModel(application)  {

    var name : MutableLiveData<String> = MutableLiveData()
    var type : MutableLiveData<String> = MutableLiveData()
    var id : MutableLiveData<String> = MutableLiveData()
    var frontSprite : MutableLiveData<String> = MutableLiveData()
    var shinySprite : MutableLiveData<String> = MutableLiveData()
    var spriteText: MutableLiveData<String> = MutableLiveData()
    var shinyText: MutableLiveData<String> = MutableLiveData()
    private lateinit var type1:String
    private var type2:String = ""

    /*
     * Makes a request to an API and grabs the name, id, sprite image, and type on a requested pokemon
     * @param searchID the id or name of the pokemon the user is requesting information on.
     */
    fun getPokemonData(searchID : String){
        val context = getApplication<Application>().applicationContext
        val url = "https://pokeapi.co/api/v2/pokemon/$searchID"
        val q = Volley.newRequestQueue(context)

        val request = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->

                if(searchID=="" || !response.has("name")){  //if user didn't enter valid pokemon
                    Toast.makeText(context,"Please Enter Valid Name or Number", Toast.LENGTH_LONG).show()
                    name.value = "Please Enter Valid Pokemon"
                    id.value = ""
                    type.value=""
                    frontSprite.value=""
                    spriteText.value =""
                    shinySprite.value=""
                    shinyText.value=""

                }else { //sets found Pokemon's data
                    name.value = response.getString("name")
                    id.value = response.getInt("id").toString()
                    frontSprite.value = response.getJSONObject("sprites").getString("front_default")
                    shinySprite.value = response.getJSONObject("sprites").getString("front_shiny")
                    spriteText.value = "Sprite"
                    shinyText.value = "Shiny Sprite"

                    //sets pokemon type
                    type1 = response.getJSONArray("types").getJSONObject(0).getJSONObject("type")
                        .getString("name")
                    if (!response.getJSONArray("types").isNull(1)) {
                        //if pokemon has two types
                        type2 =
                            response.getJSONArray("types").getJSONObject(1).getJSONObject("type")
                                .getString("name")
                        type.value = "$type1/ $type2"
                    } else type.value = type1

                }

            },{ error ->
                Toast.makeText(context,"Please Enter Valid Name or Number", Toast.LENGTH_LONG).show()
                name.value = "Please Enter Valid Pokemon"
                id.value = ""
                type.value=""
                frontSprite.value=""
                spriteText.value =""
                shinySprite.value=""
                shinyText.value=""
            }
        )//end request

        q.add(request)
    }
}