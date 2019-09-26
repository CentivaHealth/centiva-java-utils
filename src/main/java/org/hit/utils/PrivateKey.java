package org.hit.utils;

import org.nem.core.model.NetworkInfos;
import org.json.*;
public class PrivateKey {
    private org.nem.core.crypto.PrivateKey privateKey;
    public PrivateKey(String key){
        try{
            this.privateKey = org.nem.core.crypto.PrivateKey.fromHexString(key);
        }
        catch (Exception e){
            throw new IllegalArgumentException("Wrong private key");
        }
    }

    public String getDraw(){
        if(this.privateKey != null){
            return this.privateKey.toString();
        }
        return null;
    }

    public String getPublicKey(){
        try{
            return new org.nem.core.crypto.KeyPair(this.privateKey).getPublicKey().toString();
        }
        catch (Exception e){
            return null;
        }
    }
    public String getTestNetAddress(){
        try{
            NetworkInfos.setDefault(NetworkInfos.getTestNetworkInfo());
            return org.nem.core.model.Address.fromPublicKey(new org.nem.core.crypto.KeyPair(this.privateKey).getPublicKey()).toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String getMainNetAddress() {
        try {
            NetworkInfos.setDefault(NetworkInfos.getMainNetworkInfo());
            return org.nem.core.model.Address.fromPublicKey(new org.nem.core.crypto.KeyPair(this.privateKey).getPublicKey()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String signData(String data){
        try {
            JSONObject jsonObject = new JSONObject(data);
            String dataSign = jsonObject.toString();
            org.nem.core.crypto.KeyPair keyPair = new org.nem.core.crypto.KeyPair(this.privateKey);
            org.nem.core.crypto.Signer signer = new org.nem.core.crypto.Signer(keyPair);

            return signer.sign(dataSign.getBytes()).toString();
        }
        catch (Exception e){
            return null;
        }
    }
}
