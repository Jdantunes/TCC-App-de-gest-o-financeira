package com.example.gestaofinanceira.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AmareloHome = Color(0xFFFFB900)
private val FundoHome = Color(0xFF111111)
private val CartaoHome = Color(0xFF292929)
private val TextoHome = Color.White

@Composable
fun HomeScreen(
    onMetasClick: () -> Unit = {},
    onTransacoesClick: () -> Unit = {},
    onAdicionarClick: () -> Unit = {},
    onHistoricoClick: () -> Unit = {},
    onConfiguracaoClick: () -> Unit = {}
) {
    Scaffold(
        containerColor = FundoHome,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF444444))
                    .navigationBarsPadding()
                    .height(64.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                MenuItem("⌂", "Início") {}
                MenuItem("↻", "Metas", onMetasClick)
                MenuItem("+", "Adicionar", onAdicionarClick)
                MenuItem("▤", "Histórico", onHistoricoClick)
                MenuItem("⚙", "Perfil", onConfiguracaoClick)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            // Cabeçalho
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Olá! 👋",
                    color = TextoHome,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "♧",
                    color = AmareloHome,
                    fontSize = 28.sp,
                    modifier = Modifier.clickable {
                        onConfiguracaoClick()
                    }
                )
            }

            // Saldo total
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = AmareloHome
                )
            ) {
                Column(
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        "Saldo Total",
                        color = Color.Black,
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "R$ 0,00",
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "Seu saldo disponível",
                        color = Color.DarkGray,
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Receitas e despesas
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ResumoCard(
                    titulo = "↑ Receitas",
                    valor = "R$ 0,00",
                    modifier = Modifier.weight(1f)
                )

                ResumoCard(
                    titulo = "↓ Despesas",
                    valor = "R$ 0,00",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Acesso rápido",
                color = TextoHome,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Atalho("◎", "Metas", onMetasClick)
                Atalho("$", "Transações", onTransacoesClick)
                Atalho("+", "Adicionar", onAdicionarClick)
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "Despesas por categoria",
                color = TextoHome,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        CartaoHome,
                        androidx.compose.foundation.shape.RoundedCornerShape(14.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CategoriaItem("🍔", "Alimentação", "R$ 0,00")
                CategoriaItem("🚗", "Transporte", "R$ 0,00")
                CategoriaItem("⌂", "Casa", "R$ 0,00")
                CategoriaItem("◇", "Lazer", "R$ 0,00")
            }
        }
    }
}

@Composable
private fun ResumoCard(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier.Companion
) {
    Card(
        modifier = modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = CartaoHome
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(titulo, color = TextoHome, fontSize = 13.sp)
            Spacer(Modifier.height(8.dp))
            Text(
                valor,
                color = TextoHome,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun Atalho(
    simbolo: String,
    titulo: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Surface(
            shape = androidx.compose.foundation.shape.RoundedCornerShape(50),
            color = AmareloHome,
            modifier = Modifier.size(54.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    simbolo,
                    color = Color.Black,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(Modifier.height(6.dp))

        Text(titulo, color = TextoHome, fontSize = 12.sp)
    }
}

@Composable
private fun CategoriaItem(
    simbolo: String,
    titulo: String,
    valor: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(simbolo, fontSize = 21.sp)

        Spacer(Modifier.width(12.dp))

        Text(
            titulo,
            color = TextoHome,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp
        )

        Text(
            valor,
            color = AmareloHome,
            fontSize = 13.sp
        )
    }
}

@Composable
private fun MenuItem(
    simbolo: String,
    titulo: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
    ) {
        Text(simbolo, color = Color.White, fontSize = 23.sp)
        Text(titulo, color = Color.White, fontSize = 10.sp)
    }

}
