package com.dongnate.ui.screens.ong

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongnate.data.model.Request
import com.dongnate.di.AppRepositories
import com.dongnate.ui.Routes
import com.dongnate.util.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRequestsScreen(navController: NavController) {
    val ong = Session.currentUser?.let { AppRepositories.ongs.findByUserId(it.id) }
    val requests = remember { mutableStateListOf<Request>() }

    LaunchedEffect(Unit) {
        if (ong != null) {
            requests.clear()
            requests.addAll(AppRepositories.requests.findByOngId(ong.id))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Pedidos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.CREATE_REQUEST) }) {
                Icon(Icons.Default.Add, contentDescription = "Novo pedido")
            }
        }
    ) { padding ->
        if (ong == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Perfil de ONG não encontrado")
            }
            return@Scaffold
        }
        if (requests.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Nenhum pedido criado")
            }
        } else {
            LazyColumn(Modifier.padding(padding).padding(16.dp)) {
                items(requests) { req ->
                    Card(Modifier.fillMaxWidth()) {
                        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text(req.title, fontWeight = FontWeight.Bold)
                                Text("Status: ${req.status} | Urgência: ${req.urgency}")
                                val count = AppRepositories.interests.findByRequestId(req.id).size
                                Text("$count interesse(s)")
                            }
                            IconButton(onClick = {
                                AppRepositories.requests.delete(req.id)
                                requests.remove(req)
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Excluir")
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}
