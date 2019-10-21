package com.example.geb

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DebugActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart.setOnClickListener{
            val Slogin: String = inputUser.text.toString()
            val Spassword:String = inputPassword.text.toString()

            if (Slogin == "aluno" && Spassword == "impacta"){
                Toast.makeText(
                        this, "Bem vindo, $Slogin!",
                        Toast.LENGTH_SHORT).show()

                val intent: Intent = Intent(this, TelaInicialActivity::class.java)
                startActivity(intent)
            }
            else if(Slogin == "" && Spassword == ""){
                Toast.makeText(
                        this, "Campos em branco, Informe usuário e senha!",
                        Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(
                        this, "Usuário ou senha incorretos",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }
}