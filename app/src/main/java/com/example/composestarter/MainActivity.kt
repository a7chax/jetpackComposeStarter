package com.example.composestarter

import android.content.res.Configuration
import android.os.Bundle
import android.os.Message
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composestarter.ui.theme.ComposeStarterTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ComposeStarterTheme {
        // A surface container using the 'background' color from the theme
            Conversation(messages = MessageData.conversationSample)
//          MessageCard(MessageItem("Android", "Jetpack Compose"))
      }
    }
  }
}

@Composable
fun MessageCard(msg: MessageItem) {
  Row(modifier = Modifier.padding(all = 8.dp)) {
    Image(
      painter = painterResource(R.drawable.profile_picture),
      contentDescription = null,
      modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
        .border(1.5.dp, MaterialTheme.colors.secondaryVariant, CircleShape)
    )
    Spacer(modifier = Modifier.width(8.dp))

    // We keep track if the message is expanded or not in this
    // variable

    // We toggle the isExpanded variable when we click on this Column
    var isExpanded by remember {
      mutableStateOf(false)
    }

    val surfaceColor : Color by animateColorAsState(targetValue =
      if(isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    )

    Column(
      modifier = Modifier.clickable { isExpanded = !isExpanded }) {
      Text(
        text = msg.author,
        color = MaterialTheme.colors.secondaryVariant,
        style = MaterialTheme.typography.subtitle2
      )

      Spacer(modifier = Modifier.height(4.dp))

      Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 1.dp,
      ) {
        Text(
          text = msg.body,
          modifier = Modifier.padding(all = 4.dp),
          // If the message is expanded, we display all its content
          // otherwise we only display the first line
          maxLines = if (isExpanded) Int.MAX_VALUE else 1,
          style = MaterialTheme.typography.body2
        )
      }
    }
  }
}

@Composable
fun Conversation(messages: List<MessageItem>) {
  LazyColumn {
    items(messages) { message ->
      MessageCard(message)
    }
  }
}




@Preview(
  showBackground = true,
  name = "Dark Mode",
  uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DefaultPreview() {
  ComposeStarterTheme {
    Conversation(MessageData.conversationSample)
  }
}

