package com.dongnate.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dongnate.data.model.User
import com.dongnate.di.AppRepositories
import com.dongnate.ui.Routes
import com.dongnate.ui.theme.DS_Green
import com.dongnate.ui.theme.DS_Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("doador") }
    var error by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("CRIAR CONTA", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold) },
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
                text = "Seja bem-vindo!",
                style = MaterialTheme.typography.titleLarge,
                color = DS_Green,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Preencha os dados para começar a ajudar.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome completo") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )
            
            Spacer(Modifier.height(12.dp))
            
            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                label = { Text("Confirmar Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = DS_Green, focusedLabelColor = DS_Green)
            )

            Spacer(Modifier.height(24.dp))
            
            Text(
                "Eu quero ser um:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = role == "doador",
                    onClick = { role = "doador" },
                    colors = RadioButtonDefaults.colors(selectedColor = DS_Green)
                )
                Text("Doador", style = MaterialTheme.typography.bodyMedium)
                
                Spacer(Modifier.width(24.dp))
                
                RadioButton(
                    selected = role == "ong",
                    onClick = { role = "ong" },
                    colors = RadioButtonDefaults.colors(selectedColor = DS_Green)
                )
                Text("Representante de ONG", style = MaterialTheme.typography.bodyMedium)
            }

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(32.dp))
            
            Button(
                onClick = {
                    when {
                        name.isBlank() || email.isBlank() || password.isBlank() -> error = "Preencha todos os campos"
                        name.length < 3 -> error = "Nome muito curto"
                        !email.contains("@") -> error = "E-mail inválido"
                        password.length < 6 -> error = "Senha deve ter ao menos 6 caracteres"
                        password != confirm -> error = "Senhas não coincidem"
                        else -> {
                            val result = AppRepositories.users.create(
                                User(fullName = name.trim(), email = email.trim().lowercase(), passwordHash = password, role = role)
                            )
                            result.fold(
                                onSuccess = { user ->
                                    if (role == "ong") {
                                        navController.navigate(Routes.REGISTER_ONG + "/${user.id}") {
                                            popUpTo(Routes.REGISTER) { inclusive = true }
                                        }
                                    } else {
                                        navController.navigate(Routes.LOGIN) {
                                            popUpTo(Routes.REGISTER) { inclusive = true }
                                        }
                                    }
                                },
                                onFailure = { error = it.message ?: "Erro ao cadastrar" }
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DS_Orange),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("CADASTRAR", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(Modifier.height(16.dp))
            
            TextButton(onClick = { navController.popBackStack() }) {
                Text("Já tenho uma conta? Voltar", color = DS_Green)
            }
        }
    }
}
