package com.shaicha.common.utils;

public class ShiLiZhuanHuanUtils {
    
    public static String zhuanhuanshiliForSc(String shili){
        if (Double.parseDouble(shili)<4.0){
            shili="0.1";
        }
        if (Double.parseDouble(shili)>5.0){
            shili="1.0";
        }
        if ("4.0".equals(shili)){
            shili="0.1";
        }

        if ("4.1".equals(shili)){
            shili="0.12";
        }
        if ("4.2".equals(shili)){
            shili="0.15";
        }
        if ("4.3".equals(shili)){
            shili="0.2";
        }
        if ("4.4".equals(shili)){
            shili="0.25";
        }
        if ("4.5".equals(shili)){
            shili="0.3";
        }
        if ("4.6".equals(shili)){
            shili="0.4";
        }
        if ("4.7".equals(shili)){
            shili="0.5";
        }
        if ("4.8".equals(shili)){
            shili="0.6";
        }
        if ("4.9".equals(shili)){
            shili="0.8";
        }
        if ("5.0".equals(shili)){
            shili="1.0";
        }
        return shili;
    }

    public static String zhuanhuanshiliForLdFenShu(String shili){
        
        if ("10/100".equals(shili)){
            shili="0.1";
        }
        if ("10/80".equals(shili)){
            shili="0.12";
        }
        if ("10/60".equals(shili)){
            shili="0.15";
        }
        if ("10/50".equals(shili)){
            shili="0.2";
        }
        if ("10/40".equals(shili)){
            shili="0.25";
        }
        if ("10/30".equals(shili)){
            shili="0.3";
        }
        if ("10/25".equals(shili)){
            shili="0.4";
        }
        if ("10/20".equals(shili)){
            shili="0.5";
        }
        if ("10/15".equals(shili)){
            shili="0.6";
        }
        if ("10/12.5".equals(shili)){
            shili="0.8";
        }
        if ("10/10".equals(shili)){
            shili="1.0";
        }
        return shili;
    }


}
