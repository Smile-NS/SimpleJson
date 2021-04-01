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

public class SimpleJson extends SimpleJsonProperty {

    private final ObjectNode root;
    private final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public SimpleJson() {
        root = mapper.createObjectNode();
    }

    public SimpleJson(File file) throws IOException {
        super(file);
        if (!file.exists()) mapper.writeValue(file, mapper.createObjectNode());

        JsonNode node = mapper.readTree(file);
        root = node.deepCopy();
    }

    public SimpleJson(Object obj) {
        JsonNode node = mapper.convertValue(obj, JsonNode.class);
        root = node.deepCopy();
    }

    public SimpleJson(String content) throws IOException {
        JsonNode node = mapper.readTree(content);
        root = node.deepCopy();
    }

    public SimpleJson(JsonNode node) {
        root = node.deepCopy();
    }

    public void save() throws IOException{
        mapper.writeValue(file, root);
    }
    
    public Set<String> getKeySet(String fieldPath) {
        Map<String, Object> map = getMap(fieldPath);
        return map.keySet();
    }

    public SimpleJson getJsonObject(String fieldPath) {
        JsonNode node = getNode(fieldPath);
        return new SimpleJson(node);
    }

    public JsonNode getNode(String fieldPath) {
        String[] path = fieldPath.split("\\.");
        JsonNode node = root.deepCopy();

        if (!deepSearch) return node;
        for (String key : path) {
            if (node == null) return null;
            node = node.get(key);
        }
        return node;
    }

    public String getString(String fieldPath){
        return getNode(fieldPath).asText();
    }

    public boolean getBoolean(String fieldPath){
        return getNode(fieldPath).asBoolean();
    }

    public double getDouble(String fieldPath){
        return getNode(fieldPath).asDouble();
    }

    public int getInt(String fieldPath){
        return getNode(fieldPath).asInt();
    }

    public long getLong(String fieldPath){
        return getNode(fieldPath).asLong();
    }

    public Map<String, Object> getMap(String fieldPath) {
        JsonNode node = getNode(fieldPath);
        return mapper.convertValue(node, new TypeReference<Map<String, Object>>() {});
    }

    public List<Object> getList(String fieldPath) {
        JsonNode node = getNode(fieldPath);
        return mapper.convertValue(node, new TypeReference<List<Object>>() {});
    }

    public <T> T getJavaObject(String fieldName, Class<T> c) {
        return mapper.convertValue(getNode(fieldName), c);
    }

    public boolean containsNode(String fieldPath){
        JsonNode node = getNode(fieldPath);
        return node != null;
    }

    public boolean isEmpty(){
        return root.isEmpty(null);
    }

    public int size(){
        return root.size();
    }

    private void deepPutPrim(String fieldPath, Object v) {
        String[] path = fieldPath.split("\\.");
        JsonNode deepNode = getNode(getFieldPath(path.length - 1, path));
        ObjectNode deepTable = deepNode == null ? mapper.createObjectNode() : deepNode.deepCopy();
        deepTable.putPOJO(path[path.length - 1], v);

        putOutside(deepTable.deepCopy(), path);
    }

    private void deepPutRef(String fieldPath, JsonNode v) {
        String[] path = fieldPath.split("\\.");
        JsonNode deepNode = getNode(getFieldPath(path.length - 1, path));
        ObjectNode deepTable = deepNode == null ? mapper.createObjectNode() : deepNode.deepCopy();
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

    public void remove(String fieldPath) {
        if (!fieldPath.contains(".")) {
            root.remove(fieldPath);
            return;
        }
        if (deepSearch) deepRemove(fieldPath);
    }

    private void deepRemove(String fieldPath) {
        String[] path = fieldPath.split("\\.");
        JsonNode node = getNode(getFieldPath(path.length - 1, path));
        ObjectNode table = node.deepCopy();

        table.remove(Arrays.asList(path));
        putNode(getFieldPath(path.length - 1, path), table);
    }

    public void clear() {
        root.removeAll();
    }

    public void clear(String fieldPath) {
        String[] path = fieldPath.split("\\.");
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

    private void putNode(String fieldPath, JsonNode v) {
        if (!fieldPath.contains(".")) {
            root.set(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutRef(fieldPath, v);
    }

    private void putRef(String fieldPath, Object v) {
        JsonNode node = mapper.valueToTree(v);
        putNode(fieldPath, node);
    }

    private String getFieldPath(int index, String[] path) {
        StringBuilder fieldPath = new StringBuilder();
        for (int j = 0;j < index;j++) {
            fieldPath.append(path[j]);
            if (j != index - 1) fieldPath.append(".");
        }
        return fieldPath.toString();
    }

    public void put(String fieldPath, Integer v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, int v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, Long v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, long v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, Float v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, float v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, Short v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, short v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, byte[] v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, Double v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, double v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, String v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, Boolean v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, boolean v) {
        if (!fieldPath.contains(".")) {
            root.put(fieldPath, v);
            return;
        }
        if (deepSearch) deepPutPrim(fieldPath, v);
    }

    public void put(String fieldPath, SimpleJson v) {
        putNode(fieldPath, v.toJsonNode());
    }

    public void put(String fieldPath, Object v) {
        putRef(fieldPath, v);
    }

    public <T> void put(String fieldPath, Map<String, T> v) {
        putRef(fieldPath, v);
    }

    public <T> void put(String fieldPath, List<T> v) {
        putRef(fieldPath, v);
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(root);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JsonNode toJsonNode() {
        return root.deepCopy();
    }

    public <T> T toJavaObject(Class<T> c) throws IOException {
        return mapper.readValue(toString(), c);
    }

    public Map<String, Object> toMap(){
        return mapper.convertValue(root, new TypeReference<Map<String, Object>>() {});
    }
}