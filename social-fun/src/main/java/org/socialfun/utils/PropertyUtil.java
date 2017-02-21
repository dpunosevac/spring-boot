/**
 * 
 */
package org.socialfun.utils;

import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * This class tweaks properties of input
 *
 * @author dpunosevac
 * 
 */
public class PropertyUtil {

	public static <T> List<T> checkList(final List<T> list) {
		return list == null ? Collections.<T>emptyList() : list;
	}

	public static void getNullProperties(Object source, Object target) {
		Set<String> ignoreProperties = new HashSet<String>();
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		for (PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				ignoreProperties.add(pd.getName());
		}

		if (ignoreProperties.isEmpty())
			BeanUtils.copyProperties(source, target, (String[]) null);
		else
			BeanUtils.copyProperties(source, target, ignoreProperties.toArray(new String[ignoreProperties.size()]));
	}
}
