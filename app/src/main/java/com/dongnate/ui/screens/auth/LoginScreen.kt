package com.dongnate.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.dongnate.di.AppRepositories
import com.dongnate.ui.Routes
import com.dongnate.ui.theme.DS_Green
import com.dongnate.ui.theme.DS_Orange
import com.dongnate.util.AuthHelper
import com.dongnate.util.Session

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "DONGNATE",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = DS_Green,
                fontSize = 40.sp
            )
            Text(
                text = "Conectando solidariedade",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
            
            Spacer(Modifier.height(48.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it; error = "" },
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DS_Green,
                    focusedLabelColor = DS_Green
                ),
                singleLine = true
            )
            
            Spacer(Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; error = "" },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = DS_Green,
                    focusedLabelColor = DS_Green
                ),
                singleLine = true
            )

            if (error.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }

            Spacer(Modifier.height(32.dp))
            
            Button(
                onClick = {
                    val trimmedEmail = email.trim().lowercase()
                    val user = AppRepositories.users.findByEmail(trimmedEmail)
                    when {
                        email.isBlank() || password.isBlank() -> error = "Preencha todos os campos"
                        user == null -> error = "E-mail não encontrado"
                        !AuthHelper.verify(password, user.passwordHash) -> error = "Senha incorreta"
                        user.accountStatus != "active" -> error = "Conta inativa"
                        else -> {
                            Session.currentUser = user
                            navController.navigate(Routes.HOME) {
                                popUpTo(Routes.LOGIN) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DS_Orange),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("ENTRAR", fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(Modifier.height(24.dp))
            
            TextButton(onClick = { navController.navigate(Routes.REGISTER) }) {
                Text(
                    "Não tem uma conta? Cadastre-se",
                    color = DS_Green,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                "DICA PARA TESTES:\n" +
                "Doador: doador@teste.com (senha: 123456)\n" +
                "ONG: ong@teste.com (senha: 123456)",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
