package com.valorin.configuration.languagefile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.valorin.Dantiao;

public class LanguageFileLoader {
	
	private Map<File, List<String>> lang;
	private List<String> defaultLang;
	private List<File> customLangList;
	private Map<String, List<String>> subDefaultLang;
	
    public LanguageFileLoader() {
      lang = new HashMap<File, List<String>>();
      defaultLang = new ArrayList<String>();
      subDefaultLang = new HashMap<String, List<String>>();
      customLangList = addLanguages();
      
      loadDefaultLanguage();
      loadSubDefaultLanguage();
      
      copyChecker();
      customLangList = addLanguages();
      LanguageFileChecker();
      loadLang();
    }
    
    public void close() {
      lang.clear();
      defaultLang.clear();
      subDefaultLang.clear();
      customLangList.clear();
    }
    
    public List<File> getLanguagesList() {
      return customLangList;
    }
    
    public Map<File, List<String>> getLang() {
      return lang;
    }
    
    public List<String> getDefaultLang() {
      return defaultLang;
    }
	
	private void loadDefaultLanguage() {
	  try {
	    BufferedReader bufr = 
	    new BufferedReader(new InputStreamReader(Dantiao.getInstance().getResource("DefaultLanguage.txt"),"UTF-8"));
	    String s = null;
	    while ((s = bufr.readLine()) != null) {
	      defaultLang.add(s);
	    }
	    bufr.close();
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	}
	
	private List<File> addLanguages() {//获取所有语言文件 
	  List<File> fileList = new ArrayList<File>();
	  File file = new File("plugins/Dantiao/Languages");
	  File[] files = file.listFiles();
	  if (files == null)  {
		return fileList;
	  }
	  for (File f : files)  {
		if (f.isFile()) {
		  if (f.getName().substring(f.getName().lastIndexOf(".") + 1).equals("txt")) {
		    fileList.add(f);
		  }
		}
	  }
	  return fileList;
	}
	
	private void loadLang() {//载入语言文件的内容
	  lang.clear();
	  List<File> filelist = customLangList;
	  if (filelist == null) {
		return;
	  }
	  for (File file : filelist) {
		List<String> messages = new ArrayList<String>();
		try {
		  FileReader fr = new FileReader(file);
		  BufferedReader bufr = new BufferedReader(fr);
		  String s = null;
		  while ((s = bufr.readLine()) != null) {
			messages.add(s);
		  }
		  bufr.close();
		  fr.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		lang.put(file, messages);
	  }
	}
	
	private void copyChecker() {
        File testFile1 = new File("plugins/Dantiao/Languages/zh_CN.txt");
        File testFile2 = new File("plugins/Dantiao/Languages/zh_TW.txt");
        File testFile3 = new File("plugins/Dantiao/Languages/en_US.txt");
        File fileParent = testFile1.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        File[] flist = {testFile1,testFile2,testFile3};
        for (File testFile : flist)
        {
          if (!testFile.exists())
          {
		    try 
		    {
			  testFile.createNewFile();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		  }
		}
    }
	
	private void LanguageFileChecker() {
	  List<File> fileList = customLangList;
	  for (File file : fileList) {
		int count = 0;
		FileInputStream fis;
		try  {
		  fis = new FileInputStream(file);
		  Scanner scanner = new Scanner(fis);
		  while(scanner.hasNextLine()) {
			scanner.nextLine();
			count++;
		  }
		  scanner.close();
		  if (count < defaultLang.size()) {//检测到行数缺失
			loadSubDefaultLanguage();
			List<String> mould = defaultLang; //提示语模板
			if (file.getName().equals("zh_CN.txt") || 
				file.getName().equals("zh_TW.txt") || 
				file.getName().equals("en_US.txt"))
			{
			   mould = subDefaultLang.get(file.getName().replace(".txt", ""));
			}
			
			List<String> logMessageList = new ArrayList<String>();

			try {
			  FileReader fr = new FileReader(file);
			  BufferedReader bufr = new BufferedReader(fr);
			  String s = null;
			  while ((s = bufr.readLine()) != null) {
				  logMessageList.add(s);
			  }
			  bufr.close();
			} catch (Exception e) {
			  e.printStackTrace();
			}
			
			for (int i = logMessageList.size();i < mould.size();i++)
			{
				logMessageList.add(mould.get(i));
			}
			
			try {
			  BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			  for (int i = 0;i < logMessageList.size();i++) {
				if (i == logMessageList.size()-1) {
				  writer.write(logMessageList.get(i));
				} else {
			      writer.write(logMessageList.get(i)+"\n");
				}
			  }
			  writer.flush();
			  writer.close();
			} catch (IOException e) {
			  e.printStackTrace();
			}
		  }
		} catch (FileNotFoundException e1) {
		  e1.printStackTrace();
		}
	  }
	}
	
	private void loadSubDefaultLanguage() {
	  String[] locs = {"Languages/zh_CN.txt","Languages/zh_TW.txt","Languages/en_US.txt"};
	  for (String loc : locs) {
		try {
		  BufferedReader bufr = 
		  new BufferedReader(new InputStreamReader(Dantiao.getInstance().getResource(loc),"UTF-8"));
		  String s = null;
		  List<String> mList = new ArrayList<String>();
		  while ((s = bufr.readLine()) != null) {
			mList.add(s);
		  }
		  subDefaultLang.put(loc.replace("Languages/", "").replace(".txt", ""), mList);
		  bufr.close();
		} catch (Exception e) {
		  e.printStackTrace();
	    }
	  }
	}
}
