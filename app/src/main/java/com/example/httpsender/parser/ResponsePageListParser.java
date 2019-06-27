package com.example.httpsender.parser;



import com.example.httpsender.entity.Response;
import com.example.httpsender.entity.PageList;

import java.io.IOException;
import java.lang.reflect.Type;

import rxhttp.wrapper.annotation.Parser;
import rxhttp.wrapper.entity.ParameterizedTypeImpl;
import rxhttp.wrapper.exception.ParseException;
import rxhttp.wrapper.parse.AbstractParser;
import rxhttp.wrapper.utils.GsonUtil;

/**
 * Data<PageList<T>> 数据解析器,解析完成对Data对象做判断,如果ok,返回数据 PageList<T>
 * User: ljx
 * Date: 2018/10/23
 * Time: 13:49
 */
@Parser(name = "ResponsePageList")
public class ResponsePageListParser<T> extends AbstractParser<PageList<T>> {

    protected ResponsePageListParser() {
        super();
    }

    private ResponsePageListParser(Type type) {
        super(type);
    }

    public static <T> ResponsePageListParser<T> get(Class<T> type) {
        return new ResponsePageListParser<>(type);
    }

    @Override
    public PageList<T> onParse(okhttp3.Response response) throws IOException {
        String content = getResult(response); //从Response中取出Http执行结果
        final Type type = ParameterizedTypeImpl.get(Response.class, PageList.class, mType); //获取泛型类型
        Response<PageList<T>> data = GsonUtil.getObject(content, type);
        if (data == null) //为空 ，表明数据不正确
            throw new ParseException("data parse fail");
        //跟服务端协议好，code等于100，才代表数据正确,否则，抛出异常
        if (data.getCode() != 100) {
            throw new ParseException(String.valueOf(data.getCode()), data.getMsg());
        }
        PageList<T> t = data.getData(); //获取data字段
        if (t == null) {  //为空，有可能是参数错误或者其他原因，导致服务器不能正确给我们data字段数据
            throw new ParseException(String.valueOf(data.getCode()), data.getMsg());
        }
        return t;
    }
}
