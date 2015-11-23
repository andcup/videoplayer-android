package com.qioq.android.lib.video.plugins.subtitle;

import android.content.Context;
import android.os.AsyncTask;
import com.qioq.android.lib.video.core.NotificationService;
import com.qioq.android.lib.video.core.model.Subtitle;
import com.qioq.android.lib.video.plugins.subtitle.core.OnSubtitleLoadingListener;
import com.qioq.android.lib.video.plugins.subtitle.core.SubtitleDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amos on 2015/8/3.
 */
public class SubtitlePlayer  implements SubtitleProvider.OnSubtitleGetterListener, OnSubtitleLoadingListener {

    private static SubtitlePlayer sSubtitlePlayer;
    private List<Subtitle>        mSubtitles = new ArrayList<Subtitle>();
    private SubtitleDownloadTask  mSubtitleDownloadTask;
    private static final String   SUBTITLE_POSTFIX = ".sb";
    private Subtitle              mSubtitle;

    private SubtitlePlayer(){

    }

    public static SubtitlePlayer get(){
        if( null == sSubtitlePlayer){
            sSubtitlePlayer = new SubtitlePlayer();
        }
        return sSubtitlePlayer;
    }

    public void open(Context context, SubtitleProvider subtitleProvider){
        mSubtitleDownloadTask = new SubtitleDownloadTask(context);
        subtitleProvider.load(this);
    }

    public void close(){
        sSubtitlePlayer = null;
        if( null != mSubtitleDownloadTask){
            mSubtitleDownloadTask.cancel(true);
        }
        mSubtitleDownloadTask = null;
    }

    public List<Subtitle> getSubtitles() {
        return mSubtitles;
    }

    public Subtitle getSubtitle(){
        return mSubtitle;
    }

    public void setSubtitle(Subtitle subtitle){
        mSubtitle = subtitle;
    }

    @Override
    public void onSubtitleGetFinish(List<Subtitle> subtitleList) {
        mSubtitles.clear();
        if( null != subtitleList && subtitleList.size() > 0){
            mSubtitles.addAll(subtitleList);
            mSubtitleDownloadTask.execute();
        }
    }

    @Override
    public void onSubtitleLoadingStart(Subtitle subtitle) {

    }

    @Override
    public void onSubtitleLoading(Subtitle subtitle, int progress) {

    }

    @Override
    public void onSubtitleLoadingComplete(Subtitle subtitle) {

    }

    @Override
    public void onSubtitleLoadingFailed(Subtitle subtitle) {

    }

    class SubtitleDownloadTask extends AsyncTask<String, Integer, Void>{

        Context mContext;
        public SubtitleDownloadTask(Context context){
            mContext = context;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try{
                if( null == mSubtitles || mSubtitles.size() <= 0){
                    return null;
                }
                for(int i = 0; i < mSubtitles.size(); i++){
                    Subtitle subtitle = mSubtitles.get(i);
                    if(subtitle.isExists()){
                        publishProgress(i);
                        continue;
                    }
                    String path = getSubtitleStoragePath(mContext, subtitle);
                    subtitle.setLocalUrl(path);
                    File file = new File(path+ SUBTITLE_POSTFIX);
                    if(file.exists()){
                        file.delete();
                    }
                    file.createNewFile();
                    if(SubtitleDownloader.download(subtitle, file, SubtitlePlayer.this)){
                        file.renameTo(new File(path));
                        SubtitlePlayer.this.onSubtitleLoadingComplete(subtitle);
                    }else{
                        SubtitlePlayer.this.onSubtitleLoadingFailed(subtitle);
                    }
                    publishProgress(i);
                }
            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(values[0] == 0){
                NotificationService.get().onSubtitleChanged(mSubtitles.get(0));
            }
        }
    }

    private String getSubtitleStoragePath(Context context, Subtitle subtitle){
        int index = subtitle.getUrl().lastIndexOf("/");
        if(index != -1){
            String filename = subtitle.getUrl().substring(index, subtitle.getUrl().length());
            String saveFile = context.getCacheDir() + filename;
            return saveFile;
        }
        return null;
    }
}
