package com.demo.springUtil.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.*;

public class BeanValidators {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Map<String, String> validate(Validator validator, T object, Class<?>... groups) {
		Set constraintViolations = validator.validate(object, groups);
		if (!constraintViolations.isEmpty()) {
			return extractPropertyAndMessage(constraintViolations);
		} else {
			return null;
		}
	}

	public static <T> List<Map<String, String>> validate(Validator validator, List<T> objects, Class<?>... groups) {
		if (objects != null && !objects.isEmpty()) {
			List<Map<String, String>> validateResult = new ArrayList<Map<String, String>>();
			for (T object : objects) {
				Map<String, String> validateMap = validate(validator, object, groups);
				if (validateMap != null) {
					validateResult.add(validateMap);
				}
			}
			return validateResult;
		} else {
			return null;
		}
	}

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为Map<property, message>.
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> extractPropertyAndMessage(Set<? extends ConstraintViolation> constraintViolations) {
		Map<String, String> errorMessages = new HashMap<String, String>();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return errorMessages;
	}

}
