package com.example.parchis1

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

                val game = Game(gameCode, player1, null, playerUID, false, false, null)
                gameReference?.child(gameCode)?.setValue(game)?.addOnSuccessListener{
                    Toast.makeText(this, R.string.code_message_creation_ok, Toast.LENGTH_LONG)
                }?.addOnFailureListener{
                    Toast.makeText(this, R.string.code_message_creation_fail, Toast.LENGTH_LONG)
                }

                Log.d(TAG, "Finaliza")
            }
        }
    }
}