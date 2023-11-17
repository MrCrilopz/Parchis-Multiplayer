package com.example.parchis1

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.parchis1.databinding.ActivityCrearPartidaBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
class CrearPartidaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearPartidaBinding
    private var gameRef: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearPartidaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        gameRef = FirebaseDatabase.getInstance().getReference("games")
        var gameCode = binding.txtCodeGame.text.toString()

        var auth = FirebaseAuth.getInstance()

        var currentUser = auth.currentUser
        var playerName = ""
        var playerUID = ""
        if (currentUser != null) {
            playerName = currentUser.displayName.toString()
            playerUID = currentUser.uid.toString()
        }
        binding.btnCrear.setOnClickListener {
            if (currentUser != null && gameCode != null){
                Log.d("Prueba", "Entrando a guardar datos")

                var player1 = Jugador(playerName, playerUID)

                val game = Juego(gameCode, player1, null, playerUID)
                gameRef?.child(gameCode)?.setValue(game)?.addOnSuccessListener{
                    Toast.makeText(this, "Creacion ok, todo nice", Toast.LENGTH_LONG)
                }?.addOnFailureListener{
                    Toast.makeText(this, "Creacion falla, todo bad", Toast.LENGTH_LONG)
                }

                Log.d("Creacion juego", "Finaliza")
            }
        }

        binding.btnUnirse.setOnClickListener {
            var game = gameRef?.child(gameCode) as Juego

            game.jugador2 = Jugador(playerName, playerUID)
            //game.isStart = true

            gameRef?.child(gameCode)?.setValue(game)?.addOnSuccessListener{
                goToBoard()
            }?.addOnFailureListener{
                // message fail
            }
        }
    }
    fun goToBoard(){
        val intent = Intent(this, ActivityTablero::class.java)
        startActivity(intent)
    }
}