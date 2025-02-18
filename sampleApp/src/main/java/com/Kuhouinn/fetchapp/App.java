package com.Kuhouinn.fetchapp;

import android.app.Application;

import com.Kuhouinn.fetch2.HttpUrlConnectionDownloader;
import com.Kuhouinn.fetch2core.Downloader;
import com.Kuhouinn.fetch2.Fetch;
import com.Kuhouinn.fetch2.FetchConfiguration;
import com.Kuhouinn.fetch2okhttp.OkHttpDownloader;
import com.Kuhouinn.fetch2rx.RxFetch;

import okhttp3.OkHttpClient;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //if (BuildConfig.DEBUG) {
        //    Timber.plant(new Timber.DebugTree());
        //}
        final FetchConfiguration fetchConfiguration = new FetchConfiguration.Builder(this)
                .enableRetryOnNetworkGain(true)
                .setDownloadConcurrentLimit(3)
                .setHttpDownloader(new HttpUrlConnectionDownloader(Downloader.FileDownloaderType.PARALLEL))
                // OR
                //.setHttpDownloader(getOkHttpDownloader())
                .build();
        Fetch.Impl.setDefaultInstanceConfiguration(fetchConfiguration);
        RxFetch.Impl.setDefaultRxInstanceConfiguration(fetchConfiguration);
    }

    private OkHttpDownloader getOkHttpDownloader() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        return new OkHttpDownloader(okHttpClient,
                Downloader.FileDownloaderType.PARALLEL);
    }

}
