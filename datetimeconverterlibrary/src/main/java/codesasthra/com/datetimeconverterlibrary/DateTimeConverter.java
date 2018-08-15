package codesasthra.com.datetimeconverterlibrary;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Avadhesh Pandey.
 * DateTime converter converts date time to specified format Using SimpleDateFormat
 * It returns current date if no date is provided.
 * Default output date format is "dd MMM yyyy"
 * Default Locale is ENG-us
 * Usage example
 * String dtc=new DateTimeConverter.Builder()
 .setDateString("2017-10-20")
 .setOutputFormat("dd MM YY")
 .create();
 */

public class DateTimeConverter {


    public static class Builder {

        private Context ctx;
        private String inputFormat;
        private String outputFormat;
        private String mDate;
        private Date date;
        private boolean returnDateObject;

        public Builder setReturnDateObject(boolean returnDateObject) {
            this.returnDateObject = returnDateObject;
            return this;
        }

        public Builder setLocale(Context ctx) {
            this.ctx = ctx;
            return this;
        }

        public Builder setInputFormat(String inputFormat) {
            this.inputFormat = inputFormat;
            return this;
        }

        public Builder setOutputFormat(String outputFormat) {
            this.outputFormat = outputFormat;
            return this;
        }

        public Builder setDateString(String mDate) {
            this.mDate = mDate;
            return this;
        }

//        public Builder setDateObject(Date date) {
//            this.date = date;
//            return this;
//        }

        public String create() {
            DateTimeConverter dtc=new DateTimeConverter(this);
            return dtc.convert();
        }



    }

    /**
     * This function will return current device time if no time or input format is provided
     * @return returns date in string format as specified.
     */
    public String convert()
    {
        String formattedDate="N/A";
        Locale locale;
        try {
            if(ctx!=null)
            {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    locale=ctx.getResources().getConfiguration().getLocales().get(0);
                } else
                {
                    locale=ctx.getResources().getConfiguration().locale;
                }
            }
            else
            {
                locale= new Locale("en", "US");
            }

            if(!is_valid(outputFormat))
            {
                outputFormat="dd MMM yyyy";
            }
            SimpleDateFormat mOutputDateFormat = new SimpleDateFormat(outputFormat,locale);

            if(date==null)
            {
                if(!is_valid(inputFormat))
                {
                    inputFormat=getInputDateTimeFormat(mDate);
                }

                if(is_valid(inputFormat) && is_valid(mDate))
                {
                    SimpleDateFormat mInputDateFormat = new SimpleDateFormat(inputFormat,locale);
                    Date inputDate=mInputDateFormat.parse(mDate);
                    formattedDate=mOutputDateFormat.format(inputDate);
                }
                else
                {
                    Date inputDate= Calendar.getInstance().getTime();
                    formattedDate=mOutputDateFormat.format(inputDate);
                }
            }
            else
            {
                formattedDate=mOutputDateFormat.format(date);
            }

        }catch (Exception e)
        {
            Log.e("ERROR","Specify the correct input/Output format for " +
                    "Ex : new DateTimeConverter.Builder()\n" +
                    "                .setDateString(\"23-07-2017\")\n" +
                    "                .setInputFormat(\"dd-MM-yyyy\")\n" +
                    "                .create();");
            e.printStackTrace();}

        return formattedDate;
    }

    public Context getCtx() {
        return ctx;
    }

    public String getInputFormat() {
        return inputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public String getmDate() {
        return mDate;
    }

    public Date getDate() {
        return date;
    }

    public boolean isReturnDateObject() {
        return returnDateObject;
    }

    private Context ctx;
    private String inputFormat;
    private String outputFormat;
    private String mDate;
    private Date date;
    private boolean returnDateObject;

    private DateTimeConverter(final Builder builder) {
        ctx = builder.ctx;
        inputFormat = builder.inputFormat;
        outputFormat = builder.outputFormat;
        mDate = builder.mDate;
        date = builder.date;
        returnDateObject = builder.returnDateObject;
    }

    private static boolean is_valid(String str)
    {return !(str == null || str.equalsIgnoreCase("null") || str.trim().length() == 0);}

    private String getInputDateTimeFormat(String mDate)
    {
        if(!is_valid(mDate))
        {
            return "";
        }
        if(Pattern.matches("....-..-..", mDate))
        {
            return "yyyy-MM-dd";
        }
        else if(Pattern.matches("..-..-....", mDate))
        {
            return "dd-MM-yyyy";
        }
        else if(Pattern.matches("..../../..", mDate))
        {
            return "yyyy/mm/dd";
        }else if(Pattern.matches("../../....", mDate))
        {
            return "dd/MM/yyyy";
        }else if(Pattern.matches("....-..-.. ..:..:..", mDate))
        {
            return "yyyy-MM-dd kk:mm:ss";
        }else if(Pattern.matches("../../.... ..:..:..", mDate))
        {
            return "MM/dd/yyyy HH:mm:ss";
        }else
        {
            return "";
        }

    }


/*      Some predefined formats are mentioned here-under
        h:mm a                        //12:08 PM
        yyyy-MM-dd
        dd-MM-yyyy
        yyyy-MM-dd kk:mm:ss
        yyyy-MM-dd HH:mm:ss
        MM/dd/yyyy HH:mm:ss
        dd MMM yyyy
        MMM dd, yyyy hh:mm:ss aaa//   Mar 10, 2016 6:30:00 PM
        E, MMM dd yyyy                Fri, June 7 2013
        EEEE, MMM dd, yyyy HH:mm:ss a   //Friday, Jun 7, 2013 12:10:56 PM

        No.	Format	Example
        1	dd/mm/yy	03/08/06
        2	dd/mm/yyyy	03/08/2006
        3	d/m/yy	3/8/06
        4	d/m/yyyy	3/8/2006
        5	ddmmyy	030806
        6	ddmmyyyy	03082006
        7	ddmmmyy	03Aug06
        8	ddmmmyyyy	03Aug2006
        9	dd-mmm-yy	03-Aug-06
        10	dd-mmm-yyyy	03-Aug-2006
        11	dmmmyy	3Aug06
        12	dmmmyyyy	3Aug2006
        13	d-mmm-yy	3-Aug-06
        14	d-mmm-yyyy	3-Aug-2006
        15	d-mmmm-yy	3-August-06
        16	d-mmmm-yyyy	3-August-2006
        17	yymmdd	060803
        18	yyyymmdd	20060803
        19	yy/mm/dd	06/08/03
        20	yyyy/mm/dd	2006/08/03
        21	mmddyy	080306
        22	mmddyyyy	08032006
        23	mm/dd/yy	08/03/06
        24	mm/dd/yyyy	08/03/2006
        25	mmm-dd-yy	Aug-03-06
        26	mmm-dd-yyyy	Aug-03-2006
        27	yyyy-mm-dd	2006-08-03
        28	weekday, dth mmmm yyyy	Monday, 3 of August 2006
        29	weekday	Monday
        30	mmm-yy	Aug-06
        31	yy	06
        32	yyyy	2006
        33	dd-mmm-yyyy time	03-Aug-2006 18:55:30.35
        34	yyyy-mm-dd time24 (ODBC Std)	2006-08-03 18:55:30
        35	dd-mmm-yyyy time12	03-Aug-2006 6:55:30 pm
        36	time24	18:55:30
        37	time12	6:55:30 pm
        38	hours	48:55:30
        39	seconds	68538.350*/
}
