package mr_xiaoliang.com.github.lview_as.option;

import java.io.File;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mr_xiaoliang.com.github.lview_as.R;

public class MyApplication extends Application {
	
	private static DisplayImageOptions imageOptions =null;
	private Toast toast;
	@Override
	public void onCreate() {
		super.onCreate();
		initImageLoader(getApplicationContext());
	}
	private static void initImageLoader(Context context) {
		// 缓存文件的目录
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"MaoHeShu/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(800, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.threadPoolSize(4)
				// 线程池内线程的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
				.diskCacheSize(50 * 1024 * 1024) // SD卡缓存的最大值
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				// 由原先的discCache -> diskCache
//				.diskCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000))
				// connectTimeout(5s),readTimeout(30s)超时时间
				.writeDebugLogs() // Remove for release app
				.build();
		// 全局初始化此配置
		ImageLoader.getInstance().init(config);
	}
	/**
	 * 获取一个图片加载类的参数设置
	 * @return
	 * 包括:
	 * 下载期间显示图片
	 * 加载出错显示图片
	 * 下载的图片存入缓存
	 * 自动设置jpeg图片的exif参数
	 * 设置解码方式为:EXACTLY_STRETCHED
	 * 解码类型为:RGB_565
	 * 自动在图片下载前复位
	 * 图片加载动画时间:100ms
	 * 
	 * 图片加载地址:
	 * String imageUri = "http://site.com/image.png"; // from Web  
	 * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card  
	 * String imageUri = "content://media/external/audio/albumart/13"; // from content provider  
	 * String imageUri = "assets://image.png"; // from assets  
	 * String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)  
	 */
	public static DisplayImageOptions getImageLoaderOption(){
		if(imageOptions==null){
			imageOptions = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.mipmap.ic_launcher) // 设置图片在下载期间显示的图片
			.showImageForEmptyUri(R.mipmap.ic_launcher)// 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.mipmap.ic_launcher) // 设置图片加载/解码过程中错误时候显示的图片
			.cacheInMemory(true)// 设置下载的图片是否缓存在内存中
			.considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转）
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// 设置图片以如何的编码方式显示
			.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
			.resetViewBeforeLoading(true)// 设置图片在下载前是否重置，复位
			.displayer(new FadeInBitmapDisplayer(100))// 是否图片加载好后渐入的动画时间
//			.displayer(new RoundedBitmapDisplayer(20))//圆角图片
			.build();// 构建完成
		}
		return imageOptions;
	}
	/**
	 * 吐司（Toast）输出
	 * @param s
	 */
	public void t(String s){
		toast = Toast.makeText(this,s,Toast.LENGTH_SHORT);
		toast.show();
	}
}
