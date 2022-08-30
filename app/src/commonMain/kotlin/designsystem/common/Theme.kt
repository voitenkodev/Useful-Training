package designsystem.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.ExperimentalUnitApi
import designsystem.LocalAppColors
import designsystem.LocalAppTypography

@ExperimentalUnitApi
@Composable
fun DesignTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) = CompositionLocalProvider(
    LocalAppColors provides when (darkTheme) {
        true -> DarkPalette()
        false -> LightPalette()
    },
    LocalAppTypography provides AppTypography(),
    content = content
)
