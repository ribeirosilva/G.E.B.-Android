package com.example.geb

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

object AndroidUtils {
    // verifica se tem algum tipo de conexão disponível
    fun isInternetDisponivel(context: Context): Boolean {
        val conexao = context.getSystemService(Context.CONNECTIVITY_SERVICE)  as ConnectivityManager

        val redes = conexao.allNetworks
        return redes.map{conexao.getNetworkInfo(it)}.any{it.state == NetworkInfo.State.CONNECTED}
    }
}