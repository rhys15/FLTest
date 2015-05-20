package test.freelancer.com.fltest.interfaces;

/**
 * Created by Android 17 on 5/20/2015.
 */
public interface JSONListener {
    public void onDataFetch(String jsonString);
    public void onNetworkError(String errorMessage);
    public void onUnknownError(String errorMessage);
}
