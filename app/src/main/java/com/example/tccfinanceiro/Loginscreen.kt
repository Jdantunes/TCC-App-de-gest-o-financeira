package com.example.tccfinanceiro

import android.widget.Button
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val Amarelo = Color(0xFFFFC107)
val Preto = Color(0xFF111111)
val Cinza = Color(0xFF777777)
val Branco = Color(0xFFFFFFFF)

@Composable
fun Loginscreen(
    onLoginClick: () -> Unit = {},
    onCadastroClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Preto)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "LOGIN",
            color = Branco,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Logo provisória
        Box(
            modifier = Modifier
                .size(130.dp)
                .background(Amarelo, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "LOGO",
                color = Preto,
                fontSize = 26.sp
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        // Campo de usuário
        Text(
            text = "E-mail ou usuário:",
            color = Branco,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text("Digite seu e-mail ou usuário")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarelo,
                unfocusedBorderColor = Cinza,
                focusedTextColor = Branco,
                unfocusedTextColor = Branco,
                focusedPlaceholderColor = Cinza,
                unfocusedPlaceholderColor = Cinza,
                cursorColor = Amarelo
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Campo de senha
        Text(
            text = "Senha:",
            color = Branco,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = {
                senha = it
                mensagemErro = ""
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text("Digite sua senha")
            },
            visualTransformation = if (mostrarSenha) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                TextButton(
                    onClick = {
                        mostrarSenha = !mostrarSenha
                    }
                ) {
                    Text(
                        text = if (mostrarSenha) "Ocultar" else "Mostrar",
                        color = Amarelo
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Amarelo,
                unfocusedBorderColor = Cinza,
                focusedTextColor = Branco,
                unfocusedTextColor = Branco,
                focusedPlaceholderColor = Cinza,
                unfocusedPlaceholderColor = Cinza,
                cursorColor = Amarelo
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Recuperação de senha
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(
                onClick = {
                    mensagemErro = "A recuperação de senha será implementada depois."
                }
            ) {
                Text(
                    text = "Esqueceu sua senha?",
                    color = Amarelo
                )
            }
        }

        // Mensagem de validação de erro
        if (mensagemErro.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = mensagemErro,
                color = Color(0xFFFF6B6B),
                fontSize = 13.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botão de entrar preenchendo a largura
        Button(
            onClick = {
                mensagemErro = when {
                    email.isBlank() ->
                        "Digite seu e-mail."

                    !android.util.Patterns.EMAIL_ADDRESS
                        .matcher(email.trim()).matches() ->
                        "Digite um e-mail válido."

                    senha.isBlank() ->
                        "Digite sua senha."

                    senha.length < 6 ->
                        "A senha deve ter pelo menos 6 caracteres."

                    else -> ""
                }

                if (mensagemErro.isEmpty()) {
                    onLoginClick()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Amarelo
            )
        ) {
            Text(
                text = "ENTRAR",
                fontSize = 16.sp,
                color = Preto
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Cadastro
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Não tem uma conta? ",
                color = Branco
            )

            TextButton(onClick = onCadastroClick) {
                Text(
                    text = "Cadastre-se",
                    color = Amarelo
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Opção visual Google
        Text(
            text = "Entrar com Google",
            color = Cinza,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
    } // Fim da Column correto aqui!
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Loginscreen()
}