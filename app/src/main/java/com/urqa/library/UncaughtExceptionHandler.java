package com.urqa.library;

import com.urqa.Collector.ErrorReport;
import com.urqa.Collector.ErrorReportFactory;
import com.urqa.common.SendErrorProcess;
import com.urqa.common.StateData;
import com.urqa.rank.ErrorRank;

public class UncaughtExceptionHandler implements
		Thread.UncaughtExceptionHandler {

	private Thread.UncaughtExceptionHandler mDefaultExceptionHandler = null;
	private Thread.UncaughtExceptionHandler mUncaughtExceptionHandler = null;

	public UncaughtExceptionHandler() {
		mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public void setUncaughtExceptionHandler(
			Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
		mUncaughtExceptionHandler = uncaughtExceptionHandler;

	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		try {
			ErrorReport report = ErrorReportFactory.CreateErrorReport(ex, "",
					ErrorRank.Unhandle, StateData.AppContext);

			SendErrorProcess process = new SendErrorProcess(report);
			process.start();

			if (mUncaughtExceptionHandler != null)
				mUncaughtExceptionHandler.uncaughtException(thread, ex);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			mDefaultExceptionHandler.uncaughtException(thread, ex);

		}
	}
}
