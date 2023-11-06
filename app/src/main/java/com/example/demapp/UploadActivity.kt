package com.example.demapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UploadActivity : AppCompatActivity() {
    private lateinit var uploadButton: FloatingActionButton
    private lateinit var uploadImage: ImageView
    private lateinit var uploadCaption: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var imageUri: Uri
    private val databaseReference = FirebaseDatabase.getInstance().reference.child("Images")
    private val storageReference = FirebaseStorage.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        uploadButton = findViewById(R.id.uploadButton)
        uploadCaption = findViewById(R.id.uploadCaption)
        uploadImage = findViewById(R.id.uploadImage)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data!!.data!!
                uploadImage.setImageURI(imageUri)
            } else {
                Toast.makeText(this@UploadActivity, "No Image Selected", Toast.LENGTH_SHORT).show()
            }
        }


        uploadImage.setOnClickListener {
            val photoPicker = Intent()
            photoPicker.action = Intent.ACTION_GET_CONTENT
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }
        uploadButton.setOnClickListener {
            if (imageUri != null) {
                uploadToFirebase(imageUri)
            } else {
                Toast.makeText(this@UploadActivity, "Please select image", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun uploadToFirebase(uri: Uri) {
        val caption = uploadCaption.text.toString()
        val imageReference = storageReference.child("${System.currentTimeMillis()}.${getFileExtension(uri)}")
        imageReference.putFile(uri).addOnSuccessListener {
            imageReference.downloadUrl.addOnSuccessListener { uri ->
                val dataClass = DataClass(uri.toString(), caption)
                val key = databaseReference.push().key
                databaseReference.child(key!!).setValue(dataClass)
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@UploadActivity, "Uploaded", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnProgressListener {
            progressBar.visibility = View.VISIBLE
        }.addOnFailureListener {
            progressBar.visibility = View.INVISIBLE
            Toast.makeText(this@UploadActivity, "Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(fileUri: Uri): String? {
        val contentResolver = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri))
    }
}