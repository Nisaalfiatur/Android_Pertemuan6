package id.ac.pertemuan6

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class activity_settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val tvContextFile = findViewById<TextView>(R.id.tv_context_file)
        registerForContextMenu(tvContextFile)

        tvContextFile.setOnClickListener { view ->
            openContextMenu(view)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                showToast("Search di Settings diklik")
                true
            }
            R.id.action_share -> {
                showToast("Bagikan di Settings diklik")
                true
            }
            R.id.action_folder -> {
                showToast("Folder di Settings diklik")
                true
            }
            R.id.action_feedback -> {
                showToast("Feedback diklik")
                true
            }
            R.id.action_help -> {
                showToast("Help diklik")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?,
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context_file, menu)
        menu?.setHeaderTitle("pilih aksi")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                showToast("Context: Edit dipilih")
                true
            }
            R.id.action_delete -> {
                showToast("Context: Delete dipilih")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}