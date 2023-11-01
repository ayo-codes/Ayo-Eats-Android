package org.wit.ayoeats.helpers

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import org.wit.ayoeats.R

// old imagePicker without need for permissions
//fun showImagePicker (intentLauncher : ActivityResultLauncher<Intent>) {
//    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT) // Sets the intent based on the storage access framework
//    chooseFile.type = "image/*"
//    chooseFile = Intent.createChooser(chooseFile, R.string.select_mealLocation_image.toString()) // selects the intent string
//    intentLauncher.launch(chooseFile) //launches the file
//
//}

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>, context: Context) {
    var imagePickerTargetIntent = Intent()

    imagePickerTargetIntent.action = Intent.ACTION_OPEN_DOCUMENT
    imagePickerTargetIntent.addFlags( Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION )
    imagePickerTargetIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    imagePickerTargetIntent.type = "image/*"
    imagePickerTargetIntent = Intent.createChooser(imagePickerTargetIntent , context.getString(R.string.select_mealLocation_image))
    intentLauncher.launch(imagePickerTargetIntent)
}