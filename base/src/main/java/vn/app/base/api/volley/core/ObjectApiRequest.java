package vn.app.base.api.volley.core;

import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import vn.app.base.BaseApplication;
import vn.app.base.api.response.BaseResponse;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.api.volley.event.ApiEvent;
import vn.app.base.api.volley.event.ApiEventType;
import vn.app.base.constant.ApiParam;
import vn.app.base.util.DebugLog;
import vn.app.base.util.NetworkUtils;
import vn.app.base.util.SharedPrefUtils;

public abstract class ObjectApiRequest<T extends BaseResponse> extends BaseApiRequest<T> {

    public static final int ERROR_CODE = 999;

    String tag = null;

    private boolean suppressCommonApiError = false;

    public void setSuppressCommonApiError(boolean suppressCommonApiError) {
        this.suppressCommonApiError = suppressCommonApiError;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDontCancel() {
        this.tag = NetworkUtils.DONT_CANCEL;
    }

    public void setIsLoadMore(boolean isLoadMore) {
        if (isLoadMore) {
            this.tag = NetworkUtils.LOAD_MORE;
        }
    }

    abstract public boolean isRequiredAuthorization();

    protected Map<String, String> handleRequestHeaders() {
        Map<String, String> requestHeaders = getRequestHeaders();
        if (isRequiredAuthorization()) {
            if (requestHeaders == null) {
                requestHeaders = new HashMap<>();
            }
            requestHeaders.put(ApiParam.AUTHORIZATION, SharedPrefUtils.getAccessToken());
        }
        if (requestHeaders != null) {
            DebugLog.i(requestHeaders.toString());
        }
        return requestHeaders;
    }

    @Override
    protected void excecuteRequest() {
        super.excecuteRequest();
        if ((getMethod() == Request.Method.GET || getMethod() == Request.Method.DELETE) && getRequestParams() != null) {
            baseTypeRequest = new GsonRequest<>(getMethod(), createRequestUrl(), getResponseClass(), handleRequestHeaders(), new HashMap<String, String>(), getListener(), getErrorListener());
        } else {
            baseTypeRequest = new GsonRequest<>(getMethod(), createRequestUrl(), getResponseClass(), handleRequestHeaders(), handleRequestParams(), getListener(), getErrorListener());
        }
        baseTypeRequest.setGsonRequestHeaderOnResult(this);
        baseTypeRequest.setRetryPolicy(getDefaultRetryPolicy());
        if (tag != null) {
            baseTypeRequest.setTag(this.tag);
        }
        NetworkUtils.getInstance(BaseApplication.getInstance()).addToRequestQueue(baseTypeRequest);
    }

    public ApiObjectCallBack<T> requestCallBack;

    public void setRequestCallBack(ApiObjectCallBack<T> requestCallBack) {
        this.requestCallBack = requestCallBack;
    }

    public void handleResponse(T response) {
        if (response.status != 1) {
            if (!suppressCommonApiError()) {
                EventBus.getDefault().post(new ApiEvent(ApiEventType.SHOW_API_ERROR_DIALOG, getErrorDialogTitle(), response.message));
            }
            if (requestCallBack != null) {
                requestCallBack.onFail(response.status, response.message);
            }
        } else {
            if (requestCallBack != null) {
                requestCallBack.onSuccess(response);
            }
        }
    }

    @Override
    public void onRequestSuccess(T response) {
        handleResponse(response);
    }

    @Override
    public void onRequestError(VolleyError error) {
        if (requestCallBack != null) {
            int errorCode = ERROR_CODE;
            if (error.networkResponse != null) {
                errorCode = error.networkResponse.statusCode;
            }
            requestCallBack.onFail(errorCode, error.getMessage());
        }
    }

    public String getErrorDialogTitle() {
        return null;
    }

    public boolean suppressCommonApiError() {
        return suppressCommonApiError;
    }

    @Override
    protected boolean isHideConnectionErrorDialog() {
        return suppressCommonApiError() || super.isHideConnectionErrorDialog();
    }

    @Override
    protected boolean isHideApiErrorDialog() {
        return suppressCommonApiError() || super.isHideApiErrorDialog();
    }
}
