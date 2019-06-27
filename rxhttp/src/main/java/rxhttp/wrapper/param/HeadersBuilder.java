package rxhttp.wrapper.param;

import okhttp3.Headers;

/**
 * User: ljx
 * Date: 2019/1/22
 * Time: 13:58
 */
public interface HeadersBuilder {

    Headers getHeaders();

    String getHeader(String key);

    Headers.Builder getHeadersBuilder();

    Param setHeadersBuilder(Headers.Builder builder);

    Param addHeader(String key, String value);

    Param addHeader(String line);

    Param setHeader(String key, String value);

    Param removeAllHeader(String key);

    /**
     * 设置断点下载开始位置，结束位置默认为文件末尾
     *
     * @param startIndex 开始位置
     * @return Param
     */
    default Param setRangeHeader(long startIndex) {
        return setRangeHeader(startIndex, -1);
    }

    /**
     * 设置断点下载范围
     * 注：
     * 1、开始位置小于0，及代表下载完整文件
     * 2、结束位置要大于开始位置，否则结束位置默认为文件末尾
     *
     * @param startIndex 开始位置
     * @param endIndex   结束位置
     * @return Param
     */
    default Param setRangeHeader(long startIndex, long endIndex) {
        if (endIndex < startIndex) endIndex = -1;
        String headerValue = "bytes=" + startIndex + "-";
        if (endIndex >= 0) {
            headerValue = headerValue + endIndex;
        }
        return addHeader("RANGE", headerValue);
    }
}
