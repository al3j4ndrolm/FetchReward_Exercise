package com.example.fetch_problem

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/**
 * Entry of the app.
 */
@Composable
fun App(contactsMap : Map<Int, List<ContactGroup>>){
    Column {
        Header()
        Contacts(contactsMap)
    }
}

/**
 * Displays the header with a clickable image and a title, showing an Easter egg dialog on click.
 */
@Composable
private fun Header(){

    var showDialog by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.fetch),
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .clickable { showDialog = true },
                contentDescription = "fetch banner")
            Box(modifier = Modifier
                .width(260.dp)
                .height(1.dp)
                .background(Color.LightGray))
            Text(
                text = "Hiring Contacts",
                color = Configs.CONTACT_LIST_BG_COLOR_LIGHT,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }

        if (showDialog){
            EasterEggDialog(onDismissRequest = {showDialog = false})
        }
    }
}

/**
 * Displays an Easter Egg dialog with a custom message and two buttons.
 */
@Composable
private fun EasterEggDialog(onDismissRequest: () -> Unit){
    Dialog(onDismissRequest = {  }) {
        Box(modifier = Modifier
            .height(180.dp)
            .width(240.dp)
            .background(color = Configs.CONTACT_LIST_TEXT_COLOR, shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Please, hire me.\nI'm ready ;)", textAlign = TextAlign.Center, fontSize = 24.sp, fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 20.dp))
                Button(onClick = { onDismissRequest() }) {
                    Text(text = "Ok")
                }
                Button(onClick = { onDismissRequest() }) {
                    Text(text = "Sure")
                }
            }

        }
    }
}

/**
 * Displays a scrollable list of contact groups organized by their list ID.
 */
@Composable
fun Contacts(contactsMap : Map<Int, List<ContactGroup>>) {
    Column(modifier = Modifier.verticalScroll(
        rememberScrollState()
    )) {
        val sortedListIds = contactsMap.keys.sorted()
        for (listId in sortedListIds){
            Box(
                Modifier
                    .padding(top = 10.dp)
                    .clip(Configs.CURVING_SHAPE)
                    ){
                ContactList(contactsMap[listId]!!)
            }
        }
    }
}

/**
 * Displays a toggleable list of contacts, with an animated width based on their visibility.
 */
@Composable
fun ContactList(contactGroupList: List<ContactGroup>){
    var showContacts by remember {
        mutableStateOf(false)
    }

    val boxWidth by animateDpAsState(
        targetValue = if (showContacts) Configs.OPEN_BOX_WIDTH.dp else Configs.CLOSED_BOX_WIDTH.dp,
        animationSpec = tween(100)
    )

    Box(modifier = Modifier
        .clickable { showContacts = !showContacts }
        .width(boxWidth)
    ){
        Column {
            ListHeader(contactGroupList)

            if (showContacts){
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Configs.CONTACT_LIST_BG_COLOR_LIGHT))
                ContactListTableHeader()

                val contactGroups =  contactGroupList.toMutableList()
                contactGroups.sortBy { it.name }
                ContactContent(contactGroups = contactGroups)
            }
        }
    }
}

/**
 * Displays a header for a list of contact groups, showing the list ID and the total number of contacts.
 */
@Composable
fun ListHeader(contactGroupList: List<ContactGroup>) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(color = Configs.CONTACT_LIST_BG_COLOR_DARK),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "List ID ${contactGroupList[0].listId}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Configs.CONTACT_LIST_TEXT_COLOR,
            modifier = Modifier.padding(start = 10.dp)
        )
        Text(
            text = "Total ${contactGroupList.size}",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Configs.CONTACT_LIST_TEXT_COLOR,
            modifier = Modifier.padding(end = 5.dp),
        )
    }
}

/**
 * Displays the header row for the contact list table with column titles.
 */
@Composable
private fun ContactListTableHeader() {
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .background(color = Configs.CONTACT_LIST_BG_COLOR_DARK)) {
        Text(
            text = "Name",
            modifier = Modifier
                .width(180.dp)
                .padding(start = 20.dp),
            fontWeight = FontWeight.Medium,
            color = Configs.CONTACT_LIST_TEXT_COLOR,)
        Text(
            text = "ID",
            modifier = Modifier.padding(start = 10.dp),
            fontWeight = FontWeight.Medium,
            color = Configs.CONTACT_LIST_TEXT_COLOR,)
    }
}

/**
 * Displays a list of contact group entries with alternating background colors.
 */
@Composable
private fun ContactContent(contactGroups: List<ContactGroup>) {
    var useDark = false
    for (contactGroup in contactGroups){
        val bgColor = if (useDark) Configs.CONTACT_LIST_BG_COLOR_DARK else Configs.CONTACT_LIST_BG_COLOR_LIGHT
        useDark = !useDark

        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = bgColor)) {
            Text(
                text = "${contactGroup.name}",
                modifier = Modifier
                    .width(180.dp)
                    .padding(start = 10.dp),
                color = Configs.CONTACT_LIST_TEXT_COLOR,
            )

            Text(
                text = "${contactGroup.id}",
                color = Configs.CONTACT_LIST_TEXT_COLOR,
            )
        }
    }
}