package dez.plom
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


@Suppress("DEPRECATION")
class NumberSucc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "Страница чека"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_succ)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        val rnds = (1..10000).random()
        findViewById<TextView>(R.id.textView6).setText("" + rnds)
        val cardView = findViewById<CardView>(R.id.card_View)
        val done = findViewById<Button>(R.id.done)
        done.setOnClickListener {
            onn()
        }
        val captureButton = findViewById<Button>(R.id.btn_capture)

        captureButton.setOnClickListener {

            val bitmap = getScreenShotFromView(cardView)

            if (bitmap != null) {
                saveMediaToStorage(bitmap)
            }
        }
    }
    fun onn(){

        val intent = Intent(this@NumberSucc, MainActivity::class.java)
        startActivityForResult(intent, 1000)

    }
    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Не сохранилось(" + e.message)
        }
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }

            }

        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Фотография чека сохранена в галерее" , Toast.LENGTH_SHORT).show()


        }

    }

}
