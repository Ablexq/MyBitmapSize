
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



