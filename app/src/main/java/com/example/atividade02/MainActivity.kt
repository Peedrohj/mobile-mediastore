package com.example.atividade02

import android.Manifest
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FileAdapter.OnItemClickListener {
    private var baseList = ArrayList<FileData>()
    private var adapter = FileAdapter(baseList, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        }

        file_list.adapter = adapter
        file_list.layoutManager = LinearLayoutManager(this)
        file_list.setHasFixedSize(true)

        baseList.addAll(getBaseList())
        adapter.notifyDataSetChanged()
    }

    private fun getBaseList(): ArrayList<FileData> {
        val list = ArrayList<FileData>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
        )

        applicationContext.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val fileId = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val fileName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(fileId)
                val title = cursor.getString(fileName)

                print("DEBUG Media ID: $id")
                print("DEBUG Media Title: $title")

                val contentUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
                print("DEBUG contentUri: $contentUri")


                val file = FileData(name = title, imageUri = contentUri)

                list.add(file)
            }
        }
        return list
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
    
}