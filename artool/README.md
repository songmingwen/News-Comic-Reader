特征文件生成工具使用
官方文档：https://github.com/artoolkitx/artoolkitx/wiki/Creating-and-using-NFT-markers,图片需要满足一下条件

追踪的图片必须是矩形图片。
图片必须是jpeg格式。(大部分商用AR SDK支持多种图片格式，比如EasyAR)
图片本身要有足够多的细节和边缘（自相似度较低，并且空间频率较高）。如果图片带有大量模糊或者细节较少的色块，追踪效果会比较差。
图片分辨率的提升会使ARToolKit提取出更多的特征点，这对于相机接近图片的情况或者使用高精度相机的情况，会大大提升追踪效果。

我这里提前将/SDK/bin配置到了环境变量里，所以可以直接使用，我在/SDK目录下新建了mark目录并找了一张图命名为lol.jpg拷贝了进来，然后打开cmd命令行执行
artoolkitx_genTexData D:\workspace\artoolkit\artoolkitx\SDK\mark\lol.jpg

回车之后会提示让你设置一些配置项，分为5步

1、Select extraction level for tracking features, 0(few) <--> 4(many), [default=2]

输入默认值2回车

解释：跟踪特征提取级别，按官方文档上说简单解释就是更大或更高分辨率的图像（更多像素）将允许以更高的细节级别提取特征点，因此当相机更接近图像时，或当使用更高分辨率相机时，将更好地跟踪，按道理级别越高特征文件应该会越大

2、Select extraction level for initializing features, 0(few) <--> 3(many), [default=1]

输入默认值1回车

解释：初始特征提取级别，应该跟跟踪特征级别一样的意义

3、Enter resolution to use (in decimal DPI)

输入480回车

想要使用的dpi尺寸，应该相当于你想显示的尺寸大小（这里可以用photoshop查看文档大小信息里的分辨率尺寸是多少像素/英寸，这里最好就填多少,也可以用widows照片查看器查看详细信息，详细信息里也有dpi信息），这里输入多少，那么能够提取特征的最大的dpi尺寸就是多少

4、Enter the minimum image resolution (DPI, in range [14.000, 480.000])

输入14.000回车

允许提取特征的最小的dpi尺寸

5、Enter the maximum image resolution (DPI, in range [14.000, 480.000])

输入360.000回车

允许提取特征的最大的dpi尺寸

最大最小分辨率范围，根据不同相机分辨率及相机远近有不同取值，一般使用20~120最为合适

设置完成之后就会在最小dpi到最大dpi尺寸之间提取不同dpi大小的特征数据



执行完之后会在lol.jpg目录生成lol.iset、lol.fset、lol.fset3三个文件，iset是图片原始数据，fset与fset3是特征数据