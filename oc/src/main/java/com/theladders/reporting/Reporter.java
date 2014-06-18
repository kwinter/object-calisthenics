package com.theladders.reporting;


public interface Reporter<T>
{
  void report(T target);
}
