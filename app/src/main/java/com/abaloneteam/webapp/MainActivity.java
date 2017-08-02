package com.abaloneteam.webapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends AppCompatActivity
{
    WebView mainWebView;
    public static final int SELECT_PHOTO = 1003;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainWebView = (WebView) findViewById(R.id.main_webview);

        String data = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n" +
                "<title>title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>Web View Demo</h1>\n" +
                "<br>\n" +
                "    <input type=\"button\" value=\"Say hello\"\n" +
                "        onClick=\"showAndroidToast('Hello Android!')\" />\n" +
                "    <br />\n" +
                "    File Uri: <label id=\"lbluri\">no file uri</label>\n" +
                "    <br />\n" +
                "    File Path: <label id=\"lblpath\">no file path</label>\n" +
                "    <br />\n" +
                "    <input type=\"button\" value=\"Choose Photo\" onClick=\"choosePhoto()\" />\n" +
                "    <script type=\"text/javascript\">\n" +
                "        function showAndroidToast(toast) {\n" +
                "            Android.showToast(toast);\n" +
                "        }\n" +
                "        function setFilePath(file) {\n" +
                "            document.getElementById('lblpath').innerHTML = file;\n" +
                "            Android.showToast(file);\n" +
                "        }\n" +
                "        function setFileUri(uri) {\n" +
                "            document.getElementById('lbluri').innerHTML = uri;\n" +
                "            Android.showToast(uri);\n" +
                "        }\n" +
                "        function choosePhoto() {\n" +
                "            var file = Android.choosePhoto();\n" +
                "        }\n" +
                "    </script>\n" +
                "</body>\n" +
                "</html>";
        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        mainWebView.setWebViewClient(new WebViewClient());
        mainWebView.addJavascriptInterface(new MyJavascriptInterface(this), "Android");
        mainWebView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
            }
        });
        mainWebView.loadData(data, "text/html; charset=utf-8", "UTF-8");
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if (mainWebView.canGoBack())
        {
            mainWebView.goBack();
        } else {
            finish();
        }
    }

    private class MyJavascriptInterface
    {
        Context mContext;

        /** Instantiate the interface and set the context */
        MyJavascriptInterface(Context c)
        {
            mContext = c;
        }

        /** Show a toast from the web page *//*
        @JavascriptInterface
        public void showToast(String toast)
        {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }*/

        @JavascriptInterface
        public String choosePhoto()
        {
            // TODO Auto-generated method stub
            String file = "test";
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            return file;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        switch (requestCode)
        {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK)
                {
                    Uri selectedImage = intent.getData();
                    mainWebView.loadUrl("javascript:setFileUri('" + selectedImage.toString() + "')");
                    String path = getRealPathFromURI(this, selectedImage);
                    mainWebView.loadUrl("javascript:setFilePath('" + path + "')");
                }
        }

    }

    public String getRealPathFromURI(Context context, Uri contentUri)
    {
        Cursor cursor = null;
        try
        {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            if (cursor != null)
            {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
            }
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
        return null;
    }
}
