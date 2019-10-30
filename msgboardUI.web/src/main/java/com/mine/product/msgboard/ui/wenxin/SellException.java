package com.mine.product.msgboard.ui.wenxin;
//异常类
public class SellException extends RuntimeException {
	private static final long serialVersionUID = -4190699908595470768L;
	private Integer code;

    public SellException(Integer code, String msg) {
        super(msg);
        this.setCode(code);
    }

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.setCode(resultEnum.getCode());
    }

	/**
	 * @return the code
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(Integer code) {
		this.code = code;
	}

}
