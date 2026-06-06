package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
          bottomBar = { BottomNavigation() }
        ) { innerPadding ->
          HomeScreen(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
  ) {
    // Header
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(40.dp)
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
          ) {
            Icon(Icons.Filled.LocationOn, contentDescription = "Location", tint = Color.White)
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "DELIVERING TO",
              fontSize = 11.sp,
              fontWeight = FontWeight.Medium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              letterSpacing = 1.sp
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "JKUAT Main Gate, Juja",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
              )
              Icon(
                Icons.Filled.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
            }
          }
        }
        Box(
          modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color(0xFF818CF8)) // indigo-400 equivalent
            .border(2.dp, Color.White, CircleShape),
          contentAlignment = Alignment.Center
        ) {
          Text("JN", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
      }

      // Search Bar
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .clip(RoundedCornerShape(24.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
          .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(Icons.Outlined.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Search for Kibandas or Fast Food",
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 14.sp,
          modifier = Modifier.weight(1f)
        )
        Icon(Icons.Filled.Menu, contentDescription = "Tune", tint = MaterialTheme.colorScheme.onSurfaceVariant)
      }
    }

    // Categories
    LazyRow(
      modifier = Modifier.fillMaxWidth(),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        CategoryChip("Trending", Icons.Filled.Star, isSelected = true)
      }
      item {
        CategoryChip("Kibanda", Icons.Filled.ShoppingCart, isSelected = false) // Replaced Icons that missing
      }
      item {
        CategoryChip("Fast Food", Icons.Filled.ShoppingCart, isSelected = false)
      }
      item {
        CategoryChip("Breakfast", Icons.Filled.ShoppingCart, isSelected = false)
      }
    }

    // List
    LazyColumn(
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      item {
        FeaturedCard()
      }
      item {
        SecondaryCard("JKUAT Student Canteen", "Budget friendly meals for students.", "10 min", "M-Pesa OK")
      }
      item {
        SecondaryCard("Juja Highway Grill", "Fast food, burgers, and fries.", "20 min", null)
      }
    }
  }
}

@Composable
fun CategoryChip(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isSelected: Boolean) {
  Row(
    modifier = Modifier
      .clip(RoundedCornerShape(16.dp))
      .background(if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.White)
      .border(
        1.dp,
        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.outline,
        RoundedCornerShape(16.dp)
      )
      .padding(horizontal = 16.dp, vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      icon,
      contentDescription = null,
      tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.size(16.dp)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = label,
      fontSize = 14.sp,
      fontWeight = FontWeight.Medium,
      color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@Composable
fun FeaturedCard() {
  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column {
      Box(modifier = Modifier.fillMaxWidth().height(128.dp).background(Color(0xFF4A2D18))) {
        Box(modifier = Modifier.matchParentSize().background(Color.Black.copy(alpha = 0.2f)))
        Box(
          modifier = Modifier
            .padding(12.dp)
            .align(Alignment.TopEnd)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White.copy(alpha = 0.9f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFA000), modifier = Modifier.size(12.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("4.8", fontSize = 10.sp, fontWeight = FontWeight.Bold)
          }
        }
        Box(
          modifier = Modifier
            .padding(12.dp)
            .align(Alignment.BottomStart)
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text("TOP RATED", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }
      PaddingValues(16.dp)
      Column(modifier = Modifier.padding(16.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
          Column {
            Text("Mama Nduku's Kibanda", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            Text("Authentic Pilau • Chapati • Stews", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
          Column(horizontalAlignment = Alignment.End) {
            Text("KSh 150+", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("15-25 min", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      }
    }
  }
}

@Composable
fun SecondaryCard(title: String, subtitle: String, time: String, tag: String?) {
  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(modifier = Modifier.fillMaxWidth()) {
      Box(
        modifier = Modifier
          .size(96.dp)
          .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center
      ) {
        Icon(Icons.Filled.ShoppingCart, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
      }
      Column(modifier = Modifier.padding(12.dp).weight(1f), verticalArrangement = Arrangement.Center) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
          Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
          Icon(Icons.Filled.Favorite, contentDescription = "Favorite", modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(6.dp))
              .background(MaterialTheme.colorScheme.surfaceVariant)
              .padding(horizontal = 8.dp, vertical = 2.dp)
          ) {
            Text("⏱ $time", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
          }
          if (tag != null) {
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
              Text("💳 $tag", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Medium)
            }
          }
        }
      }
    }
  }
}

@Composable
fun BottomNavigation() {
  NavigationBar(
    containerColor = MaterialTheme.colorScheme.surfaceVariant,
    tonalElevation = 0.dp
  ) {
    NavigationBarItem(
      icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
      label = { Text("Home", fontWeight = FontWeight.Bold) },
      selected = true,
      onClick = { },
      colors = NavigationBarItemDefaults.colors(
        indicatorColor = MaterialTheme.colorScheme.primaryContainer,
        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedTextColor = MaterialTheme.colorScheme.onBackground
      )
    )
    NavigationBarItem(
      icon = { Icon(Icons.Filled.List, contentDescription = "Orders") },
      label = { Text("Orders") },
      selected = false,
      onClick = { }
    )
    NavigationBarItem(
      icon = { Icon(Icons.Filled.AccountBox, contentDescription = "Wallet") },
      label = { Text("Wallet") },
      selected = false,
      onClick = { }
    )
    NavigationBarItem(
      icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
      label = { Text("Profile") },
      selected = false,
      onClick = { }
    )
  }
}
