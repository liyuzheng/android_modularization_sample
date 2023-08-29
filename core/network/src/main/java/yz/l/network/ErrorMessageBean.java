package yz.l.network;


import androidx.annotation.Keep;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

@Keep
public class ErrorMessageBean {

    @Keep
    @SerializedName("error")
    public ErrorBean error;

    @Keep
    public static class ErrorBean {

        @SerializedName("message")
        @Nullable
        public String message = "";

        @SerializedName("code")
        public int code;
    }
}
