package com.example.geb

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.*
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import kotlinx.android.synthetic.main.toolbar.*

class TelaInicialActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val context: Context get() = this

    private var disciplinas = listOf<Disciplina>()
    private var REQUEST_CADASTRO = 1
    private var REQUEST_REMOVE= 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        val args:Bundle? = intent.extras

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Disciplinas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configuraMenuLateral()

        // configurar cardview
        recyclerDisciplinas?.layoutManager = LinearLayoutManager(context)
        recyclerDisciplinas?.itemAnimator = DefaultItemAnimator()
        recyclerDisciplinas?.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        // task para recuperar as disciplinas
        taskDisciplinas()
    }

    fun taskDisciplinas() {
        // Criar a Thread

        Thread {
            // Código para procurar as disciplinas
            // que será executado em segundo plano / Thread separada
            this.disciplinas = DisciplinaService.getDisciplinas(context)
            runOnUiThread {
                // Código para atualizar a UI com a lista de disciplinas
                recyclerDisciplinas?.adapter = DisciplinaAdapter(this.disciplinas) { onClickDisciplina(it) }
            }
        }.start()

    }

    // tratamento do evento de clicar em uma disciplina
    fun onClickDisciplina(disciplina: Disciplina) {
        Toast.makeText(context, "Clicou disciplina ${disciplina.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, DisciplinaActivity::class.java)
        intent.putExtra("disciplina", disciplina)
        startActivityForResult(intent, REQUEST_REMOVE)
    }

    // configuraçao do navigation Drawer com a toolbar
    private fun configuraMenuLateral() {
        // ícone de menu (hamburger) para mostrar o menu
        var toogle = ActionBarDrawerToggle(this, layoutMenuLateral, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        layoutMenuLateral.addDrawerListener(toogle)
        toogle.syncState()

        menu_lateral.setNavigationItemSelectedListener(this)
    }

    // método que deve ser implementado quando a activity implementa a interface NavigationView.OnNavigationItemSelectedListener
    // para tratar os eventos de clique no menu lateral
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_diciplinas -> {
                Toast.makeText(this, "Clicou em Disciplinas", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_mensagens -> {
                Toast.makeText(this, "Clicou em Mensagens", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_forum -> {
                Toast.makeText(this, "Clicou em Forum", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_localizacao -> {
                Toast.makeText(this, "Clicou em Localização", Toast.LENGTH_SHORT).show()
            }

            R.id.nav_config -> {
                Toast.makeText(this, "Clicou em Config", Toast.LENGTH_SHORT).show()
            }
        }

        // fecha menu depois de tratar o evento
        layoutMenuLateral.closeDrawer(GravityCompat.START)
        return true
    }

    fun cliqueSair() {
        val returnIntent = Intent();
        returnIntent.putExtra("result","Saída do BrewerApp");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        (menu?.findItem(R.id.action_buscar)?.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
       val id = item?.itemId

        if  (id == R.id.action_buscar) {
            Toast.makeText(context, "Clicou em buscar", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_atualizar) {
            Toast.makeText(context, "Clicou em atualizar", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_config) {
            Toast.makeText(context, "Clicou em configurações", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_adicionar) {
            // iniciar activity de cadastro
            val intent = Intent(context, DisciplinaCadastroActivity::class.java)
            startActivityForResult(intent, REQUEST_CADASTRO)
        // botão up navigation
        } else if (id == R.id.action_sair) {
            finishAffinity()
        }else if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    // esperar o retorno do cadastro da disciplina
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CADASTRO || requestCode == REQUEST_REMOVE ) {
            // atualizar lista de disciplinas
            taskDisciplinas()
        }
    }


}
