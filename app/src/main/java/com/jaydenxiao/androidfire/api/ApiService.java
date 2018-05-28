package com.jaydenxiao.androidfire.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jaydenxiao.androidfire.bean.BKBroadDisplayData;
import com.jaydenxiao.androidfire.bean.BkDept;
import com.jaydenxiao.androidfire.bean.BkDeptData;
import com.jaydenxiao.androidfire.bean.BkOrderData;
import com.jaydenxiao.androidfire.bean.BkSearchData;
import com.jaydenxiao.androidfire.bean.BkTSendData;
import com.jaydenxiao.androidfire.bean.BsLSumm;
import com.jaydenxiao.androidfire.bean.BsListEntity;
import com.jaydenxiao.androidfire.bean.GirlData;
import com.jaydenxiao.androidfire.bean.NewsDetail;
import com.jaydenxiao.androidfire.bean.NewsSummary;
import com.jaydenxiao.androidfire.bean.SendLoginData;
import com.jaydenxiao.androidfire.bean.User;
import com.jaydenxiao.androidfire.bean.VideoData;
import com.jaydenxiao.common.basebean.BaseRespose;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * des:ApiService
 * Created by xsf
 * on 2016.06.15:47
 */
//          https://www.jianshu.com/p/7687365aa946
//@Path： 所有在网址中的参数（URL的问号前面），如：
//        http://102.10.10.132/api/Accounts/{accountId}
//@Query：URL问号后面的参数，如：
//        http://102.10.10.132/api/Comments?access_token={access_token}
//@QueryMap：相当于多个@Query
//@Field：用于POST请求，提交单个数据
//@Body： 相当于多个@Field，以对象的形式提交
// Tip1
//        使用@Field时记得添加@FormUrlEncoded
// Tip2
//        若需要重新定义接口地址，可以使用@Url，将地址以参数的形式传入即可
public interface ApiService {

    @GET("login")
    Observable<BaseRespose<User>> login(@Query("username") String username, @Query("password") String password);

    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewDetail(
            @Header("Cache-Control") String cacheControl,//缓存的方式
            @Path("postId") String postId);

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsSummary>>> getNewsList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type, @Path("id") String id,
            @Path("startPage") int startPage);

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Header("Cache-Control") String cacheControl,
            @Url String photoPath);
    //@Url，它允许我们直接传入一个请求的URL。这样以来我们可以将上一个请求的获得的url直接传入进来，baseUrl将被无视
    // baseUrl 需要符合标准，为空、""、或不合法将会报错

    @GET("data/福利/{size}/{page}")
    Observable<GirlData> getPhotoList(
            @Header("Cache-Control") String cacheControl,
            @Path("size") int size,
            @Path("page") int page);

    @GET("nc/video/list/{type}/n/{startPage}-10.html")
    Observable<Map<String, List<VideoData>>> getVideoList(
            @Header("Cache-Control") String cacheControl,
            @Path("type") String type,
            @Path("startPage") int startPage);

    @GET("api/ApiAdmin/MessageDetails/{id}")
    Observable<JSONObject> getMessageDetail(
            @Path("id") String id);

    //使用@Field时记得添加@FormUrlEncoded
    @FormUrlEncoded
    @POST("api/Project/GetStationList")
    Observable<JSONObject> getJZList(
            @Field("Level") String level,
            @Field("No") String no
    );

    @FormUrlEncoded
    @POST("api/Project/GetStationList")
    Observable<BsListEntity> getJZList1(
            @Field("Level") String level,
            @Field("No") String no
    );

    @FormUrlEncoded
    @POST("api/Project/GetStationList")
    Observable<BsLSumm> getJZList2(
            @Field("Level") String level,
            @Field("No") String no
    );

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @GET("autoLogin")
    Observable<SendLoginData> CheckLogin(
                    @Query("username") String username,
                    @Query("password") String password
            );


    @GET("android/getAllDept")
    Observable<JSONObject> getAllDept();

    /**
     * 根据deptbox虚拟信箱号 获取报箱号,已发报刊的信息
     * @param deptBox
     * @return
     */
    @GET("android/getAllDept")
    Observable<BkDeptData> getBkDeptData(
            @Query("deptBox") int deptBox
    );

    @POST("android/getAllDept")
    Observable<JSONArray> getBkDeptData1(@Body BkDept bkDept);

    /**
     * 根据报刊 postCode查询该报刊的所有部门
     * @param category
     * @param year
     * @param domesticPostCode
     * @return
     */
    @GET("android/orderNumByBk")
    Observable<JSONArray> getBkSendData(
            @Query("category") int category,
            @Query("year") int year,
            @Query("domesticPostCode") String domesticPostCode
    );

    @GET("android/orderNumByBk")
    Observable<JSONArray> getBkSendData1(
            @Query("category") int category,
            @Query("year") int year,
            @Query("domesticPostCode") String domesticPostCode
    );

    /**
     * 根据报刊模糊查询报刊
     * @param bkName
     * @param category
     * @param year
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @GET("android/searchBkInfo")
    Observable<BkSearchData> getBkSearchData(
            @Query("bkName") String bkName,
            @Query("domesticPostCode") String domesticPostCode,
            @Query("category") int category,
            @Query("year") int year,
            @Query("pageNumber") int pageNumber,
            @Query("pageSize") int pageSize
    );

    @GET("android/todaySend")
    Observable<BkTSendData> getTSendData();

    /**
     * 更新订单也就是已发报刊
     * @param orderId
     * @return
     */
    @GET("android/setOrderTime")
    Observable<BkOrderData> getBkOrderData(
            @Query("orderId") int orderId
    );

    /**
     * 获取需要点亮的报箱
     * http://rj.zzx1983.com:30034/android/getDeptByType
     */
    @GET("android/getDeptByType")
    Observable<JSONArray> getAllDeptData(
            @Query("type") int type
    );

    //硬件请求服务器地址

    /**
     * 控制单个白灯灯亮 1亮0暗
     */
    @GET("gdsf/onOff")
    Observable<String> getOnoffData(
            @Query("idint") int idint,
            @Query("statusint") int statusint
    );

    /**
     * idint 地址  eAddrint 数码管id  设置对应数码管数字
     */
    @GET("gdsf/display")
    Observable<JSONObject> getdisplayData1(
            @Query("idint") int idint,
            @Query("eAddrint") int eAddrint,
            @Query("valueint") int valueint
    );
    /**
     * idint 地址  eAddrint 数码管id  valueint设置对应数码管数字
     */
    @GET("gdsf/display")
    Observable<JSONObject> getdisplayData(
            @Query("idint") int idint,
            @Query("eAddrint") int eAddrint,
            @Query("valueint") int valueint
    );
    /**
     * 控制所有白灯灯亮 1亮0暗
     */
    @GET("gdsf/broadcastOnOff")
    Observable<String> getbroadcastOnOffData(
            @Query("statusint") int statusint
    );

    /**
     * 统一设置eAddrint为1的所有数码管的数字
     */
    @GET("gdsf/broadcastDisplay")
    Observable<BKBroadDisplayData> getbroadcastDisplayData(
            @Query("eAddrint") int eAddrint,
            @Query("valueint") int valueint
    );

    /**
     * 开始一次性分发报纸数量
     * http://10.71.23.103:8778/gdsf/multipleDisplayControl?jsonString=
     */
    @GET("gdsf/multipleDisplayControl")
    Observable<JSONObject> getStartSendALLPaper(
            @Query("jsonString") String result
//            @Query("id1") int id1,
//            @Query("values1") int values1,
//            @Query("eaddr1") int eaddr1,
//            @Query("id2") int id2,
//            @Query("values2") int values2,
//            @Query("eaddr2") int eaddr2
    );

}
