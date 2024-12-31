package com.ug.taskaya.ui.drawer


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ug.taskaya.R
import com.ug.taskaya.ui.theme.DarkGray
import com.ug.taskaya.ui.theme.Ment
import com.ug.taskaya.utils.Screen
import com.ug.taskaya.utils.SharedState


@Composable
fun DrawerScreen(
    navController: NavController,
    viewModel: DrawerViewModel = hiltViewModel()
){

    val screenState by viewModel.screenState.collectAsState()
    val labels by SharedState.allLabels.collectAsState()

    viewModel.updateLabels(labels)

    DrawerContent(
        onClickSettings = { navController.navigate(Screen.SettingsScreen.route) },
        createNewLabel = { navController.navigate(Screen.DeleteLabelsScreen.route) },
        labels = screenState.labels,
        onClickStaredTasks = { navController.navigate(Screen.StaredTasksScreen.route) },
    )
}

@Composable
fun DrawerContent(
    onClickSettings: () -> Unit,
    createNewLabel: () -> Unit,
    labels: List<String>,
    onClickStaredTasks: () -> Unit,
) {

    val scrollState = rememberScrollState()

    ModalDrawerSheet(
        modifier = Modifier
            .requiredWidth(300.dp)
            .fillMaxHeight()
            .verticalScroll(scrollState),
        drawerContainerColor = Color.White
    ){

        Image(
            painter = painterResource(id = R.drawable.drawer_logo),
            contentDescription = "Header Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        DrawerItem(
            iconID = R.drawable.star_icon,
            label = "Stared Tasks",
            onClick = onClickStaredTasks
        )

        Labels(labels)

        DrawerItem(
            iconID = R.drawable.plus_icon,
            label = "Create New",
            onClick = createNewLabel
        )

        DrawerItem(
            iconID = R.drawable.settings_icon,
            label = "Settings",
            onClick = onClickSettings
        )
    }
}

@Composable
fun DrawerItem(iconID: Int, label: String, onClick: () -> Unit) {

    NavigationDrawerItem(
        label = {

            Text(
                fontFamily = FontFamily(Font(R.font.inter)),
                text = label,
                fontSize = 18.sp,
                color = Color.Black)
        },
        icon = {
            Icon(
                painter = painterResource(id = iconID),
                tint = Ment,
                contentDescription = null)
        },
        onClick = onClick,
        selected = false,
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.White
        )
    )
}

@Composable
fun Labels(subCategories: List<String>) {

    var expanded by remember{ mutableStateOf(false) }

    Column(
        modifier = Modifier
            .animateContentSize()
    ){

        NavigationDrawerItem(

            label = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        fontFamily = FontFamily(Font(R.font.inter)),
                        text = "Labels",
                        fontSize = 18.sp,
                        color = Color.Black
                    )

                    Icon(
                        modifier = Modifier
                            .size(40.dp),
                        painter = if (expanded) painterResource(id = R.drawable.arrow_up) else painterResource(id = R.drawable.arrow__down),
                        tint = DarkGray,
                        contentDescription = null
                    )
                }
            },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.category_icon),
                    tint = Ment,
                    contentDescription = null)
            },
            onClick = { expanded = !expanded },
            selected = false,
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.White
            )
        )

        if (expanded){

            for (subCategory in subCategories){

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    DrawerSubcategory(iconID = R.drawable.sub_icon, label = subCategory, count = "0", onClick = {})
                }

            }
        }
    }
}

@Composable
fun DrawerSubcategory(iconID: Int, label: String, onClick: () -> Unit, count: String) {

    NavigationDrawerItem(
        label = {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontFamily = FontFamily(Font(R.font.inter)),
                    text = label,
                    fontSize = 18.sp,
                    color = Color.Black
                )

                Text(
                    fontFamily = FontFamily(Font(R.font.inter)),
                    text = count,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        },
        icon = {
            Icon(
                painter = painterResource(id = iconID),
                tint = DarkGray,
                contentDescription = null)
        },
        onClick = onClick,
        selected = false,
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.White
        )
    )
}