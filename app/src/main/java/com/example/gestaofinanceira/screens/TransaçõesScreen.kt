
package com.example.gestaofinanceira.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Transacao(
    val nome: String,
    val categoria: String,
    val data: String,
    val valor: Double,
    val receita: Boolean
)

@Composable
fun TransacoesScreen(
    onNovaTransacaoClick: () -> Unit
) {

    val transacoes = listOf(
        Transacao("Salário", "Renda", "18/11/2026", 3000.0, true),
        Transacao("Alimentação", "Comida", "17/11/2026", 250.0, false),
        Transacao("Transporte", "Locomoção", "16/11/2026", 150.0, false),
        Transacao("Internet", "Contas", "15/11/2026", 150.0, false)
    )

    var filtro by remember { mutableStateOf("Todas") }

    val receitas = transacoes.filter { it.receita }
    val despesas = transacoes.filter { !it.receita }

    val saldo = receitas.sumOf { it.valor } -
            despesas.sumOf { it.valor }

    val listaFiltrada = when (filtro) {
        "Receitas" -> receitas
        "Despesas" -> despesas
        else -> transacoes
    }

    val amarelo = Color(0xFFFFC107)
    val preto = Color(0xFF111111)
    val branco = Color(0xFFFFFFFF)
    val cinza = Color(0xFF777777)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(preto)
            .padding(20.dp)
    ) {

        Text(
            text = "Transações",
            color = branco,
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Resumo financeiro
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF242424),
                    RoundedCornerShape(16.dp)
                )
                .padding(20.dp)
        ) {

            Text(
                text = "Saldo atual",
                color = cinza
            )

            Text(
                text = "R$ %.2f".format(saldo),
                color = branco,
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    Text("Receitas", color = cinza)

                    Text(
                        "+ R$ %.2f".format(
                            receitas.sumOf { it.valor }
                        ),
                        color = Color(0xFF4CAF50)
                    )
                }

                Column {
                    Text("Despesas", color = cinza)

                    Text(
                        "- R$ %.2f".format(
                            despesas.sumOf { it.valor }
                        ),
                        color = Color(0xFFF44336)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Filtros
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            listOf("Todas", "Receitas", "Despesas").forEach { opcao ->

                Button(
                    onClick = { filtro = opcao },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (filtro == opcao)
                            amarelo else Color(0xFF333333),
                        contentColor = if (filtro == opcao)
                            preto else branco
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    )
                ) {
                    Text(opcao)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Movimentações",
            color = branco,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Lista de transações
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(listaFiltrada) { transacao ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFF242424),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {

                        Text(
                            text = transacao.nome,
                            color = branco
                        )

                        Text(
                            text = "${transacao.categoria} • ${transacao.data}",
                            color = cinza,
                            fontSize = 12.sp
                        )
                    }

                    Text(
                        text = (if (transacao.receita) "+ " else "- ") +
                                "R$ %.2f".format(transacao.valor),
                        color = if (transacao.receita)
                            Color(0xFF4CAF50)
                        else
                            Color(0xFFF44336)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botão Nova transação
        Button(
            onClick = onNovaTransacaoClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = amarelo
            )
        ) {

            Text(
                text = "+ Nova transação",
                color = preto,
                fontSize = 16.sp
            )
        }
    }
}
