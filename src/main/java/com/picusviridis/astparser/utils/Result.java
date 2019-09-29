package com.picusviridis.astparser.utils;

public class Result<T>
{
    private boolean success;

    private T       value;

    public Result(Boolean success, T value)
    {
        this.success = success;
        this.value = value;
    }

    public boolean isSuccess()
    {
        return this.success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public T getValue()
    {
        return this.value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }
}
