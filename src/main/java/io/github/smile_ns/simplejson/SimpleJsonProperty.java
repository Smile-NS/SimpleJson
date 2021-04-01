package io.github.smile_ns.simplejson;

import java.io.File;

public class SimpleJsonProperty {

    protected File file;

    protected boolean deepSearch = true;

    protected SimpleJsonProperty() {
    }

    protected SimpleJsonProperty(File file) {
        setFile(file);
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setDeepSearch(boolean deepSearch) {
        this.deepSearch = deepSearch;
    }

    public boolean isDeepSearch() {
        return deepSearch;
    }
}
