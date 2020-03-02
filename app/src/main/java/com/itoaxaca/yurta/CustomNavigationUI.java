package com.itoaxaca.yurta;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

import java.lang.ref.WeakReference;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.NavigationUI;

public class CustomNavigationUI {

    public static void setupWithNavController(@NonNull final NavigationView navigationView,
                                              @NonNull final NavController navController,
                                              @Nullable final NavigationView.OnNavigationItemSelectedListener customListener) {
        navigationView.setNavigationItemSelectedListener(
                item -> {
                    boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
                    if (handled) {
                        ViewParent parent = navigationView.getParent();
                        if (parent instanceof DrawerLayout) {
                            ((DrawerLayout) parent).closeDrawer(navigationView);
                        } else {
                            BottomSheetBehavior bottomSheetBehavior =
                                    findBottomSheetBehavior(navigationView);
                            if (bottomSheetBehavior != null) {
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                            }
                        }
                    } else {
                        if (customListener != null) {
                            customListener.onNavigationItemSelected(item);
                        }
                    }
                    return handled;
                });
        final WeakReference<NavigationView> weakReference = new WeakReference<>(navigationView);
        navController.addOnDestinationChangedListener(
                new NavController.OnDestinationChangedListener() {
                    @Override
                    public void onDestinationChanged(@NonNull NavController controller,
                                                     @NonNull NavDestination destination, @Nullable Bundle arguments) {
                        NavigationView view = weakReference.get();
                        if (view == null) {
                            navController.removeOnDestinationChangedListener(this);
                            return;
                        }
                        Menu menu = view.getMenu();
                        for (int h = 0, size = menu.size(); h < size; h++) {
                            MenuItem item = menu.getItem(h);
                            item.setChecked(matchDestination(destination, item.getItemId()));
                        }
                    }
                });
    }

    static BottomSheetBehavior findBottomSheetBehavior(@NonNull View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            ViewParent parent = view.getParent();
            if (parent instanceof View) {
                return findBottomSheetBehavior((View) parent);
            }
            return null;
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) params)
                .getBehavior();
        if (!(behavior instanceof BottomSheetBehavior)) {
            // We hit a CoordinatorLayout, but the View doesn't have the BottomSheetBehavior
            return null;
        }
        return (BottomSheetBehavior) behavior;
    }

    @SuppressWarnings("WeakerAccess") /* synthetic access */
    static boolean matchDestination(@NonNull NavDestination destination,
                                    @IdRes int destId) {
        NavDestination currentDestination = destination;
        while (currentDestination.getId() != destId && currentDestination.getParent() != null) {
            currentDestination = currentDestination.getParent();
        }
        return currentDestination.getId() == destId;
    }

}