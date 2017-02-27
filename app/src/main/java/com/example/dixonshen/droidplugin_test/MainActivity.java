package com.example.dixonshen.droidplugin_test;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.morgoo.droidplugin.pm.PluginManager;
import com.morgoo.helper.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btn_plugin_start;
    Button btn_plugin_install;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_plugin_install = (Button) findViewById(R.id.btn_plugin_install);
        btn_plugin_start = (Button) findViewById(R.id.btn_plugin_start);

        btn_plugin_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PluginManager.getInstance().isConnected()) {
                    //return "连接失败";
                    Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                }
                try {
                    final File files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.i("============", files.listFiles()[0].getPath());
                                int result = PluginManager.getInstance().installPackage(files
                                .listFiles()[0].getPath(), 0);
                                Log.i("============++++++++", result + "");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btn_plugin_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoPlugin(btn_plugin_start);
            }
        });

    }

    private void gotoPlugin(View view) {
        if (isServiceAvailable(view.getContext(), PluginConsts.PLUGIN_ACTION_SERVICE)) {
            //启动service
            Intent intent = new Intent(PluginConsts.PLUGIN_ACTION_SERVICE);
            startService(intent);
        } else {
            Toast.makeText(view.getContext(), "打开失败", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isServiceAvailable(Context context, String action) {
        Intent intent = new Intent(action);
        return context.getPackageManager().resolveService(intent, 0) != null;
    }
}
