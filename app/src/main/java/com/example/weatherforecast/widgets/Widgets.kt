import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.weatherforecast.R
import com.example.weatherforecast.model.WeatherItem
import com.example.weatherforecast.utils.formatDate
import com.example.weatherforecast.utils.formatDateTime
import com.example.weatherforecast.utils.formatDecimals

@Composable
fun WeatherDetailRow(weatherItem: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Surface(modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colors.surface){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = formatDate(weatherItem.dt).split(",")[0],
                modifier = Modifier.padding(start = 5.dp), style = MaterialTheme.typography.subtitle1)

            WeatherStateImage(imageUrl = imageUrl)
            Surface(shape = CircleShape,
                color =  Color(0xFFFFC400)
            ) {
                Text(text = weatherItem.weather[0].description,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)
            }

            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold)
                ){
                    append(formatDecimals(weatherItem.temp.max) + "◦")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray,
                    //fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weatherItem.temp.min) + "◦")
                }
            })

        }
    }
}


@Composable
fun HumidityWindPressureRow(weather: WeatherItem,isImperial: Boolean) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon", modifier = Modifier.size(20.dp))

            Text(text = "${weather.humidity}%",
                style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon", modifier = Modifier.size(20.dp))

            Text(text = "${weather.pressure} psi",
                style = MaterialTheme.typography.caption)
        }
        Row(modifier = Modifier.padding(4.dp)){
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon", modifier = Modifier.size(20.dp))

            Text(text =  "${formatDecimals(weather.speed)} " + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption)
        }
    }
}
@Composable
fun SunriseSunsetRow(weather: WeatherItem){
    Row(modifier = Modifier
        .padding(top = 10.dp, bottom = 10.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Row(modifier = Modifier.padding(4.dp)){
            Icon(
                painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(20.dp))
            Text(text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(4.dp)){
            Icon(
                painterResource(id = R.drawable.sunset),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(20.dp))
            Text(text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.caption)
        }
    }
}
@Composable
fun WeatherStateImage(imageUrl:String){
    Image(painter = rememberImagePainter(imageUrl),
        contentDescription = "icon image",
        modifier = Modifier.size(80.dp))
}