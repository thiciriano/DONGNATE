package com.dongnate.ui.screens.ong

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dongnate.data.model.Request
import com.dongnate.di.AppRepositories
import com.dongnate.util.Session

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRequestScreen(navController: NavController) {
    val ong = Session.currentUser?.let { AppRepositories.ongs.findByUserId(it.id) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("food") }
    var quantity by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("medium") }
    var location by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val categories = listOf("food", "clothing", "hygiene", "volunteer", "furniture", "others")
    val urgencies = listOf("low", "medium", "high", "urgent")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Pedido") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { padding ->
        if (ong == null) {
            Box(Modifier.fillMaxSize().padding(padding)) { Text("ONG não encontrada") }
            return@Scaffold
        }

        Column(Modifier.padding(padding).padding(16.dp).verticalScroll(rememberScrollState())) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descrição") }, minLines = 3, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(10.dp))

            Text("Categoria:")
            categories.chunked(3).forEach { row ->
                Row {
                    row.forEach { cat ->
                        FilterChip(
                            selected = category == cat,
                            onClick = { category = cat },
                            label = { Text(cat, fontSize = 12.sp) },
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(10.dp))
            if (category != "volunteer") {
                Row {
                    OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("Quantidade") }, modifier = Modifier.weight(1f))
                    Spacer(Modifier.width(8.dp))
                    OutlinedTextField(value = unit, onValueChange = { unit = it }, label = { Text("Unidade") }, modifier = Modifier.weight(1f))
                }
                Spacer(Modifier.height(10.dp))
            }

            Text("Urgência:")
            Row {
                urgencies.forEach { u ->
                    FilterChip(
                        selected = urgency == u,
                        onClick = { urgency = u },
                        label = { Text(u, fontSize = 12.sp) },
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }

            Spacer(Modifier.height(10.dp))
            OutlinedTextField(value = location, onValueChange = { location = it }, label = { Text("Local de entrega (opcional)") }, modifier = Modifier.fillMaxWidth())

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(error, color = MaterialTheme.colorScheme.error, fontSize = 13.sp)
            }

            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {
                    val minDesc = if (urgency == "high" || urgency == "urgent") 100 else 30
                    when {
                        title.length < 10 -> error = "Título muito curto"
                        description.length < minDesc -> error = "Descrição muito curta (min. $minDesc chars)"
                        else -> {
                            val result = AppRepositories.requests.create(
                                Request(
                                    ongId = ong.id,
                                    title = title,
                                    description = description,
                                    category = category,
                                    quantity = quantity.toIntOrNull(),
                                    unit = unit,
                                    urgency = urgency,
                                    deliveryLocation = location
                                )
                            )
                            result.fold(
                                onSuccess = { navController.popBackStack() },
                                onFailure = { error = it.message ?: "Erro" }
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Publicar Pedido") }
        }
    }
}
