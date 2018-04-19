package com.codelover.utils;

public class Response<T> {
	private Integer code;
	private String msg;
	private T data;
	
	/*
	 * 自定义返回数据
	 * code 代码
	 * msg  说明Object
	 * data 数据
	 */
	public Response(Integer _code, String _msg, T _data) {
		this.code = _code;
		this.msg = _msg;
		this.data = _data;
	}
	/*
	 * 成功数据返回
	 * code 200
	 * msg  说明
	 * data 数据
	 */
	public Response(String _msg, T _data) {
		this.code = 200;
		this.msg = _msg;
		this.data = _data;
	}
	/*
	 * 成功返回
	 * code 200
	 * msg success
	 * data 数据
	 */
	public Response(T _data) {
		this.code = 200;
		this.msg = "success";
		this.data = _data;
	}
	public Response(Integer _code) {
		this.code = _code;
		this.msg = null;
		this.data = null;
	}
	
//	/*
//	 * 默认错误返回
//	 * code 400
//	 * msg  error
//	 * data null
//	 */
//	public Response() {
//		this.code = 400;
//		this.msg = "error";
//		this.data = null;
//	}
	/*
	 * 返回code和msg
	 * code 代码
	 * msg  信息
	 */
	public Response(Integer _code, String _msg) {
		this.code = _code;
		this.msg = _msg;
		this.data = null;
	}
		
	public Response(String _msg) {
		this.code = 200;
		this.msg = _msg;
		this.data = null;
	}
	public Integer getCode() 
	{
		return code;
	}
	public String getMsg()
	{
		return msg;
	}
	public T getData()
	{
		return data;
	}
	public void setCode(Integer _code)
	{
		this.code = _code;
	}
	
	public void setMsg(String _msg)
	{
		this.msg = _msg;
	}
	public void setData(T _data)
	{
		this.data = _data;
	}
	
		
//	@Override
//    public String toString() {
//        return "{" +
//                "code=" + code +
//                ", msg='" + msg + '\'' +
//                ", data=" + data +
//                '}';
//    }
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
//		System.out.println("{"+
//				"\"code\":"+
//				code+
//				",\"msg\":"+
//				"\""+msg+"\""
//				+",\"data\":"+"{"+
//				data
//				+"}}");
		return "{"+
				"\"code\":"+
				code+
				",\"msg\":"+
				"\""+msg+"\""
				+",\"data\":"+
				data
				+"}";
		
//		return super.toString();
	}
	
	
}

