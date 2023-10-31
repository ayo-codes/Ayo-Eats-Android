package org.wit.ayoeats.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.wit.ayoeats.R

fun showImagePicker (intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT) // Sets the intent based on the storage access framework
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_mealLocation_image.toString()) // selects the intent string
    intentLauncher.launch(chooseFile) //launches the file

}