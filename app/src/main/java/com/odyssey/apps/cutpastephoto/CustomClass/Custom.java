package com.odyssey.apps.cutpastephoto.CustomClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by admin on 2018-03-07.
 */

public class Custom {

    public static  String makeFileNameFrom(String format) {
        String dateString = new SimpleDateFormat("yyyyMMddhhssSS", Locale.getDefault()).format(new Date());
        String filename = "CutPastePhoto"  + dateString + format ;
        return  filename ;
    }

}
