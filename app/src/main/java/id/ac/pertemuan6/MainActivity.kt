package id.ac.pertemuan6

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                run("Search diklik")
                true
            }
            R.id.action_share -> {
                run("Bagikan diklik")
                true
            }
            R.id.action_folder -> {
                run("Folder diklik")
                true
            }
            R.id.action_about -> {
                run("About App diklik")
                true
            }
            R.id.action_setting -> {
                val intent = Intent(this, activity_settings::class.java)
                startActivity(intent)
                true
            }
            R.id.action_quotes -> {
                val intent = Intent(this, activity_quotes::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun run(message: String = "Private run dieksekusi") {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}