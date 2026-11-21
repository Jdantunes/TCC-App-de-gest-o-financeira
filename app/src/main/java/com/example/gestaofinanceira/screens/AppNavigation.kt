
package com.example.gestaofinanceira.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.tccfinanceiro.CadastroScreen
import com.example.tccfinanceiro.Loginscreen
import com.example.tccfinanceiro.MetasScreen
import com.example.tccfinanceiro.LancamentoScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
        composable("login") {
            Loginscreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },
                onCadastroClick = {
                    navController.navigate("cadastro")
                }
            )
        }

        // CADASTRO
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

        // HOME
        composable("home") {
            HomeScreen(
                onMetasClick = {
                    navController.navigate("metas")
                },
                onAdicionarClick = {
                    navController.navigate("lancamento")
                },
                onTransacoesClick = {
                    navController.navigate("transacoes")
                },
                onHistoricoClick = {
                    navController.navigate("historico")
                },
                onConfiguracaoClick = {
                    navController.navigate("configuracao")
                }
            )
        }

        // METAS
        composable("metas") {
            MetasScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        // LANÇAMENTO
        composable("lancamento") {
            LancamentoScreen(
                onVoltarClick = {
                    navController.popBackStack()
                },
                onSalvarClick = { lancamento ->
                    navController.popBackStack()
                }
            )
        }

        // TRANSAÇÕES
        composable("transacoes") {
            TransacoesScreen(
                onNovaTransacaoClick = {
                    navController.navigate("novaTransacao")
                }
            )
        }

        // NOVA TRANSAÇÃO
        composable("novaTransacao") {
            NovaTransacaoScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        // HISTÓRICO
        composable("historico") {
            HistoricoScreen(
                onVoltarClick = {
                    navController.popBackStack()
                }
            )
        }

        // CONFIGURAÇÕES
        composable("configuracao") {
            ConfiguracaoScreen (onVoltarClick = {
                navController.popBackStack()
            }

            )

            }
        }
    }

