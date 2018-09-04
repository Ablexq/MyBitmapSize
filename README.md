
参考：

[Android图片占用内存大小及加载解析](https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650239680&idx=1&sn=713ab63459f5353719e16d11220fb07e&chksm=886383afbf140ab9ccef170aae7bb6cdccad25183f3d15cf0d4c3252595c0cf38d3fc579a869&mpshare=1&scene=23&srcid=0817GIcz0PmnFFkmn5CiMqzn#rd)

[关于Android中图片大小、内存占用与drawable文件夹关系的研究与分析](https://mp.weixin.qq.com/s?__biz=MzA4NDM2MjAwNw==&mid=400540949&idx=1&sn=f2c53f1e5e57cdc11fdc1cb5e333be94&mpshare=1&scene=23&srcid=0817Z5kDLa0lZOj3D009795A#rd)

[玩转Android drawable图片适配](https://blog.csdn.net/myoungmeng/article/details/54090891)

[Android drawable微技巧，你所不知道的drawable的那些细节](https://blog.csdn.net/guolin_blog/article/details/50727753)

Android系统适配原则
===

Android为了更好地优化应用在不同屏幕密度下的用户体验，在项目的res目录下可以创建drawab-[density]（density为6种通用密度名）目录，开发者在进行APP开发时，针对不同的屏幕密度，将图片放置于对应的drawable-[density]目录，Android系统会依据特定的原则来查找各drawable目录下的图片。查找流程为： 
1. 先查找和屏幕密度最匹配的文件夹。如当前设备屏幕密度dpi为160，则会优先查找drawable-mdpi目录；如果设备屏幕密度dpi为420，则会优先查找drawable-xxhdpi目录。 
2. 如果在最匹配的目录没有找到对应图片，就会向更高密度的目录查找，直到没有更高密度的目录。例如，在最匹配的目录drawable-mdpi中没有查找到，就会查找drawable-hdpi目录，如果还没有查找到，就会查找drawable-xhdpi目录，直到没有更高密度的drawable-[density]目录。 
3. 如果一直往高密度目录均没有查找，Android就会查找drawable-nodpi目录。drawable-nodpi目录中的资源适用于所有密度的设备，不管当前屏幕的密度如何，系统都不会缩放此目录中的资源。因此，对于永远不希望系统缩放的资源，最简单的方法就是放在此目录中；同时，放在该目录中的资源最好不要再放到其他drawable目录下了，避免得到非预期的效果。 
4. 如果在drawable-nodpi目录也没有查找到，系统就会向比最匹配目录密度低的目录依次查找，直到没有更低密度的目录。例如，最匹配目录是xxhdpi，更高密度的目录和nodpi目录查找不到后，就会依次查找drawable-xhdp、drawable-hdpi、drawable-mdpi、drawable-ldpi。

举个例子，假如当前设备的dpi是320，系统会优先去drawable-xhdpi目录查找，如果找不到，会依次查找xxhdpi → xxxhdpi → hdpi → mdpi → ldpi。对于不存在的drawable-[density]目录直接跳过，中间任一目录查找到资源，则停止本次查找。

总结一下图片查找过程：优先匹配最适合的图片→查找密度高的目录（升序）→查找密度低的目录（降序）。


比如，设备的dpi为320，这匹配目录为drawable-xhdpi；设备的dpi为150，则匹配目录为drawable-mdpi。图片的放大和缩小遵循以下规律：

如果图片所在目录为匹配目录，则图片会根据设备dpi做适当的缩放调整。
如果图片所在目录dpi低于匹配目录，那么该图片被认为是为低密度设备需要的，现在要显示在高密度设备上，图片会被放大。
如果图片所在目录dpi高于匹配目录，那么该图片被认为是为高密度设备需要的，现在要显示在低密度设备上，图片会被缩小。
如果图片所在目录为drawable-nodpi，则无论设备dpi为多少，保留原图片大小，不进行缩放。
那么六种通用密度下的缩放倍数是多少呢？以mdpi为基线，各密度目录下的放大倍数（即缩放因子density）如下

密度	   放大倍数
ldpi	0.75
mdpi	1.0
hdpi	1.5
xhdpi	2.0
xxhdpi	3.0
xxxhdpi	4.0

关于切图的选取，Android官方给的建议，各种密度都给出一套图，分别放置在对应的drawable目录下，这种适配是最好的。但也存在问题，一是这种方式会增大安装包的大小；二是很多公司UI在出图时只会出一套。

在这种情况下，怎么使用好这一套切图呢？由于目前的Android智能手机的屏幕基本都在1080p了，屏幕的dpi多数都处于320~480，为了更好地适配，同时为了节省内存成本，建议将切图放置在drawable-xxhdpi目录，同时建议UI针对该密度的设备设计切图。如果UI的切图基于不同尺寸设计，Sketch导出切图时须调整相应的倍数。

例如，假设切图基于376×667的一倍屏幕设计，而要适配1080×1920的屏幕，导出三倍图存放于drawable-xxhdpi目录是适配最好的。

```

//图片分辨率：790*1115
//图片大小：65.48K
//手机：华为Nova2，1080*1788 xxhdpi
//放在不同文件夹中的形成的bitmap内存如下：

drawable- 默认（效果等同drawable- mdpi）
bitmapSize========================31710600bit
bitmapSize========================30967kb
bitmapSize========================30Mb

drawable- ldpi
bitmapSize========================56374400bit
bitmapSize========================55053kb
bitmapSize========================53Mb

drawable- mdpi
bitmapSize========================31710600bit
bitmapSize========================30967kb
bitmapSize========================30Mb

drawable- hdpi
bitmapSize========================14093600bit
bitmapSize========================13763kb
bitmapSize========================13Mb

drawable- xhdpi
bitmapSize========================7930020bit
bitmapSize========================7744kb
bitmapSize========================7Mb

drawable- xxhdpi
bitmapSize========================3523400bit
bitmapSize========================3440kb
bitmapSize========================3Mb

drawable- xxxhdpi
bitmapSize========================1982992bit
bitmapSize========================1936kb
bitmapSize========================1Mb

drawable- nodpi:不会被放大
bitmapSize========================3523400bit
bitmapSize========================3440kb
bitmapSize========================3Mb

```

获取屏幕dpi:

```
private void getDensity() {
	DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
	System.out.println("=====Density is " + displayMetrics.density);
	System.out.println("=====densityDpi is " + displayMetrics.densityDpi);
	System.out.println("=====height " + displayMetrics.heightPixels);
	System.out.println("=====width " + displayMetrics.widthPixels);
}
```

DisplayMetrics类源码：

```
//DPI
public static final int DENSITY_LOW = 120;
public static final int DENSITY_MEDIUM = 160;//默认值
public static final int DENSITY_TV = 213;	 //TV专用
public static final int DENSITY_HIGH = 240;
public static final int DENSITY_260 = 260;
public static final int DENSITY_280 = 280;
public static final int DENSITY_300 = 300;
public static final int DENSITY_XHIGH = 320;
public static final int DENSITY_340 = 340;
public static final int DENSITY_360 = 360;
public static final int DENSITY_400 = 400;
public static final int DENSITY_420 = 420;
public static final int DENSITY_XXHIGH = 480;
public static final int DENSITY_560 = 560;
public static final int DENSITY_XXXHIGH = 640;

//默认DENSITY_MEDIUM
public static final int DENSITY_DEFAULT = DENSITY_MEDIUM;

```
同一张正方形图片放在    mdpi  hdpi    xhdpi   xxhdpi
宽高度会依次变化        3倍   2倍     1.5倍    1倍
bitmap所占内存依次变化  9倍   4倍     2.25倍   1倍



以一张大小为 1024*731 = 748544B, 大小为 485.11K 的图片为例，下面是测试手机占用的内存情况。

![](https://github.com/Ablexq/MyBitmapSize/blob/master/pics/result.jpg)

从上表可以看出不同屏幕密度的手机加载图片，如果图片放在与自己屏幕密度相同的文件夹下，占用的内存都是 2994176B，

与图片本身大小 748544B 存在一个4倍关系，因为图片采用的ARGB-888色彩格式，每个像素点占用4个字节。


手机在加载图片时，会先查找自己本密度的文夹下是否存在资源，不存在则会向上查找，再向下查找，并对图片进行相应倍数的缩放：

如果在与自己屏幕密度相同的文件夹下存在此资源，会原样显示出来，占用内存正好是: 图片的分辨率*色彩格式占用字节数；

若自己屏幕密度相同的文件夹下不存在此文件，而在大于自己屏幕密度的文件夹下存在此资源，会进行缩小相应的倍数的平方；

若在大于自己屏幕密度的文件夹下没找到此资源，则会向小于自己屏幕密度的文件夹下查找，如果存在，则会进行放大相应的倍数的平方，

这两种情况图片占用内存为：占用内存=图片宽度 X 图片高度/((资源文件夹密度/手机屏幕密度)^2) * 色彩格式每一个像素占用字节数

结论：

1. 在对手机进行屏幕适时，可以只切一套图适配所有的手机。

但是如果只切一套小图，那在高屏幕密度手机上，会对图片进行放大，这样图片占用的内存往往比切相应图片放在高密度文件夹下，占用的内存还要大。

那如果只切一套大图放在高幕文件夹下，在小屏幕密度手机上，会缩小显示，按道理是行得通的。但系统在对图片进行缩放时，会进行大量计算，会对手机的性能有一定的影响。同时如果图片缩放比较狠，可能导致图片出现抖动或是毛边。

所以最好切出不同比便的图片放在不同幕度的文件夹下，对于性能要求不大高的图片，可以只切一套大图；

2. 一张图片占用内存=图片长 * 图片宽 ／ （资源图片文件密度/手机屏幕密度）^2 * 每一象素占用字节数，所以图片占用内存跟图片本身大小、手机屏幕密度、图片所在的文件夹密度，图片编码的色彩格式有关；

3. 对于网络图片，在不同屏幕密度的手机上加载出来，占用内存是一样的。

4. 对于网络或是assets/手机本地图片加载，如果想通过设置 Options 里的 inDensity 或是 inTargetDensity 参数来调整图片的缩放比，必须两个参数均设置才能起作用，只设置一个，不会起作用。

5. drawable 和 mipmap 文件夹存放图片的区别，首先图片放在 drawable-xhdpi 和 mipmap-xhdpi 下，两者占用的内存是一样的， Mipmaps 早在Android2.2+就可以用了，但是直到4.3 google才强烈建议使用。把图片放到 mipmaps 可以提高系统渲染图片的速度，提高图片质量，减少GPU压力。其他并没有什么区别。



华为Nova2
密度：480dp/xxhdpi/3.0x
分辨率：1080*1920px（可用：1080*1788）
屏幕尺寸：2.4*4.3/5.0英寸

华为p8
ram 2g
rom 16g
密度：320dp/xhdpi/2.0x
分辨率：720*1280px（可用：720*1208）
屏幕：2.4*4.3/5.0英寸


经测试一：

未设置        android:largeHeap="true"
maxMemory======================192 Mb

设置        android:largeHeap="true"
maxMemory======================512 Mb

经测试二：

Nova2 加载xxhdpi中图片7m
p8    加载xxhdpi中图片3m


经测试三：华为p8中：

flash_bg1 图片放在xxhdpi：
图片所占内存：宽px*高px*1像素所占内存
(1242/1.5)*(2208/1.5)*4/1024=4761kb
defalut:4761kb
good:   1190kb

flash_bg1 图片放在xhdpi：
图片所占内存：宽px*高px*1像素所占内存
1242*2208*4/1024=10712kb
defalut:10712kb
good:   2678kb



参考
[安卓1080P界面设计规范解读](https://www.25xt.com/appdesign/9487.html)

[Android进程的内存管理分析](https://blog.csdn.net/gemmem/article/details/8920039?utm_source=tuicool)

[Android内存泄漏分析及调试](https://blog.csdn.net/gemmem/article/details/13017999)

[Android O Bitmap 内存分配](http://www.cnblogs.com/xiaji5572/p/7794083.html)

Bitmap的回收
=========

1、Android 2.3.3(API 10)及以下的系统

在2.3以下的系统中，Bitmap的像素数据是存储在native中，Bitmap对象是存储在java堆中的，所以在回收Bitmap时，

需要回收两个部分的空间：native和java堆。即先调用recycle()释放native中Bitmap的像素数据，再对Bitmap对象置null，保证GC对Bitmap对象的回收

2、Android 3.0(API 11)及以上的系统

在3.0以上的系统中，Bitmap的像素数据和对象本身都是存储在java堆中的，无需主动调用recycle()，只需将对象置null，由GC自动管理



密度	建议尺寸

| 布局   | icon尺寸 |
|----------|-------------|
|mipmap-mdpi	|   48 x 48|
|mipmap-hdpi	|   72 x 72|
|mipmap-xhdpi   |	96 x 96|
|mipmap-xxhdpi  |	144 x 144|
|mipmap-xxxhdpi |	192 x 192|

其他
==

|dpi范围|	密度|	density分辨率|	图片icon尺寸|
|----------|-------------|-------------|-------------|
|0dpi ~ 120dpi	|ldpi	|0.75|	240x320 |	36x36
|120dpi ~ 160dpi|	mdpi(默认)|	1|	320x480	|48x48
|160dpi ~ 240dpi|	hdpi	|1.5|	480x800	|72x72
|240dpi ~ 320dpi|	xhdpi	|2	|720x1080	|96x96
|320dpi ~ 480dpi|	xxhdpi	|3	|1080x1920	|144x144
|480dpi ~ 640dpi|	xxxhdpi	|4	|1440x2560	|192x192

ppi就是pixel per inch，像素每英寸，用于连续调图像，
dpi则是dots per inch，一般对应打印机的分辨率。
即：ppi=dpi


iPhone6
主屏尺寸	4.7英寸
主屏分辨率	1334x750像素
屏幕像素密度	326ppi
屏幕像素密度=（1334*1334+750*750）的开方/4.7=326
所以iPhone6的尺寸图可放在xhdpi中


参考：

[Android性能优化之Bitmap的内存优化](https://www.tuicool.com/articles/3eMNr2n)


```
public static Bitmap readBitMap(Context context, int resId) {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inPreferredConfig = Bitmap.Config.RGB_565;
    opt.inPurgeable = true;
    opt.inInputShareable = true;
    //获取资源图片
    InputStream is = context.getResources().openRawResource(resId);
    return BitmapFactory.decodeStream(is, null, opt);
}
```


```
SoftReference<Bitmap> bitmap = new SoftReference(pNetBitmap);
if (bitmap != null) {
    if (bitmap.get() != null && !bitmap.get().isRecycled()) {
        bitmap.get().recycle();
        bitmap = null;
    }
}
```

参考：

[内存溢出（OOM）and内存泄露---及其解决](https://blog.csdn.net/taoolee/article/details/49718585)

[总结android面试题](https://blog.csdn.net/qq_33078541/article/details/50925842)

[AndroidStudio3.0 Android Profiler分析器(cpu memory network 分析器)](https://blog.csdn.net/niubitianping/article/details/72617864)

[Android 应用开发性能优化完全分析](http://android.jobbole.com/81746/)



Bitmap内存占用大小的计算
===============

一个BitMap位图占用的内存=图片长度*图片宽度*单位像素占用的字节数。

使用BitmapFactory来decode一张bitmap时，

其单位像素占用的字节数由其参数BitmapFactory.Options的inPreferredConfig变量决定。

(注：drawable目录下有的png图使用Bitmap.Config.RGB_565和ARGB_8888decode出来的大小一样，未解)

ALPHA_8:只有alpha值，没有RGB值，占一个字节。计算：size=w*h

ARGB_4444:一个像素占用2个字节，alpha(A)值，Red（R）值，Green(G)值，Blue（B）值各占4个bites,共16bites，
这种格式的图片，看起来质量太差，已经不推荐使用。计算：size=wh2

ARGB_8888:一个像素占用4个字节，alpha(A)值，Red（R）值，Green(G)值，Blue（B）值各占8个bites,共32bites,
即4个字节。这是一种高质量的图片格式，电脑上普通采用的格式。android2.3开始的默认格式。计算：size=wh4

RGB_565:一个像素占用2个字节，没有alpha(A)值，即不支持透明和半透明，
Red（R）值占5个bites ，Green(G)值占6个bites ，Blue（B）值占5个bites,共16bites,即2个字节。
对于没有透明和半透明颜色的图片并且不需要颜色鲜艳的来说，该格式的图片能够达到比较的呈现效果，
相对于ARGB_8888来说也能减少一半的内存开销，因此它是一个不错的选择。计算：size=wh2


实际开发中通过代码获取bitmap大小
```
     @TargetApi(Build.VERSION_CODES.KITKAT)
     public static int getBitmapSize(BitmapDrawable value) {
         Bitmap bitmap = value.getBitmap();
    if (VersionUtils.hasKitKat()) {
        return bitmap.getAllocationByteCount();
    }   if (VersionUtils.hasHoneycombMR1()) {
        return bitmap.getByteCount();
    }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
```

减少Bitmap对象的内存占用：

1. inBitmap:如果设置了这个字段，Bitmap在加载数据时可以复用这个字段所指向的bitmap的内存空间。但是，内存能够复用也是有条件的。在Android4.4（API Level 19）之前，只有新旧两个Bitmap的尺寸一样才能复用内存空间。Android4.4开始只要旧Bitmap的尺寸大于等于新的Bitmap就可以复用了。

2. inSampleSize：缩放比例，在把图片载入内存之前，我们需要先计算出一个合适的缩放比例，避免不必要的大图载入。

3. Decode format：解码格式，选择ARGB_8888 RBG_565 ARGB_444ALPHA_8，存在很大差异。

4. 使用更小的图片：在设计给到资源图片的时候，我们需要特别留意这张图片是否存在可以压缩的空间，是否可以使用一张更小的图片。尽量使用更小的图片不仅仅可以减少内存的使用，还可以避免出现大量的InfaltionException。假设有一张很大的 图片被XML文件直接引用，很有可能在初始化视图的时候就会因为内存不足而发生inflationException，这个问题的根本原因是发生了OOM。

参考：[bitmap的六种压缩方式，Android图片压缩](https://blog.csdn.net/HarryWeasley/article/details/51955467)

[Android面试系列2018总结(全方面覆盖Android知识结构)](https://mp.weixin.qq.com/s?__biz=MzA4NDM2MjAwNw==&mid=2650578278&idx=1&sn=649700edad3b55d5d6fd356c6ab7f4e2&chksm=87e06b1eb097e20865bc39b606f2579b59b4329cb49bf45462e4e4ac97debee929bac6165cd6&mpshare=1&scene=23&srcid=0826G42gZO5RmWuc1aud8FFn#rd)



图片有以下存在形式：
1.文件形式(即以二进制形式存在于硬盘上)
2.流的形式(即以二进制形式存在于内存中)
3.Bitmap形式：图片大小=宽px*高px*色彩模式单位像素所占内存

质量压缩：
特点: File形式的图片确实被压缩了, 但是当你重新读取压缩后的file为 Bitmap是,它占用的内存并没有改变

二次采样压缩：
特点：通过设置采样率, 减少图片的像素, 达到对内存中的Bitmap进行压缩

色彩模式压缩：
特点：由ARGB8888调整为GRB565内存占用减少一半

注意：
png不能色彩模式压缩
质量压缩改变的是file形式的大小
二次采样改变的是bitmap形式的大小

PNG格式有8位、24位、32位三种形式，
其中
8位PNG支持两种不同的透明形式（索引透明和alpha透明），
24位PNG不支持透明，
32位PNG在24位基础上增加了8位透明通道，因此可展现256级透明程度。
PNG8和PNG24后面的数字则是代表这种PNG格式最多可以索引和存储的颜色值。
8代表2的8次方也就是256色，而24则代表2的24次方大概有1600多万色。



当使用像 imageView.setBackgroundResource，imageView.setImageResource,
或者 BitmapFactory.decodeResource  这样的方法来设置一张大图片的时候，

这些函数在完成decode后，最终都是通过java层的createBitmap来完成的，需要消耗更多内存。

因此，改用先通过BitmapFactory.decodeStream方法，创建出一个bitmap，
再将其设为ImageView的 source，
decodeStream最大的秘密在于其直接调用JNI>>nativeDecodeAsset()来完成decode，
无需再使用java层的createBitmap，从而节省了java层的空间。
如果在读取时加上图片的Config参数，可以跟有效减少加载的内存，从而跟有效阻止抛out of Memory异常。

另外，需要特别注意：

decodeStream是直接读取图片资料的字节码了， 不会根据机器的各种分辨率来自动适应，

使用了decodeStream之后，需要在hdpi和mdpi，ldpi中配置相应的图片资源，

否则在不同分辨率机器上都是同样大小（像素点数量），显示出来的大小就不对了。


setImageResource/setBackgroundResource/setImageBitmap/直接xml设置src/直接xml设置background：

设置图片所占内存与设置的大小无关，且强制回收回收不掉，xml直接设置比set方法所占内存少

setImageResource:占用内存如下：
                20dp*20dp 占用55.34MB
                200dp*200dp 占用55.39MB
                match_parent 占用56.1MB
                所以设置图片src占用内存与设置尺寸无关

setBackgroundResource:占用内存如下：
                20dp*20dp 占用56.33MB
                200dp*200dp 占用55.37MB
                match_parent 占用55.36MB
setImageBitmap:占用内存如下：
                20dp*20dp 占用55.35MB
                200dp*200dp 占用55.34MB
                match_parent 占用55.43MB

直接设置src：
                20dp*20dp 占用49.91MB
                200dp*200dp 占用49.95MB
                match_parent 占用49.89MB

直接设置background：
                20dp*20dp 占用49.79MB
                200dp*200dp 占用49.91MB
                match_parent 占用49.83MB

glide设置图片：
                20dp*20dp 占用49.89MB
                200dp*200dp 占用49.98MB
                match_parent 占用55.88MB


















