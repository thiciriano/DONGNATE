package com.dongnate.ui.screens.donor

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dongnate.data.model.Interest
import com.dongnate.di.AppRepositories
import com.dongnate.util.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInterestsScreen(navController: NavController) {
    val user = Session.currentUser
    val interests = remember { mutableStateListOf<Interest>() }

    LaunchedEffect(Unit) {
        if (user != null) {
            interests.clear()
            interests.addAll(AppRepositories.interests.findByUserId(user.id))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Interesses") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        if (interests.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Você ainda não manifestou interesse em nenhum pedido")
            }
        } else {
            LazyColumn(Modifier.padding(padding).padding(16.dp)) {
                items(interests) { interest ->
                    val request = AppRepositories.requests.findById(interest.requestId)
                    val ong = request?.let { AppRepositories.ongs.findById(it.ongId) }
                    Card(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text(request?.title ?: "Pedido removido", fontWeight = FontWeight.Bold)
                            if (ong != null) {
                                Text("ONG: ${ong.organizationName}")
                                Text("Contato: ${ong.phone}")
                            }
                            Text("Status: ${interest.status}")
                            if (interest.message.isNotEmpty()) Text("Sua mensagem: ${interest.message}")
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}
