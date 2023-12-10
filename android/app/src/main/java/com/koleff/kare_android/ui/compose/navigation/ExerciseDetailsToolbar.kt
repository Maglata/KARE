import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.koleff.kare_android.R
import com.koleff.kare_android.data.MainScreen
import com.koleff.kare_android.ui.compose.navigation.NavigationItem


@Composable
fun ExerciseDetailsToolbar(
    modifier: Modifier,
    navController: NavHostController,
    isNavigationInProgress: MutableState<Boolean>,
    exerciseImageId: Int
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val primaryContainerColor = MaterialTheme.colorScheme.primaryContainer

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    Box(
        modifier = modifier
    ) {
        //Exercise Muscle Group Image
        Image(
            painter = painterResource(id = exerciseImageId),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2.5f)
//                .graphicsLayer { alpha = 0.80f } //Configure gradient over image...
//                .drawWithContent {
//
//                    val colors = listOf(
//                        primaryContainerColor,
//                        primaryContainerColor,
//                        primaryColor,
//                        primaryColor,
//                        Color.Transparent,
//                        Color.Transparent
//                    )
//                    drawContent()
//                    drawRect(
//                        brush = Brush.verticalGradient(colors),
//                        blendMode = BlendMode.Overlay
//                    )
//                }
                .clip(RoundedToolbarShape())
        )

        //Navigation
        Row(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .drawBehind {

                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NavigationItem(
//                modifier = Modifier.drawBehind {
//                    drawRect(
//                        color = primaryContainerColor
//                    )
//                },
                navController = navController,
                screen = null, //Backstack pop
                icon = Icons.Filled.ArrowBack,
                label = "Go back",
                isBlocked = isNavigationInProgress,
                tint = Color.White
            )

            NavigationItem(
//                modifier = Modifier.drawBehind {
//                    drawRect(
//                        color = primaryContainerColor
//                    )
//                },
                navController = navController,
                screen = MainScreen.Settings,
                icon = Icons.Filled.Settings,
                label = "Settings",
                isBlocked = isNavigationInProgress,
                tint = Color.White
            )
        }
    }
}

class RoundedToolbarShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawRoundedToolbarSquare(size)
        )
    }

    private fun drawRoundedToolbarSquare(size: Size): Path {
        val screenWidth = size.width
        val screenHeight = size.height
        val cornerRadius = screenWidth / 3

        val rect = Rect(
            left = screenWidth - 2 * cornerRadius,
            top = screenHeight - 2 * cornerRadius,
            right = screenWidth,
            bottom = screenHeight
        )

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(screenWidth, 0f)
            lineTo(screenWidth, screenHeight - cornerRadius)
            arcTo(
                rect = rect,
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(cornerRadius, screenHeight)
            arcTo(
                rect = Rect(
                    left = 0f,
                    top = screenHeight - 2 * cornerRadius,
                    right = cornerRadius * 2,
                    bottom = screenHeight
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            close()
        }

        return path
    }
}


@Preview
@Composable
fun PreviewRoundedSquare() {
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    ExerciseDetailsToolbar(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 2.5f)
            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        MaterialTheme.colorScheme.primaryContainer,
//                        MaterialTheme.colorScheme.primaryContainer,
//                        MaterialTheme.colorScheme.primaryContainer,
//                        MaterialTheme.colorScheme.primary,
//                        MaterialTheme.colorScheme.primary
//                    )
//                )
                color = MaterialTheme.colorScheme.primaryContainer
            ),
        isNavigationInProgress = mutableStateOf(false),
        navController = navController,
        exerciseImageId = R.drawable.ic_legs
    )
}




