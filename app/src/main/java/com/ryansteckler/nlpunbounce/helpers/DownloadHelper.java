package com.ryansteckler.nlpunbounce.helpers;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ProgressBar;

import com.ryansteckler.nlpunbounce.R;

/**
 * Created by rsteckler on 10/17/14.
 */
public class DownloadHelper {

    public void startDownload(final Activity context, final ProgressBar progressBar, final DownloadListener listener) {

        String urlDownload = "http://dl-xda.xposed.info/modules/de.robv.android.xposed.installer_v32_de4f0d.apk";
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlDownload));

        request.setDescription(context.getResources().getString(R.string.download_xposed_description));
        request.setTitle(context.getResources().getString(R.string.download_xposed_title));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        final long downloadId = manager.enqueue(request);

        new Thread(new Runnable() {

            @Override
            public void run() {

                boolean downloading = true;

                while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);

                    Cursor cursor = manager.query(q);
                    cursor.moveToFirst();
                    int bytes_downloaded = cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                        if (listener != null) {
                            listener.onFinished(status == DownloadManager.STATUS_SUCCESSFUL, cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME)));
                        }
                    }

                    final double dl_progress = (bytes_downloaded / bytes_total) * 100;
                    context.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            progressBar.setProgress((int) dl_progress);
                        }
                    });

                    cursor.close();
                }

            }
        }).start();
    }

    public interface DownloadListener {
        public void onFinished(boolean success, String filename);
    }

}
