package com.example.royalsoftapk;

public  class Convert {
    public static int ConvertToint(boolean a)
    {
        if(a)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
    public static boolean ConvertTobool(String a)
    {
        if(a.equals("1"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
