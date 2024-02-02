package com.example.photoeditapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.photoeditapp.ui.theme.PhotoEditAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoEditAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SelectImage()
                }
            }
        }
    }
}

@Composable
fun SelectImage() {
    val ctx = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            imageUri.value = uri

            if (uri != null) {
                launchHandlingActivity(ctx, uri)
            }

        })

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success && imageUri.value != null) {

                launchHandlingActivity(ctx, imageUri.value!!)
            }
        }
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Select an image")
        Row {
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .width(128.dp)
                    .height(128.dp)
                    .clip(CircleShape),
                onClick = { imagePicker.launch("image/*") }) {
                Text(text = "Picker")
            }
            Button(
                modifier = Modifier
                    .padding(8.dp)
                    .width(128.dp)
                    .height(128.dp)
                    .clip(CircleShape),
                onClick = {
                    val uri = ComposeFileProvider.getImageUri(ctx)
                    imageUri.value = uri
                    cameraLauncher.launch(uri)
                }) {
                Text(text = "Camera")
            }
        }
    }
}

fun launchHandlingActivity(ctx: Context, uri: Uri) {
    val intent = Intent(ctx, EditActivity::class.java)
    intent.putExtra("imageUri", uri.toString())
    ctx.startActivity(intent)
}