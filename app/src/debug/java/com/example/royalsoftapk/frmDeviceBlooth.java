package com.example.royalsoftapk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bxl.config.editor.BXLConfigLoader;
import com.example.tscdll.TSCActivity;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.util.ArrayList;
import java.util.Set;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.config.JposEntry;

import static com.bxl.config.editor.BXLConfigLoader.PRODUCT_NAME_SPP_R200II;
import static com.bxl.config.editor.BXLConfigLoader.PRODUCT_NAME_SPP_R200III;
import static com.bxl.config.editor.BXLConfigLoader.PRODUCT_NAME_SPP_R210;
import static com.bxl.config.editor.BXLConfigLoader.PRODUCT_NAME_SPP_R300;
import static com.bxl.config.editor.BXLConfigLoader.PRODUCT_NAME_SPP_R310;
import static com.bxl.config.editor.BXLConfigLoader.PRODUCT_NAME_SPP_R400;

public class frmDeviceBlooth extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    private final ArrayList<CharSequence> bondedDevices = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;

    DataBaseHelper da=new DataBaseHelper(frmDeviceBlooth.this);
    AdapterBluetoothDevices Adapter;
    ArrayList<ClsBluetoothDevices>ListDeviceAvilable=new ArrayList<ClsBluetoothDevices>();

    int CurrentCount=0;

     ListView listView;

    private static final String TAG = "ListBluooth";
    private static final int REQUEST_CODE_BLUETOOTH = 1;
    private static final int REQUEST_CODE_ACTION_PICK = 2;
    public static String frmName="frmPrintBarcode";
    private static final String DEVICE_ADDRESS_START = " (";
    private static final String DEVICE_ADDRESS_END = ")";
    private BXLConfigLoader bxlConfigLoader;
    Button btnOpenPrinter,btnClosePrinter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_device_blooth);
        listView= findViewById(R.id.listViewPairedDevices);
       frmName= (String) getIntent().getExtras().get("frm");
        Load();
       /* setBondedDevices();
        showPairedDevices();
        loadBXLConfig();*/
        findViewById(R.id.buttonOpenPrinter).setOnClickListener(this);
        findViewById(R.id.buttonClosePrinter).setOnClickListener(this);

        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()) {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent, 1);
        }
    }

    void Load()
    {

        try {

            //--------------- Profile Login ------------------------------------------------//
            int ID,TypeID;
            String AName,Mac,TypeName,CreationDate;
            //--------------- Profile Login ------------------------------------------------//
            SQLiteDatabase db2 = da.getWritableDatabase();
            Cursor res = db2.rawQuery("select * from Tbl_SettingDeviceBltooth",null);
            if(res.getCount()>0) {
                while (res.moveToNext()) {
                    ID = res.getInt(0);
                    AName = res.getString(1);
                    TypeID = res.getInt(2);
                    TypeName = res.getString(3);
                    Mac = res.getString(4);
                    CreationDate = res.getString(5);
                    ListDeviceAvilable.add(new ClsBluetoothDevices(ID, AName, TypeID, TypeName, Mac, CreationDate));
                    bondedDevices.add(AName + DEVICE_ADDRESS_START
                            + Mac + DEVICE_ADDRESS_END);
                }

                /*Adapter = new AdapterBluetoothDevices(frmDeviceBlooth.this, ListDeviceAvilable, frmDeviceBlooth.this);
                listView.setAdapter(Adapter);
                listView.setOnItemClickListener(frmDeviceBlooth.this);*/
                showPairedDevices();
            }
            else
            {
                Global.msgBox(frmDeviceBlooth.this,getString(R.string.txtWrongoperation),getString(R.string.Please_identify), Color.RED,R.drawable.ic_error_outline_black_24dp);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmDeviceBlooth.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }

    public ZebraPrinter connect(String Address) {
        Global.connectionZebra = null;
        Global.connectionZebra = new BluetoothConnection(Address);//00:81:F9:04:CD:3E
            SettingsHelper.saveBluetoothAddress(this, Address);


        try {
            Global.connectionZebra.open();
            Global.StatusBlutooth=true;
        } catch (ConnectionException e) {
            Global.StatusBlutooth=false;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            // disconnect();
        }

        ZebraPrinter printer = null;

        if (Global.connectionZebra.isConnected()) {
            try {

                printer = ZebraPrinterFactory.getInstance(Global.connectionZebra);

                String pl = SGD.GET("device.languages", Global.connectionZebra);

            } catch (ConnectionException e) {
                printer = null;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
               // disconnect();
            } catch (ZebraPrinterLanguageUnknownException e) {
                printer = null;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
               // disconnect();
            }
        }

        return printer;
    }

    private void loadBXLConfig() {
        bxlConfigLoader = new BXLConfigLoader(this);
        try {
            bxlConfigLoader.openFile();
        } catch (Exception e) {
            e.printStackTrace();
            bxlConfigLoader.newFile();
        }
        Global.posPrinter = new POSPrinter(this);
    }
    private void setBondedDevices() {
        Global.logicalName = null;
        bondedDevices.clear();
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            final Set<BluetoothDevice> bondedDeviceSet = bluetoothAdapter.getBondedDevices();
            for (final BluetoothDevice device : bondedDeviceSet) {
                bondedDevices.add(device.getName() + DEVICE_ADDRESS_START
                        + device.getAddress() + DEVICE_ADDRESS_END);
            }
            if (arrayAdapter != null) {
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
    private void showPairedDevices() {
        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, bondedDevices);
        final ListView listView = findViewById(R.id.listViewPairedDevices);
        listView.setAdapter(arrayAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(frmDeviceBlooth.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonOpenPrinter:

                openPrinter();
                break;


            case R.id.buttonClosePrinter:
                closePrinter();
                break;



        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int ID=ListDeviceAvilable.get(position).getID();
        int TypeID=ListDeviceAvilable.get(position).getTypeId();
        String Name=ListDeviceAvilable.get(position).getDevicesName();
        String Mac=ListDeviceAvilable.get(position).getMacAddress();


        Global.TypePrinterConnected=TypeID;
        Global.addressBlutooth=Mac;
        CurrentCount=position;
        switch(TypeID) {
            case 1:

                break;
            case 2:

                try {

                    for (final Object entry : bxlConfigLoader.getEntries()) {
                        final JposEntry jposEntry = (JposEntry) entry;
                        bxlConfigLoader.removeEntry(jposEntry.getLogicalName());
                    }


                } catch (final Exception e) {
                    Log.e(TAG, "Error removing entry.", e);
                }

                try {
                    Global.logicalName = setProductName(Name);
                    Toast.makeText(this, Global.logicalName, Toast.LENGTH_SHORT).show();
                    bxlConfigLoader.addEntry(Global.logicalName,
                            BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER,
                            Global.logicalName,
                            BXLConfigLoader.DEVICE_BUS_BLUETOOTH, Mac);

                    bxlConfigLoader.saveFile();
                } catch (final Exception e) {
                    Log.e(TAG, "Error saving file.", e);
                }
                break;
            case 3:
                if(Global.TscDll==null) {
                    Global.TscDll = new TSCActivity();
                    Global.TscDll.openport(Global.addressBlutooth);
                }
                break;
            case 4:
                Global.addressBlutooth=Mac;
                break;
        }

      /*  final String device = ((TextView) view).getText().toString();
        final String name = device.substring(0, device.indexOf(DEVICE_ADDRESS_START));
        final String address = device.substring(device.indexOf(DEVICE_ADDRESS_START) + DEVICE_ADDRESS_START.length(), device.indexOf(DEVICE_ADDRESS_END));


        if(address.equals("C4:64:E3:11:BC:99"))
        {
            Global.addressBlutooth="C4:64:E3:11:BC:99";
        }
        else  if(address.equals("00:81:F9:04:CD:3E"))
        {
            Global.addressBlutooth="00:81:F9:04:CD:3E";
        }
        else
        {*/
        //}
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void openPrinter() {
        try {
            if(ListDeviceAvilable.size()>0) {

                switch(ListDeviceAvilable.get(CurrentCount).getTypeId()) {
                    case 1:
                        Global.printerZebra = connect(Global.addressBlutooth);
                        if (Global.printerZebra == null) {
                            if (Global.connectionZebra != null) {
                                Global.connectionZebra.close();
                            }
                        }
                        break;
                    case 2:
                        Global.posPrinter.open(Global.logicalName);
                        Global.posPrinter.claim(0);
                        Global.posPrinter.setDeviceEnabled(true);
                        Global.StatusBlutooth = true;
                        break;
                    case 3 :
                        Global.TscDll= new TSCActivity();
                        Global.TscDll.IsConnected=false;
                        if(Global.TscDll.IsConnected==false) {
                            Global.TscDll.openport("00:0C:BF:35:5A:67");
                        }

                        break;
                    case  4 :
                        break;
                }

             /*   if (Global.addressBlutooth.equals("C4:64:E3:11:BC:99") || Global.addressBlutooth.equals("00:81:F9:04:CD:3E") || Global.addressBlutooth.equals("58:93:D8:4D:BC:C0") || Global.addressBlutooth.equals("CC:78:AB:9D:FE:82")) {

                }
                else {

                }*/
                if (frmName.equals("frmCashVanPos")) {
                    finish();
                   // onBackPressed();
                } else {
                    Intent intent = new Intent(this, frmPrintBarcode.class);
                    startActivity(intent);
                }
            }
        } catch (final JposException e) {
            Log.e(TAG, "Error opening printer.", e);
            Global.StatusBlutooth=false;
            Toast.makeText(this, "يرجى محاولة الاتصال مرة اخرى", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
           /* if(!Global.addressBlutooth.equals("C4:64:E3:11:BC:99")||!Global.addressBlutooth.equals("00:81:F9:04:CD:3E"))
            closePrinter();*/
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }
    private void closePrinter() {
        try {
            //Global.posPrinter.close();
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error closing printer.", e);
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String setProductName(final String name) {
        if ((name.contains("SPP-R200II"))) {
            if (name.length() > 10) {
                if (name.substring(10, 11).equals("I")) {
                    return PRODUCT_NAME_SPP_R200III;
                }
            }
        } else if ((name.contains("SPP-R210"))) {
            return PRODUCT_NAME_SPP_R210;
        } else if ((name.contains("SPP-R310"))) {
            return PRODUCT_NAME_SPP_R310;
        } else if ((name.contains("SPP-R300"))) {
            return PRODUCT_NAME_SPP_R300;
        } else if ((name.contains("SPP-R400"))) {
            return PRODUCT_NAME_SPP_R400;
        }else {
            return name;
        }

        return PRODUCT_NAME_SPP_R200II;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==-1)
        {
            startActivity(getIntent());
        }
        else if(resultCode==0)
        {
            finish();
        }
    }
}
