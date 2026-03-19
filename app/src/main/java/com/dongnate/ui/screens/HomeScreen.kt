package com.dongnate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dongnate.data.model.Request
import com.dongnate.di.AppRepositories
import com.dongnate.ui.Routes
import com.dongnate.ui.theme.DS_Orange
import com.dongnate.ui.theme.DS_Green
import com.dongnate.util.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val requests = remember { mutableStateListOf<Request>() }

    LaunchedEffect(Unit) {
        requests.clear()
        requests.addAll(AppRepositories.requests.findAll())
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("DONGNATE", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DS_Green,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (requests.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhum pedido ativo no momento", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Pedidos Recentes",
                            style = MaterialTheme.typography.titleLarge,
                            color = DS_Green
                        )
                    }
                    items(requests) { request ->
                        RequestCard(request) {
                            navController.navigate("request_detail/${request.id}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RequestCard(request: Request, onClick: () -> Unit) {
    val ong = AppRepositories.ongs.findById(request.ongId)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = request.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            
            Spacer(Modifier.height(8.dp))
            
            // Category Badge
            Surface(
                color = DS_Green,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = request.category.uppercase(),
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            Spacer(Modifier.height(8.dp))
            
            if (ong != null) {
                Text(
                    text = "ONG ${ong.organizationName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            
            Spacer(Modifier.height(12.dp))
            
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = DS_Orange),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = if (Session.isOng()) "VER DETALHES" else "QUERO DOAR",
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        contentColor = DS_Green
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") },
            selected = currentRoute == Routes.HOME,
            onClick = {
                if (currentRoute != Routes.HOME) {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = DS_Green,
                selectedTextColor = DS_Green,
                indicatorColor = DS_Green.copy(alpha = 0.1f)
            )
        )
        
        val listLabel = if (Session.isOng()) "Meus Pedidos" else "Interesses"
        val listRoute = if (Session.isOng()) Routes.MY_REQUESTS else Routes.MY_INTERESTS
        
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = listLabel) },
            label = { Text(listLabel) },
            selected = currentRoute == listRoute,
            onClick = {
                if (currentRoute != listRoute) {
                    navController.navigate(listRoute)
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = DS_Green,
                selectedTextColor = DS_Green,
                indicatorColor = DS_Green.copy(alpha = 0.1f)
            )
        )

        NavigationBarItem(
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Sair") },
            label = { Text("Sair") },
            selected = false,
            onClick = {
                Session.logout()
                navController.navigate(Routes.LOGIN) {
                    popUpTo(0) { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}
