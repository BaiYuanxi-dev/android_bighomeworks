package com.example.homework4


import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.ListView
import org.jetbrains.anko.progressDialog
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button1)


        button.setOnClickListener {
//            MyAsyncTask(this).execute();
//            Thread.
        }
    }


    class MyHandler(context: Context): Handler(){
        lateinit var dialog: ProgressDialog;
        var i:Int = 0;
        var context : Context?=null
        init {
            this.context = context;
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }
//    class MyAsyncTask(context: Context) : AsyncTask<String,Int,String>(){
//        lateinit var dialog: ProgressDialog;
//        var i:Int = 0;
//        var context : Context?=null
//        init {
//            this.context = context;
//        }
//        //任务执行之前开始调用此方法，可以在这里显示进度对话框。
//        override fun onPreExecute() {
//            super.onPreExecute()
//            dialog = ProgressDialog(context);
//            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setTitle("任务执行...")
////            dialog.setButton("确定", new DialogInterface.OnClickListener() {
//
//
////            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            dialog.setMax(100);
//            dialog.show();
//        }
//
//
//        //此方法在后台线程 执行，完成任务的主要工作，通常需要较长的时间。
//        override fun doInBackground(vararg p0: String?): String {
//            i = 0;
//            while (i < 100){
//                i = i+1
//                publishProgress(i)
//                Thread.sleep(50);
////                dialog.setProgress((i / 100000) * 100);
////                dialog.progress = (i / 100000) * 100
////                Log.d(i+"")
//            }
//            return "js"
//        }
//
//        //更新UI
//        override fun onProgressUpdate(vararg values: Int?) {
//            super.onProgressUpdate(*values)
//            values[0]?.let { dialog.setProgress(it) };
////            dialog.progress = (i / 10000) * 100
//            //若有复杂逻辑，可以增加异常捕捉
//        }
//
//        //任务执行完了后执行
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            dialog.dismiss()
////        Toast.makeText(context,"进程结束",Toast.LENGTH_LONG).show()
//        }
//    }
}






