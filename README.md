# SimpleJson
# What is this?
SimpleJson is a library for writing or reading JSON easily.  
You can use as similar to Map.  
This library was made using [Jackson](https://github.com/FasterXML/jackson).
# Download
* ver1.0.0:  [SimpleJson-1.0.0.jar](https://github.com/Smile-NS/SimpleJson/raw/master/target/SimpleJson-1.0.0.jar)
#### maven
```xml
<repositories>
    ...
    <repository>
        <id>github</id>
        <name>SimpleJson</name>
        <url>https://raw.githubusercontent.com/Smile-NS/SimpleJson/mvn-repo/</url>
    </repository>
    ...
</repositories>

<dependencies>
    ...
    <dependency>
        <groupId>io.github.smile-ns.simplejson</groupId>
        <artifactId>SimpleJson</artifactId>
        <version>1.0.0</version>
    </dependency>
    ...
</dependencies>
```
# Javadoc
[Javadoc](https://smile-ns.github.io/SimpleJson/1.0.0/javadoc)
# How to use?
Here we show you how to use it and some sample code.  
However, you will need to look at the JavaDoc, as not everything here is available.  
### Create an object. 
```java
SimpleJson json = new SimpleJson(); // An empty object.
```
```java
// An object of a json file.  
// If the file already exists, SimpleJson creates a new file by the path.
SimpleJson json = new SimpleJson(file);
```
```java
SimpleJson json = new SimpleJson(new Foo()); // An object of an instance.
```
```java
SimpleJson json = new SimpleJson("{\"smile\":\"noob\"}"); // An object of a json text.
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
You can put a value in a deep node with separate characters.  
If the node doesn't exist, SimpleJson creates an empty node.  
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
Remove a node.  
You can operate as well as put.  
```java
json.remove(key, value);
```
### Get
Get a node as (pseudo) primitive type, their wrapper classes, or reference type.  
```java
json.getInt("key");
json.getList("key2");
```
### Convert
Convert and get an object.
<dl>
  <dt>SimpleJson#toString()</dt>
  <dd>Convert an object into String</dd>
  <dt>SimpleJson#toJsonNode()</dt>
  <dd>Convert an object into JsonNode</dd>
  <dt>SimpleJson#toJavaObject()</dt>
  <dd>Convert an object into an instance of the specified class</dd>
  <dt>SimpleJson#toMap()</dt>
  <dd>Convert an object into Map</dd>
</dl>

### Save
Save in a file.  
You can't save if you haven't set a file to save from a constructor or SimpleJsonProperty#setFile(file).  
```java
json.save();
```
