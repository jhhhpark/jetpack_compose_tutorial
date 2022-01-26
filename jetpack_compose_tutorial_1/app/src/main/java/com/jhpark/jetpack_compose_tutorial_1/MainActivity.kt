package com.jhpark.jetpack_compose_tutorial_1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.tutorial.SampleData
import com.jhpark.jetpack_compose_tutorial_1.ui.theme.Jetpack_compose_tutorial_1Theme

data class Message(val author: String, val body: String)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            MessageCard(msg = Message("Android", "Jetpack Compose"))
            Conversation(messages = SampleData.conversationSample)
        }
    }


    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) { message ->
                MessageCard(msg = message)
            }
        }
    }

    @Preview
    @Composable
    fun PreviewConversation() {
        Jetpack_compose_tutorial_1Theme {
            Conversation(messages = SampleData.conversationSample)
        }
    }

    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.no_image),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
//                     Clip image to be shaped as a circle
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )

            // Add a horizontal space between the image and column
            Spacer(modifier = Modifier.width(8.dp))

            // We keep track if the message is expanded or not in this
            // variable
            var isExpanded by remember { mutableStateOf(false) }

            // surfaceColor will be updated gradually from one color to the other
            val surfaceColor: Color by animateColorAsState(
                targetValue = if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
            )

            Column(modifier = Modifier.clickable { isExpanded = isExpanded.not() }) {
                Text(
                    text = msg.author,
                    color = MaterialTheme.colors.secondaryVariant
                )
                // Add a vertical space between the author and message
                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium, elevation = 1.dp,
                    color = surfaceColor,
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.animateContentSize().padding(1.dp),
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

    @Preview(name = "Light Mode")
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
    fun PreviewMessageCard() {
        Jetpack_compose_tutorial_1Theme {
            MessageCard(
                msg = Message("Colleague", "Hey, Take a Look at Jetpack Compose, It`s great!")
            )
        }
    }
}