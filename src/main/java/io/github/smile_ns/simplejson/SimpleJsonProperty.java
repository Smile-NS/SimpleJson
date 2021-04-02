package io.github.smile_ns.simplejson;

import java.io.File;

/**
 * Class for setting properties of an object.
 * This class will be extended by SimpleJson.
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
     * Specify a file to save.
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
     * Set an symbol of hierarchy.
     * The default value is "\\.".
     * @param separator symbol of hierarchy
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * Set whether to object handles deep nodes.
     * If set true, you will be able to specify the hierarchy with a symbol.
     * The default value is true.
     * @param deep True if you want to be able to specify the hierarchy with a symbol.
     */
    public void setDeep(boolean deep) {
        this.deep = deep;
    }

    /**
     * Set whether to when try to find a node that doesn't exist, create an empty node.
     * The default value is true.
     * @param autoCreateNode True if you want to create an empty node.
     */
    public void setAutoCreateNode(boolean autoCreateNode) {
        this.autoCreateNode = autoCreateNode;
    }
}
