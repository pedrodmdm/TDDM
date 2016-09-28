package edu.uoc.library.calback;

import edu.uoc.library.model.Monument;

/**
 * Created by SGAR810 on 24/09/2016.
 */
public interface SaveCallback {
    void onSuccess(Monument monument);
    void onFailure(Throwable e);
}
