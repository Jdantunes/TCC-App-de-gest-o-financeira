package com.example.gestaofinanceira.screens



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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class HistoricoTransacao(
    val descricao: String,
    val categoria: String,
    val data: String,
    val valor: Double,
    val receita: Boolean
)

@Composable
fun HistoricoScreen(
    onVoltarClick: () -> Unit = {}
) {
    val amarelo = Color(0xFFFFC107)
    val preto = Color(0xFF111111)
    val branco = Color.White
    val cinza = Color(0xFF777777)
    val verde = Color(0xFF4CAF50)
    val vermelho = Color(0xFFE53935)

    var filtroSelecionado by remember { mutableStateOf("Todas") }

    // Dados demonstrativos
    val transacoes = listOf(
        HistoricoTransacao("Salário", "Trabalho", "18/11/2026", 2000.0, true),
        HistoricoTransacao("Mercado", "Alimentação", "17/11/2026", 180.50, false),
        HistoricoTransacao("Internet", "Moradia", "15/11/2026", 100.0, false),
        HistoricoTransacao("Freelance", "Renda extra", "12/11/2026", 350.0, true),
        HistoricoTransacao("Combustível", "Transporte", "10/11/2026", 150.0, false),
        HistoricoTransacao("Farmácia", "Saúde", "08/11/2026", 65.0, false)
    )

    val totalReceitas = transacoes
        .filter { it.receita }
        .sumOf { it.valor }

    val totalDespesas = transacoes
        .filter { !it.receita }
        .sumOf { it.valor }

    val saldo = totalReceitas - totalDespesas

    val transacoesFiltradas = when (filtroSelecionado) {
        "Receitas" -> transacoes.filter { it.receita }
        "Despesas" -> transacoes.filter { !it.receita }
        else -> transacoes
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(preto)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        // CABEÇALHO
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onVoltarClick) {
                Text(
                    text = "← Voltar",
                    color = amarelo
                )
            }

            Spacer(modifier = Modifier.width(15.dp))

            Text(
                text = "Histórico",
                color = branco,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Extrato de movimentações",
            color = cinza,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // SALDO
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF222222)
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Saldo das movimentações",
                    color = cinza,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "R$ %.2f".format(saldo),
                    color = amarelo,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // RECEITAS E DESPESAS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF222222)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Receitas", color = cinza, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "R$ %.2f".format(totalReceitas),
                        color = verde,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF222222)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text("Despesas", color = cinza, fontSize = 13.sp)

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "R$ %.2f".format(totalDespesas),
                        color = vermelho,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // FILTROS
        Text(
            text = "Movimentações",
            color = branco,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todas", "Receitas", "Despesas").forEach { filtro ->
                FilterChip(
                    selected = filtroSelecionado == filtro,
                    onClick = { filtroSelecionado = filtro },
                    label = { Text(filtro) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = amarelo,
                        selectedLabelColor = preto,
                        labelColor = branco
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // LISTA DE TRANSAÇÕES
        if (transacoesFiltradas.isEmpty()) {
            Text(
                text = "Nenhuma movimentação encontrada.",
                color = cinza,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        } else {
            transacoesFiltradas.forEach { transacao ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF222222)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = transacao.descricao,
                                color = branco,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = transacao.categoria,
                                color = cinza,
                                fontSize = 13.sp
                            )

                            Text(
                                text = transacao.data,
                                color = cinza,
                                fontSize = 12.sp
                            )
                        }

                        Text(
                            text = (if (transacao.receita) "+ R$ " else "- R$ ") +
                                    "%.2f".format(transacao.valor),
                            color = if (transacao.receita) verde else vermelho,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
