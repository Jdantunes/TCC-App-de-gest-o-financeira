
package com.example.tccfinanceiro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TipoLancamento {
    RECEITA, DESPESA
}

data class Lancamento(
    val descricao: String,
    val valor: Double,
    val categoria: String,
    val tipo: TipoLancamento
)

@Composable
fun LancamentoScreen(
    onVoltarClick: () -> Unit = {},
    onSalvarClick: (Lancamento) -> Unit = {}
) {
    var tipoSelecionado by remember {
        mutableStateOf(TipoLancamento.RECEITA)
    }

    var descricao by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf("") }

    val amarelo = Color(0xFFFFC107)
    val preto = Color(0xFF111111)
    val branco = Color(0xFFFFFFFF)
    val cinza = Color(0xFF777777)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(preto)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onVoltarClick) {
                Text("Voltar", color = amarelo)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "NOVO LANÇAMENTO",
                color = branco,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "O que deseja adicionar?",
            color = branco,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Escolha entre receita e despesa
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FilterChip(
                selected = tipoSelecionado == TipoLancamento.RECEITA,
                onClick = {
                    tipoSelecionado = TipoLancamento.RECEITA
                    mensagemErro = ""
                },
                label = { Text("↑ Receita") },
                modifier = Modifier.weight(1f),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = amarelo,
                    selectedLabelColor = preto,
                    labelColor = branco
                )
            )

            FilterChip(
                selected = tipoSelecionado == TipoLancamento.DESPESA,
                onClick = {
                    tipoSelecionado = TipoLancamento.DESPESA
                    mensagemErro = ""
                },
                label = { Text("↓ Despesa") },
                modifier = Modifier.weight(1f),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = amarelo,
                    selectedLabelColor = preto,
                    labelColor = branco
                )
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        // Descrição
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Descrição") },
            placeholder = { Text("Ex: Salário, alimentação...") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = amarelo,
                unfocusedBorderColor = cinza,
                focusedTextColor = branco,
                unfocusedTextColor = branco,
                focusedLabelColor = amarelo,
                unfocusedLabelColor = cinza,
                cursorColor = amarelo
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Valor
        OutlinedTextField(
            value = valor,
            onValueChange = { valor = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Valor (R$)") },
            placeholder = { Text("Ex: 150,00") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = amarelo,
                unfocusedBorderColor = cinza,
                focusedTextColor = branco,
                unfocusedTextColor = branco,
                focusedLabelColor = amarelo,
                unfocusedLabelColor = cinza,
                cursorColor = amarelo
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Categoria
        OutlinedTextField(
            value = categoria,
            onValueChange = { categoria = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Categoria") },
            placeholder = { Text("Ex: Trabalho, alimentação...") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = amarelo,
                unfocusedBorderColor = cinza,
                focusedTextColor = branco,
                unfocusedTextColor = branco,
                focusedLabelColor = amarelo,
                unfocusedLabelColor = cinza,
                cursorColor = amarelo
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (mensagemErro.isNotEmpty()) {
            Text(
                text = mensagemErro,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Salvar
        Button(
            onClick = {
                val valorConvertido = valor
                    .trim()
                    .replace(".", "")
                    .replace(",", ".")
                    .toDoubleOrNull()

                when {
                    descricao.isBlank() ||
                            valor.isBlank() ||
                            categoria.isBlank() -> {
                        mensagemErro = "Preencha todos os campos."
                    }

                    valorConvertido == null ||
                            valorConvertido <= 0 -> {
                        mensagemErro = "Digite um valor válido."
                    }

                    else -> {
                        val novoLancamento = Lancamento(
                            descricao = descricao.trim(),
                            valor = valorConvertido,
                            categoria = categoria.trim(),
                            tipo = tipoSelecionado
                        )

                        onSalvarClick(novoLancamento)

                        descricao = ""
                        valor = ""
                        categoria = ""
                        mensagemErro = ""
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = amarelo
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "SALVAR LANÇAMENTO",
                color = preto,
                fontSize = 16.sp
            )
        }
    }
}
