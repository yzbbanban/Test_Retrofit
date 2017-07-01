package com.bb.yzbbanban.test_retrofit;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by YZBbanban on 2017/6/30.
 */

public interface IUploadImg {
    @POST("postFile")
    @Multipart
    Call<ResponseBody> uploadImg(@Part MultipartBody.Part file);


}
