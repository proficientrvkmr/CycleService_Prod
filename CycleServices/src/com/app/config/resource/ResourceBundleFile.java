package com.app.config.resource;

import java.util.ResourceBundle;

/**
 * 
 * @author Ravi Kumar
 *
 */
public class ResourceBundleFile {

	public static String getValueFromKey(String key) {
		ResourceBundle bundle1 = ResourceBundle.getBundle("com.app.config.resource.application");
		return bundle1.getString(key);

	}

}
