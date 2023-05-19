package dez.plom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.SwitchCompat
import dez.plom.model.Katmodel
import java.util.concurrent.TimeUnit

class SuccessOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_order)
        val restaurantModel: Katmodel? = intent.getParcelableExtra("RestaurantModel")
        val actionbar: ActionBar? = supportActionBar
        title = "Страница оплаты"
        actionbar?.setDisplayHomeAsUpEnabled(false)
        TimeUnit.SECONDS.sleep(1)
        findViewById<TextView>(R.id.buttonDone).setOnClickListener {

            if (TextUtils.isEmpty(findViewById<TextView>(R.id.inputCardNumber).text.toString())) {
                findViewById<TextView>(R.id.inputCardNumber).error = "Введите номер карты"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(findViewById<TextView>(R.id.inputCardExpiry).text.toString())) {
                findViewById<TextView>(R.id.inputCardExpiry).error = "Введите месяц и год карты"
                return@setOnClickListener
            } else if (TextUtils.isEmpty(findViewById<TextView>(R.id.inputCardPin).text.toString())) {
                findViewById<TextView>(R.id.inputCardPin).error = "Введите  CVC"
                return@setOnClickListener
            }

            onn()

            setResult(RESULT_OK)

            finish()
        }
    }
        fun onn() {

            val intent = Intent(this@SuccessOrderActivity, NumberSucc::class.java)
            startActivityForResult(intent, 1000)

        }

//            return
//        } else if( TextUtils.isEmpty(findViewById<TextView>(R.id.inputCardNumber).text.toString())) {
//            findViewById<TextView>(R.id.inputCardNumber).error =  "Введите номер карты"
//            return
//        } else if( TextUtils.isEmpty(findViewById<TextView>(R.id.inputCardExpiry).text.toString())) {
//            findViewById<TextView>(R.id.inputCardExpiry).error =  "Ввелите скор карты"
//            return
//        } else if( TextUtils.isEmpty(findViewById<TextView>(R.id.inputCardPin).text.toString())) {
//            findViewById<TextView>(R.id.inputCardPin).error =  "Введите ccv карты"
//        }

}
