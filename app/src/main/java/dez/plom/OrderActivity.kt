package dez.plom

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dez.plom.Adapter.PlaceYourOrderAdapter
import dez.plom.model.Katmodel

class OrderActivity : AppCompatActivity() {

    var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    var isDeliveryOn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val restaurantModel: Katmodel? = intent.getParcelableExtra("RestaurantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.setTitle(restaurantModel?.name)
        actionbar?.setSubtitle(restaurantModel?.address)
        actionbar?.setDisplayHomeAsUpEnabled(true)

        findViewById<TextView>(R.id.buttonPlaceYourOrder).setOnClickListener {
            onPlaceOrderButtonCLick(restaurantModel)
        }

        findViewById<SwitchCompat>(R.id.switchDelivery1)?.setOnCheckedChangeListener { buttonView, isChecked ->

            if(isChecked) {
                findViewById<TextView>(R.id.inputAddress).visibility = View.VISIBLE

                findViewById<TextView>(R.id.inputCity).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tvPickup).setTextColor(Color.parseColor("#59166A"))
                findViewById<TextView>(R.id.tvDelivery).setTextColor(Color.parseColor("#E921BD"))
                findViewById<TextView>(R.id.tvDeliveryCharge).visibility = View.VISIBLE
                findViewById<TextView>(R.id.circlepic).visibility = View.GONE
                findViewById<TextView>(R.id.cricleDe).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tvDeliveryChargeAmount).visibility = View.VISIBLE
                findViewById<TextView>(R.id.tvDelivery).setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f)
                findViewById<TextView>(R.id.tvPickup).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                isDeliveryOn = true
                calculateTotalAmount(restaurantModel)
            } else {
                findViewById<TextView>(R.id.circlepic).visibility = View.VISIBLE
                findViewById<TextView>(R.id.cricleDe).visibility = View.GONE
                findViewById<TextView>(R.id.inputAddress).visibility = View.GONE
                        findViewById<TextView>(R.id.inputCity).visibility = View.GONE
                findViewById<TextView>(R.id.tvDelivery).setTextColor(Color.parseColor("#000000"))
                findViewById<TextView>(R.id.tvPickup).setTextColor(Color.parseColor("#E921BD"))
                findViewById<TextView>(R.id.tvPickup).setTextSize(TypedValue.COMPLEX_UNIT_SP, 23f)
                findViewById<TextView>(R.id.tvDelivery).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                        findViewById<TextView>(R.id.tvDeliveryCharge).visibility = View.GONE
                        findViewById<TextView>(R.id.tvDeliveryChargeAmount).visibility = View.GONE
                isDeliveryOn = false
                calculateTotalAmount(restaurantModel)
            }
        }

        initRecyclerView(restaurantModel)
        calculateTotalAmount(restaurantModel)
        showGif()
    }
    fun showGif() {
        val imageView: ImageView = findViewById(R.id.imageView3)
        Glide.with(this).load(R.drawable.load).into(imageView)
    }
    private fun initRecyclerView(restaurantModel: Katmodel?) {
        findViewById<RecyclerView>(R.id.cartItemsRecyclerView).layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel?.menus)
        findViewById<RecyclerView>(R.id.cartItemsRecyclerView).adapter =placeYourOrderAdapter
    }

    private fun calculateTotalAmount(restaurantModel: Katmodel?) {
        var subTotalAmount = 0f
        for(menu in restaurantModel?.menus!!) {
            subTotalAmount += menu?.price!!  * menu?.totalInCart!!

        }
        findViewById<TextView>(R.id.tvSubtotalAmount).text = "₽"+ String.format("%.2f", subTotalAmount)
        if(isDeliveryOn) {
            findViewById<TextView>(R.id.tvDeliveryChargeAmount).text = "₽"+String.format("%.2f", restaurantModel.delivery_charge?.toFloat())
            subTotalAmount += restaurantModel?.delivery_charge?.toFloat()!!
        }

        findViewById<TextView>(R.id.tvTotalAmount).text = "₽"+ String.format("%.2f", subTotalAmount)
    }

    private fun onPlaceOrderButtonCLick(restaurantModel: Katmodel?) {
        if(TextUtils.isEmpty(   findViewById<TextView>(R.id.inputName).text.toString())) {
            findViewById<TextView>(R.id.inputName).error =  "Введите своё имя"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(   findViewById<TextView>(R.id.inputAddress).text.toString())) {
            findViewById<TextView>(R.id.inputAddress).error =  "Введите свой адрес"
            return
        } else if(isDeliveryOn && TextUtils.isEmpty(   findViewById<TextView>(R.id.inputCity).text.toString())) {
            findViewById<TextView>(R.id.inputCity).error =  "Введите город"
            return
        }

        onn()
        return

    }
fun onn(){

    val intent = Intent(this@OrderActivity, fakeloading::class.java)
    startActivityForResult(intent, 1000)

}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000) {
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}