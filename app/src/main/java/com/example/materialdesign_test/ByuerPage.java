package com.example.materialdesign_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

public class ByuerPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ImageView menu;
    View HeaderView;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_byuer_page);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        drawerLayout=findViewById(R.id.drawable);
        navigationView=findViewById(R.id.navigation);
        menu=findViewById(R.id.imageView);

//        String Username=getIntent().getStringExtra("username");
        nvigationDrawer();
    }

    private void nvigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        HeaderView=navigationView.getHeaderView(0);
        username=(TextView)HeaderView.findViewById(R.id.username);

        username.setText(getIntent().getStringExtra("username"));
        navigationView.setCheckedItem(R.id.byer);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){drawerLayout.closeDrawer(GravityCompat.START);}
                else{drawerLayout.openDrawer(GravityCompat.START);}
            }
        });
    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.byer:
                break;
            case R.id.Post:
                Intent pst=new Intent(getApplicationContext(),Post_Page.class);
                startActivity(pst);
                break;
            case R.id.profile:
                Intent proile=new Intent(ByuerPage.this,Profile_Page.class);
                startActivity(proile);
                break;
            case R.id.logout:
                break;
        }
        return true;
    }
}