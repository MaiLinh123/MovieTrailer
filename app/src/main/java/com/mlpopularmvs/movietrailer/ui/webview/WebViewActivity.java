package com.mlpopularmvs.movietrailer.ui.webview;

import static com.mlpopularmvs.movietrailer.utils.Constant.URL_OF_REVIEW;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mlpopularmvs.movietrailer.R;
import com.mlpopularmvs.movietrailer.databinding.ActivityWebViewBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WebViewActivity extends AppCompatActivity {

    private ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);

        binding.indicator.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String url = intent.getStringExtra(URL_OF_REVIEW);
        binding.webView.setWebViewClient(new WebViewClient());
        binding.webView.loadUrl(url);

        binding.webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                binding.indicator.setVisibility(View.GONE);
            }
        });
    }
}
