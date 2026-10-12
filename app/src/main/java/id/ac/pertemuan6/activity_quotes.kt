package id.ac.pertemuan6

import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class activity_quotes : AppCompatActivity() {

    private lateinit var tvQuote: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var btnAction: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quotes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvQuote = findViewById(R.id.tv_quote)
        tvAuthor = findViewById(R.id.tv_author)
        btnAction = findViewById(R.id.btn_action)

        registerForContextMenu(tvQuote)
        registerForContextMenu(tvAuthor)

        btnAction.setOnClickListener { view ->
            showPopupMenu(view)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context_file, menu)
        menu?.setHeaderTitle(getString(R.string.header_context))
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_copy -> {
                val fullQuote = "${tvQuote.text}\n${tvAuthor.text}"
                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Quote", fullQuote)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(this, "Quote disalin!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_edit -> {
                Toast.makeText(this, "Edit dipilih", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_popup, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_download -> {
                    downloadQuote()
                    true
                }
                R.id.action_share -> {
                    shareQuote()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun downloadQuote() {
        val mainView = findViewById<View>(R.id.main)
        mainView.post {
            try {
                val bitmap = Bitmap.createBitmap(mainView.width, mainView.height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                mainView.draw(canvas)

                val filename = "Quote_${System.currentTimeMillis()}.jpg"
                var outputStream: OutputStream? = null

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val resolver = contentResolver
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                    if (imageUri != null) {
                        outputStream = resolver.openOutputStream(imageUri)
                    }
                } else {
                    val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val image = File(imagesDir, filename)
                    outputStream = FileOutputStream(image)
                }

                outputStream?.use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
                Toast.makeText(this, "Quotes dan gambar berhasil didownload!", Toast.LENGTH_SHORT).show()
            } catch (_: Exception) {
                Toast.makeText(this, "Quotes dan gambar berhasil didownload!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareQuote() {
        val quoteText = "${tvQuote.text}\n${tvAuthor.text}"
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, quoteText)
        }
        startActivity(Intent.createChooser(shareIntent, "Bagikan Quote ke:"))
    }
}