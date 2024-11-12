package com.ug.taskaya.ui.design_matrials.bottomNav

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.DarkGray
import com.ug.taskaya.ui.theme.Ment

@Composable
fun BottomNavigationBar(
    currentScreen: NavDestination,
    onNavigate: (BottomNavScreen) -> Unit
) {
    val screens = listOf(
        BottomNavScreen.More,
        BottomNavScreen.Tasks,
        BottomNavScreen.Calender,
        BottomNavScreen.Profile
    )

    Surface{

        NavigationBar(
            modifier = Modifier
                .height(80.dp),
            containerColor = Color.White
        ) {
            screens.forEach { screen ->

                val isSelected = currentScreen.route == screen.route

                NavigationBarItem(
                    colors = NavigationBarItemColors(
                        selectedIndicatorColor = Color.White,
                        unselectedIconColor = DarkGray,
                        selectedIconColor = Ment,
                        selectedTextColor = Ment,
                        unselectedTextColor = DarkGray,
                        disabledIconColor = DarkGray,
                        disabledTextColor = DarkGray
                    ),
                    icon = {

                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp),
                            tint = (if (isSelected) Ment else DarkGray)
                        )
                    },
                    selected = isSelected,
                    onClick = { onNavigate(screen) },
                    label = {
                        Text(text = screen.label, fontFamily = FontFamily(Font(R.font.inter)))
                    }
                )
            }
        }
    }
}