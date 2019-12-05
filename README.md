# WrapRecyclerView
可动态添加删除头部和底部的RecyclerView
## 功能：
1. 可添加指定index位置的头部和底部
2. 可添加自定义layout布局头部和底部
3. 可添加自定义view的头部和底部
4. 可删除指定index位置的头部和底部
5. 可删除指定的头部和底部view
6. 可删除已添加的layout头部和底部

## 用法：
* 在项目的根目录的build.gradle添加：
```
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

* 在应用模块的build.gradle添加：
```
  dependencies {
          implementation 'androidx.recyclerview:recyclerview:1.1.0'  //系统的RecyclerView
	  implementation 'com.github.summer-0:WrapRecyclerView:1.0.7'
	}
```
