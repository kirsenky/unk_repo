package unk.test.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {
    static final String mainURL = "http://localhost:8080/t1/Item\n";
    public enum ReqMethod {
        GET,
        POST,
        PUT,
        DELETE
    }

    private int queryServer(ReqMethod method) {
        int result;
        try {
            URL srvURL = new URL(mainURL );
            HttpURLConnection conn = (HttpURLConnection) srvURL.openConnection();
            conn.setRequestMethod(method.toString());
            conn.setRequestProperty("User-Agent","test-client");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Length","0");
            result = conn.getResponseCode();
            String res=conn.getResponseMessage();
            System.out.println("Output from Server:\n"+res);
/*            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }*/
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            result=-2;
        }
        return result;
    }

    public static void main(String[] args) {
        int res;
        RestClient client = new RestClient();
        ReqMethod method;
        method = ReqMethod.GET;
        System.out.println("Querying Server: "+method.toString());
        res=client.queryServer(method);
        System.out.println("Server result:"+res);
    }

}