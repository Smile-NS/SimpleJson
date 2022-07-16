package io.github.smile_ns.simplejson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * SimpleJson
 * @author Smile_NS
 * @version 1.0.0
 */
public class SimpleJson extends SimpleJsonProperty {

    private final ObjectNode root;
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    /**
     * Create an empty object.
     */
    public SimpleJson() {
        root = mapper.createObjectNode();
    }

    /**
     * Create an object from the json file.
     * If the file already exists, SimpleJson creates a new file by the path.
     * @param file file to save
     * @throws IOException if a low-level I/O problem (unexpected end-of-input, network error)
     * occurs (passed through as-is without additional wrapping -- note that this is one case where.
     * (from Jackson's Javadoc)
     */
    public SimpleJson(File file) throws IOException {
        super(file);
        if (!file.exists()) mapper.writeValue(file, mapper.createObjectNode());

        JsonNode node = mapper.readTree(file);
        root = node.deepCopy();
    }

    /**
     * Create an object from an instance.
     * @param obj referenced instance
     */
    public SimpleJson(Object obj) {
        JsonNode node = mapper.convertValue(obj, JsonNode.class);
        root = node.deepCopy();
    }

    /**
     * Crate an object from the json text.
     * @param content referenced instance.
     * @throws IOException if a low-level I/O problem (unexpected end-of-input, network error)
     * occurs (passed through as-is without additional wrapping -- note that this is one case where.
     * (from Jackson's Javadoc)
     */
    public SimpleJson(String content) throws IOException {
        JsonNode node = mapper.readTree(content);
        root = node.deepCopy();
    }

    /**
     * Create an object from JsonNode.
     * @param node referenced JsonNode
     */
    public SimpleJson(JsonNode node) {
        root = node.deepCopy();
    }

    /**
     * Write JSON to the file.
     * @throws IOException When fail to write.
     */
    public void save() throws IOException{
        mapper.writeValue(file, root);
    }

    /**
     * Get all of keys contained in the node.
     * @param fieldPath node path
     * @return all of keys contained in the node
     */
    public Set<String> getKeySet(String fieldPath) {
        Map<String, Object> map = getMap(fieldPath);
        return map.keySet();
    }

    /**
     * Get the node as JsonNode.
     * @param fieldPath node path
     * @return node as JsonNode
     */
    public JsonNode getNode(String fieldPath) {
        JsonNode node = root.deepCopy();
        if (!deep) return node.get(fieldPath);

        String[] path = fieldPath.split(separator);
        for (String key : path) {
            if (node == null) return null;
            node = node.get(key);
        }
        return node;
    }

    /**
     * Get the node as SimpleJson.
     * @param fieldPath node path
     * @return node as SimpleJson
     */
    public SimpleJson getJsonObject(String fieldPath) {
        JsonNode node = getNode(fieldPath);
        return new SimpleJson(node);
    }

    /**
     * Get the node as String.
     * @param fieldPath node path
     * @return node as String
     */
    public String getString(String fieldPath){
        return getNode(fieldPath).asText();
    }

    /**
     * Get the node as boolean.
     * @param fieldPath node path
     * @return node as Boolean
     */
    public boolean getBoolean(String fieldPath){
        return getNode(fieldPath).asBoolean();
    }

    /**
     * Get the node as double.
     * @param fieldPath node path
     * @return node as double
     */
    public double getDouble(String fieldPath){
        return getNode(fieldPath).asDouble();
    }

    /**
     * Get the node as int.
     * @param fieldPath node path
     * @return node as int
     */
    public int getInt(String fieldPath){
        return getNode(fieldPath).asInt();
    }

    /**
     * Get the node as long.
     * @param fieldPath node path
     * @return node as long
     */
    public long getLong(String fieldPath){
        return getNode(fieldPath).asLong();
    }

    /**
     * Get the node as Map.
     * @param fieldPath node path
     * @return node as Map
     */
    public Map<String, Object> getMap(String fieldPath) {
        JsonNode node = getNode(fieldPath);
        return mapper.convertValue(node, new TypeReference<Map<String, Object>>() {});
    }

    /**
     * Get the node as List.
     * @param fieldPath node path
     * @return node as List
     */
    public List<Object> getList(String fieldPath) {
        JsonNode node = getNode(fieldPath);
        return mapper.convertValue(node, new TypeReference<List<Object>>() {});
    }

    /**
     * Get the node as an instance of the set class.
     * @param fieldPath node path
     * @param c class type
     * @return node as an instance of the set class
     */
    public <T> T getJavaObject(String fieldPath, Class<T> c) {
        return mapper.convertValue(getNode(fieldPath), c);
    }

    /**
     * Check whether the SimpleJson object contains the node.
     * @param fieldPath node path
     * @return True if the SimpleJson object contains the node.
     */
    public boolean containsNode(String fieldPath){
        JsonNode node = getNode(fieldPath);
        return node != null;
    }

    /**
     * Check whether the object is empty.
     * @return True if the object is empty.
     */
    public boolean isEmpty(){
        return root.isEmpty(null);
    }

    /**
     * Get an amount of the shallowest nodes.
     * @return node amount
     */
    public int size(){
        return root.size();
    }

    /**
     * Remove the node.
     * @param fieldPath node path
     */
    public void remove(String fieldPath) {
        if (!fieldPath.contains(".")) {
            root.remove(fieldPath);
            return;
        }
        if (deep) deepRemove(fieldPath);
    }

    /**
     * Clear the contents in the object.
     */
    public void clear() {
        root.removeAll();
    }

    /**
     * Clear the contents in the node.
     * @param fieldPath node path
     */
    public void clear(String fieldPath) {
        String[] path = fieldPath.split(separator);
        Set<String> keySet = getKeySet(fieldPath);
        JsonNode node = getNode(getFieldPath(path.length, path));
        ObjectNode table = node.deepCopy();

        for (String key : keySet) {
            List<String> list = new ArrayList<>(Arrays.asList(path));
            list.add(key);
            table.remove(list);
        }
        putNode(getFieldPath(path.length, path), table);
    }

    /**
     * Put an Integer element.
     * @param fieldPath node path
     * @param v Integer element
     */
    public void put(String fieldPath, Integer v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put an int element.
     * @param fieldPath node path
     * @param v int element
     */
    public void put(String fieldPath, int v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a Long element.
     * @param fieldPath node path
     * @param v Long element
     */
    public void put(String fieldPath, Long v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a long element.
     * @param fieldPath node path
     * @param v long element
     */
    public void put(String fieldPath, long v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a Float element.
     * @param fieldPath node path
     * @param v Float element
     */
    public void put(String fieldPath, Float v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a float element.
     * @param fieldPath node path
     * @param v float element
     */
    public void put(String fieldPath, float v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a Short element.
     * @param fieldPath node path
     * @param v Short element
     */
    public void put(String fieldPath, Short v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a short element.
     * @param fieldPath node path
     * @param v short element
     */
    public void put(String fieldPath, short v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a byte array element.
     * @param fieldPath node path
     * @param v byte array element
     */
    public void put(String fieldPath, byte[] v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a Double element.
     * @param fieldPath node path
     * @param v Double element
     */
    public void put(String fieldPath, Double v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a double element.
     * @param fieldPath node path
     * @param v double element
     */
    public void put(String fieldPath, double v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a String element.
     * @param fieldPath node path
     * @param v String element
     */
    public void put(String fieldPath, String v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a Boolean element.
     * @param fieldPath node path
     * @param v Boolean element
     */
    public void put(String fieldPath, Boolean v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a boolean element.
     * @param fieldPath node path
     * @param v boolean element
     */
    public void put(String fieldPath, boolean v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deep) deepPutPrim(fieldPath, v);
    }

    /**
     * Put a SimpleJson element.
     * @param fieldPath node path
     * @param v SimpleJson element
     */
    public void put(String fieldPath, SimpleJson v) {
        putNode(fieldPath, v.toJsonNode());
    }

    /**
     * Put a instance.
     * @param fieldPath node path
     * @param v instance
     */
    public void put(String fieldPath, Object v) {
        putRef(fieldPath, v);
    }

    /**
     * Put a Map.
     * @param fieldPath node path
     * @param v Map
     */
    public <T> void put(String fieldPath, Map<String, T> v) {
        putRef(fieldPath, v);
    }

    /**
     * Put a List.
     * @param fieldPath node path
     * @param v List
     * @param <T> Type
     */
    public <T> void put(String fieldPath, List<T> v) {
        putRef(fieldPath, v);
    }

    /**
     * Convert the contents in the object into a json text.
     * @return the contents converted into a json text.
     */
    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert the contents in the object into JsonNode.
     * @return the contents converted into JsonNode.
     */
    public JsonNode toJsonNode() {
        return root.deepCopy();
    }

    /**
     * Convert the contents in the object into an instance of the set class.
     * @return the contents converted into an instance of the set class.
     */
    public <T> T toJavaObject(Class<T> c) throws IOException {
        return mapper.readValue(toString(), c);
    }

    /**
     * Convert the contents in the object into Map.
     * @return the contents converted into Map.
     */
    public Map<String, Object> toMap(){
        return mapper.convertValue(root, new TypeReference<Map<String, Object>>() {});
    }


    private void deepRemove(String fieldPath) {
        String[] path = fieldPath.split(separator);
        JsonNode node = getNode(getFieldPath(path.length - 1, path));
        ObjectNode table = node.deepCopy();

        table.remove(Arrays.asList(path));
        putNode(getFieldPath(path.length - 1, path), table);
    }

    private void deepPutPrim(String fieldPath, Object v) {
        String[] path = fieldPath.split(separator);
        JsonNode deepNode = getNode(getFieldPath(path.length - 1, path));

        ObjectNode deepTable;
        if (autoCreateNode) deepTable = deepNode == null ? mapper.createObjectNode() : deepNode.deepCopy();
        else deepTable = deepNode.deepCopy();

        deepTable.putPOJO(path[path.length - 1], v);
        putOutside(deepTable.deepCopy(), path);
    }

    private void deepPutRef(String fieldPath, JsonNode v) {
        String[] path = fieldPath.split(separator);
        JsonNode deepNode = getNode(getFieldPath(path.length - 1, path));

        ObjectNode deepTable;
        if (autoCreateNode) deepTable = deepNode == null ? mapper.createObjectNode() : deepNode.deepCopy();
        else deepTable = deepNode.deepCopy();

        deepTable.set(path[path.length - 1], v);
        putOutside(deepTable.deepCopy(), path);
    }

    private void putOutside(JsonNode lastNode, String[] path) {
        for (int i = path.length - 2;i > 0;i--) {
            String key = getFieldPath(i, path);
            JsonNode node = getNode(key);
            ObjectNode table = node == null ? mapper.createObjectNode() : node.deepCopy();

            table.set(path[i], lastNode);
            lastNode = table.deepCopy();
        }
        root.set(path[0], lastNode);
    }

    private void putNode(String fieldPath, JsonNode v) {
        if (!fieldPath.contains(".")) {
            root.set(fieldPath, v);
            return;
        }
        if (deep) deepPutRef(fieldPath, v);
    }

    private void putRef(String fieldPath, Object v) {
        JsonNode node = mapper.valueToTree(v);
        putNode(fieldPath, node);
    }

    private static String getFieldPath(int index, String[] path) {
        StringBuilder fieldPath = new StringBuilder();
        for (int j = 0;j < index;j++) {
            fieldPath.append(path[j]);
            if (j != index - 1) fieldPath.append(".");
        }
        return fieldPath.toString();
    }
}
