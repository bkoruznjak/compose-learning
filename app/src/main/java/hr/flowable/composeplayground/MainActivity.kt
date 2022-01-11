package hr.flowable.composeplayground

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.unit.ExperimentalUnitApi
import hr.flowable.composeplayground.compose.AnimatedLists
import hr.flowable.composeplayground.compose.HorizontalRowPicker

class MainActivity : AppCompatActivity() {

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HorizontalRowPicker()
        }
    }
}
