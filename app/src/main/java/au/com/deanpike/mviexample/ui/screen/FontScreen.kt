package au.com.deanpike.mviexample.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import au.com.deanpike.uishared.theme.Dimension.DIM_8
import au.com.deanpike.uishared.theme.MviExampleTheme

@Composable
fun FontScreen() {
    MviExampleTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(DIM_8)
        ) {
            Text("Display Large", style = MaterialTheme.typography.displayLarge)
            Text("Display Medium", style = MaterialTheme.typography.displayMedium)
            Text("Display Small", style = MaterialTheme.typography.displaySmall)
            HorizontalDivider(
                modifier = Modifier.padding(DIM_8)
            )

            Text("Headline Large", style = MaterialTheme.typography.headlineLarge)
            Text("Headline Medium", style = MaterialTheme.typography.headlineMedium)
            Text("Headline Small", style = MaterialTheme.typography.headlineSmall)
            HorizontalDivider(
                modifier = Modifier.padding(DIM_8)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Title Large", style = MaterialTheme.typography.titleLarge)
                Text("Title Large Bold", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Title Medium", style = MaterialTheme.typography.titleMedium)
                Text("Title Medium Bold", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Title Small", style = MaterialTheme.typography.titleSmall)
                Text("Title Small Bold", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider(
                modifier = Modifier.padding(DIM_8)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Body Large", style = MaterialTheme.typography.bodyLarge)
                Text("Body Large Bold", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Body Medium", style = MaterialTheme.typography.bodyMedium)
                Text("Body Medium Bold", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Body Small", style = MaterialTheme.typography.bodySmall)
                Text("Body Small Bold", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider(
                modifier = Modifier.padding(DIM_8)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Label Large", style = MaterialTheme.typography.labelLarge)
                Text("Label Large Bold", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Label Medium", style = MaterialTheme.typography.labelMedium)
                Text("Label Medium Bold", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Label Small", style = MaterialTheme.typography.labelSmall)
                Text("Label Small Bold", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FontScreenPreview() {
    FontScreen()
}