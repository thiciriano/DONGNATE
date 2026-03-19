package com.dongnate.ui.screens.auth

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
import com.dongnate.data.model.Ong
import com.dongnate.di.AppRepositories
import com.dongnate.ui.Routes
import com.dongnate.ui.theme.DS_Green
import com.dongnate.ui.theme.DS_Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterOngScreen(navController: NavController, userId: Int) {
    var orgName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("DADOS DA ONG", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DS_Green,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quase lá!",
                style = MaterialTheme.typography.titleLarge,
                color = DS_Green,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Precisamos das informações da sua organização.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = orgName,
                onValueChange = { orgName = it },
                label = { Text("Nome da Organização") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descrição (O que a ONG faz?)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = cnpj,
                onValueChange = { cnpj = it },
                label = { Text("CNPJ (00.000.000/0000-00)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Endereço Completo") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Telefone de Contato") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = website,
                onValueChange = { website = it },
                label = { Text("Site ou Redes Sociais (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(32.dp))
            
            Button(
                onClick = {
                    when {
                        orgName.length < 3 -> error = "Nome muito curto"
                        description.length < 20 -> error = "Descrição muito curta"
                        cnpj.isEmpty() -> error = "Informe o CNPJ"
                        address.isEmpty() -> error = "Informe o endereço"
                        phone.isEmpty() -> error = "Informe o telefone"
                        else -> {
                            val result = AppRepositories.ongs.create(
                                Ong(userId = userId, organizationName = orgName, description = description,
                                    cnpj = cnpj, fullAddress = address, phone = phone, website = website)
                            )
                            result.fold(
                                onSuccess = {
                                    navController.navigate(Routes.LOGIN) {
                                        popUpTo(0) { inclusive = true }
                                    }
                                },
                                onFailure = { error = it.message ?: "Erro ao salvar" }
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DS_Orange),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("FINALIZAR CADASTRO", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}
