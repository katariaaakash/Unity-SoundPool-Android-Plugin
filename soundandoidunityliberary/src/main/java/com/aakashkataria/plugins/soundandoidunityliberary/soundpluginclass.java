package com.aakashkataria.plugins.soundandoidunityliberary;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.io.IOException;

/**
 * Created by aakashkataria on 28/02/17.
 */

public class soundpluginclass {
    private Context activity_context;
    private SoundPool soundpool;
    private AssetManager assetManager;
    private int id;

    public static Object createInstance(Context context){
        return new soundpluginclass(context);
    }

    @SuppressWarnings("deprecation")
    private void instantiatesoundpool(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundpool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }
        else{
            soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }
    }

    private soundpluginclass(Context context){
        if (context == null){
            throw new NullPointerException();
        }

        activity_context = context;
        assetManager = context.getAssets();
        instantiatesoundpool();
        soundpool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                soundPool.play(id, 1.0f, 1.0f, 1, 0, 1.0f);
            }
        });
    }

    public int registersound(String filename){
        AssetFileDescriptor assetFileDescriptor;
        try {
            assetFileDescriptor = assetManager.openFd(filename);
        } catch (IOException e) {
            return -1;
        }
        id = soundpool.load(assetFileDescriptor, 1);
        return id;
    }

    public void destroysoundpool(){
        soundpool.release();;
        soundpool = null;
        assetManager = null;
        activity_context = null;
    }
}
