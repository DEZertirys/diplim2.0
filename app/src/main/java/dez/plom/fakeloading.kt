package dez.plom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.util.concurrent.TimeUnit

class fakeloading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fakeloading)
        title = "Загрузка"
//        TimeUnit.SECONDS.sleep(0)
//        val intent = Intent(this@fakeloading, fakeloading::class.java)
//        startActivityForResult(intent, 1000)
//        TimeUnit.SECONDS.sleep(3)
//        val intent = Intent(this@fakeloading, SuccessOrderActivity::class.java)
//        startActivityForResult(intent, 1000)
        val imageView: ImageView = findViewById(R.id.imageView2)
        Glide.with(this).load(R.drawable.load).into(imageView)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SuccessOrderActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
}
