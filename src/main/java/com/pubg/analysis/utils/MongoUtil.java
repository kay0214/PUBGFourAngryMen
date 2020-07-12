package com.pubg.analysis.utils;


import org.springframework.data.mongodb.core.aggregation.Field;
import org.springframework.data.mongodb.core.aggregation.Fields;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mongodb 工具
 *
 * @author yangy
 * @date 2020/7/12 13:25
 */
public class MongoUtil {

	/**
	 * 根据类生成字段映射
	 *
	 * @param clazz        类
	 * @param targetPrefix 映射前缀
	 * @param includeSuper 是否包括父类
	 * @param <T>          类型
	 * @return 聚合操作用的Field数组
	 */
	public static <T> Field[] getFieldsByClass(Class<T> clazz, String targetPrefix, boolean includeSuper) {

		List<java.lang.reflect.Field> reflectedFields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
		if (includeSuper) {
			reflectedFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
		}
		Field[] fieldArray = new Field[reflectedFields.size()];
		for (int i = 0; i < reflectedFields.size(); i++) {
			String name = reflectedFields.get(i).getName();
			fieldArray[i] = Fields.field(name, targetPrefix + name);
		}
		return fieldArray;
	}
}
