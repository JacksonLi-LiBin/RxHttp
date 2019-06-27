package rxhttp.wrapper.exception;


import java.io.IOException;

import io.reactivex.annotations.NonNull;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 异常处理帮助类
 * User: ljx
 * Date: 2018/11/21
 * Time: 09:30
 */
public class ExceptionHelper {

    /**
     * 根据Http执行结果过滤异常
     *
     * @param response Http响应体
     * @throws IOException 请求失败异常、网络不可用异常
     */
    public static ResponseBody throwIfFatal(@NonNull Response response) throws IOException {
        ResponseBody body = response.body();
        if (body == null || !response.isSuccessful())
            throw new HttpStatusCodeException(response);
        return body;
    }
}
