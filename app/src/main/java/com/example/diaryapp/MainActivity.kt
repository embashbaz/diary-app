package com.example.diaryapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.preference.PreferenceManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.LinearLayout.HORIZONTAL
import android.widget.LinearLayout.VERTICAL
import android.widget.TextView
import com.example.diaryapp.databaseTemplate.entrriesMainTable.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    protected lateinit var dbopenhelper:dbOpenHelper ;
    protected  lateinit var recyclerview : MainActivityRecyclerviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        dbopenhelper = dbOpenHelper(this);

        recyclerview = MainActivityRecyclerviewAdapter(this,null);

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            startActivity(Intent(this, activity_note::class.java));
        }


        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        loadenty();

        val recyView : androidx.recyclerview.widget.RecyclerView = findViewById(R.id.items)
        // recyclerview  =  MainActivityRecyclerviewAdapter(this,null);
        val linearlayout  = androidx.recyclerview.widget.LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyView.layoutManager= linearlayout;
        recyView.adapter= recyclerview;

    }

    override fun onResume() {
        super.onResume()
        loadenty()
        setUserInfo()

    }

    private fun setUserInfo() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        val pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val headview: View = navView.getHeaderView(0)

        val name: TextView = headview.findViewById(R.id.nav_name)
        val email: TextView = headview.findViewById(R.id.nav_email)

        val nName: String = pref.getString("Name","")
        val nEmail: String = pref.getString("Email","")

        name.setText(nName)
        email.setText(nEmail)

    }

    fun loadenty(){
        val db : SQLiteDatabase = dbopenhelper.readableDatabase;

        val selclose: Array<String> = arrayOf(SUBJECT_COLUMS, TITLE_COLUMN, DATE_COLOMN,_ID )
        val cursor: Cursor = db.query(TABLE_NAME,selclose, null,null, null, null, null)
        recyclerview.cursorChange(cursor);
    }

    override fun onBackPressed() {
        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
        R.id.action_settings -> {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
            else -> {super.onOptionsItemSelected(item)}
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }


            R.id.nav_tools -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: androidx.drawerlayout.widget.DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
