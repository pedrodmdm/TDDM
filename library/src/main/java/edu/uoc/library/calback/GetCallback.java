package edu.uoc.library.calback;

/**
 * Created by SGAR810 on 24/09/2016.
 */
public interface GetCallback<T> {
    void onSuccess(T monument);
    void onFailure(Throwable e);
}
