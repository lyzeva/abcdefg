package com.oneapm.research.granger;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by ruan on 16-6-15.
 */
public class MyFilenameFilter implements FilenameFilter {
    private String type;

    public MyFilenameFilter(String regex) {
        this.type = regex;
    }

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(type);
    }
}
