package com.example.gestaofinanceira.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NovaTransacaoScreen(
    onVoltarClick: () -> Unit = {}
) {

    val amarelo = Color(0xFFFFC107)
    val preto = Color(0xFF111111)
    val branco = Color.White
    val cinza = Color(0xFF777777)

    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }

    var tipo by remember { mutableStateOf("Despesa") }
    var mensagemErro by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(preto)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        // Cabeçalho
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(
                onClick = onVoltarClick
            ) {
                Text(
                    text = "← Voltar",
                    color = amarelo
                )
            }

            Text(
                text = "Nova transação",
                color = branco,
                fontSize = 24.sp
            )
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Tipo de movimentação
        Text(
            text = "Tipo de movimentação",
            color = branco,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            listOf("Receita", "Despesa").forEach { opcao ->

                Button(
                    onClick = {
                        tipo = opcao
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (tipo == opcao)
                            amarelo else Color(0xFF333333),
                        contentColor = if (tipo == opcao)
                            preto else branco
                    )
                ) {
                    Text(opcao)
                }
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Descrição
        Text("Descrição", color = branco)

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Ex: Compra no mercado")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = amarelo,
                unfocusedBorderColor = cinza,
                focusedTextColor = branco,
                unfocusedTextColor = branco
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Valor
        Text("Valor (R$)", color = branco)

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Ex: 150,00")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = amarelo,
                unfocusedBorderColor = cinza,
                focusedTextColor = branco,
                unfocusedTextColor = branco
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Categoria
        Text("Categoria", color = branco)

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("Ex: Alimentação, transporte")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = amarelo,
                unfocusedBorderColor = cinza,
                focusedTextColor = branco,
                unfocusedTextColor = branco
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Data
        Text("Data", color = branco)

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            value = data,
            onValueChange = { data = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text("DD/MM/AAAA")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = amarelo,
                unfocusedBorderColor = cinza,
                focusedTextColor = branco,
                unfocusedTextColor = branco
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Mensagem de erro
        if (mensagemErro.isNotEmpty()) {
            Text(
                text = mensagemErro,
                color = Color.Red,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        // Botão salvar
        Button(
            onClick = {

                val valorConvertido = valor
                    .replace(",", ".")
                    .toDoubleOrNull()

                mensagemErro = when {

                    descricao.isBlank() ->
                        "Informe a descrição."

                    valorConvertido == null ||
                            valorConvertido <= 0 ->
                        "Informe um valor válido."

                    categoria.isBlank() ->
                        "Informe a categoria."

                    data.isBlank() ->
                        "Informe a data."

                    else ->
                        "Dados preenchidos! O salvamento será implementado com o banco de dados."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = amarelo
            ),
            shape = RoundedCornerShape(10.dp)
        ) {

            Text(
                text = "SALVAR TRANSAÇÃO",
                color = preto,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(15.dp))
    }
}
