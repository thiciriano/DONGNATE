package com.dongnate.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dongnate.di.AppRepositories
import com.dongnate.data.model.Interest
import com.dongnate.ui.theme.DS_Green
import com.dongnate.ui.theme.DS_Orange
import com.dongnate.util.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestDetailScreen(navController: NavController, requestId: Int) {
    val request = AppRepositories.requests.findById(requestId)
    val ong = request?.let { AppRepositories.ongs.findById(it.ongId) }
    var message by remember { mutableStateOf("") }
    var feedback by remember { mutableStateOf("") }
    var alreadyInterested by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val user = Session.currentUser
        if (user != null && request != null) {
            alreadyInterested = AppRepositories.interests.exists(user.id, requestId)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("DETALHES DO PEDIDO", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DS_Green,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        if (request == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Pedido não encontrado", style = MaterialTheme.typography.bodyLarge)
            }
            return@Scaffold
        }
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = request.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Surface(color = DS_Green, shape = RoundedCornerShape(4.dp)) {
                            Text(
                                text = request.category.uppercase(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                        
                        Surface(
                            color = if (request.urgency.lowercase() == "alta") Color.Red else DS_Orange,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "URGÊNCIA: ${request.urgency.uppercase()}",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Text(
                        text = request.description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 22.sp
                    )

                    if (request.quantity != null) {
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Quantidade Necessária:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "${request.quantity} ${request.unit}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    if (request.deliveryLocation.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = "Local de Entrega:",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = request.deliveryLocation,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

            if (ong != null) {
                Spacer(Modifier.height(20.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(Modifier.padding(20.dp)) {
                        Text(
                            text = "ONG RESPONSÁVEL",
                            style = MaterialTheme.typography.labelLarge,
                            color = DS_Green,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = ong.organizationName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Telefone: ${ong.phone}", style = MaterialTheme.typography.bodyMedium)
                        if (ong.website.isNotEmpty()) {
                            Text(text = "Site: ${ong.website}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }

            val user = Session.currentUser
            if (user != null && !Session.isOng()) {
                Spacer(Modifier.height(24.dp))
                if (alreadyInterested) {
                    Surface(
                        color = DS_Green.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Você já manifestou interesse! Entre em contato com a ONG para combinar a doação.",
                            color = DS_Green,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { if (it.length <= 500) message = it },
                        label = { Text("Mensagem para a ONG (opcional)") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DS_Green,
                            focusedLabelColor = DS_Green
                        )
                    )
                    
                    Spacer(Modifier.height(20.dp))
                    
                    Button(
                        onClick = {
                            val result = AppRepositories.interests.create(
                                Interest(requestId = requestId, userId = user.id, message = message)
                            )
                            result.fold(
                                onSuccess = { 
                                    alreadyInterested = true
                                    feedback = "Interesse enviado com sucesso!" 
                                },
                                onFailure = { feedback = it.message ?: "Erro ao enviar" }
                            )
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DS_Orange),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("QUERO DOAR AGORA", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
                if (feedback.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = feedback,
                        color = if (feedback.contains("sucesso")) DS_Green else Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
