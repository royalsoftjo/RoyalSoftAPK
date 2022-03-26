package com.example.royalsoftapk;

import java.util.ArrayList;

public class conRoyal {


    public  static  String VendorID="0";
    public static String ConnectionString="";

    public static ArrayList<Cls_ItemsHistoryIncomeVoucher> alistIncome =new ArrayList<Cls_ItemsHistoryIncomeVoucher>();
    public static ArrayList<Cls_ItemsInvoiceSales> listInvoiceSales=new ArrayList<Cls_ItemsInvoiceSales>();
    //------------------------------Url-------------------------------------//


   public static  String ConIpImg= "http://www.eleganzafitnesscenter.com/APIAPK/Img/";
    public static  String ConIpRoyal= "http://www.eleganzafitnesscenter.com/APIAPK/api/Inventory";
    public static  String ConIpRoyal2= "http://www.eleganzafitnesscenter.com/APIAPK/api/Inventory/";
    //-------------------------------------------------------------------//
    public static final String B_versionCode= "1.0.0";
    public static String A_versionCode=null;
    public static String Flag_compulsory=null;
    //-------------------Fun Url User-------------------------------//

    public static String Url_Login;
    public static String Url_VersionCode;
    public static String Url_Access_Screen;
    public static String Url_Cash_Dolar;
    public static String Url_feedBackSave;
    public static String Url_SessionOpen;
    public static String Url_OutgoingVoucher;
    public static String Url_SelectItemsToPrepareByEmployee;
    public static String Url_SelectCompositeOrdermerge;
    public static String Url_UpdateItemsToPrepareByEmployeeByID;
    public static String Url_UpdateStateCompositeOrderMergeByID;
    public static String Url_IncomingVoucher;
    public static String Url_Name_Employees;
    public static String Url_Name_Stations;
    public static String Url_DefultCmb;
    public static String Url_SelectDataItemBarcode;
    public static String Url_PurchasePrice;
    public static String Url_StoreCmb;
    public static String Url_SupplierCmb;
    public static String Url_OfferHeaders;
    public static String Url_Cash_Count;
    public static String Url_Load_advertising;
    public static String Url_Custody_staff;
    public static String Url_info_company;
    public static String Url_FingerPrintPost;
    public static String Url_Send_Message;
    public static String Url_Close_Cash;
    public static String Url_SavePDAIncomeVoucher;
    public static String Url_SavePDAIncomeVoucherDetails;
    public static String Url_SavePDAIncomeVoucherDetailsUpdateItem;
    public static String Url_SavePDAIncomeVoucherDetailsDeleteItem;
    public static String Url_DeletePDAIncomeVoucherCancel;
    public static String Url_ItemquantityPDA;
    public static String Url_repshelftag;
    public static String Url_repshelftag2;
    public static String Url_UpdateSalesPrice;
    public static String Url_SelectListItemsALL;
    public static String Url_GetCustomer;
    public static String Url_GetVendor;
    public static String Url_GetCategory;
    public static String Url_GetSubCategory;
    public static String Url_InsertItems;
    public static String Url_SelectSections;
    public static String Url_SelectTables;
    public static String Url_SelectItemsByCategoryID;
    public static String Url_SelectItemAdditionsByItemID;
    public static String Url_UpdateStatusTableByTableID;
    public static String Url_LoginIsHoldResturent;
    public static String Url_LoginIsCashVan;
    public static String Url_SelectOrderHeaderByTableID;
    public static String Url_SelectOrderDetailsByTableID;
    public static String Url_SaveVendorLogin;
    public static String Url_Query;
    public static String Url_GetItemsCashVanPOSByCategoryID;
    public static String Url_GetSelectItemsByCategoryIDForCashVanForCustomerID;
    public static String Url_GetSaveCashVanRoutesAuto;
    public static String Url_InsertCustomerERP;
    public static String Url_GetSelectMaxVoucherHeaderId;
    public static String Url_GetPrintSaveOrderCashVanPOSBefore;
    public static String Url_GetPrintCashVoucherCashVanPOS;
    public static String Url_GetSelectCashVanPosDashBoard;
    public static String Url_GetSelectItemopsByItemID;
    public static String Url_GetLoginIsFeedBack;


    //-------------------Fun Url User------------------------------//
    //-------------------Load Data User-----------------------------
    public static int IDUser;

    public static String UserName="";
    public static  String Password="";
    public static  String ipStatic="";
    public static  String DataBase_Name="";
    public static  String User_DB="";
    public static  String Password_DB="";
    public static  String StartDate="";
    public static  String EndDate="";
    public static  String Activate_User="";
    //-------------------Load Data User-----------------------------

    public static String  IncomeVoucherHeaderID;
    //----------------------Access , Screen_id-----------------------------------------



    public static int Access_Home_ID;
    public static int Access_Pos_ID;
    public static int Access_Inventory_ID;
    public static int Access_Account_ID;
    public static int Access_Emp_ID;
    public static int Access_PDA;
    public static int Access_Tablet;
    public static int Access_SettingsDevice;
    public static int Access_Updatingdata;
    public static int Access_Data_migration;

    public static int Sccess_Home_ID=0;
    public static int Sccess_Pos_ID=0;
    public static int Sccess_Inventory_ID=0;
    public static int Sccess_Account_ID=0;
    public static int Sccess_Emp_ID=0;
    public static int Sccess_PDA=0;
    public static int Sccess_Tablet=0;
    public static int Sccess_SettingsDevice=0;
    public static int Sccess_Updatingdata=0;
    public static int Sccess_Data_migration=0;
    //-------------------------Access , Screen_id--------------------------------------

    public static void UrlRoyalLogin(String User,String Pass)
    {
         Url_Login= ConIpRoyal+"/Login?User_Name="+User+"&Password="+Pass;

    }

    public static void UrlRoyal_VersionCode()
    {
        Url_VersionCode= ConIpRoyal+"/versionCode";

    }


    public static void UrlRoyal_Access_Screen(int User_Id)
    {
        Url_Access_Screen= ConIpRoyal+"/Access_Screen?User_Id="+User_Id;

    }

    public static void UrlRoyal_Cash_Dolar(String conn)
    {
        Url_Cash_Dolar= ConIpRoyal+"/Cash_WorkDay?conn="+conn;

    }
    public static void UrlRoyal_feedBackSave(String conn,String Aname,String Number,int t1,int t2,int t3,boolean way,String Note)
    {
        Url_feedBackSave= ConIpRoyal+"/feedBackSave?conn="+conn+"&Aname="+Aname+"&Number="+Number+"&t1="+t1+"&t2="+t2+"&t3="+t3+"&way="+way+"&Note="+Note+"";
    }

    public static void UrlRoyal_SessionOpen(String conn)
    {
        Url_SessionOpen= ConIpRoyal+"/SessionOpen?conn="+conn;

    }

    public static void UrlRoyal_OutgoingVoucher(String conn,int StationID,String WorkDayDate,int CreationUserID)
    {
        Url_OutgoingVoucher= ConIpRoyal+"/OutgoingVoucher?conn="+conn+"&StationID="+StationID+"&WorkDayDate="+WorkDayDate+"&CreationUserID="+CreationUserID;

    }

    public static void UrlRoyal_SelectItemsToPrepareByEmployee(int EmployeeID)
    {
        Url_SelectItemsToPrepareByEmployee= ConIpRoyal+"/SelectItemsToPrepareByEmployee?EmployeeID="+EmployeeID;

    }

    public static void UrlRoyal_SelectCompositeOrdermerge(int EmployeeID)
    {
        Url_SelectCompositeOrdermerge= ConIpRoyal+"/SelectCompositeOrdermerge?EmployeeID="+EmployeeID;

    }
    public static void UrlRoyal_UpdateItemsToPrepareByEmployeeByID(int ID, double width, double length)
    {
        Url_UpdateItemsToPrepareByEmployeeByID= ConIpRoyal+"/UpdateItemsToPrepareByEmployeeByID?ID="+ID+"&width="+width+"&length="+length+"";

    }

    public static void UrlRoyal_UpdateStateCompositeOrderMergeByID(int ID)
    {
        Url_UpdateStateCompositeOrderMergeByID= ConIpRoyal+"/UpdateStateCompositeOrderMergeByID?ID="+ID;

    }

    public static void UrlRoyal_IncomingVoucher(String conn,int StationID,String WorkDayDate,int CreationUserID)
    {
        Url_IncomingVoucher= ConIpRoyal+"/IncomingVoucher?conn="+conn+"&StationID="+StationID+"&WorkDayDate="+WorkDayDate+"&CreationUserID="+CreationUserID;

    }


    public static void UrlRoyal_ConnectionString()
    {
        ConnectionString= "Data Source="+ipStatic+";Initial Catalog="+DataBase_Name+";Persist Security Info=True;User ID="+User_DB+";Password="+Password_DB;

    }
    public static void UrlRoyal_ConnectionString2(String ConnectionStringt)
    {
        ConnectionString= ConnectionStringt;

    }
    public static void UrlRoyal_Name_Employees(String conn)
    {
        Url_Name_Employees= ConIpRoyal+"/Name_Employees?conn="+conn;

    }


    public static void UrlRoyal_Name_Stations(String conn)
    {
        Url_Name_Stations= ConIpRoyal+"/Name_Stations?conn="+conn;

    }

    public static void UrlRoyal_cmb_Fill(String conn,String Name)
    {
        Url_DefultCmb= ConIpRoyal+"/"+Name+"?conn="+conn;
    }


    public static void UrlRoyal_cmb_FillStore(String conn,String Name,String BranchID)
    {
        Url_StoreCmb= ConIpRoyal+"/"+Name+"?conn="+conn+"&BranchID="+BranchID;
    }

    public static void UrlRoyal_cmb_FillSupplier(String conn)
    {
        Url_SupplierCmb= ConIpRoyal+"/"+"Supplier"+"?conn="+conn;
    }

    public static void UrlRoyal_SelectDataItemBarcode(String conn,String Barcode)
    {
        Url_SelectDataItemBarcode= ConIpRoyal+"/"+"Item"+"?conn="+conn+"&Barcode="+Barcode+"";
    }

    public static void UrlRoyal_SelectPurchasePrice(String conn,String Barcode)
    {
        Url_PurchasePrice= ConIpRoyal+"/"+"PurchasePrice"+"?conn="+conn+"&Barcode="+Barcode+"";
    }

    public static void UrlRoyal_OfferHeaders(String conn)
    {
        Url_OfferHeaders= ConIpRoyal+"/OfferHeaders?conn="+conn;
    }

    public static void UrlRoyal_Cash_Count(String conn, String WorkDayDate1 ,String WorkDayDate2)
    {
        Url_Cash_Count= ConIpRoyal+"/Closingbalance?conn="+conn+"&WorkDayDate1="+WorkDayDate1+"&WorkDayDate2="+WorkDayDate2;

    }

    public static void UrlRoyal_Load_advertising()
    {
        Url_Load_advertising= ConIpRoyal+"/Load_advertising";
    }


    public static void UrlRoyal_Custody_staff(String conn)
    {
        Url_Custody_staff= ConIpRoyal+"/Custody_staff?conn="+conn;
    }



    public static void UrlRoyal_info_company(String conn)
    {
        Url_info_company= ConIpRoyal+"/info_company?conn="+conn;
    }

    public static void UrlRoyal_FingerPrintPost(String conn,String FingerPrintDate1)
    {
        Url_FingerPrintPost= ConIpRoyal+"/FingerPrintPost?conn="+conn+"&FingerPrintDate1="+FingerPrintDate1;
    }

    public static void UrlRoyal_Send_Message(String conn,String StationID,String UserID,String text_Message)
    {
        Url_Send_Message= ConIpRoyal+"/Send_Message?conn="+conn+"&StationID="+StationID+"&UserID="+UserID+"&text_Message="+text_Message+"&active_Message=True";
    }
    public static void UrlRoyal_Close_Cash(String conn , String StationID,String Flag)
    {
        Url_Close_Cash=ConIpRoyal+"/Close_Cash?conn="+conn+"&StationID="+StationID+"&Flag="+Flag+"";
    }

    public static void UrlRoyal_SavePDAIncomeVoucher(String conn, String BranchId, String StoreId, String VendorId,
                                                     String ReceiverName, String Note, String VoucherNumber, String VoucherDate, String Reference, String TransactionID)
    {
        Url_SavePDAIncomeVoucher=ConIpRoyal+"/SavePDAIncomeVoucherHeader?conn="+conn+"&BranchId="+BranchId+"&StoreId="+StoreId+"&VendorId="+VendorId+"&ReceiverName="+ReceiverName+"&Note="+Note+"&VoucherNumber="+VoucherNumber+"&VoucherDate="+VoucherDate+"&Reference="+Reference+"&TransactionID="+TransactionID;
       // Url_SavePDAIncomeVoucher=ConIpRoyal+"/SavePDAIncomeVoucher";
     }


    public static void UrlRoyal_SavePDAIncomeVoucherDetails(String conn, String HeaderID, String Details)
    {
        Url_SavePDAIncomeVoucherDetails=ConIpRoyal+"/SavePDAIncomeVoucherDetails?conn="+conn+"&HeaderID="+HeaderID+"&Details="+Details;
        // Url_SavePDAIncomeVoucher=ConIpRoyal+"/SavePDAIncomeVoucher";
    }


    public static void UrlRoyal_UpdatePDAIncomeVoucherDetailsItem(String conn, String HeaderID, String Details)
    {
        Url_SavePDAIncomeVoucherDetailsUpdateItem=ConIpRoyal+"/UpdatePDAIncomeVoucherDetails?conn="+conn+"&HeaderID="+HeaderID+"&Details="+Details;
    }

    public static void UrlRoyal_DeletePDAIncomeVoucherDetailsItem(String conn, String HeaderID, String Details)
    {
        Url_SavePDAIncomeVoucherDetailsDeleteItem=ConIpRoyal+"/DeletePDAIncomeVoucherDetails?conn="+conn+"&HeaderID="+HeaderID+"&Details="+Details;
    }

    public static void UrlRoyal_DeletePDAIncomeVoucherCancel(String conn, String HeaderID)
    {
        Url_DeletePDAIncomeVoucherCancel=ConIpRoyal+"/DeletePDAIncomeVoucherCancel?conn="+conn+"&HeaderID="+HeaderID;
    }

    public static void UrlRoyal_ItemquantityPDA(String conn, String itemId)
    {
        Url_ItemquantityPDA=ConIpRoyal+"/Itemquantity?conn="+conn+"&itemId="+itemId;
    }

    public static void UrlRoyal_repshelftag(String conn, String Barcode)
    {
        Url_repshelftag=ConIpRoyal+"/repshelftag?conn="+conn+"&Barcode="+Barcode+"&id="+ conRoyal.IDUser+"";
    }

    public static void UrlRoyal_repshelftag2()
 {
  Url_repshelftag2=ConIpRoyal+"/repshelftag2";
 }
    public static void UrlRoyal_UpdateSalesPrice(String conn, String SalesPrice,String MainBarcode)
    {
        Url_UpdateSalesPrice=ConIpRoyal+"/UpdateSalesPrice?conn="+conn+"&SalesPrice="+SalesPrice+"&MainBarcode="+MainBarcode;
    }
    public static void UrlRoyal_ListItemsALL(String conn,int CategoryID)
    {
        Url_SelectListItemsALL=ConIpRoyal+"/ListItemsALL?conn="+conn+"&CategoryID="+CategoryID+"";
    }

    public static void UrlRoyal_GetCustomer(String conn,String SalesManID)
    {
        Url_GetCustomer=ConIpRoyal+"/GetCustomer?conn="+conn+"&SalesManID="+SalesManID+"";
    }
    public static void UrlRoyal_GetVendor(String conn,String CustomerId)
    {
        Url_GetVendor=ConIpRoyal+"/GetVendor?conn="+conn+"&CustomerId="+CustomerId+"";
    }
    public static void UrlRoyal_SaveVendorLogin(String IDLogin,String SalesManID)
    {
        Url_SaveVendorLogin=ConIpRoyal+"/SaveVendorLogin?ID="+IDLogin+"&SalesManID="+SalesManID+"";
    }
    public static void UrlRoyal_GetCategory(String conn)
    {
        Url_GetCategory=ConIpRoyal+"/GetCategory?conn="+conn;
    }
    public static void UrlRoyal_GetSubCategory(String conn,int CategoryID)
    {
        Url_GetSubCategory=ConIpRoyal+"/GetSubCategory?conn="+conn+"&CategoryID="+CategoryID;
    }
    public static void UrlRoyal_InsertItems(String conn,String Barcode,String ItemName,int CategoryID,double SalesPrice,int SalesTaxID,int VendorID)
    {
        Url_InsertItems=ConIpRoyal+"/InsertItems?conn="+conn+"&Barcode="+Barcode+"&ItemName="+ItemName+"&CategoryID="+CategoryID+"&SalesPrice="+SalesPrice+"&SalesTaxID="+SalesTaxID+"&vendorid="+VendorID;
    }

    public static void UrlRoyal_SelectSections(String conn)
    {
        Url_SelectSections=ConIpRoyal+"/SelectSections?conn="+conn;
    }

    public static void UrlRoyal_SelectTables(String conn,int SectionID)
    {
        Url_SelectTables=ConIpRoyal+"/SelectTablesBySectionID?conn="+conn+"&SectionID="+SectionID;
    }

    public static void UrlRoyal_SelectItemsByCategoryID(String conn,int CategoryID)
    {
        Url_SelectItemsByCategoryID=ConIpRoyal+"/SelectItemsByCategoryID?conn="+conn+"&CategoryID="+CategoryID;
    }

    public static void UrlRoyal_SelectItemAdditionsByItemID(String conn,int ItemID)
    {
        Url_SelectItemAdditionsByItemID=ConIpRoyal+"/SelectItemAdditionsByItemID?conn="+conn+"&ItemID="+ItemID;
    }

    public static void UrlRoyal_UpdateStatusTableByTableID(String conn,int TableID,int TableStatusID)
    {
        Url_UpdateStatusTableByTableID=ConIpRoyal+"/UpdateStatusTableByTableID?conn="+conn+"&TableID="+TableID+"&TableStatusID="+TableStatusID+"";
    }
    public static void UrlRoyal_LoginIsHoldResturent(String User,String Pass)
    {
        Url_LoginIsHoldResturent=ConIpRoyal+"/LoginIsHoldResturent?User="+User+"&Pass="+Pass;
    }
    public static void UrlRoyal_LoginIsCashVan(String User,String Pass,String DataSourceName)
    {
        Url_LoginIsCashVan=ConIpRoyal+"/LoginIsCashVan?User="+User+"&Pass="+Pass+"&DataSourceName="+DataSourceName+"";
    }
    public static void UrlRoyal_SelectOrderHeaderByTableID(String conn,int TableID,String WorkDayDate)
    {
        Url_SelectOrderHeaderByTableID=ConIpRoyal+"/SelectOrderHeaderByTableID?conn="+conn+"&TableID="+TableID+"&WorkDayDate="+WorkDayDate;
    }
    public static void UrlRoyal_SelectOrderDetailsByTableID(String conn,int TableID)
    {
        Url_SelectOrderDetailsByTableID=ConIpRoyal+"/SelectOrderDetailsByTableID?conn="+conn+"&TableID="+TableID;
    }

    public static void UrlRoyal_Query(String conn,String CommandText)
    {
        Url_Query=ConIpRoyal+"/Query?conn="+conn+"&CommandText="+CommandText;
    }
    public static void UrlRoyal_GetItemsCashVanPOSByCategoryID(String conn, int CategoryID , int BranchID, int StoreID)
    {
        Url_GetItemsCashVanPOSByCategoryID=ConIpRoyal+"/GetItemsCashVanPOSByCategoryID?conn="+conn+"&CategoryID="+CategoryID+"&Status=true"+"&BranchID="+BranchID+"&StoreID="+StoreID+"";
    }
    public static void UrlRoyal_GetItemsByCategoryIDForCashVanForCustomerID(String conn, int CategoryID , int BranchID, int StoreID,int CustomerID)
    {
        Url_GetSelectItemsByCategoryIDForCashVanForCustomerID=ConIpRoyal+"/SelectItemsByCategoryIDForCashVanForCustomerID?conn="+conn+"&CategoryID="+CategoryID+"&Status=true"+"&BranchID="+BranchID+"&StoreID="+StoreID+"&CustomerID="+CustomerID;
    }
    public static void UrlRoyal_GetItemsByCategoryIDForCashVanForWholeSalePrice(String conn, int CategoryID , int BranchID, int StoreID,boolean IsBalance)
    {
        Url_GetSelectItemsByCategoryIDForCashVanForCustomerID=ConIpRoyal+"/SelectItemsByCategoryIDForCashVanForWholeSalePrice?conn="+conn+"&CategoryID="+CategoryID+"&Status=true"+"&BranchID="+BranchID+"&StoreID="+StoreID+"&IsBalance="+IsBalance;
    }
    public static void UrlRoyal_SelectMaxVoucherHeaderId(String conn)
    {
        Url_GetSelectMaxVoucherHeaderId=ConIpRoyal+"/SelectMaxVoucherHeaderId?conn="+conn+"&cashVoucherType="+26+"";
    }
    public static void UrlRoyal_PrintSaveOrderCashVanPOSBefore(String conn,int StoreID,int LoginUserId,String LoginUserName,String LoginStoreName)
    {
        Url_GetPrintSaveOrderCashVanPOSBefore=ConIpRoyal+"/PrintSaveOrderCashVanPOSBefore?conn="+conn+"&StoreID="+StoreID+"&LoginUserId="+LoginUserId+"&LoginUserName="+LoginUserName+"&LoginStoreName="+LoginStoreName+"&DataSourceName="+Global.DataSourceName+"";
    }
    public static void UrlRoyal_PrintCashVoucherCashVanPOS(String conn,int CashID,String CashName)
    {
        Url_GetPrintCashVoucherCashVanPOS=ConIpRoyal+"/PrintCashVoucherCashVanPOS?conn="+conn+"&LoginUserID="+conRoyal.IDUser+"&LoginUserName="+conRoyal.UserName+"&LoginBranchName="+Global.LoginBranchName+"&LoginStoreName="+Global.LoginStoreName+"&CashID="+CashID+"&CashName="+CashName+"&DataSourceName="+Global.DataSourceName+"";
    }
    public static void UrlRoyal_SelectCashVanPosDashBoard(String conn)
    {
        Url_GetSelectCashVanPosDashBoard=ConIpRoyal+"/SelectCashVanPosDashBoard?conn="+conn+"&EmpID="+conRoyal.IDUser+"";
    }
    public static void UrlRoyal_SelectItemopsByItemID(String conn,int ItemID)
    {
        Url_GetSelectItemopsByItemID=ConIpRoyal+"/SelectItemopsByItemID?conn="+conn+"&ItemID="+ItemID+"";
    }
    public static void UrlRoyal_LoginIsFeedBack(String DataSourceName)
    {
        Url_GetLoginIsFeedBack=ConIpRoyal+"/LoginIsFeedBack?DataSourceName="+DataSourceName;
    }
    public static void UrlRoyal_GetItemsCashVanPOSByCategoryIDIsProdaction(String conn, int CategoryID )
    {
        Url_GetItemsCashVanPOSByCategoryID=ConIpRoyal+"/GetItemsCashVanPOSByCategoryIDIsProdaction?conn="+conn+"&CategoryID="+CategoryID;
    }
    public static void UrlRoyal_GetSaveCashVanRoutesAuto(String conn, int EmpID,int BranchId ,int StoreID,int CustomerID)
    {
        Url_GetSaveCashVanRoutesAuto=ConIpRoyal+"/SaveCashVanRoutesAuto?EmpID="+EmpID+"&BranchId="+BranchId+"&StoreID="+StoreID+"&CustomerID="+CustomerID+"&conn="+conn;
    }
    public static void UrlRoyal_InsertCustomerERP(String conn, String AName,String Telephone1 ,int CreationUserID)
    {
        Url_InsertCustomerERP=ConIpRoyal+"/InsertCustomerERP?AName="+AName+"&Telephone1="+Telephone1+"&CreationUserID="+CreationUserID+"&conn="+conn;
    }

}
