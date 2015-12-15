package com.urqa.Collector;

import android.content.Context;

import com.urqa.common.CallStackData;
import com.urqa.common.JsonObj.ErrorSendData;
import com.urqa.common.StateData;
import com.urqa.eventpath.EventPathManager;
import com.urqa.rank.ErrorRank;

import java.util.Calendar;
import java.util.TimeZone;

public class ErrorReportFactory {

    public static ErrorReport CreateErrorReport(Throwable e, String tag, ErrorRank rank, Context context) {
        ErrorReport report = new ErrorReport();
        report.ErrorData = CreateSendData(e, tag, rank, context);
        report.LogData = LogCollector.getLog(context);
        report.mId = getId();
        report.mUrQAVersion = getUrQAVersion();
        return report;
    }


    public static ErrorReport CreateNativeErrorReport(Context context) {
        ErrorReport report = new ErrorReport();
        report.ErrorData = CreateNativeSendData(context);
        report.LogData = LogCollector.getLog(context);
        report.mId = getId();
        report.mUrQAVersion = StateData.SDKVersion;
        return report;
    }

    private static long getId() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        return calendar.getTimeInMillis();
    }

    private static String getUrQAVersion() {
        return StateData.SDKVersion;
    }

    private static ErrorSendData CreateNativeSendData(Context context) {
        ErrorSendData senddata = new ErrorSendData();

        senddata.apikey = StateData.APIKEY;
        senddata.datetime = DateCollector.GetDateYYMMDDHHMMSS(context);
        senddata.device = DeviceCollector.getDeviceModel();
        senddata.country = DeviceCollector.getCountry(context);
        senddata.appversion = DeviceCollector.GetAppVersion(context);
        senddata.osversion = DeviceCollector.getVersionRelease();
        senddata.gpson = (DeviceCollector.GetGps(context)) ? 1 : 0;
        senddata.wifion = (DeviceCollector.GetWiFiNetwork(context)) ? 1 : 0;
        senddata.mobileon = (DeviceCollector.GetMobileNetwork(context)) ? 1 : 0;
        senddata.scrwidth = DeviceCollector.GetWidthScreenSize(context);
        senddata.scrheight = DeviceCollector.GetHeightScreenSize(context);
        senddata.batterylevel = DeviceCollector.GetBatteryLevel(context);
        senddata.availsdcard = DeviceCollector.BytetoMegaByte(DeviceCollector.getAvailableExternalMemorySize());
        senddata.rooted = (DeviceCollector.CheckRoot()) ? 1 : 0;
        senddata.appmemtotal = DeviceCollector.BytetoMegaByte(DeviceCollector.GetTotalMemory());
        senddata.appmemfree = DeviceCollector.BytetoMegaByte(DeviceCollector.GetFreeMemory());
        senddata.appmemmax = DeviceCollector.BytetoMegaByte(DeviceCollector.GetMaxMemory());
        senddata.kernelversion = DeviceCollector.GetLinuxKernelVersion();
        senddata.xdpi = DeviceCollector.GetXDPI(context);
        senddata.ydpi = DeviceCollector.GetYDPI(context);
        senddata.scrorientation = DeviceCollector.GetOrientation(context);
        senddata.sysmemlow = (DeviceCollector.GetSystemLowMemory()) ? 1 : 0;
        senddata.eventpaths = EventPathManager.GetErrorEventPath();
        senddata.locale = DeviceCollector.GetLocale(context);
        senddata.mCarrierName = DeviceCollector.getCarrierName(context);
        senddata.mDeviceId = DeviceCollector.getDeviceId(context, StateData.APIKEY);
        senddata.rank = ErrorRank.Native.value();

        return senddata;
    }

    private static ErrorSendData CreateSendData(Throwable e, String tag, ErrorRank rank, Context context) {
        ErrorSendData senddata = new ErrorSendData();

        String CallStack = CallStackCollector.GetCallStack(e);
        CallStackData data = CallStackCollector.ParseStackTrace(e, CallStack);

        senddata.apikey = StateData.APIKEY;
        senddata.datetime = DateCollector.GetDateYYMMDDHHMMSS(context);
        senddata.device = DeviceCollector.getDeviceModel();
        senddata.country = DeviceCollector.getCountry(context);
        senddata.errorname = data.ErrorName;
        senddata.errorclassname = data.ClassName;
        senddata.linenum = data.Line;
        senddata.lastactivity = data.ActivityName;
        senddata.callstack = CallStack;
        senddata.appversion = DeviceCollector.GetAppVersion(context);
        senddata.osversion = DeviceCollector.getVersionRelease();
        senddata.gpson = (DeviceCollector.GetGps(context)) ? 1 : 0;
        senddata.wifion = (DeviceCollector.GetWiFiNetwork(context)) ? 1 : 0;
        senddata.mobileon = (DeviceCollector.GetMobileNetwork(context)) ? 1 : 0;
        senddata.scrwidth = DeviceCollector.GetWidthScreenSize(context);
        senddata.scrheight = DeviceCollector.GetHeightScreenSize(context);
        senddata.batterylevel = DeviceCollector.GetBatteryLevel(context);
        senddata.availsdcard = DeviceCollector.BytetoMegaByte(DeviceCollector.getAvailableExternalMemorySize());
        senddata.rooted = (DeviceCollector.CheckRoot()) ? 1 : 0;
        senddata.appmemtotal = DeviceCollector.BytetoMegaByte(DeviceCollector.GetTotalMemory());
        senddata.appmemfree = DeviceCollector.BytetoMegaByte(DeviceCollector.GetFreeMemory());
        senddata.appmemmax = DeviceCollector.BytetoMegaByte(DeviceCollector.GetMaxMemory());
        senddata.kernelversion = DeviceCollector.GetLinuxKernelVersion();
        senddata.xdpi = DeviceCollector.GetXDPI(context);
        senddata.ydpi = DeviceCollector.GetYDPI(context);
        senddata.scrorientation = DeviceCollector.GetOrientation(context);
        senddata.sysmemlow = (DeviceCollector.GetSystemLowMemory()) ? 1 : 0;
        senddata.tag = tag;
        senddata.rank = rank.value();
        senddata.eventpaths = EventPathManager.GetErrorEventPath();
        senddata.locale = DeviceCollector.GetLocale(context);
        senddata.mCarrierName = DeviceCollector.getCarrierName(context);
        senddata.mDeviceId = DeviceCollector.getDeviceId(context, StateData.APIKEY);
        return senddata;
    }
}
