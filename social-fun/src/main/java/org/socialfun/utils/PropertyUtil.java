/**
 * 
 */
package org.socialfun.utils;

import java.util.Collections;
import java.util.List;

/**
 * This class tweaks properties of input
 *
 * @author dpunosevac
 * 
 */
public class PropertyUtil {

	public static <T> List<T> checkList(final List<T> list){
		return list == null ? Collections.<T>emptyList() : list;
	}
}
