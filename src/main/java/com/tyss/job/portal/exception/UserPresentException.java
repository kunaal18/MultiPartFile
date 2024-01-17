package com.tyss.job.portal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@Getter
@AllArgsConstructor
public class UserPresentException extends RuntimeException {

	private final String message;

}
