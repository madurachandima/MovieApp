package com.madura.movieapp.presentation.composible

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.madura.movieapp.R
import com.madura.movieapp.presentation.theme.background
import com.madura.movieapp.presentation.theme.darkPurple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun searchTextField(
    hint: String = "Search",
    onSearch: ((String) -> Unit),

    ) {
    var searchTextValue by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .background(color = darkPurple)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            color = background

        ) {
            OutlinedTextField(
                searchTextValue,
                enabled = true,
                onValueChange = {
                    searchTextValue = it
//                    onTextChange(it)
                },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Search, contentDescription = "null",
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp),
                    )
                },
                trailingIcon = {
                    if (searchTextValue != "") {
                        IconButton(onClick = {
                            searchTextValue = ""
                            onSearch(searchTextValue)

                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "close",
                                Modifier
                                    .padding(10.dp)
                                    .size(30.dp),
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions {
                    Log.e("SEARCH", "call search ------->>>>>>>> ${searchTextValue}")
                    onSearch(searchTextValue)
                },
                shape = RoundedCornerShape(15.dp),
                placeholder = {
                    Text(
                        text = hint,
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                )

            )


        }
    }
}

@Preview
@Composable
fun searchTextFieldP() {
    searchTextField {

    }
}
