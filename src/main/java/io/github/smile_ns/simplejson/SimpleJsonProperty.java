package io.github.smile_ns.simplejson;

import java.io.File;

/**
 * Class to set properties of a SimpleJson object.
 * This class is extended by SimpleJson.
 */
public class SimpleJsonProperty {

    File file;

    String separator = "\\.";

    boolean deep = true;

    boolean autoCreateNode = true;

    SimpleJsonProperty() {
    }

    SimpleJsonProperty(File file) {
        setFile(file);
    }

    /**
     * Set a file to save.
     * @param file file to save.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Get the file to save.
     * @return file to save
     */
    public File getFile() {
        return file;
    }

    /**
     * Set an symbol as a delimiter character.
     * The default value is "\\.".
     * @param separator symbol as a delimiter character
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Set whether the object handles deep nodes.
     * If you set true, you will be able to specify the hierarchy with a symbol.
     * The default value is true.
     * @param deep True if you want to be able to specify the hierarchy with a symbol.
     */
    public void setDeep(boolean deep) {
        this.deep = deep;
    }

    /**
     * Set whether SimpleJson creates an empty node when SimpleJson tries to find the node that doesn't exist.
     * The default value is true.
     * @param autoCreateNode True if you want SimpleJson to create an empty node.
     */
    public void setAutoCreateNode(boolean autoCreateNode) {
        this.autoCreateNode = autoCreateNode;
    }
}
