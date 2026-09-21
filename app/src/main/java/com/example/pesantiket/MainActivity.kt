package com.example.pesantiket
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TicketScreen()
                }
            }
        }
    }
}
@Composable
fun TicketScreen() {

    var jumlahTiket by rememberSaveable {
        mutableStateOf(1)
    }

    val hargaTiket = 50000
    val totalBayar = hargaTiket * jumlahTiket

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { paddingValues ->

        TicketContent(
            hargaTiket = hargaTiket,
            jumlahTiket = jumlahTiket,
            totalBayar = totalBayar,

            onJumlahTiketChange = { jumlahBaru ->

                jumlahTiket = jumlahBaru

                scope.launch {
                    snackbarHostState.showSnackbar(
                        "Jumlah tiket: $jumlahBaru"
                    )
                }
            },

            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun TicketContent(
    hargaTiket: Int,
    jumlahTiket: Int,
    totalBayar: Int,
    onJumlahTiketChange: (Int) -> Unit,
    modifier: Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Pembelian Tiket",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "Harga Tiket",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = hargaTiket.formatRupiah(),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "Jumlah Tiket",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    if (jumlahTiket > 1) {
                        onJumlahTiketChange(jumlahTiket - 1)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD35EAF)
                )
            ) {
                Text(text = "-")
            }

            Text(
                text = jumlahTiket.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    horizontal = 24.dp
                )
            )

            Button(
                onClick = {
                    onJumlahTiketChange(jumlahTiket + 1)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF67B035)
                )
            ) {
                Text(text = "+")
            }
        }

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            text = "Total Bayar",
            fontSize = 18.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = totalBayar.formatRupiah(),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun Int.formatRupiah(): String {
    return "Rp%,d".format(this).replace(',', '.')
}