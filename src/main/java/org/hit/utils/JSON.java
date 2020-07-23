package org.hit.utils;

import org.json.JSONObject;

public class JSON {
    private  JSONObject object = null;

    public JSON(){
        this.object = new JSONObject();
    }

    public void add(String key, String value){
        this.object.put(key, value);
    }
    public boolean remove(String key){
        try {
            String value = this.object.get(key).toString();
            this.object.remove(key);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public String getString(){
        return this.object.toString();
    }
}
