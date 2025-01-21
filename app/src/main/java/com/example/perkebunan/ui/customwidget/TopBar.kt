import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.perkebunan.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onRefresh: () -> Unit,
    onNotificationClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xFF00A67E)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Selamat Pagi,",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "Abrar Imam Satria",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNotificationClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.notification),
                            contentDescription = "Notifications",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.man),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(55.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                color = Color(0xFF000000).copy(alpha = 0.26f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.location),
                                contentDescription = "Location",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Sleman, Yogyakarta",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        }

                        Text(
                            text = "Selasa 21 januari 2022",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "27°",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Hujan Ringan",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            IconButton(
                onClick = onRefresh,
                modifier = Modifier
                    .align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}