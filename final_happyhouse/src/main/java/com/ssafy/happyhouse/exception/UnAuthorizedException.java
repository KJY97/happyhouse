package com.ssafy.happyhouse.exception;

public class UnAuthorizedException extends RuntimeException {
	private static final long serialVersionUID = 3174049038847115002L;

	public UnAuthorizedException() {
		super("계정 권한이 유효하지 않습니다.\n다시 로그인을 해주세요.");
	}
}
