## 说明：
适用于图片上传下载或 app 安装的阴影进度控件，
支持指定上下左右4个方向和失败提醒配置。

![gif](./captures/1.gif)

## 使用：
### xml
``` xml
  <com.thearyong.progressview.ProgressView
        android:id="@+id/pv"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_gravity="center_horizontal"
        android:layout_weight="1"
        android:background="@color/colorPrimaryDark"
        app:pv_direction="UP"
        app:pv_layer_color="#55111111"
        app:pv_progress="30"
        app:pv_text_color="#f1f1f1"
        app:pv_text_able="true"
        app:pv_text_size="28sp"/>

```

``` java
    ProgressView pv = (ProgressView) findViewById(R.id.pv);
    pv.setDirect(ProgressView.DIRECT.UP).setProgress(50);

```
