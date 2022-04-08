package com.technokratos.compose.localization.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.technokratos.compose.localization.app.ui.stringFromOverride
import com.technokratos.compose.localization.locales.target
import com.technokratos.localization.compose.Localization
import com.technokratos.localization.compose.Vocabulary
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Localization(locale = Locale.ENGLISH) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = Vocabulary.localization.target())
                    Spacer(modifier = Modifier.requiredHeight(8.dp))
                    Text(text = Vocabulary.localization.stringFromOverride())
                }
            }
        }
    }
}