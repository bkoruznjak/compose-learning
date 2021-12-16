package hr.flowable.composeplayground.compose

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun ComposableConstrainedButtonWithText(){
    
    ConstraintLayout {
        
        val (button, text) = createRefs()

            
        Button(onClick = { Log.d("žžž","something")}) {

        }
                
    }
    
    
}
