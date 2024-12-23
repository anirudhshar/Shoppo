package com.example.shoppo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shoppo.ui.feature.account.login.LoginScreen
import com.example.shoppo.ui.feature.account.register.RegisterScreen
import com.example.shoppo.ui.feature.cart.CartScreen
import com.example.shoppo.ui.feature.home.HomeScreen
import com.example.shoppo.ui.feature.orders.OrdersScreen
import com.example.shoppo.ui.feature.product_details.ProductDetailsScreen
import com.example.shoppo.ui.feature.summary.CartSummaryScreen
import com.example.shoppo.ui.feature.user_address.UserAddressScreen
import com.example.shoppo.ui.theme.ShoppoTheme
import com.model.UiProductModel
import com.navigation.CartSummaryScreen
import com.navigation.HomeScreen
import com.navigation.LoginScreen
import com.navigation.OrderScreen
import com.navigation.ProductDetails
import com.navigation.ProfileScreen
import com.navigation.RegisterScreen
import com.navigation.UserAddressRoute
import com.navigation.UserAddressWrapper
import com.navigation.productNavType
import com.navigation.userAddressNavType
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoppoTheme {
                val shouldShowBottomBar = remember {
                    mutableStateOf(true)
                }
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        AnimatedVisibility(visible = shouldShowBottomBar.value, enter = fadeIn()) {

                            BottomNavigationBar(navController)
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {



                        NavHost(navController = navController, startDestination = if(ShopperSession.getUser()!=null){
                            HomeScreen
                        }
                        else{
                            LoginScreen
                        }) {
                            composable<HomeScreen> {
                                HomeScreen(navController)
                                shouldShowBottomBar.value = true
                            }
                            composable<OrderScreen> {
                                shouldShowBottomBar.value = true
                                CartScreen(navController)
                            }
                            composable<ProfileScreen> {
                                shouldShowBottomBar.value = true
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Profile")
                                }
                            }

                            composable<ProductDetails>(
                                typeMap = mapOf(typeOf<UiProductModel>() to productNavType)
                            ) {
                                shouldShowBottomBar.value = false
                                val productRoute = it.toRoute<ProductDetails>()
                                ProductDetailsScreen(
                                    navController = navController,
                                    product = productRoute.product
                                )

                            }

                            composable<OrderScreen> {
                                shouldShowBottomBar.value = false
                                OrdersScreen()



                            }

                            composable<CartSummaryScreen> {
                                shouldShowBottomBar.value = false
                                CartSummaryScreen(navController = navController)

                            }

                            composable<UserAddressRoute>(
                                typeMap = mapOf(typeOf<UserAddressWrapper>() to userAddressNavType)
                            ) {
                                shouldShowBottomBar.value = false
                                val userAddressRoute = it.toRoute<UserAddressRoute>()
                                UserAddressScreen(
                                    navController = navController,
                                    userAddress = userAddressRoute.userAddressWrapper.userAddress
                                )

                            }
                            
                            composable<LoginScreen> {
                                shouldShowBottomBar.value =false
                                LoginScreen(navController = navController)
                            }

                            composable<RegisterScreen> {
                                shouldShowBottomBar.value =false
                                RegisterScreen(navController = navController)
                            }
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {

        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        Log.d("current route", "$currentRoute")
        val items = listOf(
            BottomNavItems.Home,
            BottomNavItems.Orders,
            BottomNavItems.Profile
        )

        items.forEach { item ->
            val isSelected = currentRoute?.substringBefore("?") == item.route::class.qualifiedName
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { startRoute ->
                            popUpTo(startRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(text = item.title) },
                icon = {
                    Image(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray)
                    )
                }, colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}

sealed class BottomNavItems(val route: Any, val title: String, val icon: Int) {
    object Home : BottomNavItems(HomeScreen, "Home", icon = R.drawable.ic_home)
    object Orders : BottomNavItems(OrderScreen, "Orders", icon = R.drawable.ic_cart)
    object Profile : BottomNavItems(ProfileScreen, "Profile", icon = R.drawable.ic_profile_bn)
}

