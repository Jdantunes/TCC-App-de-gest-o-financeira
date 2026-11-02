
package com.example.tccfinanceiro


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val CadastroAmarelo = Color(0xFFFFB900)
private val CadastroFundo = Color(0xFF111111)
private val CadastroCinza = Color(0xFF858585)

@Composable
fun CadastroScreen(
    onVoltarClick: () -> Unit = {},
    onCadastroConcluido: () -> Unit = {}
) {
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CadastroFundo)
    ) {
        // Cabeçalho amarelo
        IconButton(onClick = onVoltarClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .background(CadastroAmarelo)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onVoltarClick) {
                Text(
                    "←",
                    color = Color.Black,
                    fontSize = 28.sp
                )
            }

            Spacer(Modifier.width(12.dp))

            Text(
                "CADASTRO",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 22.dp)
                .padding(top = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nome
            CampoCadastro(
                titulo = "Nome:",
                valor = nome,
                aoAlterar = { nome = it },
                dica = "Digite seu nome"
            )

            Spacer(Modifier.height(14.dp))

            // E-mail
            CampoCadastro(
                titulo = "E-mail:",
                valor = email,
                aoAlterar = { email = it },
                dica = "Digite seu e-mail",
                tipoTeclado = KeyboardType.Email
            )

            Spacer(Modifier.height(14.dp))

            // Senha
            CampoCadastro(
                titulo = "Senha:",
                valor = senha,
                aoAlterar = { senha = it },
                dica = "Crie uma senha",
                senha = true
            )

            Spacer(Modifier.height(14.dp))

            // Confirmar senha
            CampoCadastro(
                titulo = "Confirmar senha:",
                valor = confirmarSenha,
                aoAlterar = { confirmarSenha = it },
                dica = "Repita sua senha",
                senha = true
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    mensagem = when {
                        nome.isBlank() ->
                            "Informe seu nome."
                        email.isBlank() ->
                            "Informe seu e-mail."
                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(email.trim()).matches() ->
                            "Informe um e-mail válido."
                        senha.length < 6 ->
                            "A senha deve ter pelo menos 6 caracteres."
                        senha != confirmarSenha ->
                            "As senhas não coincidem."
                        else -> ""
                    }

                    if (mensagem.isEmpty()) {
                        onCadastroConcluido()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CadastroAmarelo
                )
            ) {
                Text(
                    "CADASTRAR",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            if (mensagem.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))

                Text(
                    mensagem,
                    color = Color(0xFFFF7777),
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(22.dp))

            Text(
                "Já possui uma conta?",
                color = Color.White,
                fontSize = 14.sp
            )

            TextButton(onClick = onVoltarClick) {
                Text(
                    "Voltar para o login",
                    color = CadastroAmarelo
                )
            }
        }
    }
}

@Composable
private fun CampoCadastro(
    titulo: String,
    valor: String,
    aoAlterar: (String) -> Unit,
    dica: String,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    senha: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            titulo,
            color = Color.White,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(6.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = aoAlterar,
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp),
            placeholder = {
                Text(dica, color = Color.DarkGray, fontSize = 13.sp)
            },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (senha) {
                    KeyboardType.Password
                } else {
                    tipoTeclado
                }
            ),
            visualTransformation = if (senha) {
                PasswordVisualTransformation()
            } else {
                androidx.compose.ui.text.input.VisualTransformation.None
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CadastroCinza,
                unfocusedContainerColor = CadastroCinza,
                focusedBorderColor = CadastroAmarelo,
                unfocusedBorderColor = CadastroCinza,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = Color.Black
            )
        )
    }
}
