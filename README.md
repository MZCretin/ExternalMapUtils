# ExternalMapuUilsLibrary

## 最新版本V1.1.0
png文件夹下有截图文件 自行查看

![image](https://github.com/MZCretin/ExternalMapUtils/blob/master/png/Screenshot_20170509-152819.png)

![视频](https://github.com/MZCretin/ExternalMapUtils/blob/master/movie/movie.mp4)

## 更新说明(2018年01月05日14:46:39)

   + 考虑到每次跳转浏览器可能会被浏览器的推送或资讯带走客户，体验不佳，所以添加了App内部WebView展示地图的功能；
   + 新增几个不同的方法，现在可以自由的控制：是否检测本地是否有地图类APP；内部WebView显示地图还是外部浏览器


## 使用方式：

-------------------

**Step 1.** Add the JitPack repository to your build file Add it in your root build.gradle at the end of repositories: 
```gradle
allprojects { repositories { ... maven { url 'https://jitpack.io' } } }
```

**Step 2.** Add the dependency
```gradle
dependencies { compile 'com.github.MZCretin:ExternalMapUtils:v1.1.0' }
```

**Step 3.** Start using it wherever you want as below.

```
//打开路径规划
OpenExternalMapAppUtils.openMapDirection(this, split[0], split[1], sName,
                split1[0], split1[1], dName, "测试DEMO");
//打开marker一个点   
OpenExternalMapAppUtils.openMapMarker(this, longitude, latitude, name, des, "测试DEMO");
//打开网页版导航
OpenExternalMapAppUtils.openBrosserNaviMap(this, split[0], split[1], sName,
                split1[0], split1[1], dName, "深圳", "测试DEMO");
//打开app端导航 使用高德和百度
OpenExternalMapAppUtils.openMapNavi(this, longitude, latitude, name, des, "测试DEMO");
                
```


## 使用说明

我只是简单的对百度和高度的Uri使用方式进行了一个简单的归纳和总结，只是为了使用起来更加方便，开发者就应该全力注重业务逻辑，而不是花时间东找西找。所以做个简单集成。


#### 有什么意见或者建议欢迎与我交流，觉得不错欢迎Star

我叫Cretin，我是可爱的小男孩

使用过程中如果有什么问题或者建议 欢迎在issue中提出来或者直接联系我 QQ:792075058 添加好友请说明来意  嘿嘿

