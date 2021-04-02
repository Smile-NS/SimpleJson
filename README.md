# SimpleJson
# What is this?
SimpleJson is a library for easily writing or reading JSON.  
You can use like Map.  
You can also make handle to deep nodes with a symbol.  
The library was made using [Jackson](https://github.com/FasterXML/jackson).
# Download
* ver1.0.0:  
# Dependencies 
#### maven
```xml
<!-- https://github.com/Smile-NS/SimpleJson -->
<dependency>
  <groupId>io.github.smile-ns.simplejson</groupId>
  <artifactId>SimpleJson</artifactId>
  <version>1.0.0</version>
</dependency>
```
#### gradle
```gradle
// https://github.com/Smile-NS/SimpleJson
implementation 'io.github.smile-ns.simplejson:SimpleJson:1.0.0'
```
# Javadoc
Javadoc
# How to use?
These are ways to use and example codes. 
### Create an object.
An empty object. 
```java
SimpleJson json = new SimpleJson();
```
An object with the specified file to save.  
If already the file exists, create one by referencing the file.
```java
SimpleJson json = new SimpleJson(file);
```
An object with another instance.
```java
SimpleJson json = new SimpleJson(new Foo());
```
An object with a string.
```java
SimpleJson json = new SimpleJson("{\"smile\":\"noob\"}");
```
An object with JsonNode.
```java
SimpleJson json = new SimpleJson(JsonNode);
```
### Put
Put a (pseudo) primitive type element.
```java
SimpleJson json = new SimpleJson();
json.put("id", 10);
json.put("name", "test");
...
```
```json
{
  "id": 10,
  "name": "test"
}
```
In this way, you can also put a value for a deep node.  
Separate with '.'
```java
SimpleJson json = new SimpleJson();
json.put("smile.iq", 80);
```
```json
{
  "smile": {
    "iq": 80
  }
}
```
Put a reference type element.
* Map and List
```java
SimpleJson json = new SimpleJson();

Map<String, Integer> map = new HasMap<>();
map.put("key", 3);
json.put("map", map);

List<String> list = new ArrayList<>();
list.add("smile");
list.add("angry");
json.put("list", list);
```
```json
{
  "map": {
    "key": 3
  },
  "list": [ "smile", "angry" ]
}
```
* instance
```java
class Foo {
    int id = 1;
    String name = "test";
}

SimpleJson json = new SimpleJson();
json.put("Foo", new Foo());
```
```json
{
  "Foo": {
    "id": 1,
    "name": "test"
  }
}
```
When put a reference type element, using SimpleJson#put(String, SimpleJson) is the fastest.
```java
SimpleJson json = new SimpleJson();
SimpleJson json2 = new SimpleJson();
json2.put("key", 10);
json.put("json2", json2);
```
```json
{
  "json2": {
    "key": 10
  }
}
```
### Remove
Remove a selected node.  
Like "put", you can remove a deep node.
```java
json.remove(key, value);
```
