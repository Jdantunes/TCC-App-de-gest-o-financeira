package com.example.tccfinanceiro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
private val FundoMetas = Color(0xFF111111)
private val AmareloMetas = Color(0xFFFFC107)
private val BrancoMetas = Color(0xFFFFFFFF)
private val CinzaMetas = Color(0xFF777777)
private val CartaoMetas = Color(0xFF202020)

data class MetaFinanceira(
    val nome: String,
    val valorObjetivo: Double,
    val valorGuardado: Double
)

@Composable
fun MetasScreen(
    onVoltarClick: () -> Unit = {}
) {
    var mostrarCadastro by remember { mutableStateOf(false) }
    var nomeMeta by remember { mutableStateOf("") }
    var valorObjetivo by remember { mutableStateOf("") }
    var valorGuardado by remember { mutableStateOf("") }

    var metas by remember {
        mutableStateOf(
            listOf(
                MetaFinanceira("Comprar uma moto", 15000.0, 4500.0),
                MetaFinanceira("Reserva de emergência", 5000.0, 1200.0),
                MetaFinanceira("Viagem", 2000.0, 600.0)
            )
        )
    }

    val totalObjetivos = metas.sumOf { it.valorObjetivo }
    val totalGuardado = metas.sumOf { it.valorGuardado }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(FundoMetas)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        IconButton(
            onClick = onVoltarClick,
            modifier = Modifier.offset(x = (-15).dp)

        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Voltar",
                tint = BrancoMetas
            )

        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Minhas metas",
            color = BrancoMetas,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Organize seus objetivos financeiros",
            color = CinzaMetas,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = AmareloMetas
            )
        ) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    "Total já guardado",
                    color = FundoMetas,
                    fontSize = 14.sp
                )

                Text(
                    "R$ ${"%.2f".format(totalGuardado)}",
                    color = FundoMetas,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Objetivo total: R$ ${"%.2f".format(totalObjetivos)}",
                    color = FundoMetas,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { mostrarCadastro = !mostrarCadastro },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AmareloMetas
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                if (mostrarCadastro) "Cancelar" else "+ Criar nova meta",
                color = FundoMetas,
                fontWeight = FontWeight.Bold
            )
        }

        if (mostrarCadastro) {
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = nomeMeta,
                onValueChange = { nomeMeta = it },
                label = { Text("Nome da meta") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = campoMetasColors()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = valorObjetivo,
                onValueChange = { valorObjetivo = it },
                label = { Text("Valor desejado (R$)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = campoMetasColors()
            )

            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = valorGuardado,
                onValueChange = { valorGuardado = it },
                label = { Text("Quanto já guardou (R$)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = campoMetasColors()
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    val objetivo = valorObjetivo
                        .trim().replace(",", ".").toDoubleOrNull()
                    val guardado = valorGuardado
                        .trim().replace(",", ".").toDoubleOrNull()

                    if (
                        nomeMeta.isNotBlank() &&
                        objetivo != null &&
                        objetivo > 0 &&
                        guardado != null &&
                        guardado >= 0 &&
                        guardado <= objetivo
                    ) {
                        metas = metas + MetaFinanceira(
                            nomeMeta.trim(),
                            objetivo,
                            guardado
                        )
                        nomeMeta = ""
                        valorObjetivo = ""
                        valorGuardado = ""
                        mostrarCadastro = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AmareloMetas
                )
            ) {
                Text("Salvar meta", color = FundoMetas)
            }
        }

        Spacer(Modifier.height(28.dp))

        Text(
            "Suas metas",
            color = BrancoMetas,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(14.dp))

        metas.forEach { meta ->
            val progresso = (
                    meta.valorGuardado / meta.valorObjetivo
                    ).toFloat().coerceIn(0f, 1f)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CartaoMetas
                )
            ) {
                Column(Modifier.padding(18.dp)) {
                    Text(
                        meta.nome,
                        color = BrancoMetas,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { progresso },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = AmareloMetas,
                        trackColor = CinzaMetas,
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "R$ ${"%.2f".format(meta.valorGuardado)}",
                            color = AmareloMetas,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            "${(progresso * 100).toInt()}%",
                            color = BrancoMetas
                        )
                    }

                    Text(
                        "Meta: R$ ${"%.2f".format(meta.valorObjetivo)}",
                        color = CinzaMetas,
                        fontSize = 13.sp
                    )

                    // Cálculo corrigido com o sinal de menos (-)
                    Text(
                        "Faltam: R$ ${"%.2f".format(meta.valorObjetivo - meta.valorGuardado)}",
                        color = BrancoMetas,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

private fun carregarMetas(context: Context): List<MetaFinanceira> {
    val preferencias = context.getSharedPreferences(
        "metas_financeiras",
        Context.MODE_PRIVATE
    )

    val json = preferencias.getString("lista_metas", "[]")
        ?: "[]"

    return try {
        val array = JSONArray(json)

        List(array.length()) { i ->
            val item = array.getJSONObject(i)

            MetaFinanceira(
                nome = item.getString("nome"),
                valorObjetivo = item.getDouble("objetivo"),
                valorGuardado = item.getDouble("guardado")
            )
        }
    } catch (e: Exception) {
        emptyList()
    }
}

private fun salvarMetas(
    context: Context,
    metas: List<MetaFinanceira>
) {
    val array = JSONArray()

    metas.forEach { meta ->
        val item = JSONObject().apply {
            put("nome", meta.nome)
            put("objetivo", meta.valorObjetivo)
            put("guardado", meta.valorGuardado)
        }

        array.put(item)
    }

    context.getSharedPreferences(
        "metas_financeiras",
        Context.MODE_PRIVATE
    ).edit()
        .putString("lista_metas", array.toString())
        .apply()
}
@Composable
private fun campoMetasColors() =
    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = AmareloMetas,
        unfocusedBorderColor = CinzaMetas,
        focusedLabelColor = AmareloMetas,
        unfocusedLabelColor = CinzaMetas,
        focusedTextColor = BrancoMetas,
        unfocusedTextColor = BrancoMetas,
        cursorColor = AmareloMetas
    )