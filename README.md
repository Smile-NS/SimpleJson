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
But also look at Javadoc because not all written here. 
### Create an object. 
```java
SimpleJson json = new SimpleJson(); // An empty object.
```
```java
// An object with the specified file to save.  
// If already the file exists, create one by referencing the file.
SimpleJson json = new SimpleJson(file);
```
```java
SimpleJson json = new SimpleJson(new Foo()); // An object with another instance.
```
```java
SimpleJson json = new SimpleJson("{\"smile\":\"noob\"}"); // An object with a string.
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
Like "put", you can remove deep nodes.
```java
json.remove(key, value);
```
### Get
Get a selected node as (pseudo) primitive type, their wrapper classes, or reference type.
You can get from a deep node, too.
```java
json.getInt("key");
json.getList("key2");
```
### Conversion
Convert and get an object.
<dl>
  <dt>SimpleJson#toString()</dt>
  <dd>Convert an object to String</dd>
  <dt>SimpleJson#toJsonNode()</dt>
  <dd>Convert an object to JsonNode</dd>
  <dt>SimpleJson#toJavaObject()</dt>
  <dd>Convert an object to an instance of the specified class</dd>
  <dt>SimpleJson#toMap()</dt>
  <dd>Convert an object to Map</dd>
</dl>

### Save
Save in a file.
If doesn't specify a file to save from a constructor or SimpleJsonProperty#setFile(file), you can't save.
```java
json.save();
```
