package recommend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonUtil {
    public static JSONObject StrToJson(String str) {
        JSONObject jsonObject = JSONObject.parseObject(str);
        return jsonObject;
    }

    public static List<User> DealJson(String str) {
        JSONObject jsonObject = StrToJson(str);
        List<User> userList = new ArrayList<>();
        for (String key : jsonObject.keySet()) {
//            System.out.println(key);
                User user = new User();
                user.setUser_id(key);
                DealSingleUser(jsonObject.getJSONObject(key) ,user);
                userList.add(user);
        }
        return userList;
    }

    public static Map<String , String> casetable(String str){
        JSONObject jsonObject = StrToJson(str);
        Map<String,String> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            DealSingleUser(jsonObject.getJSONObject(key) , map);
        }
        return map;
    }


    public static User DealSingleUser(JSONObject jsonObject , User user) {
        JSONArray cases = jsonObject.getJSONArray("cases");
        for (int i = 0 ; i <  cases.size() ; i ++) {
            JSONObject json_case = (JSONObject) cases.get(i);
//            System.out.println(json_case.getString("case_id"));
            user.getCases().put(json_case.getString("case_id") , json_case.getIntValue("final_score"));
//            user.getCase_id().add(json_case.getString("case_id"));
//            user.getScore().add(json_case.getIntValue("final_score"));
        }
        return user;
    }

    public static void DealSingleUser(JSONObject jsonObject , Map<String ,String> map) {
        JSONArray cases = jsonObject.getJSONArray("cases");
        for (int i = 0 ; i <  cases.size() ; i ++) {
            JSONObject json_case = (JSONObject) cases.get(i);
            map.put(json_case.getString("case_id") , json_case.getString("case_zip"));
        }
    }


    public static void main(String[] args) throws IOException {
        DealJson(FileUtil.readJsonFile("D:\\ChromeCoreDownloads\\sample.json"));
    }
}
