package cl.com.br.github.endpoint;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class Injector {

    public static Retrofit provideRetrofit(String baseUrl){
        return new Retrofit.Builder().baseUrl(baseUrl).
                addConverterFactory(GsonConverterFactory.create()).build();

    }

    public static GithubService provideGithubService(){
        return provideRetrofit(Constants.baseUrl).create(GithubService.class);
    }
}
