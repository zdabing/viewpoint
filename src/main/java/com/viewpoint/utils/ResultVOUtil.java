package com.viewpoint.utils;

import com.viewpoint.vo.ResultVO;

/**
 * Result工具类
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success(){
        return success(null);
    }

    public static ResultVO success(Object object,Long count){
        ResultVO resultVO = success(object);
        resultVO.setCount(count);
        return resultVO;
    }

    public static ResultVO error(Integer code , String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
