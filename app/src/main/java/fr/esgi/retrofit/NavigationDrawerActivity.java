package fr.esgi.retrofit;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NavigationDrawerActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(this.toolbar);

        Intent intent = getIntent();
        if (intent != null){
            Bundle userBundle = intent.getBundleExtra(getString(R.string.user));
            user = userBundle.getParcelable(getString(R.string.user));
        }

        this.mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        this.drawerToggle = this.setupDrawerToggle();
        mDrawer.addDrawerListener(this.drawerToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        View headerView;
        if (nvDrawer != null) {
            headerView = nvDrawer.getHeaderView(0);
            TextView tvName = (TextView) headerView.findViewById(R.id.nav_tv_name);
            TextView tvMail = (TextView) headerView.findViewById(R.id.nav_tv_mail);
            ImageView ivPicture = (ImageView) headerView.findViewById(R.id.iv_profile);

            tvName.setText(user.getName());
            tvMail.setText(user.getLogin());
            Glide.with(this).load(user.getAvatarUrl()).into(ivPicture);
        }

        setupDrawerContent(nvDrawer);
        if (nvDrawer != null) {
            nvDrawer.getMenu().performIdentifierAction(R.id.nav_user, 0);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem, navigationView);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem, NavigationView navigationView) {
        Fragment fragment;
        switch (menuItem.getItemId()) {
            case R.id.nav_user:
                fragment = AccountFragment.newInstance(user);
                break;
            case R.id.nav_repos:
                fragment = ReposFragment.newInstance(user);
                break;
            case R.id.nav_stats:
                fragment = ViewPagerFragment.newInstance(user);
                break;
            case R.id.nav_disconnect:
                Session.getInstance().setCurrentUser(null);
                finish();
            default:
                fragment = AccountFragment.newInstance(user);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        navigationView.setCheckedItem(menuItem.getItemId());
        setTitle(menuItem.getTitle());
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }
}
