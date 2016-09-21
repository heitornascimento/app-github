package cl.com.br.github.endpoint;

import java.util.List;

import cl.com.br.github.model.PullRequest;
import cl.com.br.github.model.RepositoryResponse;
import cl.com.br.github.model.PullRequestResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by heitornascimento on 8/16/16.
 */
public interface GithubService {

    @GET("search/repositories")
    Call<RepositoryResponse> getRepositories(@Query("q") String language, @Query("sort")
    String sort, @Query("page") int page);


    @GET("repos/{owner}/{repo_name}/pulls")
    Call<List<PullRequest>> getPullRequest(@Path("owner") String owner, @Path("repo_name")
    String repoName);

}
