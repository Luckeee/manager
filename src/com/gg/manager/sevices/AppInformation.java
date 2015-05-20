package com.gg.manager.sevices;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;

public class AppInformation {

	private ActivityManager activityManager = null;

	public AppInformation(Context context) {
		this.activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

	}

	public boolean isServiceWork(Context mContext, String serviceName) {
		boolean isWork = false;
		ActivityManager myAM = (ActivityManager) mContext
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> myList = myAM.getRunningServices(40);
		if (myList.size() <= 0) {
			return false;
		}
		for (int i = 0; i < myList.size(); i++) {
			String mName = myList.get(i).service.getClassName().toString();
			if (mName.equals(serviceName)) {
				isWork = true;
				break;
			}
		}
		return isWork;
	}



	@SuppressWarnings("deprecation")
	public String getFirstRunningTaskInfo() {
		List<?> taskList = activityManager.getRunningTasks(3);
		RunningTaskInfo taskInfo = (RunningTaskInfo) taskList.get(0);
		String packageName = taskInfo.baseActivity.getPackageName();
		return packageName;
	}
	
	
	public int killProcessByName(String killProcessName) {
	int killNum = 0;
	List<?> appProcessList = activityManager.getRunningAppProcesses();
	for (int i = 0; i < appProcessList.size(); i++) {
		RunningAppProcessInfo appProcessInfo = (RunningAppProcessInfo) appProcessList
				.get(i);
		int pid = appProcessInfo.pid;
//		int uid = appProcessInfo.uid;
		String processName = appProcessInfo.processName;
//		int[] memPid = new int[] { pid };
//		Debug.MemoryInfo[] memoryInfo = activityManager.getProcessMemoryInfo(memPid);
//		int memSize = memoryInfo[0].dalvikPrivateDirty;
//		String[] packageList = appProcessInfo.pkgList;
//		for (String pkg : packageList) {
//		}
		if (killProcessName.equals(processName)) {
			System.out.println("===============killProcess pid-->" + pid);
			android.os.Process.killProcess(pid);
			killNum++;
		}
	}
	return killNum;
}
	
	
	public String getFirstRunningProgrecssName(int i) {
		List<?> appProcessList = activityManager.getRunningAppProcesses();
		RunningAppProcessInfo appProcessInfo = (RunningAppProcessInfo) appProcessList
				.get(i);
		String processName = appProcessInfo.processName;
		return processName;
	}

//public long getSystemAvaialbeMemorySize() {
//	MemoryInfo memoryInfo = new MemoryInfo();
//	activityManager.getMemoryInfo(memoryInfo);
//	long memSize = memoryInfo.availMem;
//	System.out.println("getSystemAvaialbeMemorySize()...memory size: "
//			+ memSize);
//	return memSize;
////	 ����ϵͳ�������ַ���ת��long -String KB/MB
////	 return Formatter.formatFileSize(context, memSize);
//}

//public List<?> getRunningAppProcessInfo() {
//	System.out.println("getRunningAppProcessInfo()...");
//	List<?> appProcessList = activityManager.getRunningAppProcesses();
//	for (int i = 0; i < appProcessList.size(); i++) {
//		RunningAppProcessInfo appProcessInfo = (RunningAppProcessInfo) appProcessList
//				.get(i);
//		// ����ID
//		int pid = appProcessInfo.pid;
//		// �û�ID��������Linux��Ȩ�޲�ͬ��IDҲ�Ͳ�ͬ������root
//		int uid = appProcessInfo.uid;
//		// ��������Ĭ���ǰ�������������android:process=""ָ��
//		String processName = appProcessInfo.processName;
//		// ��øý���ռ�õ��ڴ�
//		int[] memPid = new int[] { pid };
//		// ��MemoryInfoλ��android.os.Debug.MemoryInfo���У�����ͳ�ƽ��̵��ڴ���Ϣ
//		Debug.MemoryInfo[] memoryInfo = activityManager
//				.getProcessMemoryInfo(memPid);
//		// ��ȡ����ռ�ڴ�����Ϣkb��λ
//		int memSize = memoryInfo[0].dalvikPrivateDirty;
//		System.out.println("process name: " + processName + " pid: " + pid
//				+ " uid: " + uid + " memory size is -->" + memSize + "kb");
//		// ���ÿ�����������е�Ӧ�ó���(��)����ÿ��Ӧ�ó���İ���
//		String[] packageList = appProcessInfo.pkgList;
//		for (String pkg : packageList) {
//			System.out.println("package name " + pkg
//					+ " in process id is -->" + pid);
//		}
//	}
//	return appProcessList;
//}

//public List<?> getRunningServiceInfo() {
//	System.out.println("getRunningServiceInfo()...");
//	List<?> serviceList = activityManager.getRunningServices(30);
//	for (int i = 0; i < serviceList.size(); i++) {
//		RunningServiceInfo serviceInfo = (RunningServiceInfo) serviceList
//				.get(i);
//		// ����ID
//		int pid = serviceInfo.pid;
//		// �û�ID��������Linux��Ȩ�޲�ͬ��IDҲ�Ͳ�ͬ������root
//		int uid = serviceInfo.uid;
//		// ��������Ĭ���ǰ�������������android:process=""ָ��
//		String processName = serviceInfo.process;
//		String serviceStr = serviceInfo.toString();
//		System.out.println("serviceStr: " + serviceStr);
//		// ��øý���ռ�õ��ڴ�
//		int[] memPid = new int[] { pid };
//		// ��MemoryInfoλ��android.os.Debug.MemoryInfo���У�����ͳ�ƽ��̵��ڴ���Ϣ
//		Debug.MemoryInfo[] memoryInfo = activityManager
//				.getProcessMemoryInfo(memPid);
//		// ��ȡ����ռ�ڴ�����Ϣkb��λ
//		int memSize = memoryInfo[0].dalvikPrivateDirty;
//		System.out.println("The name of the process this service runs in: "
//				+ processName + " pid: " + pid + " uid: " + uid
//				+ " memory size is -->" + memSize + "kb");
//	}
//	return serviceList;
//}
//
//@SuppressWarnings("deprecation")
//public List<?> getRunningTaskInfo() {
//	System.out.println("getRunningServiceInfo()...");
//	List<?> taskList = activityManager.getRunningTasks(30);
//	for (int i = 0; i < taskList.size(); i++) {
//		RunningTaskInfo taskInfo = (RunningTaskInfo) taskList.get(i);
//		String packageName = taskInfo.baseActivity.getPackageName();
//		System.out.println("package name: " + packageName);
//	}
//	return taskList;
//}
}
