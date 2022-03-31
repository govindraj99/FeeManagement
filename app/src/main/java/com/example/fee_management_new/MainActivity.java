package com.example.fee_management_new;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    AppBarConfiguration appBarConfiguration;
    BottomNavigationView bottomNavigationView;
    int c = 0;
    ConstraintLayout parent_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_nav_host_fragment);
        navController = navHostFragment.getNavController();
        parent_layout = findViewById(R.id.Parent_layout);
//        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);
//
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        //Toolbar

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
//        parent_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                c++;
//                if (c % 2 == 0) {
//                  bottomNavigationView.setVisibility(View.VISIBLE);
//                } else {
//                    bottomNavigationView.setVisibility(View.GONE);
//                }
//
//            }
//        });

//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavBehivaour());



//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
//                if (navDestination.getId() == R.id.generatePaymentRequest) {
//                    bottomNavigationView.setVisibility(View.GONE);
//                } else if (navDestination.getId() == R.id.addOfflinePayment){
//                    bottomNavigationView.setVisibility(View.GONE);
//
//                }else if (navDestination.getId() == R.id.transaction){
//                    bottomNavigationView.setVisibility(View.GONE);
//                }else {
//                    bottomNavigationView.setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
//        return NavigationUI.navigateUp(navController,appBarConfiguration);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.tool_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
////        navController = Navigation.findNavController(this, R.id.activity_main_nav_host_fragment);
//        return NavigationUI.onNavDestinationSelected(item, navController)
//                || super.onOptionsItemSelected(item);
//
//    }


}