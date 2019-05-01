package com.lernr.dummyurlchecker;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private ProgressBar mProgressBar;
 private Button goSearch;
 private WebView mWebView;
 private EditText mEditText;
  private final String URL = "https://www.neetprep.com/home";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    goSearch = findViewById(R.id.button);
    mWebView= findViewById(R.id.webview);
    mEditText = findViewById(R.id.editText);
    mProgressBar = findViewById(R.id.progressbar);

    goSearch.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        String url = mEditText.getText().toString();

        if(url.isEmpty()){
          Toast.makeText(getApplicationContext(),"Please enter url!!",Toast.LENGTH_LONG).show();
          return;
        }



//        if(!url.startsWith("https://")){
//          url = "https://"+url;
//        }
        setWebview(mWebView,url.replaceAll(" ",""));
        Log.i("URL",url);
      }
    });

//    setWebview(mWebView, URL);

    mWebView.setWebChromeClient(new WebChromeClient() {
      public void onProgressChanged(WebView view, int progress) {

//        if(progress==100){
//          mProgressBar.setVisibility(View.GONE);
//        }else{
//          mProgressBar.setVisibility(View.VISIBLE);
//        }


      }
    });

  }

  public void setWebview(final WebView mWebview, String url) {

    WebSettings settings = mWebview.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setMediaPlaybackRequiresUserGesture(false);

//    mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);

//        mWebview.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//                // Log.d(TAG + " 185", "Load Status: " + progress);
//                if (progress == 100) {
//
//                }
//̵̵̵
//̵            }
//        });

    mWebview.setWebViewClient(new WebViewClient() {

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mProgressBar.setVisibility(View.VISIBLE);
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mProgressBar.setVisibility(View.GONE);
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        mEditText.setText(url);
        return true;
      }
    });

    mWebview.loadUrl(url);
  }


  //enabling back button to go to previous page
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (event.getAction() == KeyEvent.ACTION_DOWN) {
      switch (keyCode) {
        case KeyEvent.KEYCODE_BACK:
          if (mWebView.canGoBack()) {
            mWebView.goBack();
          } else {
            finish();
          }
          return true;
      }

    }
    return super.onKeyDown(keyCode, event);
  }
}
