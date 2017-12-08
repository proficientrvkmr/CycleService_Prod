package com.app.mail;

import java.util.ResourceBundle;

public class ResourceBundleFile {
	
	 public static String getValueFromKey(String p_strKey) {
	        ResourceBundle bundle1 = ResourceBundle.getBundle("com.app.service.resourceBundlePath");    
	        return bundle1.getString(p_strKey);

 }

}
