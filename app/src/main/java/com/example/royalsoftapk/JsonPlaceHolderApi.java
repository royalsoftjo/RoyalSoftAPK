package com.example.royalsoftapk;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
public String Url=conRoyal.ConIpRoyal;

    @Headers("Content-Type: application/json")
    @POST("SaveTabletOrderSales")
    Call<post> createPost(@Body post post);


    @Headers("Content-Type: application/json")
    @POST("SaveOrderTables")
    Call<Post_OrderDetails> InsertOrder(@Body Post_OrderDetails post_OrderDetails);


    @Headers("Content-Type: application/json")
    @POST("SaveOrderCashVanPOS")
    Call<post_SaveCashVan> InsertOrder(@Body post_SaveCashVan post_saveCashVan);


    @Headers("Content-Type: application/json")
    @POST("PrintSaveOrderCashVanPOS")
    Call<post_SaveCashVan> PrintSaveOrderCashVanPOS(@Body post_SaveCashVan post_saveCashVan);

    @Headers("Content-Type: application/json")
    @POST("SaveCashVoucherCashVanPOS")
    Call<post_CashVoucher> InsertOrder(@Body post_CashVoucher post_CashVoucher);

    @Headers("Content-Type: application/json")
    @POST("InsertCashVanPosRound")
    Call<ClsCashVanPosRound> InsertCashVanPosRound(@Body ClsCashVanPosRound ClsCashVanPosRound);

    @FormUrlEncoded
    @POST("SaveTabletOrderSales")
    Call<post> createpp(
            @Field("conn") String conn,
            @Field("BranchId") String BranchId,
            @Field("StoreId") String StoreId,
            @Field("VendorId") String VendorId,
            @Field("ReceiverName") String ReceiverName,
            @Field("Note") String Note,
            @Field("VoucherNumber") String VoucherNumber,
            @Field("VoucherDate") String VoucherDate,
            @Field("Reference") String Reference,
            @Field("TransactionID") String TransactionID,
            @Field("CustomerId") String CustomerId,
            @Field("PaymentId") String PaymentId,
            @Field("SalesManID") String SalesManID,
            @Field("Details") String Details
    );
}
