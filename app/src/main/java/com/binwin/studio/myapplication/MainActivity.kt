package com.binwin.studio.myapplication

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL


class MainActivity : AppCompatActivity() {

    private lateinit var mDbWorkerThread: WorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = WorkerThread("WorkerThread")
        mDbWorkerThread.start()


        Picasso.get().load("http://square.github.io/picasso/static/sample.png").into(imageView)

        download_btn.setOnClickListener {
            val task = Runnable {

                val imageurl = URL("http://square.github.io/picasso/static/sample.png")
                val bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream())
                val bytes = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

                val f = File(Environment.getExternalStorageDirectory().toString() + File.separator + "IMAGE_.bmp")

                f.createNewFile()

                val fo = FileOutputStream(f)
                fo.write(bytes.toByteArray())

                fo.close()

            }

            mDbWorkerThread.postTask(task)
        }


    }

}

