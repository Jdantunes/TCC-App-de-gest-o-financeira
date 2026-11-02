package com.example.tccfinanceiro

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gestaofinanceira.screens.HomeScreen
import com.example.tccfinanceiro.MetasScreen
// Importe as outras telas conforme for criando:
// import com.example.gestaofinanceira.screens.CadastroScreen
// import com.example.gestaofinanceira.screens.Loginscreen
// import com.example.gestaofinanceira.screens.TransacoesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Tela de login
        composable("login") {
            Loginscreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onCadastroClick = {
                    navController.navigate("cadastro")
                }
            )
        }

        // Tela de cadastro
        composable("cadastro") {
            CadastroScreen(
                onVoltarClick = {
                    navController.popBackStack()
                },
                onCadastroConcluido = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Tela inicial (Home) com todos os links corretos dentro da chave
        composable("home") {
            HomeScreen(
                onMetasClick = {
                    navController.navigate("metas")
                },
                onTransacoesClick = {
                    navController.navigate("transacoes")
                },
                onAdicionarClick = {
                    navController.navigate("adicionar")
                },
                onHistoricoClick = {
                    navController.navigate("historico")
                },
                onConfiguracaoClick = {
                    navController.navigate("configuracao")
                }
            )
        }

        // Tela de Metas (agora dentro do NavHost corretamente)
        composable(route = "metas") {
            MetasScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        // Exemplo para as próximas telas que ainda vai criar:
        composable(route = "transacoes") {
            // TransacoesScreen(onVoltarClick = { navController.popBackStack() })
        }

        composable(route = "adicionar") {
            // AdicionarScreen(onVoltarClick = { navController.popBackStack() })
        }

        composable(route = "historico") {
            // HistoricoScreen(onVoltarClick = { navController.popBackStack() })
        }

        composable(route = "configuracao") {
            // ConfiguracaoScreen(onVoltarClick = { navController.popBackStack() })

        }
    } // Fim do NavHost correto
}