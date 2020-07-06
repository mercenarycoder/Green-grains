package com.greengrains;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class JsonParser
{
    public String userLoginFromJSON(String url,String id,String pass)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try{
            Log.d("LoginData", "I reached here---> "+id);

            URL url1=new URL(url);
            httpURLConnection=(HttpURLConnection)url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            //params are made over here
            HashMap<String,String> params = new HashMap<>();
            params.put("mobile_no",id);
            params.put("password",pass);

            StringBuilder builder = new StringBuilder();
            boolean first=true;

            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
    public String userSignUpFromJSON(String url,String name,String pass,
                                     String con_pass,String mobile_number,String refer_code)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try{
            URL url1=new URL(url);
            httpURLConnection=(HttpURLConnection)url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os=httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            //params are made over here
            HashMap<String,String> params = new HashMap<>();

            params.put("mobile_number",mobile_number);
            params.put("password",pass);
            params.put("confirm_password",con_pass);

            StringBuilder builder = new StringBuilder();
            boolean first=true;

            for(Map.Entry<String,String> entry:params.entrySet())
            {
                if(first)
                    first=false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
            }
            String flow=builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current="";
            InputStream ir=httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }
public String baseGetRequest(String url)
{
    HttpURLConnection httpURLConnection = null;
    try
    {
        URL url2 = new URL(url);
        httpURLConnection=(HttpURLConnection)url2.openConnection();
        httpURLConnection.setRequestMethod("GET");
        String current="";
        InputStream ir=httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    } catch (Exception e) {
        e.printStackTrace();
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String getProfile(String url,String id)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try{
        URL url1=new URL(url);
        httpURLConnection=(HttpURLConnection)url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os=httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
        //params are made over here
        HashMap<String,String> params = new HashMap<>();

        params.put("id",id);

        StringBuilder builder = new StringBuilder();
        boolean first=true;

        for(Map.Entry<String,String> entry:params.entrySet())
        {
            if(first)
                first=false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        String flow=builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current="";
        InputStream ir=httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String updatePassword(String old,String new_pass,String confirm_pass,String url,String id)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("id", id);
        params.put("current_password",old);
        params.put("new_password",new_pass);
        params.put("confirm_password",confirm_pass);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
       catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
    finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
}
public String getProducts(String url,String id)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("service_id", id);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String getProductDetails(String url,String product_id)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("product_id", product_id);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String signUp(String url,String name,String mobile,String password,String confirm_password)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("name", name);
        params.put("mobile_number", mobile);
        params.put("password", password);
        params.put("confirm_password", confirm_password);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;

}
public String verifyOTP(String mobile,String otp,String url)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("mobile_no",mobile);
        params.put("otp",otp);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String sendOTPFirst(String url,String mobile)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("mobile_no",mobile);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String updateProfile(String url,String id,String name,String address,
                            String lankmark,String photo,String city,String latitude,String longitude)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("id",id);
        params.put("name",name);
        params.put("address",address);
        params.put("landmark",lankmark);
        params.put("city",city);
        params.put("photo",photo);
        params.put("latitude",latitude);
        params.put("longitude",longitude);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String getHistory(String url,String client_id)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("client_id",client_id);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
        //    System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String searchFunction(String url,String search)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("search",search);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
public String getGroupProducts(String url,String product_group_id)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
        //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();

        params.put("product_group_id",product_group_id);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}

public String confirmBook(String url,String customerid,String modeofpayment,String remark,
                          String couponid,String discounttype,String discountamount,String discountval
,String walletvalue,String walletamount,String storeid,String payment_resquest_id,String payment_transaction_id
,JSONArray arr)
{
    HttpURLConnection connection = null;
    BufferedReader reader = null;
    try {
        URL url2 = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url2.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json");
        httpURLConnection.connect();

        JSONObject loginData = new JSONObject();
        loginData.put("customerid",customerid );
        JSONArray jsonArray = new JSONArray();
        loginData.put("product",arr );
        loginData.put("modeofpayment", modeofpayment);
        loginData.put("remark", remark);
        loginData.put("couponid", couponid);
        loginData.put("discounttype", discounttype);
        loginData.put("discountamount",discountamount);
        loginData.put("discountval", discountval);
        loginData.put("walletvalue",walletvalue);
        loginData.put("walletamount", walletamount);
        loginData.put("storeid", storeid);
        loginData.put("payment_resquest_id",payment_resquest_id);
        loginData.put("payment_transaction_id",payment_transaction_id);

        Log.d("LoginData", "---> " + loginData);

        DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
        wr.writeBytes(loginData.toString());
        wr.flush();
        wr.close();

        InputStream stream = httpURLConnection.getInputStream();
        reader = new BufferedReader(new InputStreamReader(stream));

        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }

        String finalJson = buffer.toString();
        return finalJson;


    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    }
    return null;
}
public String confirmbooking2(String url,String customerid,String modeofpayment,String remark,
                              String couponid,String discounttype,String discountamount,String discountval
,String walletvalue,String walletamount,String storeid,String payment_resquest_id,String payment_transaction_id
        ,String product_id,String quantity)
{
    HttpURLConnection httpURLConnection=null;
    BufferedReader reader=null;
    try {
        URL url1 = new URL(url);
        httpURLConnection = (HttpURLConnection) url1.openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        //params are made over here
        HashMap<String, String> params = new HashMap<>();
        String arr[]=product_id.split(",");
        String arr1[]=quantity.split(",");
        params.put("customerid",customerid);
        params.put("modeofpayment",modeofpayment);
        for(int i=0; i < arr.length; i++) {
            params.put("product_id["+String.valueOf(i)+"]", arr[i]);
            params.put("quantity["+String.valueOf(i)+"]", arr1[i]);
        }
        params.put("remark","sai pharma mai ilaj karao");
        params.put("couponid",couponid);
        params.put("discountamount",discountamount);
        params.put("discounttype",discounttype);
        params.put("discountval",discountval);
        params.put("walletvalue",walletvalue);
        params.put("walletamount",walletamount);
        params.put("storeid",storeid);
        params.put("payment_resquest_id",payment_resquest_id);
        params.put("payment_transaction_id",payment_transaction_id);

        StringBuilder builder = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                builder.append("&");

            builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        String flow = builder.toString();
        System.out.println(flow+"\n\n");
        bufferedWriter.write(flow);
        bufferedWriter.flush();
        bufferedWriter.close();
        os.close();

        String current = "";
        InputStream ir = httpURLConnection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(ir);
        int data = inputStreamReader.read();
        while (data != -1) {
            current += (char) data;
            data = inputStreamReader.read();
            System.out.print(current);
        }
        return current;
    }
    catch (Exception e)
    {
        Log.d("Exception","Some Exceptions"+e);
    }
    finally {
        if(httpURLConnection!=null)
        {
            httpURLConnection.disconnect();
        }
    }
    return null;
}
    public String updatePassword2(String new_pass,String confirm_pass,String url,String id)
    {
        HttpURLConnection httpURLConnection=null;
        BufferedReader reader=null;
        try {
            URL url1 = new URL(url);
            httpURLConnection = (HttpURLConnection) url1.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            //httpURLConnection.setRequestProperty("Content-Type","Application/JSON");
            //httpURLConnection.connect();
           /* JSONObject jsonObject=new JSONObject();
            jsonObject.put("email",id);
            jsonObject.put("password",pass);
            Log.d("LoginData", "---> " + jsonObject);
//OutputStream out;
            DataOutputStream dr=new DataOutputStream(httpURLConnection.getOutputStream());
            dr.writeBytes(jsonObject.toString());
            dr.flush();
            dr.close();*/
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //params are made over here
            HashMap<String, String> params = new HashMap<>();

            params.put("id", id);
            params.put("new_password",new_pass);
            params.put("confirm_password",confirm_pass);

            StringBuilder builder = new StringBuilder();
            boolean first = true;

            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first)
                    first = false;
                else
                    builder.append("&");

                builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            String flow = builder.toString();
            bufferedWriter.write(flow);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            String current = "";
            InputStream ir = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(ir);
            int data = inputStreamReader.read();
            while (data != -1) {
                current += (char) data;
                data = inputStreamReader.read();
                System.out.print(current);
            }
            return current;
        }
        catch (Exception e)
        {
            Log.d("Exception","Some Exceptions"+e);
        }
        finally {
            if(httpURLConnection!=null)
            {
                httpURLConnection.disconnect();
            }
        }
        return null;
    }

}


