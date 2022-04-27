package com.riyandifirman.movie.sign.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.riyandifirman.movie.home.HomeActivity
import com.riyandifirman.movie.R
import com.riyandifirman.movie.sign.signin.User
import com.riyandifirman.movie.utils.Preferences
import java.util.*
import kotlinx.android.synthetic.main.activity_sign_up_photoscreen.*

class SignUpPhotoscreenActivity : AppCompatActivity(), PermissionListener {

    val REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    lateinit var storage : FirebaseStorage
    lateinit var storageReference : StorageReference
    lateinit var preferences: Preferences

    lateinit var user : User
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase

//    private val tv_hello:TextView
//    get() = findViewById(R.id.tv_hello)
//    private val iv_add:ImageView
//    get() = findViewById(R.id.iv_add)
//    private val iv_profile:ImageView
//    get() = findViewById(R.id.iv_profile)
//    private val btn_save:View
//    get() = findViewById(R.id.btn_save)
//    private val btn_upload_later:View
//    get() = findViewById(R.id.btn_upload_later)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_photoscreen)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage.getReference()

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        user = intent.getParcelableExtra("data")!!
        tv_hello.setText("Welcome\n"+user.name)

        iv_add.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                btn_save.visibility = View.INVISIBLE
                iv_add.setImageResource(R.drawable.ic_baseline_add_white_24)
                iv_profile.setImageResource(R.drawable.user_pic)
            } else {
//                Dexter.withActivity(this)
//                    .withPermission(android.Manifest.permission.CAMERA)
//                    .withListener(this)
//                    .check()

                ImagePicker.with(this)
                    .cameraOnly()
                    .start()
            }
        }

        btn_upload_later.setOnClickListener {
            finishAffinity()
            startActivity(Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java))
        }

        btn_save.setOnClickListener {
            if (filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath)
                    .addOnSuccessListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()
                        ref.downloadUrl.addOnSuccessListener {
                            saveToFirebase(it.toString())
                        }
                    }

                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
                    }

                    .addOnProgressListener {
                        taskSnapshot -> val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploading " + progress.toInt() + " %")
                    }
            }
        }
    }

    private fun saveToFirebase(url: String) {
        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValue("nama", user.name.toString())
                preferences.setValue("user", user.username.toString())
                preferences.setValue("saldo", "")
                preferences.setValue("url", "")
                preferences.setValue("email", user.email.toString())
                preferences.setValue("status", "1")
                preferences.setValue("url", url)

                finishAffinity()
                startActivity(Intent(this@SignUpPhotoscreenActivity, HomeActivity::class.java))

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpPhotoscreenActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }

        ImagePicker.with(this)
            .cameraOnly()
            .start()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "You can't add photo profile", Toast.LENGTH_LONG).show()
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Hurry? click upload button later", Toast.LENGTH_LONG).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//
//            filePath = data.getData()!!
//            Glide.with(this)
//                .load(bitmap)
//                .apply(RequestOptions.circleCropTransform())
//                .into(iv_profile)
//
//            btn_save.visibility = View.VISIBLE
//            iv_add.setImageResource(R.drawable.ic_baseline_delete_forever_24)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            statusAdd = true
            filePath = data?.data!!

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(iv_profile)

            btn_save.visibility = View.VISIBLE
            iv_add.setImageResource(R.drawable.ic_baseline_delete_forever_24)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_LONG).show()
        }
    }
}