package org.hit.utils;

import org.json.JSONObject;
import org.nem.core.utils.HexEncoder;

public class PublicKey {
    private org.nem.core.crypto.PublicKey publicKey;
    public PublicKey(String key){
        this.publicKey = org.nem.core.crypto.PublicKey.fromHexString(key);
    }
    public String getDraw(){
        return this.publicKey.toString();
    }

    public boolean verify(String data, String signature){
        try {
            JSONObject json = new JSONObject(data);
            String dataSign = json.toString();
            org.nem.core.crypto.KeyPair keyPair = new org.nem.core.crypto.KeyPair(this.publicKey);
            org.nem.core.crypto.Signer signer = new org.nem.core.crypto.Signer(keyPair);
            return signer.verify(dataSign.getBytes(),new org.nem.core.crypto.Signature(HexEncoder.getBytes(signature)));
        }
        catch (Exception e){
            return false;
        }

    }

}
