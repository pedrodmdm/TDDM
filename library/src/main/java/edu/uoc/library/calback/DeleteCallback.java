package edu.uoc.library.calback;

/**
 * Created by SGAR810 on 24/09/2016.
 */
public interface DeleteCallback {
    void onSuccess();
    void onFailure(Throwable e);
}
