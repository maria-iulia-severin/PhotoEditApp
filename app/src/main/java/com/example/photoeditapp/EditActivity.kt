package com.example.photoeditapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import com.example.photoeditapp.ui.theme.PhotoEditAppTheme

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoEditAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    var uriString: String? = null
                    uriString = intent.extras?.getString("imageUri")
                    if (uriString == null) {
                        uriString = intent.extras?.get(Intent.EXTRA_STREAM).toString()
                    } else {
                        Text(text = "no data")
                    }

                    val uri = Uri.parse(uriString)

                    EditImage(uri = uri)
                }
            }
        }
    }
}

@Composable
fun EditImage(uri: Uri) {
    val painter = rememberAsyncImagePainter(model = uri)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painter, contentDescription = null)
    }
}
