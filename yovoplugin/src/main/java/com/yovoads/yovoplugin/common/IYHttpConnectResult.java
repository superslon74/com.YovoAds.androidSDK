package com.yovoads.yovoplugin.common;

public interface IYHttpConnectResult {

    public void ResultError(String _error);
    public void ResultError(EWwwCommand _wwwCommand, String _error);
    public void ResultOk(EWwwCommand _wwwCommand, String _json);
}
