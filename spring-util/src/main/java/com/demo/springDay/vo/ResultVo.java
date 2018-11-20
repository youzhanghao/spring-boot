package com.demo.springUtil.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by base.it on 2017/4/18.
 */
public class ResultVo extends HashMap<String, Object> {

	private static final long serialVersionUID = 7958823206191412535L;

	public ResultVo() {
        //200表示执行成功
        put("code", 200);
    }

    public static ResultVo error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static ResultVo error(String msg) {
        return error(500, msg);
    }

    public static ResultVo error(int code, String msg) {
        ResultVo r = new ResultVo();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultVo ok(String msg) {
        ResultVo r = new ResultVo();
        r.put("msg", msg);
        return r;
    }

    public static ResultVo ok(Map<String, Object> map) {
        ResultVo r = new ResultVo();
        r.putAll(map);
        return r;
    }

    public static ResultVo ok() {
        return new ResultVo();
    }

    @Override
    public ResultVo put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    
    public boolean isSuccess(){
    	return this.get("code") != null && this.get("code").toString().equals("200") ;
    }

    /* edit by youzhanghao for dto params*/
    /**
     * mapper error
     * @param errorCode
     * @param errMsgMap
     * @return
     */
    public static ResultVo error(Integer errorCode, Map<String, String> errMsgMap){
        return getFailure(errorCode, errMsgMap);
    }

    /* edit by youzhanghao for dto params*/
    /**
     * mapper error convert
     * @param errorCode
     * @param errMsgMap
     * @return
     */
    public static ResultVo getFailure(Integer errorCode, Map<String, String> errMsgMap){
        StringBuilder stb = new StringBuilder();
        for (String errMsg : errMsgMap.values()){
            stb.append(errMsg);
        }
        return error(errorCode, stb.toString());
    }

    /* edit by youzhanghao for BusinessErrorCode */
    /**
     * BusinessErrorCode message
     * @param key
     * @param value
     * @return
     */
    public ResultVo put(Integer key, Object value) {
        super.put(String.valueOf(key), value);
        return this;
    }
}
