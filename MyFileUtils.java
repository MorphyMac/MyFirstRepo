package com.example.f1_provider;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.webkit.MimeTypeMap;


public class MyFileUtils {


    public static String APP_CUSTOM_DIR_NAME = "MyApp";
    private static boolean hasAllotedAppCustomFolderName = false;
    public static boolean LOG_ENABLE = true;
    
	public static final int FREE_MEMORY=0;
	public static final int USED_MEMORY=1;
	public static final int TOTAL_MEMORY=2;
	
	public static final long ONE_KB = 1024;
	public static final long ONE_MB = ONE_KB * ONE_KB;
	public static final long ONE_GB = ONE_MB * ONE_KB;
	
	public static final	BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);
	public static final	BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);
	public static final	BigInteger ONE_GB_BI = ONE_MB_BI.multiply(ONE_KB_BI);
	
	
	public static final	BigInteger THREE_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(3));
	public static final	BigInteger FIVE_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(5));
	public static final	BigInteger TEN_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(10));
	public static final	BigInteger TWENTY_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(20));
	public static final	BigInteger FORTY_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(40));
	public static final	BigInteger FIFTY_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(50));
	public static final	BigInteger SIXTY_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(60));
	public static final	BigInteger EIGHTY_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(80));
	public static final	BigInteger CENT_MB_BI = ONE_MB_BI.multiply(BigInteger.valueOf(100));
	
    public String[] fileType_images = {"bmp","cmx","cod","gif","ico","ief","jpe","jpeg","jpg","jfif","pbm","pgm","png","pnm","ppm","ras","rgb","svg","tif","tiff","xbm","xpm","xwd"};
   
    public String[] fileType_text = {"doc","docx","log","msg","odt","pages","rtf","tex","txt","wpd","wps"};
    public String[] fileType_data = {"csv","dat","gbr","ged","key","keychain","pps","ppt","pptx","sdf","tar","tax2012","tax2014","vcf","xml"};
    public String[] fileType_audio = {"aif","iff","m3u","m4a","mid","mp3","mpa","ra","wav","wma"};
    public String[] fileType_video = {"3g2","3gp","asf","asx","avi","flv","m4v","mov","mp4","mpg","rm","srt","swf","vob","wmv"};
    public String[] fileType_image3D = {"3dm","3ds","max","obj"};
    public String[] fileType_imageRaster = {"bmp","dds","gif","jpg","png","psd","pspimage","tga","thm","tif","tiff","yuv"};
    public String[] fileType_imageVector = {"ai","eps","ps","svg"};
    public String[] fileType_pageLayout = {"indd","pct","pdf"};
    public String[] fileType_spreadsheet = {"xlr","xls","xlsx"};
    public String[] fileType_database = {"accdb","db","dbf","mdb","pdb","sql"};
    public String[] fileType_executable = {"apk","app","bat","cgi","com","exe","gadget","jar","pif","vb","wsf"};
    public String[] fileType_CAD = {"dwg","dxf"};
    public String[] fileType_GIS = {"gpx","kml","kmz"};
    public String[] fileType_web = {"asp","aspx","cer","cfm","csr","css","htm","html","js","jsp","php","rss","xhtml"};
    public String[] fileType_plugin = {"crx","plugin"};
    public String[] fileType_font = {"fnt","fon","otf","ttf"};
    public String[] fileType_systemFiles = {"cab","cpl","cur","deskthemepack","dll","dmp","drv","icns","ico","lnk","sys"};
    public String[] fileType_settings = {"cfg","ini","prf"};
    public String[] fileType_encoded = {"hqx","mim","uue"};
    public String[] fileType_compressed = {"7z","cbr","deb","gz","pkg","rar","rpm","sitx","gz","tar.gz","zip","zipx"};
    public String[] fileType_diskImage = {"bin","cue","dmg","iso","mdf","toast","vcd"};
    public String[] fileType_developer = {"c","class","cpp","cs","dtd","fla","h","java","lua","m","pl","py","sh","sln","swift","vcxproj","xcodeproj"};
    public String[] fileType_backup = {"bak","tmp"};
    public String[] fileType_misc = {"crdownload","ics","msi","part","torrent"};
    
    
        
    
    private static  Context context;
	public Crypto crypto;
    public MyFileUtils(Context context) {
    	MyFileUtils.context = context;
    	this.crypto = new Crypto(context);
    	setDefaultCustomFolderName();
	}
    
   

	/* (1:CACHE) 	cache folders getters	*/
    public File getFolder_internalCache() {return context.getCacheDir();}
    public File getFolder_externalCache() {return context.getExternalCacheDir();}
    
    /* (2:INTERNAL)	apps internal Private folder	**/
    public File getFolder_privateInternal_Sandbox() {return context.getFilesDir();}

    
    /* (3:EXTERNAL_PRIVATE)	******** 	private external_folders (e.g. sdCard) getters	***********/
    public File getFolder_privateExternalFolder(String folderName) {return context.getExternalFilesDir(folderName);}
	
  /*  private File getFolder_privateExternal_picture() {return getFolder_privateExternalFolder(Environment.DIRECTORY_PICTURES);}
    private File getFolder_privateExternal_alarm() {return getFolder_privateExternalFolder(Environment.DIRECTORY_ALARMS);}
	private File getFolder_privateExternal_downloads() {return getFolder_privateExternalFolder(Environment.DIRECTORY_DOWNLOADS);}
	private File getFolder_privateExternal_movies() {return getFolder_privateExternalFolder(Environment.DIRECTORY_MOVIES);}
	private File getFolder_privateExternal_DCIM() {return getFolder_privateExternalFolder(Environment.DIRECTORY_DCIM);}
	private File getFolder_privateExternal_music() {return getFolder_privateExternalFolder(Environment.DIRECTORY_MUSIC);}
	private File getFolder_privateExternal_podcasts() {return getFolder_privateExternalFolder(Environment.DIRECTORY_PODCASTS);}
	private File getFolder_privateExternal_ringtones() {return getFolder_privateExternalFolder(Environment.DIRECTORY_RINGTONES);}
	private File getFolder_privateExternal_notifications() {return getFolder_privateExternalFolder(Environment.DIRECTORY_NOTIFICATIONS);}
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private File getFolder_privateExternal_documents() {if(Build.VERSION.SDK_INT >=19){return getFolder_privateExternalFolder(Environment.DIRECTORY_DOCUMENTS);}else{return null;}}
	*/
	/* (4:EXTERNAL_PUBLIC)	******** 	public external_folders (e.g. sdCard) getters	***********/
	public File getFolder_publicExternalFolder(String folderName) {return Environment.getExternalStoragePublicDirectory(folderName);}
	public File getFolder_publicExternalRootFolder() {return Environment.getExternalStorageDirectory();}
	
	/*private File getFolder_publicExternal_picture() {return getFolder_publicExternalFolder(Environment.DIRECTORY_PICTURES);}
	private File getFolder_publicExternal_alarm() {return getFolder_publicExternalFolder(Environment.DIRECTORY_ALARMS);}
	private File getFolder_publicExternal_downloads() {return getFolder_publicExternalFolder(Environment.DIRECTORY_DOWNLOADS);}
	private File getFolder_publicExternal_movies() {return getFolder_publicExternalFolder(Environment.DIRECTORY_MOVIES);}
	private File getFolder_publicExternal_DCIM() {return getFolder_publicExternalFolder(Environment.DIRECTORY_DCIM);}
	private File getFolder_publicExternal_music() {return getFolder_publicExternalFolder(Environment.DIRECTORY_MUSIC);}
	private File getFolder_publicExternal_podcasts() {return getFolder_publicExternalFolder(Environment.DIRECTORY_PODCASTS);}
	private File getFolder_publicExternal_ringtones() {return getFolder_publicExternalFolder(Environment.DIRECTORY_RINGTONES);}
	private File getFolder_publicExternal_notifications() {return getFolder_publicExternalFolder(Environment.DIRECTORY_NOTIFICATIONS);}
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private File getFolder_publicExternal_documents() {if(Build.VERSION.SDK_INT >=19){return getFolder_publicExternalFolder(Environment.DIRECTORY_DOCUMENTS);}else{return null;}}
	*/
   
	
	
	
	
	
    
    
    
    
    /* 
     * 
     * 
     * 
     * 																**************  Saving files	*************************
     * 
     * 
     * 
     * 
     * *********************/
    public boolean saveTo_privateInternal_SandBox(Context context,String fileName,String data) {
        FileOutputStream fos = null;
        boolean status = false;
        try {
            //String fileSavingLocation = context.getFilesDir().getAbsolutePath();
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());

            System.out.println("File "+fileName+" saved internally success !!");
            status = true;
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) System.err.println("Exception : File "+fileName+" not found.");
            else if(e instanceof IOException) System.err.println("Exception : IO exception accurs when writing file.");
        }finally { try {if(fos!=null) {fos.close();}} catch (Exception e2) {System.err.println("Exception closing fileOutputStream.");}}
        return status;
    }
    public boolean saveTo_privateInternal_SandBox(Context context,String fileName,byte[] data) {
        FileOutputStream fos = null;
        boolean status = false;
        try {
            //String fileSavingLocation = context.getFilesDir().getAbsolutePath();
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data);

            System.out.println("File "+fileName+" saved internally success !!");
            status = true;
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) System.err.println("Exception : File "+fileName+" not found.");
            else if(e instanceof IOException) System.err.println("Exception : IO exception accurs when writing file.");
        }finally { try {if(fos!=null) {fos.close();}} catch (Exception e2) {System.err.println("Exception closing fileOutputStream.");}}
        return status;
    }
    public boolean saveTo_InternalCache(Context context,String fileName,String data) {
        File folder = context.getCacheDir();
        File file = new File(folder, fileName);
        return writeData(file, data);
    }
    public boolean saveTo_ExternalCache(Context context,String fileName,String data) {
        File folder = context.getExternalCacheDir();
        File file = new File(folder, fileName);
        return writeData(file, data);
    }
   
    
    public boolean saveTo_privateExternalSDCard_AppCustomFolder(Context context,String fileName,String data) {
    	if(confirmDirectory(getFolder_publicExternalFolder(APP_CUSTOM_DIR_NAME))){
    		  File folder = context.getExternalFilesDir(APP_CUSTOM_DIR_NAME);
    	        File file = new File(folder, fileName);
    	        return writeData(file, data);
    	}else{
    		System.err.println("App custom Folder not exist.File saving failed !");
    	}
		return false;
    }
    public boolean saveTo_publicExternalSDCard_AppCustomFolder(Context context,String fileName,String data) {
    	if(confirmDirectory(getFolder_publicExternalFolder(APP_CUSTOM_DIR_NAME))){
    		  File folder =	getFolder_publicExternalFolder(APP_CUSTOM_DIR_NAME);
    	        File file = new File(folder, fileName);
    	        return writeData(file, data);
    	}else{
    		System.err.println("App custom Folder not exist.Saving file: "+fileName+" failed !");
    	}
		return false;
    }
    
    public boolean saveTo_privateExternalSDCard(Context context,File folder,String fileName,String data) {
    	if(confirmDirectory(folder)){
    	        File file = new File(folder, fileName);
    	        return writeData(file, data);
    	}else{
    		System.err.println("Folder not exist.File saving failed !");
    	}
		return false;
    }
    public boolean saveTo_publicExternalSDCard(Context context,String folderName,String fileName,String data) {
        File folder = getFolder_publicExternalFolder(folderName);
        if (confirmDirectory(folder)) {
            File file = new File(folder, fileName);
            return writeData(file, data);
        } else {
            System.err.println("Folder "+folderName+" not exists.File saving failed.");
        }
		return false;
    }
    
    
    /* 
     * 
     * 
     * 																	******************	reading Files **************************
     * 
     * ***/
    public String readFrom_privateInternal_SandBox(Context context,String fileName) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(fileName);
            int read=-1;
            StringBuffer buffer = new StringBuffer();
            while ((read = fis.read()) != -1 ) {
                buffer.append((char)read);
            }
            return buffer.toString();

        } catch (Exception e) {
            if(e instanceof FileNotFoundException) System.err.println("Exception : File "+fileName+" not found.");
            else if(e instanceof IOException) System.err.println("Exception : IO exception occurs when reading file "+fileName);
        }finally {try {if(fis!=null) {fis.close();}}catch (Exception e2) {System.err.println("Exception in closing fileInputStream");}}
        return null;
    }

    public String readFrom_InternalCache(Context context,String fileName) {
        File folder =  context.getCacheDir();
        File file = new File(folder,fileName);
        if (new File(fileName).exists()) {
			 String data = readData(file);
			 return data;
		 }else{
			 System.err.println("File "+fileName+" not exist.");
			 return null;
		 }
    }
    public String readFrom_ExternalCache(Context context,String fileName) {
        File folder =  context.getExternalCacheDir();
        File file = new File(folder,fileName);
        if (new File(fileName).exists()) {
			 String data = readData(file);
			 return data;
		 }else{
			 System.err.println("File "+fileName+" not exist.");
			 return null;
		 }
    }

    public String readFrom_privateExternalSDCard_AppCustomFolder(Context context,String fileName) {
			File folder = context.getExternalFilesDir(APP_CUSTOM_DIR_NAME);
			if(folder.exists()){
			File file = new File(folder, fileName);
				 if (new File(fileName).exists()) {
					 String data = readData(file);
					 return data;
				 }else{
					 System.err.println("File "+fileName+" not exist.");
					 return null;
				 }
			}else{
				System.err.println("App custom folder not found.");
				return null;
			}
    }
    public String readFrom_PublicExternalSDCard_AppCustomFolder(Context context,String fileName) {
        File folder = getFolder_publicExternalFolder(APP_CUSTOM_DIR_NAME);
        if (!folder.exists()) {
        	System.err.println("App custom Folder not exist.reading file: "+fileName+" failed !");
            return null;
        }
        File file = new File(folder,fileName);
        if (new File(fileName).exists()) {
			 String data = readData(file);
			 return data;
		 }else{
			 System.err.println("File "+fileName+" not exist in custom folder.");
			 return null;
		 }
    }
   
    public String readFrom_privateExternalSDCard(Context context,File folder,String fileName) {
		if(folder.exists()){
			 File file = new File(folder, fileName);
			 if (new File(fileName).exists()) {
				 String data = readData(file);
				 return data;
			 }else{
				 System.err.println("File "+fileName+" not exist.");
				 return null;
			 }
		}else{
			System.err.println("Folder not found.");
			return null;
		}
}
    public String readFrom_PublicExternalSDCard(Context context,String folderName,String fileName) {
        File folder = getFolder_publicExternalFolder(folderName);
        if (!folder.exists()) {
            System.err.println("Folder "+folderName+" not exist.");
            return null;
        }
        File file = new File(folder,fileName);
        if (new File(fileName).exists()) {
			 String data = readData(file);
			 return data;
		 }else{
			 System.err.println("File "+fileName+" not exist.");
			 return null;
		 }
    }

    
    
    
    
    
    
    
   
    

    /* 
     * 
     * 													************		file size/format/etc utils	***************
     * 
     * */
    @SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public long getAvailableInternalMemorySize() {

        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        if(VERSION.SDK_INT >= 18){
        	long blockSize = stat.getBlockSizeLong();
        	long availableBlocks = stat.getAvailableBlocksLong();
        	return (availableBlocks * blockSize) / 1048576;
        }else{
        	long blockSize = stat.getBlockSize();
        	long availableBlocks = stat.getAvailableBlocks();
        	return (availableBlocks * blockSize) / 1048576;
        }
    }
    @SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        if(VERSION.SDK_INT >= 18){
        	long blockSize = stat.getBlockSizeLong();
        	long availableBlocks = stat.getAvailableBlocksLong();
        	return (availableBlocks * blockSize) / 1048576;
        }else{
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
        	return (availableBlocks * blockSize) / 1048576;
        }
    }
    /**
     * @param memPath : memory path size
     * @param	memPartition : pass these parameter declared static to Util class.<br><b> FREE_MEMORY   </b><b> USED_MEMORY   </b><b> TOTAL_MEMORY </b>
     * */
    public String getMemorySize(String memPath, int memPartition) {
		String memSize = "";
		switch (memPartition) {
		case MyFileUtils.FREE_MEMORY:
			memSize = getFormattedMemSize(FreeMemory(memPath));
			break;
		case MyFileUtils.USED_MEMORY:
			memSize = getFormattedMemSize(UsedMemory(memPath));
			break;
		case MyFileUtils.TOTAL_MEMORY:
			memSize = getFormattedMemSize(TotalMemory(memPath));
			break;
		}
		return memSize;
	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@SuppressWarnings("deprecation")
	private  long TotalMemory(String path) {
		StatFs statFs = new StatFs(path);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			long Total = ((long) statFs.getBlockCountLong() * (long) statFs.getBlockSizeLong());
			return Total;
		} else {
			long Total = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
			return Total;
		}

	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@SuppressWarnings("deprecation")
	private  long FreeMemory(String path) {
		StatFs statFs = new StatFs(path);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			long freeMem = (statFs.getAvailableBlocksLong() * (long) statFs.getBlockSizeLong());
			return freeMem;
		} else {
			long freeMem = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
			return freeMem;
		}

	}
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	@SuppressWarnings("deprecation")
	private  long UsedMemory(String path) {
		StatFs statFs = new StatFs(path);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			long Total = ((long) statFs.getBlockCountLong() * (long) statFs
					.getBlockSizeLong());
			long Free = (statFs.getAvailableBlocksLong() * (long) statFs
					.getBlockSizeLong());
			long Busy = Total - Free;
			return Busy;
		} else {
			long Total = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
			long Free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
			long Busy = Total - Free;
			return Busy;
		}
	}
	public String getfilesTotalSize(ArrayList<File> files) {
		long totalSize=0;
		for (File file : files) {
			totalSize += file.length();
		}
		return getFormattedMemSize(totalSize);
	}
	public long getfilesTotalSizeLong(ArrayList<File> files) {
		long totalSize=0;
		for (File file : files) {
			totalSize += file.length();
		}
		return totalSize;
	}
	public long getFileSize(File file) {
		return file.length();
	}
	public String getFormattedMemSize(long size) {
		long Kb = 1 * 1024;
		long Mb = Kb * 1024;
		long Gb = Mb * 1024;
		long Tb = Gb * 1024;
		long Pb = Tb * 1024;
		long Eb = Pb * 1024;

		if (size < Kb) {
			return floatForm(size) + " bytes";
		}
		if (size >= Kb && size < Mb) {
			return floatForm((double) size / Kb) + " KB";
		}
		if (size >= Mb && size < Gb) {
			return floatForm((double) size / Mb) + " MB";
		}
		if (size >= Gb && size < Tb) {
			return floatForm((double) size / Gb) + " GB";
		}
		if (size >= Tb && size < Pb) {
			return floatForm((double) size / Tb) + " TB";
		}
		if (size >= Pb && size < Eb) {
			return floatForm((double) size / Pb) + " PB";
		}
		if (size >= Eb) {
			return floatForm((double) size / Eb) + " EB";
		}

		return "???";
	}private String floatForm(double d) {return new DecimalFormat("#.##").format(d);}
	
	
	
	
	
	
	
	
	/* 
	 * 
	 * 													********* files and directories  :  		 names and paths	****
	 * 
	 * 
	 * 
	 * */
	/* 																					************	get	Files **************************/
	public ArrayList<String> getFileNamesList(ArrayList<File> files){
	        ArrayList<String> fileNames = new ArrayList<String>();
	         if (files.size() == 0){
	        	 System.out.println("Empty arrayList input : files");
	        	 return null;
	         }else {	
	        	 for (File file : files) {
	        		 fileNames.add(file.getName());
	        	 }
	         }
	        return fileNames;
	}
	public String[] getFileNamesArray(ArrayList<File> files){
		if(!files.isEmpty()) {
			String[] fileArr = new String[files.size()];
			for (int i = 0; i < fileArr.length; i++) {
				fileArr[i] = files.get(i).getName();
			}
			return fileArr;
		}
		return null;
	}
	public ArrayList<String> getFilesAbsPathList(ArrayList<File> files){
        ArrayList<String> filePaths = new ArrayList<String>();
         if (files.size() == 0){
        	 System.out.println("Empty arrayList input : files");
        	 return null;
         }else {	
        	 for (File file : files) {
        		 filePaths.add(file.getAbsolutePath());
        	 }
         }
        return filePaths;
	}
	public String[] getFilesAbsPathArray(ArrayList<File> files){
		if(!files.isEmpty()) {
			String[] fileArr = new String[files.size()];
			for (int i = 0; i < fileArr.length; i++) {
				fileArr[i] = files.get(i).getAbsolutePath();
			}
			return fileArr;
		}
		return null;
	}
	public ArrayList<File> getFilesInDirectory(String directoryPath) {
		ArrayList<File> filesList = new ArrayList<File>();
        File f = new File(directoryPath);
	    if (f.exists()) {
	        File[] files = f.listFiles();   
	        if(files!=null){
	        	for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if(file.isDirectory()){
						filesList.addAll(getFilesInDirectory(file.getAbsolutePath()));
					}else{
							filesList.add(file);
						}
					}
		        }
			}else{
				logSEP("Directory "+directoryPath+" not exists.");
			}
	        return filesList;
	}
	public ArrayList<File> getFilesInDirectory(File directory) {
			ArrayList<File> filesList = new ArrayList<File>();
	        if (directory.exists()) {
		        File[] files = directory.listFiles();   
		        if(files!=null && files.length>0){
		        	for (File file : files) {
							if(file.isDirectory()){
								filesList.addAll(getFilesInDirectory(file));
							}else{
								filesList.add(file);
							}
						}
		        }else{
		        	System.err.println("null return , dir.listFiles()");
		        }
	        }else{
	        	System.err.println("Directory "+directory+" not exists !!");
	        }
	        return filesList;
	}
	public ArrayList<File> getFilesByType(File folder,ArrayList<String> fileExtensions) {
	    	ArrayList<File> localList = new ArrayList<File>();
	    	String tempFileName,tempFileType;
	        File listFile[] = folder.listFiles();
	        if (listFile != null && listFile.length > 0) {
	        	for (File file : listFile) {
					if(file.isDirectory()){
						localList.addAll(getFilesByType(file, fileExtensions));
					}else{
	                    tempFileName = file.getName();
	                    if(tempFileName.contains(".")){
	                    	tempFileType = tempFileName.substring(tempFileName.lastIndexOf(".")+1);
	                    	if(fileExtensions!=null && !fileExtensions.isEmpty()){
	                    		if(fileExtensions.contains(tempFileType.toLowerCase(Locale.US))){
	                    			//if(LOG_ENABLE)System.out.println("file : "+tempFileName);
	                    			localList.add(file);
	                    		}
	                    	}
	                    }
	                }
				}
	        }
	       // logSOP("Folder : "+folder.getAbsolutePath() +"\t\t\tcount: "+localList.size());
	        return localList;
	    }
	public ArrayList<File> getFilesHidden(File folder) {
    	ArrayList<File> localList = new ArrayList<File>();
    	String tempFileName;
        File listFile[] = folder.listFiles();
        if (listFile != null && listFile.length > 0) {
        	for (File file : listFile) {
				if(file.isDirectory()){
					localList.addAll(getFilesHidden(file));
				}else{
                    tempFileName = file.getName();
                    if(tempFileName.startsWith(".")){
                    	//System.out.println("Hiddenfile : "+tempFileName);
                   		localList.add(file);
                    }
                }
			}
        }
        logSOP("Folder : "+folder.getAbsolutePath() +"\t\t\thidddenFileCount: "+localList.size());
        return localList;
    }
	public ArrayList<File> getFilesHiddenByType(File folder,ArrayList<String> fileExtensions) {
    	ArrayList<File> localList = new ArrayList<File>();
    	String tempFileType;
        File listFile[] = folder.listFiles();
        if (listFile != null && listFile.length > 0) {
        	for (File file : listFile) {
				if(file.isDirectory()){
					System.out.println("dir : "+file.getName());
					localList.addAll(getFilesHiddenByType(file, fileExtensions));
				}else{
					System.err.println("file : "+file.getName());
                    if(file.getName().startsWith(".")){
                    	tempFileType = getExtension(file.getName());
                    	if(fileExtensions!=null && !fileExtensions.isEmpty()){
                    		if(fileExtensions.contains(tempFileType.toLowerCase(Locale.US))){
                    			System.out.println("file : "+file.getName());
                    			localList.add(file);
                    		}
                    	}
                   		
                    }
                }
			}
        }
       // logSOP("Folder : "+folder.getAbsolutePath() +"\t\t\tcount: "+localList.size());
        return localList;
    }
	
	
	/**
	 * Gives group of file list with required maxGroupFileSize. 
	 * File size passed as BigInteger data can be used statically from MyFileUtils.ONE_MB_BI, MyFileUtils.TWENTY_MB_BI etc.
	 * 
	 *  @param filesList : list of total files.
	 *  @param maxGroupSize : BigInteger for max size for file group.
	 *  @return {@link ArrayList} : returns list of array list containing files.This arrayList consists of group of files for 'maxGroupSize' passed as param.  
	 * */
	public ArrayList<ArrayList<File>> getFilesGroupOfSize(ArrayList<File> filesList,BigInteger maxGroupSize) {
			
			ArrayList<ArrayList<File>> resultList = new ArrayList<ArrayList<File>>();
			ArrayList<File> tempList = new ArrayList<File>();
			for (File file : filesList) {
				
				tempList.add(file);
	
				if(getfilesTotalSizeLong(tempList) 	> 	maxGroupSize.longValue() ) {
					resultList.add(new ArrayList<File>(tempList));	
					tempList.clear();
				}
			}
			resultList.add(tempList);
			
			return resultList;
		}
	
	
	
	
	
	
	
	/*																	 		************	get	directories **************************/
	public ArrayList<File> getDirectoiesInside(File directory) {
		 ArrayList<File> dir_List = new ArrayList<File>();
	        FileFilter fileFilter =  new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isDirectory();
				}
			};
	        File[] filesRoot = directory.listFiles(fileFilter);
	        if(filesRoot !=null){
	        	dir_List = new ArrayList<File>(Arrays.asList(filesRoot));
	        }
	        return dir_List;
	}
	public ArrayList<File> getDIRs_PublicExternalSDCard() {
        ArrayList<File> paths_publicAllDIRs = new ArrayList<File>();
        File rootDIR = Environment.getExternalStorageDirectory();
        System.out.println("rootDIR : "+rootDIR);
        paths_publicAllDIRs = getDirectoiesInside(rootDIR);					logArrayListFileNames("************		Public_ExternalDIRs		************",paths_publicAllDIRs);
        return paths_publicAllDIRs;
    }
	public ArrayList<File> getDIRs_PrivateExternalSDCard() {
        ArrayList<File> paths_privateAllDIRs = new ArrayList<File>();
        File rootDIR = context.getExternalFilesDir(null);
        System.out.println("rootDIR : "+rootDIR);
        paths_privateAllDIRs = getDirectoiesInside(rootDIR);				logArrayListFileNames("************		Private_ExternalDIRs		************",paths_privateAllDIRs);
        return paths_privateAllDIRs;
    }
	public ArrayList<File> getDIRs_Cache() {
        ArrayList<File> paths_cacheDIRs = new ArrayList<File>();
        paths_cacheDIRs.add(getFolder_internalCache());
        paths_cacheDIRs.add(getFolder_externalCache());
        return paths_cacheDIRs;
    }
	public File searchDirectory(File searchIn,File searchFor) {
		File file=null;
		ArrayList<File> sub_dir = getDirectoiesInside(searchIn);
		if(!sub_dir.isEmpty()){
				for (File subDIR : sub_dir) {
					if (subDIR.getName().equals(searchFor.getName())) {
						file = subDIR;
					} else {
						file = searchDirectory(subDIR, searchFor);
					}
					if(file!=null){
						return file;
					}
				}
		}
		return file;
	}
	public File searchFile(File directory,File searchFor) {
		return searchDirectory(directory, searchFor);
	}
   
	
	
	
	/*	
     * 
     * 
     * 
     * 
     * 															******	actual file operations	: read/write/copy/move/delete/hide*****
     * 
     * 
     * 
     * */
    public boolean writeData(File file,String data) {
        FileOutputStream fos = null;
        boolean status=false;
        try {
            fos = new FileOutputStream(file);
            fos.write(data.getBytes());

            System.out.println("Data written to file '"+file.getName()+"' saved successfully !!");
            status = true;
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) System.out.println("Exception : File not found");
            else if(e instanceof IOException) System.out.println("Exception : IO exception");
        }finally {try {if(fos!=null) {fos.close();}} catch (Exception e2) {System.out.println("Exception in closing fileOutputStream");}}
		return status;
    }
    public String readData(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int read=-1;
            StringBuffer buffer = new StringBuffer();
            while ((read = fis.read()) != -1 ) {
                buffer.append((char)read);
            }
            return buffer.toString();
            
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) System.out.println("Exception : File not found");
            else if(e instanceof IOException) System.out.println("Exception : IO exception");
        }finally {try {if(fis!=null) {fis.close();}} catch (Exception e2) {System.out.println("Exception in closing fileInputStream");}}
        return null;
    }
    public byte[] readFileBytes(File file) {
		FileInputStream fileInputStream = null;
		byte[] fileContent = null;
		try {
			
			fileInputStream = new FileInputStream(file);
			fileContent = new byte[(int) file.length()];
			fileInputStream.read(fileContent);

		} catch (FileNotFoundException e) {System.out.println("File not found" + e);
		} catch (IOException ioe) {System.out.println("EPathxception while reading file " + ioe);
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error while closing stream: " + ioe);
			}
		}
		return fileContent;
	}
	public boolean writeFileBytes(File file, byte[] data) {
		FileOutputStream fos = null;
		boolean fileWriteStatus = false;
		try {
			fos = new FileOutputStream(file);
			fos.write(data);
			fileWriteStatus = true;
		} catch (FileNotFoundException e) {
			System.out.println("File not found" + e);
		} catch (IOException ioe) {
			System.out.println("Exception while writing file " + ioe);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException ioe) {
				System.out.println("Error while closing stream: " + ioe);
			}
		}
		return fileWriteStatus;
	}
	
	public File makeDirectory(String dirName) {
	        File directory = new File(dirName);
	        //check if the location exists
	        if (!directory.exists()) {
	            //let's try to create it
	            try {
	                directory.mkdirs();
	            } catch (SecurityException secEx) {directory = null;}
	        }
	        return directory;
	 }
	public boolean confirmDirectory(File _file) {
		if (_file.isDirectory()){  System.out.println("confirmDIR : already exists");
			return true; // already exists
		}
		if (_file.exists()){ System.out.println("confirmDIR : already exists, but is not a directory");
			return false; // already exists, but is not a directory
		}
		System.out.println("confirmDIR : creating requested DIR ==>   "+_file);
		return _file.mkdirs(); // create it
}
    
	public boolean copyFile(File sourceFile, File destFile){
        if (!destFile.exists()) {
            try {		destFile.createNewFile();		} catch (IOException e) {System.err.println("Destination file create failed.");return false;}
        }

        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(sourceFile);
            out = new FileOutputStream(destFile);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len=-1;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (Exception exception) {exception.printStackTrace();return false;}
        finally {
            try {if(in!=null){in.close();}} catch (Exception e) {e.printStackTrace();}
            try {if(out!=null){out.close();}} catch (Exception e) {e.printStackTrace();}
        }
        return true;
    }
    public void copyDirectory(File sourceDir, File destDir){
		if(confirmDirectory(destDir)){
			try {
				File[] children = sourceDir.listFiles();

				for (File sourceChild : children) {
					String name = sourceChild.getName();
					File destChild = new File(destDir, name);
					if (sourceChild.isDirectory()) {
						copyDirectory(sourceChild, destChild);
					} else {
						copyFile(sourceChild, destChild);
					}
				}
			} catch (Exception e) {e.printStackTrace();}
		}else{
			System.err.println("Destination directory not exists......Copying failed.");
		}
	}
    
    public boolean moveFile(File sourceFile, File destFile) {
        if(copyFile(sourceFile, destFile) && deleteFile(sourceFile)) {
            return true;
        }else {
            return false;
        }
    }
    public boolean moveFile_lightWeight(File sourceFile,File destination) {
    	 if(sourceFile.renameTo(destination)){
    	        Log.v("temp", "Move file successful.");
    	        return true;
    	    }
    	 Log.v("temp", "Move file failed.");
    	 return false;
	}
   
    public void moveDirectory(File sourceDir, File destDir) {
		copyDirectory(sourceDir, destDir);
		deleteDirectory(sourceDir);
	}
   
    public boolean deleteFile(File file) {
        return file.delete();
    }
    public boolean deleteDirectory(File dir) {
		boolean a = true;
		try {
			File[] filenames = dir.listFiles();
			if (filenames != null && filenames.length > 0) {
				for (File tempFile : filenames) {
					if (tempFile.isDirectory()) {
						deleteDirectory(tempFile);
					} else {
						tempFile.delete();
					}
				}
			} else {
				dir.delete();
			}
		} catch (Exception e) {e.printStackTrace();return false;}
		return a;
	}
    public boolean createAndWriteData(File file,String data) {
   	 try {
			if(!file.exists() && file.createNewFile()){
				System.out.println("File not exist : creating and writing file....");
				return writeData(file, data);
			}else{
				System.out.println("File exist :  writing file....");
				return writeData(file, data);
			}
		} catch (IOException e) {e.printStackTrace();System.err.println("exception : File creation failed");}
   	 return false;
	}
    public boolean createNoMediaFile(File directory) {
    	 File output = new File(directory, ".nomedia");
    	 try {
			return output.createNewFile();
		} catch (IOException e) {e.printStackTrace();}
    	 return false;
	}
    public boolean createDirectoryWithNoMediaFile(File directory) {
    	if(confirmDirectory(directory) && createNoMediaFile(directory)){
    		return true;
    	}
   	 	return false;
	}
   
    public boolean hideFile(File file){
	    File dstFile = new File(file.getParent(), "." + file.getName());
	    return file.renameTo(dstFile);
	}
    public boolean unHideFile(File file){
    	if(!file.getName().startsWith(".")){
    		return false;
    	}
	    File dstFile = new File(file.getParent(), file.getName().replaceFirst(".", ""));
	    return file.renameTo(dstFile);
	}
	/*
    public boolean encryptFile_deviceBased(File inputFile,File outputFile) {
    	if(!outputFile.exists())	try {outputFile.createNewFile();} catch (IOException e) {L.e("enable to create output file");return false;}
		
    	DBManagerEncryp dbme =  new DBManagerEncryp(context, "It is quiet understanding, mutual confidence, sharing and forgiving.");
		dbme.create();
		byte[] temp = dbme.encryptData(readFileBytes(inputFile));
		if(temp!=null){
					boolean b = writeFileBytes(outputFile, temp);
					if(b){
						System.out.println("success encryption : "+inputFile.getName() +"	to "+outputFile.getName());
						return true;
					}
		}else{L.e("read encrypted data is null");}
		if(dbme!=null)dbme.close();
		return false;
	}
    public boolean decryptFile_deviceBased(File inputFile,File outputFile) {
		if(!outputFile.exists())	try {outputFile.createNewFile();} catch (IOException e) {L.e("enable to create output file");return false;}
		DBManagerEncryp dbme =  new DBManagerEncryp(context, "It is quiet understanding, mutual confidence, sharing and forgiving.");
		try {
			dbme.create();
			byte[] temp = dbme.decryptData(readFileBytes(inputFile));
			if(temp!=null){
						boolean b = writeFileBytes(outputFile, temp);
						if(b){
							System.out.println("success decryption : "+inputFile.getName()+" to "+outputFile.getName());
							return true;
						}
			}else{L.e("read decrypted data is null");}
			if(dbme!=null)dbme.close();
		} catch (Exception e) {e.printStackTrace();}
		finally{if(dbme!=null)dbme.close();}
		return false;
	}
	
	public int encryptAllFiles_deviceBased(ArrayList<File> fileList) {
		int numOfSuccessfulEncryption=0;
		if(fileList!=null && !fileList.isEmpty()) {
			for (File file : fileList) {
				if(encryptFile_deviceBased(file, file)) {
					numOfSuccessfulEncryption++;
				}
			}
		}
		return numOfSuccessfulEncryption;
	}
	public int decryptAllFiles_deviceBased(ArrayList<File> fileList) {
		int numOfSuccessfulEncryption=0;
		if(fileList!=null && !fileList.isEmpty()) {
			for (File file : fileList) {
				if(decryptFile_deviceBased(file, file)) {
					numOfSuccessfulEncryption++;
				}
			}
		}
		return numOfSuccessfulEncryption;
	}
	public ArrayList<File> encryptAllFiles_fork_deviceBased(ArrayList<File> fileList) {
		ArrayList<File> encFileList = new ArrayList<File>();
		if(fileList!=null && !fileList.isEmpty()) {
			for (File file : fileList) {
				File temp = new File("en_"+file.getName());
				try {
					temp.createNewFile();
					if(encryptFile_deviceBased(temp, temp)) {
						encFileList.add(temp);
					}
				} catch (IOException e) {e.printStackTrace();}
				
			}
		}
		return encFileList;
	}
	public ArrayList<File> decryptAllFiles_fork_deviceBased(ArrayList<File> fileList) {
		ArrayList<File> decFileList = new ArrayList<File>();
		if(fileList!=null && !fileList.isEmpty()) {
			for (File file : fileList) {
				File temp = new File("dec_"+file.getName());
				try {
					temp.createNewFile();
					if(decryptFile_deviceBased(temp, temp)) {
						decFileList.add(temp);
					}
				} catch (IOException e) {e.printStackTrace();}
				
			}
		}
		return decFileList;
	}
	*/
	
	public void openFile(Context context,File file,String chooserTitle) {
		try {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			Uri uri = Uri.fromFile(file);
			String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(uri.toString()));
			intent.setDataAndType(uri, type == null ? "*/*" : type);
			context.startActivity((Intent.createChooser(intent,chooserTitle)));
		} catch (Exception e) {e.printStackTrace();}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    /**
    * Check for a directory if it is possible to create files within this directory, either via normal writing or via
    * Storage Access Framework.
    *
    * @param folder The directory
    * @return true if it is possible to write in this directory.
    */
   /* public boolean isWritableNormalOrSaf(final File folder) {
	    // Verify that this is a directory.
	    if (!folder.exists() || !folder.isDirectory()) {
	    	return false;
	    }
	    // Find a non-existing file in this directory.
	    int i = 0;
	    File file;
	    do {
	    	String fileName = "AugendiagnoseDummyFile" + (++i);
	    	file = new File(folder, fileName);
	    }while (file.exists());
	    
	    // First check regular writability
	    if (isWritable(file)) {
	    	return true;
	    }
	    // Next check SAF writability.
	    DocumentFile document = getDocumentFile(file, false);
	    if (document == null) {
	    	return false;
	    }
	    // This should have created the file - otherwise something is wrong with access URL.
	    boolean result = document.canWrite() && file.exists();
	    // Ensure that the dummy file is not remaining.
	    document.delete();
	    return result;
    }*/
    /**
    * Get a list of external SD card paths. (Kitkat or higher.)
    *
    * @return A list of external SD card paths.
    */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String[] getExtSdCardPaths() {
	    List<String> paths = new ArrayList<String>();
	    for (File file : context.getExternalFilesDirs("external")) {
		    if (file != null && !file.equals(context.getExternalFilesDir("external"))) {
			    int index = file.getAbsolutePath().lastIndexOf("/Android/data");
			    if (index < 0) {
			    	Log.w("AmazeFileUtils", "Unexpected external file dir: " + file.getAbsolutePath());
			    } else {
			    	String path = file.getAbsolutePath().substring(0, index);
				    try {
				    	path = new File(path).getCanonicalPath();
				    } catch (IOException e) {
				    // Keep non-canonical path.
				    }
				    paths.add(path);
			    }
		    }
	    }
	    return paths.toArray(new String[0]);
    }
    /**
    * Determine the main folder of the external SD card containing the given file.
    *
    * @param file the file.
    * @return The main folder of the external SD card containing this file, if the file is on an SD card. Otherwise,
    * null is returned.
    */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String getExtSdCardFolder(final File file) {
	    String[] extSdPaths = getExtSdCardPaths();
	    try {
		    for (int i = 0; i < extSdPaths.length; i++) {
			    if (file.getCanonicalPath().startsWith(extSdPaths[i])) {
			    	return extSdPaths[i];
			    }
		    }
		} catch (IOException e) {
		    return null;
	    }
	    return null;
    }
    /**
    * Determine if a file is on external sd card. (Kitkat or higher.)
    *
    * @param file The file.
    * @return true if on external sd card.
    */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean isOnExtSdCard(final File file) {
    	return getExtSdCardFolder(file) != null;
    }
    /**
    * Get a DocumentFile corresponding to the given file (for writing on ExtSdCard on Android 5). If the file is not
    * existing, it is created.
    *
    * @param file The file.
    * @param isDirectory flag indicating if the file should be a directory.
    * @return The DocumentFile
    */
   /* public DocumentFile getDocumentFile(final File file, final boolean isDirectory) {
	    String baseFolder = getExtSdCardFolder(file);
	    if (baseFolder == null) {
	    	return null;
	    }
	    
	    String relativePath;
	    try {
		    String fullPath = file.getCanonicalPath();
		    relativePath = fullPath.substring(baseFolder.length() + 1);
	    } catch (IOException e) {
	    	return null;
	    }
	    
	    String as = PreferenceManager.getDefaultSharedPreferences(context).getString("URI", null);
	    Uri treeUri = null;
	    if (as != null) treeUri = Uri.parse(as);
	    if (treeUri == null) {
	    	return null;
	    }
	    
	    // start with root of SD card and then parse through document tree.
	    DocumentFile document = DocumentFile.fromTreeUri(context, treeUri);
	    String[] parts = relativePath.split("\\/");
	    for (int i = 0; i < parts.length; i++) {
		    DocumentFile nextDocument = document.findFile(parts[i]);
		    if (nextDocument == null) {
			    if ((i < parts.length - 1) || isDirectory) {
			    	nextDocument = document.createDirectory(parts[i]);
			    } else {
			    	nextDocument = document.createFile("image", parts[i]);
			    }
		    }
		    document = nextDocument;
	    }
	    return document;
    }*/
    /**
    * Check is a file is writable. Detects write issues on external SD card.
    *
    * @param file The file
    * @return true if the file is writable.
    */
    public boolean isWritable(final File file) {
	    boolean isExisting = file.exists();
	    try {
	    	FileOutputStream output = new FileOutputStream(file, true);
		    try {
		    	output.close();
		    } catch (IOException e) {
		    	// do nothing.
		    }
		} catch (FileNotFoundException e) {
			return false;
	    }
	    
	    boolean result = file.canWrite();
	    // Ensure that file is not created during this process.
	    if (!isExisting) {
	    	file.delete();
	    }
	    return result;
    }
	
	
	
	
	
	
	
	
	
	
	
    
    
	private void setDefaultCustomFolderName() {
    	if (!hasAllotedAppCustomFolderName) {
			if (APP_CUSTOM_DIR_NAME == null || APP_CUSTOM_DIR_NAME.equals("")) {
				APP_CUSTOM_DIR_NAME = getAppName();
				if (APP_CUSTOM_DIR_NAME != null && !APP_CUSTOM_DIR_NAME.equals("")) {
					hasAllotedAppCustomFolderName=true;
				}
			}
		}
	}
	private String getAppName() {
			try {
				    return context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_ACTIVITIES).applicationInfo.loadLabel(context.getPackageManager()).toString();
			}catch (Exception e) {e.printStackTrace();}
			return "";
		}
	public ArrayList<File> getFilesListFromArray(File[] filesArray) {
		return new ArrayList<File>(Arrays.asList(filesArray));
	}
	public File[] getFilesArrayFromList(ArrayList<File> filesList) {
		return ((File[]) filesList.toArray());
	}
	public ArrayList<String> getPathListFromArray(File[] filesArray) {
		if(filesArray!=null && filesArray.length >0 ){
			ArrayList<String> filePaths = new ArrayList<String>();
			for (int i = 0; i < filesArray.length; i++) {
				File file = filesArray[i];
				filePaths.add(file.getAbsolutePath());
			}
			return filePaths;
		}
		return null;
	}
	public String[] getArrayFromList(ArrayList<File> filesList) {
		if (!filesList.isEmpty()) {
			String[] fileNames = new String[filesList.size()];
			for (int i = 0; i < fileNames.length; i++) {
				fileNames[i] = filesList.get(i).getAbsolutePath();
			}
			return fileNames;
		}
		return null;
	}
	public void logArrayListFileNames(String headingForLog,ArrayList<File> list) {
		if (LOG_ENABLE) {
			if (list != null && !list.isEmpty()) {
				System.out.println(""+headingForLog);
				for (File file : list) {
					System.out.println("name: " + file.getName());
				}
			} else {
				System.err.println("list passed as Null or empty");
			}
		}
	}
	public void logArrayListFilePath(String headingForLog,ArrayList<File> list) {
		if (LOG_ENABLE) {
			if (list != null && !list.isEmpty()) {
				System.out.println(""+headingForLog);
				for (File file : list) {
					System.out.println("path: " + file.getAbsolutePath());
				}
			} else {
				System.err.println("list passed as Null or empty");
			}
		}
	}
	public static String getExtension(String name) {
		String ext="";
		if (name.lastIndexOf(".") == -1) {
			ext = "";
		} else {
			int index = name.lastIndexOf(".");
			ext = name.substring(index + 1, name.length());
		}
		return ext;
	}
	public static String getExtension(File file) {
		String name = file.getName();
		String ext="";
		if (name.lastIndexOf(".") == -1) {
			ext = "";
		} else {
			int index = name.lastIndexOf(".");
			ext = name.substring(index + 1, name.length());
		}
		return ext;
	}
	public String getDate() {	try {Thread.sleep(2);} catch (Exception e){}	return new SimpleDateFormat("ddMMyyHHMMssSSS").format(new Date());	}
	private void logSOP(String logMsg) {if (LOG_ENABLE) {System.out.println("" + logMsg);}}
	private void logSEP(String logMsg) {if (LOG_ENABLE) {System.err.println("" + logMsg);}}
	
	
	public class Crypto {
	    private static final String ALGORITHM = "AES";
	    private static final String TRANSFORMATION = "AES";
	    private static final String KEY = "It$i#%qu~et un(er^ta)din=, mut8a";
	    Context context;
		private Crypto(Context context) {this.context = context;}
		
		
		public boolean encrypt(File inputFile, File outputFile)/*throws CryptoException*/ {
	       return doCrypto(Cipher.ENCRYPT_MODE, KEY, inputFile, outputFile);
	    }
	    public boolean decrypt(File inputFile, File outputFile)/*throws CryptoException*/ {
	        return doCrypto(Cipher.DECRYPT_MODE, KEY, inputFile, outputFile);
	    }

	    public  int encryptAll(ArrayList<File> fileList) {
			int numOfSuccessfulEncryption=0;
			if(fileList!=null && !fileList.isEmpty()) {
				for (File file : fileList) {
					if(encrypt(file, file)) {
						numOfSuccessfulEncryption++;
					}
				}
			}
			return numOfSuccessfulEncryption;
		}
		public  int decryptAll(ArrayList<File> fileList) {
			int numOfSuccessfulEncryption=0;
			if(fileList!=null && !fileList.isEmpty()) {
				for (File file : fileList) {
					if(decrypt(file, file)) {
						numOfSuccessfulEncryption++;
					}
				}
			}
			return numOfSuccessfulEncryption;
		}
		
		
		public  ArrayList<File> encryptAllFiles_fork(ArrayList<File> fileList) {
			ArrayList<File> encFileList = new ArrayList<File>();
			if(fileList!=null && !fileList.isEmpty()) {
				for (File file : fileList) {
					File temp = new File("en_"+file.getName());
					try {
						temp.createNewFile();
						if(encrypt(temp, temp)) {
							encFileList.add(temp);
						}
					} catch (IOException e) {e.printStackTrace();}
					
				}
			}
			return encFileList;
		}
		public  ArrayList<File> decryptAllFiles_fork(ArrayList<File> fileList) {
			ArrayList<File> decFileList = new ArrayList<File>();
			if(fileList!=null && !fileList.isEmpty()) {
				for (File file : fileList) {
					File temp = new File("dec_"+file.getName());
					try {
						temp.createNewFile();
						if(decrypt(temp, temp)) {
							decFileList.add(temp);
						}
					} catch (IOException e) {e.printStackTrace();}
					
				}
			}
			return decFileList;
		}
	    
		
		
		
		private  boolean doCrypto(int cipherMode, String key, File inputFile,File outputFile)/* throws CryptoException*/ {
		        try {
		            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
		            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		            cipher.init(cipherMode, secretKey);
		             
		            FileInputStream inputStream = new FileInputStream(inputFile);
		            byte[] inputBytes = new byte[(int) inputFile.length()];
		            inputStream.read(inputBytes);
		             
		            byte[] outputBytes = cipher.doFinal(inputBytes);
		             
		            FileOutputStream outputStream = new FileOutputStream(outputFile);
		            outputStream.write(outputBytes);
		             
		            inputStream.close();
		            outputStream.close();
		             return true;
		        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException ex) {
		            return false;//throw new CryptoUtils().new CryptoException("Error encrypting/decrypting file", ex);
		        }
		    }
			
	    
	   /* private class CryptoException extends Exception {
	        public CryptoException() {}
	        public CryptoException(String message, Throwable throwable) {	super(message, throwable);	}
	    }*/
	}
}