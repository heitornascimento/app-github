package cl.com.br.github.endpoint;

/**
 * Created by heitornascimento on 8/16/16.
 */
public class Constants {

    public static final String baseUrl =
            "https://api.github.com/";


    public static final String ACTION_REPOSITORIES = "action_repository";
    public static final String ACTION_PULL_REQUESTS = "action_pull_requests";

    public static String SUCCESS_ENDPOINT = "success";
    public static String ERROR_ENDPOINT = "error";
    public static int RESULT_OK = 0;
    public static int RESULT_ERROR = 1;
}
