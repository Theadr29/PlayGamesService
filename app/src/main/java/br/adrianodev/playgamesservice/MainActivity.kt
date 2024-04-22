package br.adrianodev.playgamesservice

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.adrianodev.playgamesservice.ui.theme.PlayGamesServiceTheme
import com.google.android.gms.common.images.ImageManager
import com.google.android.gms.games.AuthenticationResult
import com.google.android.gms.games.PlayGames
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayGamesServiceTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),

                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        var playerIconBitmap by remember { mutableStateOf<Bitmap?>(null) }
                        var playerUsername by remember { mutableStateOf<String?>(null) }
                        var playerPlayerId by remember { mutableStateOf<String?>(null) }

                        var isLoading by mutableStateOf(false)
                        val gamesSignInClient = PlayGames.getGamesSignInClient(Activity())
                        val playersClient = PlayGames.getPlayersClient(Activity())
                        val context = LocalContext.current
                        gamesSignInClient.isAuthenticated()
                            .addOnCompleteListener { isAuthenticatedTask: Task<AuthenticationResult> ->
                                if (isAuthenticatedTask.isSuccessful) {
                                    val isAuthenticated = isAuthenticatedTask.result.isAuthenticated
                                    if (isAuthenticated) {
                                        playersClient.getCurrentPlayer()
                                            .addOnCompleteListener { playerTask ->
                                                if (playerTask.isSuccessful) {
                                                    // É imprescindivel que sequer a tentativa de usar os dados do usuario só comecem
                                                    // apenas se a task for concluida (ou ser feita com sucesso como no caso)
                                                    val player = playerTask.result
                                                    val username = player.displayName
                                                    val playerId = player.playerId
                                                    val iconUri = player?.iconImageUri

                                                    iconUri?.let { uri ->
                                                        val imageManager =
                                                            ImageManager.create(context)
                                                        imageManager.loadImage(
                                                            object :
                                                                ImageManager.OnImageLoadedListener {
                                                                override fun onImageLoaded(
                                                                    uri: Uri,
                                                                    drawable: Drawable?,
                                                                    loaded: Boolean
                                                                ) {
                                                                    if (loaded) {
                                                                        val bitmap =
                                                                            (drawable as BitmapDrawable).bitmap

                                                                        playerIconBitmap = bitmap
                                                                        playerUsername = username
                                                                        playerPlayerId = playerId
                                                                    }
                                                                }
                                                            },
                                                            uri
                                                        )

                                                    }
                                                } else {
                                                    // Lidar com falha ao obter o jogador
                                                    val exception = playerTask.exception
                                                    Log.e(
                                                        "PlayGames",
                                                        "Erro ao obter o jogador: ${exception?.message}"
                                                    )
                                                }
                                                isLoading = playerTask.isSuccessful

                                            }
                                    } else {

                                    }
                                } else {
                                    // Lidar com falha ao verificar a autenticação
                                    val exception = isAuthenticatedTask.exception
                                    Log.e(
                                        "PlayGames",
                                        "Erro ao verificar a autenticação: ${exception?.message}"
                                    )
                                }
                            }

                        LaunchedEffect(Unit) {
                            delay(7000)
                            isLoading = false
                        }

                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(medida_por_largura_da_tela() / 5),
                                color = Color.Green,
                                strokeWidth = medida_por_largura_da_tela() / 50 // Ajuste o valor conforme necessário
                            )
                        } else {
                            if (playerIconBitmap != null && playerUsername != null) {

                                Image(
                                    bitmap = playerIconBitmap!!.asImageBitmap(),
                                    contentDescription = "perfil",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(medida_por_largura_da_tela() / 2f)
                                        .clip(RoundedCornerShape(medida_por_largura_da_tela() / 4))
                                        .border(
                                            width = 3.dp,
                                            color = Color.Yellow,
                                            shape = RoundedCornerShape(medida_por_largura_da_tela() / 4)
                                        )

                                )

                                Spacer(modifier = Modifier.padding(medida_por_altura_da_tela() / 65))

                                Text(
                                    playerUsername.toString(),
                                    fontSize = fonte_por_largura_da_tela() / 8
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun medida_por_largura_da_tela() = LocalConfiguration.current.screenWidthDp.dp

@Composable
fun medida_por_altura_da_tela() = LocalConfiguration.current.screenHeightDp.dp

@Composable
fun fonte_por_largura_da_tela() =
    with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toSp() } //CONVERTENDO DP