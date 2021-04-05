package com.example.conrep.ui.util;

/**
 * This generic interface is used as custom callback for async tasks.
 * For an example usage see .
 */
public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
