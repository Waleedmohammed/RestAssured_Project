package common;

import io.restassured.path.json.JsonPath;

public class commonMethods {

    public static JsonPath rowToJson(String response){

        JsonPath json = new JsonPath(response);
        return json;
    }

    public static int getIntElement(String response,String ElementKey){

        JsonPath json = new JsonPath(response);
        return json.getInt(ElementKey);
    }


    public static int elementCount(String response,String elementKey){

        JsonPath json = new JsonPath(response);
        return json.get(elementKey+".size()");
    }

}
