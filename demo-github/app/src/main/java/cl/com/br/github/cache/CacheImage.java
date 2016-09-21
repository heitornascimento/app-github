package cl.com.br.github.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import cl.com.br.github.R;

/**
 * Created by heitornascimento on 8/21/16.
 */
public class CacheImage {

    private static CacheImage instance = new CacheImage();
    private LruCache<String, Bitmap> mMemoryCache;
    private static Context context;
    final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    // Use 1/8th of the available memory for this memory cache.
    final int cacheSize = maxMemory / 8;

    private CacheImage() {
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public static CacheImage getInstance() {
        return instance;
    }

    public void addImageToCache(String profile, Bitmap image) {
        if (mMemoryCache.get(profile) == null) {
            mMemoryCache.put(profile, image);
        }
    }

    public Bitmap getImageFromCache(String profile) {
        Bitmap bitmap = mMemoryCache.get(profile);
        return  bitmap;
    }

}
