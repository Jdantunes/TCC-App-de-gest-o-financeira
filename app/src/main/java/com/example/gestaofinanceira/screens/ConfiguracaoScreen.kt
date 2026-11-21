package com.example.gestaofinanceira.screens



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConfiguracaoScreen(
    onVoltarClick: () -> Unit = {},
    onSairClick: () -> Unit = {},
    onAlterarSenhaClick: () -> Unit = {},
    onPrivacidadeClick: () -> Unit = {}
) {
    val amarelo = Color(0xFFFFC107)
    val preto = Color(0xFF111111)
    val branco = Color.White
    val cinza = Color(0xFF777777)

    var notificacoesAtivas by remember { mutableStateOf(true) }
    var temaSelecionado by remember { mutableStateOf("Escuro") }
    var mostrarSobre by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(preto)
            .padding(20.dp)
    ) {

        // CABEÇALHO
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onVoltarClick) {
                Text("← Voltar", color = amarelo)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Configurações",
                color = branco,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // CONTA
        Text(
            text = "CONTA",
            color = amarelo,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        ConfigItem(
            titulo = "Meu perfil",
            descricao = "Visualizar e editar seus dados"
        ) {
            // Edição de perfil será implementada posteriormente
        }

        ConfigItem(
            titulo = "Alterar senha",
            descricao = "Atualize sua senha de acesso"
        ) {
            onAlterarSenhaClick()
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PREFERÊNCIAS
        Text(
            text = "PREFERÊNCIAS",
            color = amarelo,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF222222)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    text = "Tema do aplicativo",
                    color = branco,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("Escuro", "Claro").forEach { tema ->
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    temaSelecionado = tema
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = temaSelecionado == tema,
                                onClick = {
                                    temaSelecionado = tema
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = amarelo,
                                    unselectedColor = cinza
                                )
                            )

                            Text(
                                text = tema,
                                color = branco
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Notificações",
                        color = branco,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Receber avisos do aplicativo",
                        color = cinza,
                        fontSize = 12.sp
                    )
                }

                Switch(
                    checked = notificacoesAtivas,
                    onCheckedChange = {
                        notificacoesAtivas = it
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = preto,
                        checkedTrackColor = amarelo
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // PRIVACIDADE E INFORMAÇÕES
        Text(
            text = "INFORMAÇÕES",
            color = amarelo,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        ConfigItem(
            titulo = "Privacidade e segurança",
            descricao = "Saiba como seus dados são protegidos"
        ) {
            onPrivacidadeClick()
        }

        ConfigItem(
            titulo = "Sobre o aplicativo",
            descricao = "Informações sobre o Gestão Financeira"
        ) {
            mostrarSobre = true
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SAIR
        Button(
            onClick = onSairClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB3261E)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "SAIR DA CONTA",
                color = branco,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Gestão Financeira • Versão 1.0",
            color = cinza,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }

    if (mostrarSobre) {
        AlertDialog(
            onDismissRequest = { mostrarSobre = false },
            containerColor = Color(0xFF222222),
            title = {
                Text("Sobre o aplicativo", color = branco)
            },
            text = {
                Text(
                    "Gestão Financeira é um aplicativo desenvolvido para auxiliar no controle de receitas, despesas e metas financeiras pessoais.",
                    color = cinza
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { mostrarSobre = false }
                ) {
                    Text("Fechar", color = amarelo)
                }
            }
        )
    }
}

@Composable
private fun ConfigItem(
    titulo: String,
    descricao: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF222222)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = descricao,
                color = Color(0xFF999999),
                fontSize = 12.sp
            )
        }
    }
}
