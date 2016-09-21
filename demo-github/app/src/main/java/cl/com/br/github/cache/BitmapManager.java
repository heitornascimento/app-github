package cl.com.br.github.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cl.com.br.github.R;

/**
 * Created by heitornascimento on 8/21/16.
 */
public class BitmapManager {

    private static BitmapManager ourInstance = new BitmapManager();
    private static Context ctx;
    private ExecutorService mPool = Executors.newFixedThreadPool(8);

    public static BitmapManager getInstance(Context context) {
        ctx = context;
        return ourInstance;
    }

    private BitmapManager() {
    }


    /**
     * Download the image, store into CacheImage and update ImageView reference.
     * @param profile
     * @param url
     * @param imageview
     * @return
     */
    public Bitmap loadImageFromCache(final String profile, final String url, final ImageView imageview) {

        Bitmap bitmap = CacheImage.getInstance().getImageFromCache(profile);

        //The ImageWorkerThread will call this handler.
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg != null && msg.obj != null) {
                    imageview.setImageBitmap((Bitmap) msg.obj);
                    imageview.invalidate();
                }
            }
        };

        if (bitmap == null) {
            mPool.execute(new ImageWorkerTask(profile, url, handler));
            bitmap = BitmapFactory.
                    decodeResource(ctx.getResources(), R.drawable.image_placeholder);

        }
        return bitmap;
    }
}
