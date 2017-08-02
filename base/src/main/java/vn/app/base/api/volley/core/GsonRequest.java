package vn.app.base.api.volley.core;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.nio.charset.Charset;
import java.util.Map;

import vn.app.base.util.DebugLog;


public class GsonRequest<T> extends BaseTypeRequest<T> {

    protected static Gson gson = getGson();

    public static Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).serializeNulls().create();
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener, clazz, listener, headers, params);
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

//            String json = new String(
//                    response.data,
//                    HttpHeaderParser.parseCharset(response.headers));

            String json = new String(
                    response.data,
                    Charset.forName("UTF-8"));

            if (!isCanceled()) {
                DebugLog.i("url: " + getUrl() + "\njson: " + json);
            }

            Response<T> success = Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));

            this.responseHeader = response.headers;

            if (gsonRequestHeaderOnResult != null) {
                gsonRequestHeaderOnResult.onGsonRequestHeaderResult(responseHeader);
            }

            return success;
//        } catch (UnsupportedEncodingException e) {
//            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

}
