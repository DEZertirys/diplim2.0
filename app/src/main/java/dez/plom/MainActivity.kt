package dez.plom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import dez.plom.Adapter.MenuListAdapter
import dez.plom.model.Katmodel
import java.io.*

class MainActivity : AppCompatActivity(), MenuListAdapter.KatListClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val restaurantModel = getRestaurantData()
        initRecyclerView(restaurantModel)
        title = "Dezshop"

    }
    fun initRecyclerView(katlist: List<Katmodel?>?) {

                        val recyclerViewRestaurant = findViewById<RecyclerView>(R.id.listochek)
                        recyclerViewRestaurant?.layoutManager = LinearLayoutManager(this)
                        val adapter = MenuListAdapter(katlist, this)
                        recyclerViewRestaurant?.adapter = adapter
                    }


                    fun getRestaurantData(): List<Katmodel?> {
                        val inputStream: InputStream = resources.openRawResource(R.raw.katigories)
                        val writer: Writer = StringWriter()
                        val buffer = CharArray(1024)
                        try {
                            val reader: Reader =
                                BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                            var n: Int
                            while (reader.read(buffer).also { n = it } != -1) {
                                writer.write(buffer, 0, n)

                            }


                        } catch (e: Exception) {
                        }
                        val jsonStr: String = writer.toString()
                        val gson = Gson()
                        val restaurantModel =
                            gson.fromJson<Array<Katmodel>>(
                                jsonStr,
                                Array<Katmodel>::class.java
                            )
                                .toList()
                        return restaurantModel
                    }

    override fun onItemCLick(katlogmodel: Katmodel?) {
       val intent = Intent(this@MainActivity, RestMenu::class.java)
        intent.putExtra("Katmodel", katlogmodel)
        startActivity(intent)



    }


}