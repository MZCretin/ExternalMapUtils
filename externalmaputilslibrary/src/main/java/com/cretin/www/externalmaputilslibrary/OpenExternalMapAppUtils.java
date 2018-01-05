package com.cretin.www.externalmaputilslibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.cretin.www.externalmaputilslibrary.popwindow.SelectPopupWindow;
import com.cretin.www.externalmaputilslibrary.web.TbWebViewActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cretin on 2017/5/4.
 */

public class OpenExternalMapAppUtils {
    private static String[] paks = new String[]{"com.autonavi.minimap",//高德
            "com.baidu.BaiduMap"};     //百度
    public static final int TYPE_MAPVIEW_WITH_TIPS = 0;
    public static final int TYPE_MAPVIEW_DIRECTION = 1;
    public static final int TYPE_MAPVIEW_NAVI = 2;

    private static SelectPopupWindow menuWindow;

    //打开地图显示位置
    public static void openMapMarker(Activity activity, String longitude, String latitude,
                                     String title, String content, String appName) {
        List<String> mapApps = getMapApps(activity);
        if ( mapApps != null && !mapApps.isEmpty() ) {
            //有安装客户端 打开PopWindow显示数据
            if ( mapApps.contains(paks[0]) && mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, true,
                        true, longitude, latitude, title, "", "", "", content, appName);
            } else if ( mapApps.contains(paks[0]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, true, false,
                        longitude, latitude, title, "", "", "", content, appName);
            } else if ( mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, false, true,
                        longitude, latitude, title, "", "", "", content, appName);
            }
        } else {
            //没有安装客户端 打开网页版
            openBrosserMarkerMap(activity, longitude, latitude, appName, title, content, false);
        }
    }

    //打开地图显示位置
    public static void openMapMarker(Activity activity, String longitude, String latitude,
                                     String title, String content, String appName, boolean useOutWeb) {
        List<String> mapApps = getMapApps(activity);
        if ( mapApps != null && !mapApps.isEmpty() ) {
            //有安装客户端 打开PopWindow显示数据
            if ( mapApps.contains(paks[0]) && mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, true,
                        true, longitude, latitude, title, "", "", "", content, appName);
            } else if ( mapApps.contains(paks[0]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, true, false,
                        longitude, latitude, title, "", "", "", content, appName);
            } else if ( mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, false, true,
                        longitude, latitude, title, "", "", "", content, appName);
            }
        } else {
            //没有安装客户端 打开网页版
            openBrosserMarkerMap(activity, longitude, latitude, appName, title, content, useOutWeb);
        }
    }


    /**
     * 打开地图显示位置
     *
     * @param activity
     * @param longitude
     * @param latitude
     * @param title
     * @param content
     * @param appName
     * @param useOutWeb
     * @param forceUseBro 强制使用浏览器打开 不考虑是否有app
     */
    public static void openMapMarker(Activity activity, String longitude, String latitude,
                                     String title, String content, String appName, boolean useOutWeb, boolean forceUseBro) {
        if ( forceUseBro ) {
            openBrosserMarkerMap(activity, longitude, latitude, appName, title, content, useOutWeb);
        } else {
            List<String> mapApps = getMapApps(activity);
            if ( mapApps != null && !mapApps.isEmpty() ) {
                //有安装客户端 打开PopWindow显示数据
                if ( mapApps.contains(paks[0]) && mapApps.contains(paks[1]) ) {
                    showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, true,
                            true, longitude, latitude, title, "", "", "", content, appName);
                } else if ( mapApps.contains(paks[0]) ) {
                    showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, true, false,
                            longitude, latitude, title, "", "", "", content, appName);
                } else if ( mapApps.contains(paks[1]) ) {
                    showAlertDialog(activity, TYPE_MAPVIEW_WITH_TIPS, false, true,
                            longitude, latitude, title, "", "", "", content, appName);
                }
            } else {
                //没有安装客户端 打开网页版
                openBrosserMarkerMap(activity, longitude, latitude, appName, title, content, useOutWeb);
            }
        }
    }

    /**
     * 打开地图显示路径规划 网页版没有路径规划
     *
     * @param activity
     * @param sLongitude
     * @param sLatitude
     * @param sName
     * @param dLongitude
     * @param dLatitude
     * @param dName
     * @param appName
     */
    public static void openMapDirection(Activity activity, String sLongitude, String sLatitude,
                                        String sName, String dLongitude, String dLatitude,
                                        String dName, String appName) {
        List<String> mapApps = getMapApps(activity);
        if ( mapApps != null && !mapApps.isEmpty() ) {
            //有安装客户端 打开PopWindow显示数据
            if ( mapApps.contains(paks[0]) && mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_DIRECTION, true, true,
                        sLongitude, sLatitude, sName, dLongitude, dLatitude, dName, "", appName);
            } else if ( mapApps.contains(paks[0]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_DIRECTION, true, false,
                        sLongitude, sLatitude, sName, dLongitude, dLatitude, dName, "", appName);
            } else if ( mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_DIRECTION, false, true,
                        sLongitude, sLatitude, sName, dLongitude, dLatitude, dName, "", appName);
            }
        } else {
            //没有安装客户端 但是又没有网页版的 提示
            Toast.makeText(activity, "请下载百度或高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开地图导航 此方法只有目标点 用于调起客户端执行导航 若需要调起网页版 请调用此方法
     * {@link #openMapNaviWithTwoPoints}
     */
    public static void openMapNavi(Activity activity, String longitude, String latitude,
                                   String title, String content, String appName) {
        List<String> mapApps = getMapApps(activity);
        if ( mapApps != null && !mapApps.isEmpty() ) {
            //有安装客户端 打开PopWindow显示数据
            if ( mapApps.contains(paks[0]) && mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_NAVI, true, true,
                        longitude, latitude, title, "", "", "", content, appName);
            } else if ( mapApps.contains(paks[0]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_NAVI, true, false,
                        longitude, latitude, title, "", "", "", content, appName);
            } else if ( mapApps.contains(paks[1]) ) {
                showAlertDialog(activity, TYPE_MAPVIEW_NAVI, false, true,
                        longitude, latitude, title, "", "", "", content, appName);
            }
        } else {
            //没有安装客户端 但是又没有网页版的 提示
            Toast.makeText(activity, "请下载百度或高德地图客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开地图导航 如果需要调用网页版导航 需要提供两个点 只提供一个点的请调用
     * {@link #openMapNavi}
     */
    public static void openMapNaviWithTwoPoints(Activity activity, String sLongitude, String sLatitude,
                                                String sName, String dLongitude, String dLatitude, String dName, String region, String appName) {
        //百度地图网页版导航必须提供两个坐标点
        openBrosserNaviMap(activity, sLongitude, sLatitude, sName, dLongitude, dLatitude, dName, region, appName, false);

    }

    /**
     * 打开地图导航 如果需要调用网页版导航 需要提供两个点 只提供一个点的请调用 设置是否需要调用外部浏览器打开
     * {@link #openMapNavi}
     */
    public static void openMapNaviWithTwoPoints(Activity activity, String sLongitude, String sLatitude,
                                                String sName, String dLongitude, String dLatitude, String dName, String region, String appName, boolean useOutWeb) {
        //百度地图网页版导航必须提供两个坐标点
        openBrosserNaviMap(activity, sLongitude, sLatitude, sName, dLongitude, dLatitude, dName, region, appName, useOutWeb);

    }
    //************************************************************************
    //*************************    百度专区     *******************************
    //************************************************************************

    /**
     * 调起百度客户端 自定义打点
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     *
     * @param activity
     * @param longitude
     * @param latitude
     * @param title
     * @param content
     */
    public static void openBaiduMarkerMap(Context activity, String longitude, String latitude,
                                          String title, String content) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/marker?location=" + latitude + "," +
                        longitude + "&title=" + title + "&content=" + content + "&traffic=on"));
        activity.startActivity(intent);
    }

    /**
     * 调起百度客户端 展示地图
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * 范围参数
     * lat,lng,lat,lng (先纬度，后经度, 先左下,后右上)
     *
     * @param activity
     */
    public static void openBaiduCenterMap(Context activity, String cLongitude, String cLatitude,
                                          String zoom, boolean traffic, String lLatitude, String lLongitude, String rLatitude, String rLongitude) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/show?center=" + cLatitude + "," +
                        cLongitude + "&zoom=" + zoom + "&traffic=" + (traffic ? "on" : "off") +
                        "&bounds=" + lLatitude + "," + lLongitude + "," + rLatitude + "," + rLongitude));
        activity.startActivity(intent);
    }

    /**
     * 调起百度客户端 驾车导航
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     *
     * @param activity
     */
    public static void openBaiduNaviMap(Context activity, String longitude, String latitude) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/navi?location=" + latitude + "," + longitude));
        activity.startActivity(intent);
    }

    /**
     * 调起百度客户端 路径规划
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * lat,lng,lat,lng (先纬度，后经度, 先左下,后右上)
     *
     * @param activity
     */
    public static void openBaiduiDrectionMap(Context activity, String sLongitude, String sLatitude, String sName,
                                             String dLongitude, String dLatitude, String dName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("baidumap://map/direction?origin=name:" +
                        sName + "|latlng:" + sLatitude + "," + sLongitude + "&destination=name:" +
                        dName + "|latlng:" + dLatitude + "," + dLongitude + "&" +
                        "mode=transit&sy=0&index=0&target=0"));
        activity.startActivity(intent);
    }


    //************************************************************************
    //*************************    高德专区     *******************************
    //************************************************************************


    /**
     * 调起高德客户端 展示标注点
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * 根据名称或经纬度，启动高德地图产品展示一个标注点，如分享位置，标注店铺。支持版本V4.1.3起。
     *
     * @param activity
     * @param longitude
     * @param latitude
     * @param appName
     * @param poiname
     */
    public static void openGaodeMarkerMap(Context activity, String longitude, String latitude,
                                          String appName, String poiname) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://viewMap?sourceApplication=" +
                        appName + "&poiname=" + poiname + "&lat=" + latitude + "&lon=" + longitude + "&dev=1"));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 路径规划
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     * 输入起点和终点，搜索公交、驾车或步行的线路。支持版本 V4.2.1 起。
     *
     * @param activity
     * @param sLongitude
     * @param sLatitude
     * @param sName
     * @param dLongitude
     * @param dLatitude
     * @param dName
     * @param appName
     */
    public static void openGaodeRouteMap(Context activity, String sLongitude, String sLatitude, String sName,
                                         String dLongitude, String dLatitude, String dName, String appName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("amapuri://route/plan/?sourceApplication=" + appName +
                        "&sid=&slat=" + sLatitude + "&slon=" +
                        sLongitude + "&sname=" + sName + "&did=&dlat=" +
                        dLatitude + "&dlon=" + dLongitude + "&dname=" + dName + "&dev=1&t=1"));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 我的位置
     * 显示我当前的位置。支持版本V4.2.1 起。
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeMyLocationMap(Context activity, String appName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://myLocation?sourceApplication=" + appName));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 导航
     * 输入终点，以用户当前位置为起点开始路线导航，提示用户每段行驶路线以到达目的地。支持版本V4.1.3 起。
     * lat,lng (先纬度，后经度)
     * 40.057406655722,116.2964407172
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeNaviMap(Context activity, String appName, String poiname,
                                        String latitude, String longitude) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://navi?sourceApplication=" + appName + "&poiname=" +
                        poiname + "&lat=" + latitude + "&lon=" + longitude + "&dev=1&style=2"));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 公交线路查询
     * 输入公交线路名称，如 445，搜索该条线路经过的所有公交站点。支持版本 v4.2.1 起。
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeBusMap(Context activity, String appName, String busName,
                                       String city) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://bus?sourceApplication=" + appName + "&busname=" + busName + "&city=" + city));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    /**
     * 调起高德客户端 地图主图
     * 进入高德地图主图页面。支持版本V4.2.1起。
     *
     * @param activity
     * @param appName
     */
    public static void openGaodeRootmapMap(Context activity, String appName) {
        Intent intent = new Intent("android.intent.action.VIEW",
                android.net.Uri.parse("androidamap://rootmap?sourceApplication=" + appName));
        intent.setPackage("com.autonavi.minimap");
        activity.startActivity(intent);
    }

    //************************************************************************
    //*************************    网页专区     *******************************
    //************************************************************************

    /**
     * 打开网页版 反向地址解析
     *
     * @param activity
     * @param longitude
     * @param latitude
     * @param appName
     * @param useOutWeb 是否调用外部的浏览器 默认不调用
     */
    public static void openBrosserMarkerMap(Context activity, String longitude, String latitude,
                                            String appName, String title, String content, boolean useOutWeb) {
        if ( useOutWeb ) {
            Uri mapUri = Uri.parse("http://api.map.baidu.com/marker?location=" + latitude + "," + longitude +
                    "&title=" + title + "&content=" + content + "&output=html&src=" + appName);
            Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
            activity.startActivity(loction);
        } else {
            TbWebViewActivity.startActivity(activity, title, "http://api.map.baidu.com/marker?location=" + latitude + "," + longitude +
                    "&title=" + title + "&content=" + content + "&output=html&src=" + appName);
        }
    }

    /**
     * 打开网页版 导航
     *
     * @param activity
     * @param region    当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。
     * @param appName
     * @param useOutWeb 是否调用外部的浏览器 默认不调用
     */
    public static void openBrosserNaviMap(Context activity, String sLongitude, String sLatitude,
                                          String sName, String dLongitude, String dLatitude, String dName, String region, String appName, boolean useOutWeb) {
        if ( useOutWeb ) {
            Uri mapUri = Uri.parse("http://api.map.baidu.com/direction?origin=latlng:" +
                    sLatitude + "," + sLongitude + "|name:" + sName + "&destination=latlng:" +
                    dLatitude + "," + dLongitude + "|name:" + dName + "&mode=driving&region=" + region +
                    "&output=html&src=" + appName);
            Intent loction = new Intent(Intent.ACTION_VIEW, mapUri);
            activity.startActivity(loction);
        } else {
            TbWebViewActivity.startActivity(activity, "导航", "http://api.map.baidu.com/direction?origin=latlng:" +
                    sLatitude + "," + sLongitude + "|name:" + sName + "&destination=latlng:" +
                    dLatitude + "," + dLongitude + "|name:" + dName + "&mode=driving&region=" + region +
                    "&output=html&src=" + appName);
        }
    }

    /**
     * 打开网页版 导航
     *
     * @param activity
     * @param region   当给定region时，认为起点和终点都在同一城市，除非单独给定起点或终点的城市。
     * @param appName
     */
    public static void openBrosserNaviMap(Context activity, String sLongitude, String sLatitude,
                                          String sName, String dLongitude, String dLatitude, String dName, String region, String appName) {
        openBrosserNaviMap(activity, sLongitude, sLatitude, sName, dLongitude, dLatitude, dName, region, appName, false);
    }


    /**
     * 显示对话框
     */
    public static void showAlertDialog(final Activity activity, final int type, boolean showGaode,
                                       boolean showBaidu, final String longitude,
                                       final String latitude, final String title,
                                       final String dLongitude,
                                       final String dLatitude, final String dName,
                                       final String content, final String appName) {
        menuWindow = new SelectPopupWindow(activity, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(View view) {
                if ( view.getId() == R.id.btn_select_baidu ) {
                    //百度
                    if ( type == TYPE_MAPVIEW_WITH_TIPS ) {
                        openBaiduMarkerMap(activity, longitude, latitude, title, content);
                    } else if ( type == TYPE_MAPVIEW_DIRECTION ) {
                        openBaiduiDrectionMap(activity, longitude, latitude, title, dLongitude, dLatitude, dName);
                    } else if ( type == TYPE_MAPVIEW_NAVI ) {
                        openBaiduNaviMap(activity, longitude, latitude);
                    }
                } else if ( view.getId() == R.id.btn_select_gaode ) {
                    //高德
                    if ( type == TYPE_MAPVIEW_WITH_TIPS ) {
                        openGaodeMarkerMap(activity, longitude, latitude, appName, title);
                    } else if ( type == TYPE_MAPVIEW_DIRECTION ) {
                        openGaodeRouteMap(activity, longitude, latitude, title, dLongitude, dLatitude, dName, appName);
                    } else if ( type == TYPE_MAPVIEW_NAVI ) {
                        openGaodeNaviMap(activity, appName, title, latitude, longitude);
                    }
                }
            }
        }, showGaode, showBaidu);
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = activity.getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }

    /**
     * 通过包名获取应用信息
     *
     * @param context
     * @param packageName
     * @return
     */
    private static String getAppInfoByPak(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for ( PackageInfo packageInfo : packageInfos ) {
            if ( packageName.equals(packageInfo.packageName) ) {
                return packageName;
            }
        }
        return null;
    }

    /**
     * 返回当前设备上的地图应用集合
     *
     * @param context
     * @return
     */
    private static List<String> getMapApps(Context context) {
        LinkedList<String> apps = new LinkedList<>();
        for ( String pak : paks ) {
            String appinfo = getAppInfoByPak(context, pak);
            if ( appinfo != null ) {
                apps.add(appinfo);
            }
        }
        return apps;
    }
}
