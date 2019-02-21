package com.zkys.operationtool.converter;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by DGDL-08 on ${DATA}
 */
public class MGsonConverterFactory extends Converter.Factory {

    private Gson gson;
    private final static String TOKEN = "FBC4240212B6435181ACA6A9F409BB7A";

    public static MGsonConverterFactory create() {
        return new MGsonConverterFactory();
    }


    private MGsonConverterFactory() {
        gson = new Gson();
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new RequestBodyConverter<>(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new ResponseBodyConverter<>(type);
    }

    /**
     * 自定义请求RequestBody
     */
    public static class RequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/wxt;charset=UTF-8");
        private static final MediaType MEDIA_TYPE1 = MediaType.parse("application/json; charset=UTF-8");
        private Gson gson;


        public RequestBodyConverter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public RequestBody convert(T value) throws IOException {//T就是传入的参数
//            System.out.println(gson.toJson(value).toString());

            return RequestBody.create(MEDIA_TYPE1, gson.toJson(gson.toJson(value)));
        }

        /**
         * {"InMember":"{\"fMobile\":\"16670118179\"}","Sign":"FBC4240212B6435181ACA6A9F409BB7A"}
         */

    }


    /**
     * 自定义响应ResponseBody
     */
    public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Type type;

        public ResponseBodyConverter(Type type) {
            this.type = type;
        }

        /**
         * @param value
         * @return T
         * @throws IOException
         */
        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                String json = value.string();
                return new Gson().fromJson(json, type);
            } finally {
                value.close();
            }
        }

    }

}
