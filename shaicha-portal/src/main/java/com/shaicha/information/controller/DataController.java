package com.shaicha.information.controller;

import com.shaicha.information.domain.ResultEyesightDO;
import com.shaicha.information.domain.StudentDO;
import com.shaicha.information.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    private ResultService resultService;

    /**
     * 星康接口 查询用户
     * @param AppKey
     * @param AppSecret
     * @param userID
     * @return
     * {
      "json":
       {
        "status":0,
        "errmsg":"",
        "result":  {"userName": “XXX”,”userClass”:”XXX” }
    }
    }
     */
    @PostMapping("/info")
    public Map<String,Object> getUserInfo(String AppKey, String AppSecret, Long userID, String Right1,
                                          String Right2,
                                          String Left1,
                                          String Left2,
                                          Integer isGlasses){
        if(isGlasses==null) {//数据获取
            StudentDO studentInfoByUserID = resultService.getStudentInfoByUserID(userID);
            Map<String, Object> resultMap = new HashMap<>();
            Map<String, Object> result11Map = new HashMap<>();
            if (studentInfoByUserID == null) {
                resultMap.put("status", 1);
                resultMap.put("errmsg", "用户不存在!");
                result11Map.put("userName", "");
                result11Map.put("userClass", "");
                resultMap.put("reslut", result11Map);
            } else {

                result11Map.put("userName", studentInfoByUserID.getStudentName());
                result11Map.put("userClass", studentInfoByUserID.getGrade() + studentInfoByUserID.getStudentClass());
                resultMap.put("result", result11Map);
                resultMap.put("status", 0);
                resultMap.put("errmsg", "数据查询到 姓名为【" + studentInfoByUserID.getStudentName() + "】，班级为【" + studentInfoByUserID.getGrade() + studentInfoByUserID.getStudentClass() + "】");
            }
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("json", resultMap);
            return jsonMap;
        }
        if(isGlasses!=null){//数据保存
            ResultEyesightDO resultEyesightDO  = new ResultEyesightDO();
            resultEyesightDO.setStudentId(userID);
            resultEyesightDO.setCheckDate(new Date());
            if(Right2!=null && (Right2.contains("<") || Right2.contains(">")))
                Right2=Right2.substring(1);
            if(Left2!=null && (Left2.contains("<") || Left2.contains(">")))
                Left2=Left2.substring(1);
            if(isGlasses==0){//裸眼视力
                resultEyesightDO.setNakedFarvisionOd(Right2);
                resultEyesightDO.setNakedFarvisionOs(Left2);
            }
            if(isGlasses==1){//戴镜视力
                resultEyesightDO.setCorrectionFarvisionOd(Right2);
                resultEyesightDO.setCorrectionFarvisionOs(Left2);
            }
            List<ResultEyesightDO> eyeSight = resultService.getEyeSight(userID);
            int result=0;
            if(eyeSight.size()==0){
                result=resultService.saveDianziEye(resultEyesightDO);
            }else{
                result = resultService.updateDianziEye(resultEyesightDO);
            }
            if(result>0){
                resultService.updateLastCheckTime(userID,new Date());
            }
            Map<String,Object> resultMap11 = new HashMap<>();
            resultMap11.put("object","");
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("result",resultMap11);
            resultMap.put("status" , result>0?0:1);
            resultMap.put("errmsg" , result>0?"数据保存成功":"数据保存失败");
            Map<String,Object> jsonMap = new HashMap<>();
            jsonMap.put("json",resultMap);
            return jsonMap;
        }
        return null;
    }

}
