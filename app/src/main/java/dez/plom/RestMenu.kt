package dez.plom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dez.plom.Adapter.MenuRestAdapter
import dez.plom.model.Katmodel
import dez.plom.model.Menus
class RestMenu : AppCompatActivity(), MenuRestAdapter.MenuListClickListener {
    private var menuList: List<Menus?>? = null
    private var menuListAdapter: MenuRestAdapter? = null
    private var totalItemInCartCount= 0
    private var itemsinthecartlist: MutableList<Menus?>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rest_menu)
        val katmodel = intent?.getParcelableExtra<Katmodel>("Katmodel")
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setTitle(katmodel?.name)
        actionBar?.setSubtitle(katmodel?.address)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        menuList = katmodel?.menus
        initRecyclerView(menuList)

    }

    private fun initRecyclerView(menus: List<Menus?>?) {
        findViewById<RecyclerView>(R.id.menuRecyclerVuew).layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuRestAdapter(menus, this)
        findViewById<RecyclerView>(R.id.menuRecyclerVuew).adapter = menuListAdapter
    }

    override fun addToCartClickListener(menus: Menus) {
        if (itemsinthecartlist == null) {
            itemsinthecartlist = ArrayList()
        }
        itemsinthecartlist?.add(menus)
        totalItemInCartCount = 0
        for (menu in itemsinthecartlist!!) {
            totalItemInCartCount = totalItemInCartCount + menus?.totalInCart!!
        }
        findViewById<TextView>(R.id.basket).text = " " + totalItemInCartCount + ""

    }

    override fun updateCartClickListener(menus: Menus) {
        val index = itemsinthecartlist!!.indexOf(menus)
        itemsinthecartlist?.removeAt(index)
        itemsinthecartlist?.add(menus)
        totalItemInCartCount = 0
        for (menu in itemsinthecartlist!!) {
            totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
        }
        findViewById<TextView>(R.id.basket).text = " " + totalItemInCartCount + ""
    }
        override fun removeFromCartClickListener(menus: Menus) {
            if (itemsinthecartlist!!.contains(menus)) {
                itemsinthecartlist?.remove(menus)
                totalItemInCartCount = 0
                for (menu in itemsinthecartlist!!) {
                    totalItemInCartCount = totalItemInCartCount + menu?.totalInCart!!
                }
                findViewById<TextView>(R.id.basket).text = " " + totalItemInCartCount + ""
            }
        }

        override fun onCreateOptionsMenu(menus: Menu?): Boolean {
            menuInflater.inflate(R.menu.titleorder, menus)
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val katmodel = intent?.getParcelableExtra<Katmodel>("Katmodel")
            when (item.itemId) {
                android.R.id.home -> finish()
                R.id.basket -> {
                    if (totalItemInCartCount<= 0) {
                        Toast.makeText(this, "Добавьте товар для покупки", Toast.LENGTH_SHORT).show()
                    } else {
                        katmodel?.menus = itemsinthecartlist
                        val intent = Intent(this@RestMenu, OrderActivity::class.java)
                        intent.putExtra("RestaurantModel", katmodel)
                        startActivityForResult(intent, 1000)
                    }
                }
                else -> {}
            }
            return super.onOptionsItemSelected(item)
        }


        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 1000 && resultCode == RESULT_OK) {
                finish()
            }
        }
    }

