import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val factory = rememberPermissionsControllerFactory()
        val controller = remember(factory) {
            factory.createPermissionsController()
        }
        BindEffect(controller)

        val viewModel = viewModel {
            PermissionViewModel(controller)
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (viewModel.state) {
                PermissionState.Granted -> {
                    Text(
                        text = "Record audio permission granted!"
                    )
                }

                PermissionState.DeniedAlways -> {
                    Button(
                        onClick = {
                            controller.openAppSettings()
                        }
                    ) {
                        Text(
                            text = "Open app settings"
                        )
                    }
                    Text(
                        text = "Permission was permanently declined"
                    )
                }

                else -> {
                    Button(
                        onClick = {
                            viewModel.provideOrRequestRecordAudioPermission()
                        }
                    ) {
                        Text(
                            text = "Request permission"
                        )
                    }
                    Text(
                        text = "Permission was permanently declined"
                    )
                }
            }
        }
    }
}